package mightydanp.techcore.common.jsonconfig.material.ore;

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
public class OreTypeRegistry extends JsonConfigMultiFile<IOreType> {

    @Override
    public void initiate() {
        setJsonFolderName("ore_type");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

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
                this.saveJsonObject(oreType.getName(), toJsonObject(oreType));
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
                        IOreType oreType = fromJsonObject(jsonObject);

                        registryMap.put(oreType.getName(), oreType);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to ore type list because a ore type already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("ore type json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IOreType fromJsonObject(JsonObject jsonObjectIn){
        return new IOreType() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }
        };
    }

    public JsonObject toJsonObject(IOreType oreType) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", oreType.getName());

        return jsonObject;
    }
}