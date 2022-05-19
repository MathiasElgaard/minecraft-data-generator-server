package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.ClientSideAnnoyances.SkyColor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.EndBiome;
import net.minecraft.world.biome.NetherBiome;

import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        if (biome instanceof NetherBiome) {
            return "nether";
        } else if (biome instanceof EndBiome) {
            return "end";
        }
        return "overworld";
    }

    public static JsonObject generateBiomeInfo(SimpleRegistry<Identifier, Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getIdentifier(biome);
        String localizationKey = String.format("biome.%s.%s", Objects.requireNonNull(registryKey).getNamespace(), registryKey.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", registryKey.getPath());

        biomeDesc.addProperty("category", biome.getCategory().name().toLowerCase(Locale.ENGLISH));
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().name().toLowerCase(Locale.ENGLISH));
        biomeDesc.addProperty("depth", biome.getDepth());
        biomeDesc.addProperty("dimension", guessBiomeDimensionFromCategory(biome));
        biomeDesc.addProperty("displayName", DGU.translateText(localizationKey));
        biomeDesc.addProperty("color", SkyColor.getSkyColor(biome));
        biomeDesc.addProperty("rainfall", biome.getRainfall());

        return biomeDesc;
    }

    @Override
    public String getDataName() {
        return "biomes";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray biomesArray = new JsonArray();
        SimpleRegistry<Identifier, Biome> biomeRegistry = Biome.REGISTRY;

        for (Biome biome : biomeRegistry) {
            biomesArray.add(generateBiomeInfo(biomeRegistry, biome));
        }
        return biomesArray;
    }
}
