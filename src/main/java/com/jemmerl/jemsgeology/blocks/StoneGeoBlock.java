package com.jemmerl.jemsgeology.blocks;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.data.enums.ore.GradeType;
import com.jemmerl.jemsgeology.init.JemsGeoConfig;
import com.jemmerl.jemsgeology.data.enums.ore.OreType;
import com.jemmerl.jemsgeology.data.enums.StoneGroupType;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.util.UtilMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneGeoBlock extends BaseGeoBlock implements IGeoBlock {

    public StoneGeoBlock(Properties properties, GeologyType geologyType) {
        super(properties, geologyType);
        this.setDefaultState(this.stateContainer.getBaseState().with(ORE_TYPE, OreType.NONE).with(GRADE_TYPE, GradeType.LOWGRADE));
    }

    @Override
    public float getExplosionResistance() {
        return (this.blastResistance * JemsGeoConfig.SERVER.stoneResistance.get());
    }

    // Stone blocks experience both a hardness modifier and depth scaling modifier
    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos) * JemsGeoConfig.SERVER.stoneHardness.get();
        if (f == -1.0F) {
            return 0.0F;
        }

        int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100; // Normal "cannot harvest" speed modifier
        if (!this.getStoneGroupType().equals(StoneGroupType.DETRITUS)) {
            int y = pos.getY();
            if (y <= 50) { f *= (1 + (JemsGeoConfig.SERVER.hardnessDepthFactor.get() - 1) * ((50f - y) / 50f)); } // Increases linearly starting at y = 50
        }
        return player.getDigSpeed(state, pos) / f / (float)i;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity playerIn) {
        if (!world.isRemote) {
            if (!playerIn.isCreative()) {
                if (holdingQuarryTools(state, playerIn) && canQuarryBlock(world, pos, playerIn)) {
                    playerIn.getHeldItemOffhand().damageItem(1, playerIn, (player) -> {
                        player.sendBreakAnimation(Hand.OFF_HAND);
                    });
                    world.destroyBlock(pos, false);
                    spawnAsEntity(world, pos, new ItemStack(state.getBlock().asItem()));

                    // Drop a poor ore item if the quarry'ed block has ore in it (with 50% chance)
                    OreType oreType = state.get(ORE_TYPE);
                    if (JemsGeoConfig.SERVER.ore_quarrying.get() && oreType.hasOre() && world.rand.nextBoolean()) {
                        spawnAsEntity(world, pos, new ItemStack(oreType.getPoorOreItem()));
                    }
                }
            }
            super.onBlockHarvested(world, pos, state, playerIn);
        }
    }


    // Check if the player is holding the right tools to quarry
    private boolean holdingQuarryTools(BlockState state, PlayerEntity player) {
        boolean quarryTool = player.getHeldItemOffhand().isItemEqual(ModItems.QUARRY_TOOL.get().getDefaultInstance());
        boolean canBreak = player.getHeldItemMainhand().canHarvestBlock(state);

        return (quarryTool && canBreak);
    }

    // Check if three connected faces that share a vertex are open to air
    private boolean canQuarryBlock(World world, BlockPos pos, PlayerEntity player) {
        double reachDist = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();

        Direction facing = null;
        RayTraceResult result = player.pick(reachDist, 0.0f, false);
        if (result.getType() == RayTraceResult.Type.BLOCK) {
            ItemUseContext context = new ItemUseContext(player, Hand.MAIN_HAND, ((BlockRayTraceResult) result));
            BlockRayTraceResult res = new BlockRayTraceResult(context.getHitVec(), context.getFace(), context.getPos(), false);
            facing = res.getFace();
        }

        // Catch no face found case
        if (facing == null) {
            return  false;
        }

        // Check if the interacted face is open (should always be, but just in case!)
        boolean center = isFaceOpen(world, pos.offset(facing));
        if (!center) {
            return false;
        }

        // Check vertical pair, if neither are open, then no open corners are possible
        BlockPos upPos = pos.offset(UtilMethods.rotateDirection(facing, Direction.NORTH));
        BlockPos downPos = pos.offset(UtilMethods.rotateDirection(facing, Direction.SOUTH));
        boolean up = isFaceOpen(world, upPos);
        boolean down = isFaceOpen(world, downPos);
        if (!(up || down)) {
            return false;
        }

        // Check horizontal pair. If reached, one of the vertical is open, so any open horizontal means success
        BlockPos leftPos = pos.offset(UtilMethods.rotateDirection(facing, Direction.WEST));
        BlockPos rightPos = pos.offset(UtilMethods.rotateDirection(facing, Direction.EAST));
        boolean left = isFaceOpen(world, leftPos);
        boolean right = isFaceOpen(world, rightPos);

        return (left || right);
    }

    // Check if a face is exposed
    private boolean isFaceOpen(World world, BlockPos offsetPos) {
        BlockState state = world.getBlockState(offsetPos);
        Material stateMaterial = state.getMaterial();
        return (state.isIn(BlockTags.FIRE) || stateMaterial.isLiquid() || stateMaterial.isReplaceable());
    }

}
