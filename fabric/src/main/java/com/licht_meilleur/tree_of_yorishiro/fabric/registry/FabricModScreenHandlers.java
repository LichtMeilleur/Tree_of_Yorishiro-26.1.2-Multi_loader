package com.licht_meilleur.tree_of_yorishiro.fabric.registry;

import com.licht_meilleur.tree_of_yorishiro.screen.ModScreenHandlers;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroScreenHandler;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeScreenHandler;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public final class FabricModScreenHandlers {

    private static boolean registered;

    private FabricModScreenHandlers() {
    }

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;

        MenuType<TreeOfYorishiroScreenHandler>
                treeMenu =
                Registry.register(
                        BuiltInRegistries.MENU,
                        ModScreenHandlers
                                .TREE_OF_YORISHIRO_KEY
                                .identifier(),
                        new ExtendedMenuType<
                                TreeOfYorishiroScreenHandler,
                                TreeOfYorishiroMenuData
                                >(
                                ModScreenHandlers::createTree,
                                TreeOfYorishiroMenuData.STREAM_CODEC
                        )
                );

        MenuType<YorisyokuninTradeScreenHandler>
                tradeMenu =
                Registry.register(
                        BuiltInRegistries.MENU,
                        ModScreenHandlers
                                .YORISYOKUNIN_TRADE_KEY
                                .identifier(),
                        new ExtendedMenuType<
                                YorisyokuninTradeScreenHandler,
                                YorisyokuninTradeMenuData
                                >(
                                ModScreenHandlers::createTrade,
                                YorisyokuninTradeMenuData.STREAM_CODEC
                        )
                );

        ModScreenHandlers.bindFabric(
                treeMenu,
                tradeMenu
        );
    }
}