package com.jemmerl.jemsgeology.init.conditionsinit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.jemmerl.jemsgeology.init.ModLootConditionTypes;
import com.jemmerl.jemsgeology.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;

public class StonesCondition implements ILootCondition {

    private static final StonesCondition INSTANCE = new StonesCondition();

    private StonesCondition() {}

    @Override
    public LootConditionType getConditionType() {
        return ModLootConditionTypes.IS_GEOSTONE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockState = lootContext.get(LootParameters.BLOCK_STATE);
        return (blockState != null) && ModTags.Blocks.JEMSGEO_STONE.contains(blockState.getBlock());
    }

    public static class Serializer implements ILootSerializer<StonesCondition> {
        @Override
        public void serialize(JsonObject jsonObject, StonesCondition stonesCondition, JsonSerializationContext context) {}

        @Override
        public StonesCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return INSTANCE;
        }
    }
}
