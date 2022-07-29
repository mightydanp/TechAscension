package mightydanp.techascension.common.generation;

import static net.minecraft.world.level.biome.Biome.BiomeCategory.*;

import net.minecraft.world.level.biome.Biome;

import java.util.List;


/**
 * Created by MightyDanp on 10/3/2020.
 */
public class OreGeneration {
    public static Biome.BiomeCategory[] OverWorldBiomes = {
        TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM
    };

    public static List<Object> small_ore_gems;

    public static List<Object> platinumVein, emeraldVein, uraniumVein, manganeseVein, redstoneVein, olivineVein, rareEarthVein, tinVein,
            copperIronVein, galenaVein, molybdeniteVein, copperVein, tungstateVein, copperIronOxideVein, garniteVein, diamondVein, quartzVein,
            phosphorusVein, nickleVein, magnetiteVein, coalVein, saltCommonVein, saltRareVein, lapisVein, rutileVein;


    public static void init() {
        /*
        //small ore
        small_ore_gems = new ArrayList<Object>() {{
        add(ModMaterials.jasper);add(10);
        add(ModMaterials.blue_jasper); add(10);
        add(ModMaterials.green_jasper); add(10);
        add(ModMaterials.yellow_jasper); add(10);
        add(ModMaterials.rainforest_jasper); add(10);
        add(ModMaterials.tiger_eye); add(10);
        add(ModMaterials.cats_eye); add(10);
        add(ModMaterials.dragon_eye); add(10);
        add(ModMaterials.hawks_eye); add(10);
        add(ModMaterials.black_eye); add(10);
        add(ModMaterials.tiger_iron); add(10);
        add(ModMaterials.dioptase); add(10);
        add(ModMaterials.sapphire); add(10);
        add(ModMaterials.green_sapphire); add(10);
        add(ModMaterials.yellow_sapphire); add(10);
        add(ModMaterials.orange_sapphire); add(10);
        add(ModMaterials.blue_sapphire); add(10);
        add(ModMaterials.purple_sapphire); add(10);
        add(ModMaterials.craponite); add(10);
        add(ModMaterials.amethyst); add(10);
        add(ModMaterials.alexandrite); add(10);
        add(ModMaterials.aquamarine); add(10);
        add(ModMaterials.morganite); add(10);
        add(ModMaterials.heliodor); add(10);
        add(ModMaterials.goshenite); add(10);
        add(ModMaterials.bixbite); add(10);
        add(ModMaterials.maxixe); add(10);
        }};
        OreGenerationHandler.addSmallOreGeneration("small_ore_gems", 1, 256, 5, small_ore_gems, OverWorldBiomes);


        //regular ore
        platinumVein = new ArrayList<Object>() {{
            add(ModMaterials.cooperite);
            add(45);
            add(ModMaterials.sperrylite);
            add(45);
            add(ModMaterials.iridium);
            add(10);
        }};
        OreGenerationHandler.addOreGeneration("platinum_vein",24, 3, 10, 50, 100, rare, platinumVein, OverWorldBiomes);

        //spawns in deserts
        emeraldVein = new ArrayList<Object>() {{
            add(ModMaterials.beryllium);
            add(100);
            add(ModMaterials.thorium);
            add(100);
            add(ModMaterials.emerald);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("emerald_vein", 24, 3, 30, 250, 50, uncommon, emeraldVein, OverWorldBiomes);

        uraniumVein = new ArrayList<Object>() {{
            add(ModMaterials.pitchblende);
            add(100);
            add(ModMaterials.uraninite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("uranium_vein", 24, 3, 40, 150, 100, uncommon, uraniumVein, OverWorldBiomes);

        //depends how hard it is to turn into titanium
        manganeseVein = new ArrayList<Object>() {{
            add(ModMaterials.grossular);
            add(75);
            add(ModMaterials.spessartine);
            add(75);
            add(ModMaterials.pyrolusite);
            add(100);
            add(ModMaterials.tantalite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("manganese_vein", 24, 3, 30, 250, 20, common, manganeseVein, OverWorldBiomes);

        //depends what tier the mineral effect, what you use it for in your processing line
        redstoneVein = new ArrayList<Object>() {{
            add(ModMaterials.ruby);
            add(50);
            add(ModMaterials.cinnabar);
            add(75);
            add(ModMaterials.redstone);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("redstone_vein", 24, 3, 40, 250, 10, common, redstoneVein, OverWorldBiomes);

        olivineVein = new ArrayList<Object>() {{
            add(ModMaterials.bentonite);
            add(100);
            add(ModMaterials.magnesite);
            add(100);
            add(ModMaterials.olivine);
            add(75);
            add(ModMaterials.glauconite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("olivine_vein", 24, 3, 40, 250, 10, uncommon, olivineVein, OverWorldBiomes);

        rareEarthVein = new ArrayList<Object>() {{
            add(ModMaterials.bastnasite);
            add(85);
            add(ModMaterials.monazite);
            add(75);
            add(ModMaterials.pyrolusite);
            add(100);
            add(ModMaterials.tantalite);
            add(90);
        }};
        OreGenerationHandler.addOreGeneration("rare_earth_vein", 24, 3, 20, 250, 100, rare, rareEarthVein, OverWorldBiomes);

        tinVein = new ArrayList<Object>() {{
            add(ModMaterials.cassiterite);
            add(100);
            add(ModMaterials.stannite);
            add(100);
            add(ModMaterials.kesterite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("tin_vein", 24, 3, 120, 250, 20, common, tinVein, OverWorldBiomes);

        copperIronVein = new ArrayList<Object>() {{
            add(ModMaterials.sphalerite);
            add(100);
            add(ModMaterials.chalcopyrite);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("copper_iron_vein", 24, 3, 30, 250, 10, common, copperIronVein, OverWorldBiomes);
        
        galenaVein = new ArrayList<Object>() {{
            add(ModMaterials.galena);
            add(100);
            add(ModMaterials.stibnite);
            add(50);
            add(ModMaterials.bromargyrite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("galena_vein", 24, 3, 60, 250, 30, common, galenaVein, OverWorldBiomes);

        molybdeniteVein = new ArrayList<Object>() {{
            add(ModMaterials.arsenopyrite);
            add(100);
            add(ModMaterials.powellite);
            add(85);
            add(ModMaterials.molybdenite);
            add(40);
        }};
        OreGenerationHandler.addOreGeneration("molybdenite_vein", 24, 3, 50, 250, 20, uncommon, molybdeniteVein, OverWorldBiomes);

        //high levels
        copperVein = new ArrayList<Object>() {{
            add(ModMaterials.tetrahedrite);
            add(100);
            add(ModMaterials.copper);
            add(50);
            add(ModMaterials.stibnite);
            add(35);
        }};
        OreGenerationHandler.addOreGeneration("copper_vein", 24, 3, 120, 250, 30, common, copperVein, OverWorldBiomes);

        tungstateVein = new ArrayList<Object>() {{
            add(ModMaterials.scheelite);
            add(100);
            add(ModMaterials.wolframite);
            add(100);
            add(ModMaterials.ferberite);
            add(100);
            add(ModMaterials.huebnerite);
            add(100);
            add(ModMaterials.tungstate);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("tungstate_vein", 24, 3, 50, 250, 30, rare, tungstateVein, OverWorldBiomes);

        copperIronOxideVein = new ArrayList<Object>() {{
            add(ModMaterials.yellow_limonite);
            add(100);
            add(ModMaterials.brown_limonite);
            add(100);
            add(ModMaterials.malachite);
            add(85);
            add(ModMaterials.azurite);
            add(50);
        }};
        OreGenerationHandler.addOreGeneration("copper_iron_oxide_vein", 24, 3, 40, 250, 10, common, copperIronOxideVein, OverWorldBiomes);

        garniteVein = new ArrayList<Object>() {{
            add(ModMaterials.andradite);
            add(100);
            add(ModMaterials.almandine);
            add(100);
            add(ModMaterials.spessartine);
            add(100);
            add(ModMaterials.pyrope);
            add(100);
            add(ModMaterials.grossular);
            add(100);
            add(ModMaterials.uvarovite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("garnite_vein", 24, 3, 30, 250, 20, common, garniteVein, OverWorldBiomes);

        diamondVein = new ArrayList<Object>() {{//
            add(ModMaterials.diamond);
            add(75);
            add(ModMaterials.spinel);
            add(85);
            add(ModMaterials.balasruby);
            add(85);
            add(ModMaterials.graphite);
            add(100);
            add(ModMaterials.coal);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("diamond_vein", 24, 3, 20, 3, 50, uncommon, diamondVein, OverWorldBiomes);

        quartzVein = new ArrayList<Object>() {{
            add(ModMaterials.certus_quartz);
            add(100);
            add(ModMaterials.quartzite);
            add(85);
            add(ModMaterials.barite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("quartz_vein", 24, 3, 80, 250, 40, common, quartzVein, OverWorldBiomes);

        phosphorusVein = new ArrayList<Object>() {{
            add(ModMaterials.apatite);
            add(100);
            add(ModMaterials.phosphate);
            add(50);
            add(ModMaterials.phosphorite);
            add(50);
            add(ModMaterials.phosphorus);
            add(50);
        }};
        OreGenerationHandler.addOreGeneration("phosphorus_vein", 24, 3, 60, 250, 40, common, phosphorusVein, OverWorldBiomes);

        nickleVein = new ArrayList<Object>() {{
            add(ModMaterials.garnierite);
            add(100);
            add(ModMaterials.pentlandite);
            add(100);
            add(ModMaterials.cobaltite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("nickle_vein", 24, 3, 40, 250, 10, common, nickleVein, OverWorldBiomes);

        magnetiteVein = new ArrayList<Object>() {{
            add(ModMaterials.gold);
            add(50);
            add(ModMaterials.magnetite);
            add(100);
            add(ModMaterials.vanadium_magnetite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("magnetite_vein", 24, 3, 80, 250, 60, common, magnetiteVein, OverWorldBiomes);

        coalVein = new ArrayList<Object>() {{//
            add(ModMaterials.anthracite);
            add(100);
            add(ModMaterials.lignite);
            add(100);
            add(ModMaterials.subbituminous);
            add(100);
            add(ModMaterials.peat_bituminous);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("coal_vein", 24, 3, 80, 250, 50, common, coalVein, OverWorldBiomes);

        saltCommonVein = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.iodinesalt);
            add(75);
            add(ModMaterials.rocksalt);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_common_vein", 24, 3, 60, 250, 50, common, saltCommonVein, OverWorldBiomes);

        saltRareVein = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.lepidolite);
            add(75);
            add(ModMaterials.spodumene);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_rare_vein", 24, 3, 60, 50, 50, rare, saltRareVein, OverWorldBiomes);

        lapisVein = new ArrayList<Object>() {{//
            add(ModMaterials.lapis);
            add(100);
            add(ModMaterials.lazurite);
            add(75);
            add(ModMaterials.sodalite);
            add(75);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("lapis_vein", 24, 3, 50, 250, 20, uncommon, lapisVein, OverWorldBiomes);

        rutileVein = new ArrayList<Object>() {{//
            add(ModMaterials.rutile);
            add(85);
            add(ModMaterials.ilmenite);
            add(100);
            add(ModMaterials.zircon);
            add(65);
        }};
        OreGenerationHandler.addOreGeneration("rutile_vein", 24, 3, 40, 250, 20, uncommon, rutileVein, OverWorldBiomes);

         */
    }
}
