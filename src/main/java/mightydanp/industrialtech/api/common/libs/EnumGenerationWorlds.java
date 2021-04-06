package mightydanp.industrialtech.api.common.libs;

import net.minecraft.util.IStringSerializable;

/**
 * Created by MightyDanp on 10/6/2020.
 */
public enum EnumGenerationWorlds implements IStringSerializable
{
    overworld("overworld"),
    end("end"),
    nether("nether");

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

    private EnumGenerationWorlds(String i_name)
    {
        this.name = i_name;
    }
}