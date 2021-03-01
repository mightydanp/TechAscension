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


    public static List<Object> platinumVain, emeraldVain, uraniumVain, manganeseVain, redstoneVain, olivineVain, rareEarthVain, tinVain,
            copperIronVain, galenaVain, molybdeniteVain, copperVain, tungstateVain, copperIronOxideVain, garniteVain, diamondVain, quartzVain,
            phosphorusVain, nickleVain, magnetiteVain, coalVain, saltCommonVain, saltRareVain, lapisVain, rutileVain;

    public static void init() {

        platinumVain = new ArrayList<Object>() {{
            add(ModMaterials.cooperite);
            add(45);
            add(ModMaterials.sperrylite);
            add(45);
            add(ModMaterials.iridium);
            add(10);
        }};
        OreGenerationHandler.addOreGeneration("platinum_vain",128, 1, 10, 50, 5000, platinumVain, OverWorldBiomes);

        emeraldVain = new ArrayList<Object>() {{
            add(ModMaterials.beryllium);
            add(100);
            add(ModMaterials.thorium);
            add(100);
            add(ModMaterials.emerald);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("emerald_vain", 128, 5, 30, 250, 5000, emeraldVain, OverWorldBiomes);

        uraniumVain = new ArrayList<Object>() {{
            add(ModMaterials.pitchblende);
            add(100);
            add(ModMaterials.uraninite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("uranium_vain", 128, 10, 40, 150, 5000, uraniumVain, OverWorldBiomes);

        manganeseVain = new ArrayList<Object>() {{
            add(ModMaterials.grossular);
            add(75);
            add(ModMaterials.spessartine);
            add(75);
            add(ModMaterials.pyrolusite);
            add(100);
            add(ModMaterials.tantalite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("manganese_vain", 128, 20, 30, 250, 5000, manganeseVain, OverWorldBiomes);

        redstoneVain = new ArrayList<Object>() {{
            add(ModMaterials.ruby);
            add(50);
            add(ModMaterials.cinnabar);
            add(75);
            add(ModMaterials.redstone);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("redstone_vain", 128, 10, 40, 250, 5000, redstoneVain, OverWorldBiomes);

        olivineVain = new ArrayList<Object>() {{//
            add(ModMaterials.bentonite);
            add(100);
            add(ModMaterials.magnesite);
            add(100);
            add(ModMaterials.olivine);
            add(75);
            add(ModMaterials.glauconite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("olivine_vain", 128, 10, 40, 250, 5000, olivineVain, OverWorldBiomes);

        rareEarthVain = new ArrayList<Object>() {{
            add(ModMaterials.bastnasite);
            add(85);
            add(ModMaterials.monazite);
            add(75);
            add(ModMaterials.pyrolusite);
            add(100);
            add(ModMaterials.tantalite);
            add(90);
        }};
        OreGenerationHandler.addOreGeneration("rare_earth_vain", 128, 1, 20, 250, 5000, rareEarthVain, OverWorldBiomes);

        tinVain = new ArrayList<Object>() {{
            add(ModMaterials.cassiterite);
            add(100);
            add(ModMaterials.stannite);
            add(100);
            add(ModMaterials.kesterite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("tin_vain", 128, 60, 120, 250, 5000, tinVain, OverWorldBiomes);

        copperIronVain = new ArrayList<Object>() {{
            add(ModMaterials.sphalerite);
            add(100);
            add(ModMaterials.chalcopyrite);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("copper_iron_vain", 128, 10, 30, 250, 5000, copperIronVain, OverWorldBiomes);

        galenaVain = new ArrayList<Object>() {{
            add(ModMaterials.galena);
            add(100);
            add(ModMaterials.stibnite);
            add(50);
            add(ModMaterials.bromargyrite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("galena_vain", 128, 30, 60, 250, 5000, galenaVain, OverWorldBiomes);

        molybdeniteVain = new ArrayList<Object>() {{
            add(ModMaterials.arsenopyrite);
            add(100);
            add(ModMaterials.powellite);
            add(85);
            add(ModMaterials.molybdenite);
            add(40);
        }};
        OreGenerationHandler.addOreGeneration("molybdenite_vain", 128, 20, 50, 250, 5000, molybdeniteVain, OverWorldBiomes);

        copperVain = new ArrayList<Object>() {{
            add(ModMaterials.tetrahedrite);
            add(100);
            add(ModMaterials.copper);
            add(50);
            add(ModMaterials.stibnite);
            add(35);
        }};
        OreGenerationHandler.addOreGeneration("copper_vain", 128, 80, 120, 250, 5000, copperVain, OverWorldBiomes);

        tungstateVain = new ArrayList<Object>() {{
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
        OreGenerationHandler.addOreGeneration("tungstate_vain", 128, 20, 50, 250, 5000, tungstateVain, OverWorldBiomes);

        copperIronOxideVain = new ArrayList<Object>() {{
            add(ModMaterials.yellow_limonite);
            add(100);
            add(ModMaterials.brown_limonite);
            add(100);
            add(ModMaterials.malachite);
            add(85);
            add(ModMaterials.azurite);
            add(50);
        }};
        OreGenerationHandler.addOreGeneration("copper_iron_oxide_vain", 128, 10, 40, 250, 5000, copperIronOxideVain, OverWorldBiomes);

        garniteVain = new ArrayList<Object>() {{
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
        OreGenerationHandler.addOreGeneration("garnite_vain", 128, 20, 30, 250, 5000, garniteVain, OverWorldBiomes);

        diamondVain = new ArrayList<Object>() {{//
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
        OreGenerationHandler.addOreGeneration("diamond_vain", 128, 5, 20, 3, 5000, diamondVain, OverWorldBiomes);

        quartzVain = new ArrayList<Object>() {{
            add(ModMaterials.certus_quartz);
            add(100);
            add(ModMaterials.quartzite);
            add(85);
            add(ModMaterials.barite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("quartz_vain", 128, 40, 80, 250, 5000, quartzVain, OverWorldBiomes);

        phosphorusVain = new ArrayList<Object>() {{
            add(ModMaterials.apatite);
            add(100);
            add(ModMaterials.phosphate);
            add(50);
            add(ModMaterials.phosphorite);
            add(50);
            add(ModMaterials.phosphorus);
            add(50);
        }};
        OreGenerationHandler.addOreGeneration("phosphorus_vain", 128, 40, 60, 250, 5000, phosphorusVain, OverWorldBiomes);

        nickleVain = new ArrayList<Object>() {{
            add(ModMaterials.garnierite);
            add(100);
            add(ModMaterials.pentlandite);
            add(100);
            add(ModMaterials.cobaltite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("nickle_vain", 128, 10, 40, 250, 5000, nickleVain, OverWorldBiomes);

        magnetiteVain = new ArrayList<Object>() {{
            add(ModMaterials.gold);
            add(50);
            add(ModMaterials.magnetite);
            add(100);
            add(ModMaterials.vanadium_magnetite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("magnetite_vain", 128, 60, 80, 250, 5000, magnetiteVain, OverWorldBiomes);

        coalVain = new ArrayList<Object>() {{//
            add(ModMaterials.anthracite);
            add(100);
            add(ModMaterials.lignite);
            add(100);
            add(ModMaterials.subbituminous);
            add(100);
            add(ModMaterials.peat_bituminous);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration("coal_vain", 128, 50, 80, 250, 5000, coalVain, OverWorldBiomes);

        saltCommonVain = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.iodinesalt);
            add(75);
            add(ModMaterials.rocksalt);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_common_vain", 128, 50, 60, 250, 5000, saltCommonVain, OverWorldBiomes);

        saltRareVain = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.lepidolite);
            add(75);
            add(ModMaterials.spodumene);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("salt_rare_vain", 128, 50, 60, 50, 5000, saltRareVain, OverWorldBiomes);

        lapisVain = new ArrayList<Object>() {{//
            add(ModMaterials.lapis);
            add(100);
            add(ModMaterials.lazurite);
            add(75);
            add(ModMaterials.sodalite);
            add(75);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration("lapis_vain", 128, 20, 50, 250, 5000, lapisVain, OverWorldBiomes);

        rutileVain = new ArrayList<Object>() {{//
            add(ModMaterials.rutile);
            add(85);
            add(ModMaterials.ilmenite);
            add(100);
            add(ModMaterials.zircon);
            add(65);
        }};
        OreGenerationHandler.addOreGeneration("rutile_vain", 128, 10, 40, 250, 5000, rutileVain, OverWorldBiomes);
    }
}
