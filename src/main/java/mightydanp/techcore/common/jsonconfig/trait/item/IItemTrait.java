package mightydanp.techcore.common.jsonconfig.trait.item;

import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;

public interface IItemTrait {
    String getRegistry();
    //weight
    Double getPounds();
    Double getKilograms();

    default Double poundsToKilograms(Double pounds){
        return pounds/2.205D;
    }

    default Double kilogramsToPounds(Double kilograms){
        return kilograms*2.205D;
    }

    //size
    Double getMeters();
    Double getYards();

    default Double meterToYard(Double meters){
        return meters*1.094D;
    }

    default Double yardToMeter(Double yards){
        return yards/1.094D;
    }

    //tool properties
    Integer getColor();
    Integer getDurability();
    ITextureIcon getTextureIcon();

}
