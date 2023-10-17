package mightydanp.techcore.common.jsonconfig.tool.part;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static mightydanp.techcore.common.jsonconfig.tool.part.ToolPartCodec.fixesToName;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolPartRegistry extends JsonConfigMultiFile<ToolPartCodec> {
    @Override
    public void initiate() {
        setJsonFolderName(ToolPartCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultToolPart codec : DefaultToolPart.values()) {
            register(codec.getCodec());
        }
        //

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(ToolPartCodec codec) {
        if (registryMap.containsValue(codec)) {
            throw new IllegalArgumentException(ToolPartCodec.codecName + " with the prefix:(" + codec.prefix() + "), and the suffix:(" + codec.suffix() + "), already exists.");
        }

        registryMap.put(fixesToName(codec), codec);
    }

    public ToolPartCodec getByName(String name) {
        return registryMap.get(name);
    }

    public void buildJson() {
        for (ToolPartCodec codec : registryMap.values()) {
            String name = fixesToName(codec);

            if (!name.equals("")) {
                JsonObject jsonObject = getJsonObject(name);

                if (jsonObject.size() == 0) {
                    this.saveJsonObject(name, toJsonObject(codec));
                }
            }
        }
    }

    public void loadExistingJsons() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        String toolPartName = jsonObject.get("name").getAsString();
                        ToolPartCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(fixesToName(codec), codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + ToolPartCodec.codecName + " list because a tool part already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(ToolPartCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ToolPartCodec fromJsonObject(JsonObject jsonObjectIn) {
        return ToolPartCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + ToolPartCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(ToolPartCodec codec) {
        return ToolPartCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + ToolPartCodec.codecName + ", please fix this"))).getAsJsonObject();
    }
}