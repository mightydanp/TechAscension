package mightydanp.techcore.common.jsonconfig.tool.part;

/**
 * Created by MightyDanp on 11/28/2021.
 */
public interface IToolPart {
    String getPrefix();
    String getSuffix();

    static String fixesToName(IToolPart codec){
        String prefix = codec.getPrefix().replace("_", "");
        String suffix = codec.getSuffix().replace("_", "");
        String name = "";

        if(!prefix.equals("") && !suffix.equals("")){
            name = prefix + "_" + suffix;
        }

        if(prefix.equals("") && !suffix.equals("")){
            name = suffix;
        }

        if(!prefix.equals("") && suffix.equals("")){
            name = prefix;
        }

        return name;
    }
}