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

public class JsonConfigMultiFileDirectory<T> extends IJsonConfig<T> {
    @Override
    public void saveJsonObject(String name, JsonObject jsonConfig) {
        Path path = Paths.get(getJsonFolderLocation() + "/" + getJsonFolderName() + "/" + name.split(":")[0]);
        Path file = Paths.get(path + "/" + name.split(":")[1] + ".json");

        if (!getJsonObject(name).equals(jsonConfig)) {
            try {
                file.toFile().delete();
                Files.createDirectories(file.getParent().getParent());
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

        if (name.contains(".json")) {
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name.split(":")[0] + "/" + name.split(":")[1]);
        } else {
            file = Paths.get(getJsonFolderLocation() + "/" + jsonFolderName + "/" + name.split(":")[0] + "/" + name.split(":")[1] + ".json");
        }

        try {
            if (Files.exists(file)) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        //System.out.println(line);
                    }

                    jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

                    return jsonObject;
                }
            } else {
                return new JsonObject();
            }
        } catch (IOException ioexception) {
            TechAscension.LOGGER.error("Couldn't read json {}", file.getFileName().toString().replace(".json", ""), ioexception);
        }
        return jsonObject;
    }
}