package mightydanp.techcore.common.material.fluid;

import mightydanp.techcore.common.material.TCMaterials;
import mightydanp.techcore.common.material.TCMaterial;
import mightydanp.techcore.common.jsonconfig.flag.DefaultMaterialFlag;
import mightydanp.techcore.common.items.ModItemGroups;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by MightyDanp on 10/27/2021.
 */
public class TCBucket extends BucketItem {
    public TCMaterial ITMaterial;
    public int rednessStartingTemperature;
    public int meltingTemperature;
    public int fluidRGB = 0;
    public int bucketRGB = 0;
    public int temperature = 0;
    public int rednessLevel = getRednessLevel();

    public List<Object> validMaterials = new ArrayList<Object>(){{
        addAll(validMaterial(Collections.singletonList(TCMaterials.ITMaterials)));
        }};

    //for empty bucket with no material;
    public TCBucket(Properties propertiesIn, int meltingTemperatureIn, int bucketRGBIn) {
        this(propertiesIn, 100, meltingTemperatureIn, bucketRGBIn);
    }

    //for empty bucket with no material;
    public TCBucket(Properties propertiesIn, int rednessStartingTemperatureIn, int meltingTemperatureIn, int bucketRGBIn) {
        super(Fluids.EMPTY, propertiesIn.stacksTo(1).tab(ModItemGroups.fluid_tab));
        rednessStartingTemperature = rednessStartingTemperatureIn;
        meltingTemperature = meltingTemperatureIn;
        bucketRGB = bucketRGBIn;

    }

    //FluidBucket for non ITMaterial, ITFluid
    public TCBucket(Fluid fluidIn, Properties propertiesIn, Item craftingReturnIn, int meltingTemperatureIn, int fluidRGBIn, int bucketRGBIn) {
        this(fluidIn, propertiesIn, craftingReturnIn, 100, meltingTemperatureIn, fluidRGBIn, bucketRGBIn);
    }

    //FluidBucket for non ITMaterial, ITFluid
    public TCBucket(Fluid fluidIn, Properties propertiesIn, Item craftingReturnIn, int rednessStartingTemperatureIn, int meltingTemperatureIn, int fluidRGBIn, int bucketRGBIn) {
        super(fluidIn, propertiesIn.stacksTo(1).craftRemainder(craftingReturnIn).tab(ModItemGroups.fluid_tab));
        rednessStartingTemperature = rednessStartingTemperatureIn;
        meltingTemperature = meltingTemperatureIn;
        fluidRGB = fluidRGBIn;
        bucketRGB = bucketRGBIn;
    }

    //FluidBucket for ITMaterial, ITFluid
    public TCBucket(TCFluid fluidIn, Properties propertiesIn, Item craftingReturnIn, TCMaterial ITMaterialIn) {
        this(fluidIn, propertiesIn, craftingReturnIn, ITMaterialIn.meltingPoint, ITMaterialIn);
    }

    //FluidBucket for ITMaterial, ITFluid
    public TCBucket(TCFluid fluidIn, Properties propertiesIn, Item craftingReturnIn, int rednessStartingTemperatureIn, TCMaterial ITMaterialIn) {
        super(fluidIn, propertiesIn.stacksTo(1).craftRemainder(craftingReturnIn).tab(ModItemGroups.fluid_tab));
        rednessStartingTemperature = rednessStartingTemperatureIn;
        ITMaterial = ITMaterialIn;
        meltingTemperature = ITMaterialIn.meltingPoint;
    }

    public List<Object> validMaterial(List<Object> listIn){
        List<Object> validMaterials = new ArrayList<Object>(){};
        for(int i = 0; i <= listIn.size(); i++){
            if(validMaterials.get(i) instanceof TCMaterial) {
                if (ITMaterial.materialFlags.contains(DefaultMaterialFlag.FLUID))
                    if (ITMaterial.meltingPoint >= meltingTemperature) {
                        validMaterials.add(ITMaterial);
                    }
            }else{
                validMaterials.add(ITMaterial);
            }
        }

        return validMaterials;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> textComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, world, textComponents, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt == null) {
            stack.setTag(null);
        }
    }



    public int getRednessLevel(){
        if(ITMaterial != null){
            int modifiedTemperature = temperature - rednessStartingTemperature;
            int temperatureIncrement = modifiedTemperature / 8;

            for(int i = 0; i< 8 ; i++){
                if(modifiedTemperature == rednessStartingTemperature  + (temperatureIncrement * i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getTemperature(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("temperature")) {
                return nbt.getInt("temperature");
            } else {
                return temperature;
            }
        } else {
            return 0;
        }
    }

    public void setTemperature(ItemStack itemStackIn, int temperatureIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("temperature", temperatureIn);
        this.temperature = temperatureIn;
    }

    public int getRednessStartingTemperature(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("redness_starting_temperature")) {
                return nbt.getInt("redness_starting_temperature");
            } else {
                return 100;
            }
        } else {
            return 100;
        }
    }

    public void setRednessStartingTemperature(ItemStack itemStackIn, int rednessStartingTemperatureIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("redness_starting_temperature", rednessStartingTemperatureIn);
        this.rednessStartingTemperature = rednessStartingTemperatureIn;
    }

    public int getMeltingTemperature(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("melting_temperature")) {
                return nbt.getInt("melting_temperature");
            } else {
                return 999999;
            }
        } else {
            return 999999;
        }
    }

    public void setMeltingTemperature(ItemStack itemStackIn, int meltingTemperatureIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("melting_temperature", meltingTemperatureIn);
        this.meltingTemperature = meltingTemperatureIn;
    }

    public int getRgbBucket(ItemStack itemStackIn) {
        if (itemStackIn != null) {
            CompoundTag nbt = itemStackIn.getOrCreateTag();
            if (nbt.contains("rgb_bucket")) {
                return nbt.getInt("rgb_bucket");
            } else {
                return 0xFFFFFFFF;
            }
        } else {
            return 0;
        }
    }

    public void setRgbBucket(ItemStack itemStackIn, int bucketRGBIn) {
        CompoundTag nbt = itemStackIn.getOrCreateTag();
        nbt.putInt("rgb_bucket", bucketRGBIn);
        this.bucketRGB = bucketRGBIn;
    }
}
