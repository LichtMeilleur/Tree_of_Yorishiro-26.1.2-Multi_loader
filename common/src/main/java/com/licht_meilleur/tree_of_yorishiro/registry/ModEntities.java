package com.licht_meilleur.tree_of_yorishiro.registry;

import com.licht_meilleur.tree_of_yorishiro
        .TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.entity
        .ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity
        .YorisyokuninEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntities {

    public static final ResourceKey<
            EntityType<?>
            > CHIBISHIRO_KEY =
            createKey(
                    "chibishiro"
            );

    public static final ResourceKey<
            EntityType<?>
            > YORISYOKUNIN_KEY =
            createKey(
                    "yorisyokunin"
            );


    public static EntityType<
            ChibishiroEntity
            > CHIBISHIRO;

    public static EntityType<
            YorisyokuninEntity
            > YORISYOKUNIN;


    private static boolean fabricRegistered;


    public static void registerFabric() {
        if (fabricRegistered) {
            return;
        }

        fabricRegistered = true;

        CHIBISHIRO =
                Registry.register(
                        BuiltInRegistries.ENTITY_TYPE,
                        CHIBISHIRO_KEY.identifier(),
                        EntityType.Builder.of(
                                        ChibishiroEntity::new,
                                        MobCategory.CREATURE
                                )
                                .sized(
                                        0.6F,
                                        0.9F
                                )
                                .clientTrackingRange(
                                        80
                                )
                                .updateInterval(
                                        3
                                )
                                .build(
                                        CHIBISHIRO_KEY
                                )
                );

        YORISYOKUNIN =
                Registry.register(
                        BuiltInRegistries.ENTITY_TYPE,
                        YORISYOKUNIN_KEY.identifier(),
                        EntityType.Builder.of(
                                        YorisyokuninEntity::new,
                                        MobCategory.MISC
                                )
                                .sized(
                                        0.6F,
                                        1.6F
                                )
                                .clientTrackingRange(
                                        64
                                )
                                .updateInterval(
                                        2
                                )
                                .build(
                                        YORISYOKUNIN_KEY
                                )
                );

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric entities registered"
        );
    }


    public static void bindNeoForge(
            EntityType<ChibishiroEntity>
                    chibishiro,
            EntityType<YorisyokuninEntity>
                    yorisyokunin
    ) {
        CHIBISHIRO =
                chibishiro;

        YORISYOKUNIN =
                yorisyokunin;
    }


    private static ResourceKey<
            EntityType<?>
            > createKey(
            String name
    ) {
        return ResourceKey.create(
                Registries.ENTITY_TYPE,
                TreeofYorishiroMod.id(name)
        );
    }

    private ModEntities() {
    }
}