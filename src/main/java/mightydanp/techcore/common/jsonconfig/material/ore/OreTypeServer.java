package mightydanp.techcore.common.jsonconfig.material.ore;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import net.minecraft.network.FriendlyByteBuf;

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
 * Created by MightyDanp on 1/25/2022.
 */
public class OreTypeServer extends JsonConfigServer<OreTypeCodec> {

    @Override
    public Map<String, OreTypeCodec> getServerMapFromList(List<OreTypeCodec> oreTypesIn) {
        Map<String, OreTypeCodec> codecMap = new LinkedHashMap<>();
        oreTypesIn.forEach(oreType -> codecMap.put(oreType.name(), oreType));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<OreTypeCodec> clientList = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                .filter(OreTypeCodec.class::isInstance)
                .map(OreTypeCodec.class::cast).toList();

        if(message.getConfig(TCJsonConfigs.oreTypeID).size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(OreTypeCodec.codecName, sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverCodec) -> {

            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<OreTypeCodec> optional = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(optional.isPresent()) {
                    OreTypeCodec clientCodec = optional.get();
                    JsonObject serverJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(OreTypeCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, OreTypeCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + OreTypeCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(OreTypeCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.oreType.getFirst().getJsonObject(file.getName());
                    OreTypeCodec codec = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(codec.name(), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(serverCodec.name()));

                    if(sync.get()) {
                        OreTypeCodec clientCodec = getServerMap().get(serverCodec.name());
                        JsonObject serverJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put(OreTypeCodec.codecName, sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put(OreTypeCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + OreTypeCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (OreTypeCodec oreType : getServerMap().values()) {
            String name = oreType.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).toJsonObject(oreType);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    @Override
    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + OreTypeCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + OreTypeCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.oreType.getFirst().getJsonObject(file.getName());
                    OreTypeCodec client = ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).fromJsonObject(jsonObject);

                    String name = client.name();

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
        List<OreTypeCodec> list = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                .filter(OreTypeCodec.class::isInstance)
                .map(OreTypeCodec.class::cast).toList();

        Map<String, OreTypeCodec> map = list.stream()
                .collect(Collectors.toMap(OreTypeCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(map);

        TechAscension.LOGGER.info("Loaded {} " + OreTypeCodec.codecName + " from the server", map.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, OreTypeCodec oreType) {//friendlybotbuff
        buffer.writeUtf(oreType.name());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<OreTypeCodec> list = message.getConfig(TCJsonConfigs.oreTypeID).stream()
                .filter(OreTypeCodec.class::isInstance)
                .map(OreTypeCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public OreTypeCodec singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return null;
    }

    @Override
    public List<OreTypeCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<OreTypeCodec> codecs = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            OreTypeCodec codec = singleFromBuffer(buffer);

            codecs.add(codec);
        }

        return codecs;
    }

}