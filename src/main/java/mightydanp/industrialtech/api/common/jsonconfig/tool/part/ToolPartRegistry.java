package mightydanp.industrialtech.api.common.jsonconfig.tool.part;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
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
public class ToolPartRegistry extends JsonConfigMultiFile<IToolPart> {
    @Override
    public void initiate() {
        setJsonFolderName("tool_part");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultToolPart toolPart : DefaultToolPart.values()) {
            register(toolPart);
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IToolPart toolPartIn) {
        if (registryMap.containsValue(toolPartIn)) {
            throw new IllegalArgumentException("Tool Part with the prefix:(" + toolPartIn.getPrefix() + "), and the suffix:(" + toolPartIn.getSuffix() + "), already exists.");
        }

        registryMap.put(fixesToName(new Pair<>(toolPartIn.getPrefix(), toolPartIn.getSuffix())), toolPartIn);
    }

    public IToolPart getByName(String fixesIn) {
        return registryMap.get(fixesIn);
    }

    public void buildJson(){
        for(IToolPart toolPart : registryMap.values()) {
            String prefix = toolPart.getPrefix().replace("_", "");
            String suffix = toolPart.getSuffix().replace("_", "");
            String name = fixesToName(new Pair<>(toolPart.getPrefix(), toolPart.getSuffix()));

            if(!name.equals("")) {
                JsonObject jsonObject = getJsonObject(name);

                if (jsonObject.size() == 0) {
                    JsonObject toolPartJson = new JsonObject();
                    {
                        toolPartJson.addProperty("name", name);
                        toolPartJson.addProperty("prefix", toolPart.getPrefix());
                        toolPartJson.addProperty("suffix", toolPart.getSuffix());

                        if (toolPartJson.size() > 0) {
                            jsonObject.add("tool_part", toolPartJson);
                        }
                    }
                    this.saveJsonObject(name, jsonObject);
                }
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
                        JsonObject toolPartJson = jsonObject.getAsJsonObject("tool_part");
                        String toolPartName = toolPartJson.get("name").getAsString();
                        IToolPart toolPart = getFromJsonObject(jsonObject);

                        registryMap.put(fixesToName(new Pair<>(toolPart.getPrefix(), toolPart.getSuffix())), toolPart);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to tool part list because a tool part already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("tool part json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IToolPart getFromJsonObject(JsonObject jsonObjectIn){
        JsonObject toolPartJson = jsonObjectIn.getAsJsonObject("tool_part");

        String name = toolPartJson.get("name").getAsString();
        String prefix = toolPartJson.get("prefix").getAsString();
        String suffix = toolPartJson.get("suffix").getAsString();

        return new IToolPart() {

            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public String getSuffix() {
                return suffix;
            }

            @Override
            public Pair<String, String> getFixes() {
                return new Pair<>(prefix, suffix);
            }
        };
    }

    public JsonObject toJsonObject(IToolPart toolPart) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", fixesToName(new Pair<>(toolPart.getPrefix(), toolPart.getSuffix())));
        json.addProperty("prefix", toolPart.getPrefix());
        json.addProperty("suffix", toolPart.getSuffix());

        if (json.size() > 0) {
            jsonObject.add("tool_part", json);
        }

        return jsonObject;
    }
}