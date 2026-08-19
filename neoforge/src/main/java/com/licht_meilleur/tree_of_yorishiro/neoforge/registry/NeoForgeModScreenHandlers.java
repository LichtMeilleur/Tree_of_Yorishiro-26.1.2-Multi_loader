package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.screen.ModScreenHandlers;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroScreenHandler;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeScreenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModScreenHandlers {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(
                    Registries.MENU,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<
            MenuType<?>,
            MenuType<TreeOfYorishiroScreenHandler>
            > TREE_OF_YORISHIRO =
            MENUS.register(
                    "tree_of_yorishiro",
                    () -> IMenuTypeExtension.create(
                            (syncId, inventory, buffer) ->
                                    ModScreenHandlers.createTree(
                                            syncId,
                                            inventory,
                                            TreeOfYorishiroMenuData.STREAM_CODEC.decode(buffer)
                                    )
                    )
            );

    public static final DeferredHolder<
            MenuType<?>,
            MenuType<YorisyokuninTradeScreenHandler>
            > YORISYOKUNIN_TRADE =
            MENUS.register(
                    "yorisyokunin_trade",
                    () -> IMenuTypeExtension.create(
                            (syncId, inventory, buffer) ->
                                    ModScreenHandlers.createTrade(
                                            syncId,
                                            inventory,
                                            YorisyokuninTradeMenuData.STREAM_CODEC.decode(buffer)
                                    )
                    )
            );

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }

    public static void bindCommonReferences() {
        ModScreenHandlers.bindNeoForge(
                TREE_OF_YORISHIRO.get(),
                YORISYOKUNIN_TRADE.get()
        );
    }

    private NeoForgeModScreenHandlers() {
    }
}