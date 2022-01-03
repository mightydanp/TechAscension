package mightydanp.industrialtech.api.common.datagen.material;

import mightydanp.industrialtech.api.common.material.data.MaterialJsonProvider;
import net.minecraft.data.DataGenerator;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public class GenMaterial extends MaterialJsonProvider {
    public String modID;
    public String materialName = "Materials";

    public GenMaterial(DataGenerator generator, String modID){
        this.modID = modID;
        addMaterial();
    }

    public void addMaterial(){
        //this.materialList.add()
    }
}
