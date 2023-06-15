package mightydanp.techcore.common.jsonconfig.materialflag;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static mightydanp.techcore.common.jsonconfig.materialflag.MaterialFlagCodec.fixesToName;

/**
 * Created by MightyDanp on 1/16/2022.
 */
public class MaterialFlagRegistry extends JsonConfigMultiFile<MaterialFlagCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(MaterialFlagCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultMaterialFlag codec : DefaultMaterialFlag.values()) {
            register(codec.getCodec());
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(MaterialFlagCodec codec) {
        if (registryMap.containsValue(codec)) {
            throw new IllegalArgumentException("" + MaterialFlagCodec.codecName + " with the prefix:(" + codec.prefix() + "), and the suffix:(" + codec.suffix() + "), already exists.");
        }

        registryMap.put(fixesToName(codec), codec);
    }

    public MaterialFlagCodec getMaterialFlagByName(String name){
        Optional<MaterialFlagCodec> codec = registryMap.values().stream().filter(o -> fixesToName(o).equals(name)).findFirst();

        if(codec.isEmpty()) {
            TechAscension.LOGGER.warn("(" + name + "), does not exist as a " + MaterialFlagCodec.codecName + ".");
        }

        return codec.orElse(null);
    }

    public void buildJson(){
        for(MaterialFlagCodec codec : registryMap.values()) {
            String name = fixesToName(codec);
            JsonObject jsonObject = getJsonObject(name);

            if (jsonObject.size() == 0) {
                this.saveJsonObject(name, toJsonObject(codec));
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
                        String name = jsonObject.get("name").getAsString();
                        MaterialFlagCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(name, codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + MaterialFlagCodec.codecName + " list because a " + MaterialFlagCodec.codecName + " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("" + MaterialFlagCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public MaterialFlagCodec fromJsonObject(JsonObject jsonObjectIn){
        return MaterialFlagCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + MaterialFlagCodec.codecName + ", please fix this"))).getFirst();

    }

    public JsonObject toJsonObject(MaterialFlagCodec codec) {
        return MaterialFlagCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + MaterialFlagCodec.codecName + ", please fix this"))).getAsJsonObject();
    }
}
