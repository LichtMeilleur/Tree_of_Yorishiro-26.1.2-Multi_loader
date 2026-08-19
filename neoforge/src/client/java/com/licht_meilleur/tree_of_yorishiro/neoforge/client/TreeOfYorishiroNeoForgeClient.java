package com.licht_meilleur.tree_of_yorishiro.neoforge.client;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.client.block.GrowingTreeOfYorishiroRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.block.SyokuninDeskPreviewRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.block.SyokuninDeskRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.block.TreeOfYorishiroRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.entity.ChibishiroRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.entity.YorisyokuninRenderer;
import com.licht_meilleur.tree_of_yorishiro.client.screen.TreeOfYorishiroScreen;
import com.licht_meilleur.tree_of_yorishiro.client.screen.YorisyokuninTradeScreen;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModBlockEntities;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModEntities;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModScreenHandlers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = TreeofYorishiroMod.MOD_ID, value = Dist.CLIENT)
public final class TreeOfYorishiroNeoForgeClient {

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerBlockEntityRenderer(
                NeoForgeModBlockEntities.GROWING_TREE_OF_YORISHIRO.get(),
                GrowingTreeOfYorishiroRenderer::new
        );
        event.registerBlockEntityRenderer(
                NeoForgeModBlockEntities.TREE_PART.get(),
                TreeOfYorishiroRenderer::new
        );
        event.registerBlockEntityRenderer(
                NeoForgeModBlockEntities.SYOKUNIN_DESK.get(),
                SyokuninDeskRenderer::new
        );

        event.registerEntityRenderer(
                NeoForgeModEntities.CHIBISHIRO.get(),
                ChibishiroRenderer::new
        );
        event.registerEntityRenderer(
                NeoForgeModEntities.YORISYOKUNIN.get(),
                YorisyokuninRenderer::new
        );
    }

    @SubscribeEvent
    public static void registerMenuScreens(
            RegisterMenuScreensEvent event
    ) {
        event.register(
                NeoForgeModScreenHandlers.TREE_OF_YORISHIRO.get(),
                TreeOfYorishiroScreen::new
        );
        event.register(
                NeoForgeModScreenHandlers.YORISYOKUNIN_TRADE.get(),
                YorisyokuninTradeScreen::new
        );
    }

    @SubscribeEvent
    public static void onClientTick(
            ClientTickEvent.Post event
    ) {
        SyokuninDeskPreviewRenderer.clientTick();
    }

    private TreeOfYorishiroNeoForgeClient() {
    }
}