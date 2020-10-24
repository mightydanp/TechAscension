package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.handler.OreGenerationHandler;
import mightydanp.industrialtech.api.common.libs.EnumGenerationWorlds;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 10/3/2020.
 */
public class OreGeneration {
    public static List<Object> platinumVain, emeraldVain, uraniumVain, manganeseVain, redstoneVain, olivineVain, rareEarthVain, tinVain,
            copperIronVain, galenaVain, molybdeniteVain, copperVain, tungstateVain, copperIronOxideVain, garniteVain, diamondVain, quartzVain,
            phosphorusVain, nickleVain, magnetiteVain, coalVain, saltCommonVain, saltRareVain, lapisVain, rutileVain;
    public static void init() {

        platinumVain = new ArrayList<Object>() {{
            add(ModMaterials.cooperite);
            add(100);
            add(ModMaterials.sperrylite);
            add(100);
            add(ModMaterials.iridium);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 1, 2000, platinumVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        emeraldVain = new ArrayList<Object>() {{
            add(ModMaterials.beryllium);
            add(100);
            add(ModMaterials.thorium);
            add(100);
            add(ModMaterials.emerald);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 2000, emeraldVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        uraniumVain = new ArrayList<Object>() {{
            add(ModMaterials.pitchblende);
            add(100);
            add(ModMaterials.uraninite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 3, 2000, uraniumVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, manganeseVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, redstoneVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, olivineVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, rareEarthVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        tinVain = new ArrayList<Object>() {{
            add(ModMaterials.cassiterite);
            add(100);
            add(ModMaterials.stannite);
            add(100);
            add(ModMaterials.kesterite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, tinVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        copperIronVain = new ArrayList<Object>() {{
            add(ModMaterials.sphalerite);
            add(100);
            add(ModMaterials.chalcopyrite);
            add(100);
            add(ModMaterials.pyrite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, copperIronVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        galenaVain = new ArrayList<Object>() {{
            add(ModMaterials.galena);
            add(100);
            add(ModMaterials.stibnite);
            add(50);
            add(ModMaterials.bromargyrite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, galenaVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        molybdeniteVain = new ArrayList<Object>() {{
            add(ModMaterials.arsenopyrite);
            add(100);
            add(ModMaterials.powellite);
            add(85);
            add(ModMaterials.molybdenite);
            add(40);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, molybdeniteVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        copperVain = new ArrayList<Object>() {{
            add(ModMaterials.tetrahedrite);
            add(100);
            add(ModMaterials.copper);
            add(50);
            add(ModMaterials.stibnite);
            add(35);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, copperVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, tungstateVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, copperIronOxideVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, garniteVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 3, 2000, diamondVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        quartzVain = new ArrayList<Object>() {{
            add(ModMaterials.certus_quartz);
            add(100);
            add(ModMaterials.quartzite);
            add(85);
            add(ModMaterials.barite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, quartzVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, phosphorusVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        nickleVain = new ArrayList<Object>() {{
            add(ModMaterials.garnierite);
            add(100);
            add(ModMaterials.pentlandite);
            add(100);
            add(ModMaterials.cobaltite);
            add(100);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, nickleVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        magnetiteVain = new ArrayList<Object>() {{
            add(ModMaterials.gold);
            add(50);
            add(ModMaterials.magnetite);
            add(100);
            add(ModMaterials.vanadium_magnetite);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, magnetiteVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, coalVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        saltCommonVain = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.iodinesalt);
            add(75);
            add(ModMaterials.rocksalt);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, saltCommonVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        saltRareVain = new ArrayList<Object>() {{//
            add(ModMaterials.salt);
            add(100);
            add(ModMaterials.lepidolite);
            add(75);
            add(ModMaterials.spodumene);
            add(75);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 1, 2000, saltRareVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
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
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, lapisVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
        rutileVain = new ArrayList<Object>() {{//
            add(ModMaterials.rutile);
            add(85);
            add(ModMaterials.ilmenite);
            add(100);
            add(ModMaterials.zircon);
            add(65);
        }};
        OreGenerationHandler.addOreGeneration(85, 80, 5, 1000, rutileVain, new ArrayList<EnumGenerationWorlds>() {{
            add(EnumGenerationWorlds.overworld);
        }});
    }
}
