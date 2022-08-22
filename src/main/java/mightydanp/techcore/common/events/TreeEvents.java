package mightydanp.techcore.common.events;

import mightydanp.techascension.mixin.BlockBehaviourMixin;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class TreeEvents {
    /*
    @SubscribeEvent
    public static void noCollisionLeaves(FMLCommonSetupEvent event){
        Set<Block> leaves = new HashSet<>(new HashSet<>(ForgeRegistries.BLOCKS.getValues()).stream().filter(block -> {
            String registryName = Objects.requireNonNull(block.getRegistryName()).toString();
            return registryName.contains("leaf") || registryName.contains("Leaf");
        }).toList());

        for(Block block :leaves){
            if(block instanceof LeavesBlock leave){
                ((BlockBehaviourMixin)(ForgeRegistryEntry<Block>)leave).setHasCollision(false);
            }
        }
    }

     */
}
