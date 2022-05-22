package dev.u9g.minecraftdatagenerator.ClientSideAnnoyances;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public interface BlockColorable {
    int method_12155(BlockState blockState, @Nullable WorldView worldView, @Nullable BlockPos blockPos, int i);
}