package mightydanp.industrialtech.api.common.handler;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public interface RecipeTypeHandler<T extends Recipe<?>> {
    static <T extends Recipe<?>> RecipeType<T> register(final String p_222147_0_) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(p_222147_0_), new RecipeType<T>() {
            public String toString() {
                return p_222147_0_;
            }
        });
    }

    default <C extends Container> Optional<T> tryMatch(Recipe<C> p_222148_1_, Level p_222148_2_, C p_222148_3_) {
        return p_222148_1_.matches(p_222148_3_, p_222148_2_) ? Optional.of((T)p_222148_1_) : Optional.empty();
    }
}
