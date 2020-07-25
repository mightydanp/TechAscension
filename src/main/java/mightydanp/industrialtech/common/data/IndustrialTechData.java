package mightydanp.industrialtech.common.data;

/**
 * Created by MightyDanp on 7/19/2020.
 */
import mightydanp.industrialtech.common.blocks.BlockCasing;
import mightydanp.industrialtech.common.blocks.BlockMachineFrame;
import mightydanp.industrialtech.common.lib.References;
import muramasa.antimatter.AntimatterConfig;
import mightydanp.industrialtech.common.blocks.BlockCoil;
import muramasa.antimatter.cover.Cover;
import muramasa.antimatter.item.ItemBasic;
import muramasa.antimatter.item.ItemBattery;
import muramasa.antimatter.item.ItemCover;
import muramasa.antimatter.item.ItemFluidCell;
import muramasa.antimatter.machine.Tier;
import muramasa.antimatter.ore.StoneType;
import muramasa.antimatter.pipe.PipeSize;
import muramasa.antimatter.pipe.types.Cable;
import muramasa.antimatter.pipe.types.FluidPipe;
import muramasa.antimatter.pipe.types.ItemPipe;
import muramasa.antimatter.pipe.types.Wire;
import muramasa.antimatter.texture.Texture;
import mightydanp.industrialtech.common.blocks.BlockFusionCasing;
import mightydanp.industrialtech.common.cover.CoverConveyor;
import mightydanp.industrialtech.common.cover.CoverPlate;
import mightydanp.industrialtech.common.cover.CoverPump;
import mightydanp.industrialtech.common.tree.BlockRubberLeaves;
import mightydanp.industrialtech.common.tree.BlockRubberLog;
import mightydanp.industrialtech.common.tree.BlockRubberSapling;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;

import static mightydanp.industrialtech.common.data.Materials.*;
import static muramasa.antimatter.machine.MachineFlag.*;

public class IndustrialTechData {

    private static final boolean HC = AntimatterConfig.GAMEPLAY.HARDCORE_CABLES;

    public static void init() {

    }
    public static final Cover COVER_PLATE = new CoverPlate();
    public static final Cover COVER_CONVEYOR = new CoverConveyor();
    public static final Cover COVER_PUMP = new CoverPump();

    public static ItemBasic<?> StickyResin = new ItemBasic<>(References.ID, "sticky_resin");
    public static ItemBasic<?> ComputerMonitor = new ItemBasic<>(References.ID, "computer_monitor").tip("Can be placed on machines as a cover");

    public static ItemFluidCell CellTin = new ItemFluidCell(Tin, 1000);
    public static ItemFluidCell CellSteel = new ItemFluidCell(Steel, 16000);
    public static ItemFluidCell CellTungstensteel = new ItemFluidCell(TungstenSteel, 64000);

    public static ItemBasic<?> ItemFilter = new ItemBasic<>(References.ID, "item_filter");
    public static ItemBasic<?> DiamondSawBlade = new ItemBasic<>(References.ID, "diamond_saw_blade");
    public static ItemBasic<?> DiamondGrindHead = new ItemBasic<>(References.ID, "diamond_grind_head");
    public static ItemBasic<?> TungstenGrindHead = new ItemBasic<>(References.ID, "tungsten_grind_head");
    public static ItemBasic<?> IridiumAlloyIngot = new ItemBasic<>(References.ID, "iridium_alloy_ingot").tip("Used to make Iridium Plates");
    public static ItemBasic<?> IridiumReinforcedPlate = new ItemBasic<>(References.ID, "iridium_reinforced_plate").tip("GT2s Most Expensive Component");
    public static ItemBasic<?> IridiumNeutronReflector = new ItemBasic<>(References.ID, "iridium_neutron_reflector").tip("Indestructible");
    public static ItemBasic<?> QuantumEye = new ItemBasic<>(References.ID, "quantum_eye").tip("Improved Ender Eye");
    public static ItemBasic<?> QuantumStat = new ItemBasic<>(References.ID, "quantum_star").tip("Improved Nether Star");
    public static ItemBasic<?> GraviStar = new ItemBasic<>(References.ID, "gravi_star").tip("Ultimate Nether Star");

    public static ItemBasic<?> MotorLV = new ItemBasic<>(References.ID, "motor_lv");
    public static ItemBasic<?> MotorMV = new ItemBasic<>(References.ID, "motor_mv");
    public static ItemBasic<?> MotorHV = new ItemBasic<>(References.ID, "motor_hv");
    public static ItemBasic<?> MotorEV = new ItemBasic<>(References.ID, "motor_ev");
    public static ItemBasic<?> MotorIV = new ItemBasic<>(References.ID, "motor_iv");
    public static ItemCover PumpLV = new ItemCover(References.ID, CoverPump.ID, Tier.LV).tip("640 L/s (as Cover)");
    public static ItemCover PumpMV = new ItemCover(References.ID, CoverPump.ID, Tier.MV).tip("2,560 L/s (as Cover)");
    public static ItemCover PumpHV = new ItemCover(References.ID, CoverPump.ID, Tier.HV).tip("10,240 L/s (as Cover)");
    public static ItemCover PumpEV = new ItemCover(References.ID, CoverPump.ID, Tier.EV).tip("40,960 L/s (as Cover)");
    public static ItemCover PumpIV = new ItemCover(References.ID, CoverPump.ID, Tier.IV).tip("163,840 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorLV = new ItemBasic<>(References.ID, "fluid_regulator_lv").tip("Configurable up to 640 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorMV = new ItemBasic<>(References.ID, "fluid_regulator_mv").tip("Configurable up to 2,560 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorHV = new ItemBasic<>(References.ID, "fluid_regulator_hv").tip("Configurable up to 10,240 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorEV = new ItemBasic<>(References.ID, "fluid_regulator_ev").tip("Configurable up to 40,960 L/s (as Cover)");
    public static ItemBasic<?> FluidRegulatorIV = new ItemBasic<>(References.ID, "fluid_regulator_iv").tip("Configurable up to 163,840 L/s (as Cover)");
    public static ItemCover ConveyorLV = new ItemCover(References.ID, CoverConveyor.ID, Tier.LV).tip("1 Stack every 20s (as Cover)");
    public static ItemCover ConveyorMV = new ItemCover(References.ID, CoverConveyor.ID, Tier.MV).tip("1 Stack every 5s (as Cover)");
    public static ItemCover ConveyorHV = new ItemCover(References.ID, CoverConveyor.ID, Tier.HV).tip("1 Stack every 1s (as Cover)");
    public static ItemCover ConveyorEV = new ItemCover(References.ID, CoverConveyor.ID, Tier.EV).tip("1 Stack every 0.5s (as Cover)");
    public static ItemCover ConveyorIV = new ItemCover(References.ID, CoverConveyor.ID, Tier.IV).tip("1 Stack every 0.05s (as Cover)");
    public static ItemBasic<?> PistonLV = new ItemBasic<>(References.ID, "piston_lv");
    public static ItemBasic<?> PistonMV = new ItemBasic<>(References.ID, "piston_mv");
    public static ItemBasic<?> PistonHV = new ItemBasic<>(References.ID, "piston_hv");
    public static ItemBasic<?> PistonEV = new ItemBasic<>(References.ID, "piston_ev");
    public static ItemBasic<?> PistonIV = new ItemBasic<>(References.ID, "piston_iv");
    public static ItemBasic<?> RobotArmLV = new ItemBasic<>(References.ID, "robot_arm_lv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmMV = new ItemBasic<>(References.ID, "robot_arm_mv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmHV = new ItemBasic<>(References.ID, "robot_arm_hv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmEV = new ItemBasic<>(References.ID, "robot_arm_ev").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> RobotArmIV = new ItemBasic<>(References.ID, "robot_arm_iv").tip("Insets into specific Slots (as Cover)");
    public static ItemBasic<?> FieldGenLV = new ItemBasic<>(References.ID, "field_gen_lv");
    public static ItemBasic<?> FieldGenMV = new ItemBasic<>(References.ID, "field_gen_mv");
    public static ItemBasic<?> FieldGenHV = new ItemBasic<>(References.ID, "field_gen_hv");
    public static ItemBasic<?> FieldGenEV = new ItemBasic<>(References.ID, "field_gen_ev");
    public static ItemBasic<?> FieldGenIV = new ItemBasic<>(References.ID, "field_gen_iv");
    public static ItemBasic<?> EmitterLV = new ItemBasic<>(References.ID, "emitter_lv");
    public static ItemBasic<?> EmitterMV = new ItemBasic<>(References.ID, "emitter_mv");
    public static ItemBasic<?> EmitterHV = new ItemBasic<>(References.ID, "emitter_hv");
    public static ItemBasic<?> EmitterEV = new ItemBasic<>(References.ID, "emitter_ev");
    public static ItemBasic<?> EmitterIV = new ItemBasic<>(References.ID, "emitter_iv");
    public static ItemBasic<?> SensorLV = new ItemBasic<>(References.ID, "sensor_lv");
    public static ItemBasic<?> SensorMV = new ItemBasic<>(References.ID, "sensor_mv");
    public static ItemBasic<?> SensorHV = new ItemBasic<>(References.ID, "sensor_hv");
    public static ItemBasic<?> SensorEV = new ItemBasic<>(References.ID, "sensor_ev");
    public static ItemBasic<?> SensorIV = new ItemBasic<>(References.ID, "sensor_iv");

    public static ItemBasic<?> NandChip = new ItemBasic<>(References.ID, "nand_chip").tip("A very simple circuit");
    public static ItemBasic<?> AdvCircuitParts = new ItemBasic<>(References.ID, "adv_circuit_parts").tip("Used for making Advanced Circuits");
    public static ItemBasic<?> EtchedWiringMV = new ItemBasic<>(References.ID, "etched_wiring_mv").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringHV = new ItemBasic<>(References.ID, "etched_wiring_hv").tip("Circuit board parts");
    public static ItemBasic<?> EtchedWiringEV = new ItemBasic<>(References.ID, "etched_wiring_ev").tip("Circuit board parts");
    public static ItemBasic<?> EngravedCrystalChip = new ItemBasic<>(References.ID, "engraved_crystal_chip").tip("Needed for Circuits");
    public static ItemBasic<?> EngravedLapotronChip = new ItemBasic<>(References.ID, "engraved_lapotron_chip").tip("Needed for Circuits");
    public static ItemBasic<?> CircuitBoardEmpty = new ItemBasic<>(References.ID, "circuit_board_empty").tip("A board Part");
    public static ItemBasic<?> CircuitBoardBasic = new ItemBasic<>(References.ID, "circuit_board_basic").tip("A basic Board");
    public static ItemBasic<?> CircuitBoardAdv = new ItemBasic<>(References.ID, "circuit_board_adv").tip("An advanced Board");
    public static ItemBasic<?> CircuitBoardProcessorEmpty = new ItemBasic<>(References.ID, "circuit_board_processor_empty").tip("A Processor Board Part");
    public static ItemBasic<?> CircuitBoardProcessor = new ItemBasic<>(References.ID, "circuit_board_processor").tip("A Processor Board");
    public static ItemBasic<?> CircuitBasic = new ItemBasic<>(References.ID, "circuit_basic").tip("A basic Circuit");
    public static ItemBasic<?> CircuitGood = new ItemBasic<>(References.ID, "circuit_good").tip("A good Circuit");
    public static ItemBasic<?> CircuitAdv = new ItemBasic<>(References.ID, "circuit_adv").tip("An advanced Circuit");
    public static ItemBasic<?> CircuitDataStorage = new ItemBasic<>(References.ID, "circuit_data_storage").tip("A Data Storage Chip");
    public static ItemBasic<?> CircuitDataControl = new ItemBasic<>(References.ID, "circuit_data_control").tip("A Data Control Processor");
    public static ItemBasic<?> CircuitEnergyFlow = new ItemBasic<>(References.ID, "circuit_energy_flow").tip("A High Voltage Processor");
    public static ItemBasic<?> CircuitDataOrb = new ItemBasic<>(References.ID, "circuit_data_orb").tip("A High Capacity Data Storage");
    public static ItemBasic<?> DataStick = new ItemBasic<>(References.ID, "data_stick").tip("A Low Capacity Data Storage");

    public static ItemBasic<?> BatteryTantalum = new ItemBattery(References.ID, "battery_tantalum", Tier.ULV, 10000, true).tip("Reusable");
    public static ItemBasic<?> BatteryHullSmall = new ItemBasic<>(References.ID, "battery_hull_small").tip("An empty LV Battery Hull");
    public static ItemBasic<?> BatteryHullMedium = new ItemBasic<>(References.ID, "battery_hull_medium").tip("An empty MV Battery Hull");
    public static ItemBasic<?> BatteryHullLarge = new ItemBasic<>(References.ID, "battery_hull_large").tip("An empty HV Battery Hull");
    public static ItemBasic<?> BatterySmallAcid = new ItemBattery(References.ID, "battery_small_acid", Tier.LV, 50000, false).tip("Single Use");
    public static ItemBasic<?> BatterySmallMercury = new ItemBattery(References.ID, "battery_small_mercury", Tier.LV, 100000, false).tip("Single Use");
    public static ItemBasic<?> BatterySmallCadmium = new ItemBattery(References.ID, "battery_small_cadmium", Tier.LV,75000, true).tip("Reusable");
    public static ItemBasic<?> BatterySmallLithium = new ItemBattery(References.ID, "battery_small_lithium", Tier.LV, 100000, true).tip("Reusable");
    public static ItemBasic<?> BatterySmallSodium = new ItemBattery(References.ID, "battery_small_sodium", Tier.LV, 50000, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumAcid = new ItemBattery(References.ID, "battery_medium_acid", Tier.MV, 200000, false).tip("Single Use");
    public static ItemBasic<?> BatteryMediumMercury = new ItemBattery(References.ID, "battery_medium_mercury", Tier.MV, 400000, false).tip("Single Use");
    public static ItemBasic<?> BatteryMediumCadmium = new ItemBattery(References.ID, "battery_medium_cadmium", Tier.MV, 300000, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumLithium = new ItemBattery(References.ID, "battery_medium_lithium", Tier.MV, 400000, true).tip("Reusable");
    public static ItemBasic<?> BatteryMediumSodium = new ItemBattery(References.ID, "battery_medium_sodium", Tier.MV,200000, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeAcid = new ItemBattery(References.ID, "battery_large_acid", Tier.HV, 800000, false).tip("Single Use");
    public static ItemBasic<?> BatteryLargeMercury = new ItemBattery(References.ID, "battery_large_mercury", Tier.HV, 1600000, false).tip("Single Use");
    public static ItemBasic<?> BatteryLargeCadmium = new ItemBattery(References.ID, "battery_large_cadmium", Tier.HV, 1200000, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeLithium = new ItemBattery(References.ID, "battery_large_lithium", Tier.HV, 1600000, true).tip("Reusable");
    public static ItemBasic<?> BatteryLargeSodium = new ItemBattery(References.ID, "battery_large_sodium", Tier.HV, 800000, true).tip("Reusable");
    public static ItemBasic<?> BatteryEnergyOrb = new ItemBasic<>(References.ID, "battery_energy_orb");
    public static ItemBasic<?> BatteryEnergyOrbCluster = new ItemBasic<>(References.ID, "battery_energy_orb_cluster");

    public static ItemBasic<?> EmptyShape = new ItemBasic<>(References.ID, "empty_shape_plate").tip("Raw plate to make Molds and Extruder Shapes");
    public static ItemBasic<?> MoldPlate = new ItemBasic<>(References.ID, "mold_plate").tip("Mold for making Plates");
    public static ItemBasic<?> MoldGear = new ItemBasic<>(References.ID, "mold_gear").tip("Mold for making Gears");
    public static ItemBasic<?> MoldGearSmall = new ItemBasic<>(References.ID, "mold_small_gear").tip("Mold for making Small Gears");
    public static ItemBasic<?> MoldCoinage = new ItemBasic<>(References.ID, "mold_coinage").tip("Secure Mold for making Coins (Don't lose it!)");
    public static ItemBasic<?> MoldBottle = new ItemBasic<>(References.ID, "mold_bottle").tip("Mold for making Bottles");
    public static ItemBasic<?> MoldIngot = new ItemBasic<>(References.ID, "mold_ingot").tip("Mold for making Ingots");
    public static ItemBasic<?> MoldBall = new ItemBasic<>(References.ID, "mold_ball").tip("Mold for making Balls");
    public static ItemBasic<?> MoldBlock = new ItemBasic<>(References.ID, "mold_block").tip("Mold for making Blocks");
    public static ItemBasic<?> MoldNugget = new ItemBasic<>(References.ID, "mold_nugget").tip("Mold for making Nuggets");
    public static ItemBasic<?> MoldAnvil = new ItemBasic<>(References.ID, "mold_anvil").tip("Mold for making Anvils");
    public static ItemBasic<?> ShapePlate = new ItemBasic<>(References.ID, "shape_plate").tip("Shape for making Plates");
    public static ItemBasic<?> ShapeRod = new ItemBasic<>(References.ID, "shape_rod").tip("Shape for making Rods");
    public static ItemBasic<?> ShapeBolt = new ItemBasic<>(References.ID, "shape_bolt").tip("Shape for making Bolts");
    public static ItemBasic<?> ShapeRing = new ItemBasic<>(References.ID, "shape_ring").tip("Shape for making Rings");
    public static ItemBasic<?> ShapeCell = new ItemBasic<>(References.ID, "shape_cell").tip("Shape for making Cells");
    public static ItemBasic<?> ShapeIngot = new ItemBasic<>(References.ID, "shape_ingot").tip("Shape for making Ingots");
    public static ItemBasic<?> ShapeWire = new ItemBasic<>(References.ID, "shape_wire").tip("Shape for making Wires");
    public static ItemBasic<?> ShapePipeTiny = new ItemBasic<>(References.ID, "shape_pipe_tiny").tip("Shape for making Tiny Pipes");
    public static ItemBasic<?> ShapePipeSmall = new ItemBasic<>(References.ID, "shape_pipe_small").tip("Shape for making Small Pipes");
    public static ItemBasic<?> ShapePipeNormal = new ItemBasic<>(References.ID, "shape_pipe_normal").tip("Shape for making Normal Pipes");
    public static ItemBasic<?> ShapePipeLarge = new ItemBasic<>(References.ID, "shape_pipe_large").tip("Shape for making Large Pipes");
    public static ItemBasic<?> ShapePipeHuge = new ItemBasic<>(References.ID, "shape_pipe_huge").tip("Shape for making Huge Pipes");
    public static ItemBasic<?> ShapeBlock = new ItemBasic<>(References.ID, "shape_block").tip("Shape for making Blocks");
    public static ItemBasic<?> ShapeHeadSword = new ItemBasic<>(References.ID, "shape_head_sword").tip("Shape for making Sword Blades");
    public static ItemBasic<?> ShapeHeadPickaxe = new ItemBasic<>(References.ID, "shape_head_pickaxe").tip("Shape for making Pickaxe Heads");
    public static ItemBasic<?> ShapeHeadShovel = new ItemBasic<>(References.ID, "shape_head_shovel").tip("Shape for making Shovel Heads");
    public static ItemBasic<?> ShapeHeadAxe = new ItemBasic<>(References.ID, "shape_head_axe").tip("Shape for making Axe Heads");
    public static ItemBasic<?> ShapeHeadHoe = new ItemBasic<>(References.ID, "shape_head_hoe").tip("Shape for making Hoe Heads");
    public static ItemBasic<?> ShapeHeadHammer = new ItemBasic<>(References.ID, "shape_head_hammer").tip("Shape for making Hammer Heads");
    public static ItemBasic<?> ShapeHeadFile = new ItemBasic<>(References.ID, "shape_head_file").tip("Shape for making File Heads");
    public static ItemBasic<?> ShapeHeadSaw = new ItemBasic<>(References.ID, "shape_head_saw").tip("Shape for making Saw Heads");
    public static ItemBasic<?> ShapeGear = new ItemBasic<>(References.ID, "shape_gear").tip("Shape for making Gears");
    public static ItemBasic<?> ShapeGearSmall = new ItemBasic<>(References.ID, "shape_gear_small").tip("Shape for making Small Gears");
    public static ItemBasic<?> ShapeBottle = new ItemBasic<>(References.ID, "shape_bottle").tip("Shape for making Bottles"); //TODO needed?
    //
    //    //TODO optional items (register anyway, but don't show in JEI?)
    //    //TODO move to IC2+IC2C Registrar
    //    public static final RegistryObject<Item> DropTin = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_tin", "Source of Tin")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropLead = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_lead", "Source of Lead")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropSilver = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_silver", "Source of Silver")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropIron = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_iron", "Source of Iron")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropGold = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_gold", "Source of Gold")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropAluminium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_aluminium", "Source of Aluminium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropTitanium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_titanium", "Source of Titanium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUranium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_uranium", "Source of Uranium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUranite = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_uranite", "Source of Uranite")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropThorium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_thorium", "Source of Thorium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropNickel = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_nickel", "Source of Nickel")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropZinc = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_zinc", "Source of Zinc")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropManganese = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_manganese", "Source of Manganese")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropCopper = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_copper", "Source of Copper")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropTungsten = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_tungsten", "Source of Tungsten")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropPlatinum = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_platinum", "Source of Platinum")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropIridium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_iridium", "Source of Iridium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropOsmium = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_osmium", "Source of Osmium")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropNaquadah = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_naquadah", "Source of Naquadah")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropEmerald = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_emerald", "Source of Emeralds")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropOil = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_oil", "Source of Oil")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUUM = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_uum", "Source of UU Matter")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //    public static final RegistryObject<Item> DropUUA = new ItemBasic<>(References.ID, "").tip(References.ID, "drop_uua", "Source of UU Amplifier")/*.optional(References.MOD_IC2, References.MOD_IC2C)*/;
    //
    //    //TODO move to Forestry Registrar
    //    public static final RegistryObject<Item> CombLignite = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_lignite", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCoal = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_coal", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombResin = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_resin", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombOil = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_oil", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombStone = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_stone", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCertus = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_certus", "")/*.required(References.MOD_FR, References.MOD_AE)*/;
    //    public static final RegistryObject<Item> CombRedstone = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_redstone", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombLapis = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_lapis", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombRuby = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_ruby", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSapphire = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_sapphire", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombDiamond = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_diamond", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombOlivine = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_olivine", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombEmerald = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_emerald", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSlag = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_slag", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombCopper = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_copper", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTin = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_tin", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombLead = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_lead", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombIron = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_iron", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSteel = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_steel", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombNickel = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_nickel", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombZinc = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_zinc", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombSilver = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_silver", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombGold = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_gold", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombAluminium = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_aluminium", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombManganese = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_manganese", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTitanium = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_titanium", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombChrome = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_chrome", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombTungsten = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_tungsten", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombPlatinum = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_platinum", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombIridium = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_iridium", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombUranium = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_uranium", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombPlutonium = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_plutonium", "")/*.optional(References.MOD_FR)*/;
    //    public static final RegistryObject<Item> CombNaquadah = new ItemBasic<>(References.ID, "").tip(References.ID, "comb_naquadah", "")/*.optional(References.MOD_FR)*/;

    //TODO
    //public static BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling();
    //public static BlockRubberLog RUBBER_LOG = new BlockRubberLog();
    //public static BlockLeavesBase RUBBER_LEAVES = new BlockLeavesBase("rubber_leaves", RUBBER_SAPLING);

    //STONE should be the only non-removable StoneType. It serves as the foundation. It is also used natively by BlockRock
    //TODO move vanilla stone types (and (vanilla) materials)
    public static StoneType STONE = new StoneType(References.ID, "stone", Materials.Stone, new Texture("minecraft", "block/stone"), SoundType.STONE, false).setState(Blocks.STONE);

    public static StoneType GRANITE = new StoneType(References.ID, "granite", Granite, new Texture("minecraft", "block/granite"), SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || References.debugStones).setState(Blocks.GRANITE);
    public static StoneType DIORITE = new StoneType(References.ID, "diorite", Diorite, new Texture("minecraft", "block/diorite"), SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || References.debugStones).setState(Blocks.DIORITE);
    public static StoneType ANDESITE = new StoneType(References.ID, "andesite", Andesite, new Texture("minecraft", "block/andesite"), SoundType.STONE, AntimatterConfig.WORLD.VANILLA_STONE_GEN || References.debugStones).setState(Blocks.ANDESITE);

    public static StoneType GRAVEL = new StoneType(References.ID, "gravel", Gravel, new Texture("minecraft", "block/gravel"), SoundType.GROUND, false).setState(Blocks.GRAVEL);
    public static StoneType SAND = new StoneType(References.ID, "sand", Sand, new Texture("minecraft", "block/sand"), SoundType.SAND, false).setState(Blocks.SAND);
    public static StoneType SAND_RED = new StoneType(References.ID, "sand_red", RedSand, new Texture("minecraft", "block/red_sand"), SoundType.SAND, false).setState(Blocks.RED_SAND);
    public static StoneType SANDSTONE = new StoneType(References.ID, "sandstone", Sandstone, new Texture("minecraft", "block/sandstone"), SoundType.STONE, false).setState(Blocks.SANDSTONE);

    public static StoneType NETHERRACK = new StoneType(References.ID, "netherrack", Materials.Netherrack, new Texture("minecraft", "block/netherrack"), SoundType.STONE, false).setState(Blocks.NETHERRACK);
    public static StoneType ENDSTONE = new StoneType(References.ID, "endstone", Materials.Endstone, new Texture("minecraft", "block/end_stone"), SoundType.STONE, false).setState(Blocks.END_STONE);

    public static StoneType GRANITE_RED = new StoneType(References.ID, "granite_red", Materials.RedGranite, new Texture(References.ID, "block/stone/granite_red"), SoundType.STONE, true);
    public static StoneType GRANITE_BLACK = new StoneType(References.ID, "granite_black", Materials.BlackGranite, new Texture(References.ID, "block/stone/granite_black"), SoundType.STONE, true);
    public static StoneType MARBLE = new StoneType(References.ID, "marble", Materials.Marble, new Texture(References.ID, "block/stone/marble"), SoundType.STONE, true);
    public static StoneType BASALT = new StoneType(References.ID, "basalt", Materials.Basalt, new Texture(References.ID, "block/stone/basalt"), SoundType.STONE, true);

    public static StoneType KOMATIITE = new StoneType(References.ID, "komatiite", Materials.Komatiite, new Texture(References.ID, "block/stone/komatiite"), SoundType.STONE, true);
    public static StoneType LIMESTONE = new StoneType(References.ID, "limestone", Limestone, new Texture(References.ID, "block/stone/limestone"), SoundType.STONE, true);
    public static StoneType GREEN_SCHIST = new StoneType(References.ID, "green_schist", GreenSchist, new Texture(References.ID, "block/stone/green_schist"), SoundType.STONE, true);
    public static StoneType BLUE_SCHIST = new StoneType(References.ID, "blue_schist", BlueSchist, new Texture(References.ID, "block/stone/blue_schist"), SoundType.STONE, true);
    public static StoneType KIMBERLITE = new StoneType(References.ID, "kimberlite", Kimberlite, new Texture(References.ID, "block/stone/kimberlite"), SoundType.STONE, true);
    public static StoneType QUARTZITE = new StoneType(References.ID, "quartzite", Quartzite, new Texture(References.ID, "block/stone/quartzite"), SoundType.STONE, true);

    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(References.ID, "anthracite_coal", new Texture(References.ID, "block/basic/anthracite_coal");
    //public static BlockBasic ANTHRACITE_COAL = new BlockBasic(References.ID, "anthracite_coal", new Texture(References.ID, "block/basic/anthracite_coal");

    //    public static BlockStone STONE_GRANITE_RED = new BlockStone(References.ID, GRANITE_RED);
    //    public static BlockStone STONE_GRANITE_BLACK = new BlockStone(References.ID, GRANITE_BLACK);
    //    public static BlockStone STONE_MARBLE = new BlockStone(References.ID, MARBLE);
    //    public static BlockStone STONE_BASALT = new BlockStone(References.ID, BASALT);
    //
    //    public static BlockStone STONE_KOMATIITE = new BlockStone(References.ID, KOMATIITE);
    //    public static BlockStone STONE_LIMESTONE = new BlockStone(References.ID, LIMESTONE);
    //    public static BlockStone STONE_GREEN_SCHIST = new BlockStone(References.ID, GREEN_SCHIST);
    //    public static BlockStone STONE_BLUE_SCHIST = new BlockStone(References.ID, BLUE_SCHIST);
    //    public static BlockStone STONE_KIMBERLITE = new BlockStone(References.ID, KIMBERLITE);
    //    public static BlockStone STONE_QUARTZITE = new BlockStone(References.ID, QUARTZITE);

    public static final BlockCasing CASING_ULV = new BlockCasing(References.ID, "casing_ulv");
    public static final BlockCasing CASING_LV = new BlockCasing(References.ID, "casing_lv");
    public static final BlockCasing CASING_MV = new BlockCasing(References.ID, "casing_mv");
    public static final BlockCasing CASING_HV = new BlockCasing(References.ID, "casing_hv");
    public static final BlockCasing CASING_EV = new BlockCasing(References.ID, "casing_ev");
    public static final BlockCasing CASING_IV = new BlockCasing(References.ID, "casing_iv");
    public static final BlockCasing CASING_LUV = new BlockCasing(References.ID, "casing_luv");
    public static final BlockCasing CASING_ZPM = new BlockCasing(References.ID, "casing_zpm");
    public static final BlockCasing CASING_UV = new BlockCasing(References.ID, "casing_uv");
    public static final BlockCasing CASING_MAX = new BlockCasing(References.ID, "casing_max");

    public static final BlockCasing CASING_FIRE_BRICK = new BlockCasing(References.ID, "casing_fire_brick");
    public static final BlockCasing CASING_BRONZE = new BlockCasing(References.ID, "casing_bronze");
    public static final BlockCasing CASING_BRICKED_BRONZE = new BlockCasing(References.ID, "casing_bricked_bronze");
    public static final BlockCasing CASING_BRONZE_PLATED_BRICK = new BlockCasing(References.ID, "casing_bronze_plated_brick");
    public static final BlockCasing CASING_STEEL = new BlockCasing(References.ID, "casing_steel");
    public static final BlockCasing CASING_BRICKED_STEEL = new BlockCasing(References.ID, "casing_bricked_steel");
    public static final BlockCasing CASING_SOLID_STEEL = new BlockCasing(References.ID, "casing_solid_steel");
    public static final BlockCasing CASING_STAINLESS_STEEL = new BlockCasing(References.ID, "casing_stainless_steel");
    public static final BlockCasing CASING_TITANIUM = new BlockCasing(References.ID, "casing_titanium");
    public static final BlockCasing CASING_TUNGSTENSTEEL = new BlockCasing(References.ID, "casing_tungstensteel");
    public static final BlockCasing CASING_HEAT_PROOF = new BlockCasing(References.ID, "casing_heat_proof");
    public static final BlockCasing CASING_FROST_PROOF = new BlockCasing(References.ID, "casing_frost_proof");
    public static final BlockCasing CASING_RADIATION_PROOF = new BlockCasing(References.ID, "casing_radiation_proof");
    public static final BlockCasing CASING_FIREBOX_BRONZE = new BlockCasing(References.ID, "casing_firebox_bronze");
    public static final BlockCasing CASING_FIREBOX_STEEL = new BlockCasing(References.ID, "casing_firebox_steel");
    public static final BlockCasing CASING_FIREBOX_TITANIUM = new BlockCasing(References.ID, "casing_firebox_titanium");
    public static final BlockCasing CASING_FIREBOX_TUNGSTENSTEEL = new BlockCasing(References.ID, "casing_firebox_tungstensteel");
    public static final BlockCasing CASING_GEARBOX_BRONZE = new BlockCasing(References.ID, "casing_gearbox_bronze");
    public static final BlockCasing CASING_GEARBOX_STEEL = new BlockCasing(References.ID, "casing_gearbox_steel");
    public static final BlockCasing CASING_GEARBOX_TITANIUM = new BlockCasing(References.ID, "casing_gearbox_titanium");
    public static final BlockCasing CASING_GEARBOX_TUNGSTENSTEEL = new BlockCasing(References.ID, "casing_gearbox_tungstensteel");
    public static final BlockCasing CASING_PIPE_BRONZE = new BlockCasing(References.ID, "casing_pipe_bronze");
    public static final BlockCasing CASING_PIPE_STEEL = new BlockCasing(References.ID, "casing_pipe_steel");
    public static final BlockCasing CASING_PIPE_TITANIUM = new BlockCasing(References.ID, "casing_pipe_titanium");
    public static final BlockCasing CASING_PIPE_TUNGSTENSTEEL = new BlockCasing(References.ID, "casing_pipe_tungstensteel");
    public static final BlockCasing CASING_ENGINE_INTAKE = new BlockCasing(References.ID, "casing_engine_intake");

    public static final BlockFusionCasing CASING_FUSION_1 = new BlockFusionCasing(References.ID, "casing_fusion_1");
    public static final BlockFusionCasing CASING_FUSION_2 = new BlockFusionCasing(References.ID, "casing_fusion_2");
    public static final BlockFusionCasing CASING_FUSION_3 = new BlockFusionCasing(References.ID, "casing_fusion_3");

    //public static final BlockTurbineCasing CASING_TURBINE_1 = new BlockTurbineCasing(References.ID, "casing_turbine_1");
    //public static final BlockTurbineCasing CASING_TURBINE_2 = new BlockTurbineCasing(References.ID, "casing_turbine_2");
    //public static final BlockTurbineCasing CASING_TURBINE_3 = new BlockTurbineCasing(References.ID, "casing_turbine_3");
    //public static final BlockTurbineCasing CASING_TURBINE_4 = new BlockTurbineCasing(References.ID, "casing_turbine_4");

    public static final BlockCoil COIL_CUPRONICKEL = new BlockCoil(References.ID, "coil_cupronickel", 113); //1808
    public static final BlockCoil COIL_KANTHAL = new BlockCoil(References.ID, "coil_kanthal", 169); //2704
    public static final BlockCoil COIL_NICHROME = new BlockCoil(References.ID, "coil_nichrome", 225); //3600
    public static final BlockCoil COIL_TUNGSTENSTEEL = new BlockCoil(References.ID, "coil_tungstensteel", 282); //4512
    public static final BlockCoil COIL_HSSG = new BlockCoil(References.ID, "coil_hssg", 338); //5408
    public static final BlockCoil COIL_NAQUADAH = new BlockCoil(References.ID, "coil_naquadah", 450); //7200
    public static final BlockCoil COIL_NAQUADAH_ALLOY = new BlockCoil(References.ID, "coil_naquadah_alloy", 563); //9008
    public static final BlockCoil COIL_FUSION = new BlockCoil(References.ID, "coil_fusion", 563); //9008
    public static final BlockCoil COIL_SUPERCONDUCTOR = new BlockCoil(References.ID, "coil_superconductor", 563); //9008

    public static final Cable<?> CABLE_RED_ALLOY = new Cable<>(References.ID, RedAlloy, 0, Tier.ULV).amps(1);
    public static final Cable<?> CABLE_COBALT = new Cable<>(References.ID, Cobalt, 2, Tier.LV).amps(2); //LV
    public static final Cable<?> CABLE_LEAD = new Cable<>(References.ID, Lead, 2, Tier.LV).amps(2);
    public static final Cable<?> CABLE_TIN = new Cable<>(References.ID, Tin, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_ZINC = new Cable<>(References.ID, Zinc, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_SOLDERING_ALLOY = new Cable<>(References.ID, SolderingAlloy, 1, Tier.LV).amps(1);
    public static final Cable<?> CABLE_IRON = new Cable<>(References.ID, Iron, HC ? 3 : 4, Tier.MV).amps(2); //MV
    public static final Cable<?> CABLE_NICKEL = new Cable<>(References.ID, Nickel, HC ? 3 : 5, Tier.MV).amps(3);
    public static final Cable<?> CABLE_CUPRONICKEL = new Cable<>(References.ID, Cupronickel, HC ? 3 : 4, Tier.MV).amps(2);
    public static final Cable<?> CABLE_COPPER = new Cable<>(References.ID, Copper, HC ? 2 : 3, Tier.MV).amps(1);
    public static final Cable<?> CABLE_ANNEALED_COPPER = new Cable<>(References.ID, AnnealedCopper, HC ? 1 : 2, Tier.MV).amps(1);
    public static final Cable<?> CABLE_KANTHAL = new Cable<>(References.ID, Kanthal, HC ? 3 : 8, Tier.HV).amps(4); //HV
    public static final Cable<?> CABLE_GOLD = new Cable<>(References.ID, Gold, HC ? 2 : 6, Tier.HV).amps(3);
    public static final Cable<?> CABLE_ELECTRUM = new Cable<>(References.ID, Electrum, HC ? 2 : 5, Tier.HV).amps(2);
    public static final Cable<?> CABLE_SILVER = new Cable<>(References.ID, Silver, HC ? 1 : 4, Tier.HV).amps(1);
    public static final Cable<?> CABLE_NICHROME = new Cable<>(References.ID, Nichrome, HC ? 4 : 32, Tier.EV).amps(3); //EV
    public static final Cable<?> CABLE_STEEL = new Cable<>(References.ID, Steel, HC ? 2 : 16, Tier.EV).amps(2);
    public static final Cable<?> CABLE_TITANIUM = new Cable<>(References.ID, Titanium, HC ? 2 : 12, Tier.EV).amps(4);
    public static final Cable<?> CABLE_ALUMINIUM = new Cable<>(References.ID, Aluminium, HC ? 1 : 8, Tier.EV).amps(1);
    public static final Cable<?> CABLE_GRAPHENE = new Cable<>(References.ID, Graphene, HC ? 1 : 16, Tier.IV).amps(1); //IV
    public static final Cable<?> CABLE_OSMIUM = new Cable<>(References.ID, Osmium, HC ? 2 : 32, Tier.IV).amps(4);
    public static final Cable<?> CABLE_PLATINUM = new Cable<>(References.ID, Platinum, HC ? 1 : 16, Tier.IV).amps(2);
    public static final Cable<?> CABLE_TUNGSTEN_STEEL = new Cable<>(References.ID, TungstenSteel, HC ? 1 : 14, Tier.IV).amps(3);
    public static final Cable<?> CABLE_TUNGSTEN = new Cable<>(References.ID, Tungsten, HC ? 2 : 12, Tier.IV).amps(1);
    public static final Cable<?> CABLE_HSSG = new Cable<>(References.ID, HSSG, HC ? 2 : 128, Tier.LUV).amps(4); //LUV
    public static final Cable<?> CABLE_NIOBIUM_TITANIUM = new Cable<>(References.ID, NiobiumTitanium, HC ? 2 : 128, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_VANADIUM_GALLIUM = new Cable<>(References.ID, VanadiumGallium, HC ? 2 : 128, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_YTTRIUM_BARIUM_CUPRATE = new Cable<>(References.ID, YttriumBariumCuprate, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Cable<?> CABLE_NAQUADAH = new Cable<>(References.ID, Naquadah, HC ? 2 : 64, Tier.ZPM).amps(2); //ZPM
    public static final Cable<?> CABLE_NAQUADAH_ALLOY = new Cable<>(References.ID, NaquadahAlloy, HC ? 4 : 64, Tier.ZPM).amps(2);
    public static final Cable<?> CABLE_DURANIUM = new Cable<>(References.ID, Duranium, HC ? 8 : 64, Tier.ZPM).amps(1);
    public static final Cable<?> CABLE_SUPERCONDUCTOR = new Cable<>(References.ID, Superconductor, 0, Tier.MAX).amps(4); //MAX

    public static final Wire<?> WIRE_RED_ALLOY = new Wire<>(References.ID, RedAlloy, 1, Tier.ULV).amps(1);
    public static final Wire<?> WIRE_COBALT = new Wire<>(References.ID, Cobalt, 4, Tier.LV).amps(2); //LV
    public static final Wire<?> WIRE_LEAD = new Wire<>(References.ID, Lead, 4, Tier.LV).amps(2);
    public static final Wire<?> WIRE_TIN = new Wire<>(References.ID, Tin, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_ZINC = new Wire<>(References.ID, Zinc, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_SOLDERING_ALLOY = new Wire<>(References.ID, SolderingAlloy, 2, Tier.LV).amps(1);
    public static final Wire<?> WIRE_IRON = new Wire<>(References.ID, Iron, HC ? 6 : 8, Tier.MV).amps(2); //MV
    public static final Wire<?> WIRE_NICKEL = new Wire<>(References.ID, Nickel, HC ? 6 : 10, Tier.MV).amps(3);
    public static final Wire<?> WIRE_CUPRONICKEL = new Wire<>(References.ID, Cupronickel, HC ? 6 : 8, Tier.MV).amps(2);
    public static final Wire<?> WIRE_COPPER = new Wire<>(References.ID, Copper, HC ? 4 : 6, Tier.MV).amps(1);
    public static final Wire<?> WIRE_ANNEALED_COPPER = new Wire<>(References.ID, AnnealedCopper, HC ? 2 : 4, Tier.MV).amps(1);
    public static final Wire<?> WIRE_KANTHAL = new Wire<>(References.ID, Kanthal, HC ? 6 : 16, Tier.HV).amps(4); //HV
    public static final Wire<?> WIRE_GOLD = new Wire<>(References.ID, Gold, HC ? 4 : 12, Tier.HV).amps(3);
    public static final Wire<?> WIRE_ELECTRUM = new Wire<>(References.ID, Electrum, HC ? 4 : 10, Tier.HV).amps(2);
    public static final Wire<?> WIRE_SILVER = new Wire<>(References.ID, Silver, HC ? 2 : 8, Tier.HV).amps(1);
    public static final Wire<?> WIRE_NICHROME = new Wire<>(References.ID, Nichrome, HC ? 8 : 64, Tier.EV).amps(3); //EV
    public static final Wire<?> WIRE_STEEL = new Wire<>(References.ID, Steel, HC ? 4 : 32, Tier.EV).amps(2);
    public static final Wire<?> WIRE_TITANIUM = new Wire<>(References.ID, Titanium, HC ? 4 : 24, Tier.EV).amps(4);
    public static final Wire<?> WIRE_ALUMINIUM = new Wire<>(References.ID, Aluminium, HC ? 2 : 16, Tier.EV).amps(1);
    public static final Wire<?> WIRE_GRAPHENE = new Wire<>(References.ID, Graphene, HC ? 2 : 32, Tier.IV).amps(1); //IV
    public static final Wire<?> WIRE_OSMIUM = new Wire<>(References.ID, Osmium, HC ? 4 : 64, Tier.IV).amps(4);
    public static final Wire<?> WIRE_PLATINUM = new Wire<>(References.ID, Platinum, HC ? 2 : 32, Tier.IV).amps(2);
    public static final Wire<?> WIRE_TUNGSTEN_STEEL = new Wire<>(References.ID, TungstenSteel, HC ? 2 : 28, Tier.IV).amps(3);
    public static final Wire<?> WIRE_TUNGSTEN = new Wire<>(References.ID, Tungsten, HC ? 2 : 12, Tier.IV).amps(1);
    public static final Wire<?> WIRE_HSSG = new Wire<>(References.ID, HSSG, HC ? 4 : 256, Tier.LUV).amps(4); //LUV
    public static final Wire<?> WIRE_NIOBIUM_TITANIUM = new Wire<>(References.ID, NiobiumTitanium, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_VANADIUM_GALLIUM = new Wire<>(References.ID, VanadiumGallium, HC ? 4 : 256, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_YTTRIUM_BARIUM_CUPRATE = new Wire<>(References.ID, YttriumBariumCuprate, HC ? 8 : 512, Tier.LUV).amps(4);
    public static final Wire<?> WIRE_NAQUADAH = new Wire<>(References.ID, Naquadah, HC ? 4 : 128, Tier.ZPM).amps(2); //ZPM
    public static final Wire<?> WIRE_NAQUADAH_ALLOY = new Wire<>(References.ID, NaquadahAlloy, HC ? 8 : 128, Tier.ZPM).amps(2);
    public static final Wire<?> WIRE_DURANIUM = new Wire<>(References.ID, Duranium, HC ? 16 : 128, Tier.ZPM).amps(1);
    public static final Wire<?> WIRE_SUPERCONDUCTOR = new Wire<>(References.ID, Superconductor, 1, Tier.MAX).amps(4); //MAX

    public static final FluidPipe<?> FLUID_PIPE_BRICK = new FluidPipe<>(References.ID, Brick, 1000, false).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(10, 10, 30, 60, 60, 60).pressures(400, 400, 400, 400, 400, 400);
    public static final FluidPipe<?> FLUID_PIPE_COPPER = new FluidPipe<>(References.ID, Copper, 700, true).caps(10).pressures(600);
    public static final FluidPipe<?> FLUID_PIPE_BISMUTH_BRONZE = new FluidPipe<>(References.ID, BismuthBronze, 950, true).caps(20).pressures(800);
    public static final FluidPipe<?> FLUID_PIPE_BLACK_STEEL = new FluidPipe<>(References.ID, BlackSteel, 1200, true).caps(10).pressures(900);
    public static final FluidPipe<?> FLUID_PIPE_STAINLESS_STEEL = new FluidPipe<>(References.ID, StainlessSteel, 1300, true).caps(60).pressures(1000);
    public static final FluidPipe<?> FLUID_PIPE_TITANIUM = new FluidPipe<>(References.ID, Titanium, 1668, true).caps(80).pressures(2500);
    public static final FluidPipe<?> FLUID_PIPE_TUNGSTEN_STEEL = new FluidPipe<>(References.ID, TungstenSteel, 3422, true).caps(100).pressures(5000);
    public static final FluidPipe<?> FLUID_PIPE_PLASTIC = new FluidPipe<>(References.ID, Plastic, 160, true).caps(60).pressures(2000);
    public static final FluidPipe<?> FLUID_PIPE_POLY = new FluidPipe<>(References.ID, Polytetrafluoroethylene, 327, true).caps(480).pressures(1000);
    public static final FluidPipe<?> FLUID_PIPE_HP = new FluidPipe<>(References.ID, HighPressure, 3422, true).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(4800, 4800, 4800, 7200, 9600, 9600).pressures(10000);
    public static final FluidPipe<?> FLUID_PIPE_PLASMA = new FluidPipe<>(References.ID, PlasmaContainment, 100000, true).sizes(PipeSize.NORMAL).caps(240, 240, 240, 240, 240, 240).pressures(100000);

    public static final ItemPipe<?> ITEM_PIPE_WOOD = new ItemPipe<>(References.ID, Wood).sizes(PipeSize.SMALL).caps(0, 0, 1, 0, 0, 0);
    public static final ItemPipe<?> ITEM_PIPE_WROUGHT_IRON = new ItemPipe<>(References.ID, WroughtIron).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE).caps(0, 0, 2, 3, 4, 0);
    public static final ItemPipe<?> ITEM_PIPE_COBALT_BRASS = new ItemPipe<>(References.ID, CobaltBrass).caps(2).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_BLACK_BRONZE = new ItemPipe<>(References.ID, BlackBronze).caps(3).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_STERLING_SILVER = new ItemPipe<>(References.ID, SterlingSilver).caps(4).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_ROSE_GOLD = new ItemPipe<>(References.ID, RoseGold).caps(4).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_PLATINUM = new ItemPipe<>(References.ID, Platinum).caps(5).sizes(PipeSize.SMALL, PipeSize.NORMAL, PipeSize.LARGE);
    public static final ItemPipe<?> ITEM_PIPE_TUNGSTEN_CARBIDE = new ItemPipe<>(References.ID, TungstenCarbide).caps(6);
    public static final ItemPipe<?> ITEM_PIPE_ULTIMET = new ItemPipe<>(References.ID, Ultimet).caps(8);
    public static final ItemPipe<?> ITEM_PIPE_HC = new ItemPipe<>(References.ID, HighCapacity).caps(10);
    public static final ItemPipe<?> ITEM_PIPE_OSMIRIDIUM = new ItemPipe<>(References.ID, Osmiridium).caps(20);

    // Rubber Tree
    public static final BlockRubberLeaves RUBBER_LEAVES = new BlockRubberLeaves(References.ID, "rubber_leaves");
    public static final BlockRubberLog RUBBER_LOG = new BlockRubberLog(References.ID, "rubber_log");
    public static final BlockRubberSapling RUBBER_SAPLING = new BlockRubberSapling(References.ID, "rubber_sapling");

}
