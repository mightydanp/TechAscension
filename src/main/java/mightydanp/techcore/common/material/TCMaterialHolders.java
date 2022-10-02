package mightydanp.techcore.common.material;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class TCMaterialHolders {
    public record itemStoneLayerColorHolder(RegistryObject<Item> item, Integer stoneLayerColor){}
}
