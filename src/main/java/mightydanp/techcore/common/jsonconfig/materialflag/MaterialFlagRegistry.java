package mightydanp.techcore.common.jsonconfig.materialflag;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/16/2022.
 */
public class MaterialFlagRegistry extends JsonConfigMultiFile<IMaterialFlag> {

    @Override
    public void initiate() {
        setJsonFolderName("material_flag");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultMaterialFlag materialFlag : DefaultMaterialFlag.values()) {
            register(materialFlag);
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IMaterialFlag materialFlagIn) {
        if (registryMap.containsValue(materialFlagIn)) {
            throw new IllegalArgumentException("material flag with the prefix:(" + materialFlagIn.getPrefix() + "), and the suffix:(" + materialFlagIn.getSuffix() + "), already exists.");
        }

        registryMap.put(fixesToName(materialFlagIn.getFixes()), materialFlagIn);
    }

    public IMaterialFlag getMaterialFlagByFixes(Pair<String, String> fixesIn) {
        Optional<IMaterialFlag> materialFlag = registryMap.values().stream().filter(o -> fixesToName(new Pair<>(o.getPrefix(), o.getSuffix())).equals(fixesToName(fixesIn))).findFirst();

        if(materialFlag.isEmpty()) {
            TechAscension.LOGGER.warn("(" + fixesToName(fixesIn) + "), does not exist as a material flag.");
        }

        return materialFlag.orElse(null);
    }

    public IMaterialFlag getMaterialFlagByName(String name){
        Optional<IMaterialFlag> materialFlag = registryMap.values().stream().filter(o -> fixesToName(new Pair<>(o.getPrefix(), o.getSuffix())).equals(name)).findFirst();

        if(materialFlag.isEmpty()) {
            TechAscension.LOGGER.warn("(" + name + "), does not exist as a material flag.");
        }

        return materialFlag.orElse(null);
    }

    public void buildJson(){
        for(IMaterialFlag materialFlag : registryMap.values()) {
            String name = fixesToName(materialFlag.getFixes());
            JsonObject jsonObject = getJsonObject(name);

            if (jsonObject.size() == 0) {
                this.saveJsonObject(name, toJsonObject(materialFlag));
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        String materialFlagName = jsonObject.get("name").getAsString();
                        IMaterialFlag materialFlag = fromJsonObject(jsonObject);

                        registryMap.put(materialFlagName, materialFlag);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to material flag list because a material flag already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("material flag json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IMaterialFlag fromJsonObject(JsonObject jsonObjectIn){
            String name = jsonObjectIn.get("name").getAsString();
            String prefix = jsonObjectIn.get("prefix").getAsString();
            String suffix = jsonObjectIn.get("suffix").getAsString();

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
        jsonObject.addProperty("name", fixesToName(new Pair<>(material.getPrefix(), material.getSuffix())));
        jsonObject.addProperty("prefix", material.getPrefix());
        jsonObject.addProperty("suffix", material.getSuffix());

        return jsonObject;
    }
}
