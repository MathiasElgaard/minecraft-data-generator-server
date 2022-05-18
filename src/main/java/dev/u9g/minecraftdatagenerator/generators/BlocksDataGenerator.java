package dev.u9g.minecraftdatagenerator.generators;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.u9g.minecraftdatagenerator.Main;
import dev.u9g.minecraftdatagenerator.mixin.MiningToolItemAccessor;
import dev.u9g.minecraftdatagenerator.util.DGU;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BlocksDataGenerator implements IDataGenerator {

    private static final Logger logger = Main.LOGGER;

    private static List<Item> getItemsEffectiveForBlock(Block block) {
        return Registry.ITEM.stream()
                .filter(item -> item instanceof ToolItem)
                .filter(item -> ((MiningToolItemAccessor)item).getEffectiveBlocks().contains(block))
                .collect(Collectors.toList());
    }

    private static List<ItemStack> populateDropsIfPossible(BlockState blockState, Item firstToolItem) {
        //If we have local world context, we can actually evaluate loot tables and determine actual data
        ServerWorld serverWorld = (ServerWorld) DGU.getWorld();
        var lootContext = new LootContext.Builder(serverWorld)
                .put(LootContextParameters.POSITION, BlockPos.ORIGIN)
                .put(LootContextParameters.TOOL, DGU.stackFor(firstToolItem));
        blockState.getDroppedStacks(lootContext);
        return blockState.getDroppedStacks(lootContext);
    }

    private static String getPropertyTypeName(Property<?> property) {
        //Explicitly handle default minecraft properties
        if (property instanceof BooleanProperty) {
            return "bool";
        }
        if (property instanceof IntegerProperty) {
            return "int";
        }
        if (property instanceof EnumProperty) {
            return "enum";
        }

        //Use simple class name as fallback, this code will give something like
        //example_type for ExampleTypeProperty class name
        String rawPropertyName = property.getClass().getSimpleName().replace("Property", "");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, rawPropertyName);
    }

    private static <T extends Comparable<T>> JsonObject generateStateProperty(Property<T> property) {
        JsonObject propertyObject = new JsonObject();
        Collection<T> propertyValues = property.getValues();

        propertyObject.addProperty("name", property.getName());
        propertyObject.addProperty("type", getPropertyTypeName(property));
        propertyObject.addProperty("num_values", propertyValues.size());

        //Do not add values for vanilla boolean properties, they are known by default
        if (!(property instanceof BooleanProperty)) {
            JsonArray propertyValuesArray = new JsonArray();
            for (T propertyValue : propertyValues) {
                propertyValuesArray.add(property.getValueAsString(propertyValue));
            }
            propertyObject.add("values", propertyValuesArray);
        }
        return propertyObject;
    }

    @Override
    public String getDataName() {
        return "blocks";
    }

    @Override
    public JsonArray generateDataJson() {
        JsonArray resultBlocksArray = new JsonArray();
        Registry<Block> blockRegistry = Registry.BLOCK;

        blockRegistry.forEach(block -> resultBlocksArray.add(generateBlock(blockRegistry, block)));
        return resultBlocksArray;
    }

    public static JsonObject generateBlock(Registry<Block> blockRegistry, Block block) {
        JsonObject blockDesc = new JsonObject();

        List<BlockState> blockStates = block.getStateFactory().getStates();
        BlockState defaultState = block.getDefaultState();
        Identifier registryKey = blockRegistry.getId(block);
        String localizationKey = block.getTranslationKey();
        List<Item> effectiveTools = getItemsEffectiveForBlock(block);

        blockDesc.addProperty("id", blockRegistry.getRawId(block));
        blockDesc.addProperty("name", Objects.requireNonNull(registryKey).getPath());
        blockDesc.addProperty("displayName", DGU.translateText(localizationKey));

        float hardness = block.getDefaultState().getHardness(null, null);

        blockDesc.addProperty("hardness", hardness);
        blockDesc.addProperty("resistance", block.getBlastResistance());
        blockDesc.addProperty("stackSize", block.asItem().getMaxAmount());
        blockDesc.addProperty("diggable", hardness != -1.0f && !(block instanceof AirBlock));
        JsonObject effTools = new JsonObject();
        effectiveTools.forEach(item -> effTools.addProperty(
            String.valueOf(Registry.ITEM.getRawId(item)), // key
            item.getBlockBreakingSpeed(DGU.stackFor(item), defaultState) // value
        ));
        blockDesc.add("effectiveTools", effTools);

        blockDesc.addProperty("transparent", !defaultState.isFullOpaque(EmptyBlockView.INSTANCE, BlockPos.ORIGIN));
        blockDesc.addProperty("emitLight", defaultState.getLuminance());
        blockDesc.addProperty("filterLight", block.getLightSubtracted(block.getDefaultState(), EmptyBlockView.INSTANCE, BlockPos.ORIGIN));

        blockDesc.addProperty("defaultState", Block.getRawIdFromState(defaultState));
        blockDesc.addProperty("minStateId", Block.getRawIdFromState(blockStates.get(0)));
        blockDesc.addProperty("maxStateId", Block.getRawIdFromState(blockStates.get(blockStates.size() - 1)));
        JsonArray stateProperties = new JsonArray();
        for (Property<?> property : block.getStateFactory().getProperties()) {
            stateProperties.add(generateStateProperty(property));
        }
        blockDesc.add("states", stateProperties);

        List<ItemStack> drops = populateDropsIfPossible(defaultState, effectiveTools.stream().findFirst().orElse(Items.AIR));

        JsonArray dropsArray = new JsonArray();
        drops.forEach(dropped -> dropsArray.add(Item.getRawIdByItem(dropped.getItem())));
        blockDesc.add("drops", dropsArray);

        VoxelShape blockCollisionShape = defaultState.getCollisionShape(EmptyBlockView.INSTANCE, BlockPos.ORIGIN);
        blockDesc.addProperty("boundingBox", blockCollisionShape.isEmpty() ? "empty" : "block");

        return blockDesc;
    }
}
