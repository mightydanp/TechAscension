package mightydanp.industrialtech.api.common.jsonconfig.main.data;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigSingleFile;
import mightydanp.industrialtech.common.IndustrialTech;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by MightyDanp on 1/12/2022.
 */
public class MainJsonConfigSingleFile extends JsonConfigSingleFile {
    private String folderLocation = "";

    @Override
    public void initiate(){
        setJsonFolderLocation("config");
        setJsonFilename("main_config");
        super.initiate();
        buildMainConfigJson();

    }

    public MainJsonConfigSingleFile setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
        return this;
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public void buildMainConfigJson(){
        JsonObject jsonObject = getJsonObject();

        if(jsonObject.size() == 0) {
            JsonObject jsonConfig = new JsonObject();
            {
                if (!getFolderLocation().equals("")) {
                    jsonConfig.addProperty("folder_location", getFolderLocation());
                } else {
                    Path path = Paths.get(getJsonFolderLocation() + "/default");
                    setFolderLocation(path.toFile().toString());
                    jsonConfig.addProperty("folder_location", getFolderLocation());
                }

                if (jsonConfig.size() > 0) {
                    jsonObject.add("json_config", jsonConfig);
                }
            }

            saveJson(jsonObject);
        }else {
            reloadMainConfigFromJson();
        }
    }

    public void reloadMainConfigFromJson(){

        if(getJsonFileLocation().toFile().exists()) {
            JsonObject jsonObject = getJsonObject();{

                JsonObject jsonConfig = jsonObject.getAsJsonObject("json_config");
                setFolderLocation(jsonConfig.get("folder_location").getAsString());
            }
        }else{
            IndustrialTech.LOGGER.fatal("main json config doesn't exist at (" + getJsonFileLocation().toFile() + ").");
            //Minecraft.crash(new CrashReport("main json config doesn't exist at (" + jsonFileLocation.toFile().getAbsolutePath() + ").", new Throwable()));
        }
    }

    public void reloadMainConfigJson(){
        JsonObject jsonObject = getJsonObject();{
            JsonObject jsonConfig = new JsonObject();{
                if (folderLocation != null) {
                    jsonConfig.addProperty("folder_location", folderLocation);
                }

                if (jsonConfig.size() > 0) {
                    jsonObject.add("json_config", jsonConfig);
                }
            }

            saveJson(jsonObject);
        }
    }


}
