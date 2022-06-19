package mightydanp.industrialcore.common.tool.part;

import com.mojang.datafixers.util.Pair;

public class PartHolders {
    public record handlePartHolder(Pair<String, String> prefixAndSuffix, Class<? extends HandleItem> part){}
    public record dullHeadPartHolder(Pair<String, String> prefixAndSuffix, Class<? extends DullHeadItem> part){}
    public record headPartHolder(Pair<String, String> prefixAndSuffix, Class<? extends HeadItem> part){}
    public record bindingPartHolder(Pair<String, String> prefixAndSuffix, Class<? extends BindingItem> part){}
}
