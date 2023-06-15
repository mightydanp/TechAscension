package mightydanp.techcore.common.jsonconfig.materialflag;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public record MaterialFlagCodec(String prefix, String suffix) {
    public static String codecName = "material_flag";

    public static final Codec<MaterialFlagCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("prefix").forGetter(MaterialFlagCodec::prefix),
            Codec.STRING.fieldOf("suffix").forGetter(MaterialFlagCodec::suffix)
    ).apply(instance, MaterialFlagCodec::new));

    static String fixesToName(MaterialFlagCodec codec){
        String prefix = codec.prefix().replace("_", "");
        String suffix = codec.suffix().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }
}
