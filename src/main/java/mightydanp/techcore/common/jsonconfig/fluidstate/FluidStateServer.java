package mightydanp.techcore.common.jsonconfig.fluidstate;

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
 * Created by MightyDanp on 1/25/2022.
 */
public class FluidStateServer extends JsonConfigServer<IFluidState> {

    @Override
    public Map<String, IFluidState> getServerMapFromList(List<IFluidState> fluidStatesIn) {
        Map<String, IFluidState> fluidStatesList = new LinkedHashMap<>();
        fluidStatesIn.forEach(fluidState -> fluidStatesList.put(fluidState.getName(), fluidState));

        return fluidStatesList;
    }

    @Override
    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        List<IFluidState> list = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(IFluidState.class::isInstance)
                .map(IFluidState.class::cast).toList();

        AtomicBoolean sync = new AtomicBoolean(true);


        if(list.size() != getServerMap().size()){
            if(getServerMap().size() > 0) {
                sync.set(false);
                ConfigSync.syncedJson.put("fluid_state", sync.get());
                return false;
            }
        }

        getServerMap().forEach((name, fluidState) -> {
            sync.set(list.stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IFluidState> optional = list.stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IFluidState serverFluidState = optional.get();
                    JsonObject jsonMaterial = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(fluidState);
                    JsonObject materialJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(serverFluidState);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        ConfigSync.syncedJson.put("fluid_state", sync.get());

        return sync.get();
    }

    @Override
    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IFluidState> clientFluidStates = new HashMap<>();

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
                    IFluidState fluidState = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).getFromJsonObject(jsonObject);
                    clientFluidStates.put(fluidState.getName(), fluidState);
                }

                getServerMap().values().forEach(serverFluidState -> {
                    sync.set(clientFluidStates.containsKey(serverFluidState.getName()));

                    if(sync.get()) {
                        IFluidState clientFluidState = getServerMap().get(serverFluidState.getName());
                        JsonObject jsonMaterial = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(serverFluidState);
                        JsonObject materialJson = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(clientFluidState);

                        sync.set(materialJson.equals(jsonMaterial));
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

        for (IFluidState fluidState : getServerMap().values()) {
            String name = fluidState.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).toJsonObject(fluidState);
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
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/fluid_state");
        Path configFolder = Paths.get(TechAscension.mainJsonConfig.getFolderLocation()  + "/fluid_state");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = TCJsonConfigs.fluidState.getFirst().getJsonObject(file.getName());
                    IFluidState fluidState = ((FluidStateRegistry) TCJsonConfigs.fluidState.getFirst()).getFromJsonObject(jsonObject);

                    String name = fluidState.getName();

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
        List<IFluidState> list = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(IFluidState.class::isInstance)
                .map(IFluidState.class::cast).toList();

        Map<String, IFluidState> fluidStates = list.stream()
                .collect(Collectors.toMap(IFluidState::getName, s -> s));

        serverMap.clear();
        serverMap.putAll(fluidStates);

        TechAscension.LOGGER.info("Loaded {} fluid states from the server", fluidStates.size());
    }

    @Override
    public void singleToBuffer(FriendlyByteBuf buffer, IFluidState fluidState) {
        buffer.writeUtf(fluidState.getName());
    }

    @Override
    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {
        List<IFluidState> list = message.getConfig(TCJsonConfigs.fluidStateID).stream()
                .filter(IFluidState.class::isInstance)
                .map(IFluidState.class::cast).toList();

        buffer.writeVarInt(list.size());

        list.forEach((fluidState) -> {
            singleToBuffer(buffer, fluidState);
        });
    }

    @Override
    public IFluidState singleFromBuffer(FriendlyByteBuf buffer) {
        String name = buffer.readUtf();

        return () -> name;
    }

    @Override
    public List<IFluidState> multipleFromBuffer(FriendlyByteBuf buffer) {
        List<IFluidState> fluidStates = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IFluidState fluidState = singleFromBuffer(buffer);

            fluidStates.add(fluidState);
        }

        return fluidStates;
    }

}