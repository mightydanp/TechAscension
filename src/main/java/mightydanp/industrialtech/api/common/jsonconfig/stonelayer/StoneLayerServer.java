package mightydanp.industrialtech.api.common.jsonconfig.stonelayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.network.PacketBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/26/2022.
 */
public class StoneLayerServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, IStoneLayer> serverStoneLayersMap = new HashMap<>();

    StoneLayerRegistry stoneLayerRegistry = IndustrialTech.stoneLayerRegistry;

    public Map<String, IStoneLayer> getServerStoneLayersMap(){
        return serverStoneLayersMap;
    }

    public boolean serverHasStoneLayers(){
        return serverStoneLayersMap.size() > 0;
    }

    public static Map<String, IStoneLayer> getServerStoneLayersMap(List<IStoneLayer> stoneLayersIn) {
        Map<String, IStoneLayer> stoneLayersList = new LinkedHashMap<>();
        stoneLayersIn.forEach(stoneLayer -> stoneLayersList.put(stoneLayer.getBlock().split(":")[1], stoneLayer));

        return stoneLayersList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getStoneLayers().size() != getServerStoneLayersMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());
            return false;
        }

        getServerStoneLayersMap().forEach((name, stoneLayer) -> {
            sync.set(message.getStoneLayers().stream().anyMatch(o -> o.getBlock().split(":")[1].equals(name)));

            if(sync.get()) {
                Optional<IStoneLayer> optional = message.getStoneLayers().stream().filter(o -> o.getBlock().split(":")[1].equals(name)).findFirst();

                if(optional.isPresent()) {
                    IStoneLayer serverStoneLayer = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.stoneLayerRegistry.toJsonObject(stoneLayer);
                    JsonObject materialJson = IndustrialTech.stoneLayerRegistry.toJsonObject(serverStoneLayer);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IStoneLayer> clientStoneLayers = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/stone_layer");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerStoneLayersMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("stone_layer", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("stone_layer", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = stoneLayerRegistry.getJsonObject(file.getAbsolutePath());
                IStoneLayer stoneLayer = stoneLayerRegistry.getStoneLayer(jsonObject);
                clientStoneLayers.put(stoneLayer.getBlock().split(":")[1], stoneLayer);
            }

            getServerStoneLayersMap().values().forEach(serverStoneLayer -> {
                sync.set(clientStoneLayers.containsKey(serverStoneLayer.getBlock().split(":")[1]));

                if(sync.get()) {
                    IStoneLayer clientStoneLayer = getServerStoneLayersMap().get(serverStoneLayer.getBlock().split(":")[1]);
                    JsonObject jsonMaterial = stoneLayerRegistry.toJsonObject(serverStoneLayer);
                    JsonObject materialJson = stoneLayerRegistry.toJsonObject(clientStoneLayer);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());

        return sync.get();
    }

    public void syncClientStoneLayersWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/stone_layer");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IStoneLayer stoneLayer : getServerStoneLayersMap().values()) {
            String name = stoneLayer.getBlock().split(":")[1];
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = stoneLayerRegistry.toJsonObject(stoneLayer);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientStoneLayersConfigsWithSinglePlayerWorlds(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/stone_layer");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/stone_layer");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = stoneLayerRegistry.getJsonObject(file.getName());
                    IStoneLayer stoneLayer = stoneLayerRegistry.getStoneLayer(jsonObject);

                    String name = stoneLayer.getBlock().split(":")[1];

                    Path materialFile = Paths.get(singlePlayerSaveConfigFolder + "/" + name + ".json");
                    if (!Files.exists(materialFile)) {
                        Files.createDirectories(materialFile.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                            String s = GSON.toJson(jsonObject);
                            bufferedwriter.write(s);
                        }
                    }
                }
            }
        }
    }

    public void loadStoneLayers(SyncMessage message) {
        Map<String, IStoneLayer> stoneLayers = message.getStoneLayers().stream()
                .collect(Collectors.toMap(s -> s.getBlock().split(":")[1], s -> s));

        serverStoneLayersMap.clear();
        serverStoneLayersMap.putAll(stoneLayers);

        IndustrialTech.LOGGER.info("Loaded {} stone layers from the server", stoneLayers.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, IStoneLayer stoneLayer) {//friendlybotbuff
        buffer.writeUtf(stoneLayer.getBlock());
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getStoneLayers().size());

        message.getStoneLayers().forEach((stoneLayer) -> {
            singleToBuffer(buffer, stoneLayer);
        });
    }

    public static IStoneLayer singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return () -> name;
    }

    public static List<IStoneLayer> multipleFromBuffer(PacketBuffer buffer) {
        List<IStoneLayer> stoneLayers = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IStoneLayer stoneLayer = singleFromBuffer(buffer);

            stoneLayers.add(stoneLayer);
        }

        return stoneLayers;
    }

}