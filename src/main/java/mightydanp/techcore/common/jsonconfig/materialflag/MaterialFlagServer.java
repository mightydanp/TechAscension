package mightydanp.techcore.common.jsonconfig.materialflag;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.libs.Ref;
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
 * Created by MightyDanp on 1/23/2022.
 */
public class MaterialFlagServer extends JsonConfigServer<MaterialFlagCodec> {
    @Override
    public Map<String, MaterialFlagCodec> getServerMapFromList(List<MaterialFlagCodec> materialFlagsIn) {
        Map<String, MaterialFlagCodec> codecMap = new LinkedHashMap<>();
        materialFlagsIn.forEach(materialFlag -> codecMap.put(fixesToName(materialFlag.prefix(), materialFlag.suffix()), materialFlag));

        return codecMap;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        List<MaterialFlagCodec> clientList = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(MaterialFlagCodec.class::isInstance)
                .map(MaterialFlagCodec.class::cast).toList();

        if(clientList.size() != getServerMap().size()){
            sync.set(false);
            ConfigSync.syncedJson.put(MaterialFlagCodec.codecName, sync.get());
            return false;
        }

        getServerMap().forEach((name, serverCodec) -> {
            sync.set(clientList.stream().anyMatch(o -> fixesToName(o.prefix(), o.suffix()).equals(name)));

            if(sync.get()) {
                Optional<MaterialFlagCodec> optionalClientCodec = clientList.stream().filter(o -> fixesToName(o.prefix(), o.suffix()).equals(name)).findFirst();

                if(optionalClientCodec.isPresent()) {
                    MaterialFlagCodec clientCodec = optionalClientCodec.get();
                    JsonObject serverJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(serverCodec);
                    JsonObject clientJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(clientCodec);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put(MaterialFlagCodec.codecName, sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, MaterialFlagCodec> clientMap = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/" + MaterialFlagCodec.codecName);
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put(MaterialFlagCodec.codecName, sync.get());
                return false;
            }

            if(files.length > 0){

                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.materialFlag.getFirst().getJsonObject(file.getName());
                    MaterialFlagCodec codec = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).fromJsonObject(jsonObject);
                    clientMap.put(fixesToName(codec.prefix(), codec.suffix()), codec);
                }

                getServerMap().values().forEach(serverCodec -> {
                    sync.set(clientMap.containsKey(fixesToName(serverCodec.prefix(), serverCodec.suffix())));

                    if(sync.get()) {
                        MaterialFlagCodec clientCodec = getServerMap().get(fixesToName(serverCodec.prefix(), serverCodec.suffix()));
                        JsonObject serverJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(serverCodec);
                        JsonObject clientJson = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(clientCodec);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            sync.set(false);
            ConfigSync.syncedJson.put(MaterialFlagCodec.codecName, sync.get());
            return false;
        }

        ConfigSync.syncedJson.put(MaterialFlagCodec.codecName, sync.get());

        return sync.get();
    }


    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/" + MaterialFlagCodec.codecName);

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (MaterialFlagCodec serverCodec : getServerMap().values()) {
            String name = fixesToName(serverCodec.prefix(), serverCodec.suffix());
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).toJsonObject(serverCodec);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/" + MaterialFlagCodec.codecName);
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/" + MaterialFlagCodec.codecName);

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.materialFlag.getFirst().getJsonObject(file.getName());
                    MaterialFlagCodec codec = ((MaterialFlagRegistry) TCJsonConfigs.materialFlag.getFirst()).fromJsonObject(jsonObject);

                    String name = fixesToName(codec.prefix(), codec.suffix());

                    Path filePath = Paths.get(singlePlayerSaveConfigFolder + "/" + name + ".json");
                    if (!Files.exists(filePath)) {
                        Files.createDirectories(filePath.getParent());

                        try (BufferedWriter bufferedwriter = Files.newBufferedWriter(filePath)) {
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
        List<MaterialFlagCodec> list = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(MaterialFlagCodec.class::isInstance)
                .map(MaterialFlagCodec.class::cast).toList();

        Map<String, MaterialFlagCodec> codecMap = list.stream()
                .collect(Collectors.toMap(s -> fixesToName(s.prefix(), s.suffix()), s -> s));

        getServerMap().clear();
        getServerMap().putAll(codecMap);

        TechAscension.LOGGER.info("Loaded {} " + MaterialFlagCodec.codecName + " from the server", codecMap.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, MaterialFlagCodec codec) {
        buffer.writeWithCodec(MaterialFlagCodec.CODEC, codec);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<MaterialFlagCodec> list = message.getConfig(TCJsonConfigs.materialFlagID).stream()
                .filter(MaterialFlagCodec.class::isInstance)
                .map(MaterialFlagCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((codec) -> singleToBuffer(buffer, codec));
    }

    @Override
    public MaterialFlagCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(MaterialFlagCodec.CODEC);
    }

    @Override
    public List<MaterialFlagCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<MaterialFlagCodec> materialFlags = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            MaterialFlagCodec materialFlag = singleFromBuffer(buffer);

            materialFlags.add(materialFlag);
        }

        return materialFlags;
    }

}
