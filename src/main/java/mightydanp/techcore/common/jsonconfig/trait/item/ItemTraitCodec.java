package mightydanp.techcore.common.jsonconfig.trait.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ItemTraitCodec(String registry, int color, int maxDamage, String textureIcon, Double kilograms, Double meters) {
    public static String codecName = "item_trait";

    public static final Codec<ItemTraitCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("registry").forGetter(ItemTraitCodec::registry),
            Codec.INT.optionalFieldOf("color", 0).forGetter(ItemTraitCodec::color),
            Codec.INT.optionalFieldOf("max_damage", 0).forGetter(ItemTraitCodec::maxDamage),
            Codec.STRING.optionalFieldOf("texture_icon", "").forGetter(ItemTraitCodec::textureIcon),
            Codec.DOUBLE.optionalFieldOf("kilograms", 0D).forGetter(ItemTraitCodec::kilograms),
            Codec.DOUBLE.optionalFieldOf("meters", 0D).forGetter(ItemTraitCodec::meters)
    ).apply(instance, ItemTraitCodec::new));

}
