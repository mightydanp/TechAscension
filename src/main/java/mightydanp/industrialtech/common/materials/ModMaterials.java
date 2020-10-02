package mightydanp.industrialtech.common.materials;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */

public class ModMaterials {
    public static List<MaterialHandler> materials = new ArrayList<MaterialHandler>();
    public static MaterialHandler iron;

    public static void commonInit() {
        materials.add(iron = new MaterialHandler("iron", 0x339998, "Fe", MaterialFlags.ORE));
    }

    public static void clientInit() {
        for(MaterialHandler material : materials) {
            material.registerColorHandlerForBlock();
            material.registerColorForItem();
        }
    }
}