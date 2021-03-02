package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.OreGenerationHandler;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.world.biome.Biome;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 10/3/2020.
 */
public class OreGeneration {
    public static Biome.Category[] OverWorldBiomes = {
        Biome.Category.JUNGLE, Biome.Category.BEACH, Biome.Category.DESERT, Biome.Category.EXTREME_HILLS,
        Biome.Category.FOREST, Biome.Category.ICY, Biome.Category.MESA, Biome.Category.MUSHROOM,
        Biome.Category.OCEAN, Biome.Category.PLAINS, Biome.Category.RIVER, Biome.Category.SAVANNA,
        Biome.Category.SWAMP, Biome.Category.TAIGA};

    public static List<Object> small_ore_gems;

    public static List<Object> platinumVein, emeraldVein, uraniumVein, manganeseVein, redstoneVein, olivineVein, rareEarthVein, tinVein,
            copperIronVein, galenaVein, molybdeniteVein, copperVein, tungstateVein, copperIronOxideVein, garniteVein, diamondVein, quartzVein,
            phosphorusVein, nickleVein, magnetiteVein, coalVein, saltCommonVein, saltRareVein, lapisVein, rutileVein;


    public static void init() {
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
        OreGenerationHandler.addOreGeneration("platinum_vein",128, 1, 10, 50, 5000, platinumVein, OverWorldBiomes);

        emeraldVein = new ArrayList<Object>() {{
            add(ModMaterials.beryllium);
            add(100);
            add(ModMaterials.thorium);
            add(100);
            add(ModMaterials.emerald);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("emerald_vein", 128, 5, 30, 250, 5000, emeraldVein, OverWorldBiomes);

        uraniumVein = new ArrayList<Object>() {{
            add(ModMaterials.pitchblende);
            add(100);
            add(ModMaterials.uraninite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("uranium_vein", 128, 10, 40, 150, 5000, uraniumVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("manganese_vein", 128, 20, 30, 250, 5000, manganeseVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("redstone_vein", 128, 10, 40, 250, 5000, redstoneVein, OverWorldBiomes);

        olivineVein = new ArrayList<Object>() {{//
            add(ModMaterials.bentonite);
            add(100);
            add(ModMaterials.magnesite);
            add(100);
            add(ModMaterials.olivine);
            add(75);
            add(ModMaterials.glauconite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("olivine_vein", 128, 10, 40, 250, 5000, olivineVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("rare_earth_vein", 128, 1, 20, 250, 5000, rareEarthVein, OverWorldBiomes);

        tinVein = new ArrayList<Object>() {{
            add(ModMaterials.cassiterite);
            add(100);
            add(ModMaterials.stannite);
            add(100);
            add(ModMaterials.kesterite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("tin_vein", 128, 60, 120, 250, 5000, tinVein, OverWorldBiomes);

        copperIronVein = new ArrayList<Object>() {{
            add(ModMaterials.sphalerite);
            add(100);
            add(ModMaterials.chalcopyrite);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("copper_iron_vein", 128, 10, 30, 250, 5000, copperIronVein, OverWorldBiomes);

        galenaVein = new ArrayList<Object>() {{
            add(ModMaterials.galena);
            add(100);
            add(ModMaterials.stibnite);
            add(50);
            add(ModMaterials.bromargyrite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("galena_vein", 128, 30, 60, 250, 5000, galenaVein, OverWorldBiomes);

        molybdeniteVein = new ArrayList<Object>() {{
            add(ModMaterials.arsenopyrite);
            add(100);
            add(ModMaterials.powellite);
            add(85);
            add(ModMaterials.molybdenite);
            add(40);
        }};
        OreGenerationHandler.addOreGeneration("molybdenite_vein", 128, 20, 50, 250, 5000, molybdeniteVein, OverWorldBiomes);

        copperVein = new ArrayList<Object>() {{
            add(ModMaterials.tetrahedrite);
            add(100);
            add(ModMaterials.copper);
            add(50);
            add(ModMaterials.stibnite);
            add(35);
        }};
        OreGenerationHandler.addOreGeneration("copper_vein", 128, 80, 120, 250, 5000, copperVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("tungstate_vein", 128, 20, 50, 250, 5000, tungstateVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("copper_iron_oxide_vein", 128, 10, 40, 250, 5000, copperIronOxideVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("garnite_vein", 128, 20, 30, 250, 5000, garniteVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("diamond_vein", 128, 5, 20, 3, 5000, diamondVein, OverWorldBiomes);

        quartzVein = new ArrayList<Object>() {{
            add(ModMaterials.certus_quartz);
            add(100);
            add(ModMaterials.quartzite);
            add(85);
            add(ModMaterials.barite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("quartz_vein", 128, 40, 80, 250, 5000, quartzVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("phosphorus_vein", 128, 40, 60, 250, 5000, phosphorusVein, OverWorldBiomes);

        nickleVein = new ArrayList<Object>() {{
            add(ModMaterials.garnierite);
            add(100);
            add(ModMaterials.pentlandite);
            add(100);
            add(ModMaterials.cobaltite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("nickle_vein", 128, 10, 40, 250, 5000, nickleVein, OverWorldBiomes);

        magnetiteVein = new ArrayList<Object>() {{
            add(ModMaterials.gold);
            add(50);
            add(ModMaterials.magnetite);
            add(100);
            add(ModMaterials.vanadium_magnetite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("magnetite_vein", 128, 60, 80, 250, 5000, magnetiteVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("coal_vein", 128, 50, 80, 250, 5000, coalVein, OverWorldBiomes);

        saltCommonVein = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.iodinesalt);
            add(75);
            add(ModMaterials.rocksalt);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_common_vein", 128, 50, 60, 250, 5000, saltCommonVein, OverWorldBiomes);

        saltRareVein = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.lepidolite);
            add(75);
            add(ModMaterials.spodumene);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_rare_vein", 128, 50, 60, 50, 5000, saltRareVein, OverWorldBiomes);

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
        OreGenerationHandler.addOreGeneration("lapis_vein", 128, 20, 50, 250, 5000, lapisVein, OverWorldBiomes);

        rutileVein = new ArrayList<Object>() {{//
            add(ModMaterials.rutile);
            add(85);
            add(ModMaterials.ilmenite);
            add(100);
            add(ModMaterials.zircon);
            add(65);
        }};
        OreGenerationHandler.addOreGeneration("rutile_vein", 128, 10, 40, 250, 5000, rutileVein, OverWorldBiomes);
    }
}
