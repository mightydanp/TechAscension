package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.items.ToolHeadItem;
import mightydanp.industrialtech.api.common.items.handler.ITToolItemItemStackHandler;
import mightydanp.industrialtech.api.common.libs.ContainerRef;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemContainer extends Container {

    public static final String itToolItemContainerContainerType = ContainerRef.IT_TOOL_CONTAINER_NAME;
    public final ContainerType<?> itToolItemContainerType;
    private final ITToolItemItemStackHandler itToolItemItemStackHandler;
    public final ItemStack itemStackBeingHeld;


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


    private ITToolItemContainer(int containerIdIn, PlayerInventory playerInventoryIn, ITToolItemItemStackHandler itToolItemItemStackHandlerIn, ItemStack itemStackBeingHeldIn) {
        super(Containers.itToolItemContainer, containerIdIn);
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
        addSlot(new SlotItemHandler(itToolItemItemStackHandler, 0, 8, 58));
        addSlot(new SlotItemHandler(itToolItemItemStackHandler, 1, 8, 8));
        addSlot(new SlotItemHandler(itToolItemItemStackHandler, 2, 8, 33));
    }

    public static ITToolItemContainer createContainerServerSide(int windowIDIn, PlayerInventory playerInventoryIn, ITToolItemItemStackHandler itToolItemItemStackHandlerIn, ItemStack itemStackIn) {
        return new ITToolItemContainer(windowIDIn, playerInventoryIn, itToolItemItemStackHandlerIn, itemStackIn);
    }

    public static ITToolItemContainer createContainerClientSide(int windowID, PlayerInventory playerInventory, PacketBuffer packetBufferIn) {
        int numberOfSlotsIn = packetBufferIn.readInt();

        try {
            ITToolItemItemStackHandler itToolItemItemStackHandlerIn = new ITToolItemItemStackHandler(numberOfSlotsIn);

            return new ITToolItemContainer(windowID, playerInventory, itToolItemItemStackHandlerIn, ItemStack.EMPTY);
        } catch (IllegalArgumentException iae) {
            IndustrialTech.LOGGER.warn(iae);
        }
        return null;
    }

    @Override
    public void broadcastChanges() {
        if (itToolItemItemStackHandler.isDirty()) {
            CompoundNBT nbt = itemStackBeingHeld.getOrCreateTag();
            int dirtyCounter = nbt.getInt("dirtyCounter");
            nbt.putInt("dirtyCounter", dirtyCounter + 1);
            itemStackBeingHeld.setTag(nbt);
        }
        super.broadcastChanges();
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntityIn) {
        return true;
    }




}