package mightydanp.industrialtech.common.materials;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;

/**
 * Created by MightyDanp on 9/26/2020.
 */

public class ModMaterials {
    public static MaterialHandler iron;

    public static void commonInit() {
        iron = new MaterialHandler( "iron", 0x339998, "Fe", MaterialFlags.ORE);
    }

    public static void clientInit() {
        iron.registerColorHandlerForBlock(iron.blockOre.get());
        iron.registerColorForItem(iron.itemOre.get());
    }
}