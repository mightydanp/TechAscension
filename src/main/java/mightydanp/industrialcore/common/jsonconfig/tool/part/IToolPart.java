package mightydanp.industrialcore.common.jsonconfig.tool.part;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public interface IToolPart {
    Pair<String, String> getFixes();
    String getPrefix();
    String getSuffix();
}