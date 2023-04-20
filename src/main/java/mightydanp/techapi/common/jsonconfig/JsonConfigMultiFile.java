package mightydanp.techapi.common.jsonconfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mightydanp.techascension.common.TechAscension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by MightyDanp on 1/18/2022.
 */
public class JsonConfigMultiFile<T> extends IJsonConfig<T> {

    @Override
    public void saveJsonObject(String name, JsonObject jsonConfig) {
        Path file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name + ".json");
        if(!getJsonObject(name).equals(jsonConfig)) {
            try {
                file.toFile().delete();
                Files.createDirectories(file.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(file)) {
                    String s = getGSON().toJson(jsonConfig);
                    bufferedwriter.write(s);
                }
            } catch (IOException ioexception) {
                TechAscension.LOGGER.error(name + " cannot be deleted /or created [{}]", file.getFileName().toString().replace(".json", ""), ioexception);
            }
        }
    }

    @Override

    public JsonObject getJsonObject(String name) {
        JsonObject jsonObject = new JsonObject();
        Path file;

        if(name.contains(".json")){
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name);
        }else{
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name + ".json");
        }

        try {
            if (Files.exists(file)) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    jsonObject = JsonParser.parseString(stringBuilder.toString()).getAsJsonObject();

                    return jsonObject;
                }
            }else{
                return new JsonObject();
            }
        } catch (IOException ioexception) {
            TechAscension.LOGGER.error("Couldn't read json {}", file.getFileName().toString().replace(".json", ""), ioexception);
        }
        return jsonObject;
    }
}