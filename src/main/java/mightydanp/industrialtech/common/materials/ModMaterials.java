package mightydanp.industrialtech.common.materials;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import static mightydanp.industrialtech.api.common.libs.EnumMaterialFlags.*;
import static mightydanp.industrialtech.api.common.libs.EnumMaterialTextureFlags.*;

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

    public static void commonInit() { //materials.add(iron = new MaterialHandler("iron", 0, 51, 153, "", 8, ORE));
    //Materials that have ORES
        materials.add(alexandrite = new MaterialHandler("alexandrite", 0x6A4D6B, GEM_HORIZONTAL, 8, GEM));
        materials.add(almandine = new MaterialHandler("almandine", 0xff0000, ROUGH, 8, GEM));
        materials.add(amethyst = new MaterialHandler("amethyst", 0xd232d2, RUBY, 8, GEM));
        materials.add(andradite = new MaterialHandler("andradite", 0x967800, ROUGH, 8, GEM));
        materials.add(anthracite = new MaterialHandler("anthracite", 0x383E42, DULL, 8, ORE));
        materials.add(apatite = new MaterialHandler("apatite", 0x78b4fa, DULL, 8, ORE));
        materials.add(aquamarine = new MaterialHandler("aquamarine", 0x7FFFD4, GEM_HORIZONTAL, 8, GEM));
        materials.add(arsenopyrite = new MaterialHandler("arsenopyrite", 0xfaf01e, DULL, 8, ORE));
        materials.add(azurite = new MaterialHandler("azurite", 0x6da4f7, DULL, 8, ORE));
        materials.add(balasruby = new MaterialHandler("balasruby", 0xff6464, GEM_HORIZONTAL, 8, GEM));
        materials.add(barite = new MaterialHandler("barite", 0xe6ebff, DULL, 8, GEM));
        materials.add(bastnasite = new MaterialHandler("bastnasite", 0xc86e2d, FINE, 8, ORE));
        materials.add(bentonite = new MaterialHandler("bentonite", 0xf5d7d2, ROUGH, 16, "NaOH", ORE));
        materials.add(beryllium = new MaterialHandler("beryllium", 0x64b464, METALLIC, 16, "Be", 1560,  2742, ORE));
        materials.add(bixbite = new MaterialHandler("bixbite", 0xff5050, GEM_HORIZONTAL, 8, GEM));
        materials.add(black_eye = new MaterialHandler("black_eye", 0x424442, GEM_HORIZONTAL, 8, GEM));
        materials.add(blue_jasper = new MaterialHandler("blue_jasper", 0x3c7c97, GEM_HORIZONTAL, 8, GEM));
        materials.add(blue_sapphire = new MaterialHandler("blue_sapphire", 0x6464c8, GEM_HORIZONTAL, 8, GEM));
        materials.add(blue_topaz = new MaterialHandler("blue_topaz", 0x0000ff, GEM_HORIZONTAL, 8, GEM));
        materials.add(bromargyrite = new MaterialHandler("bromargyrite", 0x5a2d0a, DULL, 8, ORE));
        materials.add(brown_limonite = new MaterialHandler("brown_limonite", 0xc86400, METALLIC, 8, ORE));
        materials.add(cassiterite = new MaterialHandler("cassiterite", 0xdcdcdc, METALLIC, 8, ORE));
        materials.add(cats_eye = new MaterialHandler("cats_eye", 0x4d7451, GEM_HORIZONTAL, 8, GEM));
        materials.add(certus_quartz = new MaterialHandler("certus_quartz", 0xd2d2e6, QUARTZ, 8, ORE));
        materials.add(chalcopyrite = new MaterialHandler("chalcopyrite", 0xa07828, DULL, 8, ORE));
        materials.add(chromite = new MaterialHandler("chromite", 0x23140f, DULL, 8, ORE));
        materials.add(cinnabar = new MaterialHandler("cinnabar", 0x960000, ROUGH, 8, ORE));
        materials.add(coal = new MaterialHandler("coal", 0x464646, LIGNITE, 8, ORE));
        materials.add(cobaltite = new MaterialHandler("cobaltite", 0x5050fa, METALLIC, 8, ORE));
        materials.add(cooperite = new MaterialHandler("cooperite", 0xffffc8, METALLIC, 8, ORE));
        materials.add(copper = new MaterialHandler("copper", 0xff6400, SHINY, 8, "Cu", ORE));
        materials.add(craponite = new MaterialHandler("craponite", 0xffaab9, GEM_HORIZONTAL, 8, GEM));
        materials.add(diamond = new MaterialHandler("diamond", 0xc8ffff, DIAMOND, 8, GEM));
        materials.add(dioptase = new MaterialHandler("dioptase", 0x00b4b4, GEM_HORIZONTAL, 8, GEM));
        materials.add(dragon_eye = new MaterialHandler("dragon_eye", 0xa45f53, GEM_HORIZONTAL, 8, GEM));
        materials.add(emerald = new MaterialHandler("emerald",  0x50ff50, GEM_HORIZONTAL, 8, GEM));
        materials.add(ferberite = new MaterialHandler("ferberite", 0x373223, DULL, 8, ORE));
        materials.add(galena = new MaterialHandler("galena", 0x643c64, DULL, 8, ORE));
        materials.add(garnierite = new MaterialHandler("garnierite", 0x32c846, METALLIC, 8, ORE));
        materials.add(glauconite = new MaterialHandler("glauconite", 0x82b43c, DULL, 8, ORE));
        materials.add(gold = new MaterialHandler("gold", 0xffff1e, SHINY, 16, "Au", 1337,  3129, ORE));
        materials.add(goshenite = new MaterialHandler("goshenite", 0xf0f0f0, GEM_HORIZONTAL, 8, GEM));
        materials.add(graphite = new MaterialHandler("graphite", 0x808080, DULL, 8, ORE));
        materials.add(green_jasper = new MaterialHandler("green_jasper", 0x5b836c, GEM_HORIZONTAL, 8, GEM));
        materials.add(green_sapphire = new MaterialHandler("green_sapphire", 0x64c882, GEM_HORIZONTAL, 8, GEM));
        materials.add(grossular = new MaterialHandler("grossular", 0xc86400, ROUGH, 8, ORE));
        materials.add(hawks_eye = new MaterialHandler("hawks_eye", 0x4c5e6d, GEM_HORIZONTAL, 8, GEM));
        materials.add(heliodor = new MaterialHandler("heliodor", 0xffff96, GEM_HORIZONTAL, 8, GEM));
        //materials.add(hematite = new MaterialHandler("hematite", 145,  90,  90, 255, 8, ORE));
        materials.add(huebnerite = new MaterialHandler("huebnerite", 0x373223, DULL, 8, ORE));
        materials.add(ilmenite = new MaterialHandler("ilmenite", 0x463732, METALLIC, 8, ORE));
        materials.add(iodinesalt = new MaterialHandler("iodinesalt", 0xf0c8f0, DULL, 8, ORE));
        materials.add(iridium = new MaterialHandler("iridium",  0xf0f0f5, DULL, 16, "Ir",2719,  4701, ORE));
        materials.add(jasper = new MaterialHandler("jasper", 0xc85050, GEM_HORIZONTAL, 8, GEM));
        materials.add(kesterite = new MaterialHandler("kesterite", 0x699b69, DULL, 8, ORE));
        materials.add(lapis = new MaterialHandler("lapis", 0x4646dc, LAPIS, 8, ORE));
        materials.add(lazurite = new MaterialHandler("lazurite", 0x6478ff, LAPIS, 8, ORE));
        materials.add(lepidolite = new MaterialHandler("lepidolite", 0xf0328c, FINE, 8, ORE));
        materials.add(lignite = new MaterialHandler("lignite", 0x644646, LIGNITE, 8, ORE));
        materials.add(magnesite = new MaterialHandler("magnesite", 0xfafab4, METALLIC, 8, ORE));
        materials.add(magnetite = new MaterialHandler("magnetite", 0x1e1e1e, METALLIC, 8, ORE));
        materials.add(malachite = new MaterialHandler("malachite", 0x055f05, DULL, 8, ORE));
        materials.add(maxixe = new MaterialHandler("maxixe", 0x5050ff, GEM_HORIZONTAL, 8, GEM));
        materials.add(molybdenite = new MaterialHandler("molybdenite", 0x191919, DULL, 8, ORE));
        materials.add(monazite = new MaterialHandler("monazite", 0x91919, METALLIC, 8, ORE));
        materials.add(morganite = new MaterialHandler("morganite", 0x324632, DIAMOND, 8, GEM));
        materials.add(ocean_jasper = new MaterialHandler("ocean_jasper", 0x8b7356, GEM_HORIZONTAL, 8, GEM));
        materials.add(olivine = new MaterialHandler("olivine", 0x96ff96, RUBY, 8, GEM));
        materials.add(orange_sapphire = new MaterialHandler("orange_sapphire", 0xdc9632, GEM_HORIZONTAL, 8, GEM));
        materials.add(peat_bituminous = new MaterialHandler("peat_bituminous", 0x50280a, GEM_HORIZONTAL, 8, ORE));
        materials.add(pentlandite = new MaterialHandler("pentlandite", 0xa59605, DULL, 8, ORE));
        materials.add(phosphate = new MaterialHandler("phosphate", 0xffff00, DULL, 8, ORE));
        materials.add(phosphorite = new MaterialHandler("phosphorite", 0x323241, DULL, 8, ORE));
        materials.add(phosphorus = new MaterialHandler("phosphorus", 0xffff00, FLINT, 8, ORE));
        materials.add(pitchblende = new MaterialHandler("pitchblende", 0xc8d200, DULL, 8, ORE));
        materials.add(powellite = new MaterialHandler("powellite", 0xffff00, DULL, 8, ORE));
        materials.add(purple_sapphire = new MaterialHandler("purple_sapphire", 0xdc32ff, GEM_HORIZONTAL, 8, GEM));
        materials.add(pyrite = new MaterialHandler("pyrite", 0x967828, ROUGH, 8, ORE));
        materials.add(pyrolusite = new MaterialHandler("pyrolusite", 0x9696aa, DULL, 8, ORE));
        materials.add(pyrope = new MaterialHandler("pyrope", 0x783264, METALLIC, 8, GEM));
        materials.add(quartzite = new MaterialHandler("quartzite", 0xe6cdcd, QUARTZ, 8, ORE));
        materials.add(rainforest_jasper = new MaterialHandler("rainforest_jasper", 0x817b37, GEM_HORIZONTAL, 8, GEM));
        materials.add(redstone = new MaterialHandler("redstone", 0xc80000, ROUGH, 8, ORE));
        materials.add(rocksalt = new MaterialHandler("rocksalt", 0xf0c8c8, FINE, 8, ORE));
        materials.add(ruby = new MaterialHandler("ruby", 0xff6464, RUBY, 8, GEM));
        materials.add(rutile = new MaterialHandler("rutile", 0xd40d5c, GEM_HORIZONTAL, 8, ORE));
        materials.add(salt = new MaterialHandler("salt", 0xfafafa, FINE, 8, ORE));
        materials.add(sapphire = new MaterialHandler("sapphire", 0x6464c8, GEM_HORIZONTAL, 8, GEM));
        materials.add(scheelite = new MaterialHandler("scheelite", 0xc88c14, DULL, 8, ORE));
        materials.add(sodalite = new MaterialHandler("sodalite", 0x1414ff, LAPIS, 8, ORE));
        materials.add(sperrylite = new MaterialHandler("sperrylite", 0x696969, DULL, 8, ORE));
        materials.add(spessartine = new MaterialHandler("spessartine", 0xff6464, DULL, 8, ORE));
        materials.add(sphalerite = new MaterialHandler("sphalerite", 0xffffff, DULL, 8, ORE));
        materials.add(spinel = new MaterialHandler("spinel", 0x006400, GEM_HORIZONTAL, 8, GEM));
        materials.add(spodumene = new MaterialHandler("spodumene", 0xbeaaaa, DULL, 8, ORE));
        materials.add(stannite = new MaterialHandler("stannite", 0x9b9137, DULL, 8, ORE));
        materials.add(stibnite = new MaterialHandler("stibnite", 0x464646, METALLIC, 8, ORE));
        //materials.add(subbituminous = new MaterialHandler("subbituminous", 0, 51, 153, alpha, "",0 , 0, 8, ORE));
        materials.add(tantalite = new MaterialHandler("tantalite", 0x915028, METALLIC, 8, ORE));
        materials.add(tetrahedrite = new MaterialHandler("tetrahedrite", 0xc82000, DULL, 8, ORE));
        materials.add(thorium = new MaterialHandler("thorium", 0x001e00, SHINY, 16, "Th", 2115,  5061, ORE));
        materials.add(tiger_eye = new MaterialHandler("tiger_eye", 0x8e743d, GEM_HORIZONTAL, 8, GEM));
        materials.add(tiger_iron = new MaterialHandler("tiger_iron", 0x6a5642, GEM_HORIZONTAL, 8, GEM));
        materials.add(topaz = new MaterialHandler("topaz", 0x0000ff, GEM_HORIZONTAL, 8, GEM));
        materials.add(tungstate = new MaterialHandler("tungstate", 0x373223, DULL, 8, ORE));
        materials.add(uraninite = new MaterialHandler("uraninite", 0x232323, METALLIC, 8, ORE));
        materials.add(uvarovite = new MaterialHandler("uvarovite", 0xb4ffb4, DIAMOND, 8, GEM));
        //materials.add(vanadium_magnetite = new MaterialHandler("vanadium_magnetite", 0, 51, 153, alpha, "",0 , 0, 8, ORE));
        materials.add(wolframite = new MaterialHandler("wolframite", 0x373223, DULL, 8, ORE));
        materials.add(yellow_jasper = new MaterialHandler("yellow_jasper", 0x9c8027, GEM_HORIZONTAL, 8, GEM));
        materials.add(yellow_limonite = new MaterialHandler("yellow_limonite", 0xc8c800, METALLIC, 8, ORE));
        materials.add(yellow_sapphire = new MaterialHandler("yellow_sapphire", 0xd0dc78, GEM_HORIZONTAL, 8, GEM));
        materials.add(zircon = new MaterialHandler("zircon", 0x63181d, SHINY, 8, ORE));
    }

    public static void clientInit() {
        for(MaterialHandler material : materials) {
            material.registerColorHandlerForBlock();
            material.registerColorForItem();
        }
    }
}