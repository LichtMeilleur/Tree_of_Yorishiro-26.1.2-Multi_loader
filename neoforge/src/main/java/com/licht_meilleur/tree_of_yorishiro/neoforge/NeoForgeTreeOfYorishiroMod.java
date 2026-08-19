package com.licht_meilleur.tree_of_yorishiro.neoforge;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.command.ModCommands;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.screen.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(TreeofYorishiroMod.MOD_ID)
public final class NeoForgeTreeOfYorishiroMod {
    public NeoForgeTreeOfYorishiroMod(IEventBus modBus) {
        TreeofYorishiroMod.LOGGER.info("[TreeOfYorishiro] NeoForge initialization start");
        NeoForgeRegistries.register(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerAttributes);
        NeoForge.EVENT_BUS.addListener(this::registerCommands);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NeoForgeRegistries.bindCommon();
            bindMenuOpeners();
            TreeofYorishiroMod.LOGGER.info("[TreeOfYorishiro] NeoForge common references bound");
        });
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(NeoForgeRegistries.CHIBI.get(), ChibishiroEntity.createAttributes().build());
        event.put(NeoForgeRegistries.ARTISAN.get(), YorisyokuninEntity.createAttributes().build());
    }

    private void registerCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    private static void bindMenuOpeners() {
        MenuOpeningBridge.bindTreeMenuOpener((player, tree) -> player.openMenu(tree,
                buffer -> TreeOfYorishiroMenuData.STREAM_CODEC.encode(buffer, new TreeOfYorishiroMenuData(tree.getBlockPos()))));
        MenuOpeningBridge.bindTradeMenuOpener((player, desk) -> player.openMenu(desk,
                buffer -> YorisyokuninTradeMenuData.STREAM_CODEC.encode(buffer, new YorisyokuninTradeMenuData(desk.getBlockPos()))));
    }
}