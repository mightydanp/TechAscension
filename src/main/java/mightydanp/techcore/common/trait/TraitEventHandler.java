package mightydanp.techcore.common.trait;

import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.trait.item.ItemTraitCodec;
import mightydanp.techcore.common.jsonconfig.trait.item.ItemTraitRegistry;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class TraitEventHandler {

    @SubscribeEvent
    public static void toolTip(ItemTooltipEvent event){
        ItemTraitRegistry registry = (ItemTraitRegistry) TCJsonConfigs.itemTrait.getFirst();
        String registryName = Objects.requireNonNull(event.getItemStack().getItem().getRegistryName()).toString();
        if (registry.registryMap.values().stream().anyMatch(iItemTrait -> iItemTrait.registry().equals(registryName))) {
            ItemTraitCodec itemTrait = registry.registryMap.get(registryName);

            event.getToolTip().add(new TextComponent("color: " + itemTrait.color()));
            event.getToolTip().add(new TextComponent("durability: " + itemTrait.maxDamage()));
            event.getToolTip().add(new TextComponent("texture_icon: " + itemTrait.textureIcon()));

            /*
            if (itemTrait.pounds() != null) {
                event.getToolTip().add(new TextComponent("pounds: " + itemTrait.pounds()));
            }

             */

            if (itemTrait.kilograms() != null) {
                event.getToolTip().add(new TextComponent("kilograms: " + itemTrait.kilograms()));
            }

            if (itemTrait.meters() != null) {
                event.getToolTip().add(new TextComponent("meters: " + itemTrait.meters()));
            }

            /*
            if (itemTrait.yards() != null) {
                event.getToolTip().add(new TextComponent("yards: " + itemTrait.yards()));
            }
             */
        }
    }


}
