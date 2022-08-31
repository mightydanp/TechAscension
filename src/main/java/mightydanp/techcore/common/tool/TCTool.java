package mightydanp.techcore.common.tool;

import com.mojang.datafixers.util.Pair;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.flag.DefaultMaterialFlag;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.IMaterial;
import mightydanp.techcore.common.material.TCMaterial;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.ItemModelData;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techcore.common.tool.part.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Created by MightyDanp on 3/8/2021.
 */

public class TCTool {
    public String toolName;
    public int hitDamage;
    public List<String> toolParts = new ArrayList<>();

    public Set<String> effectiveBlocks;

    public Map<String, Integer> assembleItems;
    public List<String> disassembleItems;
    public List<RegistryObject<Item>> materialParts = new ArrayList<>();
    public PartHolders.handlePartHolder handle;
    public PartHolders.dullHeadPartHolder dullHead;

    public PartHolders.headPartHolder head;
    public PartHolders.bindingPartHolder binding;

    public RegistryObject<Item> toolItem;

    public TCTool(String nameIn, int hitDamageIn, Set<String> effectiveBlocksIn, Map<String, Integer> assembleItemsIn, List<String> disassembleItemsIn, PartHolders.handlePartHolder handleIn, PartHolders.dullHeadPartHolder dullHeadIn, PartHolders.headPartHolder headIn, PartHolders.bindingPartHolder bindingIn, RegistryObject<Item> toolItemIn) {
        toolName = nameIn;
        hitDamage = hitDamageIn;

        effectiveBlocks = effectiveBlocksIn;

        assembleItems = assembleItemsIn;
        disassembleItems = disassembleItemsIn;

        if(handleIn != null){
            handle = handleIn;
            toolParts.add(fixesToName(handle.prefixAndSuffix()));
        }

        if(dullHeadIn != null){
            dullHead = dullHeadIn;
            //toolParts.add(fixesToName(dullHead.prefixAndSuffix()));
        }

        if(headIn != null){
            head = headIn;
            toolParts.add(fixesToName(head.prefixAndSuffix()));
        }

        if(bindingIn != null){
            binding = bindingIn;
            toolParts.add(fixesToName(binding.prefixAndSuffix()));
        }

        TCMaterial.extraSave.put(nameIn, new IMaterial() {
            @Override
            public void save(TCMaterial material) {
                if (material.materialFlags.contains(DefaultMaterialFlag.TOOL)) {
                    if (handle != null && handle.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(handle.prefixAndSuffix()) || !material.toolPartBlackList.contains(handle.prefixAndSuffix())) {
                            String partName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }

                            if (!material.extraSaveItems.containsKey(partName)) {
                                RegistryObject<Item> part = RegistryHandler.ITEMS.register(partName, () -> {
                                    try {
                                        return handle.part().newInstance().setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color)
                                                .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol);
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        return null;
                                    }
                                });

                                material.extraSaveItems.put(partName, part);
                                materialParts.add(part);
                            }
                        }
                    }

                    if (head != null && head.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(head.prefixAndSuffix()) || !material.toolPartBlackList.contains(head.prefixAndSuffix())) {
                            if (dullHead != null && dullHead.generatePart()) {
                                String partName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();

                                if(partName.contains("__")){
                                    partName = partName.replace("__", "_");
                                }

                                if(partName.charAt(0) == '_'){
                                    partName = partName.substring(1);
                                }

                                if (!material.extraSaveItems.containsKey(partName)) {
                                    RegistryObject<Item> part = RegistryHandler.ITEMS.register(partName, () -> {
                                        try {
                                            return dullHead.part().newInstance().setMaterial(material.name).setColor(material.color)
                                                    .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol);
                                        } catch (InstantiationException | IllegalAccessException e) {
                                            return null;
                                        }
                                    });

                                    material.extraSaveItems.put(partName, part);
                                    materialParts.add(part);
                                }
                            }

                            String partName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }

                            if (!material.extraSaveItems.containsKey(partName)) {
                                Map<String, Integer> toolTypesList = new HashMap<>();

                                if (toolParts == null || toolParts.size() == 0) {
                                    TechAscension.LOGGER.warn("What the fuck is this shit? You didn't set a type for your tool, you tool! ~Bear989");
                                } else {
                                    toolTypesList.put(toolName, material.toolLevel);
                                }

                                RegistryObject<Item> part = RegistryHandler.ITEMS.register(partName, () -> {
                                    try {
                                        return head.part().newInstance().setTools(toolTypesList).setSuggestedCraftedTool(toolName).setAttackDamage(material.attackDamage).setEfficiency(material.efficiency).setMaterial(material.name).setColor(material.color)
                                                .setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color).setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol);
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        return null;
                                    }
                                });

                                material.extraSaveItems.put(partName, part);
                                materialParts.add(part);
                            }
                        }
                    }

                    if (binding != null && binding.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(binding.prefixAndSuffix()) || !material.toolPartBlackList.contains(binding.prefixAndSuffix())) {
                            String partName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }

                            if (!material.extraSaveItems.containsKey(partName)) {

                                RegistryObject<Item> part = RegistryHandler.ITEMS.register(partName, () -> {
                                    try {
                                        return binding.part().newInstance().setDurability(material.durability).setWeight(material.weight).setMaterial(material.name).setColor(material.color)
                                                .setTextureFlag(material.textureIcon).setBoilingPoint(material.boilingPoint).setMeltingPoint(material.meltingPoint).setElement(material.symbol);
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        return null;
                                    }
                                });

                                material.extraSaveItems.put(partName, part);
                                materialParts.add(part);
                            }
                        }
                    }
                }
            }
            @Override
            public void clientRenderLayerInit(TCMaterial material) {
            }

            @Override
            public void registerColorForBlock(TCMaterial material) {

            }

            @Override
            public void registerColorForItem(TCMaterial material) {
                if (material.materialFlags.contains(DefaultMaterialFlag.TOOL)) {
                    registerAToolItemColor(toolItemIn);

                    if (handle != null && handle.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(handle.prefixAndSuffix()) || !material.toolPartBlackList.contains(handle.prefixAndSuffix())) {
                            String partName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }

                            if (material.extraSaveItems.containsKey(partName)) {
                                ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, partName);

                                if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                                    Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                                    registerAItemColor(material, item, 0);
                                }
                            }
                        }
                    }

                    if (head != null && head.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(head.prefixAndSuffix()) || !material.toolPartBlackList.contains(head.prefixAndSuffix())) {
                            if (dullHead != null && dullHead.generatePart()) {
                                String partName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();

                                if(partName.contains("__")){
                                    partName = partName.replace("__", "_");
                                }

                                if(partName.charAt(0) == '_'){
                                    partName = partName.substring(1);
                                }

                                if (material.extraSaveItems.containsKey(partName)) {
                                    ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, partName);

                                    if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                                        Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                                        registerAItemColor(material, item, 0);
                                    }
                                }
                            }
                        }

                        String partName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();

                        if(partName.contains("__")){
                            partName = partName.replace("__", "_");
                        }

                        if(partName.charAt(0) == '_'){
                            partName = partName.substring(1);
                        }


                        if (material.extraSaveItems.containsKey(partName)) {
                            ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, partName);

                            if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                                Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                                registerAItemColor(material, item, 0);
                            }
                        }
                    }

                    if (binding != null && binding.generatePart()) {
                        if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(binding.prefixAndSuffix()) || !material.toolPartBlackList.contains(binding.prefixAndSuffix())) {
                            String partName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }


                            if (material.extraSaveItems.containsKey(partName)) {
                                ResourceLocation itemResource = new ResourceLocation(Ref.mod_id, partName);

                                if (ForgeRegistries.ITEMS.getValue(itemResource) != null) {
                                    Item item = ForgeRegistries.ITEMS.getValue(itemResource);
                                    registerAItemColor(material, item, 0);
                                }
                            }
                        }
                    }
                }
            }
        });

        TCMaterial.extraSaveResources.put(nameIn, (material) -> {
            LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
            ItemModelData data = new ItemModelData().setParent(new ResourceLocation("minecraft", "item/generated"));

            if (material.materialFlags.contains(DefaultMaterialFlag.TOOL)) {
                ((TCToolItem)toolItemIn.get()).parts = this.toolParts.size();

                int i = toolParts.size();

                enLang.addTranslation("item." + Ref.mod_id + "." + toolName, LangData.translateUpperCase(toolName));

                if (i == 3) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix().getFirst() + toolName + handle.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix().getFirst() + toolName + head.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer2", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + binding.prefixAndSuffix().getFirst() + toolName + binding.prefixAndSuffix().getSecond()));
                } else if (i == 2) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + handle.prefixAndSuffix().getFirst() + toolName + handle.prefixAndSuffix().getSecond()));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + head.prefixAndSuffix().getFirst() + toolName + head.prefixAndSuffix().getSecond()));
                } else if (i == 1) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_overlay"));
                }

                AssetPackRegistry.itemModelDataHashMap.put(toolName, data);

                if (handle != null && handle.generatePart()) {
                    if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(handle.prefixAndSuffix()) || !material.toolPartBlackList.contains(handle.prefixAndSuffix())) {
                        String materialPartName = handle.prefixAndSuffix().getFirst() + material.name + (handle.special() ? "_" + toolName : "") + handle.prefixAndSuffix().getSecond();
                        String partName = handle.prefixAndSuffix().getFirst() + (handle.special() ? toolName : "") + handle.prefixAndSuffix().getSecond();

                        if(partName.contains("__")){
                            partName = partName.replace("__", "_");
                        }

                        if(partName.charAt(0) == '_'){
                            partName = partName.substring(1);
                        }

                        if (material.extraSaveItems.containsKey(materialPartName)) {
                            RegistryObject<Item> registryItem = material.extraSaveItems.get(materialPartName);

                            if (registryItem.isPresent()) {
                                HandleItem handleItem = (HandleItem) registryItem.get();
                                //--Item--\\
                                //--Resources
                                AssetPackRegistry.itemModelDataHashMap.put(materialPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + partName)));
                                enLang.addTranslation("item." + Ref.mod_id + "." + materialPartName, LangData.translateUpperCase(materialPartName));
                                //--Tags
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName + "/" + material.name)).add(handleItem));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName)).add(handleItem));
                                //--LootTable
                                ((TCToolItem) toolItemIn.get()).handles.put(material.name, handleItem);
                            }
                        }

                        if(handle.partItems().size() > 0){
                            for(String registry : handle.partItems()) {
                                if(!((TCToolItem) toolItemIn.get()).handles.containsKey(registry.split(":")[1])){
                                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(registry.split(":")[0], (registry.split(":")[1])));

                                    if (item != null) {
                                        if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(item.getRegistryName()).getPath()))
                                            ((TCToolItem) toolItemIn.get()).handles.put(item.getRegistryName().getPath(), item);
                                    }
                                }
                            }
                        }
                    }

                }

                if (head != null && head.generatePart()) {
                    if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(head.prefixAndSuffix()) || !material.toolPartBlackList.contains(head.prefixAndSuffix())) {
                        if (dullHead != null && dullHead.generatePart()) {
                            String materialPartName = dullHead.prefixAndSuffix().getFirst() + material.name + (dullHead.special() ? "_" + toolName : "") + dullHead.prefixAndSuffix().getSecond();
                            String partName = dullHead.prefixAndSuffix().getFirst() + (dullHead.special() ? toolName : "") + dullHead.prefixAndSuffix().getSecond();

                            if(partName.contains("__")){
                                partName = partName.replace("__", "_");
                            }

                            if(partName.charAt(0) == '_'){
                                partName = partName.substring(1);
                            }


                            if (material.extraSaveItems.containsKey(materialPartName)) {
                                RegistryObject<Item> registryItem = material.extraSaveItems.get(materialPartName);

                                if (registryItem.isPresent()) {
                                    DullHeadItem dullHeadItem = (DullHeadItem) registryItem.get();

                                    //--Item--\\
                                    //--Resources
                                    AssetPackRegistry.itemModelDataHashMap.put(materialPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + partName)));
                                    enLang.addTranslation("item." + Ref.mod_id + "." + materialPartName, LangData.translateUpperCase(materialPartName));
                                    //--Tags
                                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName + "/" + material.name)).add(material.extraSaveItems.get(materialPartName).get()));
                                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName)).add(material.extraSaveItems.get(materialPartName).get()));
                                    //--LootTable

                                    ((TCToolItem) toolItemIn.get()).dullHeads.put(material.name, dullHeadItem);
                                }
                            }
                        }

                        String materialPartName = head.prefixAndSuffix().getFirst() + material.name + (head.special() ? "_" + toolName : "") + head.prefixAndSuffix().getSecond();
                        String partName = head.prefixAndSuffix().getFirst() + (head.special() ? toolName : "") + head.prefixAndSuffix().getSecond();

                        if(partName.contains("__")){
                            partName = partName.replace("__", "_");
                        }

                        if(partName.charAt(0) == '_'){
                            partName = partName.substring(1);
                        }


                        if (material.extraSaveItems.containsKey(materialPartName)) {
                            RegistryObject<Item> registryItem = material.extraSaveItems.get(materialPartName);

                            if (registryItem.isPresent()) {
                                HeadItem headItem = (HeadItem) registryItem.get();

                                //--Item--\\
                                //--Resources
                                AssetPackRegistry.itemModelDataHashMap.put(materialPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + partName)));
                                enLang.addTranslation("item." + Ref.mod_id + "." + materialPartName, LangData.translateUpperCase(materialPartName));
                                //--Tags
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName + "/" + material.name)).add(material.extraSaveItems.get(materialPartName).get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName)).add(material.extraSaveItems.get(materialPartName).get()));
                                //--LootTable
                                ((TCToolItem) toolItemIn.get()).heads.put(material.name, headItem);
                            }
                        }

                        if(head.partItems().size() > 0){
                            for(String registry : head.partItems()) {
                                if(!((TCToolItem) toolItemIn.get()).heads.containsKey(registry.split(":")[1])) {
                                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(registry.split(":")[0], (registry.split(":")[1])));

                                    if (item != null) {
                                        if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(item.getRegistryName()).getPath()))
                                            ((TCToolItem) toolItemIn.get()).heads.put(item.getRegistryName().getPath(), item);
                                    }
                                }
                            }
                        }
                    }
                }

                if (binding != null && binding.generatePart()) {
                    if((material.toolPartWhiteList.isEmpty() && material.toolPartBlackList.isEmpty()) || material.toolPartWhiteList.contains(binding.prefixAndSuffix()) || !material.toolPartBlackList.contains(binding.prefixAndSuffix())) {
                        String materialPartName = binding.prefixAndSuffix().getFirst() + material.name + (binding.special() ? "_" + toolName : "") + binding.prefixAndSuffix().getSecond();
                        String partName = binding.prefixAndSuffix().getFirst() + (binding.special() ? toolName : "") + binding.prefixAndSuffix().getSecond();

                        if(partName.contains("__")){
                            partName = partName.replace("__", "_");
                        }

                        if(partName.charAt(0) == '_'){
                            partName = partName.substring(1);
                        }


                        if (material.extraSaveItems.containsKey(materialPartName)) {
                            RegistryObject<Item> registryItem = material.extraSaveItems.get(materialPartName);

                            if (registryItem.isPresent()) {
                                BindingItem bindingItem = (BindingItem) registryItem.get();

                                //--Item--\\
                                //--Resources
                                AssetPackRegistry.itemModelDataHashMap.put(materialPartName, new ItemModelData().setParent(new ResourceLocation("item/generated"))
                                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + material.textureIcon.getSecond().getName() + "/" + partName)));
                                enLang.addTranslation("item." + Ref.mod_id + "." + materialPartName, LangData.translateUpperCase(materialPartName));
                                //--Tags
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName + "/" + material.name)).add(material.extraSaveItems.get(materialPartName).get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", partName)).add(material.extraSaveItems.get(materialPartName).get()));
                                //--LootTable
                                ((TCToolItem) toolItemIn.get()).bindings.put(material.name, bindingItem);
                            }
                        }

                        if(binding.partItems().size() > 0){
                            for(String registry : binding.partItems()) {
                                if(!((TCToolItem) toolItemIn.get()).bindings.containsKey(registry.split(":")[1])) {
                                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(registry.split(":")[0], (registry.split(":")[1])));

                                    if (item != null) {
                                        if (TCJsonConfigs.itemTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(item.getRegistryName()).getPath()))
                                            ((TCToolItem) toolItemIn.get()).bindings.put(item.getRegistryName().getPath(), item);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            AssetPackRegistry.langDataMap.put("en_us", enLang);
        });

        toolItem = toolItemIn;
    }




    public void registerAItemColor(TCMaterial material, Item item, int layerNumberIn) {
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
                    return ((TCToolItem) stack.getItem()).getHandleColor(stack);
                } else if (tintIndex == 1) {
                    return (((TCToolItem) stack.getItem()).getHeadColor(stack));
                } else if (tintIndex == 2) {
                    return ((TCToolItem) stack.getItem()).getBindingColor(stack);
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
