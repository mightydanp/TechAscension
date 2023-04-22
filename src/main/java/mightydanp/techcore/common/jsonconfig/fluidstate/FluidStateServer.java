package mightydanp.techcore.common.jsonconfig.fluidstate;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techapi.common.jsonconfig.sync.network.message.SyncMessage;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.definedstructure.DefinedStructureCodec;
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
 * Created by MightyDanp on 1/25/2022.
 */
public class FluidStateServer extends JsonConfigServer<FluidStateCodec> {

    @Override
    public Map<String, FluidStateCodec> getServerMapFromList(List<FluidStateCodec> fluidStatesIn) {
        Map<String, FluidStateCodec> fluidStatesList = new LinkedHashMap<>();
        fluidStatesIn.forEach(fluidState -> fluidStatesList.put(fluidState.name(), fluidState));

        return fluidStatesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        List<FluidStateCodec> clientList = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(FluidStateCodec.class::isInstance)
                .map(FluidStateCodec.class::cast).toList();

        AtomicBoolean sync = new AtomicBoolean(true);


        if(clientList.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("fluid_state", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, serverFluidState) -> {
            sync.set(clientList.stream().anyMatch(o -> o.name().equals(name)));

            if(sync.get()) {
                Optional<FluidStateCodec> client = clientList.stream().filter(o -> o.name().equals(name)).findFirst();

                if(client.isPresent()) {
                    FluidStateCodec clientFluidState = client.get();
                    JsonObject serverJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(serverFluidState);
                    JsonObject clientJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(clientFluidState);

                    sync.set(clientJson.equals(serverJson));
                }
            }
        });

        ConfigSync.syncedJson.put("fluid_state", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, FluidStateCodec> clientFluidStates = new HashMap<>();

        Path configs = Paths.get(singlePlayerConfigs + "/fluid_state");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerMap().size() != files.length){
                sync.set(false);
                ConfigSync.syncedJson.put("fluid_state", sync.get());
                return false;
            }

            if(files.length > 0){
                for(File file : files){
                    JsonObject jsonObject = TCJsonConfigs.fluidState.getFirst().getJsonObject(file.getName());
                    FluidStateCodec fluidState = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).fromJsonObject(jsonObject);
                    clientFluidStates.put(fluidState.name(), fluidState);
                }

                getServerMap().values().forEach(serverFluidState -> {
                    sync.set(clientFluidStates.containsKey(serverFluidState.name()));

                    if(sync.get()) {
                        FluidStateCodec clientFluidState = getServerMap().get(serverFluidState.name());
                        JsonObject serverJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(serverFluidState);
                        JsonObject clientJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(clientFluidState);

                        sync.set(clientJson.equals(serverJson));
                    }

                });
            }

        }else{
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("fluid_state", sync.get());
                return false;
            }
        }

        ConfigSync.syncedJson.put("fluid_state", sync.get());

        return sync.get();
    }

    @Override
    public void syncClientWithServer(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/fluid_state");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (FluidStateCodec fluidState : getServerMap().values()) {
            String name = fluidState.name();
            Path filePath = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(fluidState);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/fluid_state");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/fluid_state");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.fluidState.getFirst().getJsonObject(file.getName());
                    FluidStateCodec fluidState = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).fromJsonObject(jsonObject);

                    String name = fluidState.name();

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
        List<FluidStateCodec> list = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(FluidStateCodec.class::isInstance)
                .map(FluidStateCodec.class::cast).toList();

        Map<String, FluidStateCodec> fluidStates = list.stream()
                .collect(Collectors.toMap(FluidStateCodec::name, s -> s));

        serverMap.clear();
        serverMap.putAll(fluidStates);

        TechAscension.LOGGER.info("Loaded {} fluid states from the server", fluidStates.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, FluidStateCodec fluidState) {
        buffer.writeWithCodec(FluidStateCodec.CODEC, fluidState);
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<FluidStateCodec> list = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(FluidStateCodec.class::isInstance)
                .map(FluidStateCodec.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((fluidState) -> {
            singleToBuffer(buffer, fluidState);
        });
    }

    @Override
    public FluidStateCodec singleFromBuffer(FriendlyByteBuf buffer) {
        return buffer.readWithCodec(FluidStateCodec.CODEC);
    }

    @Override
    public List<FluidStateCodec> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<FluidStateCodec> fluidStates = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            FluidStateCodec fluidState = singleFromBuffer(buffer);

            fluidStates.add(fluidState);
        }

        return fluidStates;
    }

}