package com.licht_meilleur.tree_of_yorishiro.fabric;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.command.ModCommands;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.fabric.registry.FabricModScreenHandlers;
import com.licht_meilleur.tree_of_yorishiro.fabric.world.FabricWorldGeneration;
import com.licht_meilleur.tree_of_yorishiro.registry.*;
import com.licht_meilleur.tree_of_yorishiro.screen.MenuOpeningBridge;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import com.licht_meilleur.tree_of_yorishiro.world.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiFunction;

public final class FabricTreeOfYorishiroMod
        implements ModInitializer {

    @Override
    public void onInitialize() {
        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric initialization start"
        );

        /*
         * BlockEntityTypeやBlockItemがBlockを参照するため、
         * Blockを最初に登録する。
         */
        ModBlocks.registerFabric();
        ModItems.registerFabric();

        ModBlockEntities.registerFabric(
                FabricTreeOfYorishiroMod
                        ::registerBlockEntityType
        );

        ModEntities.registerFabric();

        FabricDefaultAttributeRegistry.register(
                ModEntities.CHIBISHIRO,
                ChibishiroEntity.createAttributes()
        );

        FabricDefaultAttributeRegistry.register(
                ModEntities.YORISYOKUNIN,
                YorisyokuninEntity.createAttributes()
        );

        FabricModScreenHandlers.register();

        ModItemGroups.registerFabric();
        ModFeatures.registerFabric();

        bindMenuOpeners();

        CommandRegistrationCallback.EVENT.register(
                (
                        dispatcher,
                        registryAccess,
                        environment
                ) ->
                        ModCommands.register(
                                dispatcher
                        )
        );

        FabricWorldGeneration.register();

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric initialization complete"
        );
    }

    private static void bindMenuOpeners() {
        MenuOpeningBridge.bindTreeMenuOpener(
                (
                        player,
                        tree
                ) ->
                        player.openMenu(
                                new ExtendedMenuProvider<
                                        TreeOfYorishiroMenuData
                                        >() {

                                    @Override
                                    public TreeOfYorishiroMenuData
                                    getScreenOpeningData(
                                            ServerPlayer ignored
                                    ) {
                                        return new TreeOfYorishiroMenuData(
                                                tree.getBlockPos()
                                        );
                                    }

                                    @Override
                                    public Component getDisplayName() {
                                        return tree.getDisplayName();
                                    }

                                    @Override
                                    public AbstractContainerMenu createMenu(
                                            int syncId,
                                            Inventory inventory,
                                            Player menuPlayer
                                    ) {
                                        return tree.createMenu(
                                                syncId,
                                                inventory,
                                                menuPlayer
                                        );
                                    }
                                }
                        )
        );

        MenuOpeningBridge.bindTradeMenuOpener(
                (
                        player,
                        desk
                ) ->
                        player.openMenu(
                                new ExtendedMenuProvider<
                                        YorisyokuninTradeMenuData
                                        >() {

                                    @Override
                                    public YorisyokuninTradeMenuData
                                    getScreenOpeningData(
                                            ServerPlayer ignored
                                    ) {
                                        return new YorisyokuninTradeMenuData(
                                                desk.getBlockPos()
                                        );
                                    }

                                    @Override
                                    public Component getDisplayName() {
                                        return desk.getDisplayName();
                                    }

                                    @Override
                                    public AbstractContainerMenu createMenu(
                                            int syncId,
                                            Inventory inventory,
                                            Player menuPlayer
                                    ) {
                                        return desk.createMenu(
                                                syncId,
                                                inventory,
                                                menuPlayer
                                        );
                                    }
                                }
                        )
        );
    }

    private static <
            T extends BlockEntity
            >
    BlockEntityType<T> registerBlockEntityType(
            ResourceKey<BlockEntityType<?>> key,
            BiFunction<BlockPos, BlockState, T> factory,
            Block... validBlocks
    ) {
        BlockEntityType<T> type =
                FabricBlockEntityTypeBuilder.create(
                        factory::apply,
                        validBlocks
                ).build();

        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                key.identifier(),
                type
        );
    }
}