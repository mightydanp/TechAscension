package mightydanp.industrialtech.api.common.jsonconfig.tool.type;

import com.mojang.datafixers.util.Pair;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public interface IToolType {
    String getPrefix();
    String getSuffix();
    Pair<String, String> getFixes();
}
