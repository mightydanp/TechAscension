package mightydanp.industrialcore.common.material.tool;

import mightydanp.industrialcore.common.items.ITToolItem;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.resources.asset.AssetPackRegistry;
import mightydanp.industrialcore.common.resources.asset.data.ItemModelData;
import mightydanp.industrialcore.common.tool.ITTool;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public class ITTools {
    public static List<ITTool> tools = new ArrayList<>();

    public static void init(){
        tools.forEach(itTool -> {
            ItemModelData data = new ItemModelData().setParent(new ResourceLocation("minecraft", "item/generated"));
            for(int i = 0; i < itTool.parts.size(); i++){
                if(i == 0){
                    if(itTool.parts.size() == 1) {
                        data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName));
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName + "_overlay"));
                    }else{
                        data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName + "_handle"));
                    }
                }

                if(i == 1){
                    if(itTool.parts.size() == 2) {
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName + "_head"));
                    }else{
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName + "_binding"));
                    }
                }

                if(i == 2){
                    data.setTexturesLocation("layer2", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + itTool.toolName + "_head"));
                }
            }

            AssetPackRegistry.itemModelDataHashMap.put(itTool.toolName, data);
        });
    }
}
