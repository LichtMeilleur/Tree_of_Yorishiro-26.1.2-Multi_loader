package com.licht_meilleur.tree_of_yorishiro.screen;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.entity.SyokuninDeskBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class ModScreenHandlers {

    public static final ResourceKey<MenuType<?>>
            TREE_OF_YORISHIRO_KEY =
            ResourceKey.create(
                    Registries.MENU,
                    TreeofYorishiroMod.id(
                            "tree_of_yorishiro"
                    )
            );

    public static final ResourceKey<MenuType<?>>
            YORISYOKUNIN_TRADE_KEY =
            ResourceKey.create(
                    Registries.MENU,
                    TreeofYorishiroMod.id(
                            "yorisyokunin_trade"
                    )
            );

    public static MenuType<TreeOfYorishiroScreenHandler>
            TREE_OF_YORISHIRO;

    public static MenuType<YorisyokuninTradeScreenHandler>
            YORISYOKUNIN_TRADE;

    public static TreeOfYorishiroScreenHandler createTree(
            int syncId,
            Inventory inventory,
            TreeOfYorishiroMenuData data
    ) {
        return new TreeOfYorishiroScreenHandler(
                syncId,
                inventory,
                data.pos()
        );
    }

    public static YorisyokuninTradeScreenHandler createTrade(
            int syncId,
            Inventory inventory,
            YorisyokuninTradeMenuData data
    ) {
        BlockEntity raw =
                inventory.player
                        .level()
                        .getBlockEntity(
                                data.pos()
                        );

        SyokuninDeskBlockEntity desk =
                raw instanceof SyokuninDeskBlockEntity found
                        ? found
                        : null;

        return new YorisyokuninTradeScreenHandler(
                syncId,
                inventory,
                desk,
                data.pos()
        );
    }

    public static void bindFabric(
            MenuType<TreeOfYorishiroScreenHandler> treeMenu,
            MenuType<YorisyokuninTradeScreenHandler> tradeMenu
    ) {
        bind(
                treeMenu,
                tradeMenu
        );
    }

    public static void bindNeoForge(
            MenuType<TreeOfYorishiroScreenHandler> treeMenu,
            MenuType<YorisyokuninTradeScreenHandler> tradeMenu
    ) {
        bind(
                treeMenu,
                tradeMenu
        );
    }

    private static void bind(
            MenuType<TreeOfYorishiroScreenHandler> treeMenu,
            MenuType<YorisyokuninTradeScreenHandler> tradeMenu
    ) {
        TREE_OF_YORISHIRO =
                treeMenu;

        YORISYOKUNIN_TRADE =
                tradeMenu;
    }

    private ModScreenHandlers() {
    }
}