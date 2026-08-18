package com.licht_meilleur.tree_of_yorishiro.fabric;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.command.ModCommands;
import com.licht_meilleur.tree_of_yorishiro.fabric.registry.FabricRegistries;
import com.licht_meilleur.tree_of_yorishiro.screen.MenuOpeningBridge;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import com.licht_meilleur.tree_of_yorishiro.world.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class FabricTreeOfYorishiroMod implements ModInitializer {
    @Override
    public void onInitialize() {
        TreeofYorishiroMod.LOGGER.info("[TreeOfYorishiro] Fabric initialization start");
        FabricRegistries.registerAll();
        bindMenuOpeners();
        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> ModCommands.register(dispatcher));
        BiomeModifications.addFeature(BiomeSelectors.all(), GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                ModPlacedFeatures.YORISHIRO_STONE);
        TreeofYorishiroMod.LOGGER.info("[TreeOfYorishiro] Fabric initialization complete");
    }

    private static void bindMenuOpeners() {
        MenuOpeningBridge.bindTreeMenuOpener((player, tree) -> player.openMenu(
                new ExtendedMenuProvider<TreeOfYorishiroMenuData>() {
                    @Override public TreeOfYorishiroMenuData getScreenOpeningData(ServerPlayer ignored) {
                        return new TreeOfYorishiroMenuData(tree.getBlockPos());
                    }
                    @Override public Component getDisplayName() { return tree.getDisplayName(); }
                    @Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player menuPlayer) {
                        return tree.createMenu(id, inv, menuPlayer);
                    }
                }));
        MenuOpeningBridge.bindTradeMenuOpener((player, desk) -> player.openMenu(
                new ExtendedMenuProvider<YorisyokuninTradeMenuData>() {
                    @Override public YorisyokuninTradeMenuData getScreenOpeningData(ServerPlayer ignored) {
                        return new YorisyokuninTradeMenuData(desk.getBlockPos());
                    }
                    @Override public Component getDisplayName() { return desk.getDisplayName(); }
                    @Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player menuPlayer) {
                        return desk.createMenu(id, inv, menuPlayer);
                    }
                }));
    }
}
