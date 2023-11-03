package mightydanp.techapi.common.resources;

import mightydanp.techapi.common.jsonconfig.IJsonConfig;
import mightydanp.techapi.common.jsonconfig.sync.gui.lang.SyncScreenLang;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.IItems;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.TCMaterial;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
@Mod.EventBusSubscriber(modid = Ref.mod_id, value= Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerResourcePackEventHandler {
    @SuppressWarnings("all")
    public static List<IJsonConfig> postInitLoad = new ArrayList<>();

    @SubscribeEvent
    public static void addResourcePack(AddPackFindersEvent event) {
        postInitLoad.forEach(IJsonConfig::loadExistingJsons);

        DataPackRegistry.init();

        PackType type = event.getPackType();

        if (type == PackType.SERVER_DATA) {
            event.addRepositorySource((packConsumer, constructor) -> {
                Pack pack = Pack.create(Ref.mod_id + ":data", true, () -> TechAscension.dataHolder, constructor, Pack.Position.TOP, PackSource.DEFAULT);
                packConsumer.accept(pack);
            });
        }
    }
}