package com.jemmerl.rekindleunderground.commands;

import com.jemmerl.rekindleunderground.util.ModBlockLists;
import com.mojang.brigadier.CommandDispatcher;
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

public class JacksonCmd {
    public JacksonCmd(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("rku").then(Commands.literal("stonewall")
                .requires((source) -> {
                    return source.hasPermissionLevel(2);
                }).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes((context) -> {
                    return placeStonesWall(context.getSource(), BlockPosArgument.getBlockPos(context, "pos"));
                }))));
    }

    private int placeStonesWall(CommandSource source, BlockPos pos) throws CommandSyntaxException {
        ServerWorld serverworld = source.getWorld();

        // Initial values
        int y = 1; // Height offset
        int x = 0; // Width offset

        // Place stones
        for (Block oreBlock : ModBlockLists.ALL_OREBLOCKS) {
            BlockState state = oreBlock.getDefaultState();

            TileEntity tileentity = serverworld.getTileEntity(pos.up(y).north(x));
            IClearable.clearObj(tileentity);
            if (!serverworld.setBlockState(pos.up(y).north(x), state, 2)) {
                throw new SimpleCommandExceptionType(new StringTextComponent("Stone-wall placement failed!")).create();
            }

            if (y < 4) {
                y++;
            } else {
                x++;
                y = 1;
            }
        }

        // Place cobbles
        y = 1;
        x++;
        for (Block cobbleBlock : ModBlockLists.COBBLESTONES) {
            BlockState state = cobbleBlock.getDefaultState();

            TileEntity tileentity = serverworld.getTileEntity(pos.up(y).north(x));
            IClearable.clearObj(tileentity);
            if (!serverworld.setBlockState(pos.up(y).north(x), state, 2)) {
                throw new SimpleCommandExceptionType(new StringTextComponent("Stone-wall placement failed!")).create();
            }

            if (y < 4) {
                y++;
            } else {
                x++;
                y = 1;
            }
        }

        source.sendFeedback(new StringTextComponent("Placed stones-wall!"), true);
        return 1;
    }


}
