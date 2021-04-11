package mightydanp.industrialtech.api.client.inventory.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.items.ToolBindingItem;
import mightydanp.industrialtech.api.common.items.ToolHandleItem;
import mightydanp.industrialtech.api.common.items.ToolHeadItem;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemContainerScreen extends ContainerScreen<ITToolItemContainer> {
    private static final ResourceLocation texture = new ResourceLocation(Ref.mod_id,  "textures/gui/it_tool_item.png");
    private PlayerInventory playerInventory;
    private ITToolItemContainer itToolItemContainer;

    public ITToolItemContainerScreen(ITToolItemContainer itToolItemContainerIn, PlayerInventory playerInventoryIn, ITextComponent iTextComponentIN) {
        super(itToolItemContainerIn, playerInventoryIn, iTextComponentIN);
        playerInventory = playerInventoryIn;
        itToolItemContainer = itToolItemContainerIn;

    }

    @Override
    public void render(MatrixStack matrixStackIn, int mouseXIn, int mouseYIn, float partialTicksIn) {
        this.renderBackground(matrixStackIn);
        this.renderTooltip(matrixStackIn, mouseXIn, mouseYIn);
        super.render(matrixStackIn, mouseXIn, mouseYIn, partialTicksIn);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseXIn, int mouseYIn) {
        //super.renderLabels(matrixStack, mouseXIn, mouseYIn);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicksIn, int mouseXIn, int mouseYIn) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.minecraft.getTextureManager().bind(texture);
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void onClose() {
        ItemStack toolHeadItemStack = itToolItemContainer.getSlot(ITToolItemContainer.firstSlotStartingIndex + 1).getItem();
        ItemStack bindingItemStack = itToolItemContainer.getSlot(ITToolItemContainer.firstSlotStartingIndex + 2).getItem();
        ItemStack toolHandleItemStack = itToolItemContainer.getSlot(ITToolItemContainer.firstSlotStartingIndex + 3).getItem();
        ToolHeadItem toolHeadItem = (ToolHeadItem)toolHeadItemStack.getItem();
        ToolBindingItem bindingItem = (ToolBindingItem)bindingItemStack.getItem();
        ToolHandleItem toolHandleItem = (ToolHandleItem)toolHandleItemStack.getItem();
        ItemStack heldItem = itToolItemContainer.itemStackBeingHeld;
        ITToolItem toolItem = (ITToolItem)heldItem.getItem();
        toolItem.setEfficiency(heldItem, toolHeadItem.efficiency);
        toolItem.setAttackDamage(heldItem, toolHeadItem.attackDamage);
        toolItem.setAttackSpeed(heldItem, toolHeadItem.weight + bindingItem.weight + toolHandleItem.weight);
        toolItem.setHarvestLevel(heldItem, toolHeadItem.itToolType);
        toolItem.setHeadColor(heldItem, toolHeadItem.color);
        toolItem.setBindingColor(heldItem, toolHeadItem.color);
        toolItem.setHandleColor(heldItem, toolHeadItem.color);
        super.onClose();

    }
}
