package mightydanp.techcore.common.jsonconfig.fluidstate;

import com.google.gson.JsonObject;
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
public class FluidStateRegistry extends JsonConfigMultiFile<IFluidState> {
    @Override
    public void initiate() {
        setJsonFolderName("fluid_state");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultFluidState fluidState : DefaultFluidState.values()) {
            register(fluidState);
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }
    @Override
    public List<IFluidState> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    public IFluidState getFluidStateByName(String fluid_state) {
        return registryMap.get(fluid_state);
    }

    @Override
    public void register(IFluidState fluidStateIn) {
        String name = fluidStateIn.getName();
        if (registryMap.containsKey(fluidStateIn.getName()))
            throw new IllegalArgumentException("fluid state with name(" + name + "), already exists.");
        registryMap.put(name, fluidStateIn);
    }
    public void buildJson(){
        for(IFluidState fluidState : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(fluidState.getName());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(fluidState.getName(), toJsonObject(fluidState));
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
                        IFluidState fluidState = fromJsonObject(jsonObject);

                        registryMap.put(fluidState.getName(), fluidState);

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
    public IFluidState fromJsonObject(JsonObject jsonObjectIn){
        return new IFluidState() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }
        };
    }

    public JsonObject toJsonObject(IFluidState fluidState) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", fluidState.getName());

        return jsonObject;
    }
}