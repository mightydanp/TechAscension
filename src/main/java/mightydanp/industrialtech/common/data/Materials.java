package mightydanp.industrialtech.common.data;

/**
 * Created by MightyDanp on 7/9/2020.
 */
import mightydanp.industrialtech.common.lib.References;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.AntimatterConfig;
import muramasa.antimatter.fluid.AntimatterMaterialFluid;
import muramasa.antimatter.material.Material;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemTier;

import static com.google.common.collect.ImmutableMap.of;
import static muramasa.antimatter.material.Element.*;
import static muramasa.antimatter.material.MaterialTag.*;
import static muramasa.antimatter.Data.*;
import static muramasa.antimatter.material.TextureSet.NONE;
import static mightydanp.industrialtech.common.data.Textures.*;
import static net.minecraft.item.ItemTier.GOLD;
import static net.minecraft.item.ItemTier.IRON;

public class Materials {

    public static Material Aluminium = new Material(References.ID, "aluminium", 0x80c8f0, DULL, Al).asMetal(933, 1700, RING, FOIL, GEAR, FRAME, ORE).addTools(1.5F, 10.0F, 140, 2);
    public static Material Beryllium = new Material(References.ID, "beryllium", 0x64b464, METALLIC, Be).asMetal(1560, 0, ORE).addTools(2.0F, 14.0F, 64, 2);
    public static Material Bismuth = new Material(References.ID, "bismuth", 0x64a0a0, METALLIC, Bi).asMetal(544, 0, ORE, ORE_SMALL);
    public static Material Carbon = new Material(References.ID, "carbon", 0x141414, DULL, C).asSolid(); //TODO: Tools, Carbon Fluid? Removed Tools
    public static Material Chrome = new Material(References.ID, "chrome", 0xffe6e6, SHINY, Cr).asMetal(2180, 1700, SCREW, BOLT, RING, PLATE, ROTOR).addTools(2.5F, 11.0F, 256, 3);
    public static Material Cobalt = new Material(References.ID, "cobalt", 0x5050fa, METALLIC, Co).asMetal(1768, 0).addTools(3.0F, 8.0F, 512, 3);
    public static Material Gold = new Material(References.ID, "gold", 0xffff1e, SHINY, Au).asMetal(1337, 0, FOIL, ROD, WIRE_FINE, GEAR, ORE, ORE_SMALL).addTools(GOLD.getAttackDamage(), GOLD.getEfficiency(), GOLD.getMaxUses(), GOLD.getHarvestLevel());
    public static Material Iridium = new Material(References.ID, "iridium", 0xf0f0f5, DULL, Ir).asMetal(2719, 2719, FRAME, ORE, ORE_SMALL).addTools(5.0F, 8.0F, 2560, 4);
    public static Material Iron = new Material(References.ID, "iron", 0xc8c8c8, METALLIC, Fe).asMetal(1811, 0, RING, GEAR, FRAME, ORE, ORE_SMALL).asPlasma().addTools(IRON.getAttackDamage(), IRON.getEfficiency(), IRON.getMaxUses(), IRON.getHarvestLevel());
    public static Material Lanthanum = new Material(References.ID, "lanthanum", 0xffffff, METALLIC, La).asSolid(1193, 1193);
    public static Material Lead = new Material(References.ID, "lead", 0x8c648c, DULL, Pb).asMetal(600, 0, PLATE, PLATE_DENSE, FOIL, ROD, ORE, ORE_SMALL);
    public static Material Manganese = new Material(References.ID, "manganese", 0xfafafa, DULL, Mn).asMetal(1519, 0, ORE);
    public static Material Molybdenum = new Material(References.ID, "molybdenum", 0xb4b4dc, SHINY, Mo).asMetal(2896, 0, ORE).addTools(2.0F, 7.0F, 512, 2);
    public static Material Neodymium = new Material(References.ID, "neodymium", 0x646464, METALLIC, Nd).asMetal(1297, 1297, PLATE, ROD, ORE); //TODO: Bastnasite or Monazite for Ore Form
    public static Material Neutronium = new Material(References.ID, "neutronium", 0xfafafa, DULL, Nt).asMetal(10000, 10000, SCREW, BOLT, RING, GEAR, FRAME).addTools(9.0F, 24.0F, 655360, 6); //TODO Vibranium
    public static Material Nickel = new Material(References.ID, "nickel", 0xc8c8fa, METALLIC, Ni).asMetal(1728, 0, ORE, ORE_SMALL).asPlasma();
    public static Material Osmium = new Material(References.ID, "osmium", 0x3232ff, METALLIC, Os).asMetal(3306, 3306, SCREW, BOLT, RING, PLATE, FOIL, ROD, WIRE_FINE).addTools(4.0F, 16.0F, 1080, 4);
    public static Material Palladium = new Material(References.ID, "palladium", 0x808080, SHINY, Pd).asMetal(1828, 1828, ORE).addTools(3.0F, 10.0F, 420, 2);
    public static Material Platinum = new Material(References.ID, "platinum", 0xffffc8, SHINY, Pt).asMetal(2041, 0, PLATE, FOIL, ROD, WIRE_FINE, ORE, ORE_SMALL).addTools(4.5F, 18.0F, 48, 2);
    public static Material Plutonium = new Material(References.ID, "plutonium_244", 0xf03232, METALLIC, Pu).asMetal(912, 0).addTools(2.5F, 6.0F, 280, 3, of(Enchantments.FIRE_ASPECT, 2)); //TODO: Enchantment: Radioactivity
    public static Material Plutonium241 = new Material(References.ID, "plutonium_241", 0xfa4646, SHINY, Pu241).asMetal(912, 0).addTools(2.5F, 6.0F, 280, 3);
    public static Material Silver = new Material(References.ID, "silver", 0xdcdcff, SHINY, Ag).asMetal(1234, 0, ORE, ORE_SMALL);
    public static Material Thorium = new Material(References.ID, "thorium", 0x001e00, SHINY, Th).asMetal(2115, 0, ORE).addTools(1.5F, 6.0F, 512, 2);
    public static Material Titanium = new Material(References.ID, "titanium", 0xdca0f0, METALLIC, Ti).asMetal(1941, 1940, ROD).addTools(2.5F, 7.0F, 1600, 3);
    public static Material Tungsten = new Material(References.ID, "tungsten", 0x323232, METALLIC, W).asMetal(3695, 3000, FOIL).addTools(2.0F, 6.0F, 512, 3); //Tungstensteel would be the one with tools
    public static Material Uranium = new Material(References.ID, "uranium_238", 0x32f032, METALLIC, U).asMetal(1405, 0, ORE);
    public static Material Uranium235 = new Material(References.ID, "uranium_235", 0x46fa46, METALLIC, U235).asMetal(1405, 0).addTools(3.0F, 6.0F, 512, 3);
    public static Material Graphite = new Material(References.ID, "graphite", 0x808080, DULL).asDust(ORE, ORE_SMALL);
    public static Material Americium = new Material(References.ID, "americium", 0xc8c8c8, METALLIC, Am).asMetal(1149, 0); //TODO: When we're thinking about fusion
    public static Material Antimony = new Material(References.ID, "antimony", 0xdcdcf0, SHINY, Sb).asMetal(1449, 0);
    public static Material Argon = new Material(References.ID, "argon", 0xff00f0, NONE, Ar).asGas();
    public static Material Arsenic = new Material(References.ID, "arsenic", 0xffffff, DULL, As).asSolid();
    public static Material Barium = new Material(References.ID, "barium", 0xffffff, METALLIC, Ba).asDust(1000);
    public static Material Boron = new Material(References.ID, "boron", 0xfafafa, DULL, B).asDust(2349);
    public static Material Caesium = new Material(References.ID, "caesium", 0xffffff, METALLIC, Cs).asMetal(2349, 0);
    public static Material Calcium = new Material(References.ID, "calcium", 0xfff5f5, METALLIC, Ca).asDust(1115);
    public static Material Cadmium = new Material(References.ID, "cadmium", 0x32323c, SHINY, Cd).asDust(594);
    public static Material Cerium = new Material(References.ID, "cerium", 0xffffff, METALLIC, Ce).asSolid(1068, 1068);
    public static Material Chlorine = new Material(References.ID, "chlorine", 0xffffff, NONE, Cr).asGas();
    public static Material Copper = new Material(References.ID, "copper", 0xff6400, SHINY, Cu).asMetal(1357, 0, PLATE, ROD, FOIL, WIRE_FINE, GEAR, ORE, ORE_SMALL);
    public static Material Deuterium = new Material(References.ID, "deuterium", 0xffff00, NONE, D).asGas();
    public static Material Dysprosium = new Material(References.ID, "dysprosium", 0xffffff, METALLIC, D).asMetal(1680, 1680);
    public static Material Europium = new Material(References.ID, "europium", 0xffffff, METALLIC, Eu).asMetal(1099, 1099);
    public static Material Fluorine = new Material(References.ID, "fluorine", 0xffffff, NONE, F).asGas();
    public static Material Gallium = new Material(References.ID, "gallium", 0xdcdcff, SHINY, Ga).asMetal(302, 0);
    public static Material Hydrogen = new Material(References.ID, "hydrogen", 0x0000ff, NONE, H).asGas();
    public static Material Helium = new Material(References.ID, "helium", 0xffff00, NONE, He).asPlasma();
    public static Material Helium3 = new Material(References.ID, "helium_3", 0xffffff, NONE, He_3).asGas();
    public static Material Indium = new Material(References.ID, "indium", 0x400080, METALLIC, In).asSolid(429, 0);
    public static Material Lithium = new Material(References.ID, "lithium", 0xe1dcff, DULL, Li).asSolid(454, 0, ORE);
    public static Material Lutetium = new Material(References.ID, "lutetium", 0xffffff, DULL, Lu).asMetal(1925, 1925);
    public static Material Magnesium = new Material(References.ID, "magnesium", 0xffc8c8, METALLIC, Mg).asMetal(923, 0);
    public static Material Mercury = new Material(References.ID, "mercury", 0xffdcdc, SHINY, Hg).asFluid();
    public static Material Niobium = new Material(References.ID, "niobium", 0xbeb4c8, METALLIC, Nb).asMetal(2750, 2750);
    public static Material Nitrogen = new Material(References.ID, "nitrogen", 0x0096c8, NONE, N).asPlasma();
    public static Material Oxygen = new Material(References.ID, "oxygen", 0x0064c8, NONE, O).asPlasma();
    public static Material Phosphor = new Material(References.ID, "phosphor", 0xffff00, DULL, P).asDust(317);
    public static Material Potassium = new Material(References.ID, "potassium", 0xfafafa, METALLIC, K).asSolid(336, 0);
    public static Material Radon = new Material(References.ID, "radon", 0xff00ff, NONE, Rn).asGas();
    public static Material Silicon = new Material(References.ID, "silicon", 0x3c3c50, METALLIC, Si).asMetal(1687, 1687, PLATE, FOIL);
    public static Material Sodium = new Material(References.ID, "sodium", 0x000096, METALLIC, Na).asDust(370);
    public static Material Sulfur = new Material(References.ID, "sulfur", 0xc8c800, DULL, S).asDust(388, ORE, ORE_SMALL).asPlasma();
    public static Material Tantalum = new Material(References.ID, "tantalum", 0xffffff, METALLIC, Ta).asSolid(3290, 0);
    public static Material Tin = new Material(References.ID, "tin", 0xdcdcdc, DULL, Sn).asMetal(505, 505, PLATE, ROD, SCREW, BOLT, RING, GEAR, FOIL, WIRE_FINE, FRAME, ORE, ORE_SMALL);
    public static Material Tritium = new Material(References.ID, "tritium", 0xff0000, METALLIC, T).asGas();
    public static Material Vanadium = new Material(References.ID, "vanadium", 0x323232, METALLIC, V).asMetal(2183, 2183);
    public static Material Yttrium = new Material(References.ID, "yttrium", 0xdcfadc, METALLIC, Y).asMetal(1799, 1799);
    public static Material Zinc = new Material(References.ID, "zinc", 0xfaf0f0, METALLIC, Zn).asMetal(692, 0, PLATE, FOIL, ORE, ORE_SMALL);

    //TODO: We can be more lenient about what fluids we have in, its not as bad as solids above, and we can stop them from showing in JEI (I think...)

    /** Gases **/
    public static Material WoodGas = new Material(References.ID, "wood_gas", 0xdecd87, NONE).asGas(24);
    public static Material Methane = new Material(References.ID, "methane", 0xffffff, NONE).asGas(104).mats(of(Carbon, 1, Hydrogen, 4));
    public static Material CarbonDioxide = new Material(References.ID, "carbon_dioxide", 0xa9d0f5, NONE).asGas().mats(of(Carbon, 1, Oxygen, 2));
    //public static Material NobleGases = new Material(References.ID, "noble_gases", 0xc9e3fc, NONE).asGas()/*.setTemp(79, 0)*/.addComposition(of(CarbonDioxide, 21, Helium, 9, Methane, 3, Deuterium, 1));
    public static Material Air = new Material(References.ID, "air", 0xc9e3fc, NONE).asGas().mats(of(Nitrogen, 40, Oxygen, 11, Argon, 1/*, NobleGases, 1*/));
    public static Material NitrogenDioxide = new Material(References.ID, "nitrogen_dioxide", 0x64afff, NONE).asGas().mats(of(Nitrogen, 1, Oxygen, 2));
    public static Material NaturalGas = new Material(References.ID, "natural_gas", 0xffffff, NONE).asGas(15);
    public static Material SulfuricGas = new Material(References.ID, "sulfuric_gas", 0xffffff, NONE).asGas(20);
    public static Material RefineryGas = new Material(References.ID, "refinery_gas", 0xffffff, NONE).asGas(128);
    public static Material LPG = new Material(References.ID, "lpg", 0xffff00, NONE).asGas(256);
    public static Material Ethane = new Material(References.ID, "ethane", 0xc8c8ff, NONE).asGas(168).mats(of(Carbon, 2, Hydrogen, 6));
    public static Material Propane = new Material(References.ID, "propane", 0xfae250, NONE).asGas(232).mats(of(Carbon, 2, Hydrogen, 6));
    public static Material Butane = new Material(References.ID, "butane", 0xb6371e, NONE).asGas(296).mats(of(Carbon, 4, Hydrogen, 10));
    public static Material Butene = new Material(References.ID, "butene", 0xcf5005, NONE).asGas(256).mats(of(Carbon, 4, Hydrogen, 8));
    public static Material Butadiene = new Material(References.ID, "butadiene", 0xe86900, NONE).asGas(206).mats(of(Carbon, 4, Hydrogen, 6));
    public static Material VinylChloride = new Material(References.ID, "vinyl_chloride", 0xfff0f0, NONE).asGas().mats(of(Carbon, 2, Hydrogen, 3, Chlorine, 1));
    public static Material SulfurDioxide = new Material(References.ID, "sulfur_dioxide", 0xc8c819, NONE).asGas().mats(of(Sulfur, 1, Oxygen, 2));
    public static Material SulfurTrioxide = new Material(References.ID, "sulfur_trioxide", 0xa0a014, NONE).asGas()/*.setTemp(344, 1)*/.mats(of(Sulfur, 1, Oxygen, 3));
    public static Material Dimethylamine = new Material(References.ID, "dimethylamine", 0x554469, NONE).asGas().mats(of(Carbon, 2, Hydrogen, 7, Nitrogen, 1));
    public static Material DinitrogenTetroxide = new Material(References.ID, "dinitrogen_tetroxide", 0x004184, NONE).asGas().mats(of(Nitrogen, 2, Oxygen, 4));
    public static Material NitricOxide = new Material(References.ID, "nitric_oxide", 0x7dc8f0, NONE).asGas().mats(of(Nitrogen, 1, Oxygen, 1));
    public static Material Ammonia = new Material(References.ID, "ammonia", 0x3f3480, NONE).asGas().mats(of(Nitrogen, 1, Hydrogen, 3));
    public static Material Chloromethane = new Material(References.ID, "chloromethane", 0xc82ca0, NONE).asGas().mats(of(Carbon, 1, Hydrogen, 3, Chlorine, 1));
    public static Material Tetrafluoroethylene = new Material(References.ID, "tetrafluoroethylene", 0x7d7d7d, NONE).asGas().mats(of(Carbon, 2, Fluorine, 4));
    public static Material CarbonMonoxide = new Material(References.ID, "carbon_monoxide", 0x0e4880, NONE).asGas(24).mats(of(Carbon, 1, Oxygen, 1));
    public static Material Ethylene = new Material(References.ID, "ethylene", 0xe1e1e1, NONE).asGas(128).mats(of(Carbon, 2, Hydrogen, 4));
    public static Material Propene = new Material(References.ID, "propene", 0xffdd55, NONE).asGas(192).mats(of(Carbon, 3, Hydrogen, 6));
    public static Material Ethenone = new Material(References.ID, "ethenone", 0x141446, NONE).asGas().mats(of(Carbon, 2, Hydrogen, 2, Oxygen, 1));
    public static Material HydricSulfide = new Material(References.ID, "hydric_sulfide", 0xffffff, NONE).asGas().mats(of(Hydrogen, 2, Sulfur, 1));

    /** Fluids **/
    public static Material Lava = new Material(References.ID, "lava", 0xff4000, NONE).asFluid();
    public static Material Water = new Material(References.ID, "water", 0x0000ff, NONE).asFluid().mats(of(Hydrogen, 2, Oxygen, 1));
    public static Material Steam = new Material(References.ID, "steam", 0xa0a0a0, NONE).asGas();
    public static Material UUAmplifier = new Material(References.ID, "uu_amplifier", 0x600080, NONE).asFluid();
    public static Material UUMatter = new Material(References.ID, "uu_matter", 0x8000c4, NONE).asFluid();
    public static Material Antimatter = new Material(References.ID, "anti_matter", 0x8000c4, NONE).asFluid();
    //public static Material CharcoalByproducts = new Material(References.ID, "charcoal_byproducts", 0x784421, NONE).asFluid(); //TODO I'll think about this and woods when I get started on pyrolysis
    public static Material Glue = new Material(References.ID, "glue", 0xc8c400, NONE).asFluid();
    public static Material Honey = new Material(References.ID, "honey", 0xd2c800, NONE).asFluid(); //TODO: Only when Forestry's present?
    public static Material Lubricant = new Material(References.ID, "lubricant", 0xffc400, NONE).asFluid();
    //public static Material WoodTar = new Material(References.ID, "wood_tar", 0x28170b, NONE).asFluid(); TODO: not sure if needed
    public static Material WoodVinegar = new Material(References.ID, "wood_vinegar", 0xd45500, NONE).asFluid();
    public static Material LiquidAir = new Material(References.ID, "liquid_air", 0xa9d0f5, NONE).asFluid()/*.setTemp(79, 0)*/.mats(of(Nitrogen, 40, Oxygen, 11, Argon, 1/*, NobleGases, 1*/)); //TODO Rrename to liquid oxygen <- Nope, add fluid to Oxygen
    public static Material DistilledWater = new Material(References.ID, "distilled_water", 0x5C5CFF, NONE).asFluid().mats(of(Hydrogen, 2, Oxygen, 1));
    public static Material Glyceryl = new Material(References.ID, "glyceryl", 0x009696, NONE).asFluid().mats(of(Carbon, 3, Hydrogen, 5, Nitrogen, 3, Oxygen, 9));
    public static Material Titaniumtetrachloride = new Material(References.ID, "titaniumtetrachloride", 0xd40d5c, NONE).asFluid().mats(of(Titanium, 1, Chlorine, 4));
    public static Material SodiumPersulfate = new Material(References.ID, "sodium_persulfate", 0xffffff, NONE).asFluid().mats(of(Sodium, 2, Sulfur, 2, Oxygen, 8));
    public static Material DilutedHydrochloricAcid = new Material(References.ID, "diluted_hydrochloric_acid", 0x99a7a3, NONE).asFluid().mats(of(Hydrogen, 1, Chlorine, 1));
    public static Material NitrationMixture = new Material(References.ID, "nitration_mixture", 0xe6e2ab, NONE).asFluid();
    public static Material Dichlorobenzene = new Material(References.ID, "dichlorobenzene", 0x004455, NONE).asFluid().mats(of(Carbon, 6, Hydrogen, 4, Chlorine, 2));
    public static Material Styrene = new Material(References.ID, "styrene", 0xd2c8be, NONE).asFluid().mats(of(Carbon, 8, Hydrogen, 8));
    public static Material Isoprene = new Material(References.ID, "isoprene", 0x141414, NONE).asFluid().mats(of(Carbon, 8, Hydrogen, 8));
    public static Material Tetranitromethane = new Material(References.ID, "tetranitromethane", 0x0f2828, NONE).asFluid().mats(of(Carbon, 1, Nitrogen, 4, Oxygen, 8));
    public static Material Epichlorohydrin = new Material(References.ID, "epichlorohydrin", 0x501d05, NONE).asFluid().mats(of(Carbon, 3, Hydrogen, 5, Chlorine, 1, Oxygen, 1));
    public static Material NitricAcid = new Material(References.ID, "nitric_acid", 0xe6e2ab, NONE).asFluid().mats(of(Hydrogen, 1, Nitrogen, 1, Oxygen, 3));
    public static Material Dimethylhydrazine = new Material(References.ID, "dimethylhydrazine", 0x000055, NONE).asFluid().mats(of(Carbon, 2, Hydrogen, 8, Nitrogen, 2));
    public static Material Chloramine = new Material(References.ID, "chloramine", 0x3f9f80, NONE).asFluid().mats(of(Nitrogen, 1, Hydrogen, 2, Chlorine, 1));
    public static Material Dimethyldichlorosilane = new Material(References.ID, "dimethyldichlorosilane", 0x441650, NONE).asFluid().mats(of(Carbon, 2, Hydrogen, 6, Chlorine, 2, Silicon, 1));
    public static Material HydrofluoricAcid = new Material(References.ID, "hydrofluoric_acid", 0x0088aa, NONE).asFluid().mats(of(Hydrogen, 1, Fluorine, 1));
    public static Material Chloroform = new Material(References.ID, "chloroform", 0x892ca0, NONE).asFluid().mats(of(Carbon, 1, Hydrogen, 1, Chlorine, 3));
    public static Material BisphenolA = new Material(References.ID, "bisphenol_a", 0xd4b300, NONE).asFluid().mats(of(Carbon, 15, Hydrogen, 16, Oxygen, 2));
    public static Material AceticAcid = new Material(References.ID, "acetic_acid", 0xc8b4a0, NONE).asFluid().mats(of(Carbon, 2, Hydrogen, 4, Oxygen, 2));
    //public static Material CalciumAcetateSolution = new Material(References.ID, "calcium_acetate_solution", 0xdcc8b4, RUBY).asFluid().addComposition(of(Calcium, 1, Carbon, 2, Oxygen, 4, Hydrogen, 6);
    public static Material Acetone = new Material(References.ID, "acetone", 0xafafaf, NONE).asFluid().mats(of(Carbon, 3, Hydrogen, 6, Oxygen, 1));
    public static Material Methanol = new Material(References.ID, "methanol", 0xaa8800, NONE).asFluid(84).mats(of(Carbon, 1, Hydrogen, 4, Oxygen, 1));
    public static Material VinylAcetate = new Material(References.ID, "vinyl_acetate", 0xffb380, NONE).asFluid().mats(of(Carbon, 4, Hydrogen, 6, Oxygen, 2));
    public static Material PolyvinylAcetate = new Material(References.ID, "polyvinyl_acetate", 0xff9955, NONE).asFluid().mats(of(Carbon, 4, Hydrogen, 6, Oxygen, 2));
    public static Material MethylAcetate = new Material(References.ID, "methyl_acetate", 0xeec6af, NONE).asFluid().mats(of(Carbon, 3, Hydrogen, 6, Oxygen, 2));
    public static Material AllylChloride = new Material(References.ID, "allyl_chloride", 0x87deaa, NONE).asFluid().mats(of(Carbon, 3, Hydrogen, 5, Chlorine, 1));
    public static Material HydrochloricAcid = new Material(References.ID, "hydrochloric_acid", 0x6f8a91, NONE).asFluid().mats(of(Hydrogen, 1, Chlorine, 1));
    public static Material HypochlorousAcid = new Material(References.ID, "hypochlorous_acid", 0x6f8a91, NONE).asFluid().mats(of(Hydrogen, 1, Chlorine, 1, Oxygen, 1));
    public static Material Cumene = new Material(References.ID, "cumene", 0x552200, NONE).asFluid().mats(of(Carbon, 9, Hydrogen, 12));
    public static Material PhosphoricAcid = new Material(References.ID, "phosphoric_acid", 0xdcdc00, NONE).asFluid().mats(of(Hydrogen, 3, Phosphor, 1, Oxygen, 4));
    public static Material SulfuricAcid = new Material(References.ID, "sulfuric_acid", 0xff8000, NONE).asFluid().mats(of(Hydrogen, 2, Sulfur, 1, Oxygen, 4));
    public static Material DilutedSulfuricAcid = new Material(References.ID, "diluted_sulfuric_acid", 0xc07820, NONE).asFluid().mats(of(SulfuricAcid, 1));
    public static Material Benzene = new Material(References.ID, "benzene", 0x1a1a1a, NONE).asFluid(288).mats(of(Carbon, 6, Hydrogen, 6));
    public static Material Phenol = new Material(References.ID, "phenol", 0x784421, NONE).asFluid(288).mats(of(Carbon, 6, Hydrogen, 6, Oxygen, 1));
    public static Material Toluene = new Material(References.ID, "toluene", 0x501d05, NONE).asFluid(328).mats(of(Carbon, 7, Hydrogen, 8));
    public static Material SulfuricNaphtha = new Material(References.ID, "sulfuric_naphtha", 0xffff00, NONE).asFluid(32);
    public static Material Naphtha = new Material(References.ID, "naphtha", 0xffff00, NONE).asFluid(256);
    public static Material DrillingFluid = new Material(References.ID, "drilling_fluid", 0xffffff, NONE).asFluid(); //TODO: Perhaps for a bedrock drill?
    public static Material BlueVitriol = new Material(References.ID, "blue_vitriol_water_solution", 0xffffff, NONE).asFluid();
    public static Material IndiumConcentrate = new Material(References.ID, "indium_concentrate", 0xffffff, NONE).asFluid();
    public static Material NickelSulfate = new Material(References.ID, "nickel_sulfate", 0xffffff, NONE).asFluid();
    public static Material RocketFuel = new Material(References.ID, "rocket_fuel", 0xffffff, NONE).asFluid();
    public static Material LeadZincSolution = new Material(References.ID, "lead_zinc_solution", 0xffffff, NONE).asFluid();

    /** Fuels **/
    public static Material Diesel = new Material(References.ID, "diesel", 0xffff00, NONE).asFluid(128);
    public static Material NitroFuel = new Material(References.ID, "cetane_boosted_diesel", 0xc8ff00, NONE).asFluid(512);
    public static Material BioDiesel = new Material(References.ID, "bio_diesel", 0xff8000, NONE).asFluid(192);
    public static Material Biomass = new Material(References.ID, "biomass", 0x00ff00, NONE).asFluid(8);
    public static Material Ethanol = new Material(References.ID, "ethanol", 0xff8000, NONE).asFluid(148).mats(of(Carbon, 2, Hydrogen, 6, Oxygen, 1));
    public static Material Creosote = new Material(References.ID, "creosote", 0x804000, NONE).asFluid(8);
    public static Material FishOil = new Material(References.ID, "fish_oil", 0xffc400, NONE).asFluid(2);
    public static Material Oil = new Material(References.ID, "oil", 0x0a0a0a, NONE).asFluid(16);
    public static Material SeedOil = new Material(References.ID, "seed_oil", 0xc4ff00, NONE).asFluid(2);
    //public static Materials SeedOilHemp = new Materials(722, "Hemp Seed Oil", 196, 255, 0, lime, NONE).asSemi(2);
    //public static Materials SeedOilLin = new Materials(723, "Lin Seed Oil", 196, 255, 0, lime, NONE).asSemi(2);
    //public static Material OilExtraHeavy = new Material(References.ID, "extra_heavy_oil", 0x0a0a0a, NONE).asFluid(40);
    public static Material OilHeavy = new Material(References.ID, "heavy_oil", 0x0a0a0a, NONE).asFluid(32);
    public static Material OilMedium = new Material(References.ID, "raw_oil", 0x0a0a0a, NONE).asFluid(24);
    public static Material OilLight = new Material(References.ID, "light_oil", 0x0a0a0a, NONE).asFluid(16);
    public static Material SulfuricLightFuel = new Material(References.ID, "sulfuric_light_diesel", 0xffff00, NONE).asFluid(32);
    public static Material SulfuricHeavyFuel = new Material(References.ID, "sulfuric_heavy_diesel", 0xffff00, NONE).asFluid(32);
    public static Material LightDiesel = new Material(References.ID, "light_diesel", 0xffff00, NONE).asFluid(256);
    public static Material HeavyDiesel = new Material(References.ID, "heavy_diesel", 0xffff00, NONE).asFluid(192);
    public static Material Glycerol = new Material(References.ID, "glycerol", 0x87de87, NONE).asFluid(164).mats(of(Carbon, 3, Hydrogen, 8, Oxygen, 3));

    /** Dusts **/
    public static Material SodiumSulfide = new Material(References.ID, "sodium_sulfide", 0xffe680, NONE).asDust().mats(of(Sodium, 2, Sulfur, 1));
    public static Material IridiumSodiumOxide = new Material(References.ID, "iridium_sodium_oxide", 0xffffff, NONE).asDust();
    public static Material PlatinumGroupSludge = new Material(References.ID, "platinum_group_sludge", 0x001e00, NONE).asDust();
    public static Material Glowstone = new Material(References.ID, "glowstone", 0xffff00, SHINY).asDust();
    public static Material Graphene = new Material(References.ID, "graphene", 0x808080, DULL).asDust();
    public static Material Oilsands = new Material(References.ID, "oilsands", 0x0a0a0a, NONE).asDust(ORE);
    public static Material RareEarth = new Material(References.ID, "rare_earth", 0x808064, FINE).asDust();
    public static Material Endstone = new Material(References.ID, "endstone", 0xffffff, DULL).asDust();
    public static Material Netherrack = new Material(References.ID, "netherrack", 0xc80000, DULL).asDust();
    public static Material Almandine = new Material(References.ID, "almandine", 0xff0000, ROUGH).asDust(ORE, ORE_SMALL).mats(of(Aluminium, 2, Iron, 3, Silicon, 3, Oxygen, 12));
    public static Material Andradite = new Material(References.ID, "andradite", 0x967800, ROUGH).asDust(ORE, ORE_SMALL).mats(of(Calcium, 3, Iron, 2, Silicon, 3, Oxygen, 12));
    public static Material Ash = new Material(References.ID, "ash", 0x969696, DULL).asDust();
    public static Material BandedIron = new Material(References.ID, "banded_iron", 0x915a5a, DULL).asDust(ORE).mats(of(Iron, 2, Oxygen, 3));
    public static Material BrownLimonite = new Material(References.ID, "brown_limonite", 0xc86400, METALLIC).asDust(ORE).mats(of(Iron, 1, Hydrogen, 1, Oxygen, 2));
    public static Material Calcite = new Material(References.ID, "calcite", 0xfae6dc, DULL).asDust(ORE).mats(of(Calcium, 1, Carbon, 1, Oxygen, 3));
    public static Material Cassiterite = new Material(References.ID, "cassiterite", 0xdcdcdc, METALLIC).asDust(ORE, ORE_SMALL).mats(of(Tin, 1, Oxygen, 2));
    public static Material Chalcopyrite = new Material(References.ID, "chalcopyrite", 0xa07828, DULL).asDust(ORE, ORE_SMALL).mats(of(Copper, 1, Iron, 1, Sulfur, 2));
    public static Material Clay = new Material(References.ID, "clay", 0xc8c8dc, ROUGH).asDust().mats(of(Sodium, 2, Lithium, 1, Aluminium, 2, Silicon, 2, Water, 6));
    public static Material Cobaltite = new Material(References.ID, "cobaltite", 0x5050fa, METALLIC).asDust(ORE).mats(of(Cobalt, 1, Arsenic, 1, Sulfur, 1));
    public static Material Cooperite = new Material(References.ID, "cooperite", 0xffffc8, METALLIC).asDust(ORE, ORE_SMALL).mats(of(Platinum, 3, Nickel, 1, Sulfur, 1, Palladium, 1));
    public static Material DarkAsh = new Material(References.ID, "dark_ash", 0x323232, DULL).asDust();
    public static Material Galena = new Material(References.ID, "galena", 0x643c64, DULL).asDust(ORE, ORE_SMALL).mats(of(Lead, 3, Silver, 3, Sulfur, 2));
    public static Material Garnierite = new Material(References.ID, "garnierite", 0x32c846, METALLIC).asDust(ORE, ORE_SMALL).mats(of(Nickel, 1, Oxygen, 1));
    public static Material Grossular = new Material(References.ID, "grossular", 0xc86400, ROUGH).asDust(ORE, ORE_SMALL).mats(of(Calcium, 3, Aluminium, 2, Silicon, 3, Oxygen, 12));
    public static Material Ilmenite = new Material(References.ID, "ilmenite", 0x463732, METALLIC).asDust(ORE).mats(of(Iron, 1, Titanium, 1, Oxygen, 3));
    public static Material Rutile = new Material(References.ID, "rutile", 0xd40d5c, GEM_H).asDust(ORE, ORE_SMALL).mats(of(Titanium, 1, Oxygen, 2));
    public static Material MagnesiumChloride = new Material(References.ID, "magnesiumchloride", 0xd40d5c, DULL).asDust().mats(of(Magnesium, 1, Chlorine, 2));
    public static Material Magnesite = new Material(References.ID, "magnesite", 0xfafab4, METALLIC).asDust(ORE).mats(of(Magnesium, 1, Carbon, 1, Oxygen, 3));
    public static Material Magnetite = new Material(References.ID, "magnetite", 0x1e1e1e, METALLIC).asDust(ORE).mats(of(Iron, 3, Oxygen, 4));
    public static Material Molybdenite = new Material(References.ID, "molybdenite", 0x91919, METALLIC).asDust(ORE).mats(of(Molybdenum, 1, Sulfur, 2));
    public static Material Obsidian = new Material(References.ID, "obsidian", 0x503264, DULL).asDust().addHandleStat(222, -0.5F, of(Enchantments.UNBREAKING, 2)).mats(of(Magnesium, 1, Iron, 1, Silicon, 2, Oxygen, 8));
    public static Material Phosphate = new Material(References.ID, "phosphate", 0xffff00, DULL).asDust(ORE).mats(of(Phosphor, 1, Oxygen, 4));
    public static Material Polydimethylsiloxane = new Material(References.ID, "polydimethylsiloxane", 0xf5f5f5, NONE).asDust().mats(of(Carbon, 2, Hydrogen, 6, Oxygen, 1, Silicon, 1));
    //public static Material Powellite = new Material(References.ID, "powellite", 0xffff00, DULL).asDust(ORE).addComposition(of(Calcium, 1, Molybdenum, 1, Oxygen, 4));
    public static Material Pyrite = new Material(References.ID, "pyrite", 0x967828, ROUGH).asDust(ORE, ORE_SMALL).mats(of(Iron, 1, Sulfur, 2));
    public static Material Pyrolusite = new Material(References.ID, "pyrolusite", 0x9696aa, DULL).asDust(ORE, ORE_SMALL).mats(of(Manganese, 1, Oxygen, 2));
    public static Material Pyrope = new Material(References.ID, "pyrope", 0x783264, METALLIC).asDust(ORE, ORE_SMALL).mats(of(Aluminium, 2, Magnesium, 3, Silicon, 3, Oxygen, 12));
    public static Material RawRubber = new Material(References.ID, "raw_rubber", 0xccc789, DULL).asDust().mats(of(Carbon, 5, Hydrogen, 8));
    public static Material Saltpeter = new Material(References.ID, "saltpeter", 0xe6e6e6, FINE).asDust(ORE, ORE_SMALL).mats(of(Potassium, 1, Nitrogen, 1, Oxygen, 3));
    public static Material Scheelite = new Material(References.ID, "scheelite", 0xc88c14, DULL).asDust(2500, ORE).mats(of(Tungsten, 1, Calcium, 2, Oxygen, 4));
    public static Material SiliconDioxide = new Material(References.ID, "silicon_dioxide", 0xc8c8c8, QUARTZ).asDust().mats(of(Silicon, 1, Oxygen, 2));
    //public static Material Pyrochlore = new Material(References.ID, "pyrochlore", 0x2b1100, METALLIC).asDust(ORE).addComposition(of(Calcium, 2, Niobium, 2, Oxygen, 7));
    public static Material FerriteMixture = new Material(References.ID, "ferrite_mixture", 0xb4b4b4, METALLIC).asDust().mats(of(Nickel, 1, Zinc, 1, Iron, 4));
    public static Material Massicot = new Material(References.ID, "massicot", 0xffdd55, DULL).asDust().mats(of(Lead, 1, Oxygen, 1));
    public static Material ArsenicTrioxide = new Material(References.ID, "arsenic_trioxide", 0xffffff, SHINY).asDust().mats(of(Arsenic, 2, Oxygen, 3));
    public static Material CobaltOxide = new Material(References.ID, "cobalt_oxide", 0x668000, DULL).asDust().mats(of(Cobalt, 1, Oxygen, 1));
    public static Material Magnesia = new Material(References.ID, "magnesia", 0xffffff, DULL).asDust().mats(of(Magnesium, 1, Oxygen, 1));
    public static Material Quicklime = new Material(References.ID, "quicklime", 0xf0f0f0, DULL).asDust().mats(of(Calcium, 1, Oxygen, 1));
    public static Material Potash = new Material(References.ID, "potash", 0x784237, DULL).asDust().mats(of(Potassium, 2, Oxygen, 1));
    public static Material SodaAsh = new Material(References.ID, "soda_ash", 0xdcdcff, DULL).asDust().mats(of(Sodium, 2, Carbon, 1, Oxygen, 3));
    public static Material Brick = new Material(References.ID, "brick", 0x9b5643, ROUGH).asDust().mats(of(Aluminium, 4, Silicon, 3, Oxygen, 12));
    public static Material Fireclay = new Material(References.ID, "fireclay", 0xada09b, ROUGH).asDust().mats(of(Brick, 1));
    public static Material SodiumBisulfate = new Material(References.ID, "sodium_bisulfate", 0x004455, NONE).asDust().mats(of(Sodium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 4));
    public static Material RawStyreneButadieneRubber = new Material(References.ID, "raw_styrene_butadiene_rubber", 0x54403d, SHINY).asDust().mats(of(Styrene, 1, Butadiene, 3));
    public static Material PhosphorousPentoxide = new Material(References.ID, "phosphorous_pentoxide", 0xdcdc00, NONE).asDust().mats(of(Phosphor, 4, Oxygen, 10));
    public static Material SodiumHydroxide = new Material(References.ID, "sodium_hydroxide", 0x003380, DULL).asDust().mats(of(Sodium, 1, Oxygen, 1, Hydrogen, 1));
    public static Material Spessartine = new Material(References.ID, "spessartine", 0xff6464, DULL).asDust(ORE, ORE_SMALL).mats(of(Aluminium, 2, Manganese, 3, Silicon, 3, Oxygen, 12));
    public static Material Sphalerite = new Material(References.ID, "sphalerite", 0xffffff, DULL).asDust(ORE, ORE_SMALL).mats(of(Zinc, 1, Sulfur, 1));
    public static Material Stibnite = new Material(References.ID, "stibnite", 0x464646, METALLIC).asDust(ORE, ORE_SMALL).mats(of(Antimony, 2, Sulfur, 3));
    public static Material Tetrahedrite = new Material(References.ID, "tetrahedrite", 0xc82000, DULL).asDust(ORE).mats(of(Copper, 3, Antimony, 1, Sulfur, 3, Iron, 1));
    public static Material Tungstate = new Material(References.ID, "tungstate", 0x373223, DULL).asDust(ORE).mats(of(Tungsten, 1, Lithium, 2, Oxygen, 4));
    public static Material Uraninite = new Material(References.ID, "uraninite", 0x232323, METALLIC).asDust(ORE).mats(of(Uranium, 1, Oxygen, 2));
    public static Material Uvarovite = new Material(References.ID, "uvarovite", 0xb4ffb4, DIAMOND).asDust(ORE, ORE_SMALL).mats(of(Calcium, 3, Chrome, 2, Silicon, 3, Oxygen, 12));
    public static Material Wood = new Material(References.ID, "wood", 0x643200, NONE).asDust(GEAR).addHandleStat(12, 0.0F).mats(of(Carbon, 1, Oxygen, 1, Hydrogen, 1));
    public static Material Stone = new Material(References.ID, "stone", 0xcdcdcd, ROUGH).asDust(DUST_IMPURE, GEAR).addHandleStat(-10, -0.5F);
    public static Material Wulfenite = new Material(References.ID, "wulfenite", 0xff8000, DULL).asDust(ORE).mats(of(Lead, 1, Molybdenum, 1, Oxygen, 4));
    public static Material YellowLimonite = new Material(References.ID, "yellow_limonite", 0xc8c800, METALLIC).asDust(ORE).mats(of(Iron, 1, Hydrogen, 1, Oxygen, 2));
    //public static Material SealedWood = new Material(References.ID, "sealed_wood", 0x502800, NONE).asDust().addTools(3.0F, 24, 0).addComposition(of(Wood, 1); TODO: Perhaps with IE integration or when we have some utility stuff
    public static Material Blaze = new Material(References.ID, "blaze", 0xffc800, NONE).asDust().addHandleStat(-10, -0.5F, of(Enchantments.FIRE_ASPECT, 1)).mats(of(Sulfur, 1, DarkAsh, 1/*, Magic, 1*/));
    public static Material Flint = new Material(References.ID, "flint", 0x002040, FLINT).asDust(ROCK).addTools(0.0F, 2.0F, 48, 1, of(Enchantments.FIRE_ASPECT, 1)).mats(of(SiliconDioxide, 1));
    public static Material PotassiumFeldspar = new Material(References.ID, "potassium_feldspar", 0x782828, FINE).asDust().mats(of(Potassium, 1, Aluminium, 1, Silicon, 3, Oxygen, 8));
    public static Material Biotite = new Material(References.ID, "biotite", 0x141e14, METALLIC).asDust().mats(b -> b.put(Potassium, 1).put(Magnesium, 3).put(Aluminium, 3).put(Fluorine, 2).put(Silicon, 3).put(Oxygen, 10));
    public static Material VanadiumMagnetite = new Material(References.ID, "vanadium_magnetite", 0x23233c, METALLIC).asDust(ORE).mats(of(Magnetite, 1, Vanadium, 1));
    public static Material Bastnasite = new Material(References.ID, "bastnasite", 0xc86e2d, FINE).asDust(ORE).mats(of(Cerium, 1, Carbon, 1, Fluorine, 1, Oxygen, 3));
    public static Material Pentlandite = new Material(References.ID, "pentlandite", 0xa59605, DULL).asDust(ORE, ORE_SMALL).mats(of(Nickel, 9, Sulfur, 8));
    public static Material Spodumene = new Material(References.ID, "spodumene", 0xbeaaaa, DULL).asDust(ORE).mats(of(Lithium, 1, Aluminium, 1, Silicon, 2, Oxygen, 6));
    public static Material Tantalite = new Material(References.ID, "tantalite", 0x915028, METALLIC).asDust(ORE).mats(of(Manganese, 1, Tantalum, 2, Oxygen, 6));
    public static Material Lepidolite = new Material(References.ID, "lepidolite", 0xf0328c, FINE).asDust(ORE).mats(of(Potassium, 1, Lithium, 3, Aluminium, 4, Fluorine, 2, Oxygen, 10)); //TODO: Ore Gen
    public static Material Glauconite = new Material(References.ID, "glauconite", 0x82b43c, DULL).asDust(ORE).mats(of(Potassium, 1, Magnesium, 2, Aluminium, 4, Hydrogen, 2, Oxygen, 12)); //TODO: Ore Gen
    public static Material Bentonite = new Material(References.ID, "bentonite", 0xf5d7d2, ROUGH).asDust(ORE).mats(b -> b.put(Sodium, 1).put(Magnesium, 6).put(Silicon, 12).put(Hydrogen, 6).put(Water, 5).put(Oxygen, 36)); //TODO: Ore Gen
    public static Material Pitchblende = new Material(References.ID, "pitchblende", 0xc8d200, DULL).asDust(ORE).mats(of(Uraninite, 3, Thorium, 1, Lead, 1));
    public static Material Malachite = new Material(References.ID, "malachite", 0x055f05, DULL).asDust(ORE).mats(of(Copper, 2, Carbon, 1, Hydrogen, 2, Oxygen, 5));
    public static Material Barite = new Material(References.ID, "barite", 0xe6ebff, DULL).asDust(ORE).mats(of(Barium, 1, Sulfur, 1, Oxygen, 4));
    public static Material Talc = new Material(References.ID, "talc", 0x5ab45a, DULL).asDust(ORE).mats(of(Magnesium, 3, Silicon, 4, Hydrogen, 2, Oxygen, 12));
    public static Material Soapstone = new Material(References.ID, "soapstone", 0x5f915f, DULL).asDust(ORE).mats(of(Magnesium, 3, Silicon, 4, Hydrogen, 2, Oxygen, 12)); //TODO: Ore Gen
    public static Material Concrete = new Material(References.ID, "concrete", 0x646464, ROUGH).asDust(300).mats(of(Stone, 1));
    public static Material AntimonyTrioxide = new Material(References.ID, "antimony_trioxide", 0xe6e6f0, DULL).asDust().mats(of(Antimony, 2, Oxygen, 3));
    public static Material CupricOxide = new Material(References.ID, "cupric_oxide", 0x0f0f0f, DULL).asDust().mats(of(Copper, 1, Oxygen, 1));
    public static Material Ferrosilite = new Material(References.ID, "ferrosilite", 0x97632a, DULL).asDust().mats(of(Iron, 1, Silicon, 1, Oxygen, 3));

    /** Gems **/
    //public static Material CertusQuartz = new Material(References.ID, "certus_quartz", 0xd2d2e6, QUARTZ).asGemBasic(false, PLATE, ORE).addTools(5.0F, 32, 1); TODO: Only when AE2 is loaded
    public static Material Dilithium = new Material(References.ID, "dilithium", 0xfffafa, DIAMOND).asGemBasic(true);
    public static Material NetherQuartz = new Material(References.ID, "nether_quartz", 0xe6d2d2, QUARTZ).asGemBasic(false, ORE, ORE_SMALL);
    public static Material NetherStar = new Material(References.ID, "nether_star", 0xffffff, NONE).asGemBasic(false).addTools(3.5F, 6.0F, 3620, 4, of(Enchantments.SILK_TOUCH, 1)); //Made Nether Stars usable

    //Brittle Gems
    public static Material BlueTopaz = new Material(References.ID, "blue_topaz", 0x0000ff, GEM_H).asGem(true, ORE_SMALL).addTools(2.5F, 7.0F, 256, 3).mats(of(Aluminium, 2, Silicon, 1, Fluorine, 2, Hydrogen, 2, Oxygen, 6));
    public static Material Charcoal = new Material(References.ID, "charcoal", 0x644646, LIGNITE).asGemBasic(false).mats(of(Carbon, 1));
    public static Material CoalCoke = new Material(References.ID, "coal_coke", 0x8c8caa, LIGNITE).asGemBasic(false);
    public static Material LigniteCoke = new Material(References.ID, "lignite_coke", 0x8c6464, LIGNITE).asGemBasic(false);

    public static Material Diamond = new Material(References.ID, "diamond", 0xc8ffff, DIAMOND).asGem(true, ORE, ORE_SMALL).addTools(ItemTier.DIAMOND.getAttackDamage(), ItemTier.DIAMOND.getEfficiency(), ItemTier.DIAMOND.getMaxUses(), ItemTier.DIAMOND.getHarvestLevel()).mats(of(Carbon, 1));
    public static Material Emerald = new Material(References.ID, "emerald", 0x50ff50, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(3.0F, 9.0F, 590, 3).mats(of(Silver, 1, Gold, 1)); //Made Emerald better
    public static Material GreenSapphire = new Material(References.ID, "green_sapphire", 0x64c882, GEM_H).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(Aluminium, 2, Oxygen, 3));
    //public static Material Lazurite = new Material(References.ID, "lazurite", 0x6478ff, LAPIS).asGemBasic(false, ORE).addComposition(of(Aluminium, 6, Silicon, 6, Calcium, 8, Sodium, 8)); //TODO I think this is needed?
    public static Material Ruby = new Material(References.ID, "ruby", 0xff6464, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(Chrome, 1, Aluminium, 2, Oxygen, 3));
    public static Material BlueSapphire = new Material(References.ID, "blue_sapphire", 0x6464c8, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(Aluminium, 2, Oxygen, 3));
    //public static Material Sodalite = new Material(References.ID, "sodalite", 0x1414ff, LAPIS).asGemBasic(false, ORE).addComposition(of(Aluminium, 3, Silicon, 3, Sodium, 4, Chlorine, 1)); //TODO I think this is needed?
    public static Material Tanzanite = new Material(References.ID, "tanzanite", 0x4000c8, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(Calcium, 2, Aluminium, 3, Silicon, 3, Hydrogen, 1, Oxygen, 13));
    public static Material Topaz = new Material(References.ID, "topaz", 0xff8000, GEM_H).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(Aluminium, 2, Silicon, 1, Fluorine, 2, Hydrogen, 2, Oxygen, 6));
    public static Material Glass = new Material(References.ID, "glass", 0xfafafa, SHINY).asDust(PLATE, LENS).mats(of(SiliconDioxide, 1));
    public static Material Olivine = new Material(References.ID, "olivine", 0x96ff96, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2, of(Enchantments.SILK_TOUCH, 1)).mats(of(Magnesium, 2, Iron, 1, SiliconDioxide, 2));
    public static Material Opal = new Material(References.ID, "opal", 0x0000ff, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(of(SiliconDioxide, 1));
    public static Material Amethyst = new Material(References.ID, "amethyst", 0xd232d2, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 3).mats(of(SiliconDioxide, 4, Iron, 1));
    public static Material Lapis = new Material(References.ID, "lapis", 0x4646dc, LAPIS).asGemBasic(false, ORE, ORE_SMALL).mats(of(/*Lazurite, 12, Sodalite, 2, */Pyrite, 1, Calcite, 1));
    public static Material EnderPearl = new Material(References.ID, "enderpearl", 0x6cdcc8, SHINY).asGemBasic(false).mats(of(Beryllium, 1, Potassium, 4, Nitrogen, 5/*, Magic, 6*/));
    public static Material EnderEye = new Material(References.ID, "endereye", 0xa0fae6, SHINY).asGemBasic(true, ROD, PLATE).mats(of(EnderPearl, 1, Blaze, 1));
    public static Material Phosphorus = new Material(References.ID, "phosphorus", 0xffff00, FLINT).asGemBasic(false, ORE).mats(of(Calcium, 3, Phosphate, 2));
    public static Material RedGarnet = new Material(References.ID, "red_garnet", 0xc85050, RUBY).asGemBasic(true, ORE, ORE_SMALL).mats(of(Pyrope, 3, Almandine, 5, Spessartine, 8));
    public static Material YellowGarnet = new Material(References.ID, "yellow_garnet", 0xc8c850, RUBY).asGemBasic(true, ORE, ORE_SMALL).mats(of(Andradite, 5, Grossular, 8, Uvarovite, 3));
    //public static Material Monazite = new Material(References.ID, "monazite", 0x324632, DIAMOND).asGemBasic(false, ORE).addComposition(of(RareEarth, 1, Phosphate, 1));

    /** **/
    public static Material Redstone = new Material(References.ID, "redstone", 0xc80000, ROUGH).asDust(ORE, ORE_SMALL, LIQUID).mats(of(Silicon, 1, Pyrite, 5, Ruby, 1, Mercury, 3));
    public static Material Cinnabar = new Material(References.ID, "cinnabar", 0x960000, ROUGH).asDust(ORE).mats(of(Mercury, 1, Sulfur, 1));

    /** Metals **/
    public static Material AnnealedCopper = new Material(References.ID, "annealed_copper", 0xff7814, SHINY).asMetal(1357, 0, PLATE, FOIL, ROD, WIRE_FINE).mats(of(Copper, 1));
    public static Material BatteryAlloy = new Material(References.ID, "battery_alloy", 0x9c7ca0, DULL).asMetal(295, 0, PLATE).mats(of(Lead, 4, Antimony, 1));
    public static Material Brass = new Material(References.ID, "brass", 0xffb400, METALLIC).asMetal(1170, 0, FRAME).mats(of(Zinc, 1, Copper, 3));
    public static Material Bronze = new Material(References.ID, "bronze", 0xff8000, METALLIC).asMetal(1125, 0, GEAR, FRAME).addTools(1.5F, 6.5F, 182, 2, of(Enchantments.UNBREAKING, 1)).mats(of(Tin, 1, Copper, 3));
    public static Material Cupronickel = new Material(References.ID, "cupronickel", 0xe39680, METALLIC).asMetal(1728, 0).mats(of(Copper, 1, Nickel, 1));
    public static Material Electrum = new Material(References.ID, "electrum", 0xffff64, SHINY).asMetal(1330, 0, PLATE, FOIL, ROD, WIRE_FINE).addTools(1.0F, 13.0F, 48, 2, of(Enchantments.UNBREAKING, 3)).mats(of(Silver, 1, Gold, 1));
    public static Material Invar = new Material(References.ID, "invar", 0xb4b478, METALLIC).asMetal(1700, 0, FRAME).addTools(2.5F, 7.0F, 320, 2, of(Enchantments.BANE_OF_ARTHROPODS, 2)).mats(of(Iron, 2, Nickel, 1));
    public static Material Kanthal = new Material(References.ID, "kanthal", 0xc2d2df, METALLIC).asMetal(1800, 1800).addTools(2.5F, 6.0F, 64, 2, of(Enchantments.BANE_OF_ARTHROPODS, 1)).mats(of(Iron, 1, Aluminium, 1, Chrome, 1));
    public static Material Magnalium = new Material(References.ID, "magnalium", 0xc8beff, DULL).asMetal(870, 0).mats(of(Magnesium, 1, Aluminium, 2));
    public static Material Nichrome = new Material(References.ID, "nichrome", 0xcdcef6, METALLIC).asMetal(2700, 2700).addTools(2.0F, 6.0F, 81, 2, of(Enchantments.BANE_OF_ARTHROPODS, 3)).mats(of(Nickel, 4, Chrome, 1));
    public static Material NiobiumTitanium = new Material(References.ID, "niobium_titanium", 0x1d1d29, DULL).asMetal(4500, 4500, PLATE, FOIL, ROD, WIRE_FINE).mats(of(Nickel, 4, Chrome, 1));
    public static Material SolderingAlloy = new Material(References.ID, "soldering_alloy", 0xdcdce6, DULL).asMetal(400, 400, PLATE, FOIL, ROD, WIRE_FINE).mats(of(Tin, 9, Antimony, 1));
    public static Material Steel = new Material(References.ID, "steel", 0x808080, METALLIC).asMetal(1811, 1000, GEAR, PLATE, FOIL, SCREW, BOLT, ROD, RING, FRAME).addTools(Iron).mats(of(Iron, 50, Carbon, 1));
    public static Material StainlessSteel = new Material(References.ID, "stainless_steel", 0xc8c8dc, SHINY).asMetal(1700, 1700, SCREW, BOLT, GEAR, FRAME).addTools(Steel).mats(of(Iron, 6, Chrome, 1, Manganese, 1, Nickel, 1));
    public static Material Ultimet = new Material(References.ID, "ultimet", 0xb4b4e6, SHINY).asMetal(2700, 2700).mats(of(Cobalt, 5, Chrome, 2, Nickel, 1, Molybdenum, 1));
    public static Material VanadiumGallium = new Material(References.ID, "vanadium_gallium", 0x80808c, SHINY).asMetal(4500, 4500, ROD).mats(of(Vanadium, 3, Gallium, 1));
    public static Material WroughtIron = new Material(References.ID, "wrought_iron", 0xc8b4b4, METALLIC).asMetal(1811, 0, RING, FRAME).addTools(Iron).mats(of(Iron, 1));
    public static Material YttriumBariumCuprate = new Material(References.ID, "yttrium_barium_cuprate", 0x504046, METALLIC).asMetal(4500, 4500, PLATE, FOIL, ROD, WIRE_FINE).mats(of(Yttrium, 1, Barium, 2, Copper, 3, Oxygen, 7));
    public static Material SterlingSilver = new Material(References.ID, "sterling_silver", 0xfadce1, SHINY).asMetal(1700, 1700).addTools(3.0F, 10.5F, 96, 2, of(Enchantments.EFFICIENCY, 2)).mats(of(Copper, 1, Silver, 4));
    public static Material RoseGold = new Material(References.ID, "rose_gold", 0xffe61e, SHINY).asMetal(1600, 1600).addTools(Gold, of(Enchantments.FORTUNE, 3, Enchantments.SMITE, 3)).mats(of(Copper, 1, Gold, 4));
    public static Material BlackBronze = new Material(References.ID, "black_bronze", 0x64327d, DULL).asMetal(2000, 2000).addTools(Bronze, of(Enchantments.SWEEPING, 1)).mats(of(Gold, 1, Silver, 1, Copper, 3));
    public static Material BismuthBronze = new Material(References.ID, "bismuth_bronze", 0x647d7d, DULL).asMetal(1100, 900, PLATE).addTools(2.5F, Bronze.getToolSpeed() + 2.0F, 350, 2, of(Enchantments.BANE_OF_ARTHROPODS, 4)).mats(of(Bismuth, 1, Zinc, 1, Copper, 3));
    public static Material BlackSteel = new Material(References.ID, "black_steel", 0x646464, METALLIC).asMetal(1200, 1200, FRAME, PLATE).addTools(3.5F, 6.5F, 768, 2).mats(of(Nickel, 1, BlackBronze, 1, Steel, 3));
    public static Material RedSteel = new Material(References.ID, "red_steel", 0x8c6464, METALLIC).asMetal(1300, 1300).addTools(3.5F, 7.0F, 896, 2).mats(of(SterlingSilver, 1, BismuthBronze, 1, Steel, 2, BlackSteel, 4));
    public static Material BlueSteel = new Material(References.ID, "blue_steel", 0x64648c, METALLIC).asMetal(1400, 1400, FRAME).addTools(3.5F, 7.5F, 1024, 2).mats(of(RoseGold, 1, Brass, 1, Steel, 2, BlackSteel, 4));
    //public static Material DamascusSteel = new Material(References.ID, "damascus_steel", 0x6e6e6e, METALLIC).asMetal(2500, 1500).addTools(8.0F, 1280, 2).addComposition(of(Steel, 1)); //TODO: Sorta a fantasy metal
    public static Material TungstenSteel = new Material(References.ID, "tungstensteel", 0x6464a0, METALLIC).asMetal(3000, 3000, SCREW, BOLT, GEAR, ROD, RING, FRAME).addTools(4.0F, 8.0F, 2560, 4).mats(of(Steel, 1, Tungsten, 1));
    public static Material RedAlloy = new Material(References.ID, "red_alloy", 0xc80000, DULL).asMetal(295, 0, PLATE, FOIL, ROD, WIRE_FINE).mats(of(Copper, 1, Redstone, 4));
    public static Material CobaltBrass = new Material(References.ID, "cobalt_brass", 0xb4b4a0, METALLIC).asMetal(1500, 0, GEAR).addTools(2.5F, 8.0F, 256, 2).mats(of(Brass, 7, Aluminium, 1, Cobalt, 1));
    public static Material IronMagnetic = new Material(References.ID, "magnetic_iron", 0xc8c8c8, MAGNETIC).asMetal(1811, 0).addTools(Iron).mats(of(Iron, 1));
    public static Material SteelMagnetic = new Material(References.ID, "magnetic_steel", 0x808080, MAGNETIC).asMetal(1000, 1000).addTools(Steel).mats(of(Steel, 1));
    public static Material NeodymiumMagnetic = new Material(References.ID, "magnetic_neodymium", 0x646464, MAGNETIC).asMetal(1297, 1297).mats(of(Neodymium, 1));
    public static Material NickelZincFerrite = new Material(References.ID, "nickel_zinc_ferrite", 0x3c3c3c, ROUGH).asMetal(1500, 1500).addTools(0.0F, 3.0F, 32, 1).mats(of(Nickel, 1, Zinc, 1, Iron, 4, Oxygen, 8));
    public static Material TungstenCarbide = new Material(References.ID, "tungsten_carbide", 0x330066, METALLIC).asMetal(2460, 2460).addTools(5.0F, 14.0F, 1280, 4).mats(of(Tungsten, 1, Carbon, 1));
    public static Material VanadiumSteel = new Material(References.ID, "vanadium_steel", 0xc0c0c0, METALLIC).asMetal(1453, 1453).addTools(3.0F, 5.0F, 1920, 3).mats(of(Vanadium, 1, Chrome, 1, Steel, 7));
    public static Material HSSG = new Material(References.ID, "hssg", 0x999900, METALLIC).asMetal(4500, 4500, GEAR, FRAME).addTools(3.8F, 10.0F, 4000, 3).mats(of(TungstenSteel, 5, Chrome, 1, Molybdenum, 2, Vanadium, 1));
    public static Material HSSE = new Material(References.ID, "hsse", 0x336600, METALLIC).asMetal(5400, 5400, GEAR, FRAME).addTools(4.2F, 10.0F, 5120, 4).mats(of(HSSG, 6, Cobalt, 1, Manganese, 1, Silicon, 1));
    public static Material HSSS = new Material(References.ID, "hsss", 0x660033, METALLIC).asMetal(5400, 5400).addTools(5.0F, 14.0F, 3000, 4).mats(of(HSSG, 6, Iridium, 2, Osmium, 1));
    public static Material Osmiridium = new Material(References.ID, "osmiridium", 0x6464ff, METALLIC).asMetal(3333, 2500, FRAME).addTools(6.0F, 15.0F, 1940, 5).mats(of(Iridium, 3, Osmium, 1));
    public static Material Duranium = new Material(References.ID, "duranium", 0xffffff, METALLIC).asMetal(295, 0).addHandleStat(620, -1.0F, of(Enchantments.SILK_TOUCH, 1)).addTools(6.5F, 16.0F, 5120, 5);
    public static Material Naquadah = new Material(References.ID, "naquadah", 0x323232, METALLIC).asMetal(5400, 5400, ORE).addHandleStat(102, 0.5F, of(Enchantments.EFFICIENCY, 2)).addTools(4.0F, 6.0F, 890, 4);
    public static Material NaquadahAlloy = new Material(References.ID, "naquadah_alloy", 0x282828, METALLIC).asMetal(7200, 7200).addTools(4.5F, 8.0F, 5120, 5);
    public static Material EnrichedNaquadah = new Material(References.ID, "enriched_naquadah", 0x323232, SHINY).asMetal(4500, 4500, ORE).addTools(5.0F, 6.0F, 1280, 4);
    public static Material Naquadria = new Material(References.ID, "naquadria", 0x1e1e1e, SHINY).asMetal(9000, 9000);
    public static Material Tritanium = new Material(References.ID, "tritanium", 0xffffff, SHINY).asMetal(295, 0, FRAME).addTools(9.0F, 15.0F, 9400, 6);
    public static Material Vibranium = new Material(References.ID, "vibranium", 0x00ffff, SHINY).asMetal(295, 0, FRAME).addTools(10.0F, 20.0F, 12240, 6);

    /** Solids (Plastic Related Stuff)**/
    public static Material Plastic = new Material(References.ID, "plastic", 0xc8c8c8, DULL).asSolid(295, 0, PLATE).addHandleStat(66, 0.5F).mats(of(Carbon, 1, Hydrogen, 2));
    public static Material Epoxid = new Material(References.ID, "epoxid", 0xc88c14, DULL).asSolid(400, 0, PLATE).addHandleStat(70, 1.5F).mats(of(Carbon, 2, Hydrogen, 4, Oxygen, 1));
    public static Material Silicone = new Material(References.ID, "silicone", 0xdcdcdc, DULL).asSolid(900, 0, PLATE, FOIL).addHandleStat(-40, 2.0F).mats(of(Carbon, 2, Hydrogen, 6, Oxygen, 1, Silicon, 1));
    public static Material Polycaprolactam = new Material(References.ID, "polycaprolactam", 0x323232, DULL).asSolid(500, 0).mats(of(Carbon, 6, Hydrogen, 11, Nitrogen, 1, Oxygen, 1));
    public static Material Polytetrafluoroethylene = new Material(References.ID, "polytetrafluoroethylene", 0x646464, DULL).asSolid(1400, 0, PLATE, FRAME).mats(of(Carbon, 2, Fluorine, 4));
    public static Material Rubber = new Material(References.ID, "rubber", 0x000000, SHINY).asSolid(295, 0, PLATE, RING).addHandleStat(11, 0.4F).mats(of(Carbon, 5, Hydrogen, 8));
    public static Material PolyphenyleneSulfide = new Material(References.ID, "polyphenylene_sulfide", 0xaa8800, DULL).asSolid(295, 0, PLATE, FOIL).mats(of(Carbon, 6, Hydrogen, 4, Sulfur, 1));
    public static Material Polystyrene = new Material(References.ID, "polystyrene", 0xbeb4aa, DULL).asSolid(295, 0).addHandleStat(3, 1.0F).mats(of(Carbon, 8, Hydrogen, 8));
    public static Material StyreneButadieneRubber = new Material(References.ID, "styrene_butadiene_rubber", 0x211a18, SHINY).asSolid(295, 0, PLATE, RING).addHandleStat(66, 1.2F).mats(of(Styrene, 1, Butadiene, 3));
    public static Material PolyvinylChloride = new Material(References.ID, "polyvinyl_chloride", 0xd7e6e6, NONE).asSolid(295, 0, PLATE, FOIL).addHandleStat(210, 0.5F).mats(of(Carbon, 2, Hydrogen, 3, Chlorine, 1));
    public static Material GalliumArsenide = new Material(References.ID, "gallium_arsenide", 0xa0a0a0, DULL).asSolid(295, 1200).mats(of(Arsenic, 1, Gallium, 1));
    public static Material EpoxidFiberReinforced = new Material(References.ID, "fiber_reinforced_epoxy_resin", 0xa07010, DULL).asSolid(400, 0).mats(of(Epoxid, 1));

    /** Stones **/
    public static Material Granite = new Material(References.ID, "granite", 0xa07882, ROUGH).asDust(ROCK);
    public static Material Diorite = new Material(References.ID, "diorite", 0xf0f0f0, ROUGH).asDust(ROCK);
    public static Material Andesite = new Material(References.ID, "andesite", 0xbfbfbf, ROUGH).asDust(ROCK);

    public static Material Gravel = new Material(References.ID, "gravel", 0xcdcdcd, ROUGH).asDust(ROCK);
    public static Material Sand = new Material(References.ID, "sand", 0xfafac8, ROUGH).asDust(ROCK);
    public static Material RedSand = new Material(References.ID, "red_sand", 0xff8438, ROUGH).asDust(ROCK);
    public static Material Sandstone = new Material(References.ID, "sandstone", 0xfafac8, ROUGH).asDust(ROCK);

    public static Material RedGranite = new Material(References.ID, "red_granite", 0xff0080, ROUGH).asDust(ROCK).addHandleStat(74, 1.0F, of(Enchantments.UNBREAKING, 1)).mats(of(Aluminium, 2, PotassiumFeldspar, 1, Oxygen, 3));
    public static Material BlackGranite = new Material(References.ID, "black_granite", 0x0a0a0a, ROUGH).asDust(ROCK).addHandleStat(74, 1.0F, of(Enchantments.UNBREAKING, 1)).mats(of(SiliconDioxide, 4, Biotite, 1));
    public static Material Marble = new Material(References.ID, "marble", 0xc8c8c8, NONE).asDust(ROCK).mats(of(Magnesium, 1, Calcite, 7));
    public static Material Basalt = new Material(References.ID, "basalt", 0x1e1414, ROUGH).asDust(ROCK).mats(of(Olivine, 1, Calcite, 3, Flint, 8, DarkAsh, 4));
    public static Material Komatiite = new Material(References.ID, "komatiite", 0xbebe69, NONE).asDust(ROCK).mats(of(Olivine, 1, /*MgCO3, 2, */Flint, 6, DarkAsh, 3));
    public static Material Limestone = new Material(References.ID, "limestone", 0xe6c882, NONE).asDust(ROCK).mats(of(Calcite, 1));
    public static Material GreenSchist = new Material(References.ID, "green_schist", 0x69be69, NONE).asDust(ROCK);
    public static Material BlueSchist = new Material(References.ID, "blue_schist", 0x0569be, NONE).asDust(ROCK);
    public static Material Kimberlite = new Material(References.ID, "kimberlite", 0x64460a, NONE).asDust(ROCK);
    public static Material Quartzite = new Material(References.ID, "quartzite", 0xe6cdcd, QUARTZ).asGemBasic(false, ORE, ROCK).mats(of(Silicon, 1, Oxygen, 2));

    /** Ore Stones **/
    public static Material Coal = new Material(References.ID, "coal", 0x464646, LIGNITE).asGemBasic(false, ORE, ORE_STONE, ORE_SMALL).mats(of(Carbon, 1));
    public static Material Lignite = new Material(References.ID, "lignite_coal", 0x644646, LIGNITE).asGemBasic(false, ORE_STONE).mats(of(Carbon, 3, Water, 1));
    public static Material Salt = new Material(References.ID, "salt", 0xfafafa, FINE).asDust(ORE_STONE, ORE_SMALL).mats(of(Sodium, 1, Chlorine, 1));
    public static Material RockSalt = new Material(References.ID, "rock_salt", 0xf0c8c8, FINE).asDust(ORE_STONE, ORE_SMALL).mats(of(Potassium, 1, Chlorine, 1));
    public static Material Bauxite = new Material(References.ID, "bauxite", 0xc86400, DULL).asDust(ORE_STONE).mats(of(Rutile, 2, Aluminium, 16, Hydrogen, 10, Oxygen, 11));
    public static Material OilShale = new Material(References.ID, "oil_shale", 0x32323c, NONE).asDust(ORE_STONE);


    /** Reference Materials **/
    public static Material Superconductor = new Material(References.ID, "superconductor", 0xffffff, NONE);
    public static Material HighPressure = new Material(References.ID, "high_pressure", 0xc80000, NONE);
    public static Material HighCapacity = new Material(References.ID, "high_capacity", 0xb00b69, NONE);
    public static Material PlasmaContainment = new Material(References.ID, "plasma_containment", 0xffff00, NONE);

    static {
        ELECSEPI.add(Bastnasite/*, Monazite*/);
        ELECSEPG.add(Magnetite, VanadiumMagnetite);
        ELECSEPN.add(YellowLimonite, BrownLimonite, Pyrite, BandedIron, Nickel, Glauconite, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Andradite);
        ELEC.add(Methane, CarbonDioxide, NitrogenDioxide, Toluene, VinylChloride, SulfurDioxide, SulfurTrioxide, Dimethylamine, DinitrogenTetroxide, NitricOxide, Ammonia, Chloromethane, Tetrafluoroethylene, CarbonMonoxide, Ethylene, Propane, Ethenone, Ethanol, Glyceryl, SodiumPersulfate, Dichlorobenzene, Styrene, Isoprene, Tetranitromethane, Epichlorohydrin, NitricAcid, Dimethylhydrazine, Chloramine, Dimethyldichlorosilane, HydrofluoricAcid, Chloroform, BisphenolA, AceticAcid, Acetone, Methanol, VinylAcetate, MethylAcetate, AllylChloride, HypochlorousAcid, Cumene, PhosphoricAcid, SulfuricAcid, Benzene, Phenol, Glycerol, SodiumSulfide, Almandine, Andradite, BandedIron, Calcite, Cassiterite, Chalcopyrite, Cobaltite, Galena, Garnierite, Grossular, Bauxite, Magnesite, Magnetite, Molybdenite, Obsidian, Phosphate, Polydimethylsiloxane, Pyrite, Pyrolusite, Pyrope, RockSalt, Saltpeter, SiliconDioxide, Massicot, ArsenicTrioxide, CobaltOxide, Magnesia, Quicklime, Potash, SodaAsh, PhosphorousPentoxide, SodiumHydroxide, Spessartine, Sphalerite, Uvarovite, PotassiumFeldspar, Biotite, RedGranite, Bastnasite, Pentlandite, Spodumene, Glauconite, Bentonite, Malachite, Barite, Talc, AntimonyTrioxide, CupricOxide, Ferrosilite, Quartzite, Charcoal, Coal, Lignite, Diamond, Emerald, Ruby, BlueSapphire, Tanzanite, Topaz, Olivine, Opal, Amethyst, EnderPearl, StainlessSteel, Steel, Ultimet, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, Osmiridium);
        CENT.add(/*NobleGases, */Air, BrownLimonite, Cinnabar, Clay, Cooperite/*, Powellite*/, Stibnite, Tetrahedrite, Uraninite, Wulfenite, YellowLimonite, Blaze, Flint, Marble, BlackGranite, VanadiumMagnetite, Pitchblende, Glass, Lapis, EnderEye, Phosphorus, Redstone, Basalt, AnnealedCopper, BatteryAlloy, Brass, Bronze, Cupronickel, Electrum, Invar, Kanthal, Magnalium, Nichrome, NiobiumTitanium, SolderingAlloy, VanadiumGallium, WroughtIron, SterlingSilver, RoseGold, BismuthBronze, TungstenSteel, RedAlloy, CobaltBrass, TungstenCarbide, VanadiumSteel, HSSG, HSSE, HSSS, GalliumArsenide/*, IndiumGalliumPhosphide, BorosilicateGlass*/);
        CRACK.add(RefineryGas, Naphtha, Ethane, Propane, Butane, Butene, Ethylene, Propene, LightDiesel, HeavyDiesel);
        CALCITE2X.add(Pyrite, BrownLimonite, YellowLimonite, Magnetite);
        CALCITE3X.add(Iron, WroughtIron);
        WASHM.add(Gold, Silver, Osmium, Platinum, Cooperite, Galena, Nickel, Tungstate, Lead, Magnetite, Iridium, Copper, Chalcopyrite);
        WASHS.add(Zinc, Nickel, Copper, Cobaltite, Tetrahedrite, Gold, Sphalerite, Garnierite, Chalcopyrite, Cooperite, Platinum, Pentlandite, Tin, Malachite, YellowLimonite);
        NOSMELT.add(Wood/*, WoodSealed*/,Sulfur, Saltpeter, Graphite, /*Paper, */Coal, Charcoal, Lignite, Glyceryl, NitroFuel, Emerald, Amethyst, Tanzanite, Topaz, /*Amber,*/ BlueSapphire, Ruby, Opal, Olivine, Lapis/*, Sodalite, Lazurite, Monazite*/, Quartzite, NetherQuartz, Phosphorus, Phosphate, NetherStar, EnderPearl, EnderEye, Blaze);
        NOSMASH.add(Wood/* WoodSealed*/,Sulfur, Saltpeter, Graphite, /*Paper, */Coal, Charcoal, Lignite, Rubber, StyreneButadieneRubber, Plastic, PolyvinylChloride, Polystyrene, Silicone, Glyceryl, NitroFuel, Concrete, Redstone, Glowstone, Netherrack, Stone, Brick, Endstone, Marble, Basalt, Obsidian, Flint, RedGranite, BlackGranite, Salt, RockSalt, Glass, Diamond, Emerald, Amethyst, Tanzanite, Topaz, /*Amber,*/ BlueSapphire, Ruby, Opal, Olivine, Lapis, Quartzite, NetherQuartz, Phosphorus, Phosphate, NetherStar, EnderPearl, EnderEye);
        GRINDABLE.add(/*Paper, */Coal, Charcoal, Lignite, Lead, Tin, SolderingAlloy, Flint, Gold, Silver, Iron, IronMagnetic, Steel, SteelMagnetic, Zinc, Antimony, Copper, AnnealedCopper, Bronze, Nickel, Invar, Brass, WroughtIron, Electrum, Clay, Blaze);
        SMELTF.add(Concrete, Redstone, Glowstone, Glass, Blaze);
        //TODO explicit recipe SMELTG.add(Mercury, CINNABAR); //TODO Remove
        NOBBF.add(Tetrahedrite, Chalcopyrite, Cooperite, Pyrolusite, Magnesite, Molybdenite, Galena);
        CRYSTALLIZE.add(Lapis, Quartzite, NetherQuartz);
        BRITTLEG.add(Coal, Charcoal, Lignite);
        RUBBERTOOLS.add(Rubber, StyreneButadieneRubber, Plastic, PolyvinylChloride, Polystyrene, Silicone);
        SOLDER.add(Lead, Tin, SolderingAlloy);
        //TODO Mercury.add(METALL, SMELTG);

        NeodymiumMagnetic.setSmeltInto(Neodymium).setMacerateInto(Neodymium).setArcSmeltInto(Neodymium);
        SteelMagnetic.setSmeltInto(Steel).setMacerateInto(Steel).setArcSmeltInto(Steel);
        Iron.setSmeltInto(Iron).setMacerateInto(Iron).setArcSmeltInto(WroughtIron);
        WroughtIron.setSmeltInto(Iron).setMacerateInto(Iron).setArcSmeltInto(WroughtIron);
        IronMagnetic.setSmeltInto(Iron).setMacerateInto(Iron).setArcSmeltInto(WroughtIron);
        Copper.setSmeltInto(Copper).setMacerateInto(Copper).setArcSmeltInto(AnnealedCopper);
        AnnealedCopper.setSmeltInto(Copper).setMacerateInto(Copper).setArcSmeltInto(AnnealedCopper);

        Cinnabar.setDirectSmeltInto(Mercury);
        Tetrahedrite.setDirectSmeltInto(Copper);
        Chalcopyrite.setDirectSmeltInto(Copper);
        Malachite.setDirectSmeltInto(Copper);
        Pentlandite.setDirectSmeltInto(Nickel);
        Sphalerite.setDirectSmeltInto(Zinc);
        Pyrite.setDirectSmeltInto(Iron);
        YellowLimonite.setDirectSmeltInto(Iron);
        BrownLimonite.setDirectSmeltInto(Iron);
        BandedIron.setDirectSmeltInto(Iron);
        Magnetite.setDirectSmeltInto(Iron);
        Cassiterite.setDirectSmeltInto(Tin);
        Garnierite.setDirectSmeltInto(Nickel);
        Cobaltite.setDirectSmeltInto(Cobalt);
        Stibnite.setDirectSmeltInto(Antimony);
        Cooperite.setDirectSmeltInto(Platinum);
        Pyrolusite.setDirectSmeltInto(Manganese);
        Magnesite.setDirectSmeltInto(Magnesium);
        Molybdenite.setDirectSmeltInto(Molybdenum);
        Galena.setDirectSmeltInto(Lead);
        Salt.setOreMulti(2).setSmeltingMulti(2);
        RockSalt.setOreMulti(2).setSmeltingMulti(2);
        Scheelite.setOreMulti(2).setSmeltingMulti(2);
        Tungstate.setOreMulti(2).setSmeltingMulti(2);
        Cassiterite.setOreMulti(2).setSmeltingMulti(2);
        NetherQuartz.setOreMulti(2).setSmeltingMulti(2);
        Phosphorus.setOreMulti(3).setSmeltingMulti(3);
        Saltpeter.setOreMulti(4).setSmeltingMulti(4);
        Redstone.setOreMulti(5).setSmeltingMulti(5);
        Glowstone.setOreMulti(5).setSmeltingMulti(5);
        Lapis.setOreMulti(6).setSmeltingMulti(6).setByProductMulti(4);
//        Plastic.setEnchantmentForTools(Enchantment.knockback, 1);
//        PolyvinylChloride.setEnchantmentForTools(Enchantment.knockback, 1);
//        Polystyrene.setEnchantmentForTools(Enchantment.knockback, 1);
//        Rubber.setEnchantmentForTools(Enchantment.knockback, 2);
//        StyreneButadieneRubber.setEnchantmentForTools(Enchantment.knockback, 2);
//        Flint.setEnchantmentForTools(Enchantment.fireAspect, 1);
//        Blaze.setEnchantmentForTools(Enchantment.fireAspect, 3);
//        EnderPearl.setEnchantmentForTools(Enchantment.silkTouch, 1);
//        NetherStar.setEnchantmentForTools(Enchantment.silkTouch, 1);
//        BlackBronze.setEnchantmentForTools(Enchantment.smite, 2);
//        Gold.setEnchantmentForTools(Enchantment.smite, 3);
//        RoseGold.setEnchantmentForTools(Enchantment.smite, 4);
//        Platinum.setEnchantmentForTools(Enchantment.smite, 5);
//        Lead.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
//        Nickel.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
//        Invar.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
//        Antimony.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
//        BatteryAlloy.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
//        Bismuth.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
//        BismuthBronze.setEnchantmentForTools(Enchantment.baneOfArthropods, 5);
//        Iron.setEnchantmentForTools(Enchantment.sharpness, 1);
//        Bronze.setEnchantmentForTools(Enchantment.sharpness, 1);
//        Brass.setEnchantmentForTools(Enchantment.sharpness, 2);
//        Steel.setEnchantmentForTools(Enchantment.sharpness, 2);
//        WroughtIron.setEnchantmentForTools(Enchantment.sharpness, 2);
//        StainlessSteel.setEnchantmentForTools(Enchantment.sharpness, 3);
//        BlackSteel.setEnchantmentForTools(Enchantment.sharpness, 4);
//        RedSteel.setEnchantmentForTools(Enchantment.sharpness, 4);
//        BlueSteel.setEnchantmentForTools(Enchantment.sharpness, 5);
//        DamascusSteel.setEnchantmentForTools(Enchantment.sharpness, 5);
//        TungstenCarbide.setEnchantmentForTools(Enchantment.sharpness, 5);
//        HSSE.setEnchantmentForTools(Enchantment.sharpness, 5);
//        HSSG.setEnchantmentForTools(Enchantment.sharpness, 4);
//        HSSS.setEnchantmentForTools(Enchantment.sharpness, 5);
//        Lava.setTemperatureDamage(3.0F);
        Chalcopyrite.addByProduct(Pyrite, Cobalt, Cadmium, Gold);
        Sphalerite.addByProduct(YellowGarnet, Cadmium, Gallium, Zinc);
        Glauconite.addByProduct(Sodium, Aluminium, Iron);
        Bentonite.addByProduct(Aluminium, Calcium, Magnesium);
        Uraninite.addByProduct(Uranium, Thorium, Uranium235);
        Pitchblende.addByProduct(Thorium, Uranium, Lead);
        Galena.addByProduct(Sulfur, Silver, Lead);
        Lapis.addByProduct(Calcite, Pyrite);
        Pyrite.addByProduct(Sulfur, Phosphorus, Iron);
        Copper.addByProduct(Cobalt, Gold, Nickel);
        Nickel.addByProduct(Cobalt, Platinum, Iron);
        RedGarnet.addByProduct(Spessartine, Pyrope, Almandine);
        YellowGarnet.addByProduct(Andradite, Grossular, Uvarovite);
        Cooperite.addByProduct(Palladium, Nickel, Iridium);
        Cinnabar.addByProduct(Redstone, Sulfur, Glowstone);
        Tantalite.addByProduct(Manganese, Niobium, Tantalum);
        Pentlandite.addByProduct(Iron, Sulfur, Cobalt);
        Uranium.addByProduct(Lead, Uranium235, Thorium);
        Scheelite.addByProduct(Manganese, Molybdenum, Calcium);
        Tungstate.addByProduct(Manganese, Silver, Lithium);
        Bauxite.addByProduct(Grossular, Rutile, Gallium);
        Redstone.addByProduct(Cinnabar, RareEarth, Glowstone);
        Malachite.addByProduct(Copper, BrownLimonite, Calcite);
        YellowLimonite.addByProduct(Nickel, BrownLimonite, Cobalt);
        Andradite.addByProduct(YellowGarnet, Iron, Boron);
        Quartzite.addByProduct(Barite);
        BrownLimonite.addByProduct(Malachite, YellowLimonite);
        Neodymium.addByProduct(RareEarth);
        Bastnasite.addByProduct(Neodymium, RareEarth);
        Glowstone.addByProduct(Redstone, Gold);
        Zinc.addByProduct(Tin, Gallium);
        Tungsten.addByProduct(Manganese, Molybdenum);
        Iron.addByProduct(Nickel, Tin);
        Gold.addByProduct(Copper, Nickel);
        Tin.addByProduct(Iron, Zinc);
        Antimony.addByProduct(Zinc, Iron);
        Silver.addByProduct(Lead, Sulfur);
        Lead.addByProduct(Silver, Sulfur);
        Thorium.addByProduct(Uranium, Lead);
        Plutonium.addByProduct(Uranium, Lead);
        Electrum.addByProduct(Gold, Silver);
        Bronze.addByProduct(Copper, Tin);
        Brass.addByProduct(Copper, Zinc);
        Coal.addByProduct(Lignite, Thorium);
        Ilmenite.addByProduct(Iron, Rutile);
        Manganese.addByProduct(Chrome, Iron);
        BlueSapphire.addByProduct(Aluminium);
        Platinum.addByProduct(Nickel, Iridium);
        Emerald.addByProduct(Beryllium, Aluminium);
        Olivine.addByProduct(Pyrope, Magnesium);
        Chrome.addByProduct(Iron, Magnesium);
        Tetrahedrite.addByProduct(Antimony, Zinc);
        Magnetite.addByProduct(Iron, Gold);
        Basalt.addByProduct(Olivine, DarkAsh);
        VanadiumMagnetite.addByProduct(Magnetite, Vanadium);
        Spodumene.addByProduct(Aluminium, Lithium);
        Ruby.addByProduct(Chrome, RedGarnet);
        Phosphorus.addByProduct(Phosphate);
        Iridium.addByProduct(Platinum, Osmium);
        Pyrope.addByProduct(RedGarnet, Magnesium);
        Almandine.addByProduct(RedGarnet, Aluminium);
        Spessartine.addByProduct(RedGarnet, Manganese);
        Grossular.addByProduct(YellowGarnet, Calcium);
        Uvarovite.addByProduct(YellowGarnet, Chrome);
        Calcite.addByProduct(Andradite, Malachite);
        EnrichedNaquadah.addByProduct(Naquadah, Naquadria);
        Naquadah.addByProduct(EnrichedNaquadah);
        Pyrolusite.addByProduct(Manganese);
        Molybdenite.addByProduct(Molybdenum);
        Stibnite.addByProduct(Antimony);
        Garnierite.addByProduct(Nickel);
        Lignite.addByProduct(Coal);
        Diamond.addByProduct(Graphite);
        Beryllium.addByProduct(Emerald);
        Magnesite.addByProduct(Magnesium);
        NetherQuartz.addByProduct(Netherrack);
        Steel.addByProduct(Iron);
        Graphite.addByProduct(Carbon);
        Netherrack.addByProduct(Sulfur);
        Flint.addByProduct(Obsidian);
        Cobaltite.addByProduct(Cobalt);
        Cobalt.addByProduct(Cobaltite);
        Sulfur.addByProduct(Sulfur);
        Saltpeter.addByProduct(Saltpeter);
        Endstone.addByProduct(Helium3);
        Osmium.addByProduct(Iridium);
        Magnesium.addByProduct(Olivine);
        Aluminium.addByProduct(Bauxite);
        Titanium.addByProduct(Almandine);
        Obsidian.addByProduct(Olivine);
        Ash.addByProduct(Carbon);
        DarkAsh.addByProduct(Carbon);
        Marble.addByProduct(Calcite);
        Clay.addByProduct(Clay);
        Cassiterite.addByProduct(Tin);
        BlackGranite.addByProduct(Biotite);
        RedGranite.addByProduct(PotassiumFeldspar);
        Phosphate.addByProduct(Phosphor);
        Phosphor.addByProduct(Phosphate);
        Tanzanite.addByProduct(Opal);
        Opal.addByProduct(Tanzanite);
        Amethyst.addByProduct(Amethyst);
//      Amber.addByProduct(Amber);
        Neutronium.addByProduct(Neutronium);
        Lithium.addByProduct(Lithium);
        Silicon.addByProduct(SiliconDioxide);
        Salt.addByProduct(RockSalt);
        RockSalt.addByProduct(Salt);

//        Glue.mChemicalFormula = "No Horses were harmed for the Production";
//        UUAmplifier.mChemicalFormula = "Accelerates the Mass Fabricator";
//        WoodSealed.mChemicalFormula = "";
//        Wood.mChemicalFormula = "";

//        Naquadah.mMoltenRGBa[0] = 0;
//        Naquadah.mMoltenRGBa[1] = 255;
//        Naquadah.mMoltenRGBa[2] = 0;
//        Naquadah.mMoltenRGBa[3] = 0;
//        NaquadahEnriched.mMoltenRGBa[0] = 64;
//        NaquadahEnriched.mMoltenRGBa[1] = 255;
//        NaquadahEnriched.mMoltenRGBa[2] = 64;
//        NaquadahEnriched.mMoltenRGBa[3] = 0;
//        Naquadria.mMoltenRGBa[0] = 128;
//        Naquadria.mMoltenRGBa[1] = 255;
//        Naquadria.mMoltenRGBa[2] = 128;
//        Naquadria.mMoltenRGBa[3] = 0;

//        NaquadahEnriched.mChemicalFormula = "Nq+";
//        Naquadah.mChemicalFormula = "Nq";
//        Naquadria.mChemicalFormula = "NqX";
    }

    //TODO go through the GT_Loader_Item_Block_And_Fluid and make sure all explicitly added fluids have the LIQUID tag
    public static void init() {
        //TODO assign correct handle materials
//                for (Material material : generated) {
//            if (material == Blaze) {
//                material.handleMaterial = "blaze";
//            } /*else if (aMaterial.contains(SubTag.MAGICAL) && aMaterial.contains(SubTag.CRYSTAL) && Utils.isModLoaded(MOD_ID_TC)) {
//                    aMaterial.mHandleMaterial = Thaumium;
//                }*/ else if (material.getMass() > Element.Tc.getMass() * 2) {
//                material.handleMaterial = Tungstensteel.;
//            } else if (material.getMass() > Element.Tc.getMass()) {
//                material.handleMaterial = Steel;
//            } else {
//                material.handleMaterial = Wood;
//            }
//        }

        //TODO move to antimatter
        LIQUID.all().stream().filter(l -> !l.equals(Water) || !l.equals(Lava)).forEach(m -> new AntimatterMaterialFluid(References.ID, m, LIQUID));
        GAS.all().forEach(m -> new AntimatterMaterialFluid(References.ID, m, GAS));
        PLASMA.all().forEach(m -> new AntimatterMaterialFluid(References.ID, m, PLASMA));

        AntimatterAPI.all(Material.class, Material::setChemicalFormula);

        //If using small ore markers, every normal ore needs a small version. This greatly increases block usage
        if (AntimatterConfig.WORLD.ORE_VEIN_SMALL_ORE_MARKERS) ORE.all().forEach(m -> m.flags(ORE_SMALL));
    }
}