package mightydanp.industrialtech.api.common.jsonconfig.fluidstate;

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
 * Created by MightyDanp on 1/25/2022.
 */
public class FluidStateServer {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private static final Map<String, IFluidState> serverFluidStatesMap = new HashMap<>();

    FluidStateRegistry fluidStateRegistry = IndustrialTech.fluidStateRegistry;

    public Map<String, IFluidState> getServerFluidStatesMap(){
        return serverFluidStatesMap;
    }

    public boolean serverHasFluidStates(){
        return serverFluidStatesMap.size() > 0;
    }

    public static Map<String, IFluidState> getServerFluidStatesMap(List<IFluidState> fluidStatesIn) {
        Map<String, IFluidState> fluidStatesList = new LinkedHashMap<>();
        fluidStatesIn.forEach(fluidState -> fluidStatesList.put(fluidState.getName(), fluidState));

        return fluidStatesList;
    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        AtomicBoolean sync = new AtomicBoolean(true);

        if(message.getFluidStates().size() != getServerFluidStatesMap().size()){
            sync.set(false);
            IndustrialTech.configSync.syncedJson.put("fluid_state", sync.get());
            return false;
        }

        getServerFluidStatesMap().forEach((name, fluidState) -> {
            sync.set(message.getFluidStates().stream().anyMatch(o -> o.getName().equals(name)));

            if(sync.get()) {
                Optional<IFluidState> optional = message.getFluidStates().stream().filter(o -> o.getName().equals(name)).findFirst();

                if(optional.isPresent()) {
                    IFluidState serverFluidState = optional.get();
                    JsonObject jsonMaterial = IndustrialTech.fluidStateRegistry.toJsonObject(fluidState);
                    JsonObject materialJson = IndustrialTech.fluidStateRegistry.toJsonObject(serverFluidState);

                    sync.set(materialJson.equals(jsonMaterial));
                }
            }
        });

        sync.set(false);

        IndustrialTech.configSync.syncedJson.put("fluid_state", sync.get());

        return sync.get();
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        AtomicBoolean sync = new AtomicBoolean(true);
        Map<String, IFluidState> clientFluidStates = new HashMap<>();

        ConfigSync configSync = IndustrialTech.configSync;

        Path configs = Paths.get(singlePlayerConfigs + "/fluid_state");
        File[] files = configs.toFile().listFiles();

        if(files != null){
            if(getServerFluidStatesMap().size() != files.length){
                sync.set(false);
                configSync.syncedJson.put("fluid_state", sync.get());
                return false;
            }
        }else{
            sync.set(false);
            configSync.syncedJson.put("fluid_state", sync.get());
            return false;
        }

        if(files.length > 0){

            for(File file : files){
                JsonObject jsonObject = fluidStateRegistry.getJsonObject(file.getAbsolutePath());
                IFluidState fluidState = fluidStateRegistry.getFluidState(jsonObject);
                clientFluidStates.put(fluidState.getName(), fluidState);
            }

            getServerFluidStatesMap().values().forEach(serverFluidState -> {
                sync.set(clientFluidStates.containsKey(serverFluidState.getName()));

                if(sync.get()) {
                    IFluidState clientFluidState = getServerFluidStatesMap().get(serverFluidState.getName());
                    JsonObject jsonMaterial = fluidStateRegistry.toJsonObject(serverFluidState);
                    JsonObject materialJson = fluidStateRegistry.toJsonObject(clientFluidState);

                    sync.set(materialJson.equals(jsonMaterial));
                }

            });
        }

        IndustrialTech.configSync.syncedJson.put("fluid_state", sync.get());

        return sync.get();
    }

    public void syncClientFluidStatesWithServers(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server" + "/fluid_state");

        if(serverConfigFolder.toFile().listFiles() != null) {
            for (File file : Objects.requireNonNull(serverConfigFolder.toFile().listFiles())) {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            }
        }

        for (IFluidState fluidState : getServerFluidStatesMap().values()) {
            String name = fluidState.getName();
            Path materialFile = Paths.get(serverConfigFolder + "/" + name + ".json");
            JsonObject jsonObject = fluidStateRegistry.toJsonObject(fluidState);
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(materialFile)) {
                Files.createDirectories(materialFile.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(materialFile)) {
                    bufferedwriter.write(s);
                }
            }
        }
    }

    public void syncClientFluidStatesConfigsWithSinglePlayerWorlds(String folderName) throws IOException {
        //Path serverConfigFolder = Paths.get("config/" + Ref.mod_id + "/server/" + folderName + "/material");
        Path singlePlayerSaveConfigFolder = Paths.get(folderName + "/fluid_state");
        Path configFolder = Paths.get(IndustrialTech.mainJsonConfig.getFolderLocation()  + "/fluid_state");

        if(singlePlayerSaveConfigFolder.toFile().listFiles() == null) {
            if(configFolder.toFile().listFiles() != null){
                for (File file : Objects.requireNonNull(configFolder.toFile().listFiles())) {
                    JsonObject jsonObject = fluidStateRegistry.getJsonObject(file.getName());
                    IFluidState fluidState = fluidStateRegistry.getFluidState(jsonObject);

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

    public void loadFluidStates(SyncMessage message) {
        Map<String, IFluidState> fluidStates = message.getFluidStates().stream()
                .collect(Collectors.toMap(s -> s.getName(), s -> s));

        serverFluidStatesMap.clear();
        serverFluidStatesMap.putAll(fluidStates);

        IndustrialTech.LOGGER.info("Loaded {} material flags from the server", fluidStates.size());
    }

    public static void singleToBuffer(PacketBuffer buffer, IFluidState fluidState) {//friendlybotbuff
        buffer.writeUtf(fluidState.getName());
    }

    public static void multipleToBuffer(SyncMessage message, PacketBuffer buffer) {
        buffer.writeVarInt(message.getFluidStates().size());

        message.getFluidStates().forEach((fluidState) -> {
            singleToBuffer(buffer, fluidState);
        });
    }

    public static IFluidState singleFromBuffer(PacketBuffer buffer) {
        String name = buffer.readUtf();

        return new IFluidState() {

            @Override
            public String getName() {
                return name;
            }
        };
    }

    public static List<IFluidState> multipleFromBuffer(PacketBuffer buffer) {
        List<IFluidState> fluidStates = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            IFluidState material = singleFromBuffer(buffer);

            fluidStates.add(material);
        }

        return fluidStates;
    }

}