package mightydanp.industrialtech.api.common.material.flag;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public interface IMaterialFlag {
    String getPrefix();
    String getSuffix();
    Pair<String, String> getFixes();
}
