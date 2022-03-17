package mightydanp.industrialtech.api.common.jsonconfig.stonelayer;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.industrialtech.api.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
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

import static java.util.stream.Collectors.toList;

/**
 * Created by MightyDanp on 1/26/2022.
 */
public class StoneLayerServer extends JsonConfigServer<IStoneLayer> {

    @Override
    public Map<String, IStoneLayer> getServerMapFromList(List<IStoneLayer> stoneLayersIn) {
        Map<String, IStoneLayer> stoneLayersList = new LinkedHashMap<>();
        stoneLayersIn.forEach(stoneLayer -> stoneLayersList.put(stoneLayer.getBlock().split(":")[1], stoneLayer));

        return stoneLayersList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<IStoneLayer> list = message.getConfig(IndustrialTech.configSync.stoneLayerID).stream()
                .filter(IStoneLayer.class::isInstance)
                .map(IStoneLayer.class::cast)
                .collect(toList());
        
        if(list.size() != getServerMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());
            return false;
        }

        getServerMap().forEach((name, stoneLayer) -> {
            sync.set(list.stream().anyMatch(o -> o.getBlock().split(":")[1].equals(name)));

            if(sync.get()) {
                Optional<IStoneLayer> optional = list.stream().filter(o -> o.getBlock().split(":")[1].equals(name)).findFirst();

                if(optional.isPresent()) {
                    IStoneLayer serverStoneLayer = optional.get();
                    JsonObject jsonMaterial = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).toJsonObject(stoneLayer);
                    JsonObject materialJson =((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).toJsonObject(serverStoneLayer);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IStoneLayer> clientStoneLayers = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/stone_layer");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
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
                JsonObject jsonObject = IndustrialTech.configSync.stoneLayer.getFirst().getJsonObject(file.getName());
                IStoneLayer stoneLayer = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).getFromJsonObject(jsonObject);
                clientStoneLayers.put(stoneLayer.getBlock().split(":")[1], stoneLayer);
            }

            getServerMap().values().forEach(serverStoneLayer -> {
                sync.set(clientStoneLayers.containsKey(serverStoneLayer.getBlock().split(":")[1]));

                if(sync.get()) {
                    IStoneLayer clientStoneLayer = getServerMap().get(serverStoneLayer.getBlock().split(":")[1]);
                    JsonObject jsonMaterial = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).toJsonObject(serverStoneLayer);
                    JsonObject materialJson = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).toJsonObject(clientStoneLayer);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("stone_layer", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/stone_layer");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getName()));
            }
        }

        for (IStoneLayer stoneLayer : getServerMap().values()) {
            String name = stoneLayer.getBlock().split(":")[1];
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).toJsonObject(stoneLayer);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/stone_layer");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/stone_layer");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = IndustrialTech.configSync.stoneLayer.getFirst().getJsonObject(file.getName());
                    IStoneLayer stoneLayer = ((StoneLayerRegistry)IndustrialTech.configSync.stoneLayer.getFirst()).getFromJsonObject(jsonObject);

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

    @Override
    public void loadFromServer(SyncMessage message) {
        List<IStoneLayer> list = message.getConfig(IndustrialTech.configSync.stoneLayerID).stream()
                .filter(IStoneLayer.class::isInstance)
                .map(IStoneLayer.class::cast)
                .collect(toList());

        Map<String, IStoneLayer> stoneLayers = list.stream()
                .collect(Collectors.toMap(s -> s.getBlock().split(":")[1], s -> s));

        serverMap.clear();
        serverMap.putAll(stoneLayers);

        IndustrialTech.LOGGER.info("Loaded {} stone layers from the server", stoneLayers.size());
    }

    @Override
    public void singleToBuffer(PacketBuffer buffer, IStoneLayer stoneLayer) {//friendlybotbuff
        buffer.writeUtf(stoneLayer.getBlock());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        List<IStoneLayer> list = message.getConfig(IndustrialTech.configSync.stoneLayerID).stream()
                .filter(IStoneLayer.class::isInstance)
                .map(IStoneLayer.class::cast)
                .collect(toList());

        buffer.writeVarInt(list.size());

        list.forEach((stoneLayer) -> singleToBuffer(buffer, stoneLayer));
    }

    @Override
    public IStoneLayer singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return () -> name;
    }

    @Override
    public List<IStoneLayer> multipleFromBuffer(PacketBuffer buffer) {
        List<IStoneLayer> stoneLayers = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IStoneLayer stoneLayer = singleFromBuffer(buffer);

            stoneLayers.add(stoneLayer);
        }

        return stoneLayers;
    }

}