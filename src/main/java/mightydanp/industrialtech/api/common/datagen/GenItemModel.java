package mightydanp.industrialtech.api.common.datagen;

import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.libs.StoneLayerFlagsEnum;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import mightydanp.industrialtech.common.tools.ModTools;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.fml.RegistryObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 3/9/2021.
 */
public class GenItemModel extends ItemModelProvider {

    public GenItemModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
        registerModels();
    }

    @Override
    protected void registerModels() {
        for(MaterialHandler material : ModMaterials.materials) {
            for(EnumMaterialFlags flag : material.flags){
                if(flag == EnumMaterialFlags.ORE) {}

                if(flag == EnumMaterialFlags.GEM) {
                    coloredMaterialPart(material.gem.get(), material);
                    coloredMaterialPart(material.chippedGem.get(), material);
                    coloredMaterialPart(material.flawedGem.get(), material);
                    coloredMaterialPart(material.flawlessGem.get(), material);
                    coloredMaterialPart(material.legendaryGem.get(), material);
                }

                if(flag == EnumMaterialFlags.GEM || flag == EnumMaterialFlags.ORE){
                    coloredMaterialPart(material.crushedOre.get(), material);
                    coloredMaterialPart(material.purifiedOre.get(), material);
                    coloredMaterialPart(material.centrifugedOre.get(), material);
                    coloredMaterialPart(material.dust.get(), material);
                    coloredMaterialPart(material.smallDust.get(), material);
                    coloredMaterialPart(material.tinyDust.get(), material);
                    for(RegistryObject<Item> item: material.oreItem){
                        generateItemBlockSuffix(item.get());
                    }
                    for(RegistryObject<Item> item: material.denseOreItem){
                        generatePrefixItemBlockSuffix(item.get(), null);
                    }
                    for(RegistryObject<Item> item: material.smallOreItem){
                        generatePrefixItemBlockSuffix(item.get(), null);
                    }
                }

                if(flag == EnumMaterialFlags.TOOL_HEAD){
                    coloredToolPart(material.dullPickaxe.get(), material);
                    coloredToolPart(material.pickaxeHead.get(), material);
                    coloredToolPart(material.hammerHead.get(), material);
                    coloredToolPart(material.dullChiselHead.get(), material);
                    coloredToolPart(material.chiselHead.get(), material);
                }
                if(flag == EnumMaterialFlags.TOOL_WEDGE) {
                    coloredToolPart(material.wedge.get(), material);
                }

                if(flag == EnumMaterialFlags.TOOL_WEDGE_HANDLE){
                  coloredToolPart(material.wedgeHandle.get(), material);
                }

            }
        }

        for(StoneLayerHandler layer : ModStoneLayers.stoneLayerList ){
            for(StoneLayerFlagsEnum flag : layer.flags){
                if(flag == StoneLayerFlagsEnum.leg) {
                    generatePrefixItemBlockSuffix(layer.leg_block.get().asItem(), "stone_layer/");
                }
                if(flag == StoneLayerFlagsEnum.thinSlab) {
                    generatePrefixItemBlockSuffix(layer.thin_slab_block.get().asItem(), "stone_layer/");
                }
            }
        }

        for(ToolHandler tool : ModTools.tools){
            coloredTool(tool.toolItem.get(), ((ITToolItem)tool.toolItem.get()).partsToWork);
        }

        singleTextureItem(ModItems.plant_fiber.get());
        singleTextureItem(ModItems.twine.get());
    }

/*
    void coloredKey(ModelFile parent) {
        final ResourceLocation baseLoc = itemLoc(Item.KEY.getRegistryName());
        final ItemModelBuilder base = factory.apply(baseLoc);
        base.parent(parent);
        base.texture("layer0", new ResourceLocation()appendPath(baseLoc, "_body"));
        base.texture("layer1", appendPath(baseLoc, "_head"));
        coloredItem(baseLoc, parent, base, appendPath(baseLoc, "_body"), appendPath(baseLoc, "_overlay"));
    }
 */
    public void generatePrefixItemBlock(Item itemIn) {
        String modId = itemIn.getRegistryName().toString().split(":")[0];
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        String prefix = itemName.split("_")[0]+ "_";
        final ItemModelBuilder denseOreIconModel = factory.apply((new ResourceLocation( modId, "block/" + prefix.replace("_", "") + "/"  + materialToMaterialPrefixStoneVariant(itemIn))));
        final ResourceLocation denseOreLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);

        final ItemModelBuilder denseOre = factory.apply(denseOreLocation);
        denseOre.parent(denseOreIconModel);

        generatedModels.put(denseOreLocation, denseOre);
    }

    public void generatePrefixItemBlockSuffix(Item itemIn, String name) {
        String modId = itemIn.getRegistryName().toString().split(":")[0];
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        String prefix = itemName.split("_")[0]+ "_";
        String suffix = "_" + itemName.split("_")[itemName.split("_").length- 1];
        String materialName = prefix.replace("_", "") + "_" + suffix.replace("_", "");
        final ItemModelBuilder denseOreIconModel = factory.apply((new ResourceLocation( modId, "block/" + (name == null ? "" : name) + materialName + "/" + materialToMaterialPrefixStoneVariantSuffix(itemIn))));
        final ResourceLocation denseOreLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);

        final ItemModelBuilder denseOre = factory.apply(denseOreLocation);
        denseOre.parent(denseOreIconModel);

        generatedModels.put(denseOreLocation, denseOre);
    }

    public void generateItemBlockSuffix(Item itemIn) {
        String modId = itemIn.getRegistryName().toString().split(":")[0];
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        String suffix = "_" + itemName.split("_")[itemName.split("_").length- 1];
        final ItemModelBuilder denseOreIconModel = factory.apply((new ResourceLocation( modId, "block/" + suffix.replace("_", "") + "/" + materialToMaterialStoneVariantSuffix(itemIn))));
        final ResourceLocation denseOreLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);

        final ItemModelBuilder denseOre = factory.apply(denseOreLocation);
        denseOre.parent(denseOreIconModel);

        generatedModels.put(denseOreLocation, denseOre);
    }

    public void coloredMaterial(Item item, MaterialHandler materialIn) {
        String modId = item.getRegistryName().toString().split(":")[0];
        String itemName = item.getRegistryName().toString().split(":")[1];
        String materialName = itemName.replaceAll(materialIn.materialName + "_", "");
        String prefix = itemName.split("_")[0]+ "_";
        String suffix = "_" + itemName.split("_")[itemName.split("_").length - 1];

        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation itemIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialName);
        final ResourceLocation itemIconOverlayTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialName + "_overlay");
        final ItemModelBuilder iconModel = factory.apply(itemIconTexture);
        iconModel.parent(parent);
        iconModel.texture("layer0", itemIconTexture);
        iconModel.texture("layer1", itemIconOverlayTexture);

        generatedModels.put(iconModel.getLocation(), iconModel);

        final ResourceLocation gemLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);
        final ModelFile iconParent = factory.apply(iconModel.getLocation());
        final ItemModelBuilder gem = factory.apply(gemLocation);
        gem.parent(iconParent);

        generatedModels.put(gemLocation, gem);
    }

    public void coloredMaterialPart(Item item, MaterialHandler materialIn) {
        String modId = item.getRegistryName().toString().split(":")[0];
        String itemName = item.getRegistryName().toString().split(":")[1];
        String materialName = itemName.replace(materialIn.materialName, "");
        String prefix = materialName.split("_")[0];
        String suffix = itemName.split("_")[itemName.split("_").length - 1];

        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation itemIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialIn.textureFlag.getName() + "/" + baseString(item, materialIn.materialName));
        final ResourceLocation itemIconOverlayTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialIn.textureFlag.getName() + "/" + baseString(item, materialIn.materialName) + "_overlay");
        final ItemModelBuilder iconModel = factory.apply(itemIconTexture);
        iconModel.parent(parent);
        iconModel.texture("layer0", itemIconTexture);
        iconModel.texture("layer1", itemIconOverlayTexture);

        generatedModels.put(iconModel.getLocation(), iconModel);

        final ResourceLocation gemLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);
        final ModelFile iconParent = factory.apply(iconModel.getLocation());
        final ItemModelBuilder gem = factory.apply(gemLocation);
        gem.parent(iconParent);

        generatedModels.put(gemLocation, gem);
    }

    public void coloredTool(Item item, int partsToWorkIn) {
        String modId = item.getRegistryName().toString().split(":")[0];
        String itemName = item.getRegistryName().toString().split(":")[1];

        String prefix = itemName.split("_")[0];
        String suffix = itemName.split("_")[itemName.split("_").length - 1];


        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation itemLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/tool_icons/" + itemName);
        final ResourceLocation itemHeadIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + "none/" + itemName + "_head");
        final ResourceLocation itemBindingIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + "none/" + itemName + "_binding");
        final ResourceLocation itemHandleIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + "none/" + itemName + "_handle");
        final ItemModelBuilder iconModel = factory.apply(itemLocation);

        iconModel.parent(parent);
        if(partsToWorkIn == 1){
            iconModel.texture("layer0", itemHandleIconTexture);
        }

        if(partsToWorkIn == 2){
            iconModel.texture("layer0", itemHandleIconTexture);
            iconModel.texture("layer1", itemHeadIconTexture);
        }

        if(partsToWorkIn == 3){
            iconModel.texture("layer0", itemHandleIconTexture);
            iconModel.texture("layer1", itemBindingIconTexture);
            iconModel.texture("layer2", itemHeadIconTexture);
        }

        //iconModel.texture("layer0", itemHandleIconTexture);
        //iconModel.texture("layer1", itemBindingIconTexture);
        //iconModel.texture("layer2", itemHeadIconTexture);

        generatedModels.put(iconModel.getLocation(), iconModel);

        final ResourceLocation toolLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);
        final ModelFile iconParent = factory.apply(iconModel.getLocation());
        final ItemModelBuilder itemBuilder = factory.apply(toolLocation);
        itemBuilder.parent(iconParent);

        generatedModels.put(toolLocation, itemBuilder);
    }

    public void coloredToolPart(Item item, MaterialHandler materialIn) {
        String modId = item.getRegistryName().toString().split(":")[0];
        String itemName = item.getRegistryName().toString().split(":")[1];
        String textureFlag = materialIn.textureFlag.getName();

        String materialName = itemName.replace(materialIn.materialName, "");
        String prefix = materialName.split("_")[0];
        String suffix = itemName.split("_")[itemName.split("_").length - 1];


        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation itemLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/material_icons/" + textureFlag + "/" + baseString(item, materialIn.materialName));
        final ResourceLocation itemHeadIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/"+ textureFlag + "/" + baseString(item, materialIn.materialName));
        final ItemModelBuilder iconModel = factory.apply(itemLocation);
        iconModel.parent(parent);
        iconModel.texture("layer0", itemHeadIconTexture);

        generatedModels.put(iconModel.getLocation(), iconModel);

        final ResourceLocation toolLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + itemName);
        final ModelFile iconParent = factory.apply(iconModel.getLocation());
        final ItemModelBuilder itemBuilder = factory.apply(toolLocation);
        itemBuilder.parent(iconParent);

        generatedModels.put(toolLocation, itemBuilder);
    }

    public void singleTextureItem(Item i) {
        singleTextureItem(i, factory.apply(mcLoc("item/generated")));
    }

    public void singleTextureItem(Item i, ModelFile parent) {
        final ResourceLocation location = itemLoc(i.getRegistryName());
        ItemModelBuilder builder = factory.apply(location).parent(parent);
        generatedModels.put(location, builder.texture("layer0", location));
    }

    public void coloredItem(final Item i) {
        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation baseLoc = itemLoc(i.getRegistryName());
        final ItemModelBuilder base = factory.apply(baseLoc);
        base.parent(parent);
        base.texture("layer0", baseLoc);
        coloredItem(i, parent, base, baseLoc, baseLoc);
    }

    public void coloredItem(Item item, final ModelFile parent, final ItemModelBuilder defaultModel, final ResourceLocation baseTexture, final ResourceLocation overlayTexture) {
        final ResourceLocation color1 = new ResourceLocation(item.toString() + "_color_1");
        final ResourceLocation color2 = new ResourceLocation(item.toString() + "_color_2");
        final ResourceLocation color3 = new ResourceLocation(item.toString() + "_color_3");

        final ItemModelBuilder overlay1 = factory.apply(color1);
        final ItemModelBuilder overlay2 = factory.apply(color2);
        final ItemModelBuilder overlay3 = factory.apply(color3);

        final ItemModelBuilder[] overlays = { overlay1, overlay2, overlay3 };
        for (ItemModelBuilder builder : overlays) {
            builder.parent(parent);
            builder.texture("layer0", baseTexture);
            builder.texture("layer1", overlayTexture);
            builder.texture("layer2", color1);
        }
        overlay2.texture("layer3", color2);
        overlay3.texture("layer3", color2);
        overlay3.texture("layer4", color3);

        defaultModel.override().predicate(new ResourceLocation("colors"), 0).model(defaultModel).end();
        defaultModel.override().predicate(new ResourceLocation("colors"), 1).model(overlay1).end();
        defaultModel.override().predicate(new ResourceLocation("colors"), 2).model(overlay2).end();
        defaultModel.override().predicate(new ResourceLocation("colors"), 3).model(overlay3).end();

        generatedModels.put(color1, defaultModel);
        for (ItemModelBuilder builder : overlays) { generatedModels.put(builder.getLocation(), builder); }
    }

    public ItemModelBuilder getKeyDisplayParent() {
        final ItemModelBuilder keyParent = withExistingParent("key_display", mcLoc("item/generated"));
        // @formatter:off
        keyParent.transforms()
                .transform(ModelBuilder.Perspective.GROUND)
                .rotation(0, 0, 0)
                .translation(0, 2, 0)
                .scale(0.5F, 0.5F, 0.5F)
                .end()
                .transform(ModelBuilder.Perspective.HEAD)
                .rotation(0, 180, 0)
                .translation(0, 13, 7)
                .scale(1, 1, 1)
                .end()
                .transform(ModelBuilder.Perspective.THIRDPERSON_RIGHT)
                .rotation(180, 0, 0)
                .translation(0, 3, 1)
                .scale(0.55F, 0.55F, 0.55F)
                .end()
                .transform(ModelBuilder.Perspective.FIRSTPERSON_RIGHT)
                .rotation(180, -90, 25)
                .translation(1.13F, 5F, 1.13F)
                .scale(0.68F, 0.68F, 0.68F)
                .end()
                .end();
        // @formatter:on
        return keyParent;
    }

    public ResourceLocation itemLoc(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + loc.getPath());
    }

    public String materialToString(Item itemIn){
        StringBuilder translatedName = new StringBuilder();
        String name = itemIn.getRegistryName().toString();
        if (name.contains("_")) {
            int i = 0;
            for (String word : name.split("_")) {
                if( i == 0 ){
                    String str1 = word.split(":")[1];
                    translatedName.append(str1);
                    i++;
                }else{
                    translatedName.append("_").append(word);
                }
            }
        }else{
            translatedName.append(name.split(":")[1]);
        }

        return translatedName.toString();
    }

    public String materialToMaterialPrefixStoneVariant(Item itemIn){
        StringBuilder translatedName = new StringBuilder();
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        String prefix = itemName.split("_")[0]+ "_";
        String stoneVariant = itemName.replace(prefix, "").split("_")[0];

        translatedName.append(prefix).append(stoneVariant);

        return translatedName.toString();
    }

    public String materialToMaterialPrefixStoneVariantSuffix(Item itemIn){
        StringBuilder translatedName = new StringBuilder();
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        String prefix = itemName.split("_")[0]+ "_";
        String suffix = "_" + itemName.split("_")[itemName.split("_").length- 1];
        String stoneVariant = itemName.replace(prefix, "").replace(suffix, "").split("_")[0];

        translatedName.append(prefix).append(stoneVariant).append(suffix);

        return translatedName.toString();
    }

    public String materialToMaterialStoneVariantSuffix(Item itemIn){
        StringBuilder translatedName = new StringBuilder();
        String itemName = itemIn.getRegistryName().toString().split(":")[1];
        //String prefix = itemName.split("_")[0]+ "_";
        String suffix = "_" + itemName.split("_")[itemName.split("_").length- 1];
        String stoneVariant = itemName.replace(suffix, "").split("_")[0];

        translatedName.append(stoneVariant).append(suffix);

        return translatedName.toString();
    }

    public String baseString(Item itemIn, String takeOf){
        String modId = itemIn.getRegistryName().toString().split(":")[0];
        String itemName = itemIn.getRegistryName().toString().split(":")[1];

        String materialName = itemName.replace(takeOf, "");
        String prefix = materialName.split("_")[0];
        String suffix = itemName.split("_")[itemName.split("_").length - 1];

        StringBuilder translatedName = new StringBuilder();

        List<String> StringList = Arrays.asList(takeOf.split("_"));

        if(takeOf.equals("flint")){
            itemIn.asItem();
        }

        int i=0;
        for (String string : itemName.split("_")) {
            if (!StringList.contains(string)) {
                if (i == 0) {
                    i++;
                    translatedName.append(string);
                } else {
                    if(!StringList.contains(string)) {
                        translatedName.append("_");
                        translatedName.append(string);
                    }
                }
            }
        }

        return translatedName.toString();
    }
}
