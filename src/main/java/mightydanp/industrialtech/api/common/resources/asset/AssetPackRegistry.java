package mightydanp.industrialtech.api.common.resources.asset;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.resources.asset.data.*;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class AssetPackRegistry {
    public static Map<String, BlockStateData> blockStateDataMap = new HashMap<>();
    public static Map<String, BlockModelData> blockModelDataMap = new HashMap<>();
    public static Map<String, ItemModelData> itemModelDataHashMap = new HashMap<>();
    public static Map<String, LangData> langDataMap = new HashMap<>();

    public static void init() {
        generateBlockStateFiles();
        generateBlockModelFiles();
        generateItemModelFiles();
        generateLangFiles();
    }

    private static void generateBlockStateFiles(){
        blockStateDataMap.forEach((s, b) -> IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "blockstates/" + s + ".json"), b.createJson()));
    }

    private static void generateBlockModelFiles(){
        blockModelDataMap.forEach((s, b) -> IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/block" + (b.getParentFolder() == null ? "" : b.getParentFolder()) + "/" + s + ".json"), b.createJson()));
    }

    private static void generateItemModelFiles(){
        itemModelDataHashMap.forEach((s, b) -> IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/item" + (b.getParentFolder() == null ? "" : b.getParentFolder()) + "/" + (s.contains(":") ? s.split(":")[1] : s) + ".json"), b.createJson()));
    }

    private static void generateLangFiles(){
        langDataMap.forEach((s, b) -> IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "lang/" + s + ".json"), b.translations));
    }
}
