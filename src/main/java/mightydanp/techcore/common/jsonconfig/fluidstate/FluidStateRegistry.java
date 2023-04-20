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
        setJsonFolderName("fluid_state");
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

    public FluidStateCodec getFluidStateByName(String name) {
        return registryMap.get(name);
    }

    @Override
    public void register(FluidStateCodec fluidStateIn) {
        String name = fluidStateIn.name();
        if (registryMap.containsKey(fluidStateIn.name()))
            throw new IllegalArgumentException("fluid state with name(" + name + "), already exists.");
        registryMap.put(name, fluidStateIn);
    }
    public void buildJson(){
        for(FluidStateCodec fluidState : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(fluidState.name());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(fluidState.name(), toJsonObject(fluidState));
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
                        FluidStateCodec fluidState = fromJsonObject(jsonObject);

                        registryMap.put(fluidState.name(), fluidState);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to fluid state list because a fluid state already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("fluid state json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public FluidStateCodec fromJsonObject(JsonObject jsonObjectIn){
        return FluidStateCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your fluid states, please fix this"))).getFirst();

    }

    public JsonObject toJsonObject(FluidStateCodec fluidState) {
        return FluidStateCodec.CODEC.encodeStart(JsonOps.INSTANCE, fluidState).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your fluid state with name [" + fluidState.name() + "], please fix this"))).getAsJsonObject();
    }
}