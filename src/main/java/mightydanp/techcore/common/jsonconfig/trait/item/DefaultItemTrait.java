package mightydanp.techcore.common.jsonconfig.trait.item;

import mightydanp.techcore.common.jsonconfig.icons.DefaultTextureIcon;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.resources.ResourceLocation;

public enum DefaultItemTrait{
    stick(new ItemTraitCodec(new ResourceLocation("stick").toString(), 0x854F2B, 16, DefaultTextureIcon.WOOD.name(), 4.922384399D, 0D)),
    plant_fiber(new ItemTraitCodec(new ResourceLocation(Ref.mod_id, "plant_fiber").toString(), 0x3c5817, 8, DefaultTextureIcon.LEAF.name(), 0.000136077711, 0.3048)),
    diamond(new ItemTraitCodec(new ResourceLocation("diamond").toString(), 0x854F2B, 10, DefaultTextureIcon.DIAMOND.name(), 0.3864607, 0D));

    private final ItemTraitCodec codec;
    DefaultItemTrait(ItemTraitCodec codec) {
        this.codec = codec;
    }

    public ItemTraitCodec getCodec(){
        return this.codec;
    }
}
