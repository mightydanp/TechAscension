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
import net.minecraft.item.Item;
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
        ItemStack toolHandleItemStack = itToolItemContainer.getSlot(0).getItem();
        ItemStack toolHeadItemStack = itToolItemContainer.getSlot(1).getItem();
        ItemStack bindingItemStack = itToolItemContainer.getSlot(2).getItem();
        Item HeadItem = toolHeadItemStack.getItem();
        Item bindingItem = bindingItemStack.getItem();
        Item HandleItem = toolHandleItemStack.getItem();
        ItemStack heldItem = this.inventory.player.getMainHandItem();

        ITToolItem toolItem = (ITToolItem)heldItem.getItem();
        if(!toolHeadItemStack.isEmpty()){
            toolItem.setEfficiency(heldItem, ((ToolHeadItem)HeadItem).efficiency);
            toolItem.setAttackDamage(heldItem, ((ToolHeadItem)HeadItem).attackDamage);
            toolItem.setHarvestLevel(heldItem, ((ToolHeadItem)HeadItem).itToolType);
            toolItem.setHeadColor(heldItem, ((ToolHeadItem)HeadItem).color);
        }

        if(!bindingItemStack.isEmpty()){
            toolItem.setBindingColor(heldItem, ((ToolBindingItem)bindingItem).color);
        }

        if(!toolHandleItemStack.isEmpty()){
            toolItem.setHandleColor(heldItem, ((ToolHandleItem)HandleItem).color);
        }

        if(!toolHeadItemStack.isEmpty() && !bindingItemStack.isEmpty() && !toolHandleItemStack.isEmpty()){
            toolItem.setAttackSpeed(heldItem, ((ToolHeadItem)HeadItem).weight + ((ToolBindingItem)bindingItem).weight + ((ToolHandleItem)HandleItem).weight);
        }

        super.onClose();
    }
}
