package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.item.TreeOfYorishiroItem;
import com.licht_meilleur.tree_of_yorishiro.item.YorisyokuninSummonItem;
import com.licht_meilleur.tree_of_yorishiro.registry.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(
                    Registries.ITEM,
                    TreeofYorishiroMod.MOD_ID
            );

    /* 旧ワールドとのレジストリー互換用BlockItem。 */
    public static final DeferredHolder<Item, Item> GROWING_TREE_OF_YORISHIRO =
            blockItem("growing_tree_of_yorishiro", NeoForgeModBlocks.GROWING_TREE_OF_YORISHIRO);
    public static final DeferredHolder<Item, Item> TREE_OF_YORISHIRO_UNDER =
            blockItem("tree_of_yorishiro_under", NeoForgeModBlocks.TREE_OF_YORISHIRO_UNDER);
    public static final DeferredHolder<Item, Item> TREE_OF_YORISHIRO_MIDDLE =
            blockItem("tree_of_yorishiro_middle", NeoForgeModBlocks.TREE_OF_YORISHIRO_MIDDLE);
    public static final DeferredHolder<Item, Item> TREE_OF_YORISHIRO_TOP =
            blockItem("tree_of_yorishiro_top", NeoForgeModBlocks.TREE_OF_YORISHIRO_TOP);
    public static final DeferredHolder<Item, Item> DEBUG_TREE_OF_YORISHIRO_BLOCK =
            blockItem("debug_tree_of_yorishiro", NeoForgeModBlocks.DEBUG_TREE_OF_YORISHIRO);
    public static final DeferredHolder<Item, Item> YORISHIRO_STONE_BLOCK =
            blockItem("yorishiro_stone", NeoForgeModBlocks.YORISHIRO_STONE);
    public static final DeferredHolder<Item, Item> SYOKUNIN_DESK_BLOCK =
            blockItem("syokunin_desk", NeoForgeModBlocks.SYOKUNIN_DESK);

    public static final DeferredHolder<Item, Item> RAINBOW_SEED =
            blockItem("rainbow_seed", NeoForgeModBlocks.BUD_OF_YORISHIRO);
    public static final DeferredHolder<Item, Item> YORISHIRO_STONE =
            blockItem("yorishiro_stone_item", NeoForgeModBlocks.YORISHIRO_STONE);

    public static final DeferredHolder<Item, Item> STUDY_BOOK = basic("study_book");
    public static final DeferredHolder<Item, Item> STUDY_SET = basic("study_set");
    public static final DeferredHolder<Item, Item> HARD_STUDY_SET = basic("hard_study_set");
    public static final DeferredHolder<Item, Item> HEADBAND = basic("headband");
    public static final DeferredHolder<Item, Item> PUNCHING_SET = basic("punching_set");
    public static final DeferredHolder<Item, Item> RUNNING_SET = basic("running_set");
    public static final DeferredHolder<Item, Item> BALL = basic("ball");
    public static final DeferredHolder<Item, Item> BUBBLE_SET = basic("bubble_set");
    public static final DeferredHolder<Item, Item> GAME = basic("game");
    public static final DeferredHolder<Item, Item> GLASSES_AND_PEN = basic("glasses_and_pen");
    public static final DeferredHolder<Item, Item> PUNCHING_MACHINE = basic("punching_machine");
    public static final DeferredHolder<Item, Item> RUNNING_MACHINE = basic("running_machine");
    public static final DeferredHolder<Item, Item> STUDY_DESK = basic("study_desk");

    public static final DeferredHolder<Item, Item> YORISYOKUNIN_SUMMON =
            ITEMS.register(
                    "yorisyokunin_item",
                    () -> new YorisyokuninSummonItem(
                            properties("yorisyokunin_item").stacksTo(16)
                    )
            );

    public static final DeferredHolder<Item, Item> TREE_OF_YORISHIRO_ITEM =
            ITEMS.register(
                    "tree_of_yorishiro_item",
                    () -> new TreeOfYorishiroItem(
                            properties("tree_of_yorishiro_item").stacksTo(1)
                    )
            );

    public static final DeferredHolder<Item, Item> DEBUG_TREE_OF_YORISHIRO_ITEM =
            ITEMS.register(
                    "debug_tree_of_yorishiro_item",
                    () -> new BlockItem(
                            NeoForgeModBlocks.DEBUG_TREE_OF_YORISHIRO.get(),
                            properties("debug_tree_of_yorishiro_item").stacksTo(1)
                    )
            );

    private static DeferredHolder<Item, Item> basic(String id) {
        return ITEMS.register(id, () -> new Item(properties(id)));
    }

    private static DeferredHolder<Item, Item> blockItem(
            String id,
            DeferredHolder<net.minecraft.world.level.block.Block, net.minecraft.world.level.block.Block> block
    ) {
        return ITEMS.register(
                id,
                () -> new BlockItem(block.get(), properties(id))
        );
    }

    private static Item.Properties properties(
            String id
    ) {
        return new Item.Properties()
                .setId(
                        ResourceKey.create(
                                Registries.ITEM,
                                TreeofYorishiroMod.id(id)
                        )
                );
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public static void bindCommonReferences() {
        ModItems.bindNeoForge(
                RAINBOW_SEED.get(),
                YORISHIRO_STONE.get(),
                STUDY_BOOK.get(),
                STUDY_SET.get(),
                HARD_STUDY_SET.get(),
                HEADBAND.get(),
                PUNCHING_SET.get(),
                RUNNING_SET.get(),
                BALL.get(),
                BUBBLE_SET.get(),
                GAME.get(),
                GLASSES_AND_PEN.get(),
                PUNCHING_MACHINE.get(),
                RUNNING_MACHINE.get(),
                STUDY_DESK.get(),
                YORISYOKUNIN_SUMMON.get(),
                TREE_OF_YORISHIRO_ITEM.get(),
                DEBUG_TREE_OF_YORISHIRO_ITEM.get()
        );
    }

    private NeoForgeModItems() {
    }
}