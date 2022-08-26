package mightydanp.techcore.common.jsonconfig.icons;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class TextureIconRegistry extends JsonConfigMultiFile<ITextureIcon> {

    @Override
    public void initiate() {
        setJsonFolderName("texture_icon");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        //
        for (DefaultTextureIcon textureIcon : DefaultTextureIcon.values()) {
            register(textureIcon);
        }
        //

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(ITextureIcon textureIconIn) {
        String name = textureIconIn.getName();
        if (registryMap.containsKey(textureIconIn.getName()))
            throw new IllegalArgumentException("texture icon with name(" + name + "), already exists.");
        registryMap.put(name, textureIconIn);
    }

    public ITextureIcon getTextureIconByName(String textureIcon) {
        return registryMap.get(textureIcon);
    }

    public Set<ITextureIcon> getAllTextureIcon() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(ITextureIcon textureIcon : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(textureIcon.getName());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(textureIcon.getName(), toJsonObject(textureIcon));
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
                        ITextureIcon textureIcon = fromJsonObject(jsonObject);

                        registryMap.put(textureIcon.getName(), textureIcon);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to texture icon list because a texture icon already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("texture icon json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ITextureIcon fromJsonObject(JsonObject jsonObjectIn){
        return new ITextureIcon() {
            @Override
            public String getName() {
                return jsonObjectIn.get("name").getAsString();
            }
        };
    }

    public JsonObject toJsonObject(ITextureIcon textureIcon) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", textureIcon.getName());

        return jsonObject;
    }
}