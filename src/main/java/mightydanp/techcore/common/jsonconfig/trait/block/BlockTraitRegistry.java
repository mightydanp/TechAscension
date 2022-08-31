package mightydanp.techcore.common.jsonconfig.trait.block;

import com.google.gson.JsonObject;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BlockTraitRegistry extends JsonConfigMultiFile<IBlockTrait> {

    @Override
    public void initiate() {
        setJsonFolderName("trait/block");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        /*
        for (DefaultBlockTrait blockTrait : DefaultBlockTrait.values()) {
            register(blockTrait);
        }
        */

        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(IBlockTrait blockTraitIn) {
        String registry = blockTraitIn.getRegistry().split(":")[1];
        if (registryMap.containsKey(registry))
            throw new IllegalArgumentException("block trait for registry block(" + registry + "), already exists.");
        registryMap.put(registry, blockTraitIn);
    }

    public IBlockTrait getBlockTraitByName(String block_trait) {
        return registryMap.get(block_trait);
    }

    public Set<IBlockTrait> getAllBlockTrait() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(IBlockTrait blockTrait : registryMap.values()) {
            String registry = blockTrait.getRegistry().split(":")[1];
            JsonObject jsonObject = getJsonObject(registry);

            if (jsonObject.size() == 0) {
                this.saveJsonObject(registry, toJsonObject(blockTrait));
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        IBlockTrait blockTrait = fromJsonObject(jsonObject);
                        String registry = blockTrait.getRegistry().split(":")[1];

                        registryMap.put(registry, blockTrait);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to block trait because a block trait already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("block trait json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IBlockTrait fromJsonObject(JsonObject jsonObjectIn){
        return new IBlockTrait() {
            @Override
            public String getRegistry() {
                return jsonObjectIn.get("registry").getAsString();
            }

            @Override
            public Integer getColor() {
                return jsonObjectIn.get("color").getAsInt();
            }

            @Override
            public Double getPounds() {
                return jsonObjectIn.get("pounds").getAsDouble();
            }

            @Override
            public Double getKilograms() {
                return jsonObjectIn.get("kilograms").getAsDouble();
            }
        };
    }

    public JsonObject toJsonObject(IBlockTrait blockTrait) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("registry", blockTrait.getRegistry());
        jsonObject.addProperty("color", blockTrait.getColor());

        if(blockTrait.getPounds() != null) {
            jsonObject.addProperty("pounds", blockTrait.getPounds());
        }

        if(blockTrait.getKilograms() != null) {
            jsonObject.addProperty("kilograms", blockTrait.getKilograms());
        }

        return jsonObject;
    }
}