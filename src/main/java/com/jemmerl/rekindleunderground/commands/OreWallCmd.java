package com.jemmerl.rekindleunderground.commands;

import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.types.OreType;
import com.jemmerl.rekindleunderground.util.ModBlockLists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.inventory.IClearable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;

public class OreWallCmd {
    public OreWallCmd (CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("rku").then(Commands.literal("orewall")
                .requires((source) -> {
                    return source.hasPermissionLevel(2);
                }).then(Commands.argument("pos", BlockPosArgument.blockPos())
                        .then(Commands.argument("ore", StringArgumentType.string()).executes((context) -> {
                            return placeOreWall(context.getSource(), BlockPosArgument.getBlockPos(context, "pos"),
                            StringArgumentType.getString(context, "ore"));
                })))
        ));
    }

    private int placeOreWall(CommandSource source, BlockPos pos, String oreString) throws CommandSyntaxException {
        ServerWorld serverworld = source.getWorld();

        OreType oreType = null;
        try {
            oreType = OreType.valueOf(oreString);
        } catch (Error e) {
            throw new SimpleCommandExceptionType(new StringTextComponent("Ore-wall placement failed! Invalid ore name.")).create();
        }

        // Initial values
        int y = 1; // Height offset
        int x = 0; // Width offset

        // Place ores
        for (Block oreBlock : ModBlockLists.ALL_OREBLOCKS) {
            BlockState state = oreBlock.getDefaultState();

            TileEntity tileentity = serverworld.getTileEntity(pos.up(y).north(x));
            IClearable.clearObj(tileentity);
            if (!serverworld.setBlockState(pos.up(y).north(x), state.with(StoneOreBlock.ORE_TYPE, oreType), 2)) {
                throw new SimpleCommandExceptionType(new StringTextComponent("Ore-wall placement failed!")).create();
            }

            if (y < 4) {
                y++;
            } else {
                x++;
                y = 1;
            }
        }

        source.sendFeedback(new StringTextComponent("Placed ores-wall with ore " + oreString + "!"), true);
        return 1;
    }
}
