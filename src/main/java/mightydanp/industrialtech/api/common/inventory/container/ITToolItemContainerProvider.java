package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.items.ITToolItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemContainerProvider implements INamedContainerProvider {
    private final ITToolItem itToolItem;
    private final ItemStack itemStack;

    public ITToolItemContainerProvider(ITToolItem itToolItemIn, ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.itToolItem = itToolItemIn;
    }

    @Override
    public ITextComponent getDisplayName() {
        return itemStack.getHoverName();
    }

    @Nullable
    @Override
    public ITToolItemContainer createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        ITToolItemContainer newContainerServerSide = ITToolItemContainer.createContainerServerSide(windowID, playerInventory, itToolItem.getItemStackHandler(itemStack), itemStack);
        return newContainerServerSide;
    }
}
