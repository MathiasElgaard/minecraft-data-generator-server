package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.world.biome.Biome;

public class SkyColor {
    public static int getSkyColor(Biome biome) {
        return biome.getSkyColor(biome.getTemperature());
    }
}
