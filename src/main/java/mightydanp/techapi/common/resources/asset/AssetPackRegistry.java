package mightydanp.techapi.common.resources.asset;

import mightydanp.techapi.common.resources.asset.data.BlockModelData;
import mightydanp.techapi.common.resources.asset.data.BlockStateData;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.resources.asset.data.ItemModelData;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AssetPackRegistry {
    public static Map<String, BlockStateData> blockStateDataMap = new HashMap<>();
    public static Map<String, BlockModelData> blockModelDataMap = new HashMap<>();
    public static Map<String, ItemModelData> itemModelDataHashMap = new HashMap<>();
    public static Map<String, LangData> langDataMap = new HashMap<>();

    public static void init() {
        blockStateDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "blockstates/" + s + ".json"), b.createJson()));
        blockModelDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/block/" + (b.getParentFolder() == null ? "" : b.getParentFolder() + "/")  + s + ".json"), b.createJson()));
        itemModelDataHashMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/item/" + (b.getParentFolder() == null ? "" : b.getParentFolder() + "/")  + (s.contains(":") ? s.split(":")[1] : s) + ".json"), b.createJson()));
        langDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "lang/" + s + ".json"), b.translations));
    }
}
