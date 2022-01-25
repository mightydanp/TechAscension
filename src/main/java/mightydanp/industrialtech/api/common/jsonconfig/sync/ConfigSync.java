package mightydanp.industrialtech.api.common.jsonconfig.sync;

import mightydanp.industrialtech.api.common.handler.NetworkHandler;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagServer;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 1/4/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ConfigSync {
    private static SyncMessage syncMessage;
    public Map<String, Boolean> syncedJson = new HashMap<>();
    public MaterialFlagServer materialFlagServer = new MaterialFlagServer();
    public MaterialServer materialServer = new MaterialServer();

    public String clientUUID;
    public boolean isSinglePlayer;
    public String singlePlayerWorldName;
    public static String serverIP;
    public static boolean hasLoadedAtLeastOnce = false;

    public void init(){
        IndustrialTech.materialFlagRegistry.initiate();
        IndustrialTech.fluidStateRegistry.initiate();
        IndustrialTech.textureIconRegistry.initiate();
        IndustrialTech.oreTypeRegistry.initiate();
        IndustrialTech.toolPartRegistry.initiate();
        //IndustrialTech.toolTypeRegistry.initiate();
        ModMaterials.commonInit();
        IndustrialTech.materialRegistryInstance.initiate();
    }

    public void initClient(){
        IndustrialTech.materialRegistryInstance.initiateClient();
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent event) {
        MaterialFlagRegistry materialFlagRegistry = IndustrialTech.materialFlagRegistry;
        /*
        for(ITMaterial material : MaterialRegistry.getMaterials()) {
            for (IMaterialFlag flag : material.materialFlags) {
                if (flag == DefaultMaterialFlag.ORE || flag == DefaultMaterialFlag.GEM) {
                    for (StoneLayerHandler stoneLayerHandler : ModStoneLayers.stoneLayerList) {
                        Block ore = RegistryHandler.registerBlock(Ref.mod_id, stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", new OreBlock(name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                        oreList.add(ore);
                        Item oreItemR = RegistryHandler.registerItem(Ref.mod_id, stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore",
                                new BlockOreItem(ore, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                        oreItemList.add(oreItemR);
                        Block smallOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore",
                                new SmallOreBlock("small_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                        smallOreList.add(smallOreBlockR);
                        Item smallOreItemR = RegistryHandler.registerItem(Ref.mod_id, "small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore",
                                new BlockItem(smallOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab)));
                        smallOreItemList.add(smallOreItemR);
                        Block denseOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore",
                                new DenseOreBlock("dense_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), denseOreDensity, stoneLayerHandler.layerBlock.defaultBlockState(), oreItemList));
                        denseOreList.add(denseOreBlockR);
                        Item denseOreItemR = RegistryHandler.registerItem(Ref.mod_id, "dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore",
                                new BlockOreItem(denseOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                        denseOreItemList.add(denseOreItemR);
                    }
                    crushedOre = RegistryHandler.registerItem(Ref.mod_id, "crushed_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    purifiedOre = RegistryHandler.registerItem(Ref.mod_id, "purified_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    centrifugedOre = RegistryHandler.registerItem(Ref.mod_id, "centrifuged_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                }

                if (flag == ORE) {
                }

                if (flag == GEM) {
                    gem = RegistryHandler.registerItem(Ref.mod_id, name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    chippedGem = RegistryHandler.registerItem(Ref.mod_id, "chipped_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    flawedGem = RegistryHandler.registerItem(Ref.mod_id, "flawed_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    flawlessGem = RegistryHandler.registerItem(Ref.mod_id, "flawless_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    legendaryGem = RegistryHandler.registerItem(Ref.mod_id, "legendary_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                }

                if (flag == DUST) {
                    dust = RegistryHandler.registerItem(Ref.mod_id, "" + name + "_dust", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    smallDust = RegistryHandler.registerItem(Ref.mod_id, "small_" + name + "_dust", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    tinyDust = RegistryHandler.registerItem(Ref.mod_id, "tiny_" + name + "_dust", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                }

                if (flag == FLUID || flag == GAS) {
                    FluidAttributes.Builder attributes;

                    if (flag == FLUID) {
                        attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                        if (fluidDensity != null) attributes.density(fluidDensity);
                        if (fluidLuminosity != null) attributes.luminosity(fluidLuminosity);
                        if (fluidViscosity != null) attributes.viscosity(fluidViscosity);
                        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                        fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                        fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                        fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(() -> fluid, fluidAcceleration, color));
                    }

                    if (flag == GAS) {
                        attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name)).temperature(boilingPoint).color(color).gaseous();
                        if (fluidDensity != null) attributes.density(fluidDensity);
                        if (fluidLuminosity != null) attributes.luminosity(fluidLuminosity);
                        if (fluidViscosity != null) attributes.viscosity(fluidViscosity);
                        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                        fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                        fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                        fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(() -> fluid, fluidAcceleration, color));
                    }
                }

                if (flag == INGOT) {
                    ingot = RegistryHandler.registerItem(Ref.mod_id, name + "_" + INGOT.name(), new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, symbol));
                }

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

         */

    }

    @SubscribeEvent
    public static void onPlayerJoinServer(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer().getServer() != null) {
            boolean isSinglePlayer = event.getPlayer().getServer().isSingleplayer();
            String singlePlayerWorldName = isSinglePlayer ? event.getPlayer().getServer().getWorldPath(FolderName.ROOT).getParent().getFileName().toString(): "";/////////

            syncMessage = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

            syncMessage.setMaterialFlags(MaterialFlagRegistry.getAllMaterialFlags());
            syncMessage.setMaterials(MaterialRegistry.getMaterials());

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
    @OnlyIn(Dist.CLIENT)
    public static void onPlayerJoinServer(ClientPlayerNetworkEvent.LoggedInEvent event) {
        if (event.getPlayer() != null) {
            if (IndustrialTech.configSync.syncedJson.containsValue(false)) {
                if (Minecraft.getInstance().level != null && Minecraft.getInstance().getCurrentServer() != null && Minecraft.getInstance().screen != null) {
                    //MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
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
                                IndustrialTech.configSync.materialFlagServer.syncClientMaterialFlagsConfigsWithSinglePlayerWorlds("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                IndustrialTech.configSync.materialServer.syncClientMaterialConfigsWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);

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

