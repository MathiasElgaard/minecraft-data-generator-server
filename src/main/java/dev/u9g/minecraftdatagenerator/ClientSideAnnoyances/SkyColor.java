//package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;
//
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.biome.Biome;
//
//public class SkyColor {
//    public static int getSkyColor(Biome biome) {
//        return realSkyColor(biome.getTemperature());
//    }
//
//    private static int realSkyColor(float f) {
//        f /= 3.0f;
//        f = MathHelper.clamp(f, -1.0f, 1.0f);
//        return hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
//    }
//
//    private static int hsvToRgb(float f, float g, float h) {
//        float p;
//        float o;
//        float n;
//        int i = (int)(f * 6.0f) % 6;
//        float j = f * 6.0f - (float)i;
//        float k = h * (1.0f - g);
//        float l = h * (1.0f - j * g);
//        float m = h * (1.0f - (1.0f - j) * g);
//        switch (i) {
//            case 0: {
//                n = h;
//                o = m;
//                p = k;
//                break;
//            }
//            case 1: {
//                n = l;
//                o = h;
//                p = k;
//                break
//            }
//            case 2 -> {
//                n = k;
//                o = h;
//                p = m;
//            }
//            case 3 -> {
//                n = k;
//                o = l;
//                p = h;
//            }
//            case 4 -> {
//                n = m;
//                o = k;
//                p = h;
//            }
//            case 5 -> {
//                n = h;
//                o = k;
//                p = l;
//            }
//            default -> throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + f + ", " + g + ", " + h);
//        }
//        int q = MathHelper.clamp((int)(n * 255.0f), 0, 255);
//        int r = MathHelper.clamp((int)(o * 255.0f), 0, 255);
//        int s = MathHelper.clamp((int)(p * 255.0f), 0, 255);
//        return q << 16 | r << 8 | s;
//    }
//}
