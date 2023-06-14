package mightydanp.techascension.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin extends net.minecraftforge.registries.ForgeRegistryEntry<Item> implements ItemLike, net.minecraftforge.common.extensions.IForgeItem {

}
