package mightydanp.industrialtech.api.common.libs;

import net.minecraft.util.IStringSerializable;

/**
 * Created by MightyDanp on 10/1/2020.
 */
public enum EnumStoneVariant implements IStringSerializable
{
    stone("stone");

    @Override
    public String toString()
    {
        return this.name;
    }
    @Override
    public String getString()
    {
        return this.name;
    }

    private final String name;

    private EnumStoneVariant(String i_name)
    {
        this.name = i_name;
    }
}