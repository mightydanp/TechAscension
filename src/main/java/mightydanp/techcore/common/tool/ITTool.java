package mightydanp.techcore.common.tool;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.*;
import mightydanp.techcore.common.jsonconfig.flag.DefaultMaterialFlag;
import mightydanp.techcore.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.IMaterial;
import mightydanp.techcore.common.material.ITMaterial;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.ItemModelData;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techcore.common.tool.part.*;
import mightydanp.techascension.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Created by MightyDanp on 3/8/2021.
 */

public class ITTool {
    public String toolName;
    public int hitDamage;
    public List<String> toolParts = new ArrayList<>();

    public Set<String> effectiveBlocks;

    public Map<String, Integer> assembleItems;
    public Map<String, Integer> parts;
    public List<String> disassembleItems;

    public RegistryObject<Item> toolItem;
    public List<RegistryObject<Item>> materialParts = new ArrayList<>();
    public PartHolders.handlePartHolder handle;
    public PartHolders.dullHeadPartHolder dullHead;

    public PartHolders.headPartHolder head;
    public PartHolders.bindingPartHolder binding;

    public ITTool(String nameIn, int hitDamageIn, Set<String> effectiveBlocksIn, Map<String, Integer> assembleItemsIn, Map<String, Integer> partsIn, List<String> disassembleItemsIn, PartHolders.handlePartHolder handleIn, PartHolders.dullHeadPartHolder dullHeadIn, PartHolders.headPartHolder headIn, PartHolders.bindingPartHolder bindingIn, RegistryObject<Item> toolItemIn) {
        toolName = nameIn;
        hitDamage = hitDamageIn;

        effectiveBlocks = effectiveBlocksIn;

        assembleItems = assembleItemsIn;
        parts = partsIn;
        disassembleItems = disassembleItemsIn;

        toolItem = toolItemIn;

        if(handleIn != null){
            handle = handleIn;
            toolParts.add(fixesToName(handle.prefixAndSuffix()));
        }

        if(dullHeadIn != null){
            dullHead = dullHeadIn;
            toolParts.add(fixesToName(dullHead.prefixAndSuffix()));
        }

        if(headIn != null){
            head = headIn;
            toolParts.add(fixesToName(head.prefixAndSuffix()));
        }

        if(bindingIn != null){
            binding = bindingIn;
            toolParts.add(fixesToName(binding.prefixAndSuffix()));
        }

        ITMaterial.extraSave.put(nameIn, new IMaterial() {
            @Override
            public void save(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolPart, List<IMaterialFlag> materialFlag) {
                if (materialFlag.contains(DefaultMaterialFlag.TOOL)) {
                    if (handle != null) {
                        String handlePartName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();
                        if (!material.extraSaveItems.containsKey(handlePartName)) {
                            RegistryObject<Item> part = RegistryHandler.ITEMS.register(handlePartName, () -> {
                                try {
                                    return handle.part().newInstance().setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color)
                                            .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol).disabled(toolPart.contains(handle.prefixAndSuffix()) && (material.toolParts.contains(handle.prefixAndSuffix())));
                                } catch (InstantiationException | IllegalAccessException e) {
                                    return null;
                                }
                            });

                            material.extraSaveItems.put(handlePartName, part);
                            materialParts.add(part);
                        }
                    }

                    if (head != null) {
                        if (dullHead != null) {
                            String headPartName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();
                            if (!material.extraSaveItems.containsKey(headPartName)) {
                                RegistryObject<Item> part = RegistryHandler.ITEMS.register(headPartName, () -> {
                                    try {
                                        return dullHead.part().newInstance().setMaterial(material.name).setColor(material.color)
                                                .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol).disabled(toolPart.contains(dullHead.prefixAndSuffix()) && (material.toolParts.contains(dullHead.prefixAndSuffix())));
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        return null;
                                    }
                                });

                                material.extraSaveItems.put(headPartName, part);
                                materialParts.add(part);
                            }

                            String partName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();
                            if (!material.extraSaveItems.containsKey(partName)) {
                                Map<String, Integer> toolTypesList = new HashMap<>();

                                if (toolParts == null || toolParts.size() == 0) {
                                    IndustrialTech.LOGGER.warn("What the fuck is this shit? You didn't set a type for your tool, you tool! ~Bear989");
                                } else {
                                    toolParts.forEach((s) -> toolTypesList.put(s, material.toolLevel));
                                }

                                RegistryObject<Item> part = RegistryHandler.ITEMS.register(partName, () -> {
                                    try {
                                        return head.part().newInstance().setMaterial(material.name).setColor(material.color)
                                                .setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color).setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol)
                                                .setItToolType(toolTypesList).setSuggestedCraftedTool("").setAttackDamage(material.attackDamage).setEfficiency(material.efficiency).disabled(toolPart.contains(head.prefixAndSuffix()) && (material.toolParts.contains(head.prefixAndSuffix())));
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        return null;
                                    }
                                });

                                material.extraSaveItems.put(partName, part);
                                materialParts.add(part);
                            }
                        }
                    }

                    if (binding != null) {
                        String bindingPartName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();
                        if (!material.extraSaveItems.containsKey(bindingPartName)) {

                            RegistryObject<Item> part = RegistryHandler.ITEMS.register(bindingPartName, () -> {
                                try {
                                    return binding.part().newInstance().setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color)
                                            .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol).disabled(toolPart.contains(binding.prefixAndSuffix()) && (material.toolParts.contains(binding.prefixAndSuffix())));
                                } catch (InstantiationException | IllegalAccessException e) {
                                    return null;
                                }
                            });

                            material.extraSaveItems.put(bindingPartName, part);
                            materialParts.add(part);
                        }
                    }
                }
            }
            @Override
            public void clientRenderLayerInit(ITMaterial material) {
            }

            @Override
            public void registerColorForBlock(ITMaterial material) {

            }

            @Override
            public void registerColorForItem(ITMaterial material) {
                if (material.materialFlags.contains(DefaultMaterialFlag.TOOL)) {
                    registerAToolItemColor(toolItem);
                    if (handle != null && material.toolParts.contains(handle.prefixAndSuffix()) && material.toolParts.contains(handle.prefixAndSuffix())) {

                        String materialToolPartName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();

                        ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, materialToolPartName);

                        if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                            Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                            registerAItemColor(material, item, 0);
                        }
                    }

                    if (head != null && material.toolParts.contains(head.prefixAndSuffix()) && material.toolParts.contains(head.prefixAndSuffix())) {
                        if (dullHead != null && material.toolParts.contains(dullHead.prefixAndSuffix()) && material.toolParts.contains(dullHead.prefixAndSuffix())) {
                            String materialToolPartName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();
                            ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, materialToolPartName);

                            if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                                Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                                registerAItemColor(material, item, 0);
                            }
                        }

                        String materialToolPartName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();
                        ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, materialToolPartName);

                        if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                            Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                            registerAItemColor(material, item, 0);
                        }
                    }

                    if (binding != null && material.toolParts.contains(binding.prefixAndSuffix()) && material.toolParts.contains(binding.prefixAndSuffix())) {
                        String materialToolPartName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();
                        ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, materialToolPartName);

                        if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                            Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                            registerAItemColor(material, item, 0);
                        }
                    }
                }
            }
        });

        ITMaterial.extraSaveResources.put(nameIn, (material, stoneLayerList, toolParts, materialFlag) -> {
            LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
            ItemModelData data = new ItemModelData().setParent(new ResourceLocation("minecraft", "item/generated"));

            if (materialFlag.contains(DefaultMaterialFlag.TOOL)) {
                int i = materialParts.size();
                if(dullHead != null){
                    i = i-1;
                }
                if (i == 3) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix().getFirst() + (handle.special() ? toolName : "") + handle.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix().getFirst() + (head.special() ? toolName : "") + head.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer2", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + binding.prefixAndSuffix().getFirst() + (binding.special() ? toolName : "") + binding.prefixAndSuffix().getSecond()));
                } else if (i == 2) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix().getFirst() + (handle.special() ? toolName : "") + handle.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix().getFirst() + (head.special() ? toolName : "") + head.prefixAndSuffix().getSecond()));
                } else if (i == 1) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_overlay"));
                }

                AssetPackRegistry.itemModelDataHashMap.put(toolName, data);

                if (handle != null && toolParts.contains(handle.prefixAndSuffix()) && material.toolParts.contains(handle.prefixAndSuffix())) {
                    String materialToolPartName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();
                    String toolPartName = handle.prefixAndSuffix().getFirst() + (handle.special() ? toolName : "") + handle.prefixAndSuffix().getSecond();

                    if (material.extraSaveItems.containsKey(materialToolPartName)) {
                        //--Item--\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put(materialToolPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + toolPartName)));
                        enLang.addTranslation("item." + Ref.mod_id + "." + toolPartName, LangData.translateUpperCase(toolPartName));
                        //TagHandler.addItemToTag("dull_pickaxe_head", new ResourceLocation(Ref.mod_id, "dull_" + name + "_pickaxe_head"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName + "/" + material.name)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        //--LootTable
                    }

                }

                if (head != null && toolParts.contains(head.prefixAndSuffix()) && material.toolParts.contains(head.prefixAndSuffix())) {
                    if (dullHead != null) {
                        String materialToolPartName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();
                        String toolPartName = dullHead.prefixAndSuffix().getFirst() + (dullHead.special() ? toolName : "") + dullHead.prefixAndSuffix().getSecond();

                        if (material.extraSaveItems.containsKey(materialToolPartName)) {
                            //--Item--\\
                            //--Resources
                            AssetPackRegistry.itemModelDataHashMap.put(materialToolPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                    .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + toolPartName)));
                            enLang.addTranslation("item." + Ref.mod_id + "." + toolPartName, LangData.translateUpperCase(toolPartName));
                            //TagHandler.addItemToTag("dull_pickaxe_head", new ResourceLocation(Ref.mod_id, "dull_" + name + "_pickaxe_dullHead"));
                            //--Tags
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName + "/" + material.name)).add(material.extraSaveItems.get(materialToolPartName).get()));
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName)).add(material.extraSaveItems.get(materialToolPartName).get()));
                            //--LootTable
                        }
                    }

                    String materialToolPartName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();
                    String toolPartName = head.prefixAndSuffix().getFirst() + (head.special() ? toolName : "") + head.prefixAndSuffix().getSecond();

                    if (material.extraSaveItems.containsKey(materialToolPartName)) {
                        //--Item--\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put(materialToolPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + toolPartName)));
                        enLang.addTranslation("item." + Ref.mod_id + "." + toolPartName, LangData.translateUpperCase(toolPartName));
                        //TagHandler.addItemToTag("dull_pickaxe_head", new ResourceLocation(Ref.mod_id, "dull_" + name + "_pickaxe_head"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName + "/" + material.name)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        //--LootTable
                    }
                }

                if (binding != null && toolParts.contains(binding.prefixAndSuffix()) && material.toolParts.contains(binding.prefixAndSuffix())) {
                    String materialToolPartName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();
                    String toolPartName = binding.prefixAndSuffix().getFirst() + (binding.special() ? toolName : "") + binding.prefixAndSuffix().getSecond();

                    if (material.extraSaveItems.containsKey(materialToolPartName)){
                        //--Item--\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put(materialToolPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + toolPartName)));
                        enLang.addTranslation("item." + Ref.mod_id + "." + materialToolPartName, LangData.translateUpperCase(materialToolPartName));
                        //TagHandler.addItemToTag("dull_pickaxe_head", new ResourceLocation(Ref.mod_id, "dull_" + name + "_pickaxe_head"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName + "/" + material.name)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        //--LootTable
                    }
                }
            }

            AssetPackRegistry.langDataMap.put("en_us", enLang);
        });
    }




    public void registerAItemColor(ITMaterial material, Item item, int layerNumberIn) {
        if (item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex <= layerNumberIn)
                    return material.color;
                else
                    return 0xFFFFFFFF;
            }, item);
        }
    }

    public void registerAToolItemColor(RegistryObject<Item> item) {
        if (item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0) {
                    return ((ITToolItem) stack.getItem()).getHandleColor(stack);
                } else if (tintIndex == 1) {
                    return (((ITToolItem) stack.getItem()).getHeadColor(stack));
                } else if (tintIndex == 2) {
                    return ((ITToolItem) stack.getItem()).getBindingColor(stack);
                } else return 0;
            }, item.get());
        }
    }

    private static int ColorToInt(int r, int g, int b) {
        int ret = 0;
        ret += r;
        ret = ret << 8;
        ret += g;
        ret = ret << 8;
        ret += b;
        return ret;
    }

    public String fixesToName(Pair<String, String> fixes){
        String prefix = fixes.getFirst().replace("_", "");
        String suffix = fixes.getSecond().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }

}
