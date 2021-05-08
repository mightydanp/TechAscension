package mightydanp.industrialtech.common.libs;

import net.minecraft.util.IStringSerializable;

/**
 * Created by MightyDanp on 5/3/2021.
 */
public enum CampfireEnum implements IStringSerializable {
    ASH("ash"),
    LOG_1("log_1"),
    LOG_2("log_2"),
    LOG_3("log_3"),
    LOG_4("log_4"),
    LOG_NO_ASH("log_no_ash"),
    LOG_ASH("log_ash"),
    LOG_4_LIT_NO_ASH("log_4_lit_no_ash"),
    LOG_4_LIT_ASH("log_4_lit_ash"),
    LOG_4_LIT_ASH_STONE_1("log_4_lit_ash_stone_1"),
    LOG_4_LIT_ASH_STONE_2("log_4_lit_ash_stone_2"),
    LOG_4_LIT_ASH_STONE_TABLET("log_4_lit_ash_stone_tablet"),
    LOG_4_LIT_NO_ASH_STONE_1("log_4_lit_no_ash_stone_1"),
    LOG_4_LIT_NO_ASH_STONE_2("log_4_lit_no_ash_stone_2"),
    LOG_4_LIT_NO_ASH_STONE_TABLET("log_4_lit_no_ash_stone_tablet");

    private final String name;

    CampfireEnum(String nameIn){
        name = nameIn;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}