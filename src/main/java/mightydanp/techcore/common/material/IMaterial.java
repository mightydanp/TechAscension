package mightydanp.techcore.common.material;

public interface IMaterial {
    void save(TCMaterial material) throws InstantiationException, IllegalAccessException, NoSuchFieldException;

    void clientRenderLayerInit(TCMaterial material);

    void registerColorForBlock(TCMaterial material);

    void registerColorForItem(TCMaterial material);
}
