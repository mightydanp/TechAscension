package mightydanp.techcore.common.jsonconfig.tool.part;

/**
 * Created by MightyDanp on 11/7/2021.
 */
public enum DefaultToolPart implements IToolPart {
    TOOL_HEAD("tool_", "_head"),
    TOOL_BINDING("tool_", "_binding"),
    TOOL_WEDGE("tool_", "_wedge"),
    TOOL_WEDGE_HANDLE("tool_", "_wedge_handle");

    private final String prefix;
    private final String suffix;

    DefaultToolPart(String prefixIn, String SuffixIn) {
        prefix = prefixIn;
        suffix = SuffixIn;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
