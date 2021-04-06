package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.datagen.*;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * Created by MightyDanp on 10/2/2020.
 */

@Mod.EventBusSubscriber(bus = Bus.MOD, modid = Ref.mod_id)
public class DataGenEventHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        if (event.includeClient())
        {
            GenBlockTags blockTags = new GenBlockTags(gen, Ref.mod_id, event.getExistingFileHelper());
            gen.addProvider(new GenLanguage(gen));
            gen.addProvider(new GenBlockStates(gen, event.getExistingFileHelper()));
            gen.addProvider(new GenLootTables(gen));
            gen.addProvider(new GenItemModel(gen, Ref.mod_id, event.getExistingFileHelper()));
            gen.addProvider(blockTags);
            gen.addProvider(new GenFluidTags(gen, Ref.mod_id, event.getExistingFileHelper()));
            gen.addProvider(new GenItemTags(gen, blockTags, Ref.mod_id, event.getExistingFileHelper()));
        }

        if (event.includeServer()){
        }
    }
}
