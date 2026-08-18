package com.licht_meilleur.tree_of_yorishiro.screen;

import com.licht_meilleur.tree_of_yorishiro.block.entity.SyokuninDeskBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public final class MenuOpeningBridge {

    @FunctionalInterface
    public interface TreeMenuOpener {
        void open(ServerPlayer player, TreeOfYorishiroPartBlockEntity tree);
    }

    @FunctionalInterface
    public interface TradeMenuOpener {
        void open(ServerPlayer player, SyokuninDeskBlockEntity desk);
    }

    private static TreeMenuOpener treeMenuOpener;
    private static TradeMenuOpener tradeMenuOpener;

    public static void bindTreeMenuOpener(TreeMenuOpener opener) {
        treeMenuOpener = Objects.requireNonNull(opener, "tree menu opener");
    }

    public static void bindTradeMenuOpener(TradeMenuOpener opener) {
        tradeMenuOpener = Objects.requireNonNull(opener, "trade menu opener");
    }

    public static void openTree(ServerPlayer player, TreeOfYorishiroPartBlockEntity tree) {
        if (treeMenuOpener == null) {
            throw new IllegalStateException("Tree menu opener is not bound");
        }
        treeMenuOpener.open(player, tree);
    }

    public static void openTrade(ServerPlayer player, SyokuninDeskBlockEntity desk) {
        if (tradeMenuOpener == null) {
            throw new IllegalStateException("Trade menu opener is not bound");
        }
        tradeMenuOpener.open(player, desk);
    }

    private MenuOpeningBridge() {
    }
}