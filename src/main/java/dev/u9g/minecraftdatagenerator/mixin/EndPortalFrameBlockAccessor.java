package dev.u9g.minecraftdatagenerator.mixin;

import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.util.math.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EndPortalFrameBlock.class)
public interface EndPortalFrameBlockAccessor {
    @Accessor("field_12655")
    public static BoundingBox portalFrame() {
        throw new IllegalStateException();
    }

    @Accessor("field_12656")
    public static BoundingBox eye() {
        throw new IllegalStateException();
    }
}
