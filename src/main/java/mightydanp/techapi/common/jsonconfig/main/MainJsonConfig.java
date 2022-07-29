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
        setJsonFolderLocation("config/");
        setJsonFilename("main_config");
        super.initiate();

    }

    public MainJsonConfig setFolderLocation(String folderLocation) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("folder_location", folderLocation);
        addToCategoryInConfig("json_config", jsonObject);
        return this;
    }

    public String getFolderLocation() {
        return getConfigFromCategory("json_config", "folder_location").getAsString();
    }

    @Override
    public void buildConfigJson(JsonObject jsonObject){
        if(jsonObject.size() == 0) {
            {
                if (!getFolderLocation().equals("")) {
                    jsonObject.addProperty("folder_location", getFolderLocation());
                } else {
                    Path path = Paths.get(getJsonFolderLocation() + "/" + Ref.mod_id + "/default");
                    setFolderLocation(path.toFile().toString());
                    jsonObject.addProperty("folder_location", getFolderLocation());
                }

                if (jsonObject.size() > 0) {
                    jsonObject.add("json_config", jsonObject);
                }
            }
        }

        super.buildConfigJson(jsonObject);
    }
}
