package mightydanp.industrialtech.common.config;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Created by MightyDanp on 2/26/2021.
 */
public class CommonConfig {
    public static ForgeConfigSpec.IntValue cattailRarityPerChunk;

    public CommonConfig(ForgeConfigSpec.Builder builder){
        builder.push("RarityPerChunk");

        cattailRarityPerChunk = builder
                .comment("\r\n This effects how often wells will attempt to spawn per chunk."
                        + "\r\n The chance of a well generating at a chunk is 1/rarityPerChunk."
                        + "\r\n 1 for wells spawning in every chunk and 10000 for no cattails.")
                .translation(Ref.mod_id + ".config.cattail_rarity_per_chunk")
                .defineInRange("cattail rarity per chunk", 250, 1, 10000);
    }
}
