package mightydanp.techcore.common.jsonconfig.trait.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ItemTraitCodec(String registry, int color, int maxDamage, String textureIcon, Double kilograms, Double meters) {
    public static String codecName = "item_trait";

    public static final Codec<ItemTraitCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("registry").forGetter(ItemTraitCodec::registry),
            Codec.INT.fieldOf("color").forGetter(ItemTraitCodec::color),
            Codec.INT.fieldOf("max_damage").forGetter(ItemTraitCodec::maxDamage),
            Codec.STRING.fieldOf("texture_icon").forGetter(ItemTraitCodec::textureIcon),
            Codec.DOUBLE.fieldOf("kilograms").forGetter(ItemTraitCodec::kilograms),
            Codec.DOUBLE.fieldOf("meters").forGetter(ItemTraitCodec::meters)
    ).apply(instance, ItemTraitCodec::new));

}
