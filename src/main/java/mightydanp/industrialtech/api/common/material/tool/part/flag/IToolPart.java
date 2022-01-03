package mightydanp.industrialtech.api.common.material.tool.part.flag;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public interface IToolPart {
    Pair<String, String> getFixes();
    String getPrefix();
    String getSuffix();
}