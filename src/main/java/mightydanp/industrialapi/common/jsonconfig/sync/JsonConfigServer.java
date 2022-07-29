package mightydanp.industrialapi.common.jsonconfig.sync;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mightydanp.industrialapi.common.jsonconfig.sync.network.message.SyncMessage;
import net.minecraft.network.FriendlyByteBuf;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonConfigServer<T> {
    protected final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    public final Map<String, T> serverMap = new HashMap<>();

    public Map<String, T> getServerMap(){
        return serverMap;
    }

    public Map<String, T> getServerMapFromList(List<T> listIn) {
        return new LinkedHashMap<>();
    }

    public void loadFromServer(SyncMessage message) {

    }

    public Boolean isClientAndServerConfigsSynced(SyncMessage message){
        return false;
    }

    public Boolean isClientAndClientWorldConfigsSynced(Path singlePlayerConfigs){
        return false;
    }

    public void syncClientWithServer(String folderName) throws IOException {
    }

    public void syncClientWithSinglePlayerWorld(String folderName) throws IOException {

    }

    public void singleToBuffer(FriendlyByteBuf buffer, T materialFlag) {

    }

    public void multipleToBuffer(SyncMessage message, FriendlyByteBuf buffer) {

    }

    public T singleFromBuffer(FriendlyByteBuf buffer) {
       return null;
    }

    public List<T> multipleFromBuffer(FriendlyByteBuf buffer){
        return null;
    }

    public String fixesToName(String prefixIn, String suffixIn){
        String prefix = prefixIn.replace("_", "");
        String suffix = suffixIn.replace("_", "");
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
