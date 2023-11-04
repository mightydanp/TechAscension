package mightydanp.techapi.common.jsonconfig.main;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigSingleFile;
import mightydanp.techcore.common.libs.Ref;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by MightyDanp on 1/12/2022.
 */
public class MainJsonConfig extends JsonConfigSingleFile {

    @Override
    public void initiate(){
        setJsonFolderLocation("config");
        setJsonFilename("main_config");

        super.initiate();

        this.buildConfigJson(getJsonObject());

    }

    public void setFolderLocation(String folderLocation) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("folder_location", folderLocation);
        addToCategoryInConfig("json_config", jsonObject);
    }

    public void setLoadDefault(boolean loadDefault) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("load_default", loadDefault);
        addToCategoryInConfig("json_config", jsonObject);
    }

    public String getFolderLocation() {
        if(!getConfigFromCategory("json_config", "folder_location").toString().equals("{}")){
            return getConfigFromCategory("json_config", "folder_location").getAsString();
        } else {
            return null;
        }
    }

    public Boolean loadDefault() {
        if(!getConfigFromCategory("json_config", "load_default").toString().equals("{}")){
            return getConfigFromCategory("json_config", "load_default").getAsBoolean();
        } else {
            return null;
        }
    }

    @Override
    public void buildConfigJson(JsonObject jsonObject){
        if(jsonObject.size() == 0) {
            {
                JsonObject json_config = new JsonObject();
                if (getFolderLocation() != null) {
                    json_config.addProperty("folder_location", getFolderLocation());
                } else {
                    Path path = Paths.get(getJsonFolderLocation() + "/" + Ref.mod_id + "/default");
                    setFolderLocation(path.toFile().toString());
                    json_config.addProperty("folder_location", getFolderLocation());
                }

                if (loadDefault() != null) {
                    json_config.addProperty("load_default", loadDefault());
                } else {
                    setLoadDefault(true);
                    json_config.addProperty("folder_location", getFolderLocation());
                }

                if (json_config.size() > 0) {
                    jsonObject.add("json_config", json_config);
                }
            }
        }

        super.buildConfigJson(jsonObject);
    }
}
