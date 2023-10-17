package mightydanp.techcore.common.jsonconfig.icons;

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
public class TextureIconRegistry extends JsonConfigMultiFile<TextureIconCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(TextureIconCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultTextureIcon codec : DefaultTextureIcon.values()) {
            register(codec.getCodec());
        }
        //

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(TextureIconCodec codec) {
        String name = codec.name();
        if (registryMap.containsKey(codec.name()))
            throw new IllegalArgumentException(TextureIconCodec.codecName +  " with name(" + name + "), already exists.");
        registryMap.put(name, codec);
    }

    public TextureIconCodec getTextureIconByName(String name) {
        return registryMap.get(name);
    }

    public Set<TextureIconCodec> getAllTextureIcon() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(TextureIconCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
            }
        }
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        TextureIconCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + TextureIconCodec.codecName +  " list because a " + TextureIconCodec.codecName +  " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(TextureIconCodec.codecName +  " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public TextureIconCodec fromJsonObject(JsonObject jsonObjectIn){
        return TextureIconCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + TextureIconCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(TextureIconCodec codec) {
        return TextureIconCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + TextureIconCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}