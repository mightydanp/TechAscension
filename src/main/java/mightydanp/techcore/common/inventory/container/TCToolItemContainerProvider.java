package mightydanp.techcore.common.inventory.container;

import mightydanp.techcore.common.items.TCToolItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class TCToolItemContainerProvider implements MenuProvider {
    private final TCToolItem itToolItem;
    private final ItemStack itemStack;

    public TCToolItemContainerProvider(TCToolItem itToolItemIn, ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.itToolItem = itToolItemIn;
    }

    @Override
    public Component getDisplayName() {
        return itemStack.getHoverName();
    }

    @Nullable
    @Override
    public TCToolItemContainer createMenu(int windowID, Inventory playerInventory, Player playerEntity) {
        TCToolItemContainer newContainerServerSide = TCToolItemContainer.createContainerServerSide(windowID, playerInventory, itToolItem.inventory, itemStack);
        return newContainerServerSide;
    }
}
