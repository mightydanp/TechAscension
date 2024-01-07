package mightydanp.techcore.common.jsonconfig.tool;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techapi.common.resources.ClientResourcePackEventHandler;
import mightydanp.techapi.common.resources.ServerResourcePackEventHandler;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolRegistry extends JsonConfigMultiFile<ToolCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(ToolCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        if(TechAscension.mainJsonConfig.loadDefault()){

        }

        buildJson();
        super.initiate();
        ServerResourcePackEventHandler.postInitLoad.add(this);
    }

    @Override
    public void register(ToolCodec codec) {
        String name = codec.name();
        if (registryMap.containsKey(codec.name())) {
            TechAscension.LOGGER.warn(ToolCodec.codecName + " with name(" + name + "), already exists.");
        } else {
            registryMap.put(name, codec);
        }
    }

    public ToolCodec getToolByName(String name) {
        return registryMap.get(name);
    }

    public Set<ToolCodec> getAllTool() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(ToolCodec tool : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(tool.name());

            if (jsonObject.size() == 0 || TechAscension.mainJsonConfig.loadDefault()) {
                this.saveJsonObject(tool.name(), toJsonObject(tool));
            }
        }
    }

    public void buildAndRegister(ToolCodec codec){
        this.register(codec);
        this.saveJsonObject(codec.name(), toJsonObject(codec));
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        ToolCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + ToolCodec.codecName + " because a tool already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(ToolCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ToolCodec fromJsonObject(JsonObject jsonObjectIn){
        return ToolCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + ToolCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(ToolCodec codec) {
        return ToolCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + ToolCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}