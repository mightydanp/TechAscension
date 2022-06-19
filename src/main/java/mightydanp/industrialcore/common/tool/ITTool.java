package mightydanp.industrialcore.common.tool;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.items.*;
import mightydanp.industrialcore.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.material.IMaterial;
import mightydanp.industrialcore.common.material.ITMaterial;
import mightydanp.industrialcore.common.resources.asset.AssetPackRegistry;
import mightydanp.industrialcore.common.resources.asset.data.ItemModelData;
import mightydanp.industrialcore.common.resources.asset.data.LangData;
import mightydanp.industrialcore.common.tool.part.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by MightyDanp on 3/8/2021.
 */

public class ITTool {
    public String toolName;
    public int hitDamage;
    public List<String> toolParts;

    public Set<String> effectiveBlocks;

    public Map<String, Integer> assembleItems;
    public Map<String, Integer> parts;
    public List<String> disassembleItems;

    public RegistryObject<Item> toolItem;

    public ITTool(String nameIn, int hitDamageIn, List<String> toolPartsIn, Set<String> effectiveBlocksIn, Map<String, Integer> assembleItemsIn, Map<String, Integer> partsIn, List<String> disassembleItemsIn, PartHolders.handlePartHolder handle, PartHolders.dullHeadPartHolder dullHead, PartHolders.headPartHolder head, PartHolders.bindingPartHolder binding, RegistryObject<Item> toolItemIn) {
        toolName = nameIn;
        hitDamage = hitDamageIn;
        toolParts = toolPartsIn;

        effectiveBlocks = effectiveBlocksIn;

        assembleItems = assembleItemsIn;
        parts = partsIn;
        disassembleItems = disassembleItemsIn;

        toolItem = toolItemIn;

        Map<String, RegistryObject<Item>> partsList = new HashMap<>();

        ITMaterial.extraSave.put(nameIn, new IMaterial() {
            @Override
            public void save(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolPart, List<IMaterialFlag> materialFlag){
                if (handle != null) {

                        RegistryObject<Item> part = RegistryHandler.ITEMS.register(handle.prefixAndSuffix().getFirst() + material.name + handle.prefixAndSuffix().getSecond(), () -> {
                            try {
                                return handle.part().getDeclaredConstructor().newInstance().setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color)
                                        .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol).disabled(toolPart.contains(handle.prefixAndSuffix()) && (material.toolParts.contains(handle.prefixAndSuffix())));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                return null;
                            }
                        });

                    partsList.put(handle.prefixAndSuffix().getFirst() + material.name + handle.prefixAndSuffix().getSecond(), part);
                    material.extraSaveItems.put(handle.prefixAndSuffix().getFirst() + material.name + handle.prefixAndSuffix().getSecond(), part);
                    /*
                    if(toolPart.contains(handleClass.prefixAndSuffix)){
                        if(material.toolParts.contains(handleClass.prefixAndSuffix)) {
                            String materialToolPartName = handleClass.prefixAndSuffix.getFirst() + material.name + "_" + toolName + handleClass.prefixAndSuffix.getSecond();

                            partsList.add(part);
                            material.extraSaveItems.put(materialToolPartName, part);
                        }
                    }
                }

                if (head != null && toolPart.contains(head.prefixAndSuffix)) {
                    if (dullHead != null) {
                        material.extraSaveItems.put(dullHead.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + head.prefixAndSuffix.getSecond(), RegistryHandler.ITEMS.register(dullHead.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + head.prefixAndSuffix.getSecond(), () -> dullHead
                                .setMaterial(material.name).setColor(material.color).setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol)));
                    }


                    Map<String, Integer> toolTypesList = new HashMap<>();

                    if (toolParts == null || toolParts.size() == 0) {
                        IndustrialTech.LOGGER.warn("What the fuck is this shit? You didn't set a type for your tool, you tool! ~Bear989");
                    } else {
                        toolParts.forEach((s) -> toolTypesList.put(s, material.toolLevel));
                    }

                    material.extraSaveItems.put(head.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + head.prefixAndSuffix.getSecond(), RegistryHandler.ITEMS.register(head.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + head.prefixAndSuffix.getSecond(), () -> head
                            .setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color).setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol)
                            .setItToolType(toolTypesList).setSuggestedCraftedTool("").setAttackDamage(material.attackDamage).setEfficiency(material.efficiency)));
                }

                if (binding != null && toolPart.contains(binding.prefixAndSuffix)) {
                    material.extraSaveItems.put(binding.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + binding.prefixAndSuffix.getSecond(), RegistryHandler.ITEMS.register(binding.prefixAndSuffix.getFirst() + material.name + "_" + nameIn + binding.prefixAndSuffix.getSecond(), () -> binding
                            .setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color).setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol)));
                }

                     */
                }
            }

            @Override
            public void saveResources(ITMaterial material, List<ITMaterial> stoneLayerList, List<Pair<String, String>> toolParts, List<IMaterialFlag> materialFlag) {
                LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
                ItemModelData data = new ItemModelData().setParent(new ResourceLocation("minecraft", "item/generated"));
                HeadItem toolHeadItem;

                /*
                partsList.stream().anyMatch(registryObject -> {
                    if(registryObject.get() instanceof ToolHeadItem part){
                        toolHeadItem == part;
                    }

                    if (registryObject.get() instanceof ToolHeadItem && registryObject.contains("_binding") && registryObject.contains("_handle")) {
                        data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix.getFirst() + toolName + handle.prefixAndSuffix.getSecond()));
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix.getFirst() + toolName + head.prefixAndSuffix.getSecond()));
                        data.setTexturesLocation("layer2", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + binding.prefixAndSuffix.getFirst() + toolName + binding.prefixAndSuffix.getSecond()));
                        return true;
                    } else if (registryObject.contains("_head") & registryObject.contains("_handle") & !registryObject.contains("_binding")) {
                        data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix.getFirst() + toolName + handle.prefixAndSuffix.getSecond()));
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix.getFirst() + toolName + head.prefixAndSuffix.getSecond()));
                        return true;
                    } else if (!registryObject.contains("_head") & !registryObject.contains("_handle") & !registryObject.contains("_binding")) {
                        data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName));
                        data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_overlay"));
                        return true;
                    } else {
                        return false;
                    }
                });

                AssetPackRegistry.itemModelDataHashMap.put(toolName, data);

                if (handle != null && toolParts.contains(handle.prefixAndSuffix) && material.toolParts.contains(handle.prefixAndSuffix)) {
                    String materialToolPartName = handle.prefixAndSuffix.getFirst() + material.name + "_" + toolName + handle.prefixAndSuffix.getSecond();
                    String toolPartName =  handle.prefixAndSuffix.getFirst() + toolName + handle.prefixAndSuffix.getSecond();
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

                if (head != null && toolParts.contains(head.prefixAndSuffix) && material.toolParts.contains(head.prefixAndSuffix)) {
                    if (dullHead != null && toolParts.contains(dullHead.prefixAndSuffix) && material.toolParts.contains(dullHead.prefixAndSuffix)) {
                        String materialToolPartName = dullHead.prefixAndSuffix.getFirst() + material.name + "_" + toolName + dullHead.prefixAndSuffix.getSecond();
                        String toolPartName =  dullHead.prefixAndSuffix.getFirst() + toolName + dullHead.prefixAndSuffix.getSecond();
                        //--Item--\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put(materialToolPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + toolPartName)));
                        enLang.addTranslation("item." + Ref.mod_id + "." + toolPartName, LangData.translateUpperCase(toolPartName));
                        //TagHandler.addItemToTag("dull_pickaxe_dullHead", new ResourceLocation(Ref.mod_id, "dull_" + name + "_pickaxe_dullHead"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName + "/" + material.name)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", toolPartName)).add(material.extraSaveItems.get(materialToolPartName).get()));
                        //--LootTable
                    }

                    String materialToolPartName = head.prefixAndSuffix.getFirst() + material.name + "_" + toolName + head.prefixAndSuffix.getSecond();
                    String toolPartName =  head.prefixAndSuffix.getFirst() + toolName + head.prefixAndSuffix.getSecond();
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

                if (binding != null && toolParts.contains(binding.prefixAndSuffix) && material.toolParts.contains(binding.prefixAndSuffix)) {
                    String materialToolPartName = binding.prefixAndSuffix.getFirst() + material.name + "_" + toolName + binding.prefixAndSuffix.getSecond();
                    String toolPartName =  binding.prefixAndSuffix.getFirst() + toolName + binding.prefixAndSuffix.getSecond();
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
                
                 */

                AssetPackRegistry.langDataMap.put("en_us", enLang);
            }

            @Override
            public void clientRenderLayerInit(ITMaterial material) {
            }

            @Override
            public void registerColorForBlock(ITMaterial material) {

            }

            @Override
            public void registerColorForItem(ITMaterial material) {
                registerAToolItemColor(toolItem);

                /*
                if (handle != null) {
                    String materialToolPartName = handle.prefixAndSuffix.getFirst() + material.name + "_" + toolName + handle.prefixAndSuffix.getSecond();
                    registerAItemColor(material, material.extraSaveItems.get(materialToolPartName).get(), 0);
                }

                if (head != null) {
                    if (dullHead != null){
                        String materialToolPartName = dullHead.prefixAndSuffix.getFirst() + material.name + "_" + toolName + dullHead.prefixAndSuffix.getSecond();
                        registerAItemColor(material, material.extraSaveItems.get(materialToolPartName).get(), 0);
                    }

                    String materialToolPartName = head.prefixAndSuffix.getFirst() + material.name + "_" + toolName + head.prefixAndSuffix.getSecond();

                    registerAItemColor(material, material.extraSaveItems.get(materialToolPartName).get(), 0);
                }

                if (handle != null) {
                    String materialToolPartName = handle.prefixAndSuffix.getFirst() + material.name + "_" + toolName + handle.prefixAndSuffix.getSecond();
                    registerAItemColor(material, material.extraSaveItems.get(materialToolPartName).get(), 0);
                }
                
                 */
            }
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

}
