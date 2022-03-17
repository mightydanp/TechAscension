package mightydanp.industrialtech.api.common.jsonconfig.fluidstate;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.crash.CrashReport;

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
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

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
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", fluidState.getName());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("fluid_state", materialFlagJson);
                    }
                }
                this.saveJsonObject(fluidState.getName(), jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(getFromJsonObject(jsonObject))) {
                        IFluidState fluidState = getFromJsonObject(jsonObject);

                        registryMap.put(fluidState.getName(), fluidState);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to fluid state list because a fluid state already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("fluid state json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IFluidState getFromJsonObject(JsonObject jsonObjectIn){
        JsonObject fluidStateJson = jsonObjectIn.getAsJsonObject("fluid_state");

        String name = fluidStateJson.get("name").getAsString();

        return () -> name;
    }

    public JsonObject toJsonObject(IFluidState fluidState) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", fluidState.getName());

        if (json.size() > 0) {
            jsonObject.add("fluid_state", json);
        }

        return jsonObject;
    }
}