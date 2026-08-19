package com.licht_meilleur.tree_of_yorishiro.registry;

import com.licht_meilleur.tree_of_yorishiro
        .TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.item
        .TreeOfYorishiroItem;
import com.licht_meilleur.tree_of_yorishiro.item
        .YorisyokuninSummonItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class ModItems {

    /*
     * 通常アイテムのResourceKey
     */

    public static final ResourceKey<Item>
            RAINBOW_SEED_KEY =
            createKey(
                    "rainbow_seed"
            );

    public static final ResourceKey<Item>
            YORISHIRO_STONE_KEY =
            createKey(
                    "yorishiro_stone_item"
            );

    public static final ResourceKey<Item>
            STUDY_BOOK_KEY =
            createKey(
                    "study_book"
            );

    public static final ResourceKey<Item>
            STUDY_SET_KEY =
            createKey(
                    "study_set"
            );

    public static final ResourceKey<Item>
            HARD_STUDY_SET_KEY =
            createKey(
                    "hard_study_set"
            );

    public static final ResourceKey<Item>
            HEADBAND_KEY =
            createKey(
                    "headband"
            );

    public static final ResourceKey<Item>
            PUNCHING_SET_KEY =
            createKey(
                    "punching_set"
            );

    public static final ResourceKey<Item>
            RUNNING_SET_KEY =
            createKey(
                    "running_set"
            );

    public static final ResourceKey<Item>
            BALL_KEY =
            createKey(
                    "ball"
            );

    public static final ResourceKey<Item>
            BUBBLE_SET_KEY =
            createKey(
                    "bubble_set"
            );

    public static final ResourceKey<Item>
            GAME_KEY =
            createKey(
                    "game"
            );

    public static final ResourceKey<Item>
            GLASSES_AND_PEN_KEY =
            createKey(
                    "glasses_and_pen"
            );

    public static final ResourceKey<Item>
            PUNCHING_MACHINE_KEY =
            createKey(
                    "punching_machine"
            );

    public static final ResourceKey<Item>
            RUNNING_MACHINE_KEY =
            createKey(
                    "running_machine"
            );

    public static final ResourceKey<Item>
            STUDY_DESK_KEY =
            createKey(
                    "study_desk"
            );

    public static final ResourceKey<Item>
            YORISYOKUNIN_SUMMON_KEY =
            createKey(
                    "yorisyokunin_item"
            );

    public static final ResourceKey<Item>
            TREE_OF_YORISHIRO_ITEM_KEY =
            createKey(
                    "tree_of_yorishiro_item"
            );

    public static final ResourceKey<Item>
            DEBUG_TREE_OF_YORISHIRO_ITEM_KEY =
            createKey(
                    "debug_tree_of_yorishiro_item"
            );


    /*
     * 旧ModBlocksが自動登録していたBlockItem
     */

    public static final ResourceKey<Item>
            GROWING_TREE_BLOCK_ITEM_KEY =
            createKey(
                    "growing_tree_of_yorishiro"
            );

    public static final ResourceKey<Item>
            TREE_UNDER_BLOCK_ITEM_KEY =
            createKey(
                    "tree_of_yorishiro_under"
            );

    public static final ResourceKey<Item>
            TREE_MIDDLE_BLOCK_ITEM_KEY =
            createKey(
                    "tree_of_yorishiro_middle"
            );

    public static final ResourceKey<Item>
            TREE_TOP_BLOCK_ITEM_KEY =
            createKey(
                    "tree_of_yorishiro_top"
            );

    public static final ResourceKey<Item>
            DEBUG_TREE_BLOCK_ITEM_KEY =
            createKey(
                    "debug_tree_of_yorishiro"
            );

    public static final ResourceKey<Item>
            YORISHIRO_STONE_BLOCK_ITEM_KEY =
            createKey(
                    "yorishiro_stone"
            );

    public static final ResourceKey<Item>
            SYOKUNIN_DESK_BLOCK_ITEM_KEY =
            createKey(
                    "syokunin_desk"
            );


    /*
     * commonから参照するアイテム
     */

    public static Item RAINBOW_SEED;
    public static Item YORISHIRO_STONE;

    public static Item STUDY_BOOK;
    public static Item STUDY_SET;
    public static Item HARD_STUDY_SET;

    public static Item HEADBAND;
    public static Item PUNCHING_SET;
    public static Item RUNNING_SET;

    public static Item BALL;
    public static Item BUBBLE_SET;
    public static Item GAME;

    public static Item GLASSES_AND_PEN;
    public static Item PUNCHING_MACHINE;
    public static Item RUNNING_MACHINE;
    public static Item STUDY_DESK;

    public static Item YORISYOKUNIN_SUMMON;
    public static Item TREE_OF_YORISHIRO_ITEM;

    public static Item
            DEBUG_TREE_OF_YORISHIRO_ITEM;


    private static boolean fabricRegistered;


    public static void registerFabric() {
        if (fabricRegistered) {
            return;
        }

        fabricRegistered = true;

        if (ModBlocks.BUD_OF_YORISHIRO
                == null) {
            throw new IllegalStateException(
                    "ModBlocks must be registered before ModItems"
            );
        }

        /*
         * 旧ModBlocksのregister()で作られていた
         * BlockItemを先に復元します。
         */

        register(
                GROWING_TREE_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks
                                .GROWING_TREE_OF_YORISHIRO,
                        properties(
                                GROWING_TREE_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                TREE_UNDER_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks
                                .TREE_OF_YORISHIRO_UNDER,
                        properties(
                                TREE_UNDER_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                TREE_MIDDLE_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks
                                .TREE_OF_YORISHIRO_MIDDLE,
                        properties(
                                TREE_MIDDLE_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                TREE_TOP_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks
                                .TREE_OF_YORISHIRO_TOP,
                        properties(
                                TREE_TOP_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                DEBUG_TREE_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks
                                .DEBUG_TREE_OF_YORISHIRO,
                        properties(
                                DEBUG_TREE_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                YORISHIRO_STONE_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks.YORISHIRO_STONE,
                        properties(
                                YORISHIRO_STONE_BLOCK_ITEM_KEY
                        )
                )
        );

        register(
                SYOKUNIN_DESK_BLOCK_ITEM_KEY,
                new BlockItem(
                        ModBlocks.SYOKUNIN_DESK,
                        properties(
                                SYOKUNIN_DESK_BLOCK_ITEM_KEY
                        )
                )
        );


        /*
         * 実際に使用するアイテム
         */

        RAINBOW_SEED =
                register(
                        RAINBOW_SEED_KEY,
                        new BlockItem(
                                ModBlocks
                                        .BUD_OF_YORISHIRO,
                                properties(
                                        RAINBOW_SEED_KEY
                                )
                        )
                );

        YORISHIRO_STONE =
                register(
                        YORISHIRO_STONE_KEY,
                        new BlockItem(
                                ModBlocks
                                        .YORISHIRO_STONE,
                                properties(
                                        YORISHIRO_STONE_KEY
                                )
                        )
                );

        STUDY_BOOK =
                registerBasic(
                        STUDY_BOOK_KEY
                );

        STUDY_SET =
                registerBasic(
                        STUDY_SET_KEY
                );

        HARD_STUDY_SET =
                registerBasic(
                        HARD_STUDY_SET_KEY
                );

        HEADBAND =
                registerBasic(
                        HEADBAND_KEY
                );

        PUNCHING_SET =
                registerBasic(
                        PUNCHING_SET_KEY
                );

        RUNNING_SET =
                registerBasic(
                        RUNNING_SET_KEY
                );

        BALL =
                registerBasic(
                        BALL_KEY
                );

        BUBBLE_SET =
                registerBasic(
                        BUBBLE_SET_KEY
                );

        GAME =
                registerBasic(
                        GAME_KEY
                );

        GLASSES_AND_PEN =
                registerBasic(
                        GLASSES_AND_PEN_KEY
                );

        PUNCHING_MACHINE =
                registerBasic(
                        PUNCHING_MACHINE_KEY
                );

        RUNNING_MACHINE =
                registerBasic(
                        RUNNING_MACHINE_KEY
                );

        STUDY_DESK =
                registerBasic(
                        STUDY_DESK_KEY
                );

        YORISYOKUNIN_SUMMON =
                register(
                        YORISYOKUNIN_SUMMON_KEY,
                        new YorisyokuninSummonItem(
                                properties(
                                        YORISYOKUNIN_SUMMON_KEY
                                )
                                        .stacksTo(16)
                        )
                );

        TREE_OF_YORISHIRO_ITEM =
                register(
                        TREE_OF_YORISHIRO_ITEM_KEY,
                        new TreeOfYorishiroItem(
                                properties(
                                        TREE_OF_YORISHIRO_ITEM_KEY
                                )
                                        .stacksTo(1)
                        )
                );

        DEBUG_TREE_OF_YORISHIRO_ITEM =
                register(
                        DEBUG_TREE_OF_YORISHIRO_ITEM_KEY,
                        new BlockItem(
                                ModBlocks
                                        .DEBUG_TREE_OF_YORISHIRO,
                                properties(
                                        DEBUG_TREE_OF_YORISHIRO_ITEM_KEY
                                )
                                        .stacksTo(1)
                        )
                );

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric items registered"
        );
    }


    public static void bindNeoForge(
            Item rainbowSeed,
            Item yorishiroStone,
            Item studyBook,
            Item studySet,
            Item hardStudySet,
            Item headband,
            Item punchingSet,
            Item runningSet,
            Item ball,
            Item bubbleSet,
            Item game,
            Item glassesAndPen,
            Item punchingMachine,
            Item runningMachine,
            Item studyDesk,
            Item yorisyokuninSummon,
            Item treeItem,
            Item debugTreeItem
    ) {
        RAINBOW_SEED =
                rainbowSeed;

        YORISHIRO_STONE =
                yorishiroStone;

        STUDY_BOOK =
                studyBook;

        STUDY_SET =
                studySet;

        HARD_STUDY_SET =
                hardStudySet;

        HEADBAND =
                headband;

        PUNCHING_SET =
                punchingSet;

        RUNNING_SET =
                runningSet;

        BALL =
                ball;

        BUBBLE_SET =
                bubbleSet;

        GAME =
                game;

        GLASSES_AND_PEN =
                glassesAndPen;

        PUNCHING_MACHINE =
                punchingMachine;

        RUNNING_MACHINE =
                runningMachine;

        STUDY_DESK =
                studyDesk;

        YORISYOKUNIN_SUMMON =
                yorisyokuninSummon;

        TREE_OF_YORISHIRO_ITEM =
                treeItem;

        DEBUG_TREE_OF_YORISHIRO_ITEM =
                debugTreeItem;
    }


    private static Item registerBasic(
            ResourceKey<Item> key
    ) {
        return register(
                key,
                new Item(
                        properties(key)
                )
        );
    }

    private static Item register(
            ResourceKey<Item> key,
            Item item
    ) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                key.identifier(),
                item
        );
    }

    private static Item.Properties properties(
            ResourceKey<Item> key
    ) {
        return new Item.Properties()
                .setId(key);
    }

    private static ResourceKey<Item> createKey(
            String name
    ) {
        return ResourceKey.create(
                Registries.ITEM,
                TreeofYorishiroMod.id(name)
        );
    }

    private ModItems() {
    }
}