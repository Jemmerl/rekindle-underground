package com.jemmerl.jemsgeology.events.loot;

import com.google.gson.JsonObject;
import com.jemmerl.jemsgeology.init.ModItems;
import com.jemmerl.jemsgeology.util.UtilMethods;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class StoneQuarryModifier extends LootModifier {
    protected StoneQuarryModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        Entity entity = context.get(LootParameters.THIS_ENTITY);
        if (!(entity instanceof PlayerEntity)) {
            return generatedLoot;
        }
        PlayerEntity player = (PlayerEntity) entity;

        Vector3d vector3d = context.get(LootParameters.ORIGIN);
        if (vector3d == null) {
            return generatedLoot;
        }
        BlockPos pos = new BlockPos(vector3d);

        BlockState state = context.get(LootParameters.BLOCK_STATE);
        if (state == null) {
            return generatedLoot;
        }

        if (isBlockQuarried(context.getWorld(), pos, state, player)) {
            generatedLoot.clear();
            generatedLoot.add(state.getBlock().asItem().getDefaultInstance());
        }
        return generatedLoot;
    }


    public static class Serializer extends GlobalLootModifierSerializer<StoneQuarryModifier> {
        @Override
        public StoneQuarryModifier read(ResourceLocation location, JsonObject object, ILootCondition[] lootConditions) {
            return new StoneQuarryModifier(lootConditions);
        }

        @Override
        public JsonObject write(StoneQuarryModifier instance) {
            return makeConditions(instance.conditions);
        }
    }


    // Check if the block has been successfully quarried by the player
    private boolean isBlockQuarried(World world, BlockPos pos, BlockState state, PlayerEntity playerIn) {
        if (world.isRemote || playerIn.isCreative()) {
            return false;
        }

        if (holdingQuarryTools(state, playerIn) && canQuarryBlock(world, pos, playerIn)) {
            playerIn.getHeldItemOffhand().damageItem(1, playerIn, (player) -> {
                player.sendBreakAnimation(Hand.OFF_HAND);
            });
            return true;
        }

        return false;
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
