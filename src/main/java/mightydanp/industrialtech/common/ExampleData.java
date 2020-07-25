package mightydanp.industrialtech.common;

import mightydanp.industrialtech.common.lib.References;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.material.Element;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.TextureSet;

public class ExampleData {

    public static TextureSet SET_DULL = new TextureSet(References.ID, "dull");

    public static Material ALUMINIUM = new Material(References.ID, "aluminium", 0x80c8f0, SET_DULL, Element.Al).asMetal(933, 1700);

    public static ItemBasic<?> IRIDIUM_ALLOY_INGOT = new ItemBasic<>(References.ID, "iridium_alloy_ingot");

    public static void init() {

    }
}
