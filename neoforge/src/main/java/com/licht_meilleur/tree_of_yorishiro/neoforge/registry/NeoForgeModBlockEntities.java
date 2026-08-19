package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.entity.GrowingTreeOfYorishiroBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.SyokuninDeskBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlockEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<TreeOfYorishiroBlockEntity>
            > TREE_OF_YORISHIRO =
            BLOCK_ENTITY_TYPES.register(
                    "tree_of_yorishiro",
                    () -> new BlockEntityType<>(
                            TreeOfYorishiroBlockEntity::new,
                            NeoForgeModBlocks.TREE_OF_YORISHIRO_UNDER.get()
                    )
            );

    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<GrowingTreeOfYorishiroBlockEntity>
            > GROWING_TREE_OF_YORISHIRO =
            BLOCK_ENTITY_TYPES.register(
                    "growing_tree_of_yorishiro",
                    () -> new BlockEntityType<>(
                            GrowingTreeOfYorishiroBlockEntity::new,
                            NeoForgeModBlocks.GROWING_TREE_OF_YORISHIRO.get()
                    )
            );

    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<TreeOfYorishiroPartBlockEntity>
            > TREE_PART =
            BLOCK_ENTITY_TYPES.register(
                    "tree_part",
                    () -> new BlockEntityType<>(
                            TreeOfYorishiroPartBlockEntity::new,
                            NeoForgeModBlocks.TREE_OF_YORISHIRO_UNDER.get(),
                            NeoForgeModBlocks.TREE_OF_YORISHIRO_MIDDLE.get(),
                            NeoForgeModBlocks.TREE_OF_YORISHIRO_TOP.get()
                    )
            );

    public static final DeferredHolder<
            BlockEntityType<?>,
            BlockEntityType<SyokuninDeskBlockEntity>
            > SYOKUNIN_DESK =
            BLOCK_ENTITY_TYPES.register(
                    "syokunin_desk",
                    () -> new BlockEntityType<>(
                            SyokuninDeskBlockEntity::new,
                            NeoForgeModBlocks.SYOKUNIN_DESK.get()
                    )
            );

    public static void register(IEventBus modBus) {
        BLOCK_ENTITY_TYPES.register(modBus);
    }

    public static void bindCommonReferences() {
        ModBlockEntities.bindNeoForge(
                TREE_OF_YORISHIRO.get(),
                GROWING_TREE_OF_YORISHIRO.get(),
                TREE_PART.get(),
                SYOKUNIN_DESK.get()
        );
    }

    private NeoForgeModBlockEntities() {
    }
}