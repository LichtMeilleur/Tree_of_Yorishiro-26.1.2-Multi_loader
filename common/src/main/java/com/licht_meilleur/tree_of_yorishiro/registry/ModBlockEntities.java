package com.licht_meilleur.tree_of_yorishiro.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.entity.GrowingTreeOfYorishiroBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.SyokuninDeskBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.BiFunction;

public final class ModBlockEntities {

    public static final ResourceKey<BlockEntityType<?>>
            TREE_OF_YORISHIRO_KEY =
            ResourceKey.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    TreeofYorishiroMod.id(
                            "tree_of_yorishiro"
                    )
            );

    public static final ResourceKey<BlockEntityType<?>>
            GROWING_TREE_OF_YORISHIRO_KEY =
            ResourceKey.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    TreeofYorishiroMod.id(
                            "growing_tree_of_yorishiro"
                    )
            );

    public static final ResourceKey<BlockEntityType<?>>
            TREE_PART_KEY =
            ResourceKey.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    TreeofYorishiroMod.id(
                            "tree_part"
                    )
            );

    public static final ResourceKey<BlockEntityType<?>>
            SYOKUNIN_DESK_KEY =
            ResourceKey.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    TreeofYorishiroMod.id(
                            "syokunin_desk"
                    )
            );

    public static BlockEntityType<TreeOfYorishiroBlockEntity>
            TREE_OF_YORISHIRO;

    public static BlockEntityType<GrowingTreeOfYorishiroBlockEntity>
            GROWING_TREE_OF_YORISHIRO;

    public static BlockEntityType<TreeOfYorishiroPartBlockEntity>
            TREE_PART;

    public static BlockEntityType<SyokuninDeskBlockEntity>
            SYOKUNIN_DESK;

    /*
     * BlockEntityTypeの生成方法だけを
     * Fabric側から受け取る。
     *
     * common側はFabric APIに依存しない。
     */
    @FunctionalInterface
    public interface FabricRegistrar {

        <T extends BlockEntity>
        BlockEntityType<T> register(
                ResourceKey<BlockEntityType<?>> key,
                BiFunction<BlockPos, BlockState, T> factory,
                Block... validBlocks
        );
    }

    public static void registerFabric(
            FabricRegistrar registrar
    ) {
        Objects.requireNonNull(
                registrar,
                "Fabric block entity registrar"
        );

        /*
         * 二重登録防止。
         */
        if (TREE_OF_YORISHIRO != null
                || GROWING_TREE_OF_YORISHIRO != null
                || TREE_PART != null
                || SYOKUNIN_DESK != null) {
            return;
        }

        TREE_OF_YORISHIRO =
                registrar.register(
                        TREE_OF_YORISHIRO_KEY,
                        TreeOfYorishiroBlockEntity::new,
                        ModBlocks.TREE_OF_YORISHIRO_UNDER
                );

        GROWING_TREE_OF_YORISHIRO =
                registrar.register(
                        GROWING_TREE_OF_YORISHIRO_KEY,
                        GrowingTreeOfYorishiroBlockEntity::new,
                        ModBlocks.GROWING_TREE_OF_YORISHIRO
                );

        TREE_PART =
                registrar.register(
                        TREE_PART_KEY,
                        TreeOfYorishiroPartBlockEntity::new,
                        ModBlocks.TREE_OF_YORISHIRO_UNDER,
                        ModBlocks.TREE_OF_YORISHIRO_MIDDLE,
                        ModBlocks.TREE_OF_YORISHIRO_TOP
                );

        SYOKUNIN_DESK =
                registrar.register(
                        SYOKUNIN_DESK_KEY,
                        SyokuninDeskBlockEntity::new,
                        ModBlocks.SYOKUNIN_DESK
                );
    }

    public static void bindNeoForge(
            BlockEntityType<TreeOfYorishiroBlockEntity> tree,
            BlockEntityType<GrowingTreeOfYorishiroBlockEntity> growingTree,
            BlockEntityType<TreeOfYorishiroPartBlockEntity> treePart,
            BlockEntityType<SyokuninDeskBlockEntity> syokuninDesk
    ) {
        TREE_OF_YORISHIRO =
                Objects.requireNonNull(
                        tree,
                        "Tree of Yorishiro block entity type"
                );

        GROWING_TREE_OF_YORISHIRO =
                Objects.requireNonNull(
                        growingTree,
                        "Growing Tree of Yorishiro block entity type"
                );

        TREE_PART =
                Objects.requireNonNull(
                        treePart,
                        "Tree part block entity type"
                );

        SYOKUNIN_DESK =
                Objects.requireNonNull(
                        syokuninDesk,
                        "Syokunin desk block entity type"
                );
    }

    private ModBlockEntities() {
    }
}