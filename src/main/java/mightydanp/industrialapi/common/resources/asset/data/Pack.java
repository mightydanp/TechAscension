package mightydanp.industrialapi.common.resources.asset.data;

import com.google.gson.JsonObject;

import java.io.File;

public class Pack {
    public static File name = new File("pack.mcmeta");

    private static final JsonObject pack = new JsonObject();
    public static JsonObject data = new JsonObject();

    public static void init(){
        pack.addProperty("pack_format", 6);
        pack.addProperty("description", "if you have this off your resource packs then your textures wont work!");

        data.add("pack", pack);
    }



}
