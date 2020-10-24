package mightydanp.industrialtech.api.client.gui.widget;

import mightydanp.industrialtech.api.client.gui.screen.CustomPlayerInventoryScreen;
import mightydanp.industrialtech.api.common.inventory.container.CustomPlayerInventoryContainer;
import mightydanp.industrialtech.api.common.packet.PacketHandler;
import mightydanp.industrialtech.api.common.packet.PacketOpenCustomInventoryGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * Created by MightyDanp on 10/13/2020.
 */
public class IngameModListButton extends Button {

    public IngameModListButton(final Screen gui, final int y, int x) {
        super(gui.width / 2 - x, y, 88, 20, ITextComponent.getTextComponentOrEmpty(I18n.format("industrialtech.general.button.custom_inventory")), button -> PacketHandler.sendToServer(new PacketOpenCustomInventoryGui()));
    }
}