package mightydanp.industrialtech.common.inventory.container;

import mightydanp.industrialtech.api.common.handler.IRegistry;
import mightydanp.industrialtech.client.gui.screen.inventory.ContainerScreenBasicMachineFrame;
import mightydanp.industrialtech.common.lib.ContainterTypeReferences;
import mightydanp.industrialtech.common.lib.References;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Created by MightyDanp on 9/5/2020.
 */
public class ModContainers implements IRegistry {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, References.ID);

    public static final RegistryObject<ContainerType<ContainerBasicMachineFrame>> containerBasicMachineFrame = CONTAINERS.register(ContainterTypeReferences.CONTAINER_TYPE_MACHINE_FRAME_NAME, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new ContainerBasicMachineFrame(windowId, world, pos, inv, inv.player);
    }));


    public static void commonInit() {
    }

    public static void clientInit() {
        ScreenManager.registerFactory(containerBasicMachineFrame.get(), ContainerScreenBasicMachineFrame::new);
    }
}
