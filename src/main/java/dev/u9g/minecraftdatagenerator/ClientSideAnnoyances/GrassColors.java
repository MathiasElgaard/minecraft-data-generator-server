package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class GrassColors {
    private static int[] colorMap = new int[65536];

    public static void setColorMap(int[] is) {
        colorMap = is;
    }

    public static int getColor(double d, double e) {
        int j = (int)((1.0 - (e *= d)) * 255.0);
        int i = (int)((1.0 - d) * 255.0);
        int k = j << 8 | i;
        if (k > colorMap.length) {
            return -65281;
        }
        return colorMap[k];
    }

    public static int getGrassColorAt(Biome biome) {
        double d = MathHelper.clamp(biome.getTemperature(), 0.0f, 1.0f);
        double e = MathHelper.clamp(biome.getRainfall(), 0.0f, 1.0f);
        return getColor(d, e);
    }
}

