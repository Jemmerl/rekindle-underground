package com.jemmerl.jemsgeology.init;

import com.jemmerl.jemsgeology.JemsGeology;
import com.jemmerl.jemsgeology.blocks.FallingCobbleBlock;
import com.jemmerl.jemsgeology.blocks.FallingOreBlock;
import com.jemmerl.jemsgeology.blocks.StoneOreBlock;
import com.jemmerl.jemsgeology.data.enums.GeologyType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    static final ItemGroup STONE_GROUP = ModItemGroups.JEMGEO_STONE_GROUP;
    static final ItemGroup COBBLE_GROUP = ModItemGroups.JEMGEO_COBBLE_GROUP;

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, JemsGeology.MOD_ID);

    // Property Templates
    public static Block.Properties STONE_PROP = AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE)
            .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);

    public static Block.Properties REGOLITH_PROP = AbstractBlock.Properties.create(Material.EARTH)
            .sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.7f);


    ////////////
    // STONES //
    ////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_STONE = registerBlock(GeologyType.CHALK.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CHALK), GeologyType.CHALK), 64, STONE_GROUP);
    public static final RegistryObject<Block> LIMESTONE_STONE = registerBlock(GeologyType.LIMESTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LIMESTONE), GeologyType.LIMESTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> DOLOSTONE_STONE = registerBlock(GeologyType.DOLOSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DOLOSTONE), GeologyType.DOLOSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> MARLSTONE_STONE = registerBlock(GeologyType.MARLSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MARLSTONE), GeologyType.MARLSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SHALE_STONE = registerBlock(GeologyType.SHALE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SHALE), GeologyType.SHALE), 64, STONE_GROUP);
    public static final RegistryObject<Block> LIMY_SHALE_STONE = registerBlock(GeologyType.LIMY_SHALE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LIMY_SHALE), GeologyType.LIMY_SHALE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SANDSTONE_STONE = registerBlock(GeologyType.SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SANDSTONE), GeologyType.SANDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> RED_SANDSTONE_STONE = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RED_SANDSTONE), GeologyType.RED_SANDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> ARKOSE_STONE = registerBlock(GeologyType.ARKOSE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ARKOSE), GeologyType.ARKOSE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GREYWACKE_STONE = registerBlock(GeologyType.GREYWACKE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GREYWACKE), GeologyType.GREYWACKE), 64, STONE_GROUP);
    public static final RegistryObject<Block> MUDSTONE_STONE = registerBlock(GeologyType.MUDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MUDSTONE), GeologyType.MUDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> CLAYSTONE_STONE = registerBlock(GeologyType.CLAYSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CLAYSTONE), GeologyType.CLAYSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SILTSTONE_STONE = registerBlock(GeologyType.SILTSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SILTSTONE), GeologyType.SILTSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> CONGLOMERATE_STONE = registerBlock(GeologyType.CONGLOMERATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CONGLOMERATE), GeologyType.CONGLOMERATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> VEIN_QUARTZ_STONE = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.VEIN_QUARTZ), GeologyType.VEIN_QUARTZ), 64, STONE_GROUP);

    // evaporites
    public static final RegistryObject<Block> ROCKSALT_STONE = registerBlock(GeologyType.ROCKSALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ROCKSALT), GeologyType.ROCKSALT), 64, STONE_GROUP);
    public static final RegistryObject<Block> ROCKGYPSUM_STONE = registerBlock(GeologyType.ROCKGYPSUM.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ROCKGYPSUM), GeologyType.ROCKGYPSUM), 64, STONE_GROUP);
    public static final RegistryObject<Block> BORAX_STONE = registerBlock(GeologyType.BORAX.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BORAX), GeologyType.BORAX), 64, STONE_GROUP);
    public static final RegistryObject<Block> KERNITE_STONE = registerBlock(GeologyType.KERNITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.KERNITE), GeologyType.KERNITE), 64, STONE_GROUP);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_STONE = registerBlock(GeologyType.RHYOLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RHYOLITE), GeologyType.RHYOLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> DACITE_STONE = registerBlock(GeologyType.DACITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DACITE), GeologyType.DACITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> ANDESITE_STONE = registerBlock(GeologyType.ANDESITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ANDESITE), GeologyType.ANDESITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> TRACHYTE_STONE = registerBlock(GeologyType.TRACHYTE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.TRACHYTE), GeologyType.TRACHYTE), 64, STONE_GROUP);
    public static final RegistryObject<Block> BASALT_STONE = registerBlock(GeologyType.BASALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BASALT), GeologyType.BASALT), 64, STONE_GROUP);
    public static final RegistryObject<Block> PAHOEHOE_STONE = registerBlock(GeologyType.PAHOEHOE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PAHOEHOE), GeologyType.PAHOEHOE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SCORIA_STONE = registerBlock(GeologyType.SCORIA.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SCORIA), GeologyType.SCORIA), 64, STONE_GROUP);
    public static final RegistryObject<Block> RHYOLITIC_TUFF_STONE = registerBlock(GeologyType.RHYOLITIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RHYOLITIC_TUFF), GeologyType.RHYOLITIC_TUFF), 64, STONE_GROUP);
    public static final RegistryObject<Block> TRACHYTIC_TUFF_STONE = registerBlock(GeologyType.TRACHYTIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.TRACHYTIC_TUFF), GeologyType.TRACHYTIC_TUFF), 64, STONE_GROUP);
    public static final RegistryObject<Block> ANDESITIC_TUFF_STONE = registerBlock(GeologyType.ANDESITIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ANDESITIC_TUFF), GeologyType.ANDESITIC_TUFF), 64, STONE_GROUP);
    public static final RegistryObject<Block> BASALTIC_TUFF_STONE = registerBlock(GeologyType.BASALTIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BASALTIC_TUFF), GeologyType.BASALTIC_TUFF), 64, STONE_GROUP);
    public static final RegistryObject<Block> ULTRAMAFIC_TUFF_STONE = registerBlock(GeologyType.ULTRAMAFIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ULTRAMAFIC_TUFF), GeologyType.ULTRAMAFIC_TUFF), 64, STONE_GROUP);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_STONE = registerBlock(GeologyType.DIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DIORITE), GeologyType.DIORITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GRANODIORITE_STONE = registerBlock(GeologyType.GRANODIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GRANODIORITE), GeologyType.GRANODIORITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GRANITE_STONE = registerBlock(GeologyType.GRANITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GRANITE), GeologyType.GRANITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SYENITE_STONE = registerBlock(GeologyType.SYENITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SYENITE), GeologyType.SYENITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GABBRO_STONE = registerBlock(GeologyType.GABBRO.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GABBRO), GeologyType.GABBRO), 64, STONE_GROUP);
    public static final RegistryObject<Block> DIABASE_STONE = registerBlock(GeologyType.DIABASE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DIABASE), GeologyType.DIABASE), 64, STONE_GROUP);
    public static final RegistryObject<Block> KIMBERLITE_STONE = registerBlock(GeologyType.KIMBERLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.KIMBERLITE), GeologyType.KIMBERLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> LAMPROITE_STONE = registerBlock(GeologyType.LAMPROITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LAMPROITE), GeologyType.LAMPROITE), 64, STONE_GROUP);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_STONE = registerBlock(GeologyType.QUARTZITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.QUARTZITE), GeologyType.QUARTZITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SCHIST_STONE = registerBlock(GeologyType.SCHIST.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SCHIST), GeologyType.SCHIST), 64, STONE_GROUP);
    public static final RegistryObject<Block> PHYLLITE_STONE = registerBlock(GeologyType.PHYLLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PHYLLITE), GeologyType.PHYLLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SLATE_STONE = registerBlock(GeologyType.SLATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SLATE), GeologyType.SLATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GNEISS_STONE = registerBlock(GeologyType.GNEISS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GNEISS), GeologyType.GNEISS), 64, STONE_GROUP);
    public static final RegistryObject<Block> MARBLE_STONE = registerBlock(GeologyType.MARBLE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MARBLE), GeologyType.MARBLE), 64, STONE_GROUP);

    public static final RegistryObject<Block> PELITIC_HORNFELS_STONE = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PELITIC_HORNFELS), GeologyType.PELITIC_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_STONE = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CARBONATE_HORNFELS), GeologyType.CARBONATE_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> MAFIC_HORNFELS_STONE = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MAFIC_HORNFELS), GeologyType.MAFIC_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> METACONGLOMERATE_STONE = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.METACONGLOMERATE), GeologyType.METACONGLOMERATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GREISEN_STONE = registerBlock(GeologyType.GREISEN.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GREISEN), GeologyType.GREISEN), 64, STONE_GROUP);


    /////////////
    // COBBLES //
    /////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_COBBLES = registerBlock(GeologyType.CHALK.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.CHALK), GeologyType.CHALK), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LIMESTONE_COBBLES = registerBlock(GeologyType.LIMESTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.LIMESTONE), GeologyType.LIMESTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DOLOSTONE_COBBLES = registerBlock(GeologyType.DOLOSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.DOLOSTONE), GeologyType.DOLOSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MARLSTONE_COBBLES = registerBlock(GeologyType.MARLSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.MARLSTONE), GeologyType.MARLSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SHALE_COBBLES = registerBlock(GeologyType.SHALE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SHALE), GeologyType.SHALE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LIMY_SHALE_COBBLES = registerBlock(GeologyType.LIMY_SHALE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.LIMY_SHALE), GeologyType.LIMY_SHALE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SANDSTONE_COBBLES = registerBlock(GeologyType.SANDSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SANDSTONE), GeologyType.SANDSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> RED_SANDSTONE_COBBLES = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.RED_SANDSTONE), GeologyType.RED_SANDSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> ARKOSE_COBBLES = registerBlock(GeologyType.ARKOSE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.ARKOSE), GeologyType.ARKOSE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GREYWACKE_COBBLES = registerBlock(GeologyType.GREYWACKE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GREYWACKE), GeologyType.GREYWACKE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MUDSTONE_COBBLES = registerBlock(GeologyType.MUDSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.MUDSTONE), GeologyType.MUDSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CLAYSTONE_COBBLES = registerBlock(GeologyType.CLAYSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.CLAYSTONE), GeologyType.CLAYSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SILTSTONE_COBBLES = registerBlock(GeologyType.SILTSTONE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SILTSTONE), GeologyType.SILTSTONE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CONGLOMERATE_COBBLES = registerBlock(GeologyType.CONGLOMERATE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.CONGLOMERATE), GeologyType.CONGLOMERATE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> VEIN_QUARTZ_COBBLES = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.VEIN_QUARTZ), GeologyType.VEIN_QUARTZ), 64, COBBLE_GROUP);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_COBBLES = registerBlock(GeologyType.RHYOLITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.RHYOLITE), GeologyType.RHYOLITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DACITE_COBBLES = registerBlock(GeologyType.DACITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.DACITE), GeologyType.DACITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> ANDESITE_COBBLES = registerBlock(GeologyType.ANDESITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.ANDESITE), GeologyType.ANDESITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> TRACHYTE_COBBLES = registerBlock(GeologyType.TRACHYTE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.TRACHYTE), GeologyType.TRACHYTE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> BASALT_COBBLES = registerBlock(GeologyType.BASALT.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.BASALT), GeologyType.BASALT), 64, COBBLE_GROUP);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_COBBLES = registerBlock(GeologyType.DIORITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.DIORITE), GeologyType.DIORITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GRANODIORITE_COBBLES = registerBlock(GeologyType.GRANODIORITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GRANODIORITE), GeologyType.GRANODIORITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GRANITE_COBBLES = registerBlock(GeologyType.GRANITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GRANITE), GeologyType.GRANITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SYENITE_COBBLES = registerBlock(GeologyType.SYENITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SYENITE), GeologyType.SYENITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GABBRO_COBBLES = registerBlock(GeologyType.GABBRO.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GABBRO), GeologyType.GABBRO), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DIABASE_COBBLES = registerBlock(GeologyType.DIABASE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.DIABASE), GeologyType.DIABASE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> KIMBERLITE_COBBLES = registerBlock(GeologyType.KIMBERLITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.KIMBERLITE), GeologyType.KIMBERLITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LAMPROITE_COBBLES = registerBlock(GeologyType.LAMPROITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.LAMPROITE), GeologyType.LAMPROITE), 64, COBBLE_GROUP);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_COBBLES = registerBlock(GeologyType.QUARTZITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.QUARTZITE), GeologyType.QUARTZITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SCHIST_COBBLES = registerBlock(GeologyType.SCHIST.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SCHIST), GeologyType.SCHIST), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> PHYLLITE_COBBLES = registerBlock(GeologyType.PHYLLITE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.PHYLLITE), GeologyType.PHYLLITE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SLATE_COBBLES = registerBlock(GeologyType.SLATE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.SLATE), GeologyType.SLATE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GNEISS_COBBLES = registerBlock(GeologyType.GNEISS.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GNEISS), GeologyType.GNEISS), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MARBLE_COBBLES = registerBlock(GeologyType.MARBLE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.MARBLE), GeologyType.MARBLE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> PELITIC_HORNFELS_COBBLES = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.PELITIC_HORNFELS), GeologyType.PELITIC_HORNFELS), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_COBBLES = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.CARBONATE_HORNFELS), GeologyType.CARBONATE_HORNFELS), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MAFIC_HORNFELS_COBBLES = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.MAFIC_HORNFELS), GeologyType.MAFIC_HORNFELS), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> METACONGLOMERATE_COBBLES = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.METACONGLOMERATE), GeologyType.METACONGLOMERATE), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GREISEN_COBBLES = registerBlock(GeologyType.GREISEN.getName() + "_cobbles", () -> new FallingCobbleBlock(buildCobblesProperties(GeologyType.GREISEN), GeologyType.GREISEN), 64, COBBLE_GROUP);


    //////////////////
    // COBBLESTONES //
    //////////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_COBBLESTONE = registerBlock(GeologyType.CHALK.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LIMESTONE_COBBLESTONE = registerBlock(GeologyType.LIMESTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DOLOSTONE_COBBLESTONE = registerBlock(GeologyType.DOLOSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MARLSTONE_COBBLESTONE = registerBlock(GeologyType.MARLSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SHALE_COBBLESTONE = registerBlock(GeologyType.SHALE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LIMY_SHALE_COBBLESTONE = registerBlock(GeologyType.LIMY_SHALE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SANDSTONE_COBBLESTONE = registerBlock(GeologyType.SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> RED_SANDSTONE_COBBLESTONE = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> ARKOSE_COBBLESTONE = registerBlock(GeologyType.ARKOSE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GREYWACKE_COBBLESTONE = registerBlock(GeologyType.GREYWACKE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MUDSTONE_COBBLESTONE = registerBlock(GeologyType.MUDSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CLAYSTONE_COBBLESTONE = registerBlock(GeologyType.CLAYSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SILTSTONE_COBBLESTONE = registerBlock(GeologyType.SILTSTONE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CONGLOMERATE_COBBLESTONE = registerBlock(GeologyType.CONGLOMERATE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> VEIN_QUARTZ_COBBLESTONE = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_COBBLESTONE = registerBlock(GeologyType.RHYOLITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DACITE_COBBLESTONE = registerBlock(GeologyType.DACITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> ANDESITE_COBBLESTONE = registerBlock(GeologyType.ANDESITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> TRACHYTE_COBBLESTONE = registerBlock(GeologyType.TRACHYTE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> BASALT_COBBLESTONE = registerBlock(GeologyType.BASALT.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_COBBLESTONE = registerBlock(GeologyType.DIORITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GRANODIORITE_COBBLESTONE = registerBlock(GeologyType.GRANODIORITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GRANITE_COBBLESTONE = registerBlock(GeologyType.GRANITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SYENITE_COBBLESTONE = registerBlock(GeologyType.SYENITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GABBRO_COBBLESTONE = registerBlock(GeologyType.GABBRO.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> DIABASE_COBBLESTONE = registerBlock(GeologyType.DIABASE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> KIMBERLITE_COBBLESTONE = registerBlock(GeologyType.KIMBERLITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> LAMPROITE_COBBLESTONE = registerBlock(GeologyType.LAMPROITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_COBBLESTONE = registerBlock(GeologyType.QUARTZITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SCHIST_COBBLESTONE = registerBlock(GeologyType.SCHIST.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> PHYLLITE_COBBLESTONE = registerBlock(GeologyType.PHYLLITE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> SLATE_COBBLESTONE = registerBlock(GeologyType.SLATE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GNEISS_COBBLESTONE = registerBlock(GeologyType.GNEISS.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MARBLE_COBBLESTONE = registerBlock(GeologyType.MARBLE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> PELITIC_HORNFELS_COBBLESTONE = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_COBBLESTONE = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> MAFIC_HORNFELS_COBBLESTONE = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> METACONGLOMERATE_COBBLESTONE = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);
    public static final RegistryObject<Block> GREISEN_COBBLESTONE = registerBlock(GeologyType.GREISEN.getName() + "_cobblestone", () -> new Block(buildCobblestoneProperties()), 64, COBBLE_GROUP);


    //////////////
    // REGOLITH //
    //////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_REGOLITH = registerBlock(GeologyType.CHALK.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CHALK), 64, STONE_GROUP);
    public static final RegistryObject<Block> LIMESTONE_REGOLITH = registerBlock(GeologyType.LIMESTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LIMESTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> DOLOSTONE_REGOLITH = registerBlock(GeologyType.DOLOSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DOLOSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> MARLSTONE_REGOLITH = registerBlock(GeologyType.MARLSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MARLSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SHALE_REGOLITH = registerBlock(GeologyType.SHALE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SHALE), 64, STONE_GROUP);
    public static final RegistryObject<Block> LIMY_SHALE_REGOLITH = registerBlock(GeologyType.LIMY_SHALE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LIMY_SHALE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SANDSTONE_REGOLITH = registerBlock(GeologyType.SANDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SANDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> RED_SANDSTONE_REGOLITH = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.RED_SANDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> ARKOSE_REGOLITH = registerBlock(GeologyType.ARKOSE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.ARKOSE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GREYWACKE_REGOLITH = registerBlock(GeologyType.GREYWACKE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GREYWACKE), 64, STONE_GROUP);
    public static final RegistryObject<Block> MUDSTONE_REGOLITH = registerBlock(GeologyType.MUDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MUDSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> CLAYSTONE_REGOLITH = registerBlock(GeologyType.CLAYSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CLAYSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SILTSTONE_REGOLITH = registerBlock(GeologyType.SILTSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SILTSTONE), 64, STONE_GROUP);
    public static final RegistryObject<Block> CONGLOMERATE_REGOLITH = registerBlock(GeologyType.CONGLOMERATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CONGLOMERATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> VEIN_QUARTZ_REGOLITH = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.VEIN_QUARTZ), 64, STONE_GROUP);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_REGOLITH = registerBlock(GeologyType.RHYOLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.RHYOLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> DACITE_REGOLITH = registerBlock(GeologyType.DACITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DACITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> ANDESITE_REGOLITH = registerBlock(GeologyType.ANDESITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.ANDESITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> TRACHYTE_REGOLITH = registerBlock(GeologyType.TRACHYTE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.TRACHYTE), 64, STONE_GROUP);
    public static final RegistryObject<Block> BASALT_REGOLITH = registerBlock(GeologyType.BASALT.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.BASALT), 64, STONE_GROUP);
    public static final RegistryObject<Block> SCORIA_REGOLITH = registerBlock(GeologyType.SCORIA.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SCORIA), 64, STONE_GROUP);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_REGOLITH = registerBlock(GeologyType.DIORITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DIORITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GRANODIORITE_REGOLITH = registerBlock(GeologyType.GRANODIORITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GRANODIORITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GRANITE_REGOLITH = registerBlock(GeologyType.GRANITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GRANITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SYENITE_REGOLITH = registerBlock(GeologyType.SYENITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SYENITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GABBRO_REGOLITH = registerBlock(GeologyType.GABBRO.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GABBRO), 64, STONE_GROUP);
    public static final RegistryObject<Block> DIABASE_REGOLITH = registerBlock(GeologyType.DIABASE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DIABASE), 64, STONE_GROUP);
    public static final RegistryObject<Block> KIMBERLITE_REGOLITH = registerBlock(GeologyType.KIMBERLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.KIMBERLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> LAMPROITE_REGOLITH = registerBlock(GeologyType.LAMPROITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LAMPROITE), 64, STONE_GROUP);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_REGOLITH = registerBlock(GeologyType.QUARTZITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.QUARTZITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SCHIST_REGOLITH = registerBlock(GeologyType.SCHIST.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SCHIST), 64, STONE_GROUP);
    public static final RegistryObject<Block> PHYLLITE_REGOLITH = registerBlock(GeologyType.PHYLLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.PHYLLITE), 64, STONE_GROUP);
    public static final RegistryObject<Block> SLATE_REGOLITH = registerBlock(GeologyType.SLATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SLATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GNEISS_REGOLITH = registerBlock(GeologyType.GNEISS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GNEISS), 64, STONE_GROUP);
    public static final RegistryObject<Block> MARBLE_REGOLITH = registerBlock(GeologyType.MARBLE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MARBLE), 64, STONE_GROUP);
    public static final RegistryObject<Block> PELITIC_HORNFELS_REGOLITH = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.PELITIC_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_REGOLITH = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CARBONATE_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> MAFIC_HORNFELS_REGOLITH = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MAFIC_HORNFELS), 64, STONE_GROUP);
    public static final RegistryObject<Block> METACONGLOMERATE_REGOLITH = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.METACONGLOMERATE), 64, STONE_GROUP);
    public static final RegistryObject<Block> GREISEN_REGOLITH = registerBlock(GeologyType.GREISEN.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GREISEN), 64, STONE_GROUP);


    //////////////
    // DETRITUS //
    //////////////

    // Stable Detritus
    public static final RegistryObject<Block> DIRT_STONE = registerBlock(GeologyType.DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
            .hardnessAndResistance(0.5f), GeologyType.DIRT), 64, STONE_GROUP);

    public static final RegistryObject<Block> COARSE_DIRT_STONE = registerBlock(GeologyType.COARSE_DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.5f), GeologyType.COARSE_DIRT), 64, STONE_GROUP);

    public static final RegistryObject<Block> CLAY_STONE = registerBlock(GeologyType.CLAY.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.CLAY).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), GeologyType.CLAY), 64, STONE_GROUP);

    // Falling Detritus
    public static final RegistryObject<Block> SAND_STONE = registerBlock(GeologyType.SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
            .hardnessAndResistance(0.5f), GeologyType.SAND), 64, STONE_GROUP);

    public static final RegistryObject<Block> RED_SAND_STONE = registerBlock(GeologyType.RED_SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
                    .hardnessAndResistance(0.5f), GeologyType.RED_SAND), 64, STONE_GROUP);

    public static final RegistryObject<Block> GRAVEL_STONE = registerBlock(GeologyType.GRAVEL.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), GeologyType.GRAVEL), 64, STONE_GROUP);


    //////////////////
    // TOOL METHODS //
    //////////////////

    // Stone Property Builder
    private static Block.Properties buildStoneProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getStoneHardness(), geologyType.getStoneResistance());
    }

    // Cobbles Property Builder
    private static Block.Properties buildCobblesProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getCobbleHardness(), geologyType.getCobbleResistance());
    }

    // Cobblestone Property Builder
    private static Block.Properties buildCobblestoneProperties() {
        return STONE_PROP.hardnessAndResistance(2F, 7F);
    }

    // Block and BlockItem registry methods
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, int stackSize, ItemGroup itemGroup) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock, stackSize, itemGroup);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, int stackSize, ItemGroup itemGroup) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(itemGroup).maxStackSize(stackSize)));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
}

// PROTOTYPE REGISTRATION CODE, ISSUE WITH CHAINING SUPPLIERS
/*

// Sedimentary
    public static final RegistryObject<Block> CHALK_STONE = registerGeoBlock(GeologyType.CHALK, 64);
    public static final RegistryObject<Block> LIMESTONE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> DOLOSTONE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    // marlstone
    public static final RegistryObject<Block> SHALE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    // limyshale
    public static final RegistryObject<Block> SANDSTONE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> RED_SANDSTONE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    // arkose
    public static final RegistryObject<Block> GREYWACKE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> MUDSTONE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    //claystone
    //siltstone
    //conglomerate
    public static final RegistryObject<Block> VEIN_QUARTZ_STONE = registerGeoBlock(GeologyType.XXX, 64);

    // evaporites
    public static final RegistryObject<Block> ROCKSALT_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> ROCKGYPSUM_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> BORAX_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> KERNITE_STONE = registerGeoBlock(GeologyType.XXX, 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> DACITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> ANDESITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> BASALT_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> PAHOEHOE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> SCORIA_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> RHYOLITIC_TUFF_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> TRACHYTIC_TUFF_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> ANDESITIC_TUFF_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> BASALTIC_TUFF_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> ULTRAMAFIC_TUFF_STONE = registerGeoBlock(GeologyType.XXX, 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> GRANODIORITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> GRANITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> SYENITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> GABBRO_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> DIABASE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> KIMBERLITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> LAMPROITE_STONE = registerGeoBlock(GeologyType.XXX, 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> SCHIST_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> PHYLLITE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> GNEISS_STONE = registerGeoBlock(GeologyType.XXX, 64);
    public static final RegistryObject<Block> MARBLE_STONE = registerGeoBlock(GeologyType.XXX, 64);
    //pelitic
    //carbonate
    //mafic
    //metaconglomerate
    //greisen

 */