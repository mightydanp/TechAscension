package mightydanp.techcore.common.handler;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Created by MightyDanp on 1/31/2021.
 */
public class RegisterHelper {
    public static BooleanProperty createBlockState(String stateName){
        return BooleanProperty.create(stateName);
    }

    public static <F extends Feature<?>> RegistryObject<F> createFeature(String name, Supplier<F> feature){
        return RegistryHandler.FEATURES.register(name, feature);
    }

    public static <T extends IForgeRegistryEntry<T>> ForgeRegistry<T> makeRegistry(String name, Class<T> clazz, String modID, boolean data) {
        RegistryBuilder<T> builder = new RegistryBuilder<T>().setType(clazz).setName(new ResourceLocation(modID, name)).allowModification();
        if (data) {
            builder.disableSaving();
            builder.disableSync();
        }
        return (ForgeRegistry<T>) builder.getCreate();
    }
}
