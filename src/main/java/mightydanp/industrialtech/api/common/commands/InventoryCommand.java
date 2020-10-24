package mightydanp.industrialtech.api.common.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mightydanp.industrialtech.api.common.inventory.container.CustomPlayerInventoryContainer;
import net.minecraft.command.CommandSource;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * Created by MightyDanp on 10/21/2020.
 */
public class InventoryCommand implements Command<CommandSource> {
    private static final InventoryCommand CMD = new InventoryCommand();

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("gui").executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        NetworkHooks.openGui(player, new SimpleNamedContainerProvider (
                (i, playerInventory, playerEntity) ->
                        new CustomPlayerInventoryContainer(i, playerInventory, playerEntity)
                , new StringTextComponent("accessories")
        ));
        return 0;
    }
}