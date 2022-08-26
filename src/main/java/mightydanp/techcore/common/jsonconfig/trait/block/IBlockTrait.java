package mightydanp.techcore.common.jsonconfig.trait.block;

public interface IBlockTrait {
    String getRegistry();

    Integer getColor();
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
    default Double getMeters() {
        return 1.0D;
    }

    default Double getYards() {
        return getMeters() * 0.9144D;
    }

    default Double meterToYard(Double meters){
        return meters*1.094D;
    }

    default Double yardToMeter(Double yards){
        return yards/1.094D;
    }
}
