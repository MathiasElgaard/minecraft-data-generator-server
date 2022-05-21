package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(EndPortalFrameBlock.class)
public class EndPortalFrameBlockMixin {
    /**
     * @author a
     */
    @Overwrite()
    public void method_11553(BlockState blockState, World world, BlockPos blockPos, BoundingBox boundingBox, List<BoundingBox> list, @Nullable Entity entity, boolean bl) {
        list.add(EndPortalFrameBlockAccessor.portalFrame());
        list.add(EndPortalFrameBlockAccessor.eye());
    }
}
