package mightydanp.industrialtech.api.common.material.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.IFinishedMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;


/**
 * Created by MightyDanp on 12/1/2021.
 */
public class MaterialJsonProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final Map<MaterialBuilder, String> readyToBuiltMaterials = new HashMap<>();

    public void buildMaterialJson(){
        Set<ResourceLocation> set = Sets.newHashSet();
        buildMaterial((iFinishedMaterial) -> {
            if (!set.add(iFinishedMaterial.getId())) {
                throw new IllegalStateException("There is a Duplicate material with ID : " + iFinishedMaterial.getId());
            } else {
                if(Minecraft.getInstance() != null){
                    String minecraftFolder = Minecraft.getInstance().gameDirectory.getAbsoluteFile().toString().replace(".", "");
                    String materialConfigFolder = minecraftFolder + "config/" + Ref.mod_id + "/material/";
                    String materialName = JSONUtils.getAsString(iFinishedMaterial.serializeMaterial(), "name");
                    try {
                        saveMaterial(iFinishedMaterial.serializeMaterial(), Paths.get(materialConfigFolder + materialName + ".json"));
                    } catch (FileNotFoundException e) {
                        LOGGER.fatal("registered material json " + materialName + " does not exist (" + materialConfigFolder + materialName + ".json" + ") This will cause to many problems!");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void buildMaterial(Consumer<IFinishedMaterial> iFinishedMaterialConsumer){
        readyToBuiltMaterials.forEach((materialBuilder, modId)-> {
            materialBuilder.save(iFinishedMaterialConsumer, modId);
        });
    }

    private static void saveMaterial(JsonObject jsonObject, Path path) throws FileNotFoundException{
        try {
            String s = GSON.toJson(jsonObject);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
                    bufferedwriter.write(s);
                }
            }else{
                LOGGER.info("material [{}] has already been generated. If you do not need to generate this json again please comment out your material in your material handler class.", path.getFileName().toString().replace(".json", ""));
            }
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save material [{}]", path.getFileName().toString().replace(".json", ""), ioexception);
        }
    }

    public Pair<MaterialBuilder, String> addCreateMaterialPair(MaterialBuilder materialIn, String modIdIn){
        return new Pair<>(materialIn, modIdIn);
    }

    public void addMaterial(MaterialBuilder materialIn, String modIdIn){
        readyToBuiltMaterials.put(materialIn, modIdIn);
    }



    /*
    default JsonObject serializeMaterial() throws FileNotFoundException {
        JsonObject jsonobject = new JsonObject();
        this.serializeMaterialData(jsonobject);
        File materialFolder = new File(minecraftFolder + "config/" + Ref.mod_id + "/material/");
        PrintWriter pw = new PrintWriter(materialFolder.getAbsoluteFile() + JSONUtils.getAsString(jsonobject, "name"));
        pw.write(jsonobject.getAsString());

        pw.flush();
        pw.close();
        return jsonobject;
    }
     */
}
