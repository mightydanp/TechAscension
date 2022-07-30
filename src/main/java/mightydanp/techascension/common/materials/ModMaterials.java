package mightydanp.techascension.common.materials;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialServer;
import mightydanp.techcore.common.jsonconfig.tool.part.IToolPart;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.TCMaterial;
import mightydanp.techcore.common.material.TCMaterials;
import mightydanp.techcore.common.jsonconfig.material.ore.DefaultOreType;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

import static mightydanp.techcore.common.jsonconfig.icons.DefaultTextureIcon.*;

/**
 * Created by MightyDanp on 9/26/2020.
 */

public class ModMaterials extends TCMaterials {

    /*
    public static MaterialHandler alexandrite, almandine, amethyst, andradite, anthracite, apatite, aquamarine, arsenopyrite, azurite, balasruby, barite, bastnasite,
            bentonite, beryllium, bixbite, black_eye, blue_jasper, blue_sapphire, blue_topaz, bromargyrite, brown_limonite, cassiterite, cats_eye, certus_quartz, chalcopyrite,
            chromite, cinnabar, coal, cobaltite, cooperite, copper, craponite, diamond, dioptase, dragon_eye, emerald, ferberite, galena, garnierite, glauconite,
            gold, goshenite, graphite, green_jasper, green_sapphire, grossular, hawks_eye, heliodor, hematite, huebnerite, ilmenite, iodinesalt, iridium, jasper, kesterite,
            lapis, lazurite, lepidolite, lignite, magnesite, magnetite, malachite, maxixe, molybdenite, monazite, morganite, ocean_jasper, olivine, orange_sapphire,
            peat_bituminous, pentlandite, phosphate, phosphorite, phosphorus, pitchblende, powellite, purple_sapphire, pyrite, pyrolusite, pyrope, quartzite, rainforest_jasper,
            redstone, rocksalt, ruby,rutile, salt, sapphire, scheelite, sodalite, sperrylite, spessartine, sphalerite, spinel, spodumene, stannite, stibnite, subbituminous,
            tantalite, tetrahedrite, thorium, tiger_eye, tiger_iron, topaz, tungstate, uraninite, uvarovite, vanadium_magnetite, wolframite, yellow_jasper, yellow_limonite,
            yellow_sapphire, zircon;

    public static MaterialHandler flint, stone;
*/

    public static TCMaterial andesite, diorite, end_stone, granite, netherrack, stone;
    public static TCMaterial alexandrite, almandine;

    public static List<Pair<String,String>> flintToolTypes, stoneToolTypes;
    public static List<IToolPart> flintToolParts;

    public static TCMaterial flint;




    public static void commonInit() {
        Pair<?, ?> materialPair = TCJsonConfigs.material;
        MaterialRegistry materialRegistry = ((MaterialRegistry)materialPair.getFirst());
        MaterialServer materialServer = ((MaterialServer)materialPair.getSecond());

        //--//stone layers
        materialRegistry.register(andesite = new TCMaterial("andesite", 0x747878, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.ANDESITE.getRegistryName()), "minecraft:block/andesite"));
        //MaterialRegistry.registerMaterial(new ITMaterial("basalt", 0x4C4A4A, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, "minecraft:block/basalt"));
        materialRegistry.register(diorite = new TCMaterial("diorite", 0x9dbfb1, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.DIORITE.getRegistryName()), "minecraft:block/diorite"));
        materialRegistry.register(end_stone = new TCMaterial("end_stone", 0xb8b09b, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.END_STONE.getRegistryName()), "minecraft:block/end_stone"));
        materialRegistry.register(granite = new TCMaterial("granite", 0xA26B56, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.GRANITE.getRegistryName()), "minecraft:block/granite"));
        materialRegistry.register(netherrack = new TCMaterial("netherrack", 0x6F4644, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.NETHERRACK.getRegistryName()), "minecraft:block/netherrack"));
        materialRegistry.register(stone = new TCMaterial("stone", 0x808080, new Pair<>(Ref.mod_id, DULL)).setStoneLayerProperties(true, String.valueOf(Blocks.STONE.getRegistryName()), "minecraft:block/stone"));
        //--//
        materialRegistry.register(alexandrite = new TCMaterial("alexandrite", 0x6A4D6B, new Pair<>(Ref.mod_id, GEM_HORIZONTAL)).setOreType(DefaultOreType.ORE).setDenseOreDensity(8));
        materialRegistry.register(almandine = new TCMaterial("almandine", 0xff0000, new Pair<>(Ref.mod_id, ROUGH)).setOreType(DefaultOreType.GEM).setDenseOreDensity(8));

        flintToolTypes = List.of(new Pair<>("", "_handle"), new Pair<>("", "_head"), new Pair<>("", "_binding"));

        materialRegistry.register(flint = new TCMaterial("flint", 0x002040, new Pair<>(Ref.mod_id, CUBE)).setToolProperties( 20, 10, 20F, 1F, 1F, 1, flintToolTypes));

        TCJsonConfigs.configs.put(7, new Pair<>(materialRegistry, materialServer));
        //materials.add(iron = new MaterialHandler("iron", 0, 51, 153, "").addOreProperties(8, false).save());
    //Materials that have ORES
        /*
        materials.add(alexandrite = new MaterialHandler("alexandrite", 0x6A4D6B, GEM_HORIZONTAL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(almandine = new MaterialHandler("almandine", 0xff0000, ROUGH).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(amethyst = new MaterialHandler("amethyst", 0xd232d2, RUBY).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(andradite = new MaterialHandler("andradite", 0x967800, ROUGH).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(anthracite = new MaterialHandler("anthracite", 0x383E42, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(apatite = new MaterialHandler("apatite", 0x78b4fa, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(aquamarine = new MaterialHandler("aquamarine", 0x7FFFD4, DULL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(arsenopyrite = new MaterialHandler("arsenopyrite", 0xfaf01e, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(azurite = new MaterialHandler("azurite", 0x6da4f7, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(balasruby = new MaterialHandler("balasruby", 0xff6464, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(barite = new MaterialHandler("barite", 0xe6ebff, DULL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(bastnasite = new MaterialHandler("bastnasite", 0xc86e2d, FINE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(bentonite = new MaterialHandler("bentonite", 0xf5d7d2, ROUGH).setOreType(DefaultOreType.ORE).setDenseOreDensity(16).addElementLocalization("NaOH").save());
        materials.add(beryllium = new MaterialHandler("beryllium", 0x64b464, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(16).addElementLocalization("Be").addTemperatureProperties(1560, 1560,  2742).save());
        materials.add(bixbite = new MaterialHandler("bixbite", 0xff5050, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(black_eye = new MaterialHandler("black_eye", 0x424442, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(blue_jasper = new MaterialHandler("blue_jasper", 0x3c7c97, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(blue_sapphire = new MaterialHandler("blue_sapphire", 0x6464c8, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(blue_topaz = new MaterialHandler("blue_topaz", 0x0000ff, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(bromargyrite = new MaterialHandler("bromargyrite", 0x5a2d0a, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(brown_limonite = new MaterialHandler("brown_limonite", 0xc86400, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(cassiterite = new MaterialHandler("cassiterite", 0xdcdcdc, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(cats_eye = new MaterialHandler("cats_eye", 0x4d7451, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(certus_quartz = new MaterialHandler("certus_quartz", 0xd2d2e6, QUARTZ).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(chalcopyrite = new MaterialHandler("chalcopyrite", 0xa07828, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(chromite = new MaterialHandler("chromite", 0x23140f, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(cinnabar = new MaterialHandler("cinnabar", 0x960000, ROUGH).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(coal = new MaterialHandler("coal", 0x464646, LIGNITE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(cobaltite = new MaterialHandler("cobaltite", 0x5050fa, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(cooperite = new MaterialHandler("cooperite", 0xffffc8, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(copper = new MaterialHandler("copper", 0xff6400, SHINY).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).addElementLocalization("Cu").save());
        materials.add(craponite = new MaterialHandler("craponite", 0xffaab9, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(diamond = new MaterialHandler("diamond", 0xc8ffff, DIAMOND).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(dioptase = new MaterialHandler("dioptase", 0x00b4b4, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(dragon_eye = new MaterialHandler("dragon_eye", 0xa45f53, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(emerald = new MaterialHandler("emerald",  0x50ff50, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(ferberite = new MaterialHandler("ferberite", 0x373223, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(galena = new MaterialHandler("galena", 0x643c64, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(garnierite = new MaterialHandler("garnierite", 0x32c846, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(glauconite = new MaterialHandler("glauconite", 0x82b43c, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(gold = new MaterialHandler("gold", 0xffff1e, SHINY).setOreType(DefaultOreType.ORE).setDenseOreDensity(16).addElementLocalization("Au").addTemperatureProperties(1337, 1337,  3129).save());
        materials.add(goshenite = new MaterialHandler("goshenite", 0xf0f0f0, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(graphite = new MaterialHandler("graphite", 0x808080, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(green_jasper = new MaterialHandler("green_jasper", 0x5b836c, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(green_sapphire = new MaterialHandler("green_sapphire", 0x64c882, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(grossular = new MaterialHandler("grossular", 0xc86400, ROUGH).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(hawks_eye = new MaterialHandler("hawks_eye", 0x4c5e6d, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(heliodor = new MaterialHandler("heliodor", 0xffff96, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        //materials.add(hematite = new MaterialHandler("hematite", 145,  90,  90, 255).addOreProperties(8, false).save());
        materials.add(huebnerite = new MaterialHandler("huebnerite", 0x373223, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(ilmenite = new MaterialHandler("ilmenite", 0x463732, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(iodinesalt = new MaterialHandler("iodinesalt", 0xf0c8f0, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(iridium = new MaterialHandler("iridium",  0xf0f0f5, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(16).addElementLocalization("Ir").addTemperatureProperties(2719, 2719,  4701).save());
        materials.add(jasper = new MaterialHandler("jasper", 0xc85050, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(kesterite = new MaterialHandler("kesterite", 0x699b69, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(lapis = new MaterialHandler("lapis", 0x4646dc, LAPIS).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(lazurite = new MaterialHandler("lazurite", 0x6478ff, LAPIS).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(lepidolite = new MaterialHandler("lepidolite", 0xf0328c, FINE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(lignite = new MaterialHandler("lignite", 0x644646, LIGNITE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(magnesite = new MaterialHandler("magnesite", 0xfafab4, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(magnetite = new MaterialHandler("magnetite", 0x1e1e1e, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(malachite = new MaterialHandler("malachite", 0x055f05, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(maxixe = new MaterialHandler("maxixe", 0x5050ff, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(molybdenite = new MaterialHandler("molybdenite", 0x191919, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(monazite = new MaterialHandler("monazite", 0x91919, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(morganite = new MaterialHandler("morganite", 0x324632, DIAMOND).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(ocean_jasper = new MaterialHandler("ocean_jasper", 0x8b7356, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(olivine = new MaterialHandler("olivine", 0x96ff96, RUBY).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(orange_sapphire = new MaterialHandler("orange_sapphire", 0xdc9632, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(peat_bituminous = new MaterialHandler("peat_bituminous", 0x50280a, GEM_HORIZONTAL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(pentlandite = new MaterialHandler("pentlandite", 0xa59605, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(phosphate = new MaterialHandler("phosphate", 0xffff00, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(phosphorite = new MaterialHandler("phosphorite", 0x323241, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(phosphorus = new MaterialHandler("phosphorus", 0xffff00, FLINT).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(pitchblende = new MaterialHandler("pitchblende", 0xc8d200, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(powellite = new MaterialHandler("powellite", 0xffff00, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(purple_sapphire = new MaterialHandler("purple_sapphire", 0xdc32ff, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(pyrite = new MaterialHandler("pyrite", 0x967828, ROUGH).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(pyrolusite = new MaterialHandler("pyrolusite", 0x9696aa, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(pyrope = new MaterialHandler("pyrope", 0x783264, METALLIC).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(quartzite = new MaterialHandler("quartzite", 0xe6cdcd, QUARTZ).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(rainforest_jasper = new MaterialHandler("rainforest_jasper", 0x817b37, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(redstone = new MaterialHandler("redstone", 0xc80000, ROUGH).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(rocksalt = new MaterialHandler("rocksalt", 0xf0c8c8, FINE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(ruby = new MaterialHandler("ruby", 0xff6464, RUBY).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(rutile = new MaterialHandler("rutile", 0xd40d5c, GEM_HORIZONTAL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(salt = new MaterialHandler("salt", 0xfafafa, FINE).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(sapphire = new MaterialHandler("sapphire", 0x6464c8, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(scheelite = new MaterialHandler("scheelite", 0xc88c14, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(sodalite = new MaterialHandler("sodalite", 0x1414ff, LAPIS).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(sperrylite = new MaterialHandler("sperrylite", 0x696969, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(spessartine = new MaterialHandler("spessartine", 0xff6464, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(sphalerite = new MaterialHandler("sphalerite", 0xffffff, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(spinel = new MaterialHandler("spinel", 0x006400, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(spodumene = new MaterialHandler("spodumene", 0xbeaaaa, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(stannite = new MaterialHandler("stannite", 0x9b9137, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(stibnite = new MaterialHandler("stibnite", 0x464646, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        //materials.add(subbituminous = new MaterialHandler("subbituminous", 0, 51, 153, alpha, "",0 , 0).addOreProperties(8, false).save());
        materials.add(tantalite = new MaterialHandler("tantalite", 0x915028, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(tetrahedrite = new MaterialHandler("tetrahedrite", 0xc82000, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(thorium = new MaterialHandler("thorium", 0x001e00, SHINY).setOreType(DefaultOreType.ORE).setDenseOreDensity(16).addElementLocalization("Th").addTemperatureProperties(2115, 2115,  5061).save());
        materials.add(tiger_eye = new MaterialHandler("tiger_eye", 0x8e743d, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(tiger_iron = new MaterialHandler("tiger_iron", 0x6a5642, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(topaz = new MaterialHandler("topaz", 0x0000ff, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(tungstate = new MaterialHandler("tungstate", 0x373223, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(uraninite = new MaterialHandler("uraninite", 0x232323, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(uvarovite = new MaterialHandler("uvarovite", 0xb4ffb4, DIAMOND).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        //materials.add(vanadium_magnetite = new MaterialHandler("vanadium_magnetite", 0, 51, 153, alpha, "",0 , 0).addOreProperties(8, false).save());
        materials.add(wolframite = new MaterialHandler("wolframite", 0x373223, DULL).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(yellow_jasper = new MaterialHandler("yellow_jasper", 0x9c8027, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(yellow_limonite = new MaterialHandler("yellow_limonite", 0xc8c800, METALLIC).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());
        materials.add(yellow_sapphire = new MaterialHandler("yellow_sapphire", 0xd0dc78, GEM_HORIZONTAL).setOreType(DefaultOreType.GEM).setDenseOreDensity(8).save());
        materials.add(zircon = new MaterialHandler("zircon", 0x63181d, SHINY).setOreType(DefaultOreType.ORE).setDenseOreDensity(8).save());

        flintToolTypes = new ArrayList<Pair<ToolType, Integer>>(){{
                add(new Pair<>(ToolType.PICKAXE, 0));
        }};

        stoneToolTypes = new ArrayList<Pair<ToolType, Integer>>(){{
            add(new Pair<>(ToolType.PICKAXE, 0));
        }};


        materials.add(flint = new MaterialHandler("flint", 0x002040, CUBE).addToolProperties( 20, 10, 20F, 1F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE).save());

        materials.add(stone = new MaterialHandler("stone", 0x808080, CUBE_SHINY).addToolProperties( 40, 20, 40F, 2F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE).save());

         */
    }
}