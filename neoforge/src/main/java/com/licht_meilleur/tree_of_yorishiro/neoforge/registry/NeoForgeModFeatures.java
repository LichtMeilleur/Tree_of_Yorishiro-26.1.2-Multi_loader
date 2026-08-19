package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.world.ModFeatures;
import com.licht_meilleur.tree_of_yorishiro.world.feature.YorishiroStoneFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(
                    Registries.FEATURE,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<
            Feature<?>,
            Feature<NoneFeatureConfiguration>
            > YORISHIRO_STONE_FEATURE =
            FEATURES.register(
                    "yorishiro_stone_feature",
                    () -> new YorishiroStoneFeature(NoneFeatureConfiguration.CODEC)
            );

    public static void register(IEventBus modBus) {
        FEATURES.register(modBus);
    }

    public static void bindCommonReferences() {
        ModFeatures.bindNeoForge(YORISHIRO_STONE_FEATURE.get());
    }

    private NeoForgeModFeatures() {
    }
}