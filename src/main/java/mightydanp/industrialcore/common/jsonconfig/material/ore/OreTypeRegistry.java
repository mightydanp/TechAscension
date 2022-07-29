package mightydanp.industrialcore.common.jsonconfig.material.ore;

import com.google.gson.JsonObject;
import mightydanp.industrialapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class OreTypeRegistry extends JsonConfigMultiFile<IOreType> {

    @Override
    public void initiate() {
        setJsonFolderName("ore_type");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultOreType oreType : DefaultOreType.values()) {
            register(oreType);
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IOreType oreTypeIn) {
        String name = oreTypeIn.getName();
        if (registryMap.containsKey(oreTypeIn.getName()))
            throw new IllegalArgumentException("ore type with name(" + name + "), already exists.");
        registryMap.put(name, oreTypeIn);
    }

    public IOreType getByName(String ore_type) {
        return registryMap.get(ore_type);
    }

    public void buildJson(){
        for(IOreType oreType : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(oreType.getName());

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", oreType.getName());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("ore_type", materialFlagJson);
                    }
                }
                this.saveJsonObject(oreType.getName(), jsonObject);
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
                        IOreType oreType = getFromJsonObject(jsonObject);

                        registryMap.put(oreType.getName(), oreType);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to ore type list because a ore type already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("ore type json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IOreType getFromJsonObject(JsonObject jsonObjectIn){
        JsonObject oreTypeJson = jsonObjectIn.getAsJsonObject("ore_type");

        String name = oreTypeJson.get("name").getAsString();

        return () -> name;
    }

    public JsonObject toJsonObject(IOreType oreType) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", oreType.getName());

        if (json.size() > 0) {
            jsonObject.add("ore_type", json);
        }

        return jsonObject;
    }
}