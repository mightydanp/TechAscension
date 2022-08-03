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

    }

    public MainJsonConfig setFolderLocation(String folderLocation) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("folder_location", folderLocation);
        addToCategoryInConfig("json_config", jsonObject);
        return this;
    }

    public String getFolderLocation() {
        if(!getConfigFromCategory("json_config", "folder_location").toString().equals("{}")){
            return getConfigFromCategory("json_config", "folder_location").getAsString();
        } else {
            return "";
        }
    }

    @Override
    public void buildConfigJson(JsonObject jsonObject){
        if(jsonObject.size() == 0) {
            {
                JsonObject jsonObject1 = new JsonObject();
                if (!getFolderLocation().equals("")) {
                    jsonObject1.addProperty("folder_location", getFolderLocation());
                } else {
                    Path path = Paths.get(getJsonFolderLocation() + "/" + Ref.mod_id + "/default");
                    setFolderLocation(path.toFile().toString());
                    jsonObject1.addProperty("folder_location", getFolderLocation());
                }

                jsonObject.add("json_config", jsonObject1);
            }
        }

        super.buildConfigJson(jsonObject);
    }
}
