package mightydanp.techcore.common.jsonconfig.fluidstate;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class FluidStateRegistry extends JsonConfigMultiFile<FluidStateCodec> {
    @Override
    public void initiate() {
        setJsonFolderName(FluidStateCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        for (DefaultFluidState fluidState : DefaultFluidState.values()) {
            register(fluidState.fluidState);
        }

        buildJson();
        loadExistJson();
        super.initiate();
    }
    @Override
    public List<FluidStateCodec> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    public FluidStateCodec getByName(String name) {
        return registryMap.get(name);
    }

    @Override
    public void register(FluidStateCodec codec) {
        String name = codec.name();
        if (registryMap.containsKey(codec.name()))
            throw new IllegalArgumentException(FluidStateCodec.codecName + " with name(" + name + "), already exists.");
        registryMap.put(name, codec);
    }
    public void buildJson(){
        for(FluidStateCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        FluidStateCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to fluid state list because a " + FluidStateCodec.codecName + " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(FluidStateCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public FluidStateCodec fromJsonObject(JsonObject jsonObjectIn){
        return FluidStateCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + FluidStateCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(FluidStateCodec codec) {
        return FluidStateCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + FluidStateCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}