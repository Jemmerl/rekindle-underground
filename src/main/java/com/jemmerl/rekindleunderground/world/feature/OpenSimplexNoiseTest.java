package com.jemmerl.rekindleunderground.world.feature;

import com.jemmerl.rekindleunderground.data.noise.OpenSimplex2;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OpenSimplexNoiseTest
{
    /*

    private static final int X_LENGTH = 512;
    private static final int Z_LENGTH = 2048;
    private static final int Y_HEIGHT = 256;
    private static final double STRATA_SIZE = 512;
    private static final double DIKE_SIZE = 64;

    public static void main(String[] args)
            throws IOException, InterruptedException {

        OpenSimplex2 noise = new OpenSimplex2();
        BufferedImage image = new BufferedImage(X_LENGTH, Y_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        for (int z = Z_LENGTH; z > (-1*Z_LENGTH); z=z-(Z_LENGTH / 4)) {
            strataGen(noise, image, z);
            dikeGen1(noise, image, z); // Multiple dike gens to encourage overlap
            ImageIO.write(image, "png", new File("noise.png"));
            TimeUnit.SECONDS.sleep(7);
        }
    }



    public static void strataGen(OpenSimplex2 noise, BufferedImage image, int z) {
        for (int y = 0; y < Y_HEIGHT; y++)
        {
            for (int x = 0; x < X_LENGTH; x++)
            {
                double value = noise.noise3_ImproveXZ(24691, (x / (STRATA_SIZE*2)), (z / (STRATA_SIZE*2)), (y / (STRATA_SIZE*0.4)));

                int rgb = 0x000000;
                if (value <= -0.8) {
                    rgb = Color.red.getRGB();
                } else if (value <= -0.6) {
                    rgb = Color.orange.getRGB();
                } else if (value <= -0.4) {
                    rgb = Color.yellow.getRGB();
                } else if (value <= -0.2) {
                    rgb = Color.green.getRGB();
                } else if (value <= 0.0) {
                    rgb = Color.blue.getRGB();
                } else if (value <= 0.2) {
                    rgb = Color.magenta.getRGB();
                } else if (value <= 0.4) {
                    rgb = Color.pink.getRGB();
                } else if (value <= 0.6) {
                    rgb = Color.lightGray.getRGB();
                } else if (value <= 0.8) {
                    rgb = Color.gray.getRGB();
                } else {
                    rgb = Color.darkGray.getRGB();
                }

                if (((x % 16) == 0) || ((y > 60) && (y < 65))){
                    image.setRGB(x, y, 0x010101);
                } else {
                    image.setRGB(x, y, rgb);
                }
            }
        }
    }

    public static void dikeGen1(OpenSimplex2 noise, BufferedImage image, int z){
        for (int y = 0; y < Y_HEIGHT; y++)
        {
            for (int x = 0; x < X_LENGTH; x++)
            {
                double value = noise.noise3_ImproveXZ(666, (x / (DIKE_SIZE)), (z / (DIKE_SIZE)), (y / (DIKE_SIZE*8)));

                if (value <= -0.8) {
                    image.setRGB(x, y, Color.white.getRGB());
                } else if (value >= 0.8) {
                    image.setRGB(x, y, Color.black.getRGB());
                }
            }
        }
    }

     */
}
