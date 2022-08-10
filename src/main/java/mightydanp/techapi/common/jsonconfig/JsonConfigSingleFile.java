package mightydanp.techapi.common.jsonconfig;

import com.google.gson.*;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.libs.Ref;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MightyDanp on 1/16/2022.
 */
public class JsonConfigSingleFile {

    public Map<String, JsonObject> config = new HashMap<>();
    private final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private String jsonFolderLocation;
    private String jsonFilename;
    private Path jsonFileLocation;


    public void initiate(){
        jsonFileLocation = Paths.get(jsonFolderLocation + "/" + Ref.mod_id + jsonFilename);
        buildConfigJson(getJsonObject());
    }

    public JsonConfigSingleFile addToCategoryInConfig(String category, JsonObject jsonObject){
        config.put(category, jsonObject);
        return this;
    }

    public JsonElement getConfigFromCategory(String category, String config){
        if(this.config.getOrDefault(category, new JsonObject()).get(config) != null) {
            return this.config.getOrDefault(category, new JsonObject()).get(config);
        } else {
            return new JsonObject();
        }
    }

    public void setJsonFilename(String jsonFilenameIn) {
        jsonFilename = "/" + jsonFilenameIn + ".json";
    }

    public String getJsonFilename() {
        return jsonFilename;
    }

    public void setJsonFolderLocation(String jsonFolderLocationIn) {
        jsonFolderLocation = jsonFolderLocationIn;
    }

    public String getJsonFolderLocation() {
        return jsonFolderLocation;
    }

    public Path getJsonFileLocation() {
        return jsonFileLocation;
    }

    public Gson getGSON() {
        return GSON;
    }

    public void buildConfigJson(JsonObject jsonObject){
        if(getJsonFileLocation().toFile().exists()) {
            JsonObject jsonObjectNew = getJsonObject();

            JsonObject test = new JsonObject();
            config.forEach(test::add);

            if(!jsonObjectNew.equals(test)) {
                saveJson(jsonObject);
            }
            /*else {
                    jsonObjectNew.keySet().forEach(category -> {
                        JsonObject jsonConfig = jsonObjectNew.getAsJsonObject(category);
                        jsonObject.add(category, jsonConfig);
                        config.put(category, jsonConfig);
                    });
                }

             */
        } else {
            saveJson(jsonObject);
        }
    }

    public void saveJson(JsonObject jsonConfig) {
        if(getJsonFileLocation() != null) {
            try {
                getJsonFileLocation().toFile().delete();
                Files.createDirectories(getJsonFileLocation().getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(getJsonFileLocation())) {
                    String s = getGSON().toJson(jsonConfig);
                    bufferedwriter.write(s);
                }
            } catch (IOException ioexception) {
                TechAscension.LOGGER.error(jsonFilename + " cannot be deleted /or created [{}]", getJsonFileLocation().getFileName().toString().replace(".json", ""), ioexception);
            }
        }
    }

    public JsonObject getJsonObject() {
        JsonObject jsonObject = new JsonObject();

        try {
            if (jsonFileLocation != null && Files.exists(jsonFileLocation)) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(jsonFileLocation)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        //System.out.println(line);
                    }

                    jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

                    return jsonObject;
                }
            }else{
                return new JsonObject();
            }
        } catch (IOException ioexception) {
            TechAscension.LOGGER.error("Couldn't read json {}", jsonFileLocation + jsonFilename, ioexception);
        }
        return jsonObject;
    }
}
