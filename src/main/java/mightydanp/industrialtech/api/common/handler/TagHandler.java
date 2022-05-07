package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.*;
import java.util.function.Supplier;

public class TagHandler {
    private static final Map<String, Set<Supplier<Block>>> blockTags = new HashMap<>();
    private static final Map<String, Set<Supplier<Item>>> itemTags = new HashMap<>();

    public static void addBlockToTag(String tagIn, Supplier<Block> blockIn){
        Set<Supplier<Block>> blocks = blockTags.getOrDefault(tagIn, new HashSet<>());
        blocks.add(blockIn);

        blockTags.put(tagIn, blocks);
    }

    public static void deleteBlockFromTag(String tagIn, Supplier<Block> blockIn){
        Set<Supplier<Block>> blocks = blockTags.getOrDefault(tagIn, new HashSet<>());

        blocks.remove(blockIn);

        blockTags.put(tagIn, blocks);
    }

    public static void addItemToTag(String tagIn, Supplier<Item> itemIn){
        Set<Supplier<Item>> items = itemTags.getOrDefault(tagIn, new HashSet<>());
        items.add(itemIn);

        itemTags.put(tagIn, items);
    }

    public static void deleteItemFromTag(String tagIn, Supplier<Item> itemIn){
        Set<Supplier<Item>> items = itemTags.getOrDefault(tagIn, new HashSet<>());

        items.remove(itemIn);

        itemTags.put(tagIn, items);
    }

    public static void initTags(){
        blockTags.forEach((tags, blocks) ->{
            if(ForgeRegistries.BLOCKS.tags().stream().anyMatch(tag -> tag.getKey().location().equals(new ResourceLocation(Ref.mod_id, tags)))){
                List<ITag<Block>> list = ForgeRegistries.BLOCKS.tags().stream().filter(tag -> tag.getKey().location().equals(new ResourceLocation(Ref.mod_id, tags))).toList();
                Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).addOptionalTagDefaults(list.get(0).getKey(), blocks);
            }else {
                Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(new ResourceLocation(Ref.mod_id, tags), blocks);
            }
        });

        itemTags.forEach((tags, items) ->{
            if(ForgeRegistries.ITEMS.tags().stream().anyMatch(tag -> tag.getKey().location().equals(new ResourceLocation(Ref.mod_id, tags)))){
                List<ITag<Item>> list = ForgeRegistries.ITEMS.tags().stream().filter(tag -> tag.getKey().location().equals(new ResourceLocation(Ref.mod_id, tags))).toList();
                Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).addOptionalTagDefaults(list.get(0).getKey(), items);
            }else {
                Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(new ResourceLocation(Ref.mod_id, tags), items);
            }
        });

    }
}
