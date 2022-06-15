package mightydanp.industrialcore.common.material;

import mightydanp.industrialcore.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialcore.common.jsonconfig.tool.part.IToolPart;

import java.util.List;

public interface IMaterial {

    void save(ITMaterial material, List<ITMaterial> stoneLayerList, List<IToolPart> toolParts, List<IMaterialFlag> materialFlag);

    void saveResources(ITMaterial material, List<ITMaterial> stoneLayerList, List<IToolPart> toolParts, List<IMaterialFlag> materialFlag);

    void clientRenderLayerInit(ITMaterial material, List<IToolPart> toolParts, List<IMaterialFlag> materialFlag);

    void registerColorForBlock(ITMaterial material, List<IToolPart> toolParts, List<IMaterialFlag> materialFlag);

    void registerColorForItem(ITMaterial material, List<IToolPart> toolParts, List<IMaterialFlag> materialFlag);
}
