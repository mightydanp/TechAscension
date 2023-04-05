package mightydanp.techapi.common.resources.asset.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockModel.GuiLight;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockElementRotation;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.client.model.generators.*;
public class TAModelBuilder extends ModelFile {

    @Nullable
    protected ModelFile parent;
    protected final Map<String, String> textures = new LinkedHashMap<>();
    protected final TAModelBuilder.TransformsBuilder transforms = new TransformsBuilder();
    protected boolean ambientOcclusion = true;
    protected GuiLight guiLight = null;

    protected final List<TAModelBuilder.ElementBuilder> elements = new ArrayList<>();

    public TAModelBuilder(ResourceLocation outputLocation) {
        super(outputLocation);
    }

    @Override
    protected boolean exists() {
        return true;
    }
    public TAModelBuilder parent(ModelFile parent) {
        this.parent = parent;
        return this;
    }

    public TAModelBuilder setParent(ResourceLocation resourceLocation) {
        this.parent(new ModelFile.UncheckedModelFile(resourceLocation));
        return this;
    }

    public TAModelBuilder setParent(ModelFile model) {
        this.parent(model);
        return this;
    }

    public TAModelBuilder setParent(ModelData modelData) {
        this.parent(modelData.getModel());
        return this;
    }

    public TAModelBuilder texture(String key, String texture) {
        Preconditions.checkNotNull(key, "Key must not be null");
        Preconditions.checkNotNull(texture, "Texture must not be null");
        if (texture.charAt(0) == '#') {
            this.textures.put(key, texture);
            return this;
        } else {
            ResourceLocation asLoc;
            if (texture.contains(":")) {
                asLoc = new ResourceLocation(texture);
            } else {
                asLoc = new ResourceLocation(getLocation().getNamespace(), texture);
            }
            return texture(key, asLoc);
        }
    }
    public TAModelBuilder texture(String key, ResourceLocation texture) {
        this.textures.put(key, texture.toString());
        return this;
    }

    public TAModelBuilder.TransformsBuilder transforms() {
        return transforms;
    }

    public TAModelBuilder ambientOcclusion(boolean ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
        return this;
    }

    public TAModelBuilder guiLight(GuiLight light) {
        this.guiLight = light;
        return this;
    }

    public TAModelBuilder.ElementBuilder element() {
        TAModelBuilder.ElementBuilder ret = new ElementBuilder();
        elements.add(ret);
        return ret;
    }
    public TAModelBuilder.ElementBuilder element(int index) {
        return elements.get(index);
    }
    public int getElementCount()
    {
        return elements.size();
    }

    @VisibleForTesting
    public JsonObject toJson() {
        JsonObject root = new JsonObject();

        if (this.parent != null) {
            root.addProperty("parent", this.parent.getLocation().toString());
        }

        if (!this.ambientOcclusion) {
            root.addProperty("ambientocclusion", this.ambientOcclusion);
        }

        if (this.guiLight != null) {
            root.addProperty("gui_light", this.guiLight.getSerializedName());
        }

        Map<TAModelBuilder.Perspective, ItemTransform> transforms = this.transforms.build();
        if (!transforms.isEmpty()) {
            JsonObject display = new JsonObject();
            for (Entry<TAModelBuilder.Perspective, ItemTransform> e : transforms.entrySet()) {
                JsonObject transform = new JsonObject();
                ItemTransform vec = e.getValue();
                if (vec.equals(ItemTransform.NO_TRANSFORM)) continue;
                if (!vec.rotation.equals(ItemTransform.Deserializer.DEFAULT_ROTATION)) {
                    transform.add("rotation", serializeVector3f(vec.rotation));
                }
                if (!vec.translation.equals(ItemTransform.Deserializer.DEFAULT_TRANSLATION)) {
                    transform.add("translation", serializeVector3f(e.getValue().translation));
                }
                if (!vec.scale.equals(ItemTransform.Deserializer.DEFAULT_SCALE)) {
                    transform.add("scale", serializeVector3f(e.getValue().scale));
                }
                display.add(e.getKey().name, transform);
            }
            root.add("display", display);
        }

        if (!this.textures.isEmpty()) {
            JsonObject textures = new JsonObject();
            for (Entry<String, String> e : this.textures.entrySet()) {
                textures.addProperty(e.getKey(), serializeLocOrKey(e.getValue()));
            }
            root.add("textures", textures);
        }

        if (!this.elements.isEmpty()) {
            JsonArray elements = new JsonArray();
            this.elements.stream().map(TAModelBuilder.ElementBuilder::build).forEach(part -> {
                JsonObject partObj = new JsonObject();
                partObj.add("from", serializeVector3f(part.from));
                partObj.add("to", serializeVector3f(part.to));

                if (part.rotation != null) {
                    JsonObject rotation = new JsonObject();
                    rotation.add("origin", serializeVector3f(part.rotation.origin));
                    rotation.addProperty("axis", part.rotation.axis.getSerializedName());
                    rotation.addProperty("angle", part.rotation.angle);
                    if (part.rotation.rescale) {
                        rotation.addProperty("rescale", part.rotation.rescale);
                    }
                    partObj.add("rotation", rotation);
                }

                if (!part.shade) {
                    partObj.addProperty("shade", part.shade);
                }

                JsonObject faces = new JsonObject();
                for (Direction dir : Direction.values()) {
                    BlockElementFace face = part.faces.get(dir);
                    if (face == null) continue;

                    JsonObject faceObj = new JsonObject();
                    faceObj.addProperty("texture", serializeLocOrKey(face.texture));
                    if (!Arrays.equals(face.uv.uvs, part.uvsByFace(dir))) {
                        faceObj.add("uv", new Gson().toJsonTree(face.uv.uvs));
                    }
                    if (face.cullForDirection != null) {
                        faceObj.addProperty("cullface", face.cullForDirection.getSerializedName());
                    }
                    if (face.uv.rotation != 0) {
                        faceObj.addProperty("rotation", face.uv.rotation);
                    }
                    if (face.tintIndex != -1) {
                        faceObj.addProperty("tintindex", face.tintIndex);
                    }
                    faces.add(dir.getSerializedName(), faceObj);
                }
                if (!part.faces.isEmpty()) {
                    partObj.add("faces", faces);
                }
                elements.add(partObj);
            });
            root.add("elements", elements);
        }

        return root;
    }

    private String serializeLocOrKey(String tex) {
        if (tex.charAt(0) == '#') {
            return tex;
        }
        return new ResourceLocation(tex).toString();
    }

    private JsonArray serializeVector3f(Vector3f vec) {
        JsonArray ret = new JsonArray();
        ret.add(serializeFloat(vec.x()));
        ret.add(serializeFloat(vec.y()));
        ret.add(serializeFloat(vec.z()));
        return ret;
    }

    private Number serializeFloat(float f) {
        if ((int) f == f) {
            return (int) f;
        }
        return f;
    }

    public static class ElementBuilder {

        private Vector3f from = new Vector3f();
        private Vector3f to = new Vector3f(16, 16, 16);
        private final Map<Direction, TAModelBuilder.ElementBuilder.FaceBuilder> faces = new LinkedHashMap<>();
        private TAModelBuilder.ElementBuilder.RotationBuilder rotation;
        private boolean shade = true;

        private void validateCoordinate(float coord, char name) {
            Preconditions.checkArgument(!(coord < -16.0F) && !(coord > 32.0F), "Position " + name + " out of range, must be within [-16, 32]. Found: %d", coord);
        }

        private void validatePosition(Vector3f pos) {
            validateCoordinate(pos.x(), 'x');
            validateCoordinate(pos.y(), 'y');
            validateCoordinate(pos.z(), 'z');
        }
        public TAModelBuilder.ElementBuilder from(float x, float y, float z) {
            this.from = new Vector3f(x, y, z);
            validatePosition(this.from);
            return this;
        }
        public TAModelBuilder.ElementBuilder to(float x, float y, float z) {
            this.to = new Vector3f(x, y, z);
            validatePosition(this.to);
            return this;
        }
        public TAModelBuilder.ElementBuilder.FaceBuilder face(Direction dir) {
            Preconditions.checkNotNull(dir, "Direction must not be null");
            return faces.computeIfAbsent(dir, TAModelBuilder.ElementBuilder.FaceBuilder::new);
        }

        public TAModelBuilder.ElementBuilder.RotationBuilder rotation() {
            if (this.rotation == null) {
                this.rotation = new TAModelBuilder.ElementBuilder.RotationBuilder();
            }

            return this.rotation;
        }

        public TAModelBuilder.ElementBuilder shade(boolean shade) {
            this.shade = shade;
            return this;
        }
        public TAModelBuilder.ElementBuilder allFaces(BiConsumer<Direction, TAModelBuilder.ElementBuilder.FaceBuilder> action) {
            Arrays.stream(Direction.values())
                    .forEach(d -> action.accept(d, face(d)));
            return this;
        }
        public TAModelBuilder.ElementBuilder faces(BiConsumer<Direction, TAModelBuilder.ElementBuilder.FaceBuilder> action) {
            faces.forEach((key, value) -> action.accept(key, value));
            return this;
        }
        public TAModelBuilder.ElementBuilder textureAll(String texture) {
            return allFaces(addTexture(texture));
        }
        public TAModelBuilder.ElementBuilder texture(String texture) {
            return faces(addTexture(texture));
        }
        public TAModelBuilder.ElementBuilder cube(String texture) {
            return allFaces(addTexture(texture).andThen((dir, f) -> f.cullface(dir)));
        }

        private BiConsumer<Direction, TAModelBuilder.ElementBuilder.FaceBuilder> addTexture(String texture) {
            return ($, f) -> f.texture(texture);
        }

        BlockElement build() {
            Map<Direction, BlockElementFace> faces = this.faces.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().build(), (k1, k2) -> { throw new IllegalArgumentException(); }, LinkedHashMap::new));
            return new BlockElement(from, to, faces, rotation == null ? null : rotation.build(), shade);
        }

        public class FaceBuilder {

            private Direction cullface;
            private int tintindex = -1;
            private String texture = MissingTextureAtlasSprite.getLocation().toString();
            private float[] uvs;
            private TAModelBuilder.FaceRotation rotation = TAModelBuilder.FaceRotation.ZERO;

            FaceBuilder(Direction dir) {
                // param unused for functional match
            }

            public TAModelBuilder.ElementBuilder.FaceBuilder cullface(@Nullable Direction dir) {
                this.cullface = dir;
                return this;
            }

            public TAModelBuilder.ElementBuilder.FaceBuilder tintindex(int index) {
                this.tintindex = index;
                return this;
            }
            public TAModelBuilder.ElementBuilder.FaceBuilder texture(String texture) {
                Preconditions.checkNotNull(texture, "Texture must not be null");
                this.texture = texture;
                return this;
            }

            public TAModelBuilder.ElementBuilder.FaceBuilder uvs(float u1, float v1, float u2, float v2) {
                this.uvs = new float[] { u1, v1, u2, v2 };
                return this;
            }
            public TAModelBuilder.ElementBuilder.FaceBuilder rotation(TAModelBuilder.FaceRotation rot) {
                Preconditions.checkNotNull(rot, "Rotation must not be null");
                this.rotation = rot;
                return this;
            }

            BlockElementFace build() {
                if (this.texture == null) {
                    throw new IllegalStateException("A model face must have a texture");
                }
                return new BlockElementFace(cullface, tintindex, texture, new BlockFaceUV(uvs, rotation.rotation));
            }

            public TAModelBuilder.ElementBuilder end() { return TAModelBuilder.ElementBuilder.this; }
        }

        public class RotationBuilder {

            private Vector3f origin;
            private Direction.Axis axis;
            private float angle;
            private boolean rescale;

            public TAModelBuilder.ElementBuilder.RotationBuilder origin(float x, float y, float z) {
                this.origin = new Vector3f(x, y, z);
                return this;
            }
            public TAModelBuilder.ElementBuilder.RotationBuilder axis(Direction.Axis axis) {
                Preconditions.checkNotNull(axis, "Axis must not be null");
                this.axis = axis;
                return this;
            }
            public TAModelBuilder.ElementBuilder.RotationBuilder angle(float angle) {
                Preconditions.checkArgument(angle == 0.0F || Mth.abs(angle) == 22.5F || Mth.abs(angle) == 45.0F, "Invalid rotation %f found, only -45/-22.5/0/22.5/45 allowed", angle);
                this.angle = angle;
                return this;
            }

            public TAModelBuilder.ElementBuilder.RotationBuilder rescale(boolean rescale) {
                this.rescale = rescale;
                return this;
            }

            BlockElementRotation build() {
                return new BlockElementRotation(origin, axis, angle, rescale);
            }

            public TAModelBuilder.ElementBuilder end() { return TAModelBuilder.ElementBuilder.this; }
        }
    }

    public enum FaceRotation {
        ZERO(0),
        CLOCKWISE_90(90),
        UPSIDE_DOWN(180),
        COUNTERCLOCKWISE_90(270),
        ;

        final int rotation;

        private FaceRotation(int rotation) {
            this.rotation = rotation;
        }
    }

    public enum Perspective {

        THIRDPERSON_RIGHT(TransformType.THIRD_PERSON_RIGHT_HAND, "thirdperson_righthand"),
        THIRDPERSON_LEFT(TransformType.THIRD_PERSON_LEFT_HAND, "thirdperson_lefthand"),
        FIRSTPERSON_RIGHT(TransformType.FIRST_PERSON_RIGHT_HAND, "firstperson_righthand"),
        FIRSTPERSON_LEFT(TransformType.FIRST_PERSON_LEFT_HAND, "firstperson_lefthand"),
        HEAD(TransformType.HEAD, "head"),
        GUI(TransformType.GUI, "gui"),
        GROUND(TransformType.GROUND, "ground"),
        FIXED(TransformType.FIXED, "fixed"),
        ;

        public final TransformType vanillaType;
        final String name;

        Perspective(TransformType vanillaType, String name) {
            this.vanillaType = vanillaType;
            this.name = name;
        }
    }

    public static class TransformsBuilder {

        private final Map<TAModelBuilder.Perspective, TAModelBuilder.TransformsBuilder.TransformVecBuilder> transforms = new LinkedHashMap<>();

        /**
         * Begin building a new transform for the given perspective.
         *
         * @param type the perspective to create or return the builder for
         * @return the builder for the given perspective
         * @throws NullPointerException if {@code type} is {@code null}
         */
        public TAModelBuilder.TransformsBuilder.TransformVecBuilder transform(TAModelBuilder.Perspective type) {
            Preconditions.checkNotNull(type, "Perspective cannot be null");
            return transforms.computeIfAbsent(type, TAModelBuilder.TransformsBuilder.TransformVecBuilder::new);
        }

        Map<TAModelBuilder.Perspective, ItemTransform> build() {
            return this.transforms.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().build(), (k1, k2) -> { throw new IllegalArgumentException(); }, LinkedHashMap::new));
        }

        public class TransformVecBuilder {

            private Vector3f rotation = ItemTransform.Deserializer.DEFAULT_ROTATION.copy();
            private Vector3f translation = ItemTransform.Deserializer.DEFAULT_TRANSLATION.copy();
            private Vector3f scale = ItemTransform.Deserializer.DEFAULT_SCALE.copy();

            TransformVecBuilder(TAModelBuilder.Perspective type) {
                // param unused for functional match
            }

            public TAModelBuilder.TransformsBuilder.TransformVecBuilder rotation(float x, float y, float z) {
                this.rotation = new Vector3f(x, y, z);
                return this;
            }

            public TAModelBuilder.TransformsBuilder.TransformVecBuilder translation(float x, float y, float z) {
                this.translation = new Vector3f(x, y, z);
                return this;
            }

            public TAModelBuilder.TransformsBuilder.TransformVecBuilder scale(float sc) {
                return scale(sc, sc, sc);
            }

            public TAModelBuilder.TransformsBuilder.TransformVecBuilder scale(float x, float y, float z) {
                this.scale = new Vector3f(x, y, z);
                return this;
            }

            ItemTransform build() {
                return new ItemTransform(rotation, translation, scale);
            }

            public TAModelBuilder.TransformsBuilder end() { return TAModelBuilder.TransformsBuilder.this; }
        }
    }

    public enum ExistingBlockModels {
        block(new TAModelBuilder(new ResourceLocation("block/block"))),
        cube_all(new TAModelBuilder(new ResourceLocation("block/cube_all"))),
        cube_column(new TAModelBuilder(new ResourceLocation("block/cube_column"))),
        cube_column_horizontal(new TAModelBuilder(new ResourceLocation("block/cube_column_horizontal"))),
        leaves(new TAModelBuilder(new ResourceLocation("block/leaves"))),
        thin_block(new TAModelBuilder(new ResourceLocation("block/thin_block")))
        ;

        public final ModelFile model;
        ExistingBlockModels(ModelFile model) {
            this.model = model;
        }
    }

    public enum ExistingItemModels {
        item_generated(new TAModelBuilder(new ResourceLocation("item/generated")));
        public final ModelFile model;

        ExistingItemModels(ModelFile model) {
            this.model = model;
        }
    }
}
