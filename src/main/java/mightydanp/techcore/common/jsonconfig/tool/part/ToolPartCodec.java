package mightydanp.techcore.common.jsonconfig.tool.part;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public record ToolPartCodec(String prefix, String suffix) {
    public static String codecName = "tool_part";

    public static final Codec<ToolPartCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("prefix").forGetter(ToolPartCodec::prefix),
            Codec.STRING.fieldOf("suffix").forGetter(ToolPartCodec::suffix)
    ).apply(instance, ToolPartCodec::new));

    public static String fixesToName(ToolPartCodec codec){
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