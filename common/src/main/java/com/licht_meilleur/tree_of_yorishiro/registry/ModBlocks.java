package com.licht_meilleur.tree_of_yorishiro.registry;

import com.licht_meilleur.tree_of_yorishiro
        .TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block
        .BudOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .DebugTreeOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .GrowingTreeOfYorishiroBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .SyokuninDeskBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .SyokuninDeskCollisionBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .TreeOfYorishiroPartBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .YorishiroStoneBlock;
import com.licht_meilleur.tree_of_yorishiro.block
        .YorishiroTrunkCollisionBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlocks {

    /*
     * ResourceKey
     */

    public static final ResourceKey<Block>
            BUD_OF_YORISHIRO_KEY =
            createKey(
                    "bud_of_yorishiro"
            );

    public static final ResourceKey<Block>
            GROWING_TREE_OF_YORISHIRO_KEY =
            createKey(
                    "growing_tree_of_yorishiro"
            );

    public static final ResourceKey<Block>
            TREE_OF_YORISHIRO_UNDER_KEY =
            createKey(
                    "tree_of_yorishiro_under"
            );

    public static final ResourceKey<Block>
            TREE_OF_YORISHIRO_MIDDLE_KEY =
            createKey(
                    "tree_of_yorishiro_middle"
            );

    public static final ResourceKey<Block>
            TREE_OF_YORISHIRO_TOP_KEY =
            createKey(
                    "tree_of_yorishiro_top"
            );

    public static final ResourceKey<Block>
            DEBUG_TREE_OF_YORISHIRO_KEY =
            createKey(
                    "debug_tree_of_yorishiro"
            );

    public static final ResourceKey<Block>
            YORISHIRO_STONE_KEY =
            createKey(
                    "yorishiro_stone"
            );

    public static final ResourceKey<Block>
            YORISHIRO_TRUNK_COLLISION_KEY =
            createKey(
                    "yorishiro_trunk_collision"
            );

    public static final ResourceKey<Block>
            SYOKUNIN_DESK_COLLISION_KEY =
            createKey(
                    "syokunin_desk_collision"
            );

    public static final ResourceKey<Block>
            SYOKUNIN_DESK_KEY =
            createKey(
                    "syokunin_desk"
            );


    /*
     * commonから参照する登録済みブロック
     */

    public static Block BUD_OF_YORISHIRO;

    public static Block
            GROWING_TREE_OF_YORISHIRO;

    public static Block
            TREE_OF_YORISHIRO_UNDER;

    public static Block
            TREE_OF_YORISHIRO_MIDDLE;

    public static Block
            TREE_OF_YORISHIRO_TOP;

    public static Block
            DEBUG_TREE_OF_YORISHIRO;

    public static Block YORISHIRO_STONE;

    public static Block
            YORISHIRO_TRUNK_COLLISION;

    public static Block
            SYOKUNIN_DESK_COLLISION;

    public static Block SYOKUNIN_DESK;


    private static boolean fabricRegistered;


    /*
     * Fabric
     *
     * Minecraft本体のRegistryだけを使用するため、
     * commonに置いてもNeoForge側でコンパイルできます。
     */

    public static void registerFabric() {
        if (fabricRegistered) {
            return;
        }

        fabricRegistered = true;

        BUD_OF_YORISHIRO =
                register(
                        BUD_OF_YORISHIRO_KEY,
                        new BudOfYorishiroBlock()
                );

        GROWING_TREE_OF_YORISHIRO =
                register(
                        GROWING_TREE_OF_YORISHIRO_KEY,
                        new GrowingTreeOfYorishiroBlock()
                );

        TREE_OF_YORISHIRO_UNDER =
                register(
                        TREE_OF_YORISHIRO_UNDER_KEY,
                        new TreeOfYorishiroPartBlock(
                                "tree_of_yorishiro_under",
                                TreeOfYorishiroPartBlock
                                        .Part.UNDER
                        )
                );

        TREE_OF_YORISHIRO_MIDDLE =
                register(
                        TREE_OF_YORISHIRO_MIDDLE_KEY,
                        new TreeOfYorishiroPartBlock(
                                "tree_of_yorishiro_middle",
                                TreeOfYorishiroPartBlock
                                        .Part.MIDDLE
                        )
                );

        TREE_OF_YORISHIRO_TOP =
                register(
                        TREE_OF_YORISHIRO_TOP_KEY,
                        new TreeOfYorishiroPartBlock(
                                "tree_of_yorishiro_top",
                                TreeOfYorishiroPartBlock
                                        .Part.TOP
                        )
                );

        DEBUG_TREE_OF_YORISHIRO =
                register(
                        DEBUG_TREE_OF_YORISHIRO_KEY,
                        new DebugTreeOfYorishiroBlock(
                                BlockBehaviour.Properties
                                        .of()
                                        .setId(
                                                DEBUG_TREE_OF_YORISHIRO_KEY
                                        )
                                        .strength(
                                                2.0F
                                        )
                                        .sound(
                                                SoundType.WOOD
                                        )
                                        .noOcclusion()
                        )
                );

        YORISHIRO_STONE =
                register(
                        YORISHIRO_STONE_KEY,
                        new YorishiroStoneBlock()
                );

        YORISHIRO_TRUNK_COLLISION =
                register(
                        YORISHIRO_TRUNK_COLLISION_KEY,
                        new YorishiroTrunkCollisionBlock(
                                BlockBehaviour.Properties
                                        .of()
                                        .setId(
                                                YORISHIRO_TRUNK_COLLISION_KEY
                                        )
                                        .strength(
                                                -1.0F,
                                                3_600_000.0F
                                        )
                                        .noLootTable()
                                        .noOcclusion()
                        )
                );

        SYOKUNIN_DESK_COLLISION =
                register(
                        SYOKUNIN_DESK_COLLISION_KEY,
                        new SyokuninDeskCollisionBlock(
                                BlockBehaviour.Properties
                                        .of()
                                        .setId(
                                                SYOKUNIN_DESK_COLLISION_KEY
                                        )
                                        .strength(
                                                0.1F
                                        )
                                        .noOcclusion()
                        )
                );

        SYOKUNIN_DESK =
                register(
                        SYOKUNIN_DESK_KEY,
                        new SyokuninDeskBlock(
                                BlockBehaviour.Properties
                                        .of()
                                        .setId(
                                                SYOKUNIN_DESK_KEY
                                        )
                                        .strength(
                                                2.0F
                                        )
                                        .sound(
                                                SoundType.WOOD
                                        )
                                        .noOcclusion()
                        )
                );

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric blocks registered"
        );
    }


    /*
     * NeoForge
     */

    public static void bindNeoForge(
            Block budOfYorishiro,
            Block growingTree,
            Block treeUnder,
            Block treeMiddle,
            Block treeTop,
            Block debugTree,
            Block yorishiroStone,
            Block trunkCollision,
            Block deskCollision,
            Block syokuninDesk
    ) {
        BUD_OF_YORISHIRO =
                budOfYorishiro;

        GROWING_TREE_OF_YORISHIRO =
                growingTree;

        TREE_OF_YORISHIRO_UNDER =
                treeUnder;

        TREE_OF_YORISHIRO_MIDDLE =
                treeMiddle;

        TREE_OF_YORISHIRO_TOP =
                treeTop;

        DEBUG_TREE_OF_YORISHIRO =
                debugTree;

        YORISHIRO_STONE =
                yorishiroStone;

        YORISHIRO_TRUNK_COLLISION =
                trunkCollision;

        SYOKUNIN_DESK_COLLISION =
                deskCollision;

        SYOKUNIN_DESK =
                syokuninDesk;
    }


    private static Block register(
            ResourceKey<Block> key,
            Block block
    ) {
        return Registry.register(
                BuiltInRegistries.BLOCK,
                key.identifier(),
                block
        );
    }

    private static ResourceKey<Block> createKey(
            String name
    ) {
        return ResourceKey.create(
                Registries.BLOCK,
                TreeofYorishiroMod.id(name)
        );
    }

    private ModBlocks() {
    }
}