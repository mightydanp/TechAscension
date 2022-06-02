package mightydanp.industrialcore.common.crafting.recipe;

import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/10/2021.
 */

@Mod.EventBusSubscriber(modid = Ref.mod_id, bus=Mod.EventBusSubscriber.Bus.MOD)
public class Recipes {
    public static RecipeType<HoleRecipe> holeType;
    public static RegistryObject<RecipeSerializer<?>> holeSerializer = RegistryHandler.RECIPE_SERIALIZER.register("hole", HoleRecipeSerializer::new);

    @SubscribeEvent
    public static void registerRecipeType(RegistryEvent.Register<Block> event) {
        holeType = RecipeType.register(Ref.mod_id + ":hole");
    }

}
