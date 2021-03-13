package mightydanp.industrialtech.api.common.datagen;

import jdk.nashorn.internal.ir.annotations.Reference;
import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.*;

/**
 * Created by MightyDanp on 3/9/2021.
 */
public class ItemModel extends ItemModelProvider {
    public ItemModel(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(MaterialHandler material : ModMaterials.materials) {
            for(EnumMaterialFlags flag : material.flags){
                if(flag == EnumMaterialFlags.GEM) {
                    coloredMaterial(material.itemGem.get(), material);
                    coloredMaterial(material.itemChippedGem.get(), material);
                    coloredMaterial(material.itemFlawedGem.get(), material);
                    coloredMaterial(material.itemFlawlessGem.get(), material);
                    coloredMaterial(material.itemLegendaryGem.get(), material);
                }
                if(flag == EnumMaterialFlags.GEM || flag == EnumMaterialFlags.ORE){
                    coloredMaterial(material.crushed_ore.get(), material);
                    coloredMaterial(material.purified_ore.get(), material);
                    coloredMaterial(material.centrifuged_ore.get(), material);
                }
            }
        }
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

    void coloredMaterial(Item item, MaterialHandler materialIn) {
        String modId = item.getRegistryName().toString().split(":")[0];
        String itemName = item.getRegistryName().toString().split(":")[1];
        String materialName = itemName.replaceAll(materialIn.materialName + "_", "");

        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation itemIconTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialName);
        final ResourceLocation itemIconOverlayTexture = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/material_icons/" + materialName + "_overlay");
        final ItemModelBuilder iconModel = factory.apply(itemIconTexture);
        iconModel.parent(parent);
        iconModel.texture("layer0", itemIconTexture);
        iconModel.texture("layer1", itemIconOverlayTexture);

        generatedModels.put(iconModel.getLocation(), iconModel);

        final ResourceLocation gemLocation = new ResourceLocation(modId + ":" + ModelProvider.ITEM_FOLDER + "/" + materialToString(item));
        final ModelFile iconParent = factory.apply(iconModel.getLocation());
        final ItemModelBuilder gem = factory.apply(gemLocation);
        gem.parent(iconParent);

        generatedModels.put(gemLocation, gem);
    }

    void singleTextureItem(Item i) {
        singleTextureItem(i, factory.apply(mcLoc("item/generated")));
    }

    void singleTextureItem(Item i, ModelFile parent) {
        final ResourceLocation location = itemLoc(i.getRegistryName());
        ItemModelBuilder builder = factory.apply(location).parent(parent);
        generatedModels.put(location, builder.texture("layer0", location));
    }

    void coloredItem(final Item i) {
        final ModelFile parent = factory.apply(mcLoc("item/generated"));
        final ResourceLocation baseLoc = itemLoc(i.getRegistryName());
        final ItemModelBuilder base = factory.apply(baseLoc);
        base.parent(parent);
        base.texture("layer0", baseLoc);
        coloredItem(i, parent, base, baseLoc, baseLoc);
    }

    void coloredItem(Item item, final ModelFile parent, final ItemModelBuilder defaultModel, final ResourceLocation baseTexture, final ResourceLocation overlayTexture) {
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

    ItemModelBuilder getKeyDisplayParent() {
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

    ResourceLocation itemLoc(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + loc.getPath());
    }

    String materialToString(Item itemIn){
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
}
