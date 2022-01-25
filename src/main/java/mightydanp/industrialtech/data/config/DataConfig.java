package mightydanp.industrialtech.data.config;

import mightydanp.industrialtech.client.config.ClientConfig;
import mightydanp.industrialtech.common.config.CommonConfig;
import mightydanp.industrialtech.server.config.ServerConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by MightyDanp on 2/26/2021.
 */
public class DataConfig {
    private static final Pair<ServerConfig, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    public static final ServerConfig SERVER = serverSpecPair.getLeft();
    public static final ForgeConfigSpec SERVER_SPEC = serverSpecPair.getRight();

    private static final Pair<ClientConfig, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
    public static final ClientConfig CLIENT = clientSpecPair.getLeft();
    public static final ForgeConfigSpec CLIENT_SPEC  = clientSpecPair.getRight();

    private static final Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
    public static final CommonConfig COMMON = commonSpecPair.getLeft();
    public static final ForgeConfigSpec COMMON_SPEC = commonSpecPair.getRight();
}
