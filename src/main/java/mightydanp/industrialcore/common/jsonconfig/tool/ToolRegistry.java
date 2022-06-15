package mightydanp.industrialcore.common.jsonconfig.tool;

import com.google.gson.JsonObject;
import mightydanp.industrialcore.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolRegistry extends JsonConfigMultiFile<ITool> {

    @Override
    public void initiate() {
        setJsonFolderName("tool");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(ITool toolIn) {
        String name = toolIn.getName();
        if (registryMap.containsKey(toolIn.getName()))
            throw new IllegalArgumentException("tool with name(" + name + "), already exists.");
        registryMap.put(name, toolIn);
    }

    public ITool getToolByName(String tool) {
        return registryMap.get(tool);
    }

    public Set<ITool> getAllTool() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(ITool tool : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(tool.getName());

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", tool.getName());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("tool", materialFlagJson);
                    }
                }
                this.saveJsonObject(tool.getName(), jsonObject);
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
                        ITool tool = getFromJsonObject(jsonObject);

                        registryMap.put(tool.getName(), tool);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to tool list because a tool already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("tool json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ITool getFromJsonObject(JsonObject jsonObjectIn){
        JsonObject textureIconJson = jsonObjectIn.getAsJsonObject("tool");

        String name = textureIconJson.get("name").getAsString();

        return () -> name;
    }

    public JsonObject toJsonObject(ITool tool) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", tool.getName());

        if (json.size() > 0) {
            jsonObject.add("tool", json);
        }

        return jsonObject;
    }
}