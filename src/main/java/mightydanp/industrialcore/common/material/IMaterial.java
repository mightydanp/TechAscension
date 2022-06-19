package mightydanp.industrialcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialcore.common.jsonconfig.tool.part.IToolPart;

import java.util.List;

public interface IMaterial {

    void save(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    void saveResources(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag);

    void clientRenderLayerInit(ITMaterial material);

    void registerColorForBlock(ITMaterial material);

    void registerColorForItem(ITMaterial material);
}
