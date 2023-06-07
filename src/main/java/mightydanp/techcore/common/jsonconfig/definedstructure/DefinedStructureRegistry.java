package mightydanp.techcore.common.jsonconfig.definedstructure;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateCodec;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class DefinedStructureRegistry extends JsonConfigMultiFile<DefinedStructureCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(DefinedStructureCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(DefinedStructureCodec codec) {
        String name = codec.name();
        if (registryMap.containsKey(codec.name()))
            throw new IllegalArgumentException(DefinedStructureCodec.codecName + " with name(" + name + "), already exists.");
        registryMap.put(name, codec);
    }

    public DefinedStructureCodec getByName(String name) {
        return registryMap.get(name);
    }

    public void buildJson(){
        for(DefinedStructureCodec codec : registryMap.values()) {
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
                        DefinedStructureCodec definedStructure = fromJsonObject(jsonObject);

                        registryMap.put(definedStructure.name(), definedStructure);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + DefinedStructureCodec.codecName + " list because a defined structure already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(DefinedStructureCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public DefinedStructureCodec fromJsonObject(JsonObject jsonObjectIn){
        return DefinedStructureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your" + DefinedStructureCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(DefinedStructureCodec codec) {
        return DefinedStructureCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + DefinedStructureCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}