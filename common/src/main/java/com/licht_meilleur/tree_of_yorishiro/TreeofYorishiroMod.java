package com.licht_meilleur.tree_of_yorishiro;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TreeofYorishiroMod {

    public static final String MOD_ID =
            "tree_of_yorishiro";

    public static final Logger LOGGER =
            LoggerFactory.getLogger(
                    MOD_ID
            );

    public static Identifier id(
            String path
    ) {
        return Identifier.fromNamespaceAndPath(
                MOD_ID,
                path
        );
    }

    private TreeofYorishiroMod() {
    }
}