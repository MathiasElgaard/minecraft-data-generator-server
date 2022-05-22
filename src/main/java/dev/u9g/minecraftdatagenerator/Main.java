package dev.u9g.minecraftdatagenerator;

import net.fabricmc.api.ModInitializer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Logger;

public class Main implements ModInitializer {
    public static Logger LOGGER = Logger.getLogger("mc-data-gen-serv");
    @Override
    public void onInitialize() {
        System.out.println("fdsnjadfshjfdashfhadsj");
        System.out.println(new BigDecimal("6", MathContext.UNLIMITED).multiply(new BigDecimal("1.2", MathContext.UNLIMITED)).toString());
        System.out.println("fdsnjadfshjfdashfhadsj");
        System.out.println();
    }
}
