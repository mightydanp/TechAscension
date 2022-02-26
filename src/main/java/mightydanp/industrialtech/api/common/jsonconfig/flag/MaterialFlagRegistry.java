package mightydanp.industrialtech.api.common.jsonconfig.flag;

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
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/16/2022.
 */
public class MaterialFlagRegistry extends JsonConfigMultiFile {
    private static final Map<String, IMaterialFlag> materialFlagList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("material_flag");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultMaterialFlag materialFlag : DefaultMaterialFlag.values()) {
            register(materialFlag);
        }
        //

        buildMaterialFlagJson();
        loadExistJson();
        super.initiate();
    }

    public void register(IMaterialFlag materialFlagIn) {
        if (materialFlagList.containsValue(materialFlagIn)) {
            throw new IllegalArgumentException("material flag with the prefix:(" + materialFlagIn.getPrefix() + "), and the suffix:(" + materialFlagIn.getSuffix() + "), already exists.");
        }

        materialFlagList.put(fixesToName(materialFlagIn.getFixes()), materialFlagIn);
    }

    public IMaterialFlag getMaterialFlagByFixes(Pair<String, String> fixesIn) {
        Optional<IMaterialFlag> materialFlag = materialFlagList.values().stream().filter(o -> fixesToName(new Pair<>(o.getPrefix(), o.getSuffix())).equals(fixesToName(fixesIn))).findFirst();

        if(!materialFlag.isPresent()) {
            IndustrialTech.LOGGER.warn("(" + fixesToName(fixesIn) + "), does not exist as a material flag.");
        }

        return materialFlag.orElse(null);
    }

    public static List<IMaterialFlag> getAllMaterialFlags() {
        return materialFlagList.values().stream().collect(Collectors.toList());
    }

    public IMaterialFlag getMaterialFlagByName(String name){
        Optional<IMaterialFlag> materialFlag = materialFlagList.values().stream().filter(o -> fixesToName(new Pair<>(o.getPrefix(), o.getSuffix())).equals(name)).findFirst();

        if(!materialFlag.isPresent()) {
            IndustrialTech.LOGGER.warn("(" + name + "), does not exist as a material flag.");
        }

        return materialFlag.orElse(null);
    }

    public void buildMaterialFlagJson(){
        for(IMaterialFlag materialFlag : materialFlagList.values()) {
            String name = fixesToName(materialFlag.getFixes());
            JsonObject jsonObject = getJsonObject(name);

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", name);
                    materialFlagJson.addProperty("prefix", materialFlag.getPrefix());
                    materialFlagJson.addProperty("suffix", materialFlag.getSuffix());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("material_flag", materialFlagJson);
                    }
                }
                this.saveJsonObject(name, jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!materialFlagList.containsValue(getMaterialFlag(jsonObject))) {
                        JsonObject materialFlagJson = jsonObject.getAsJsonObject("material_flag");
                        String materialFlagName = materialFlagJson.get("name").getAsString();
                        IMaterialFlag materialFlag = getMaterialFlag(jsonObject);

                        materialFlagList.put(materialFlagName, materialFlag);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to material flag list because a material flag already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("material flag json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IMaterialFlag getMaterialFlag(JsonObject jsonObjectIn){
        JsonObject materialFlagJson = jsonObjectIn.getAsJsonObject("material_flag");

        String name = materialFlagJson.get("name").getAsString();
        String prefix = materialFlagJson.get("prefix").getAsString();
        String suffix = materialFlagJson.get("suffix").getAsString();

        return new IMaterialFlag() {

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

    public JsonObject toJsonObject(IMaterialFlag material) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", fixesToName(new Pair<>(material.getPrefix(), material.getSuffix())));
        json.addProperty("prefix", material.getPrefix());
        json.addProperty("suffix", material.getSuffix());

        if (json.size() > 0) {
            jsonObject.add("material_flag", json);
        }

        return jsonObject;
    }
}
