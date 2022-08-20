package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;

import java.util.List;

public class PartHolders {
    public record handlePartHolder(Pair<String, String> prefixAndSuffix, boolean special, List<String> partItems, Class<? extends HandleItem> part){}
    public record dullHeadPartHolder(Pair<String, String> prefixAndSuffix, boolean special, Class<? extends DullHeadItem> part){}
    public record headPartHolder(Pair<String, String> prefixAndSuffix, boolean special, List<String> partItems, Class<? extends HeadItem> part){}
    public record bindingPartHolder(Pair<String, String> prefixAndSuffix, boolean special, List<String> partItems, Class<? extends BindingItem> part){}
}
