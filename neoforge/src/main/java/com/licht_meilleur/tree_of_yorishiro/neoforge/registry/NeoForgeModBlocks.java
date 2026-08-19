package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.BudOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block.DebugTreeOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block.GrowingTreeOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block.SyokuninDeskBlock;
import com.licht_meilleur.tree_of_yorishiro.block.SyokuninDeskCollisionBlock;
import com.licht_meilleur.tree_of_yorishiro.block.TreeOfYorishiroPartBlock;
import com.licht_meilleur.tree_of_yorishiro.block.YorishiroStoneBlock;
import com.licht_meilleur.tree_of_yorishiro.block.YorishiroTrunkCollisionBlock;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(
                    Registries.BLOCK,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<Block, Block> BUD_OF_YORISHIRO =
            BLOCKS.register(
                    "bud_of_yorishiro",
                    () -> new BudOfYorishiroBlock()
            );

    public static final DeferredHolder<Block, Block> GROWING_TREE_OF_YORISHIRO =
            BLOCKS.register(
                    "growing_tree_of_yorishiro",
                    () -> new GrowingTreeOfYorishiroBlock()
            );
    public static final DeferredHolder<Block, Block> TREE_OF_YORISHIRO_UNDER =
            BLOCKS.register(
                    "tree_of_yorishiro_under",
                    () -> new TreeOfYorishiroPartBlock(
                            "tree_of_yorishiro_under",
                            TreeOfYorishiroPartBlock.Part.UNDER
                    )
            );

    public static final DeferredHolder<Block, Block> TREE_OF_YORISHIRO_MIDDLE =
            BLOCKS.register(
                    "tree_of_yorishiro_middle",
                    () -> new TreeOfYorishiroPartBlock(
                            "tree_of_yorishiro_middle",
                            TreeOfYorishiroPartBlock.Part.MIDDLE
                    )
            );

    public static final DeferredHolder<Block, Block> TREE_OF_YORISHIRO_TOP =
            BLOCKS.register(
                    "tree_of_yorishiro_top",
                    () -> new TreeOfYorishiroPartBlock(
                            "tree_of_yorishiro_top",
                            TreeOfYorishiroPartBlock.Part.TOP
                    )
            );

    public static final DeferredHolder<Block, Block> DEBUG_TREE_OF_YORISHIRO =
            BLOCKS.register(
                    "debug_tree_of_yorishiro",
                    () -> new DebugTreeOfYorishiroBlock(
                            BlockBehaviour.Properties.of()
                                    .setId(ModBlocks.DEBUG_TREE_OF_YORISHIRO_KEY)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                    )
            );

    public static final DeferredHolder<Block, Block> YORISHIRO_STONE =
            BLOCKS.register(
                    "yorishiro_stone",
                    () -> new YorishiroStoneBlock()
            );


    public static final DeferredHolder<Block, Block> YORISHIRO_TRUNK_COLLISION =
            BLOCKS.register(
                    "yorishiro_trunk_collision",
                    () -> new YorishiroTrunkCollisionBlock(
                            BlockBehaviour.Properties.of()
                                    .setId(ModBlocks.YORISHIRO_TRUNK_COLLISION_KEY)
                                    .strength(-1.0F, 3_600_000.0F)
                                    .noLootTable()
                                    .noOcclusion()
                    )
            );

    public static final DeferredHolder<Block, Block> SYOKUNIN_DESK_COLLISION =
            BLOCKS.register(
                    "syokunin_desk_collision",
                    () -> new SyokuninDeskCollisionBlock(
                            BlockBehaviour.Properties.of()
                                    .setId(ModBlocks.SYOKUNIN_DESK_COLLISION_KEY)
                                    .strength(0.1F)
                                    .noOcclusion()
                    )
            );

    public static final DeferredHolder<Block, Block> SYOKUNIN_DESK =
            BLOCKS.register(
                    "syokunin_desk",
                    () -> new SyokuninDeskBlock(
                            BlockBehaviour.Properties.of()
                                    .setId(ModBlocks.SYOKUNIN_DESK_KEY)
                                    .strength(2.0F)
                                    .sound(SoundType.WOOD)
                                    .noOcclusion()
                    )
            );

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static void bindCommonReferences() {
        ModBlocks.bindNeoForge(
                BUD_OF_YORISHIRO.get(),
                GROWING_TREE_OF_YORISHIRO.get(),
                TREE_OF_YORISHIRO_UNDER.get(),
                TREE_OF_YORISHIRO_MIDDLE.get(),
                TREE_OF_YORISHIRO_TOP.get(),
                DEBUG_TREE_OF_YORISHIRO.get(),
                YORISHIRO_STONE.get(),
                YORISHIRO_TRUNK_COLLISION.get(),
                SYOKUNIN_DESK_COLLISION.get(),
                SYOKUNIN_DESK.get()
        );
    }

    private NeoForgeModBlocks() {
    }
}