package mightydanp.industrialcore.common.jsonconfig.tool.type;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolTypeRegistry extends JsonConfigMultiFile<IToolType> {

    @Override
    public void initiate() {
        setJsonFolderName("tool_type");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultToolType toolType : DefaultToolType.values()) {
            register(toolType);
        }
        //

        buildJson();
        loadExistJson();

        super.initiate();
    }

    @Override
    public void register(IToolType toolTypeIn) {
        Pair<String, String> fixes = new Pair<>(toolTypeIn.getPrefix(), toolTypeIn.getSuffix());
        if (registryMap.containsValue(toolTypeIn)) {
            throw new IllegalArgumentException("tool type with the prefix:(" + toolTypeIn.getPrefix() + "), and the suffix:(" + toolTypeIn.getSuffix() + "), already exists.");
        }

        registryMap.put(fixesToName(fixes), toolTypeIn);
    }

    public IToolType getToolTypeByFixes(Pair<String, String> fixesIn) {
        return registryMap.get(fixesIn);
    }

    public Set<IToolType> getAllToolType() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(IToolType toolType : registryMap.values()) {
            String prefix = toolType.getPrefix().replace("_", "");
            String suffix = toolType.getSuffix().replace("_", "");
            String name = fixesToName(new Pair<>(toolType.getPrefix(), toolType.getSuffix()));

            if(!name.equals("")) {
                JsonObject jsonObject = getJsonObject(name);

                if (jsonObject.size() == 0) {
                    JsonObject toolPartJson = new JsonObject();
                    {
                        toolPartJson.addProperty("name", name);
                        toolPartJson.addProperty("prefix", toolType.getPrefix());
                        toolPartJson.addProperty("suffix", toolType.getSuffix());

                        if (toolPartJson.size() > 0) {
                            jsonObject.add("tool_type", toolPartJson);
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
                        JsonObject toolTypeJson = jsonObject.getAsJsonObject("tool_type");
                        String toolTypeName = toolTypeJson.get("name").getAsString();
                        IToolType toolType = getFromJsonObject(jsonObject);

                        registryMap.put(fixesToName(toolType.getFixes()), toolType);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to tool type list because a tool type already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            Minecraft.crash(new CrashReport("tool type json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IToolType getFromJsonObject(JsonObject jsonObjectIn){
        JsonObject toolTypeJson = jsonObjectIn.getAsJsonObject("tool_type");

        String name = toolTypeJson.get("name").getAsString();
        String prefix = toolTypeJson.get("prefix").getAsString();
        String suffix = toolTypeJson.get("suffix").getAsString();

        return new IToolType() {

            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public String getSuffix() {
                return suffix;
            }

            @Override
            public String getName() {
                return fixesToName(new Pair<>(prefix, suffix));
            }

            @Override
            public Pair<String, String> getFixes() {
                return new Pair<>(prefix, suffix);
            }
        };
    }

    public JsonObject toJsonObject(IToolType toolType) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", fixesToName(new Pair<>(toolType.getPrefix(), toolType.getSuffix())));
        json.addProperty("prefix", toolType.getPrefix());
        json.addProperty("suffix", toolType.getSuffix());

        if (json.size() > 0) {
            jsonObject.add("tool_type", json);
        }

        return jsonObject;
    }
}