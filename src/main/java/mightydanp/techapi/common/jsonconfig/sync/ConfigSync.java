package mightydanp.techapi.common.jsonconfig.sync;

import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.sync.gui.screen.SyncScreen;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.NetworkHandler;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * Created by MightyDanp on 1/4/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ConfigSync {
    public static Map<String, Boolean> syncedJson = new HashMap<>();
    public static Map<Integer, Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>> configs = new HashMap<>();

    public static boolean isSinglePlayer;
    public static String singlePlayerWorldName;
    public static String serverIP;

    public static void init(){
        for(int i = 0; i < configs.size(); i++){
            Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = configs.get(i);

            config.getFirst().initiate();
            configs.put(i, config);
        }
    }

    public static void initClient(){
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
            String singlePlayerWorldName = isSinglePlayer ? event.getPlayer().getServer().getWorldPath(LevelResource.ROOT).getParent().getFileName().toString(): "";/////////

            SyncMessage syncMessage = new SyncMessage(isSinglePlayer, singlePlayerWorldName);

            for(int i = 0; i < configs.size(); i++){
                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = configs.get(i);
                syncMessage.setConfig(i, config.getFirst().getAllValues());
            }

            ServerPlayer player = (ServerPlayer) event.getPlayer();

            if (player != null) {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), syncMessage);
            } else {
                NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), syncMessage);
            }
            /*
            if(TechAscension.configSync.syncedJson.containsValue(false)){
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
            if (syncedJson.containsValue(false)) {
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
                if (!TechAscension.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(FolderName.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id  + "/" + Ref.mod_id)) {
                    server.close();
                }
            }
        }
    }

     */

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onDisconnect(ScreenOpenEvent event) {
        SyncScreen screen = new SyncScreen();

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if(event.getScreen() != null) {
            TechAscension.LOGGER.debug(event.getScreen());
            TechAscension.LOGGER.debug(event.getScreen().getTitle().getString());

            if (server != null) {
                if (server.isSingleplayer() && Minecraft.getInstance().getSingleplayerServer() != null) {
                        IntegratedServer integratedServer = Minecraft.getInstance().getSingleplayerServer();

                    if (event.getScreen() instanceof ProgressScreen) {
                        if (!(new File("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id).exists()) && !TechAscension.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig"  + "/" + Ref.mod_id)) {
                            for(int i = 0; i < configs.size(); i++){
                                Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> config = configs.get(i);
                                try {
                                    config.getSecond().syncClientWithSinglePlayerWorld("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            TechAscension.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                            TechAscension.mainJsonConfig.reloadConfigJson();
                        }
                    }

                    if (event.getScreen() instanceof LevelLoadingScreen) {
                        if((new File("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id).exists())) {
                            if (!TechAscension.mainJsonConfig.getFolderLocation().equals("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id)) {
                                TechAscension.mainJsonConfig.setFolderLocation("saves/" + server.getWorldPath(LevelResource.ROOT).getParent().getFileName().toString() + "/serverconfig/" + Ref.mod_id);
                                TechAscension.mainJsonConfig.reloadConfigJson();
                                Minecraft.getInstance().close();
                            }
                        }
                    }
                }
            }

            if (event.getScreen() instanceof DisconnectedScreen) {
                if (syncedJson.containsValue(false)) {
                    event.setScreen(screen);
                }
            }
        }
    }






}

