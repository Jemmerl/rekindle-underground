package com.jemmerl.jemsgeology.init.blockinit;

import com.jemmerl.jemsgeology.data.enums.GeologyType;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class OreBlockRegistry {

    private final RegistryObject<Block> baseStone;
    private final RegistryObject<Block> cobbles;
    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Block> regolith;
    //private final RegistryObject<Block> gravel;
    //private final RegistryObject<Block> sand;
    private final ArrayList<OreBlockRegistry> oreRegistry;



    // register blocks
    // add to respective tags
    // create list of ores
    public OreBlockRegistry() {
        this.baseStone = null;
        this.cobbles = null;
        this.cobblestone = null;
        this.regolith = null;
        this.oreRegistry = null;
    }

    public static OreBlockRegistry create(GeologyType geoType) {
        OreBlockRegistry oreBlockRegistry = new OreBlockRegistry();
        return oreBlockRegistry;
    }

}
