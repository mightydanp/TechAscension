package mightydanp.techapi.common.resources.asset;

import mightydanp.techapi.common.resources.asset.data.ModelData;
import mightydanp.techapi.common.resources.asset.data.BlockStateData;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class AssetPackRegistry {
    public static Map<String, BlockStateData> blockStateDataMap = new HashMap<>();
    public static Map<String, ModelData> blockModelDataMap = new HashMap<>();
    public static Map<String, ModelData> itemModelDataHashMap = new HashMap<>();
    public static Map<String, LangData> langDataMap = new HashMap<>();

    public static void init() {
        blockStateDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "blockstates/" + s + ".json"), b.createJson()));
        blockModelDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id,    "models/" + b.getModelFolder() + "/" + (b.getParentFolder() == null ? "" : b.getParentFolder() + "/")  + s + ".json"), b.createJson()));
        itemModelDataHashMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/" + b.getModelFolder() + "/" + (b.getParentFolder() == null ? "" : b.getParentFolder() + "/")  + (s.contains(":") ? s.split(":")[1] : s) + ".json"), b.createJson()));
        langDataMap.forEach((s, b) -> TechAscension.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "lang/" + s + ".json"), b.translations));
    }

    public static void saveBlockStateData(String name, BlockStateData blockStateData, boolean existCheck) {
        if(existCheck){
            if(!blockStateDataMap.containsKey(name)){
                blockStateDataMap.put(name, blockStateData);
            }
        }else{
            blockStateDataMap.put(name, blockStateData);
        }
    }

    public static void saveBlockModelDataMap(String name,  ModelData blockModelData, boolean existCheck) {
        if(existCheck){
            if(!blockStateDataMap.containsKey(name)){
                blockModelDataMap.put(name, blockModelData);
            }
        }else{
            blockModelDataMap.put(name, blockModelData);
        }
    }

    public static void saveItemModelDataHashMap(String name,  ModelData itemModelData, boolean existCheck) {
        if(existCheck){
            if(!blockStateDataMap.containsKey(name)){
                itemModelDataHashMap.put(name, itemModelData);
            }
        }else{
            itemModelDataHashMap.put(name, itemModelData);
        }
    }

    public static void saveLangDataMap(String name,  LangData langData, boolean existCheck) {
        if(existCheck){
            if(!blockStateDataMap.containsKey(name)){
                langDataMap.put(name, langData);
            }
        }else{
            langDataMap.put(name, langData);
        }
    }
}
