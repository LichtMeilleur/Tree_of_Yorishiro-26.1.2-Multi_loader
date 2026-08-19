package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.registry.ModEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(
                    Registries.ENTITY_TYPE,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<
            EntityType<?>,
            EntityType<ChibishiroEntity>
            > CHIBISHIRO =
            ENTITY_TYPES.register(
                    "chibishiro",
                    () -> EntityType.Builder.of(
                                    ChibishiroEntity::new,
                                    MobCategory.CREATURE
                            )
                            .sized(0.6F, 0.9F)
                            .clientTrackingRange(80)
                            .updateInterval(3)
                            .build(ModEntities.CHIBISHIRO_KEY)
            );

    public static final DeferredHolder<
            EntityType<?>,
            EntityType<YorisyokuninEntity>
            > YORISYOKUNIN =
            ENTITY_TYPES.register(
                    "yorisyokunin",
                    () -> EntityType.Builder.of(
                                    YorisyokuninEntity::new,
                                    MobCategory.MISC
                            )
                            .sized(0.6F, 1.6F)
                            .clientTrackingRange(64)
                            .updateInterval(2)
                            .build(ModEntities.YORISYOKUNIN_KEY)
            );

    public static void register(IEventBus modBus) {
        ENTITY_TYPES.register(modBus);
    }

    public static void bindCommonReferences() {
        ModEntities.bindNeoForge(
                CHIBISHIRO.get(),
                YORISYOKUNIN.get()
        );
    }

    private NeoForgeModEntities() {
    }
}