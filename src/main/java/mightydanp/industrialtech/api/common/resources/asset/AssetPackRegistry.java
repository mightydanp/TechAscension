package mightydanp.industrialtech.api.common.resources.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.resources.asset.data.*;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AssetPackRegistry {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    public static final Path dataPackFolder = Paths.get("resourcepacks");
    public static final Path industrialTechResourcePack = Paths.get(dataPackFolder + "/IndustrialTech(do not delete)");
    public static final Path industrialTechResources = Paths.get(industrialTechResourcePack + "/assets/" + Ref.mod_id);
    public static final Path blockStatesFolder = Paths.get(industrialTechResources + "/blockstates");
    public static final Path frontFolder = Paths.get(industrialTechResources + "/front");
    public static final Path langFolder = Paths.get(industrialTechResources + "/lang");
    public static final Path modelsFolder = Paths.get(industrialTechResources + "/models");
    public static final Path modelBlockFolder = Paths.get(modelsFolder + "/block");
    public static final Path modelItemFolder = Paths.get(modelsFolder + "/item");
    public static final Path particlesFolder = Paths.get(industrialTechResources + "/particles");
    public static final Path shadersFolder = Paths.get(industrialTechResources + "/shaders");
    public static final Path textsFolder = Paths.get(industrialTechResources + "/texts");
    public static final Path texturesFolder = Paths.get(industrialTechResources + "/textures");

    public static Map<String, BlockStateData> blockStateDataMap = new HashMap<>();
    public static Map<String, BlockModelData> blockModelDataMap = new HashMap<>();
    public static Map<String, ItemModelData> itemModelDataHashMap = new HashMap<>();
    public static Map<String, LangData> langDataMap = new HashMap<>();

    public static void init() {
        //generatePackFolders();
        //generatePackFiles();
        generateBlockStateFiles();
        generateBlockModelFiles();
        generateItemModelFiles();
        generateLangFiles();
    }

    private static void generatePackFiles() {
        File pack = new File(industrialTechResourcePack + "/" + Pack.name);
        Pack.init();
        try {
            if(pack.exists()){
                if(!getJsonObject(pack.getAbsolutePath()).equals(Pack.data)){
                    pack.delete();
                }
            }
            try (BufferedWriter bufferedwriter = Files.newBufferedWriter(Paths.get(pack.getAbsolutePath()))) {
                String s = GSON.toJson(Pack.data);
                bufferedwriter.write(s);
            }
        } catch (IOException ioexception) {
            IndustrialTech.LOGGER.error("cannot delete /or create [{}]", pack.getAbsolutePath().replace(".mcmeta", ""), ioexception);
        }
    }

    private static void generateBlockStateFiles(){
        blockStateDataMap.forEach((s, b) -> {
            IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "blockstates/" + s + ".json"), b.createJson());
            /*
            File file = new File(blockStatesFolder + "/" + s + ".json");
            try {
                if(file.exists()){
                    if(!getJsonObject(file.getAbsolutePath()).equals(b.createJson())){
                        file.delete();
                    }
                }

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
                    String s1 = GSON.toJson(b.createJson());
                    bufferedwriter.write(s1);
                }
            } catch (IOException ioexception) {
                IndustrialTech.LOGGER.error("cannot create blockState Json, (" + s + ").", ioexception);
            }

             */
        });
    }

    private static void generateBlockModelFiles(){
        blockModelDataMap.forEach((s, b) -> {
            IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/block/" + s + ".json"), b.createJson());
            /*
            File parentFolder = new File(modelBlockFolder + (b.getParentFolder() == null ? "" : b.getParentFolder()));
            File file = new File(parentFolder + "/" + s + ".json");

            try {
                if(!parentFolder.exists()){
                    Files.createDirectories(Paths.get(parentFolder.getAbsolutePath()));
                }

                if(file.exists()){
                    if(!getJsonObject(file.getAbsolutePath()).equals(b.createJson())){
                        file.delete();
                    }
                }

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
                    String s1 = GSON.toJson(b.createJson());
                    bufferedwriter.write(s1);
                }
            } catch (IOException ioexception) {
                IndustrialTech.LOGGER.error("cannot create block model Json, (" + s + ").", ioexception);
            }

             */
        });
    }

    private static void generateItemModelFiles(){
        itemModelDataHashMap.forEach((s, b) -> {
            IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "models/item/" + s + ".json"), b.createJson());
            /*
            File parentFolder = new File(modelItemFolder + (b.getParentFolder() == null ? "" : b.getParentFolder()));
            File file = new File(parentFolder + "/" + (s.contains(":") ? s.split(":")[1] : s) + ".json");
            try {
                if(!parentFolder.exists()){
                    Files.createDirectories(Paths.get(parentFolder.getAbsolutePath()));
                }

                if(file.exists()){
                    if(!getJsonObject(file.getAbsolutePath()).equals(b.createJson())){
                        file.delete();
                    }
                }

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
                    String s1 = GSON.toJson(b.createJson());
                    bufferedwriter.write(s1);
                }
            } catch (IOException ioexception) {
                IndustrialTech.LOGGER.error("cannot create item model Json, (" + s + ").", ioexception);
            }

             */
        });
    }

    private static void generateLangFiles(){
        langDataMap.forEach((s, b) -> {
            IndustrialTech.assetHolder.addToResources(new ResourceLocation(Ref.mod_id, "lang/" + s + ".json"), b.translations);
            /*
            File file = new File(langFolder + "/" + s + ".json");
            try {
                if(file.exists()){
                    if(!getJsonObject(file.getAbsolutePath()).equals(b.translations)){
                        file.delete();
                    }
                }

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(Paths.get(file.getAbsolutePath()))) {
                    String s1 = GSON.toJson(b.translations);
                    bufferedwriter.write(s1);
                }

            }catch (IOException ioexception) {
                IndustrialTech.LOGGER.error("cannot create language Json, (" + s + ").", ioexception);
            }

             */

        });


    }

    private static void generatePackFolders(){


        try {
            if(!dataPackFolder.toFile().exists()) {
                Files.createDirectories(dataPackFolder);
            }

            if(!industrialTechResourcePack.toFile().exists()) {
                Files.createDirectories(industrialTechResourcePack);
            }

            if(!industrialTechResources.toFile().exists()) {
                Files.createDirectories(industrialTechResources);
            }

            if(!blockStatesFolder.toFile().exists()) {
                Files.createDirectories(blockStatesFolder);
            }

            if(!frontFolder.toFile().exists()) {
                Files.createDirectories(frontFolder);
            }

            if(!langFolder.toFile().exists()) {
                Files.createDirectories(langFolder);
            }

            if(!modelsFolder.toFile().exists()) {
                Files.createDirectories(modelsFolder);
            }

            if(!modelBlockFolder.toFile().exists()) {
                Files.createDirectories(modelBlockFolder);
            }

            if(!modelItemFolder.toFile().exists()) {
                Files.createDirectories(modelItemFolder);
            }

            if(!particlesFolder.toFile().exists()) {
                Files.createDirectories(particlesFolder);
            }

            if(!shadersFolder.toFile().exists()) {
                Files.createDirectories(shadersFolder);
            }

            if(!textsFolder.toFile().exists()) {
                Files.createDirectories(textsFolder);
            }

            if(!texturesFolder.toFile().exists()) {
                Files.createDirectories(texturesFolder);
            }
        }catch (IOException ioexception) {
            IndustrialTech.LOGGER.error("failed to generate pack ", ioexception);
        }
    }

    private static JsonObject getJsonObject(String filePath) {
        JsonObject jsonObject = new JsonObject();
        Path file;
        if(filePath.contains(".json")){
            file = Paths.get(filePath);
        }else{
            file = Paths.get(filePath + ".json");
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
            }else{
                return new JsonObject();
            }
        } catch (IOException ioexception) {
            IndustrialTech.LOGGER.error("Couldn't read json {}", file.getFileName().toString().replace(".json", ""), ioexception);
        }
        return jsonObject;
    }

    /*
    @Override
    public void loadPacks(Consumer<ResourcePackInfo> resourcePackInfoConsumer, ResourcePackInfo.IFactory iFactory) {
        resourcePackInfoConsumer.accept();
    }

     */
}
