package mightydanp.industrialtech.common.data;

/**
 * Created by MightyDanp on 7/9/2020.
 */
import mightydanp.industrialtech.common.lib.References;
import muramasa.antimatter.material.TextureSet;
import muramasa.antimatter.texture.ITextureHandler;
import muramasa.antimatter.texture.Texture;

public class Textures {

    public static TextureSet DULL = new TextureSet(References.ID, "dull");
    public static TextureSet METALLIC = new TextureSet(References.ID, "metallic");
    public static TextureSet SHINY = new TextureSet(References.ID, "shiny");
    public static TextureSet ROUGH = new TextureSet(References.ID, "rough");
    public static TextureSet MAGNETIC = new TextureSet(References.ID, "magnetic");
    public static TextureSet DIAMOND = new TextureSet(References.ID, "diamond");
    public static TextureSet RUBY = new TextureSet(References.ID, "ruby");
    public static TextureSet LAPIS = new TextureSet(References.ID, "lapis");
    public static TextureSet GEM_H = new TextureSet(References.ID, "gem_h");
    public static TextureSet GEM_V = new TextureSet(References.ID, "gem_v");
    public static TextureSet QUARTZ = new TextureSet(References.ID, "quartz");
    public static TextureSet FINE = new TextureSet(References.ID, "fine");
    public static TextureSet FLINT = new TextureSet(References.ID, "flint");
    public static TextureSet LIGNITE = new TextureSet(References.ID, "lignite");

    public static final ITextureHandler BOILER_HANDLER = (m, t) -> new Texture[] {
            new Texture(References.ID, "block/machine/base/" + t.getId()),
            new Texture(References.ID, "block/machine/base/" + t.getId()),
            new Texture(References.ID, "block/machine/base/bricked_" + t.getId()),
            new Texture(References.ID, "block/machine/base/bricked_" + t.getId()),
            new Texture(References.ID, "block/machine/base/bricked_" + t.getId()),
            new Texture(References.ID, "block/machine/base/bricked_" + t.getId()),
    };

    public static final ITextureHandler MULTI_HANDLER = (m, t) -> m.getTiers().size() > 1 ? new Texture[]{new Texture(References.ID, "block/machine/base/" + m.getId() + "_" + t.getId())} : new Texture[]{new Texture(References.ID, "block/machine/base/" + m.getId())};

    public static final Texture[] LARGE_TURBINE = new Texture[] {
            new Texture(References.ID, "block/ct/turbine/large_turbine_0"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_1"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_2"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_3"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_4"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_5"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_6"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_7"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_8")
    };

    public static final Texture[] LARGE_TURBINE_ACTIVE = new Texture[] {
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_0"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_1"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_2"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_3"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_4"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_5"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_6"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_7"),
            new Texture(References.ID, "block/ct/turbine/large_turbine_active_8")
    };

    public static final Texture[] FUSION_1_CT = new Texture[] {
            new Texture(References.ID, "block/ct/fusion/fusion_1_0"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_1"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_2"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_3"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_4"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_5"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_6"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_7"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_8"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_9"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_10"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_11"),
            new Texture(References.ID, "block/ct/fusion/fusion_1_12")
    };

    public static final Texture[] FUSION_2_CT = new Texture[] {
            new Texture(References.ID, "block/ct/fusion/fusion_2_0"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_1"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_2"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_3"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_4"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_5"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_6"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_7"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_8"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_9"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_10"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_11"),
            new Texture(References.ID, "block/ct/fusion/fusion_2_12")
    };

    public static final Texture[] FUSION_3_CT = new Texture[] {
            new Texture(References.ID, "block/ct/fusion/fusion_3_0"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_1"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_2"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_3"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_4"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_5"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_6"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_7"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_8"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_9"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_10"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_11"),
            new Texture(References.ID, "block/ct/fusion/fusion_3_12")
    };
}
