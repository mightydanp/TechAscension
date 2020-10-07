package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.handler.OreGenerationHandler;
import mightydanp.industrialtech.api.common.libs.EnumGenerationWorlds;
import mightydanp.industrialtech.common.materials.ModMaterials;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 10/3/2020.
 */
public class OreGeneration {
    public static OreGenerationHandler platinumVain, emeraldVain;
    public static void init(){
        List<MaterialHandler> platinumVainOres = new ArrayList<MaterialHandler>(){{
            add(ModMaterials.cooperite);
            add(ModMaterials.sperrylite);
            add(ModMaterials.iridium);
        }};
        OreGenerationHandler.addOreGeneration(75, 80, 1, 2000, platinumVainOres, new ArrayList<EnumGenerationWorlds>(){{add(EnumGenerationWorlds.overworld);}} , 100, 100, 95);
        List<MaterialHandler> emeraldVainOres = new ArrayList<MaterialHandler>(){{
            add(ModMaterials.beryllium);
            add(ModMaterials.thorium);
            add(ModMaterials.emerald);
        }};
        OreGenerationHandler.addOreGeneration(75, 80, 1, 1000, emeraldVainOres, new ArrayList<EnumGenerationWorlds>(){{add(EnumGenerationWorlds.overworld);}}, 100, 100, 95);
    }
}
