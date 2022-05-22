package dev.u9g.minecraftdatagenerator.mixin;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(Enchantment.class)
public class EnchantmentAccessor {
    @Accessor("ENCHANTMENT_MAP")
    public static Map<Identifier, Enchantment> ENCHANTMENT_MAP(){throw new Error();}
}
