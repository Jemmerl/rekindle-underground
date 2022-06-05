package com.jemmerl.rekindleunderground.world.feature.stonegenutil;

import com.google.gson.reflect.TypeToken;
import com.jemmerl.rekindleunderground.RekindleUnderground;
import com.jemmerl.rekindleunderground.util.UtilMethods;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.datatemplates.PresetData;
import com.jemmerl.rekindleunderground.world.feature.stonegenutil.datatemplates.SetData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map;

public class BlockPicker {



//    // Shale, Mudstone //TODO add siltstone???

    private static final Logger LOGGER = LogManager.getLogger();

    private final JsonObject presetJsonObj;
    private final LinkedHashMap<String, ArrayList<BlockState>> presetMap;

    public BlockPicker() {
        this.presetJsonObj = getPresetJsonObj();
        this.presetMap = getPresetMap();
    }

    // Picks a block to use for a layer given the options in form of a list of blockstates
    public static BlockState selectBlock(List<BlockState> stateList, float regionNoise, float strataVal) {
        Random rand = new Random((int)(regionNoise * 10000));
        int size = stateList.size();
        int index = rand.nextInt(size) + (int)UtilMethods.remap(strataVal, new float[]{-1f, 1f}, new float[]{0, stateList.size()});
        return stateList.get(((index = (index % size)) < 0) ? (index + size) : index);
    }

    // Get a random preset from the data-defined presets
    // The shift allows for more independant-looking layers in the same region
    public String getRandomPresetName(float regionNoise, int shift) {
        ArrayList<String> keySet = new ArrayList<>(new HashSet<>(this.presetMap.keySet()));
        Collections.shuffle(keySet, new Random((int)(regionNoise * 8705) + shift));
        return keySet.get(0);
    }

    // TODO for use in layer block selection??
    public int getPresetSize(String presetKey) {
        return this.presetMap.get(presetKey).size();
    }

    // Combines given blocks (resource locations of as a string) into one list of blockstates
    // Used for hard-coded block selections
    public static List<BlockState> buildStateList(List<String> blocks) {
        List<BlockState> stateList = new ArrayList<>();
        for (String name : blocks) {
            stateList.add(stringToBlockState(name));
        }
        return stateList;
    }

    // Allows the layer generator to get a full preset list with no removed blocks
    // Used with data-generated block selections
    public List<BlockState> getBlockStateList(String presetKey) {
        List<BlockState> stateList = new ArrayList<>(this.presetMap.get(presetKey));
        return stateList;
    }

    // Allows the layer generator to pick how many blocks will be used from a preset
    // Used with data-generated block selections
    public List<BlockState> getBlockStateList(String presetKey, int numPick) {
        List<BlockState> stateList = new ArrayList<>(this.presetMap.get(presetKey));

        for (int i = (stateList.size() - numPick); i > 0; i--) {
            stateList.remove(0);
        }
        return stateList;
    }

    // TODO save info to the level or world file, so changes to data file do not impact the world
    private static JsonObject getPresetJsonObj() {
        Gson gson = new Gson();
        JsonObject presetJsonObj;
        InputStream inputStream;
        String result;
        ResourceLocation resourceLocation = new ResourceLocation(RekindleUnderground.MOD_ID, "generation/blockpresets.json");

        // Attempt to load editable preset json file in resources folder
        try {
            System.out.println("Attempting to read file: rekindleunderground:generation/blockpresets.json");
            inputStream = RekindleUnderground.class.getResourceAsStream("/data/rekindleunderground/generation/blockpresets.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            JsonElement je = gson.fromJson(reader, JsonElement.class);
            presetJsonObj = je.getAsJsonObject();
            reader.close();

            return presetJsonObj;
        }

        catch (IOException ioException) {
            LOGGER.warn("Error reading data file at " + resourceLocation, ioException);
        }
        catch (IllegalArgumentException | JsonParseException jsonParseException) {
            LOGGER.warn("Couldn't parse data file at " + resourceLocation, jsonParseException);
        }

        // TEMP TO TEST PARSING
        result = "{\"block_presets\":[{\"name\":\"default\",\"sets\":[\"sed_soil\"],\"individuals\":[\"minecraft:iron_block\"]}],\"set_list\":[{\"name\":\"sed_soil\",\"blocks\":[\"rekindleunderground:shale_stone\",\"rekindleunderground:mudstone_stone\"]}]}";
        presetJsonObj  = new JsonParser().parse(result).getAsJsonObject();
        return presetJsonObj;


        // Above is needed


        // If the above somehow fails, the mod should just crash with error...
        // but to make the compiler quit complaining,
        // this returns a bare-bones json object that is valid for this mod.
        // If your world *somehow* generates with this, something is really wrong
        // you will likely need to contact whoever is currently dev-ing your build
//        result = "{\"block_presets\":[{\"name\":\"default\",\"sets\":[\"sed_soil\"],\"individuals\":[\"minecraft:iron_block\"]}],\"set_list\":[{\"sed_soil\":[\"rekindleunderground:shale_stone\",\"rekindleunderground:mudstone_stone\"]}]}";
//        presetJsonObj  = new JsonParser().parse(result).getAsJsonObject();
//        return presetJsonObj;
    }

    // Build the preset map for strata using the block presets json
    private LinkedHashMap<String, ArrayList<BlockState>> getPresetMap() {
        Gson gson = new Gson();

        // Build map of available sets for referencing
        JsonArray setListArr = this.presetJsonObj.getAsJsonArray("set_list");
        SetData[] setDataList = gson.fromJson(setListArr, SetData[].class);
        Map<String, ArrayList<BlockState>> setListMap = new HashMap<>();
        for (SetData data : setDataList) {
            ArrayList<BlockState> blockStateArrayList = new ArrayList<>();
            for (int i = 0; i < data.getBlocks().length; i++) {
                blockStateArrayList.add(stringToBlockState(data.getBlocks()[i]));
            }
            setListMap.putIfAbsent(data.getName(), blockStateArrayList);
        }

        // Build map of available presets from given sets and individual blocks
        JsonArray presetsArr = this.presetJsonObj.getAsJsonArray("block_presets");
        PresetData[] presetDataList = gson.fromJson(presetsArr, PresetData[].class);
        LinkedHashMap<String, ArrayList<BlockState>> builtPresetMap = new LinkedHashMap<>();
        for (PresetData data : presetDataList) {
            String key = data.getName();
            ArrayList<BlockState> blockStateArrayList = new ArrayList<>(); // List of blocks in preset

            // Add all blocks from specified sets
            String[] sets = data.getSets(); // List of specified sets in preset
            for (int j = 0; j < sets.length; j++) {
                blockStateArrayList.addAll(setListMap.get(sets[j]));
            }
            // Add all specified individual blocks
            String[] individuals = data.getIndividuals(); // List of specified individual blocks in preset
            for (int j = 0; j < individuals.length; j++) {
                blockStateArrayList.add(stringToBlockState(individuals[j]));
            }

            builtPresetMap.putIfAbsent(key, blockStateArrayList);
        }

        return builtPresetMap;
    }


    // Returns a block given a string representation of its resource location
    private static BlockState stringToBlockState(String blockName) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName))).getDefaultState();
    }


}
