package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class SkyColor {
    private static int realSkyColor(float f) {
        f /= 3.0f;
        f = MathHelper.clamp(f, -1.0f, 1.0f);
        return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
    }
    public static int getSkyColor(Biome biome) {
        return realSkyColor(biome.getTemperature());
    }
}
