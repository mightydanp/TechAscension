package mightydanp.industrialcore.client.inventory.container;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mightydanp.industrialcore.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialcore.common.items.ITToolItem;
import mightydanp.industrialcore.common.tool.part.BindingItem;
import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemContainerScreen extends AbstractContainerScreen<ITToolItemContainer> {
    private static final ResourceLocation texture = new ResourceLocation(Ref.mod_id,  "textures/gui/it_tool_item.png");
    private Inventory playerInventory;
    private ITToolItemContainer itToolItemContainer;

    public ITToolItemContainerScreen(ITToolItemContainer itToolItemContainerIn, Inventory playerInventoryIn, Component iTextComponentIN) {
        super(itToolItemContainerIn, playerInventoryIn, iTextComponentIN);
        playerInventory = playerInventoryIn;
        itToolItemContainer = itToolItemContainerIn;

    }

    @Override
    public void render(PoseStack matrixStackIn, int mouseXIn, int mouseYIn, float partialTicksIn) {
        this.renderBackground(matrixStackIn);
        this.renderTooltip(matrixStackIn, mouseXIn, mouseYIn);
        super.render(matrixStackIn, mouseXIn, mouseYIn, partialTicksIn);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseXIn, int mouseYIn) {
        //super.renderLabels(matrixStack, mouseXIn, mouseYIn);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicksIn, int mouseXIn, int mouseYIn) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.minecraft.getTextureManager().bindForSetup(texture);
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void onClose() {
        ItemStack toolHandleItemStack = itToolItemContainer.getSlot(0).getItem();
        ItemStack toolHeadItemStack = itToolItemContainer.getSlot(1).getItem();
        ItemStack bindingItemStack = itToolItemContainer.getSlot(2).getItem();
        Item HeadItem = toolHeadItemStack.getItem();
        Item bindingItem = bindingItemStack.getItem();
        Item HandleItem = toolHandleItemStack.getItem();
        ItemStack heldItem = this.playerInventory.player.getMainHandItem();

        ITToolItem toolItem = (ITToolItem)heldItem.getItem();
        if(!toolHeadItemStack.isEmpty()){
            toolItem.setEfficiency(heldItem, ((mightydanp.industrialcore.common.tool.part.HeadItem)HeadItem).efficiency);
            toolItem.setAttackDamage(heldItem, ((mightydanp.industrialcore.common.tool.part.HeadItem)HeadItem).attackDamage);
            toolItem.setToolLevel(heldItem, ((mightydanp.industrialcore.common.tool.part.HeadItem)HeadItem).itToolType);
            toolItem.setHeadColor(heldItem, ((mightydanp.industrialcore.common.tool.part.HeadItem)HeadItem).color);
        }

        if(!bindingItemStack.isEmpty()){
            toolItem.setBindingColor(heldItem, ((BindingItem)bindingItem).color);
        }

        if(!toolHandleItemStack.isEmpty()){
            toolItem.setHandleColor(heldItem, ((mightydanp.industrialcore.common.tool.part.HandleItem)HandleItem).color);
        }

        if(!toolHeadItemStack.isEmpty() && !bindingItemStack.isEmpty() && !toolHandleItemStack.isEmpty()){
            toolItem.setAttackSpeed(heldItem, ((mightydanp.industrialcore.common.tool.part.HeadItem)HeadItem).weight + ((BindingItem)bindingItem).weight + ((mightydanp.industrialcore.common.tool.part.HandleItem)HandleItem).weight);
        }

        super.onClose();
    }
}
