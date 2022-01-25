package mightydanp.industrialtech.api.common.jsonconfig.tool.part;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolPartRegistry extends JsonConfigMultiFile {
    private final Map<Pair<String, String>, IToolPart> toolPartList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("tool_part");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultToolPart toolPart : DefaultToolPart.values()) {
            register(toolPart);
        }
        //

        buildToolPartJson();
        loadExistJson();
        super.initiate();
    }

    public void register(IToolPart toolPartIn) {
        Pair<String, String> fixes = new Pair<>(toolPartIn.getPrefix(), toolPartIn.getSuffix());
        if (IndustrialTech.toolPartRegistry.toolPartList.containsValue(toolPartIn)) {
            throw new IllegalArgumentException("Tool Part with the prefix:(" + toolPartIn.getPrefix() + "), and the suffix:(" + toolPartIn.getSuffix() + "), already exists.");
        }

        IndustrialTech.toolPartRegistry.toolPartList.put(fixes, toolPartIn);
    }

    public static IToolPart getToolPartByFixes(Pair<String, String> fixesIn) {
        return IndustrialTech.toolPartRegistry.toolPartList.get(fixesIn);
    }

    public Set<IToolPart> getAllToolPart() {
        return new HashSet<IToolPart>(IndustrialTech.toolPartRegistry.toolPartList.values());
    }

    public void buildToolPartJson(){
        for(Pair<String, String> toolPart : toolPartList.keySet()) {
            String prefix = toolPart.getFirst().replace("_", "");
            String suffix = toolPart.getSecond().replace("_", "");
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
                    JsonObject toolPartJson = new JsonObject();
                    {
                        toolPartJson.addProperty("name", name);
                        toolPartJson.addProperty("prefix", toolPart.getFirst());
                        toolPartJson.addProperty("suffix", toolPart.getSecond());

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

                    if (!toolPartList.containsValue(getIToolPart(jsonObject))) {
                        JsonObject toolPartJson = jsonObject.getAsJsonObject("tool_part");
                        String toolPartName = toolPartJson.get("name").getAsString();
                        IToolPart toolPart = getIToolPart(jsonObject);

                        toolPartList.put(toolPart.getFixes(), toolPart);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to tool part list because a tool part already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            Minecraft.crash(new CrashReport("tool part json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IToolPart getIToolPart(JsonObject jsonObjectIn){
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
}