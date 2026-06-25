package com.mightydanp.techascension.registries;

import com.mightydanp.techcore.api.registries.RegistriesHandler;
import com.mightydanp.techcore.materials.Material;
import com.mightydanp.techcore.materials.properties.Icons;
import com.mightydanp.techcore.materials.properties.OreTypes;
import com.mightydanp.techcore.materials.properties.RockSubTypes;
import com.mightydanp.techcore.materials.properties.RockTypes;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class Materials {

    public static Supplier<Material> alexandrite, almandine, amethyst, andradite, anthracite, apatite, aquamarine, arsenopyrite, azurite, balas_ruby, barite, bastnasite,
            bentonite, beryllium, bixbite, black_eye, blue_jasper, blue_sapphire, blue_topaz, bromargyrite, brown_limonite, cassiterite, cats_eye, certus_quartz, chalcopyrite,
            chromite, cinnabar, coal, cobaltite, cooperite, copper, craponite, diamond, dioptase, dragon_eye, emerald, ferberite, galena, garnierite, glauconite,
            gold, goshenite, graphite, green_jasper, green_sapphire, grossular, hawks_eye, heliodor, hematite, hubnerite, ilmenite, iodine_salt, iridium, jasper, kesterite,
            lapis, lazurite, lepidolite, lignite, magnesite, magnetite, malachite, maxixe, molybdenite, monazite, morganite, ocean_jasper, olivine, orange_sapphire,
            peat_bituminous, pentlandite, phosphate, phosphorite, phosphorus, pitchblende, powellite, purple_sapphire, pyrite, pyrolusite, pyrope, rainforest_jasper,
            redstone, rock_salt, ruby,rutile, salt, sapphire, scheelite, sodalite, sperrylite, spessartine, sphalerite, spinel, spodumene, stannite, stibnite, subbituminous,
           tantalite, tetrahedrite, thorium, tiger_eye, tiger_iron, topaz, tungstate, uraninite, uvarovite, vanadium_magnetite, wolframite, yellow_jasper, yellow_limonite,
           yellow_sapphire, zircon;

    //tool
    public static Supplier<Material> flint, stone;

    //rock layers
    public static Supplier<Material> andesite, diorite, basalt, deepslate, end_stone, granite, netherrack, shale, slate, marble, komatiite, limestone, quartzite,
            blueschist, kimberlite, prismarine, greenschist, red_granite, black_granite, dark_prismarine;
    //public static List<ToolPartCodec> flintToolParts;

    public static void commonInit() {
        //rock layers
        andesite = RegistriesHandler.registerMaterial("andesite", () -> new Material("andesite", Icons.DULL.icon())
                .physical.setColor(0x747878).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.EXTRUSIVE.getSubType()).end()
        );
        
        basalt = RegistriesHandler.registerMaterial("basalt", () -> new Material("basalt", Icons.DULL.icon())
                .physical.setColor(0x4C4A4A).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.EXTRUSIVE.getSubType()).end()
        );

        deepslate = RegistriesHandler.registerMaterial("deepslate", () -> new Material("deepslate", Icons.ROUGH.icon())
                .physical.setColor(0x2F3136).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.FOLIATED.getSubType()).existingRockLayer(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS).end()
        );
        
        diorite = RegistriesHandler.registerMaterial("diorite", () -> new Material("diorite", Icons.DULL.icon())
                .physical.setColor(0x9DBFB1).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.INTRUSIVE.getSubType()).end()
        );
        
        end_stone = RegistriesHandler.registerMaterial("end_stone", () -> new Material("end_stone", Icons.DULL.icon())
                .physical.setColor(0xD9DE9E).end()
                .rockLayer.rockLayer(RockTypes.GENERIC.getType(), RockSubTypes.GENERIC.getSubType()).existingRockLayer(Blocks.END_STONE, null, Blocks.END_STONE_BRICKS).end()
        );
        
        granite = RegistriesHandler.registerMaterial("granite", () -> new Material("granite", Icons.DULL.icon())
                .physical.setColor(0xA26B56).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.INTRUSIVE.getSubType()).end()
        );
        
        netherrack = RegistriesHandler.registerMaterial("netherrack", () -> new Material("netherrack", Icons.DULL.icon())
                .physical.setColor(0xC80000).end()
                .rockLayer.rockLayer(RockTypes.GENERIC.getType(), RockSubTypes.GENERIC.getSubType()).existingRockLayer(Blocks.NETHERRACK, null, Blocks.NETHER_BRICKS).end()
        );

        shale = RegistriesHandler.registerMaterial("shale", () -> new Material("shale", Icons.DULL.icon())
                .physical.setColor(0x5F6969).end()
                .rockLayer.rockLayer(RockTypes.SEDIMENTARY.getType(), RockSubTypes.CLASTIC.getSubType()).end()
        );

        slate = RegistriesHandler.registerMaterial("slate", () -> new Material("slate", Icons.DULL.icon())
                .physical.setColor(0x404850).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.FOLIATED.getSubType()).end()
        );

        marble = RegistriesHandler.registerMaterial("marble", () -> new Material("marble", Icons.DULL.icon())
                .physical.setColor(0xC8C8C8).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.NON_FOLIATED.getSubType()).end()
        );

        komatiite = RegistriesHandler.registerMaterial("komatiite", () -> new Material("komatiite", Icons.DULL.icon())
                .physical.setColor(0xD8BC52).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.EXTRUSIVE.getSubType()).end()
        );

        limestone = RegistriesHandler.registerMaterial("limestone", () -> new Material("limestone", Icons.DULL.icon())
                .physical.setColor(0xB8B2A0).end()
                .rockLayer.rockLayer(RockTypes.SEDIMENTARY.getType(), RockSubTypes.CHEMICAL.getSubType()).end()
        );

        quartzite = RegistriesHandler.registerMaterial("quartzite", () -> new Material("quartzite", Icons.QUARTZ.icon())
                .physical.setColor(0xE6CDCD).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.NON_FOLIATED.getSubType()).end()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        blueschist = RegistriesHandler.registerMaterial("blueschist", () -> new Material("blueschist", Icons.DULL.icon())
                .physical.setColor(0x53677C).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.FOLIATED.getSubType()).end()
        );

        kimberlite = RegistriesHandler.registerMaterial("kimberlite", () -> new Material("kimberlite", Icons.DULL.icon())
                .physical.setColor(0x596B52).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.INTRUSIVE.getSubType()).end()
        );

        prismarine = RegistriesHandler.registerMaterial("prismarine", () -> new Material("prismarine", Icons.DULL.icon())
                .physical.setColor(0x6EB2A5).end()
                .rockLayer.rockLayer(RockTypes.GENERIC.getType(), RockSubTypes.GENERIC.getSubType()).end()
        );

        greenschist = RegistriesHandler.registerMaterial("greenschist", () -> new Material("greenschist", Icons.DULL.icon())
                .physical.setColor(0x5B735F).end()
                .rockLayer.rockLayer(RockTypes.METAMORPHIC.getType(), RockSubTypes.FOLIATED.getSubType()).end()
        );

        red_granite = RegistriesHandler.registerMaterial("red_granite", () -> new Material("red_granite", Icons.DULL.icon())
                .physical.setColor(0x8A4B3D).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.INTRUSIVE.getSubType()).end()
        );

        black_granite = RegistriesHandler.registerMaterial("black_granite", () -> new Material("black_granite", Icons.DULL.icon())
                .physical.setColor(0x262626).end()
                .rockLayer.rockLayer(RockTypes.IGNEOUS.getType(), RockSubTypes.INTRUSIVE.getSubType()).end()
        );

        dark_prismarine = RegistriesHandler.registerMaterial("dark_prismarine", () -> new Material("dark_prismarine", Icons.DULL.icon())
                .physical.setColor(0x587D6C).end()
                .rockLayer.rockLayer(RockTypes.GENERIC.getType(), RockSubTypes.GENERIC.getSubType()).end()
        );

        // Legacy tool data kept from TCMaterial/MaterialHandler for later: weight=2F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE.
        //materials.add(stone = new MaterialHandler("stone", 0x808080, CUBE.getSubType(), RockTypes.SHINY).addToolProperties( 40, 20, 40F, 2F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE).save());

        stone = RegistriesHandler.registerMaterial("stone", () -> new Material("stone", Icons.DULL.icon())
                .physical.setColor(0xCDCDCD).end()
                    .tool.setAttackSpeed(40).setDurability(20).setAttackDamage(40F).end()
        );

        //none rock layers
        alexandrite = RegistriesHandler.registerMaterial("alexandrite", () -> new Material("alexandrite", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xC8FFAA).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
                .thermal.setMeltingPoint(1870.0).end()
        );

        almandine = RegistriesHandler.registerMaterial("almandine", () -> new Material("almandine", Icons.ROUGH.icon())
                .physical.setColor(0xFF0000).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        amethyst = RegistriesHandler.registerMaterial("amethyst", () -> new Material("amethyst", Icons.RUBY.icon())
                .physical.setColor(0xA678F1).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        andradite = RegistriesHandler.registerMaterial("andradite", () -> new Material("andradite", Icons.ROUGH.icon())
                .physical.setColor(0x967800).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        anthracite = RegistriesHandler.registerMaterial("anthracite", () -> new Material("anthracite", Icons.DULL.icon())
                .physical.setColor(0x5A5A5A).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        apatite = RegistriesHandler.registerMaterial("apatite", () -> new Material("apatite", Icons.DULL.icon())
                .physical.setColor(0x78B4FA).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        aquamarine = RegistriesHandler.registerMaterial("aquamarine", () -> new Material("aquamarine", Icons.DULL.icon())
                .physical.setColor(0xC8DCFF).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        arsenopyrite = RegistriesHandler.registerMaterial("arsenopyrite", () -> new Material("arsenopyrite", Icons.DULL.icon())
                .physical.setColor(0xFAF01E).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        azurite = RegistriesHandler.registerMaterial("azurite", () -> new Material("azurite", Icons.DULL.icon())
                .physical.setColor(0x6DA4F7).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        balas_ruby = RegistriesHandler.registerMaterial("balas_ruby", () -> new Material("balas_ruby", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xFF6464).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        barite = RegistriesHandler.registerMaterial("barite", () -> new Material("barite", Icons.DULL.icon())
                .physical.setColor(0xE6EBFF).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        bastnasite = RegistriesHandler.registerMaterial("bastnasite", () -> new Material("bastnasite", Icons.FINE.icon())
                .physical.setColor(0xC86E2D).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        bentonite = RegistriesHandler.registerMaterial("bentonite", () -> new Material("bentonite", Icons.ROUGH.icon())
                .physical.setColor(0xFFC004).end()
                .chemical.setSymbol("NaOH").end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 16, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        // Legacy middle temperature value kept for later: addTemperatureProperties(1560, 1560, 2742).
        beryllium = RegistriesHandler.registerMaterial("beryllium", () -> new Material("beryllium", Icons.METALLIC.icon())
                .physical.setColor(0x64B464).end()
                .chemical.setSymbol("Be").end()
                .thermal.setMeltingPoint(1560.0).setBoilingPoint(2742.0).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 16, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        bixbite = RegistriesHandler.registerMaterial("bixbite", () -> new Material("bixbite", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xFF5050).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        black_eye = RegistriesHandler.registerMaterial("black_eye", () -> new Material("black_eye", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x424442).end()//RockSubTypes.CLASTIC.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        blue_jasper = RegistriesHandler.registerMaterial("blue_jasper", () -> new Material("blue_jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x3C7C97).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        blue_sapphire = RegistriesHandler.registerMaterial("blue_sapphire", () -> new Material("blue_sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x7878A0).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        blue_topaz = RegistriesHandler.registerMaterial("blue_topaz", () -> new Material("blue_topaz", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x7B96DC).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        bromargyrite = RegistriesHandler.registerMaterial("bromargyrite", () -> new Material("bromargyrite", Icons.DULL.icon())
                .physical.setColor(0x5A2D0A).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        brown_limonite = RegistriesHandler.registerMaterial("brown_limonite", () -> new Material("brown_limonite", Icons.METALLIC.icon())
                .physical.setColor(0xC86400).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        cassiterite = RegistriesHandler.registerMaterial("cassiterite", () -> new Material("cassiterite", Icons.METALLIC.icon())
                .physical.setColor(0xDCDCDC).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        cats_eye = RegistriesHandler.registerMaterial("cats_eye", () -> new Material("cats_eye", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x4D7451).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        certus_quartz = RegistriesHandler.registerMaterial("certus_quartz", () -> new Material("certus_quartz", Icons.QUARTZ.icon())
                .physical.setColor(0xD2D2E6).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        chalcopyrite = RegistriesHandler.registerMaterial("chalcopyrite", () -> new Material("chalcopyrite", Icons.DULL.icon())
                .physical.setColor(0xA07828).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        chromite = RegistriesHandler.registerMaterial("chromite", () -> new Material("chromite", Icons.DULL.icon())
                .physical.setColor(0x23140F).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        cinnabar = RegistriesHandler.registerMaterial("cinnabar", () -> new Material("cinnabar", Icons.ROUGH.icon())
                .physical.setColor(0x960000).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        coal = RegistriesHandler.registerMaterial("coal", () -> new Material("coal", Icons.LIGNITE.icon())
                .physical.setColor(0x464646).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        cobaltite = RegistriesHandler.registerMaterial("cobaltite", () -> new Material("cobaltite", Icons.METALLIC.icon())
                .physical.setColor(0x5050FA).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        cooperite = RegistriesHandler.registerMaterial("cooperite", () -> new Material("cooperite", Icons.METALLIC.icon())
                .physical.setColor(0x82A0E6).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        copper = RegistriesHandler.registerMaterial("copper", () -> new Material("copper", Icons.SHINY.icon())
                .physical.setColor(0xFF825A).end().chemical.setSymbol("Cu").end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        craponite = RegistriesHandler.registerMaterial("craponite", () -> new Material("craponite", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xFFAAB9).end()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.GENERIC.getType()).end()
        );

        diamond = RegistriesHandler.registerMaterial("diamond", () -> new Material("diamond", Icons.DIAMOND.icon())
                .physical.setColor(0xC8FFFF).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        dioptase = RegistriesHandler.registerMaterial("dioptase", () -> new Material("dioptase", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x00B4B4).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        dragon_eye = RegistriesHandler.registerMaterial("dragon_eye", () -> new Material("dragon_eye", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xA45F53).end()//RockSubTypes.CLASTIC.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        emerald = RegistriesHandler.registerMaterial("emerald", () -> new Material("emerald", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x50FF50).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        ferberite = RegistriesHandler.registerMaterial("ferberite", () -> new Material("ferberite", Icons.DULL.icon())
                .physical.setColor(0x373223).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        galena = RegistriesHandler.registerMaterial("galena", () -> new Material("galena", Icons.DULL.icon())
                .physical.setColor(0x643C64).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        garnierite = RegistriesHandler.registerMaterial("garnierite", () -> new Material("garnierite", Icons.METALLIC.icon())
                .physical.setColor(0x32C846).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        glauconite = RegistriesHandler.registerMaterial("glauconite", () -> new Material("glauconite", Icons.DULL.icon())
                .physical.setColor(0x82B43C).end()//RockSubTypes.CLASTIC.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        // Legacy middle temperature value kept for later: addTemperatureProperties(1337, 1337, 3129).
        gold = RegistriesHandler.registerMaterial("gold", () -> new Material("gold", Icons.SHINY.icon())
                .physical.setColor(0xFFE650).end().chemical.setSymbol("Au").end()
                .thermal.setMeltingPoint(1337.0).setBoilingPoint(3129.0).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 16, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        goshenite = RegistriesHandler.registerMaterial("goshenite", () -> new Material("goshenite", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xF0F0F0).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        graphite = RegistriesHandler.registerMaterial("graphite", () -> new Material("graphite", Icons.DULL.icon())
                .physical.setColor(0x808080).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        green_jasper = RegistriesHandler.registerMaterial("green_jasper", () -> new Material("green_jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x5B836C).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        green_sapphire = RegistriesHandler.registerMaterial("green_sapphire", () -> new Material("green_sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x64C882).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        grossular = RegistriesHandler.registerMaterial("grossular", () -> new Material("grossular", Icons.ROUGH.icon())
                .physical.setColor(0xC86400).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        hawks_eye = RegistriesHandler.registerMaterial("hawks_eye", () -> new Material("hawks_eye", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x4C5E6D).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        heliodor = RegistriesHandler.registerMaterial("heliodor", () -> new Material("heliodor", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xFFFF96).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        // Legacy constructor had no icon mapping: new MaterialHandler("hematite", 145, 90, 90, 255).addOreProperties(8, false).
        hematite = RegistriesHandler.registerMaterial("hematite", () -> new Material("hematite", Icons.DULL.icon())
                .physical.setColor(0xC85050).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        hubnerite = RegistriesHandler.registerMaterial("hubnerite", () -> new Material("hubnerite", Icons.DULL.icon())
                .physical.setColor(0x373223).end()//RockSubTypes.getSubType.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        ilmenite = RegistriesHandler.registerMaterial("ilmenite", () -> new Material("ilmenite", Icons.METALLIC.icon())
                .physical.setColor(0x463732).end()//RockSubTypes.getSubType.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        iodine_salt = RegistriesHandler.registerMaterial("iodine_salt", () -> new Material("iodine_salt", Icons.DULL.icon())
                .physical.setColor(0xF0C8F0).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        iridium = RegistriesHandler.registerMaterial("iridium", () -> new Material("iridium", Icons.DULL.icon())
                .physical.setColor(0xF0F0F5).end()
                .chemical.setSymbol("Ir").end()
                .thermal.setMeltingPoint(2719.0).setBoilingPoint(4701.0).end()//RockSubTypes.getSubType.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 16, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        jasper = RegistriesHandler.registerMaterial("jasper", () -> new Material("jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xC85050).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        kesterite = RegistriesHandler.registerMaterial("kesterite", () -> new Material("kesterite", Icons.DULL.icon())
                .physical.setColor(0x699B69).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        lapis = RegistriesHandler.registerMaterial("lapis", () -> new Material("lapis", Icons.LAPIS.icon())
                .physical.setColor(0x4646DC).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        lazurite = RegistriesHandler.registerMaterial("lazurite", () -> new Material("lazurite", Icons.LAPIS.icon())
                .physical.setColor(0x6478FF).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        lepidolite = RegistriesHandler.registerMaterial("lepidolite", () -> new Material("lepidolite", Icons.FINE.icon())
                .physical.setColor(0xF0328C).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        lignite = RegistriesHandler.registerMaterial("lignite", () -> new Material("lignite", Icons.LIGNITE.icon())
                .physical.setColor(0x644646).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        magnesite = RegistriesHandler.registerMaterial("magnesite", () -> new Material("magnesite", Icons.METALLIC.icon())
                .physical.setColor(0xE1CDCD).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        magnetite = RegistriesHandler.registerMaterial("magnetite", () -> new Material("magnetite", Icons.METALLIC.icon())
                .physical.setColor(0x1E1E1E).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        malachite = RegistriesHandler.registerMaterial("malachite", () -> new Material("malachite", Icons.DULL.icon())
                .physical.setColor(0x055F05).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        maxixe = RegistriesHandler.registerMaterial("maxixe", () -> new Material("maxixe", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x5050FF).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        molybdenite = RegistriesHandler.registerMaterial("molybdenite", () -> new Material("molybdenite", Icons.DULL.icon())
                .physical.setColor(0x191919).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        monazite = RegistriesHandler.registerMaterial("monazite", () -> new Material("monazite", Icons.METALLIC.icon())
                .physical.setColor(0x324632).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        morganite = RegistriesHandler.registerMaterial("morganite", () -> new Material("morganite", Icons.DIAMOND.icon())
                .physical.setColor(0xFFC8C8).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        ocean_jasper = RegistriesHandler.registerMaterial("ocean_jasper", () -> new Material("ocean_jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x8B7356).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        olivine = RegistriesHandler.registerMaterial("olivine", () -> new Material("olivine", Icons.RUBY.icon())
                .physical.setColor(0x96FF96).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        orange_sapphire = RegistriesHandler.registerMaterial("orange_sapphire", () -> new Material("orange_sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xDC9632).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        peat_bituminous = RegistriesHandler.registerMaterial("peat_bituminous", () -> new Material("peat_bituminous", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x50280A).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        pentlandite = RegistriesHandler.registerMaterial("pentlandite", () -> new Material("pentlandite", Icons.DULL.icon())
                .physical.setColor(0xA59605).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        phosphate = RegistriesHandler.registerMaterial("phosphate", () -> new Material("phosphate", Icons.DULL.icon())
                .physical.setColor(0xFFFF00).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        phosphorite = RegistriesHandler.registerMaterial("phosphorite", () -> new Material("phosphorite", Icons.DULL.icon())
                .physical.setColor(0x323241).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        phosphorus = RegistriesHandler.registerMaterial("phosphorus", () -> new Material("phosphorus", Icons.FLINT.icon())
                .physical.setColor(0xFFFF00).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.ORGANIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        pitchblende = RegistriesHandler.registerMaterial("pitchblende", () -> new Material("pitchblende", Icons.DULL.icon())
                .physical.setColor(0x646E00).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        powellite = RegistriesHandler.registerMaterial("powellite", () -> new Material("powellite", Icons.DULL.icon())
                .physical.setColor(0xFFFF00).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        purple_sapphire = RegistriesHandler.registerMaterial("purple_sapphire", () -> new Material("purple_sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xDC32FF).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        pyrite = RegistriesHandler.registerMaterial("pyrite", () -> new Material("pyrite", Icons.ROUGH.icon())
                .physical.setColor(0xFFE650).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        pyrolusite = RegistriesHandler.registerMaterial("pyrolusite", () -> new Material("pyrolusite", Icons.DULL.icon())
                .physical.setColor(0x323232).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        pyrope = RegistriesHandler.registerMaterial("pyrope", () -> new Material("pyrope", Icons.METALLIC.icon())
                .physical.setColor(0x783264).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        rainforest_jasper = RegistriesHandler.registerMaterial("rainforest_jasper", () -> new Material("rainforest_jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x817B37).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        redstone = RegistriesHandler.registerMaterial("redstone", () -> new Material("redstone", Icons.ROUGH.icon())
                .physical.setColor(0xC80000).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        rock_salt = RegistriesHandler.registerMaterial("rock_salt", () -> new Material("rock_salt", Icons.FINE.icon())
                .physical.setColor(0xF0C8C8).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        ruby = RegistriesHandler.registerMaterial("ruby", () -> new Material("ruby", Icons.RUBY.icon())
                .physical.setColor(0xFF6464).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        rutile = RegistriesHandler.registerMaterial("rutile", () -> new Material("rutile", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xE6E6E6).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        salt = RegistriesHandler.registerMaterial("salt", () -> new Material("salt", Icons.FINE.icon())
                .physical.setColor(0xFAFAFA).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        sapphire = RegistriesHandler.registerMaterial("sapphire", () -> new Material("sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x7878A0).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        scheelite = RegistriesHandler.registerMaterial("scheelite", () -> new Material("scheelite", Icons.DULL.icon())
                .physical.setColor(0xDEDE00).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        sodalite = RegistriesHandler.registerMaterial("sodalite", () -> new Material("sodalite", Icons.LAPIS.icon())
                .physical.setColor(0x1414FF).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        sperrylite = RegistriesHandler.registerMaterial("sperrylite", () -> new Material("sperrylite", Icons.DULL.icon())
                .physical.setColor(0x696969).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        spessartine = RegistriesHandler.registerMaterial("spessartine", () -> new Material("spessartine", Icons.DULL.icon())
                .physical.setColor(0xFF6464).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        sphalerite = RegistriesHandler.registerMaterial("sphalerite", () -> new Material("sphalerite", Icons.DULL.icon())
                .physical.setColor(0xFFFFFF).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        spinel = RegistriesHandler.registerMaterial("spinel", () -> new Material("spinel", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x006400).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        spodumene = RegistriesHandler.registerMaterial("spodumene", () -> new Material("spodumene", Icons.DULL.icon())
                .physical.setColor(0xBEAAAA).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        stannite = RegistriesHandler.registerMaterial("stannite", () -> new Material("stannite", Icons.DULL.icon())
                .physical.setColor(0x9B9137).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        stibnite = RegistriesHandler.registerMaterial("stibnite", () -> new Material("stibnite", Icons.METALLIC.icon())
                .physical.setColor(0x464646).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        // Legacy constructor had alpha/string/extra values with no icon mapping: new MaterialHandler("subbituminous", 0, 51, 153, alpha, "", 0, 0).addOreProperties(8, false).
        subbituminous = RegistriesHandler.registerMaterial("subbituminous", () -> new Material("subbituminous", Icons.DULL.icon())
                .physical.setColor(0x003399).end()//RockSubTypes.ORGANIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        tantalite = RegistriesHandler.registerMaterial("tantalite", () -> new Material("tantalite", Icons.METALLIC.icon())
                .physical.setColor(0x915028).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        tetrahedrite = RegistriesHandler.registerMaterial("tetrahedrite", () -> new Material("tetrahedrite", Icons.DULL.icon())
                .physical.setColor(0xC82000).end()//RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        thorium = RegistriesHandler.registerMaterial("thorium", () -> new Material("thorium", Icons.SHINY.icon())
                .physical.setColor(0x001E00).end().chemical.setSymbol("Th").end()
                .thermal.setMeltingPoint(2115.0).setBoilingPoint(5061.0).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 16, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        tiger_eye = RegistriesHandler.registerMaterial("tiger_eye", () -> new Material("tiger_eye", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x8E743D).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        tiger_iron = RegistriesHandler.registerMaterial("tiger_iron", () -> new Material("tiger_iron", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x6A5642).end()//RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        topaz = RegistriesHandler.registerMaterial("topaz", () -> new Material("topaz", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xFF8000).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        tungstate = RegistriesHandler.registerMaterial("tungstate", () -> new Material("tungstate", Icons.DULL.icon())
                .physical.setColor(0x373223).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        uraninite = RegistriesHandler.registerMaterial("uraninite", () -> new Material("uraninite", Icons.METALLIC.icon())
                .physical.setColor(0x232323).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        uvarovite = RegistriesHandler.registerMaterial("uvarovite", () -> new Material("uvarovite", Icons.DIAMOND.icon())
                .physical.setColor(0xB4FFB4).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.GENERIC.getType()).end()
        );

        // Legacy constructor had alpha/string/extra values with no icon mapping: new MaterialHandler("vanadium_magnetite", 0, 51, 153, alpha, "", 0, 0).addOreProperties(8, false).
        vanadium_magnetite = RegistriesHandler.registerMaterial("vanadium_magnetite", () -> new Material("vanadium_magnetite", Icons.DULL.icon())
                .physical.setColor(0x23233C).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        wolframite = RegistriesHandler.registerMaterial("wolframite", () -> new Material("wolframite", Icons.DULL.icon())
                .physical.setColor(0x373223).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.EXTRUSIVE.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.IGNEOUS.getType(), RockTypes.GENERIC.getType()).end()
        );

        yellow_jasper = RegistriesHandler.registerMaterial("yellow_jasper", () -> new Material("yellow_jasper", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0x9C8027).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        yellow_limonite = RegistriesHandler.registerMaterial("yellow_limonite", () -> new Material("yellow_limonite", Icons.METALLIC.icon())
                .physical.setColor(0xC8C800).end()//RockSubTypes.CHEMICAL.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.SEDIMENTARY.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        yellow_sapphire = RegistriesHandler.registerMaterial("yellow_sapphire", () -> new Material("yellow_sapphire", Icons.GEM_HORIZONTAL.icon())
                .physical.setColor(0xDCDC32).end()//RockSubTypes.NON_FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.GEM.oreType(), 8, RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        zircon = RegistriesHandler.registerMaterial("zircon", () -> new Material("zircon", Icons.SHINY.icon())
                .physical.setColor(0x63181D).end()//RockSubTypes.INTRUSIVE.getSubType(), RockSubTypes.FOLIATED.getSubType(), RockSubTypes.CLASTIC.getSubType(), RockSubTypes.GENERIC.getSubType()
                .ore.setOre(OreTypes.ORE.oreType(), 8, RockTypes.IGNEOUS.getType(), RockTypes.METAMORPHIC.getType(), RockTypes.SEDIMENTARY.getType(), RockTypes.GENERIC.getType()).end()
        );

        //materials.add(flint = new MaterialHandler("flint", 0x002040, CUBE).addToolProperties( 20, 10, 20F, 1F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE).save());
        
        // Legacy tool data kept from TCMaterial/MaterialHandler for later: weight=1F, flintToolTypes, TOOL_HEAD, TOOL_WEDGE, TOOL_WEDGE_HANDLE.
        flint = RegistriesHandler.registerMaterial("flint", () -> new Material("flint", Icons.FLINT.icon())
                .physical.setColor(0x002040).end()
                .tool.setAttackSpeed(20).setDurability(10).setAttackDamage(20F).end()
        );
    }


}
