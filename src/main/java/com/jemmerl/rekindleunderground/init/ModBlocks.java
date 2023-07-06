package com.jemmerl.rekindleunderground.init;

import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.blocks.FallingOreBlock;
import com.jemmerl.rekindleunderground.blocks.IOreBlock;
import com.jemmerl.rekindleunderground.blocks.StoneOreBlock;
import com.jemmerl.rekindleunderground.data.enums.GeologyType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RekindleUnderground.MOD_ID);

    // Property Templates
    public static Block.Properties STONE_PROP = AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.STONE)
            .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0);

    public static Block.Properties REGOLITH_PROP = AbstractBlock.Properties.create(Material.EARTH)
            .sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.7f);


    ////////////
    // STONES //
    ////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_STONE = registerBlock(GeologyType.CHALK.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CHALK), GeologyType.CHALK), 64);
    public static final RegistryObject<Block> LIMESTONE_STONE = registerBlock(GeologyType.LIMESTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LIMESTONE), GeologyType.LIMESTONE), 64);
    public static final RegistryObject<Block> DOLOSTONE_STONE = registerBlock(GeologyType.DOLOSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DOLOSTONE), GeologyType.DOLOSTONE), 64);
    public static final RegistryObject<Block> MARLSTONE_STONE = registerBlock(GeologyType.MARLSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MARLSTONE), GeologyType.MARLSTONE), 64);
    public static final RegistryObject<Block> SHALE_STONE = registerBlock(GeologyType.SHALE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SHALE), GeologyType.SHALE), 64);
    public static final RegistryObject<Block> LIMY_SHALE_STONE = registerBlock(GeologyType.LIMY_SHALE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LIMY_SHALE), GeologyType.LIMY_SHALE), 64);
    public static final RegistryObject<Block> SANDSTONE_STONE = registerBlock(GeologyType.SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SANDSTONE), GeologyType.SANDSTONE), 64);
    public static final RegistryObject<Block> RED_SANDSTONE_STONE = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RED_SANDSTONE), GeologyType.RED_SANDSTONE), 64);
    public static final RegistryObject<Block> ARKOSE_STONE = registerBlock(GeologyType.ARKOSE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ARKOSE), GeologyType.ARKOSE), 64);
    public static final RegistryObject<Block> GREYWACKE_STONE = registerBlock(GeologyType.GREYWACKE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GREYWACKE), GeologyType.GREYWACKE), 64);
    public static final RegistryObject<Block> MUDSTONE_STONE = registerBlock(GeologyType.MUDSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MUDSTONE), GeologyType.MUDSTONE), 64);
    public static final RegistryObject<Block> CLAYSTONE_STONE = registerBlock(GeologyType.CLAYSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CLAYSTONE), GeologyType.CLAYSTONE), 64);
    public static final RegistryObject<Block> SILTSTONE_STONE = registerBlock(GeologyType.SILTSTONE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SILTSTONE), GeologyType.SILTSTONE), 64);
    public static final RegistryObject<Block> CONGLOMERATE_STONE = registerBlock(GeologyType.CONGLOMERATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CONGLOMERATE), GeologyType.CONGLOMERATE), 64);
    public static final RegistryObject<Block> VEIN_QUARTZ_STONE = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.VEIN_QUARTZ), GeologyType.VEIN_QUARTZ), 64);

    // evaporites
    public static final RegistryObject<Block> ROCKSALT_STONE = registerBlock(GeologyType.ROCKSALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ROCKSALT), GeologyType.ROCKSALT), 64);
    public static final RegistryObject<Block> ROCKGYPSUM_STONE = registerBlock(GeologyType.ROCKGYPSUM.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ROCKGYPSUM), GeologyType.ROCKGYPSUM), 64);
    public static final RegistryObject<Block> BORAX_STONE = registerBlock(GeologyType.BORAX.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BORAX), GeologyType.BORAX), 64);
    public static final RegistryObject<Block> KERNITE_STONE = registerBlock(GeologyType.KERNITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.KERNITE), GeologyType.KERNITE), 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_STONE = registerBlock(GeologyType.RHYOLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RHYOLITE), GeologyType.RHYOLITE), 64);
    public static final RegistryObject<Block> DACITE_STONE = registerBlock(GeologyType.DACITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DACITE), GeologyType.DACITE), 64);
    public static final RegistryObject<Block> ANDESITE_STONE = registerBlock(GeologyType.ANDESITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ANDESITE), GeologyType.ANDESITE), 64);
    public static final RegistryObject<Block> TRACHYTE_STONE = registerBlock(GeologyType.TRACHYTE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.TRACHYTE), GeologyType.TRACHYTE), 64);
    public static final RegistryObject<Block> BASALT_STONE = registerBlock(GeologyType.BASALT.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BASALT), GeologyType.BASALT), 64);
    public static final RegistryObject<Block> PAHOEHOE_STONE = registerBlock(GeologyType.PAHOEHOE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PAHOEHOE), GeologyType.PAHOEHOE), 64);
    public static final RegistryObject<Block> SCORIA_STONE = registerBlock(GeologyType.SCORIA.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SCORIA), GeologyType.SCORIA), 64);
    public static final RegistryObject<Block> RHYOLITIC_TUFF_STONE = registerBlock(GeologyType.RHYOLITIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.RHYOLITIC_TUFF), GeologyType.RHYOLITIC_TUFF), 64);
    public static final RegistryObject<Block> TRACHYTIC_TUFF_STONE = registerBlock(GeologyType.TRACHYTIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.TRACHYTIC_TUFF), GeologyType.TRACHYTIC_TUFF), 64);
    public static final RegistryObject<Block> ANDESITIC_TUFF_STONE = registerBlock(GeologyType.ANDESITIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ANDESITIC_TUFF), GeologyType.ANDESITIC_TUFF), 64);
    public static final RegistryObject<Block> BASALTIC_TUFF_STONE = registerBlock(GeologyType.BASALTIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.BASALTIC_TUFF), GeologyType.BASALTIC_TUFF), 64);
    public static final RegistryObject<Block> ULTRAMAFIC_TUFF_STONE = registerBlock(GeologyType.ULTRAMAFIC_TUFF.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.ULTRAMAFIC_TUFF), GeologyType.ULTRAMAFIC_TUFF), 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_STONE = registerBlock(GeologyType.DIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DIORITE), GeologyType.DIORITE), 64);
    public static final RegistryObject<Block> GRANODIORITE_STONE = registerBlock(GeologyType.GRANODIORITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GRANODIORITE), GeologyType.GRANODIORITE), 64);
    public static final RegistryObject<Block> GRANITE_STONE = registerBlock(GeologyType.GRANITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GRANITE), GeologyType.GRANITE), 64);
    public static final RegistryObject<Block> SYENITE_STONE = registerBlock(GeologyType.SYENITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SYENITE), GeologyType.SYENITE), 64);
    public static final RegistryObject<Block> GABBRO_STONE = registerBlock(GeologyType.GABBRO.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GABBRO), GeologyType.GABBRO), 64);
    public static final RegistryObject<Block> DIABASE_STONE = registerBlock(GeologyType.DIABASE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.DIABASE), GeologyType.DIABASE), 64);
    public static final RegistryObject<Block> KIMBERLITE_STONE = registerBlock(GeologyType.KIMBERLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.KIMBERLITE), GeologyType.KIMBERLITE), 64);
    public static final RegistryObject<Block> LAMPROITE_STONE = registerBlock(GeologyType.LAMPROITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.LAMPROITE), GeologyType.LAMPROITE), 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_STONE = registerBlock(GeologyType.QUARTZITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.QUARTZITE), GeologyType.QUARTZITE), 64);
    public static final RegistryObject<Block> SCHIST_STONE = registerBlock(GeologyType.SCHIST.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SCHIST), GeologyType.SCHIST), 64);
    public static final RegistryObject<Block> PHYLLITE_STONE = registerBlock(GeologyType.PHYLLITE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PHYLLITE), GeologyType.PHYLLITE), 64);
    public static final RegistryObject<Block> SLATE_STONE = registerBlock(GeologyType.SLATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.SLATE), GeologyType.SLATE), 64);
    public static final RegistryObject<Block> GNEISS_STONE = registerBlock(GeologyType.GNEISS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GNEISS), GeologyType.GNEISS), 64);
    public static final RegistryObject<Block> MARBLE_STONE = registerBlock(GeologyType.MARBLE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MARBLE), GeologyType.MARBLE), 64);

    public static final RegistryObject<Block> PELITIC_HORNFELS_STONE = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.PELITIC_HORNFELS), GeologyType.PELITIC_HORNFELS), 64);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_STONE = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.CARBONATE_HORNFELS), GeologyType.CARBONATE_HORNFELS), 64);
    public static final RegistryObject<Block> MAFIC_HORNFELS_STONE = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.MAFIC_HORNFELS), GeologyType.MAFIC_HORNFELS), 64);
    public static final RegistryObject<Block> METACONGLOMERATE_STONE = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.METACONGLOMERATE), GeologyType.METACONGLOMERATE), 64);
    public static final RegistryObject<Block> GREISEN_STONE = registerBlock(GeologyType.GREISEN.getName() + "_stone", () -> new StoneOreBlock(buildStoneProperties(GeologyType.GREISEN), GeologyType.GREISEN), 64);


    /////////////
    // COBBLES //
    /////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_COBBLE = registerBlock(GeologyType.CHALK.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.CHALK)), 64);
    public static final RegistryObject<Block> LIMESTONE_COBBLE = registerBlock(GeologyType.LIMESTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.LIMESTONE)), 64);
    public static final RegistryObject<Block> DOLOSTONE_COBBLE = registerBlock(GeologyType.DOLOSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.DOLOSTONE)), 64);
    public static final RegistryObject<Block> MARLSTONE_COBBLE = registerBlock(GeologyType.MARLSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.MARLSTONE)), 64);
    public static final RegistryObject<Block> SHALE_COBBLE = registerBlock(GeologyType.SHALE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SHALE)), 64);
    public static final RegistryObject<Block> LIMY_SHALE_COBBLE = registerBlock(GeologyType.LIMY_SHALE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.LIMY_SHALE)), 64);
    public static final RegistryObject<Block> SANDSTONE_COBBLE = registerBlock(GeologyType.SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SANDSTONE)), 64);
    public static final RegistryObject<Block> RED_SANDSTONE_COBBLE = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.RED_SANDSTONE)), 64);
    public static final RegistryObject<Block> ARKOSE_COBBLE = registerBlock(GeologyType.ARKOSE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.ARKOSE)), 64);
    public static final RegistryObject<Block> GREYWACKE_COBBLE = registerBlock(GeologyType.GREYWACKE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GREYWACKE)), 64);
    public static final RegistryObject<Block> MUDSTONE_COBBLE = registerBlock(GeologyType.MUDSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.MUDSTONE)), 64);
    public static final RegistryObject<Block> CLAYSTONE_COBBLE = registerBlock(GeologyType.CLAYSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.CLAYSTONE)), 64);
    public static final RegistryObject<Block> SILTSTONE_COBBLE = registerBlock(GeologyType.SILTSTONE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SILTSTONE)), 64);
    public static final RegistryObject<Block> CONGLOMERATE_COBBLE = registerBlock(GeologyType.CONGLOMERATE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.CONGLOMERATE)), 64);
    public static final RegistryObject<Block> VEIN_QUARTZ_COBBLE = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.VEIN_QUARTZ)), 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_COBBLE = registerBlock(GeologyType.RHYOLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.RHYOLITE)), 64);
    public static final RegistryObject<Block> DACITE_COBBLE = registerBlock(GeologyType.DACITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.DACITE)), 64);
    public static final RegistryObject<Block> ANDESITE_COBBLE = registerBlock(GeologyType.ANDESITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.ANDESITE)), 64);
    public static final RegistryObject<Block> TRACHYTE_COBBLE = registerBlock(GeologyType.TRACHYTE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.TRACHYTE)), 64);
    public static final RegistryObject<Block> BASALT_COBBLE = registerBlock(GeologyType.BASALT.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.BASALT)), 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_COBBLE = registerBlock(GeologyType.DIORITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.DIORITE)), 64);
    public static final RegistryObject<Block> GRANODIORITE_COBBLE = registerBlock(GeologyType.GRANODIORITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GRANODIORITE)), 64);
    public static final RegistryObject<Block> GRANITE_COBBLE = registerBlock(GeologyType.GRANITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GRANITE)), 64);
    public static final RegistryObject<Block> SYENITE_COBBLE = registerBlock(GeologyType.SYENITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SYENITE)), 64);
    public static final RegistryObject<Block> GABBRO_COBBLE = registerBlock(GeologyType.GABBRO.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GABBRO)), 64);
    public static final RegistryObject<Block> DIABASE_COBBLE = registerBlock(GeologyType.DIABASE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.DIABASE)), 64);
    public static final RegistryObject<Block> KIMBERLITE_COBBLE = registerBlock(GeologyType.KIMBERLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.KIMBERLITE)), 64);
    public static final RegistryObject<Block> LAMPROITE_COBBLE = registerBlock(GeologyType.LAMPROITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.LAMPROITE)), 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_COBBLE = registerBlock(GeologyType.QUARTZITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.QUARTZITE)), 64);
    public static final RegistryObject<Block> SCHIST_COBBLE = registerBlock(GeologyType.SCHIST.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SCHIST)), 64);
    public static final RegistryObject<Block> PHYLLITE_COBBLE = registerBlock(GeologyType.PHYLLITE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.PHYLLITE)), 64);
    public static final RegistryObject<Block> SLATE_COBBLE = registerBlock(GeologyType.SLATE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.SLATE)), 64);
    public static final RegistryObject<Block> GNEISS_COBBLE = registerBlock(GeologyType.GNEISS.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GNEISS)), 64);
    public static final RegistryObject<Block> MARBLE_COBBLE = registerBlock(GeologyType.MARBLE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.MARBLE)), 64);
    public static final RegistryObject<Block> PELITIC_HORNFELS_COBBLE = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.PELITIC_HORNFELS)), 64);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_COBBLE = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.CARBONATE_HORNFELS)), 64);
    public static final RegistryObject<Block> MAFIC_HORNFELS_COBBLE = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.MAFIC_HORNFELS)), 64);
    public static final RegistryObject<Block> METACONGLOMERATE_COBBLE = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.METACONGLOMERATE)), 64);
    public static final RegistryObject<Block> GREISEN_COBBLE = registerBlock(GeologyType.GREISEN.getName() + "_cobblestone", () -> new Block(buildCobbleProperties(GeologyType.GREISEN)), 64);


    //////////////
    // REGOLITH //
    //////////////

    // Sedimentary
    public static final RegistryObject<Block> CHALK_REGOLITH = registerBlock(GeologyType.CHALK.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CHALK), 64);
    public static final RegistryObject<Block> LIMESTONE_REGOLITH = registerBlock(GeologyType.LIMESTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LIMESTONE), 64);
    public static final RegistryObject<Block> DOLOSTONE_REGOLITH = registerBlock(GeologyType.DOLOSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DOLOSTONE), 64);
    public static final RegistryObject<Block> MARLSTONE_REGOLITH = registerBlock(GeologyType.MARLSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MARLSTONE), 64);
    public static final RegistryObject<Block> SHALE_REGOLITH = registerBlock(GeologyType.SHALE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SHALE), 64);
    public static final RegistryObject<Block> LIMY_SHALE_REGOLITH = registerBlock(GeologyType.LIMY_SHALE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LIMY_SHALE), 64);
    public static final RegistryObject<Block> SANDSTONE_REGOLITH = registerBlock(GeologyType.SANDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SANDSTONE), 64);
    public static final RegistryObject<Block> RED_SANDSTONE_REGOLITH = registerBlock(GeologyType.RED_SANDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.RED_SANDSTONE), 64);
    public static final RegistryObject<Block> ARKOSE_REGOLITH = registerBlock(GeologyType.ARKOSE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.ARKOSE), 64);
    public static final RegistryObject<Block> GREYWACKE_REGOLITH = registerBlock(GeologyType.GREYWACKE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GREYWACKE), 64);
    public static final RegistryObject<Block> MUDSTONE_REGOLITH = registerBlock(GeologyType.MUDSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MUDSTONE), 64);
    public static final RegistryObject<Block> CLAYSTONE_REGOLITH = registerBlock(GeologyType.CLAYSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CLAYSTONE), 64);
    public static final RegistryObject<Block> SILTSTONE_REGOLITH = registerBlock(GeologyType.SILTSTONE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SILTSTONE), 64);
    public static final RegistryObject<Block> CONGLOMERATE_REGOLITH = registerBlock(GeologyType.CONGLOMERATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CONGLOMERATE), 64);
    public static final RegistryObject<Block> VEIN_QUARTZ_REGOLITH = registerBlock(GeologyType.VEIN_QUARTZ.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.VEIN_QUARTZ), 64);

    // Igneous Extrusive
    public static final RegistryObject<Block> RHYOLITE_REGOLITH = registerBlock(GeologyType.RHYOLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.RHYOLITE), 64);
    public static final RegistryObject<Block> DACITE_REGOLITH = registerBlock(GeologyType.DACITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DACITE), 64);
    public static final RegistryObject<Block> ANDESITE_REGOLITH = registerBlock(GeologyType.ANDESITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.ANDESITE), 64);
    public static final RegistryObject<Block> TRACHYTE_REGOLITH = registerBlock(GeologyType.TRACHYTE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.TRACHYTE), 64);
    public static final RegistryObject<Block> BASALT_REGOLITH = registerBlock(GeologyType.BASALT.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.BASALT), 64);
    public static final RegistryObject<Block> SCORIA_REGOLITH = registerBlock(GeologyType.SCORIA.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SCORIA), 64);

    // Igneous Intrusive
    public static final RegistryObject<Block> DIORITE_REGOLITH = registerBlock(GeologyType.DIORITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DIORITE), 64);
    public static final RegistryObject<Block> GRANODIORITE_REGOLITH = registerBlock(GeologyType.GRANODIORITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GRANODIORITE), 64);
    public static final RegistryObject<Block> GRANITE_REGOLITH = registerBlock(GeologyType.GRANITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GRANITE), 64);
    public static final RegistryObject<Block> SYENITE_REGOLITH = registerBlock(GeologyType.SYENITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SYENITE), 64);
    public static final RegistryObject<Block> GABBRO_REGOLITH = registerBlock(GeologyType.GABBRO.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GABBRO), 64);
    public static final RegistryObject<Block> DIABASE_REGOLITH = registerBlock(GeologyType.DIABASE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.DIABASE), 64);
    public static final RegistryObject<Block> KIMBERLITE_REGOLITH = registerBlock(GeologyType.KIMBERLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.KIMBERLITE), 64);
    public static final RegistryObject<Block> LAMPROITE_REGOLITH = registerBlock(GeologyType.LAMPROITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.LAMPROITE), 64);

    // Metamorphic
    public static final RegistryObject<Block> QUARTZITE_REGOLITH = registerBlock(GeologyType.QUARTZITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.QUARTZITE), 64);
    public static final RegistryObject<Block> SCHIST_REGOLITH = registerBlock(GeologyType.SCHIST.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SCHIST), 64);
    public static final RegistryObject<Block> PHYLLITE_REGOLITH = registerBlock(GeologyType.PHYLLITE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.PHYLLITE), 64);
    public static final RegistryObject<Block> SLATE_REGOLITH = registerBlock(GeologyType.SLATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.SLATE), 64);
    public static final RegistryObject<Block> GNEISS_REGOLITH = registerBlock(GeologyType.GNEISS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GNEISS), 64);
    public static final RegistryObject<Block> MARBLE_REGOLITH = registerBlock(GeologyType.MARBLE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MARBLE), 64);
    public static final RegistryObject<Block> PELITIC_HORNFELS_REGOLITH = registerBlock(GeologyType.PELITIC_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.PELITIC_HORNFELS), 64);
    public static final RegistryObject<Block> CARBONATE_HORNFELS_REGOLITH = registerBlock(GeologyType.CARBONATE_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.CARBONATE_HORNFELS), 64);
    public static final RegistryObject<Block> MAFIC_HORNFELS_REGOLITH = registerBlock(GeologyType.MAFIC_HORNFELS.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.MAFIC_HORNFELS), 64);
    public static final RegistryObject<Block> METACONGLOMERATE_REGOLITH = registerBlock(GeologyType.METACONGLOMERATE.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.METACONGLOMERATE), 64);
    public static final RegistryObject<Block> GREISEN_REGOLITH = registerBlock(GeologyType.GREISEN.getName() + "_regolith", () -> new StoneOreBlock(REGOLITH_PROP, GeologyType.GREISEN), 64);


    //////////////
    // DETRITUS //
    //////////////

    // Stable Detritus
    public static final RegistryObject<Block> DIRT_STONE = registerBlock(GeologyType.DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
            .hardnessAndResistance(0.5f), GeologyType.DIRT), 64);

    public static final RegistryObject<Block> COARSE_DIRT_STONE = registerBlock(GeologyType.COARSE_DIRT.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.EARTH).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.5f), GeologyType.COARSE_DIRT), 64);

    public static final RegistryObject<Block> CLAY_STONE = registerBlock(GeologyType.CLAY.getName() + "_stone",
            () -> new StoneOreBlock(AbstractBlock.Properties.create(Material.CLAY).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), GeologyType.CLAY), 64);

    // Falling Detritus
    public static final RegistryObject<Block> SAND_STONE = registerBlock(GeologyType.SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
            .hardnessAndResistance(0.5f), GeologyType.SAND), 64);

    public static final RegistryObject<Block> RED_SAND_STONE = registerBlock(GeologyType.RED_SAND.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)
                    .hardnessAndResistance(0.5f), GeologyType.RED_SAND), 64);

    public static final RegistryObject<Block> GRAVEL_STONE = registerBlock(GeologyType.GRAVEL.getName() + "_stone",
            () -> new FallingOreBlock(AbstractBlock.Properties.create(Material.SAND).harvestLevel(0).harvestTool(ToolType.SHOVEL).sound(SoundType.GROUND)
                    .hardnessAndResistance(0.6f), GeologyType.GRAVEL), 64);


    //////////////////
    // TOOL METHODS //
    //////////////////

    // Stone Property Builder
    private static Block.Properties buildStoneProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getStoneHardness(), geologyType.getStoneResistance());
    }

    // Cobble Property Builder
    private static Block.Properties buildCobbleProperties(GeologyType geologyType) {
        return STONE_PROP.hardnessAndResistance(geologyType.getCobbleHardness(), geologyType.getCobbleResistance());
    }

    // Block and BlockItem registry methods
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, int stackSize) {
        RegistryObject<T> registeredBlock = BLOCKS.register(name, block);
        registerBlockItem(name, registeredBlock, stackSize);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, Integer stackSize) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroups.RKU_STONE_GROUP).maxStackSize(stackSize)));
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