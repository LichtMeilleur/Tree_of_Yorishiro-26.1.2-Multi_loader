package com.licht_meilleur.tree_of_yorishiro.fabric.world;

import com.licht_meilleur.tree_of_yorishiro.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class FabricWorldGeneration {

    private static boolean registered;

    private FabricWorldGeneration() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;

        BiomeModifications.addFeature(
                BiomeSelectors.all(),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                ModPlacedFeatures.YORISHIRO_STONE
        );
    }
}