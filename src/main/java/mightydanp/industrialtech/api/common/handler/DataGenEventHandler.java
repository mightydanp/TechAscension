package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.datagen.*;
import mightydanp.industrialtech.api.common.datagen.recipes.GenFurnaceRecipes;
import mightydanp.industrialtech.api.common.datagen.recipes.GenHoleRecipes;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.datagen.recipes.ModGenCampfireRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Created by MightyDanp on 10/2/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEventHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        GenFurnaceRecipes modGenFurnaceRecipes = new GenFurnaceRecipes(gen, Ref.mod_id, "smelting");
        ModGenCampfireRecipes modGenCampfireRecipes = new ModGenCampfireRecipes(gen, Ref.mod_id, "campfire");

        if (event.includeClient()){
            GenBlockTags blockTags = new GenBlockTags(gen, Ref.mod_id, event.getExistingFileHelper());
            gen.addProvider(new GenLanguage(gen));
            gen.addProvider(new GenBlockStates(gen, event.getExistingFileHelper()));
            gen.addProvider(new GenLootTables(gen));
            gen.addProvider(new GenItemModel(gen, Ref.mod_id, event.getExistingFileHelper()));
            gen.addProvider(blockTags);
            gen.addProvider(new GenFluidTags(gen, Ref.mod_id, event.getExistingFileHelper()));
            gen.addProvider(new GenItemTags(gen, blockTags, Ref.mod_id, event.getExistingFileHelper()));
            gen.addProvider(modGenFurnaceRecipes.cookingRecipes);
            gen.addProvider(modGenCampfireRecipes.campfireRecipes);
            gen.addProvider(new GenHoleRecipes(gen, Ref.mod_id, "hole"));

        }

        if (event.includeServer()){
        }
    }
}
