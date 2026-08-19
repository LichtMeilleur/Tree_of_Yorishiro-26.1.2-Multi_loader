package com.licht_meilleur.tree_of_yorishiro.neoforge;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.command.ModCommands;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModBlockEntities;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModBlocks;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModEntities;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModFeatures;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModItemGroups;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModItems;
import com.licht_meilleur.tree_of_yorishiro.neoforge.registry.NeoForgeModScreenHandlers;
import com.licht_meilleur.tree_of_yorishiro.screen.MenuOpeningBridge;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod(TreeofYorishiroMod.MOD_ID)
public final class NeoForgeTreeOfYorishiroMod {

    public NeoForgeTreeOfYorishiroMod(
            IEventBus modBus
    ) {
        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] NeoForge initialization start"
        );

        /*
         * NeoForge側のレジストリー登録。
         *
         * BlockEntityやBlockItemからBlockを参照するため、
         * Blockを最初に登録します。
         */
        NeoForgeModBlocks.register(
                modBus
        );

        NeoForgeModItems.register(
                modBus
        );

        NeoForgeModBlockEntities.register(
                modBus
        );

        NeoForgeModEntities.register(
                modBus
        );

        NeoForgeModScreenHandlers.register(
                modBus
        );

        NeoForgeModItemGroups.register(
                modBus
        );

        NeoForgeModFeatures.register(
                modBus
        );

        /*
         * DeferredRegister完了後にcommon側へ
         * 登録結果を渡す処理。
         */
        modBus.addListener(
                this::commonSetup
        );

        /*
         * Mob属性登録。
         */
        modBus.addListener(
                this::registerAttributes
        );

        /*
         * ゲームイベント用イベントバス。
         */
        NeoForge.EVENT_BUS.addListener(
                this::registerCommands
        );

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] NeoForge registration queued"
        );
    }

    private void commonSetup(
            FMLCommonSetupEvent event
    ) {
        event.enqueueWork(
                () -> {
                    /*
                     * NeoForgeで登録された実体を
                     * common側のstatic参照へ渡します。
                     */
                    NeoForgeModBlocks
                            .bindCommonReferences();

                    NeoForgeModItems
                            .bindCommonReferences();

                    NeoForgeModBlockEntities
                            .bindCommonReferences();

                    NeoForgeModEntities
                            .bindCommonReferences();

                    NeoForgeModScreenHandlers
                            .bindCommonReferences();

                    NeoForgeModItemGroups
                            .bindCommonReferences();

                    NeoForgeModFeatures
                            .bindCommonReferences();

                    /*
                     * common側からNeoForgeの
                     * GUIオープン処理を呼べるようにします。
                     */
                    bindMenuOpeners();

                    TreeofYorishiroMod.LOGGER.info(
                            "[TreeOfYorishiro] NeoForge common references bound"
                    );
                }
        );
    }

    private void registerAttributes(
            EntityAttributeCreationEvent event
    ) {
        event.put(
                NeoForgeModEntities
                        .CHIBISHIRO
                        .get(),
                ChibishiroEntity
                        .createAttributes()
                        .build()
        );

        event.put(
                NeoForgeModEntities
                        .YORISYOKUNIN
                        .get(),
                YorisyokuninEntity
                        .createAttributes()
                        .build()
        );
    }

    private void registerCommands(
            RegisterCommandsEvent event
    ) {
        ModCommands.register(
                event.getDispatcher()
        );
    }

    private static void bindMenuOpeners() {

        /*
         * よりしろの木のGUI。
         */
        MenuOpeningBridge.bindTreeMenuOpener(
                (player, tree) ->
                        player.openMenu(
                                tree,
                                buffer ->
                                        TreeOfYorishiroMenuData
                                                .STREAM_CODEC
                                                .encode(
                                                        buffer,
                                                        new TreeOfYorishiroMenuData(
                                                                tree.getBlockPos()
                                                        )
                                                )
                        )
        );

        /*
         * よりしょくにん取引GUI。
         */
        MenuOpeningBridge.bindTradeMenuOpener(
                (player, desk) ->
                        player.openMenu(
                                desk,
                                buffer ->
                                        YorisyokuninTradeMenuData
                                                .STREAM_CODEC
                                                .encode(
                                                        buffer,
                                                        new YorisyokuninTradeMenuData(
                                                                desk.getBlockPos()
                                                        )
                                                )
                        )
        );
    }
}