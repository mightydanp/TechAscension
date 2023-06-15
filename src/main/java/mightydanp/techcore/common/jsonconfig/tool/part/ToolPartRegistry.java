package mightydanp.techcore.common.jsonconfig.tool.part;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static mightydanp.techcore.common.jsonconfig.tool.part.IToolPart.fixesToName;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class ToolPartRegistry extends JsonConfigMultiFile<IToolPart> {
    @Override
    public void initiate() {
        setJsonFolderName("tool_part");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

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

        registryMap.put(fixesToName(toolPartIn), toolPartIn);
    }

    public IToolPart getByName(String fixesIn) {
        return registryMap.get(fixesIn);
    }

    public void buildJson() {
        for (IToolPart toolPart : registryMap.values()) {
            String name = fixesToName(toolPart);

            if (!name.equals("")) {
                JsonObject jsonObject = getJsonObject(name);

                if (jsonObject.size() == 0) {
                    this.saveJsonObject(name, toJsonObject(toolPart));
                }
            }
        }
    }

    public void loadExistJson() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        String toolPartName = jsonObject.get("name").getAsString();
                        IToolPart toolPart = fromJsonObject(jsonObject);

                        registryMap.put(fixesToName(toolPart), toolPart);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to tool part list because a tool part already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("tool part json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IToolPart fromJsonObject(JsonObject jsonObjectIn) {
        String prefix = jsonObjectIn.get("prefix").getAsString();
        String suffix = jsonObjectIn.get("suffix").getAsString();

        return new IToolPart() {

            @Override
            public String getPrefix() {
                return prefix;
            }

            @Override
            public String getSuffix() {
                return suffix;
            }
        };
    }

    public JsonObject toJsonObject(IToolPart toolPart) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", fixesToName(toolPart));
        jsonObject.addProperty("prefix", toolPart.getPrefix());
        jsonObject.addProperty("suffix", toolPart.getSuffix());

        return jsonObject;
    }
}