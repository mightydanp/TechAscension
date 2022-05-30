package mightydanp.industrialtech.api.common.resources;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ResourcePackEventHandler {

    @SubscribeEvent
    public static void addResourcePack(AddPackFindersEvent event){
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
