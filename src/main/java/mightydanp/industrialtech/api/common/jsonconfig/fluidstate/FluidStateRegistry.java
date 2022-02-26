package mightydanp.industrialtech.api.common.jsonconfig.fluidstate;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class FluidStateRegistry extends JsonConfigMultiFile {
    private static final Map<String, IFluidState> fluidStateList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("fluid_state");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultFluidState fluidState : DefaultFluidState.values()) {
            register(fluidState);
        }
        //

        buildFluidStateJson();
        loadExistJson();
        super.initiate();
    }

    public static List<IFluidState> getAllFluidStates() {
        return new ArrayList<>(fluidStateList.values());
    }

    public static void register(IFluidState fluidStateIn) {
        String name = fluidStateIn.getName();
        if (fluidStateList.containsKey(fluidStateIn.getName()))
            throw new IllegalArgumentException("fluid state with name(" + name + "), already exists.");
        fluidStateList.put(name, fluidStateIn);
    }

    public IFluidState getFluidStateByName(String fluid_state) {
        return fluidStateList.get(fluid_state);
    }

    public Set<IFluidState> getAllFluidState() {
        return new HashSet<IFluidState>(fluidStateList.values());
    }

    public void buildFluidStateJson(){
        for(IFluidState fluidState : fluidStateList.values()) {
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

                    if (!fluidStateList.containsValue(getFluidState(jsonObject))) {
                        IFluidState fluidState = getFluidState(jsonObject);

                        fluidStateList.put(fluidState.getName(), fluidState);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to fluid state list because a fluid state already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("fluid state json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IFluidState getFluidState(JsonObject jsonObjectIn){
        JsonObject fluidStateJson = jsonObjectIn.getAsJsonObject("fluid_state");

        String name = fluidStateJson.get("name").getAsString();

        return new IFluidState() {
            @Override
            public String getName() {
                return name;
            }
        };
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