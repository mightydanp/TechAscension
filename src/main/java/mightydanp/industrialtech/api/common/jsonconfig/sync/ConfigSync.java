package mightydanp.industrialtech.api.common.jsonconfig.sync;

import mightydanp.industrialtech.api.common.handler.NetworkHandler;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater.BlocksInWaterServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface.RandomSurfaceServer;
import mightydanp.industrialtech.api.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.smallore.SmallOreVeinServer;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconServer;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialServer;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeServer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeServer;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MightyDanp on 1/4/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ConfigSync {
    private static SyncMessage syncMessage;
    public Map<String, Boolean> syncedJson = new HashMap<>();
    public MaterialFlagServer materialFlagServer = new MaterialFlagServer();
    public FluidStateServer fluidStateServer = new FluidStateServer();
    public OreTypeServer oreTypeServer = new OreTypeServer();
    public TextureIconServer textureIconServer = new TextureIconServer();
    public ToolPartServer toolPartServer = new ToolPartServer();
    public ToolTypeServer toolTypeServer = new ToolTypeServer();
    public StoneLayerServer stoneLayerServer = new StoneLayerServer();
    public MaterialServer materialServer = new MaterialServer();
    public OreVeinServer oreVeinServer = new OreVeinServer();
    public SmallOreVeinServer smallOreVeinServer = new SmallOreVeinServer();
    public BlocksInWaterServer blocksInWaterServer = new BlocksInWaterServer();
    public RandomSurfaceServer randomSurfaceServer = new RandomSurfaceServer();

    public boolean isSinglePlayer;
    public String singlePlayerWorldName;
    public static String serverIP;

    public void init(){
        IndustrialTech.materialFlagRegistry.initiate();
        IndustrialTech.fluidStateRegistry.initiate();
        IndustrialTech.textureIconRegistry.initiate();
        IndustrialTech.oreTypeRegistry.initiate();
        IndustrialTech.toolPartRegistry.initiate();
        IndustrialTech.toolTypeRegistry.initiate();
        IndustrialTech.stoneLayerRegistry.initiate();
        ModMaterials.commonInit();
        IndustrialTech.materialRegistryInstance.initiate();
        IndustrialTech.oreVeinRegistry.initiate();
        IndustrialTech.smallOreVeinRegistry.initiate();
        IndustrialTech.blocksInWaterRegistry.initiate();
        IndustrialTech.randomSurfaceRegistry.initiate();
    }

    public void initClient(){
        IndustrialTech.materialRegistryInstance.initiateClient();
    }

    /*
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onModelBakeEvent(ModelRegistryEvent event) {
        List<ITMaterial> materials = MaterialRegistry.getMaterials();



        for(ITMaterial material : materials) {
            for (IMaterialFlag materialFlag : material.materialFlags){
                if(materialFlag == ORE || materialFlag == GEM || materialFlag == STONE_LAYER){
                    for(IStoneLayer stoneLayer : StoneLayerRegistry.getAllStoneLayers()){


                        if(materialFlag == STONE_LAYER){
                            Block thin_slab_block = RegistryHandler.registerBlock(Ref.mod_id,"thin_" + name + "_slab", new ThinSlabBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation(stoneLayer.getModID(), stoneLayer.getBlockName())));
                            thinSlabList.add(thin_slab_block);
                            Item thin_slab_item_block = RegistryHandler.registerItem(Ref.mod_id,"thin_" + name + "_slab", new ThinSlabItemBlock(thin_slab_block, new Item.Properties().stacksTo(1)));
                            thinSlabItemList.add(thin_slab_item_block);
                        /*
                        Block leg_block = RegistryHandler.registerBlock(Ref.mod_id,name + "_leg", new LegBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation(stoneLayer.getModID(), stoneLayer.getBlockName())));
                        Item leg_item_block = RegistryHandler.registerItem(Ref.mod_id, name + "_leg", new LegItemBlock(leg_block, new Item.Properties().stacksTo(1)));
                        }
                    }

                    if(materialFlag == ORE || materialFlag == GEM){
                        crushedOre = RegistryHandler.registerItem(Ref.mod_id,  "crushed_" + name + "_ore", new OreProductsItem(new Item.Properties()
                                .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                        purifiedOre = RegistryHandler.registerItem(Ref.mod_id,  "purified_" + name + "_ore", new OreProductsItem(new Item.Properties()
                                .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                        centrifugedOre = RegistryHandler.registerItem(Ref.mod_id,  "centrifuged_" + name + "_ore", new OreProductsItem(new Item.Properties()
                                .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    }

                    if(materialFlag == GEM){
                        gem = RegistryHandler.registerItem(Ref.mod_id,  name + "_gem", new GemItem(new Item.Properties()
                                .tab(ModItemGroups.gem_tab), symbol));
                        chippedGem = RegistryHandler.registerItem(Ref.mod_id,  "chipped_" + name + "_gem", new GemItem(new Item.Properties()
                                .tab(ModItemGroups.gem_tab), symbol));
                        flawedGem = RegistryHandler.registerItem(Ref.mod_id,  "flawed_" + name + "_gem", new GemItem(new Item.Properties()
                                .tab(ModItemGroups.gem_tab), symbol));
                        flawlessGem = RegistryHandler.registerItem(Ref.mod_id,  "flawless_" + name + "_gem", new GemItem(new Item.Properties()
                                .tab(ModItemGroups.gem_tab), symbol));
                        legendaryGem = RegistryHandler.registerItem(Ref.mod_id,  "legendary_" + name + "_gem", new GemItem(new Item.Properties()
                                .tab(ModItemGroups.gem_tab), symbol));
                    }

                    dust = RegistryHandler.registerItem(Ref.mod_id,  "" + name + "_dust",  new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    smallDust = RegistryHandler.registerItem(Ref.mod_id,  "small_" + name + "_dust", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    tinyDust = RegistryHandler.registerItem(Ref.mod_id,  "tiny_" + name + "_dust", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                }

                if(materialFlag == FLUID || materialFlag == GAS) {
                    FluidAttributes.Builder attributes;

                    if (materialFlag == FLUID) {
                        attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                        if(fluidDensity != null) attributes.density(fluidDensity);
                        if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                        if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                        fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                        fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                        fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(()-> fluid, fluidAcceleration, color));
                    }

                    if (materialFlag == GAS) {
                        attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name)).temperature(boilingPoint).color(color).gaseous();
                        if(fluidDensity != null) attributes.density(fluidDensity);
                        if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                        if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                        fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                        fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                        fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(()-> fluid, fluidAcceleration, color));
                    }
                }

                if(materialFlag == INGOT){
                    ingot = RegistryHandler.registerItem(Ref.mod_id, name + "_" + INGOT.name(),  new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, symbol));
                }

            for (IToolPart flag : toolParts) {
                if (flag == TOOL_HEAD) {
                    dullPickaxe = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_pickaxe_head",
                            dullPickaxe = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                    pickaxeHead = RegistryHandler.registerItem(Ref.mod_id, name + "_pickaxe_head",
                            pickaxeHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                    hammerHead = RegistryHandler.registerItem(Ref.mod_id, name + "_hammer_head",
                            hammerHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                    dullChiselHead = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_chisel_head",
                            dullChiselHead = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                    chiselHead = RegistryHandler.registerItem(Ref.mod_id, name + "_chisel_head",
                            chiselHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));

                }

                if (flag == TOOL_WEDGE) {
                    wedge = RegistryHandler.registerItem(Ref.mod_id, name + "_wedge",
                            wedge = new ToolBindingItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
                }

                if (flag == TOOL_WEDGE_HANDLE) {
                    wedgeHandle = RegistryHandler.registerItem(Ref.mod_id, name + "_wedge_handle",
                            wedgeHandle = new ToolHandleItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
                }
            }

        }

    }
    }
    */


    @SubscribeEvent
    public static void serverSyncBlocks(RegistryEvent.MissingMappings<Block> event) {
        Map<ResourceLocation, Block> missing = new HashMap<>();
        event.getMappings(Ref.mod_id).forEach(o -> missing.put(o.key, o.getTarget()));

    }

    @SubscribeEvent
    public static void serverSyncItems(RegistryEvent.MissingMappings<Item> event) {
        Map<ResourceLocation, Item> missing = new HashMap<>();
        event.getMappings(Ref.mod_id).forEach(o -> missing.put(o.key, o.getTarget()));
    }

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer().getServer() != null) {
            boolean isSinglePlayer = event.getPlayer().getServer().isSingleplayer();
            String singlePlayerWorldName = isSinglePlayer ? event.getPlayer().getServer().getWorldPath(FolderName.ROOT).getParent().getFileName().toString(): "";/////////

            syncMessage = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

            syncMessage.setMaterialFlags(MaterialFlagRegistry.getAllMaterialFlags());
            syncMessage.setFluidStates(FluidStateRegistry.getAllFluidStates());
            syncMessage.setOreTypes(OreTypeRegistry.getAllOreTypes());
            syncMessage.setTextureIcons(TextureIconRegistry.getAllTextureIcons());
            syncMessage.setToolParts(ToolPartRegistry.getAllToolParts());
            syncMessage.setToolTypes(ToolTypeRegistry.getAllToolTypes());
            syncMessage.setStoneLayers(StoneLayerRegistry.getAllStoneLayers());
            //
            syncMessage.setMaterials(MaterialRegistry.getMaterials());

            syncMessage.setOreVeins(OreVeinRegistry.getAllOreVeins());
            syncMessage.setSmallOreVeins(SmallOreVeinRegistry.getAllSmallOreVeins());
            syncMessage.setBlocksInWater(BlocksInWaterRegistry.getAllBlocksInWaters());
            syncMessage.setRandomSurface(RandomSurfaceRegistry.getAllRandomSurfaces());

            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();

            if (player != null) {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), syncMessage);
            } else {
                NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), syncMessage);
            }
            /*
            if(IndustrialTech.configSync.syncedJson.containsValue(false)){
                NetworkScreenHandler.openClientGui((ServerPlayerEntity)event.getPlayer(), RefScreenIDs.syncScreenId);
            }
            */

        }
    }

    @SubscribeEvent
    public static void tagUpdate(TagsUpdatedEvent event) {

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerJoinServer(ClientPlayerNetworkEvent.LoggedInEvent event) {
        if (event.getPlayer() != null) {
            if (IndustrialTech.configSync.syncedJson.containsValue(false)) {
                if (Minecraft.getInstance().level != null && Minecraft.getInstance().getCurrentServer() != null && Minecraft.getInstance().screen != null) {
                    serverIP = Minecraft.getInstance().getCurrentServer().ip;
                    Minecraft.getInstance().level.disconnect();
                }
            }
        }
    }

    /*
    @SubscribeEvent
    public static void onLoadChunk(ChunkEvent.Load event){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server != null){
            if(server.isSingleplayer()) {
                if (!IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id  + "/" + Ref.mod_id)) {
                    server.close();
                }
            }
        }
    }

     */

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onDisconnect(GuiOpenEvent event) {
        SyncScreen screen = new SyncScreen();

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if(event.getGui() != null) {
            IndustrialTech.LOGGER.debug(event.getGui());
            IndustrialTech.LOGGER.debug(event.getGui().getTitle().getString());

            if (server != null) {
                if (server.isSingleplayer() && Minecraft.getInstance().getSingleplayerServer() != null) {
                        IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();

                    if (event.getGui() instanceof WorkingScreen) {
                        if (!(new File("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id).exists()) && !IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id)) {
                            try {
                                IndustrialTech.configSync.materialFlagServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.fluidStateServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.oreTypeServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.toolPartServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.toolTypeServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.stoneLayerServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);

                                IndustrialTech.configSync.materialServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);

                                IndustrialTech.configSync.oreVeinServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.smallOreVeinServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.blocksInWaterServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.randomSurfaceServer.syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);

                                IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                                IndustrialTech.mainJsonConfig.reloadMainConfigJson();



                            } catch (IOException e) {
                                IndustrialTech.LOGGER.fatal("couldn't sync single player world with new world that was created.");
                            }
                        }
                    }

                    if (event.getGui() instanceof WorldLoadProgressScreen) {
                        if((new File("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id).exists())) {
                            if (!IndustrialTech.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id)) {
                                IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                                IndustrialTech.mainJsonConfig.reloadMainConfigJson();
                                Minecraft.getInstance().close();
                            }
                        }
                    }
                }
            }

            if (event.getGui() instanceof DisconnectedScreen) {
                if (IndustrialTech.configSync.syncedJson.containsValue(false)) {
                    event.setGui(screen);
                }
            }
        }
    }






}

