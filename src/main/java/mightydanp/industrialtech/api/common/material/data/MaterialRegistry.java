package mightydanp.industrialtech.api.common.material.data;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class MaterialRegistry {
    private final Logger logger = LogManager.getLogger();
    private final Map<String, ITMaterial> materialList = new LinkedHashMap<>();
    private final Map<String, JsonObject> materialJsonList = new LinkedHashMap<>();

    public Map<String, ITMaterial> grabSerializableMaterials() {
        return materialList;
    }

    public void initiateMaterials() {
        if(Minecraft.getInstance() != null) {
            String minecraftFolder = Minecraft.getInstance().gameDirectory.getAbsoluteFile().toString().replace(".", "");
            Path materialConfigFolder = Paths.get(minecraftFolder + "config/" + Ref.mod_id + "/material");

            for (final File file : Objects.requireNonNull(materialConfigFolder.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = MaterialSerializer.getJsonObject(Paths.get(file.getAbsolutePath()));
                    if (!materialJsonList.containsValue(jsonObject)) {
                        String materialName = jsonObject.get("name").getAsString();
                        materialJsonList.put(materialName, jsonObject);
                        materialList.put(materialName, MaterialSerializer.getFromJson(jsonObject));
                        ITMaterial material = MaterialSerializer.getFromJson(jsonObject);

                    } else {
                        logger.fatal("[{}] could not be added to material list because a material already exist!!", file.getAbsolutePath());
                    }
                }
            }

            materialList.forEach((modID, material) -> {
                material.save();
                RegistryHandler.MATERIALS.register(material.name, ()-> material);
            });
        }
    }

    public void initiateClientMaterials() {
        materialList.forEach((modID, material) -> {
            material.registerColorForItem();
            material.registerColorForBlock();
        });

    }

    public void writeToBuffer(PacketBuffer buffer) {
        buffer.writeVarInt(materialJsonList.size());

        materialList.forEach((id, material) -> {

            MaterialSerializer.toNetwork(buffer, material);
        });
    }

    public List<ITMaterial> readFromBuffer(PacketBuffer buffer) {
        List<ITMaterial> materials = new ArrayList<>();

        int size = buffer.readVarInt();

        for (int i = 0; i < size; i++) {
            ITMaterial material = MaterialSerializer.fromNetwork(buffer);

            materials.add(material);
        }

        return materials;
    }


}
