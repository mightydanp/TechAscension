package mightydanp.industrialtech.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import mightydanp.industrialtech.common.inventory.container.ContainerBasicMachineFrame;
import mightydanp.industrialtech.common.lib.References;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Created by MightyDanp on 9/6/2020.
 */
public class ContainerScreenBasicMachineFrame extends ContainerScreen<ContainerBasicMachineFrame> {

    private ResourceLocation GUI = new ResourceLocation(References.ID, "textures/gui/basic_machine_frame.png");

    public ContainerScreenBasicMachineFrame(ContainerBasicMachineFrame containerBasic, PlayerInventory playerInventory, ITextComponent title) {
        super(containerBasic, playerInventory, title);
        xSize = 176;
        ySize = 166;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {/*
        final float LABEL_XPOS = 5;
        final float FONT_Y_SPACING = 12;
        final float CHEST_LABEL_YPOS = ContainerBasicMachineFrame.TILE_INVENTORY_YPOS - FONT_Y_SPACING;
        font.drawString(this.title.getFormattedText(), LABEL_XPOS, CHEST_LABEL_YPOS, Color.darkGray.getRGB());

        final float PLAYER_INV_LABEL_YPOS = ContainerBasicMachineFrame.PLAYER_INVENTORY_YPOS - FONT_Y_SPACING;
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(),
                LABEL_XPOS, PLAYER_INV_LABEL_YPOS, Color.darkGray.getRGB());
        drawString(Minecraft.getInstance().fontRenderer, "Energy: " + container.getEnergy(), 10, 10, 0xffffff);
        */
    }
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }
}