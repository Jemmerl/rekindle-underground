package com.jemmerl.rekindleunderground.data.generators.server;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.data.types.StoneType;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.tags.ModTags;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.Objects;
import java.util.function.Consumer;

public class ModCobblestoneRecipeProvider extends RecipeProvider {

    public ModCobblestoneRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (StoneType stoneType : StoneType.values()) {
            if (!stoneType.hasCobble()) {
                continue;
            }

            ShapedRecipeBuilder.shapedRecipe(Objects.requireNonNull(stoneType.getCobbleState()).getBlock())
                    .key('x', UtilMethods.stringToItem(RekindleUnderground.MOD_ID + ":" + stoneType.getName() + "_rock"))
                    .patternLine("xx")
                    .patternLine("xx")
                    .setGroup("cobblestone")
                    .addCriterion("has_rock", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.ROCKS).build()))
                    .build(consumer);
//

        }
    }

}
