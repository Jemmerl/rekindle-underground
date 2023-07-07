package com.jemmerl.jemsgeology.data.generators.server;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import com.jemmerl.jemsgeology.init.ModTags;
import com.jemmerl.jemsgeology.util.lists.GeoListWrapper;
import com.jemmerl.jemsgeology.util.lists.ModBlockLists;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class ModCobblestoneRecipeProvider extends RecipeProvider {

    public ModCobblestoneRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for (GeologyType stone : GeologyType.values()) {
            if (stone.hasCobble() && !stone.equals(GeologyType.PAHOEHOE)) {
                GeoListWrapper geoList = ModBlockLists.GEO_LIST.get(stone);
                ShapedRecipeBuilder.shapedRecipe(geoList.getCobbleBlock())
                        .key('x', geoList.getRockItem())
                        .patternLine("xx")
                        .patternLine("xx")
                        .setGroup("cobblestone")
                        .addCriterion("has_rock", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(ModTags.Items.ROCKS).build()))
                        .build(consumer);
            }

        }
    }

}
