package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.items.ITToolItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemContainerProvider implements MenuProvider {
    private final ITToolItem itToolItem;
    private final ItemStack itemStack;

    public ITToolItemContainerProvider(ITToolItem itToolItemIn, ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.itToolItem = itToolItemIn;
    }

    @Override
    public Component getDisplayName() {
        return itemStack.getHoverName();
    }

    @Nullable
    @Override
    public ITToolItemContainer createMenu(int windowID, Inventory playerInventory, Player playerEntity) {
        ITToolItemContainer newContainerServerSide = ITToolItemContainer.createContainerServerSide(windowID, playerInventory, itToolItem.getItemStackHandler(itemStack), itemStack);
        return newContainerServerSide;
    }
}
