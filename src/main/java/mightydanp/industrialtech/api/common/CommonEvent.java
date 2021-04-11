package mightydanp.industrialtech.api.common;

import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialtech.common.generation.OreGeneration;
import mightydanp.industrialtech.common.generation.PlantGeneration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class CommonEvent {

    public static ContainerType<ITToolItemContainer> itToolItemContainerContainerType;

    public static void init(FMLCommonSetupEvent event) {
        ToolHandler.commonInit();
        OreGeneration.init();
        PlantGeneration.init();
    }
}
