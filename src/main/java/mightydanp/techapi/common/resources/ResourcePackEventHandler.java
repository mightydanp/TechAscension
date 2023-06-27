package mightydanp.techapi.common.resources;

import mightydanp.techapi.common.resources.asset.data.IItems;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.material.TCMaterial;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ResourcePackEventHandler {
    public static List<IItems> itemResources = new ArrayList<>();

    @SubscribeEvent
    public static void addResourcePack(AddPackFindersEvent event){
        ((ToolRegistry)TCJsonConfigs.tool.getFirst()).loadExistJson();
        ((MaterialRegistry)TCJsonConfigs.material.getFirst()).registryMap.forEach((modID, material) -> {
            try {
                material.saveResources();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        TCMaterial.saveOnceResources();

        itemResources.forEach(IItems::initResource);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AssetPackRegistry::init);

        DataPackRegistry.init();

        PackType type = event.getPackType();

        if (type == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource((packConsumer, constructor) -> {
                Pack pack = Pack.create(Ref.mod_id + ":assets", true, ()-> TechAscension.assetHolder, constructor, Pack.Position.TOP, PackSource.DEFAULT);
                packConsumer.accept(pack);
            });
        }

        if (type == PackType.SERVER_DATA) {
            event.addRepositorySource((packConsumer, constructor) -> {
                Pack pack = Pack.create(Ref.mod_id + ":data", true, ()-> TechAscension.dataHolder, constructor, Pack.Position.TOP, PackSource.DEFAULT);
                packConsumer.accept(pack);
            });
        }
    }

}
