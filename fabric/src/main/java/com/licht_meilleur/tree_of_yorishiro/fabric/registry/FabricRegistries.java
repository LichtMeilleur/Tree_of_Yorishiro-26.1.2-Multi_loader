package com.licht_meilleur.tree_of_yorishiro.fabric.registry;

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
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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

public final class FabricRegistries {
    public static void registerAll() {
        registerBlocks();
        registerItems();
        registerBlockEntities();
        registerEntities();
        registerMenus();
        registerItemGroup();
        registerFeature();
    }

    private static void registerBlocks() {
        Block bud = block("bud_of_yorishiro", new BudOfYorishiroBlock());
        Block growing = blockWithItem("growing_tree_of_yorishiro", new GrowingTreeOfYorishiroBlock());
        Block under = blockWithItem("tree_of_yorishiro_under", new TreeOfYorishiroPartBlock("tree_of_yorishiro_under", TreeOfYorishiroPartBlock.Part.UNDER));
        Block middle = blockWithItem("tree_of_yorishiro_middle", new TreeOfYorishiroPartBlock("tree_of_yorishiro_middle", TreeOfYorishiroPartBlock.Part.MIDDLE));
        Block top = blockWithItem("tree_of_yorishiro_top", new TreeOfYorishiroPartBlock("tree_of_yorishiro_top", TreeOfYorishiroPartBlock.Part.TOP));
        Block debug = blockWithItem("debug_tree_of_yorishiro", new DebugTreeOfYorishiroBlock(BlockBehaviour.Properties.of().setId(ModBlocks.DEBUG_TREE_OF_YORISHIRO_KEY).strength(2).sound(SoundType.WOOD).noOcclusion()));
        Block stone = blockWithItem("yorishiro_stone", new YorishiroStoneBlock());
        Block trunkCollision = block("yorishiro_trunk_collision", new YorishiroTrunkCollisionBlock(BlockBehaviour.Properties.of().setId(ModBlocks.YORISHIRO_TRUNK_COLLISION_KEY).strength(-1, 3600000).noLootTable().noOcclusion()));
        Block deskCollision = block("syokunin_desk_collision", new SyokuninDeskCollisionBlock(BlockBehaviour.Properties.of().setId(ModBlocks.SYOKUNIN_DESK_COLLISION_KEY).strength(0.1F).noOcclusion()));
        Block desk = blockWithItem("syokunin_desk", new SyokuninDeskBlock(BlockBehaviour.Properties.of().setId(ModBlocks.SYOKUNIN_DESK_KEY).strength(2).sound(SoundType.WOOD).noOcclusion()));
        ModBlocks.bind(bud, growing, under, middle, top, debug, stone, trunkCollision, deskCollision, desk);
    }

    private static void registerItems() {
        Item[] items = {
                item("rainbow_seed", new BlockItem(ModBlocks.BUD_OF_YORISHIRO, props("rainbow_seed"))),
                item("yorishiro_stone_item", new BlockItem(ModBlocks.YORISHIRO_STONE, props("yorishiro_stone_item"))),
                item("study_book", new Item(props("study_book"))), item("study_set", new Item(props("study_set"))),
                item("hard_study_set", new Item(props("hard_study_set"))), item("headband", new Item(props("headband"))),
                item("punching_set", new Item(props("punching_set"))), item("running_set", new Item(props("running_set"))),
                item("ball", new Item(props("ball"))), item("bubble_set", new Item(props("bubble_set"))),
                item("game", new Item(props("game"))), item("glasses_and_pen", new Item(props("glasses_and_pen"))),
                item("punching_machine", new Item(props("punching_machine"))), item("running_machine", new Item(props("running_machine"))),
                item("study_desk", new Item(props("study_desk"))),
                item("yorisyokunin_item", new YorisyokuninSummonItem(props("yorisyokunin_item").stacksTo(16))),
                item("tree_of_yorishiro_item", new TreeOfYorishiroItem(props("tree_of_yorishiro_item").stacksTo(1))),
                item("debug_tree_of_yorishiro_item", new BlockItem(ModBlocks.DEBUG_TREE_OF_YORISHIRO, props("debug_tree_of_yorishiro_item").stacksTo(1)))
        };
        ModItems.bind(items);
    }

    private static void registerBlockEntities() {
        BlockEntityType<TreeOfYorishiroBlockEntity> tree = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ModBlockEntities.TREE_OF_YORISHIRO_KEY.identifier(), FabricBlockEntityTypeBuilder.create(TreeOfYorishiroBlockEntity::new, ModBlocks.TREE_OF_YORISHIRO_UNDER).build());
        BlockEntityType<GrowingTreeOfYorishiroBlockEntity> growing = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ModBlockEntities.GROWING_TREE_OF_YORISHIRO_KEY.identifier(), FabricBlockEntityTypeBuilder.create(GrowingTreeOfYorishiroBlockEntity::new, ModBlocks.GROWING_TREE_OF_YORISHIRO).build());
        BlockEntityType<TreeOfYorishiroPartBlockEntity> parts = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ModBlockEntities.TREE_PART_KEY.identifier(), FabricBlockEntityTypeBuilder.create(TreeOfYorishiroPartBlockEntity::new, ModBlocks.TREE_OF_YORISHIRO_UNDER, ModBlocks.TREE_OF_YORISHIRO_MIDDLE, ModBlocks.TREE_OF_YORISHIRO_TOP).build());
        BlockEntityType<SyokuninDeskBlockEntity> desk = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                ModBlockEntities.SYOKUNIN_DESK_KEY.identifier(), FabricBlockEntityTypeBuilder.create(SyokuninDeskBlockEntity::new, ModBlocks.SYOKUNIN_DESK).build());
        ModBlockEntities.bind(tree, growing, parts, desk);
    }

    private static void registerEntities() {
        EntityType<ChibishiroEntity> chibi = Registry.register(BuiltInRegistries.ENTITY_TYPE, ModEntities.CHIBISHIRO_KEY.identifier(),
                EntityType.Builder.of(ChibishiroEntity::new, MobCategory.CREATURE).sized(0.6F, 0.9F).clientTrackingRange(80).updateInterval(3).build(ModEntities.CHIBISHIRO_KEY));
        EntityType<YorisyokuninEntity> artisan = Registry.register(BuiltInRegistries.ENTITY_TYPE, ModEntities.YORISYOKUNIN_KEY.identifier(),
                EntityType.Builder.of(YorisyokuninEntity::new, MobCategory.MISC).sized(0.6F, 1.6F).clientTrackingRange(64).updateInterval(2).build(ModEntities.YORISYOKUNIN_KEY));
        ModEntities.bind(chibi, artisan);
        FabricDefaultAttributeRegistry.register(chibi, ChibishiroEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(artisan, YorisyokuninEntity.createAttributes());
    }

    private static void registerMenus() {
        MenuType<TreeOfYorishiroScreenHandler> tree = Registry.register(BuiltInRegistries.MENU, ModScreenHandlers.TREE_OF_YORISHIRO_KEY.identifier(),
                new ExtendedMenuType<>(ModScreenHandlers::createTree, TreeOfYorishiroMenuData.STREAM_CODEC));
        MenuType<YorisyokuninTradeScreenHandler> trade = Registry.register(BuiltInRegistries.MENU, ModScreenHandlers.YORISYOKUNIN_TRADE_KEY.identifier(),
                new ExtendedMenuType<>(ModScreenHandlers::createTrade, YorisyokuninTradeMenuData.STREAM_CODEC));
        ModScreenHandlers.bind(tree, trade);
    }

    private static void registerItemGroup() {
        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModItemGroups.TREE_OF_YORISHIRO_GROUP_KEY.identifier(),
                CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("itemGroup.tree_of_yorishiro.group"))
                        .icon(() -> new ItemStack(ModItems.YORISHIRO_STONE)).displayItems((p, out) -> {
                            out.accept(ModItems.YORISHIRO_STONE); out.accept(ModItems.RAINBOW_SEED); out.accept(ModItems.TREE_OF_YORISHIRO_ITEM);
                            out.accept(ModItems.YORISYOKUNIN_SUMMON); out.accept(ModItems.STUDY_BOOK); out.accept(ModItems.STUDY_SET);
                            out.accept(ModItems.HARD_STUDY_SET); out.accept(ModItems.HEADBAND); out.accept(ModItems.PUNCHING_SET);
                            out.accept(ModItems.RUNNING_SET); out.accept(ModItems.BALL); out.accept(ModItems.BUBBLE_SET); out.accept(ModItems.GAME);
                            out.accept(ModItems.GLASSES_AND_PEN); out.accept(ModItems.PUNCHING_MACHINE); out.accept(ModItems.RUNNING_MACHINE);
                            out.accept(ModItems.STUDY_DESK); out.accept(ModItems.DEBUG_TREE_OF_YORISHIRO_ITEM);
                        }).build());
        ModItemGroups.bind(tab);
    }

    private static void registerFeature() {
        Feature<NoneFeatureConfiguration> feature = Registry.register(BuiltInRegistries.FEATURE,
                ModFeatures.YORISHIRO_STONE_FEATURE_KEY.identifier(), new YorishiroStoneFeature(NoneFeatureConfiguration.CODEC));
        ModFeatures.bind(feature);
    }

    private static Block block(String id, Block block) { return Registry.register(BuiltInRegistries.BLOCK, TreeofYorishiroMod.id(id), block); }
    private static Block blockWithItem(String id, Block block) {
        block(id, block); item(id, new BlockItem(block, props(id))); return block;
    }
    private static Item item(String id, Item item) { return Registry.register(BuiltInRegistries.ITEM, TreeofYorishiroMod.id(id), item); }
    private static Item.Properties props(String id) { return new Item.Properties().setId(ModItems.key(id)); }
    private FabricRegistries() {}
}
