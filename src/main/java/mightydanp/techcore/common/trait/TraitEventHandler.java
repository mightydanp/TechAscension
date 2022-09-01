package mightydanp.techcore.common.trait;

import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.trait.item.IItemTrait;
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
        if (registry.registryMap.values().stream().anyMatch(iItemTrait -> iItemTrait.getRegistry().equals(registryName))) {
            IItemTrait itemTrait = registry.registryMap.get(registryName);

            event.getToolTip().add(new TextComponent("color: " + itemTrait.getColor()));
            event.getToolTip().add(new TextComponent("durability: " + itemTrait.getMaxDamage()));
            event.getToolTip().add(new TextComponent("texture_icon: " + itemTrait.getTextureIcon()));


            if (itemTrait.getPounds() != null) {
                event.getToolTip().add(new TextComponent("pounds: " + itemTrait.getPounds()));
            }

            if (itemTrait.getKilograms() != null) {
                event.getToolTip().add(new TextComponent("kilograms: " + itemTrait.getKilograms()));
            }

            if (itemTrait.getMeters() != null) {
                event.getToolTip().add(new TextComponent("meters: " + itemTrait.getMeters()));
            }

            if (itemTrait.getYards() != null) {
                event.getToolTip().add(new TextComponent("yards: " + itemTrait.getKilograms()));
            }
        }
    }


}
