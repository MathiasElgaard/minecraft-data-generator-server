package dev.u9g.minecraftdatagenerator.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.class_4024;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public enum EmptyRenderBlockView implements WorldView {
    INSTANCE;

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    public BlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public class_4024 method_16280(BlockPos blockPos) {
        return null;
    }

    @Override
    public int method_16279() {
        return WorldView.super.method_16279();
    }

//    public FluidState getFluidState(BlockPos pos) {
//        return Fluids.EMPTY.getDefaultState();
//    }

    public int getBottomY() {
        return 0;
    }

//    public int getHeight() {
//        return 0;
//    }

//    @Override
//    public LightingProvider getLightingProvider() {
//        return null;
//    }
//
//    @Override
//    public int getColor(BlockPos pos, ColorResolver colorResolver) {
//        return colorResolver.getColor(Biomes.PLAINS, pos.getX(), pos.getY());
//    }
//
//    @Override
//    public int getLightLevel(LightType type, BlockPos pos) {
//        return type == LightType.SKY ? getMaxLightLevel() : 0;
//    }
//
//    @Override
//    public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
//        return ambientDarkness;
//    }
}
