package mightydanp.industrialtech.api.common.jsonconfig.icons;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
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
public class TextureIconRegistry extends JsonConfigMultiFile {
    private final Map<String, ITextureIcon> textureIconList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("texture_icon");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultTextureIcon textureIcon : DefaultTextureIcon.values()) {
            register(textureIcon);
        }
        //

        buildTextureIconJson();
        loadExistJson();
        super.initiate();
    }

    public static void register(ITextureIcon textureIconIn) {
        String name = textureIconIn.getName();
        if (IndustrialTech.textureIconRegistry.textureIconList.containsKey(textureIconIn.getName()))
            throw new IllegalArgumentException("texture icon with name(" + name + "), already exists.");
        IndustrialTech.textureIconRegistry.textureIconList.put(name, textureIconIn);
    }

    public ITextureIcon getTextureIconByName(String texture_icon) {
        return IndustrialTech.textureIconRegistry.textureIconList.get(texture_icon);
    }

    public Set<ITextureIcon> getAllTextureIcon() {
        return new HashSet<ITextureIcon>(IndustrialTech.textureIconRegistry.textureIconList.values());
    }

    public void buildTextureIconJson(){
        for(ITextureIcon textureIcon : textureIconList.values()) {
            JsonObject jsonObject = getJsonObject(textureIcon.getName());

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("name", textureIcon.getName());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("texture_icon", materialFlagJson);
                    }
                }
                this.saveJsonObject(textureIcon.getName(), jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!textureIconList.containsValue(getTextureIcon(jsonObject))) {
                        ITextureIcon textureIcon = getTextureIcon(jsonObject);

                        textureIconList.put(textureIcon.getName(), textureIcon);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to texture icon list because a texture icon already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            Minecraft.crash(new CrashReport("texture icon json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public ITextureIcon getTextureIcon(JsonObject jsonObjectIn){
        JsonObject textureIconJson = jsonObjectIn.getAsJsonObject("texture_icon");

        String name = textureIconJson.get("name").getAsString();

        return new ITextureIcon() {
            @Override
            public String getName() {
                return name;
            }
        };
    }

    public JsonObject toJsonObject(ITextureIcon fluidState) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("name", fluidState.getName());

        if (json.size() > 0) {
            jsonObject.add("fluid_state", json);
        }

        return jsonObject;
    }
}