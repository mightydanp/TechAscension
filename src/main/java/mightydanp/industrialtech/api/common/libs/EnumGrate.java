package mightydanp.industrialtech.api.common.libs;

import net.minecraft.util.IStringSerializable;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public enum EnumGrate implements IStringSerializable
{
    NO_COOKWARE("no_cookware"),
    CLAY_POT("clay_pot"),
    CLAY_PAN("clay_pan");

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

    private EnumGrate(String i_name)
    {
        this.name = i_name;
    }
}