// Cubik Studio 2.9.480 Beta JAVA exporter
// Designed by MightyDanp with Cubik Studio - https://cubik.studio

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CampfireLogAsh extends ModelBase {

    //fields
    public ModelRenderer e1;
    public ModelRenderer e2;
    public ModelRenderer e3;
    public ModelRenderer e4;
    public ModelRenderer e5;

    public CampfireLogAsh()
    {
        textureWidth = 64;
        textureHeight = 128;

        e1 = new ModelRenderer(this, 0, 108);
        e1.setRotationPoint(-7F, 24F, -8F);
        e1.addBox(0F, -4F, 0F, 4, 4, 16);
        e1.setTextureSize(64, 128);
        e1.mirror = false;
        setRotation(e1, 0F, 0F, 0F);
        e2 = new ModelRenderer(this, 0, 63);
        e2.setRotationPoint(-8F, 27F, 3F);
        e2.addBox(0F, -4F, 0F, 16, 4, 4);
        e2.setTextureSize(64, 128);
        e2.mirror = false;
        setRotation(e2, 0F, 0F, 0F);
        e3 = new ModelRenderer(this, 0, 88);
        e3.setRotationPoint(3F, 24F, -8F);
        e3.addBox(0F, -4F, 0F, 4, 4, 16);
        e3.setTextureSize(64, 128);
        e3.mirror = false;
        setRotation(e3, 0F, 0F, 0F);
        e4 = new ModelRenderer(this, 0, 55);
        e4.setRotationPoint(-8F, 27F, -7F);
        e4.addBox(0F, -4F, 0F, 16, 4, 4);
        e4.setTextureSize(64, 128);
        e4.mirror = false;
        setRotation(e4, 0F, 0F, 0F);
        e5 = new ModelRenderer(this, 0, 71);
        e5.setRotationPoint(-3F, 24F, -8F);
        e5.addBox(0F, -1F, 0F, 6, 1, 16);
        e5.setTextureSize(64, 128);
        e5.mirror = false;
        setRotation(e5, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        e1.render(f5);
        e2.render(f5);
        e3.render(f5);
        e4.render(f5);
        e5.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
     
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
 
}