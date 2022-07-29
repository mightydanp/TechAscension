package mightydanp.techapi.common.resources.data.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootTableData {
    private final ResourceLocation name;
    public LootTable lootTable;

    public LootTableData(ResourceLocation nameIn) {
        name = nameIn;
    }

    public ResourceLocation getName() {
        return name;
    }

    public LootTableData setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    public JsonObject createJson(){
        return LootTables.serialize(lootTable).getAsJsonObject();
    }

    public static LootTable standardDropTable(Block b) {
        return LootTable.lootTable().withPool(createStandardDrops(b)).build();
    }

    public static LootPool.Builder createStandardDrops(ItemLike itemProvider) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion()).add(LootItem.lootTableItem(itemProvider));
    }

}
