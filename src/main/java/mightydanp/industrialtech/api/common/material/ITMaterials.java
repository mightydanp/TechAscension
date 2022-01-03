package mightydanp.industrialtech.api.common.material;

import mightydanp.industrialtech.api.common.material.data.MaterialJsonProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 11/6/2021.
 */
public class ITMaterials extends MaterialJsonProvider {
    public static List<ITMaterial> ITMaterials = new ArrayList<>();

    /*
    public static List<MaterialHandler> materials = new ArrayList<>();

    public static void commonInit() {

    }



     */
    public static void clientInit() {
    /*
        for(MaterialHandler material : materials) {
            material.registerColorHandlerForBlock();
            material.registerColorForItem();
        }
        */

    }
}
