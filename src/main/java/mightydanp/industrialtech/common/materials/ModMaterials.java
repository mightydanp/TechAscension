package mightydanp.industrialtech.common.materials;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import static mightydanp.industrialtech.api.common.libs.EnumMaterialFlags.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */

public class ModMaterials {
    public static List<MaterialHandler> materials = new ArrayList<MaterialHandler>();
    public static MaterialHandler alexandrite, almandine, amethyst, andradite, anthracite, apatite, aquamarine, arsenopyrite, azurite, balasruby, barite, bastnasite,
            bentonite, beryllium, bixbite, black_eye, blue_jasper, blue_sapphire, blue_topaz, bromargyrite, brown_limonite, cassiterite, cats_eye, certus_quartz, chalcopyrite,
            chromite, cinnabar, coal, cobaltite, cooperite, copper, craponite, diamond, dioptase, dragon_eye, emerald, ferberite, galena, garnierite, glauconite,
            gold, goshenite, graphite, green_jasper, green_sapphire, grossular, hawks_eye, heliodor, hematite, huebnerite, ilmenite, iodinesalt, iridium, jasper, kesterite,
            lapis, lazurite, lepidolite, lignite, magnesite, magnetite, malachite, maxixe, molybdenite, monazite, morganite, ocean_jasper, olivine, orange_sapphire,
            peat_bituminous, pentlandite, phosphate, phosphorite, phosphorus, pitchblende, powellite, purple_sapphire, pyrite, pyrolusite, pyrope, quartzite, rainforest_jasper,
            redstone, rocksalt, ruby,rutile, salt, sapphire, scheelite, sodalite, sperrylite, spessartine, sphalerite, spinel, spodumene, stannite, stibnite, subbituminous,
            tantalite, tetrahedrite, thorium, tiger_eye, tiger_iron, topaz, tungstate, uraninite, uvarovite, vanadium_magnetite, wolframite, yellow_jasper, yellow_limonite,
            yellow_sapphire, zircon;

    public static void commonInit() { //materials.add(iron = new MaterialHandler("iron", 0, 51, 153, "", ORE));
    //Materials that have ores
        materials.add(alexandrite = new MaterialHandler("alexandrite", 255, 255,   0, 127, GEM));
        materials.add(almandine = new MaterialHandler("almandine", 255,   0,   0, -1, GEM));
        materials.add(amethyst = new MaterialHandler("amethyst", 200,  50, 200, 127, GEM));
        materials.add(andradite = new MaterialHandler("andradite", 150, 120,   0, -1, GEM));
        materials.add(anthracite = new MaterialHandler("anthracite", 90,  90,  90, 255, ORE));
        materials.add(apatite = new MaterialHandler("apatite", 120, 180, 250, 255, ORE));
        materials.add(aquamarine = new MaterialHandler("aquamarine", 200, 220, 255, -1, GEM));
        materials.add(arsenopyrite = new MaterialHandler("arsenopyrite", 250, 240,  30, 255, ORE));
        materials.add(azurite = new MaterialHandler("azurite", 109, 164, 247, 255, "",0 , 0, ORE));
        materials.add(balasruby = new MaterialHandler("balasruby", 255, 100, 100, 127, GEM));
        materials.add(barite = new MaterialHandler("barite", 230, 235, 255, 255, GEM));
        materials.add(bastnasite = new MaterialHandler("bastnasite", 200, 110,  45, 255, ORE));
        materials.add(bentonite = new MaterialHandler("bentonite", 245, 215, 210, 255, ORE));
        materials.add(beryllium = new MaterialHandler("beryllium", 100, 180, 100, -1, "Be", 1560,  2742, ORE));
        materials.add(bixbite = new MaterialHandler("bixbite", 255,  80,  80, -1, GEM));
        materials.add(black_eye = new MaterialHandler("black_eye", 66,  68,  66, -1, GEM));
        materials.add(blue_jasper = new MaterialHandler("blue_jasper", 60, 124, 151,-1, GEM));
        materials.add(blue_sapphire = new MaterialHandler("blue_sapphire", 100, 100, 200, -1, GEM));
        materials.add(blue_topaz = new MaterialHandler("blue_topaz", 123, 150, 220, 127, GEM));
        materials.add(bromargyrite = new MaterialHandler("bromargyrite", 90,  45,  10, 255, ORE));
        materials.add(brown_limonite = new MaterialHandler("brown_limonite", 200, 100,   0, 255, ORE));
        materials.add(cassiterite = new MaterialHandler("cassiterite", 220, 220, 220, 255, ORE));
        materials.add(cats_eye = new MaterialHandler("cats_eye", 77, 116,  81, -1, GEM));
        materials.add(certus_quartz = new MaterialHandler("certus_quartz", 210, 210, 230, 255, ORE));
        materials.add(chalcopyrite = new MaterialHandler("chalcopyrite", 160, 120,  40, 255, ORE));
        materials.add(chromite = new MaterialHandler("chromite", 35,  20,  15, 255, ORE));
        materials.add(cinnabar = new MaterialHandler("cinnabar", 150,   0,   0, 255, ORE));
        materials.add(coal = new MaterialHandler("coal", 70,  70,  70, 255, ORE));
        materials.add(cobaltite = new MaterialHandler("cobaltite", 80,  80, 250, 255, ORE));
        materials.add(cooperite = new MaterialHandler("cooperite", 130, 160, 230, 255, ORE));
        materials.add(copper = new MaterialHandler("copper", 130, 160, 230, 255, ORE));
        materials.add(craponite = new MaterialHandler("craponite", 255, 170, 185, 127, GEM));
        materials.add(diamond = new MaterialHandler("diamond", 200, 255, 255 , -1, GEM));
        materials.add(dioptase = new MaterialHandler("dioptase", 0, 180, 180, 127, GEM));
        materials.add(dragon_eye = new MaterialHandler("dragon_eye", 164,  95,  83, -1, GEM));
        materials.add(emerald = new MaterialHandler("emerald", 80, 255,  80, -1, GEM));
        materials.add(ferberite = new MaterialHandler("ferberite", 55,  50,  35, 255, ORE));
        materials.add(galena = new MaterialHandler("galena", 100,  60, 100, 255, ORE));
        materials.add(garnierite = new MaterialHandler("garnierite", 0, 200,  70, 255, ORE));
        materials.add(glauconite = new MaterialHandler("glauconite", 130, 180,  60, 255, ORE));
        materials.add(gold = new MaterialHandler("gold", 255, 230,  80, -1, "Au", 1337,  3129, ORE));
        materials.add(goshenite = new MaterialHandler("goshenite", 240, 240, 240, -1, GEM));
        materials.add(graphite = new MaterialHandler("graphite", 128, 128, 128, 255, ORE));
        materials.add(green_jasper = new MaterialHandler("green_jasper", 91, 131, 108, -1, GEM));
        materials.add(green_sapphire = new MaterialHandler("green_sapphire", 100, 200, 130, -1, GEM));
        materials.add(grossular = new MaterialHandler("grossular", 200, 100,   0, -1, GEM));
        materials.add(hawks_eye = new MaterialHandler("hawks_eye", 76,  94, 109, -1, GEM));
        materials.add(heliodor = new MaterialHandler("heliodor", 255, 255, 150, -1, GEM));
        materials.add(hematite = new MaterialHandler("hematite", 145,  90,  90, 255, ORE));
        materials.add(huebnerite = new MaterialHandler("huebnerite", 55,  50,  35, 255, ORE));
        materials.add(ilmenite = new MaterialHandler("ilmenite", 70,  55,  50, 255, ORE));
        materials.add(iodinesalt = new MaterialHandler("iodinesalt", 240, 200, 240, 255, ORE));
        materials.add(iridium = new MaterialHandler("iridium",  240, 240, 245, -1, "Ir",2719,  4701, ORE));
        materials.add(jasper = new MaterialHandler("jasper", 200,  80,  80 , -1, GEM));
        materials.add(kesterite = new MaterialHandler("kesterite", 105, 155, 105, 255, ORE));
        materials.add(lapis = new MaterialHandler("lapis", 70,  70, 220, 255, ORE));
        materials.add(lazurite = new MaterialHandler("lazurite", 100, 120, 255, 255, ORE));
        materials.add(lepidolite = new MaterialHandler("lepidolite", 240,  50, 140, 255, ORE));
        materials.add(lignite = new MaterialHandler("lignite", 100,  70,  70, 255, ORE));
        materials.add(magnesite = new MaterialHandler("magnesite", 250, 250, 180, 255, ORE));
        materials.add(magnetite = new MaterialHandler("magnetite", 30,  30,  30, 255, ORE));
        materials.add(malachite = new MaterialHandler("malachite", 5,  95,   5, 255, ORE));
        materials.add(maxixe = new MaterialHandler("maxixe", 80,  80, 255, -1, GEM));
        materials.add(molybdenite = new MaterialHandler("molybdenite", 25,  25,  25, 255, ORE));
        materials.add(monazite = new MaterialHandler("monazite", 50,  70,  50, 255, ORE));
        materials.add(morganite = new MaterialHandler("morganite", 255, 200, 200, -1, GEM));
        materials.add(ocean_jasper = new MaterialHandler("ocean_jasper", 139, 115,  86, -1, GEM));
        materials.add(olivine = new MaterialHandler("olivine", 150, 255, 150, 127, GEM));
        materials.add(orange_sapphire = new MaterialHandler("orange_sapphire", 220, 150,  50, -1, GEM));
        materials.add(peat_bituminous = new MaterialHandler("peat_bituminous", 80,  40,  10, 255, ORE));
        materials.add(pentlandite = new MaterialHandler("pentlandite", 165, 150,   5, 255, ORE));
        materials.add(phosphate = new MaterialHandler("phosphate", 255, 255,   0, 255, ORE));
        materials.add(phosphorite = new MaterialHandler("phosphorite", 50,  50,  65, 255, ORE));
        materials.add(phosphorus = new MaterialHandler("phosphorus", 255, 255,   0, 255, ORE));
        materials.add(pitchblende = new MaterialHandler("pitchblende", 100, 110,   0, 255, ORE));
        materials.add(powellite = new MaterialHandler("powellite", 255, 255,   0, 255, ORE));
        materials.add(purple_sapphire = new MaterialHandler("purple_sapphire", 220,  50, 255, -1, GEM));
        materials.add(pyrite = new MaterialHandler("pyrite", 255, 230,  80, 255, ORE));
        materials.add(pyrolusite = new MaterialHandler("pyrolusite", 50,  50,  70, 255, ORE));
        materials.add(pyrope = new MaterialHandler("pyrope", 120,  50, 100, -1, GEM));
        materials.add(quartzite = new MaterialHandler("quartzite", 230, 205, 205, 255, ORE));
        materials.add(rainforest_jasper = new MaterialHandler("rainforest_jasper", 129, 123,  55, -1, GEM));
        materials.add(redstone = new MaterialHandler("redstone", 200,   0,   0, 255, ORE));
        materials.add(rocksalt = new MaterialHandler("rocksalt", 240, 200, 200, 255, ORE));
        materials.add(ruby = new MaterialHandler("ruby", 255, 100, 100, -1, GEM));
        materials.add(rutile = new MaterialHandler("rutile", 110,  80, 120, 255, ORE));
        materials.add(salt = new MaterialHandler("salt", 250, 250, 250, 255, ORE));
        materials.add(sapphire = new MaterialHandler("sapphire", 120, 120, 160, -1, GEM));
        materials.add(scheelite = new MaterialHandler("scheelite", 200, 140,  20, 255, ORE));
        materials.add(sodalite = new MaterialHandler("sodalite", 20,  20, 255, 255, ORE));
        materials.add(sperrylite = new MaterialHandler("sperrylite", 105, 105, 105, 255, ORE));
        materials.add(spessartine = new MaterialHandler("spessartine", 255, 100, 100, -1, GEM));
        materials.add(sphalerite = new MaterialHandler("sphalerite", 222, 222,   0, 255, ORE));
        materials.add(spinel = new MaterialHandler("spinel", 0, 100,   0, 127, GEM));
        materials.add(spodumene = new MaterialHandler("spodumene", 190, 170, 170, 255, ORE));
        materials.add(stannite = new MaterialHandler("stannite", 155, 145,  55, 255, ORE));
        materials.add(stibnite = new MaterialHandler("stibnite", 70,  70,  70, 255, ORE));
        //materials.add(subbituminous = new MaterialHandler("subbituminous", 0, 51, 153, alpha, "",0 , 0, ORE));
        materials.add(tantalite = new MaterialHandler("tantalite", 145,  80,  40, 255, ORE));
        materials.add(tetrahedrite = new MaterialHandler("tetrahedrite", 200,  32,   0, 255, ORE));
        materials.add(thorium = new MaterialHandler("thorium", 0, 30, 0, -1, "Th", 2115,  5061, ORE));
        materials.add(tiger_eye = new MaterialHandler("tiger_eye", 142, 116,  61, -1, GEM));
        materials.add(tiger_iron = new MaterialHandler("tiger_iron", 106,  86,  66, -1, GEM));
        materials.add(topaz = new MaterialHandler("topaz", 255, 128,   0, 127, GEM));
        materials.add(tungstate = new MaterialHandler("tungstate", 55,  50,  35, 255, ORE));
        materials.add(uraninite = new MaterialHandler("uraninite", 35,  35,  35, 255, ORE));
        materials.add(uvarovite = new MaterialHandler("uvarovite", 180, 255, 180, -1, GEM));
        //materials.add(vanadium_magnetite = new MaterialHandler("vanadium_magnetite", 0, 51, 153, alpha, "",0 , 0, ORE));
        materials.add(wolframite = new MaterialHandler("wolframite", 55,  50,  35, 255, ORE));
        materials.add(yellow_jasper = new MaterialHandler("yellow_jasper", 156, 128,  39, -1, GEM));
        materials.add(yellow_limonite = new MaterialHandler("yellow_limonite", 200, 200,   0, 255, ORE));
        materials.add(yellow_sapphire = new MaterialHandler("yellow_sapphire", 120, 120, 160, -1, GEM));
        materials.add(zircon = new MaterialHandler("zircon", 99,  24,  29, 255, ORE));
    }



    public static void clientInit() {
        for(MaterialHandler material : materials) {
            material.registerColorHandlerForBlock();
            material.registerColorForItem();
        }
    }
}