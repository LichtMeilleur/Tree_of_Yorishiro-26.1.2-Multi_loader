package com.licht_meilleur.tree_of_yorishiro.world;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.world.feature.YorishiroStoneFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public final class ModFeatures {

    public static final ResourceKey<Feature<?>>
            YORISHIRO_STONE_FEATURE_KEY =
            ResourceKey.create(
                    Registries.FEATURE,
                    TreeofYorishiroMod.id(
                            "yorishiro_stone_feature"
                    )
            );

    public static Feature<NoneFeatureConfiguration>
            YORISHIRO_STONE_FEATURE;

    public static void registerFabric() {
        YORISHIRO_STONE_FEATURE =
                Registry.register(
                        BuiltInRegistries.FEATURE,
                        YORISHIRO_STONE_FEATURE_KEY
                                .identifier(),
                        new YorishiroStoneFeature(
                                NoneFeatureConfiguration.CODEC
                        )
                );
    }

    public static void bindNeoForge(
            Feature<NoneFeatureConfiguration> feature
    ) {
        YORISHIRO_STONE_FEATURE =
                feature;
    }

    private ModFeatures() {
    }
}