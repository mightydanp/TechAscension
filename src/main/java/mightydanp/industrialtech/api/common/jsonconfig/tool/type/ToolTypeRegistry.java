package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.common.ToolType;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolTypeRegistry extends JsonConfigMultiFile {
    private final Map<Pair<String, String>, IToolType> toolTypeList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("tool_type");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultToolType toolType : DefaultToolType.values()) {
            register(toolType);
        }
        //

        buildToolTypeJson();
        loadExistJson();
        super.initiate();
    }

    public void register(IToolType toolTypeIn) {
        Pair<String, String> fixes = new Pair<>(toolTypeIn.getPrefix(), toolTypeIn.getSuffix());
        if (IndustrialTech.toolTypeRegistry.toolTypeList.containsValue(toolTypeIn)) {
            throw new IllegalArgumentException("tool type with the prefix:(" + toolTypeIn.getPrefix() + "), and the suffix:(" + toolTypeIn.getSuffix() + "), already exists.");
        }

        IndustrialTech.toolTypeRegistry.toolTypeList.put(fixes, toolTypeIn);
    }

    public String fixesToName(Pair<String, String> fixes){
        String prefix = fixes.getFirst().replace("_", "");
        String suffix = fixes.getSecond().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }

    public IToolType getToolTypeByFixes(Pair<String, String> fixesIn) {
        return IndustrialTech.toolTypeRegistry.toolTypeList.get(fixesIn);
    }

    public Set<IToolType> getAllToolType() {
        return new HashSet<IToolType>(IndustrialTech.toolTypeRegistry.toolTypeList.values());
    }

    public void buildToolTypeJson(){
        for(Pair<String, String> toolType : toolTypeList.keySet()) {
            String prefix = toolType.getFirst().replace("_", "");
            String suffix = toolType.getSecond().replace("_", "");
            String name = "";

            if(!prefix.equals("") && !suffix.equals("")){
                name = prefix + "_" + suffix;
            }

            if(prefix.equals("") && !suffix.equals("")){
                name = suffix;
            }

            if(!prefix.equals("") && suffix.equals("")){
                name = prefix;
            }

            if(!name.equals("")) {
                JsonObject jsonObject = getJsonObject(name);

                if (jsonObject.size() == 0) {
                    JsonObject toolTypeJson = new JsonObject();
                    {
                        toolTypeJson.addProperty("name", name);
                        toolTypeJson.addProperty("prefix", toolType.getFirst());
                        toolTypeJson.addProperty("suffix", toolType.getSecond());

                        if (toolTypeJson.size() > 0) {
                            jsonObject.add("tool_type", toolTypeJson);
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

                    if (!toolTypeList.containsValue(getIToolType(jsonObject))) {
                        JsonObject toolTypeJson = jsonObject.getAsJsonObject("tool_type");
                        String toolTypeName = toolTypeJson.get("name").getAsString();
                        IToolType toolType = getIToolType(jsonObject);

                        toolTypeList.put(toolType.getFixes(), toolType);
                        ToolType.get(toolTypeName);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to tool type list because a tool type already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            Minecraft.crash(new CrashReport("tool type json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IToolType getIToolType(JsonObject jsonObjectIn){
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