package mightydanp.industrialcore.common.resources;

import mightydanp.industrialcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.resources.asset.AssetPackRegistry;
import mightydanp.industrialcore.common.resources.data.DataPackRegistry;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ResourcePackEventHandler {

    @SubscribeEvent
    public static void addResourcePack(AddPackFindersEvent event){
        ((MaterialRegistry)IndustrialTech.configSync.material.getFirst()).registryMap.forEach((modID, material) -> material.saveResources());

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AssetPackRegistry::init);

        DataPackRegistry.init();

        PackType type = event.getPackType();

        if (type == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource((packConsumer, constructor) -> {
                Pack pack = Pack.create(Ref.mod_id + ":assets", true, ()-> IndustrialTech.assetHolder, constructor, Pack.Position.TOP, PackSource.DEFAULT);
                packConsumer.accept(pack);
            });
        }

        if (type == PackType.SERVER_DATA) {
            event.addRepositorySource((packConsumer, constructor) -> {
                Pack pack = Pack.create(Ref.mod_id + ":data", true, ()-> IndustrialTech.dataHolder, constructor, Pack.Position.TOP, PackSource.DEFAULT);
                packConsumer.accept(pack);
            });
        }
    }

}
