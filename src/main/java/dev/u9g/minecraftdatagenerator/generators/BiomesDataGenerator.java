package dev.u9g.minecraftdatagenerator.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.ClientSideAnnoyances.SkyColor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

public class BiomesDataGenerator implements IDataGenerator {

    private static String guessBiomeDimensionFromCategory(Biome biome) {
        String category = "overworld";
        return switch (biome.getCategory()) {
            case NETHER -> "nether";
            case THEEND -> "end";
            default -> ;
        };
    }

    public static JsonObject generateBiomeInfo(Registry<Biome> registry, Biome biome) {
        JsonObject biomeDesc = new JsonObject();
        Identifier registryKey = registry.getId(biome);
        String localizationKey = String.format("biome.%s.%s", Objects.requireNonNull(registryKey).getNamespace(), registryKey.getPath());

        biomeDesc.addProperty("id", registry.getRawId(biome));
        biomeDesc.addProperty("name", registryKey.getPath());

        biomeDesc.addProperty("category", biome.getCategory().getName());
        biomeDesc.addProperty("temperature", biome.getTemperature());
        biomeDesc.addProperty("precipitation", biome.getPrecipitation().getName());
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
        Registry<Biome> biomeRegistry = Registry.BIOME;

        biomeRegistry.stream()
                .map(biome -> generateBiomeInfo(biomeRegistry, biome))
                .forEach(biomesArray::add);
        return biomesArray;
    }
}
