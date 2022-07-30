package mightydanp.techcore.common.handler;

import mightydanp.techcore.common.material.fluid.TCBucket;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.world.item.Item;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/22/2021.
 */
public class FluidHandler {
    public String name;
    public int temperature;
    public RegistryObject<Item> bucket;

    public FluidHandler(String nameIn, int temperateIn){
        name = nameIn;
        temperature = temperateIn;
    }

    public void initProperties() {
            if(bucket.isPresent()) {
                TCBucket iTBucket = (TCBucket)bucket.get();
                ItemProperties.register(bucket.get(), new ResourceLocation(Ref.mod_id, "redness_level"), (stack, world, living, dsf) -> {
                    if (living != null){
                        return iTBucket.getRednessLevel();
                    }

                    return -1;
                });
            }
        }
}
