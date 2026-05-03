package com.mightydanp.techascension;

import com.mightydanp.techascension.client.ref.ModRef;
import com.mightydanp.techascension.materials.TAMaterials;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
@Mod(ModRef.MOD_ID)
public class TechAscension
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public TechAscension(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        TAMaterials.commonInit();
    }
}
