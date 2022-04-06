package mightydanp.industrialtech.api.common.jsonconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by MightyDanp on 1/16/2022.
 */
public class JsonConfigSingleFile {
    private final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private String jsonFolderLocation;
    private String jsonFilename;
    private Path jsonFileLocation;


    public void initiate(){
        jsonFileLocation = Paths.get(jsonFolderLocation + Ref.mod_id + jsonFilename);
        String s =this.jsonFilename;
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
                IndustrialTech.LOGGER.error(jsonFilename + " cannot be deleted /or created [{}]", getJsonFileLocation().getFileName().toString().replace(".json", ""), ioexception);
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
            IndustrialTech.LOGGER.error("Couldn't read json {}", jsonFileLocation + jsonFilename, ioexception);
        }
        return jsonObject;
    }
}
