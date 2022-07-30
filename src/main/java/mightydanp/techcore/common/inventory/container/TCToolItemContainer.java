package mightydanp.techcore.common.inventory.container;

import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.itemstack.TCToolItemInventoryHelper;
import mightydanp.techcore.common.libs.ContainerRef;
import mightydanp.techcore.common.items.TCToolItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class TCToolItemContainer extends AbstractContainerMenu {

    public static final String itToolItemContainerContainerType = ContainerRef.IT_TOOL_CONTAINER_NAME;
    public MenuType<?> itToolItemContainerType;
    private TCToolItemInventoryHelper itToolItemItemStackHandler;
    public ItemStack itemStackBeingHeld;


    final int slotXSpacing = 18;
    final int slotYSpacing = 18;
    final int hotbarXPos = 8;
    final int hotbarYPos = 142;
    final int inventoryXPos = 8;
    public int inventoryYPos = 84;
    private static final int hotbarSlotCount = 9;
    private static final int inventoryRowCount = 3;
    private static final int inventoryColumnCount = 9;
    private static final int inventorySlotCount = inventoryColumnCount * inventoryRowCount;
    private static final int vanillaSlotCount = hotbarSlotCount + inventorySlotCount;

    private static final int vanillaFirstSlotIndex = 0;
    public static final int firstSlotStartingIndex = vanillaFirstSlotIndex + vanillaSlotCount;


    private TCToolItemContainer(int containerIdIn, Inventory playerInventoryIn, TCToolItemInventoryHelper itToolItemItemStackHandlerIn, ItemStack itemStackBeingHeldIn) {
        super(Containers.itToolItemContainer, containerIdIn);

        if(itemStackBeingHeldIn.getItem() instanceof TCToolItem toolItem ){

            itToolItemContainerType = Containers.itToolItemContainer;
            itToolItemItemStackHandler = itToolItemItemStackHandlerIn;
            itemStackBeingHeld = itemStackBeingHeldIn;

            for (int x = 0; x < hotbarSlotCount; x++) {
                int slotNumber = x;
                addSlot(new Slot(playerInventoryIn, slotNumber, hotbarXPos + slotXSpacing * x, hotbarYPos));
            }

            for (int y = 0; y < inventoryRowCount; y++) {
                for (int x = 0; x < inventoryColumnCount; x++) {
                    int slotNumber = hotbarSlotCount + y * inventoryColumnCount + x;
                    int xpos = inventoryXPos + x * slotXSpacing;
                    int ypos = inventoryYPos + y * slotYSpacing;
                    addSlot(new Slot(playerInventoryIn, slotNumber, xpos, ypos));
                }
            }
            //addSlot(n new Slot(this., 0, 8, 58));
            //addSlot(new SlotItemHandler(itToolItemContainerType., 1, 8, 8));
            //addSlot(new SlotItemHandler(itToolItemItemStackHandler, 2, 8, 33));
        }
    }

    public static TCToolItemContainer createContainerServerSide(int windowIDIn, Inventory playerInventoryIn, TCToolItemInventoryHelper itToolItemItemStackHandlerIn, ItemStack itemStackIn) {
        return new TCToolItemContainer(windowIDIn, playerInventoryIn, itToolItemItemStackHandlerIn, itemStackIn);
    }

    public static TCToolItemContainer createContainerClientSide(int windowID, Inventory playerInventory, FriendlyByteBuf packetBufferIn) {
        int numberOfSlotsIn = packetBufferIn.readInt();

        try {
            TCToolItemInventoryHelper itToolItemItemStackHandlerIn = new TCToolItemInventoryHelper();

            return new TCToolItemContainer(windowID, playerInventory, itToolItemItemStackHandlerIn, ItemStack.EMPTY);
        } catch (IllegalArgumentException iae) {
            TechAscension.LOGGER.warn(iae);
        }
        return null;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
    }

    @Override
    public boolean stillValid(Player playerEntityIn) {
        return true;
    }




}