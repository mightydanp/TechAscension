package mightydanp.techcore.common.jsonconfig.tool.part;

/**
 * Created by MightyDanp on 11/7/2021.
 */
public enum DefaultToolPart{
    TOOL_HEAD(new ToolPartCodec("tool_", "_head")),
    TOOL_BINDING(new ToolPartCodec("tool_", "_binding")),
    TOOL_WEDGE(new ToolPartCodec("tool_", "_wedge")),
    TOOL_WEDGE_HANDLE(new ToolPartCodec("tool_", "_wedge_handle"));
    
    private final ToolPartCodec codec;

    DefaultToolPart(ToolPartCodec codec) {
        this.codec = codec;
    }
    
    public ToolPartCodec getCodec(){
        return codec;
    }
}
