package mightydanp.techcore.common.libs;

import net.minecraft.util.StringRepresentable;

/**
 * Created by MightyDanp on 3/25/2021.
 */
public enum EnumVeinRarityFlags implements StringRepresentable {
    common("common"),
    uncommon("uncommon"),
    rare("rare");

    @Override
    public String toString()
    {
        return this.name;
    }
    @Override
    public String getSerializedName()
    {
        return this.name;
    }

    private final String name;

    private EnumVeinRarityFlags(String nameIn)
    {
        this.name = nameIn;
    }
}