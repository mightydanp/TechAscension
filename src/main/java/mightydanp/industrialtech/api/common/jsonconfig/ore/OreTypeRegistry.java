package mightydanp.industrialtech.api.common.jsonconfig.ore;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
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
public class OreTypeRegistry extends JsonConfigMultiFile {
    private static final Map<String, IOreType> oreTypeList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("ore_type");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultOreType oreType : DefaultOreType.values()) {
            register(oreType);
        }
        //

        buildOreTypeJson();
        loadExistJson();
        super.initiate();
    }

    public static List<IOreType> getAllOreTypes() {
        return new ArrayList<>(oreTypeList.values());
    }

    public static void register(IOreType oreTypeIn) {
        String name = oreTypeIn.getName();
        if (oreTypeList.containsKey(oreTypeIn.getName()))
            throw new IllegalArgumentException("ore type with name(" + name + "), already exists.");
        oreTypeList.put(name, oreTypeIn);
    }

    public static IOreType getOreTypeByName(String ore_type) {
        return oreTypeList.get(ore_type);
    }

    public Set<IOreType> getAllOreType() {
        return new HashSet<>(oreTypeList.values());
    }

    public void buildOreTypeJson(){
        for(IOreType oreType : oreTypeList.values()) {
            JsonObject jsonObject = getJsonObject(oreType.getName());

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", oreType.getName());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("ore_type", materialFlagJson);
                    }
                }
                this.saveJsonObject(oreType.getName(), jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!oreTypeList.containsValue(getOreType(jsonObject))) {
                        IOreType oreType = getOreType(jsonObject);

                        oreTypeList.put(oreType.getName(), oreType);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to ore type list because a ore type already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("ore type json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IOreType getOreType(JsonObject jsonObjectIn){
        JsonObject oreTypeJson = jsonObjectIn.getAsJsonObject("ore_type");

        String name = oreTypeJson.get("name").getAsString();

        return () -> name;
    }

    public JsonObject toJsonObject(IOreType oreType) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", oreType.getName());

        if (json.size() > 0) {
            jsonObject.add("ore_type", json);
        }

        return jsonObject;
    }
}