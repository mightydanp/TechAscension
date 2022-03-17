package mightydanp.industrialtech.api.common.jsonconfig.sync;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.NetworkHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
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
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.ore.OreTypeServer;
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
    public Map<String, Boolean> syncedJson = new HashMap<>();
    public Map<Integer, Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>> configs = new HashMap<>();
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> materialFlag = configs.getOrDefault(0, new Pair<>(new MaterialFlagRegistry(),  new MaterialFlagServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> fluidState = configs.getOrDefault(1, new Pair<>(new FluidStateRegistry(),  new FluidStateServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreType = configs.getOrDefault(2, new Pair<>(new OreTypeRegistry(),  new OreTypeServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  textureIcon = configs.getOrDefault(3, new Pair<>(new TextureIconRegistry(),  new TextureIconServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolPart = configs.getOrDefault(4, new Pair<>(new ToolPartRegistry(),  new ToolPartServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolType = configs.getOrDefault(5, new Pair<>(new ToolTypeRegistry(),  new ToolTypeServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  stoneLayer = configs.getOrDefault(6, new Pair<>(new StoneLayerRegistry(),  new StoneLayerServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  material = configs.getOrDefault(7, new Pair<>(new MaterialRegistry(),  new MaterialServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreVein = configs.getOrDefault(8, new Pair<>(new OreVeinRegistry(),  new OreVeinServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  smallOre = configs.getOrDefault(9, new Pair<>(new SmallOreVeinRegistry(),  new SmallOreVeinServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  blocksInWater = configs.getOrDefault(10, new Pair<>(new BlocksInWaterRegistry(),  new BlocksInWaterServer()));
    public Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  randomSurface = configs.getOrDefault(11, new Pair<>(new RandomSurfaceRegistry(),  new RandomSurfaceServer()));

    public boolean isSinglePlayer;
    public String singlePlayerWorldName;
    public static String serverIP;

    public void init(){
        configs.put(0, materialFlag);
        configs.put(1, fluidState);
        configs.put(2, oreType);
        configs.put(3, textureIcon);
        configs.put(4, toolPart);
        configs.put(5, toolType);
        configs.put(6, stoneLayer);
        configs.put(7, material);
        ModMaterials.commonInit();
        configs.put(8, oreVein);
        configs.put(9, smallOre);
        configs.put(10, blocksInWater);
        configs.put(11, randomSurface);


        for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
            Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);

            config.getFirst().initiate();
            configs.put(i, config);
        }
    }

    public void initClient(){
        configs.forEach((s, pair) -> {
            pair.getFirst().initiateClient();
            configs.put(s, pair);
        });
    }


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

            SyncMessage syncMessage = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

            syncMessage.setMaterialFlags(((MaterialFlagRegistry)IndustrialTech.configSync.materialFlag.getFirst()).getAllValues());
            syncMessage.setFluidStates(((FluidStateRegistry)IndustrialTech.configSync.fluidState.getFirst()).getAllValues());
            syncMessage.setOreTypes(((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).getAllValues());
            syncMessage.setTextureIcons(((TextureIconRegistry)IndustrialTech.configSync.textureIcon.getFirst()).getAllValues());
            syncMessage.setToolParts(((ToolPartRegistry)IndustrialTech.configSync.toolPart.getFirst()).getAllValues());
            syncMessage.setToolTypes(((ToolTypeRegistry)IndustrialTech.configSync.toolType.getFirst()).getAllValues());
            syncMessage.setStoneLayers(((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).getAllValues());
            //
            syncMessage.setMaterials(((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).getAllValues());

            syncMessage.setOreVeins(((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).getAllValues());
            syncMessage.setSmallOreVeins(((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).getAllValues());
            syncMessage.setBlocksInWater(((BlocksInWaterRegistry)IndustrialTech.configSync.blocksInWater.getFirst()).getAllValues());
            syncMessage.setRandomSurface(((RandomSurfaceRegistry)IndustrialTech.configSync.randomSurface.getFirst()).getAllValues());

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
                            for(int i = 0; i < IndustrialTech.configSync.configs.size(); i++){
                                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = IndustrialTech.configSync.configs.get(i);
                                try {
                                    config.getSecond().syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig" + "/" + Ref.mod_id);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }


                            IndustrialTech.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                            IndustrialTech.mainJsonConfig.reloadMainConfigJson();
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

