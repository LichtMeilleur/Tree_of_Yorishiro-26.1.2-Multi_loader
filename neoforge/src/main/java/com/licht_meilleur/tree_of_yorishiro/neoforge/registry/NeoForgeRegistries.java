package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.*;
import com.licht_meilleur.tree_of_yorishiro.block.entity.*;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.item.TreeOfYorishiroItem;
import com.licht_meilleur.tree_of_yorishiro.item.YorisyokuninSummonItem;
import com.licht_meilleur.tree_of_yorishiro.registry.*;
import com.licht_meilleur.tree_of_yorishiro.screen.*;
import com.licht_meilleur.tree_of_yorishiro.world.ModFeatures;
import com.licht_meilleur.tree_of_yorishiro.world.feature.YorishiroStoneFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeRegistries {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TreeofYorishiroMod.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, TreeofYorishiroMod.MOD_ID);

    public static final DeferredHolder<Block, Block> BUD = BLOCKS.register("bud_of_yorishiro", BudOfYorishiroBlock::new);
    public static final DeferredHolder<Block, Block> GROWING = BLOCKS.register("growing_tree_of_yorishiro", GrowingTreeOfYorishiroBlock::new);
    public static final DeferredHolder<Block, Block> UNDER = BLOCKS.register("tree_of_yorishiro_under", () -> new TreeOfYorishiroPartBlock("tree_of_yorishiro_under", TreeOfYorishiroPartBlock.Part.UNDER));
    public static final DeferredHolder<Block, Block> MIDDLE = BLOCKS.register("tree_of_yorishiro_middle", () -> new TreeOfYorishiroPartBlock("tree_of_yorishiro_middle", TreeOfYorishiroPartBlock.Part.MIDDLE));
    public static final DeferredHolder<Block, Block> TOP = BLOCKS.register("tree_of_yorishiro_top", () -> new TreeOfYorishiroPartBlock("tree_of_yorishiro_top", TreeOfYorishiroPartBlock.Part.TOP));
    public static final DeferredHolder<Block, Block> DEBUG = BLOCKS.register("debug_tree_of_yorishiro", () -> new DebugTreeOfYorishiroBlock(BlockBehaviour.Properties.of().setId(ModBlocks.DEBUG_TREE_OF_YORISHIRO_KEY).strength(2).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredHolder<Block, Block> STONE = BLOCKS.register("yorishiro_stone", YorishiroStoneBlock::new);
    public static final DeferredHolder<Block, Block> TRUNK_COLLISION = BLOCKS.register("yorishiro_trunk_collision", () -> new YorishiroTrunkCollisionBlock(BlockBehaviour.Properties.of().setId(ModBlocks.YORISHIRO_TRUNK_COLLISION_KEY).strength(-1, 3600000).noLootTable().noOcclusion()));
    public static final DeferredHolder<Block, Block> DESK_COLLISION = BLOCKS.register("syokunin_desk_collision", () -> new SyokuninDeskCollisionBlock(BlockBehaviour.Properties.of().setId(ModBlocks.SYOKUNIN_DESK_COLLISION_KEY).strength(0.1F).noOcclusion()));
    public static final DeferredHolder<Block, Block> DESK = BLOCKS.register("syokunin_desk", () -> new SyokuninDeskBlock(BlockBehaviour.Properties.of().setId(ModBlocks.SYOKUNIN_DESK_KEY).strength(2).sound(SoundType.WOOD).noOcclusion()));

    private static DeferredHolder<Item, Item> basic(String id) { return ITEMS.register(id, () -> new Item(props(id))); }
    private static DeferredHolder<Item, Item> blockItem(String id, DeferredHolder<Block, Block> block) { return ITEMS.register(id, () -> new BlockItem(block.get(), props(id))); }
    private static Item.Properties props(String id) { return new Item.Properties().setId(ModItems.key(id)); }

    // Block items that existed in the original Fabric registration.
    public static final DeferredHolder<Item, Item> GROWING_ITEM = blockItem("growing_tree_of_yorishiro", GROWING);
    public static final DeferredHolder<Item, Item> UNDER_ITEM = blockItem("tree_of_yorishiro_under", UNDER);
    public static final DeferredHolder<Item, Item> MIDDLE_ITEM = blockItem("tree_of_yorishiro_middle", MIDDLE);
    public static final DeferredHolder<Item, Item> TOP_ITEM = blockItem("tree_of_yorishiro_top", TOP);
    public static final DeferredHolder<Item, Item> DEBUG_BLOCK_ITEM = blockItem("debug_tree_of_yorishiro", DEBUG);
    public static final DeferredHolder<Item, Item> STONE_BLOCK_ITEM = blockItem("yorishiro_stone", STONE);
    public static final DeferredHolder<Item, Item> DESK_ITEM = blockItem("syokunin_desk", DESK);

    public static final DeferredHolder<Item, Item> RAINBOW_SEED = blockItem("rainbow_seed", BUD);
    public static final DeferredHolder<Item, Item> YORISHIRO_STONE_ITEM = blockItem("yorishiro_stone_item", STONE);
    public static final DeferredHolder<Item, Item> STUDY_BOOK = basic("study_book"), STUDY_SET = basic("study_set"), HARD_STUDY_SET = basic("hard_study_set");
    public static final DeferredHolder<Item, Item> HEADBAND = basic("headband"), PUNCHING_SET = basic("punching_set"), RUNNING_SET = basic("running_set");
    public static final DeferredHolder<Item, Item> BALL = basic("ball"), BUBBLE_SET = basic("bubble_set"), GAME = basic("game");
    public static final DeferredHolder<Item, Item> GLASSES_AND_PEN = basic("glasses_and_pen"), PUNCHING_MACHINE = basic("punching_machine"), RUNNING_MACHINE = basic("running_machine"), STUDY_DESK = basic("study_desk");
    public static final DeferredHolder<Item, Item> YORISYOKUNIN_SUMMON = ITEMS.register("yorisyokunin_item", () -> new YorisyokuninSummonItem(props("yorisyokunin_item").stacksTo(16)));
    public static final DeferredHolder<Item, Item> TREE_ITEM = ITEMS.register("tree_of_yorishiro_item", () -> new TreeOfYorishiroItem(props("tree_of_yorishiro_item").stacksTo(1)));
    public static final DeferredHolder<Item, Item> DEBUG_ITEM = ITEMS.register("debug_tree_of_yorishiro_item", () -> new BlockItem(DEBUG.get(), props("debug_tree_of_yorishiro_item").stacksTo(1)));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreeOfYorishiroBlockEntity>> TREE_BE = BLOCK_ENTITIES.register("tree_of_yorishiro", () -> new BlockEntityType<>(TreeOfYorishiroBlockEntity::new, UNDER.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowingTreeOfYorishiroBlockEntity>> GROWING_BE = BLOCK_ENTITIES.register("growing_tree_of_yorishiro", () -> new BlockEntityType<>(GrowingTreeOfYorishiroBlockEntity::new, GROWING.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TreeOfYorishiroPartBlockEntity>> PART_BE = BLOCK_ENTITIES.register("tree_part", () -> new BlockEntityType<>(TreeOfYorishiroPartBlockEntity::new, UNDER.get(), MIDDLE.get(), TOP.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SyokuninDeskBlockEntity>> DESK_BE = BLOCK_ENTITIES.register("syokunin_desk", () -> new BlockEntityType<>(SyokuninDeskBlockEntity::new, DESK.get()));

    public static final DeferredHolder<EntityType<?>, EntityType<ChibishiroEntity>> CHIBI = ENTITIES.register("chibishiro", () -> EntityType.Builder.of(ChibishiroEntity::new, MobCategory.CREATURE).sized(0.6F, 0.9F).clientTrackingRange(80).updateInterval(3).build(ModEntities.CHIBISHIRO_KEY));
    public static final DeferredHolder<EntityType<?>, EntityType<YorisyokuninEntity>> ARTISAN = ENTITIES.register("yorisyokunin", () -> EntityType.Builder.of(YorisyokuninEntity::new, MobCategory.MISC).sized(0.6F, 1.6F).clientTrackingRange(64).updateInterval(2).build(ModEntities.YORISYOKUNIN_KEY));

    public static final DeferredHolder<MenuType<?>, MenuType<TreeOfYorishiroScreenHandler>> TREE_MENU = MENUS.register("tree_of_yorishiro", () -> IMenuTypeExtension.create((id, inv, buf) -> ModScreenHandlers.createTree(id, inv, TreeOfYorishiroMenuData.STREAM_CODEC.decode(buf))));
    public static final DeferredHolder<MenuType<?>, MenuType<YorisyokuninTradeScreenHandler>> TRADE_MENU = MENUS.register("yorisyokunin_trade", () -> IMenuTypeExtension.create((id, inv, buf) -> ModScreenHandlers.createTrade(id, inv, YorisyokuninTradeMenuData.STREAM_CODEC.decode(buf))));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("tree_of_yorishiro_group", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("itemGroup.tree_of_yorishiro.group")).icon(() -> new ItemStack(YORISHIRO_STONE_ITEM.get())).displayItems((p, out) -> {
        out.accept(YORISHIRO_STONE_ITEM.get()); out.accept(RAINBOW_SEED.get()); out.accept(TREE_ITEM.get()); out.accept(YORISYOKUNIN_SUMMON.get());
        out.accept(STUDY_BOOK.get()); out.accept(STUDY_SET.get()); out.accept(HARD_STUDY_SET.get());
        out.accept(HEADBAND.get()); out.accept(PUNCHING_SET.get()); out.accept(RUNNING_SET.get());
        out.accept(BALL.get()); out.accept(BUBBLE_SET.get()); out.accept(GAME.get());
        out.accept(GLASSES_AND_PEN.get()); out.accept(PUNCHING_MACHINE.get()); out.accept(RUNNING_MACHINE.get());
        out.accept(STUDY_DESK.get()); out.accept(DEBUG_ITEM.get());
    }).build());
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> STONE_FEATURE = FEATURES.register("yorishiro_stone_feature", () -> new YorishiroStoneFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus bus) { BLOCKS.register(bus); ITEMS.register(bus); BLOCK_ENTITIES.register(bus); ENTITIES.register(bus); MENUS.register(bus); TABS.register(bus); FEATURES.register(bus); }
    public static void bindCommon() {
        ModBlocks.bind(BUD.get(), GROWING.get(), UNDER.get(), MIDDLE.get(), TOP.get(), DEBUG.get(), STONE.get(), TRUNK_COLLISION.get(), DESK_COLLISION.get(), DESK.get());
        ModItems.bind(RAINBOW_SEED.get(), YORISHIRO_STONE_ITEM.get(), STUDY_BOOK.get(), STUDY_SET.get(), HARD_STUDY_SET.get(), HEADBAND.get(), PUNCHING_SET.get(), RUNNING_SET.get(), BALL.get(), BUBBLE_SET.get(), GAME.get(), GLASSES_AND_PEN.get(), PUNCHING_MACHINE.get(), RUNNING_MACHINE.get(), STUDY_DESK.get(), YORISYOKUNIN_SUMMON.get(), TREE_ITEM.get(), DEBUG_ITEM.get());
        ModBlockEntities.bind(TREE_BE.get(), GROWING_BE.get(), PART_BE.get(), DESK_BE.get()); ModEntities.bind(CHIBI.get(), ARTISAN.get());
        ModScreenHandlers.bind(TREE_MENU.get(), TRADE_MENU.get()); ModItemGroups.bind(TAB.get()); ModFeatures.bind(STONE_FEATURE.get());
    }
    private NeoForgeRegistries() {}