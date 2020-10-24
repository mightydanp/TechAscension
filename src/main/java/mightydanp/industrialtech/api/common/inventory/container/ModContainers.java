package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/16/2020.
 */
public class ModContainers {

    public static RegistryObject<ContainerType<CustomPlayerInventoryContainer>> customInventoryContainer;

    public static void init(){
        customInventoryContainer = RegistryHandler.CONTAINERS.register("custom_inventory", () -> IForgeContainerType.create((windowId, inv, data) -> new CustomPlayerInventoryContainer(windowId, inv, inv.player)));
    }
}