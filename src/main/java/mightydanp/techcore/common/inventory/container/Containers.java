package mightydanp.techcore.common.inventory.container;

import mightydanp.techcore.common.handler.RegistryHandler;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

/**
 * Created by MightyDanp on 4/8/2021.
 */
public class Containers {
    public static MenuType<ITToolItemContainer> itToolItemContainer;

    public static void init() {
        itToolItemContainer = IForgeMenuType.create(ITToolItemContainer::createContainerClientSide);
        RegistryHandler.CONTAINERS.register(ITToolItemContainer.itToolItemContainerContainerType, () -> itToolItemContainer);
    }
}
