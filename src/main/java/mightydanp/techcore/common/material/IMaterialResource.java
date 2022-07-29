package mightydanp.techcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.flag.IMaterialFlag;

import java.util.List;

public interface IMaterialResource {
    void saveResources(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag);
}
