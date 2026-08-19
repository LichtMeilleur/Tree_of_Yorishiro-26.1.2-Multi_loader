package com.licht_meilleur.tree_of_yorishiro.registry;

import com.licht_meilleur.tree_of_yorishiro
        .TreeofYorishiroMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModItemGroups {

    public static final ResourceKey<
            CreativeModeTab
            > TREE_OF_YORISHIRO_GROUP_KEY =
            ResourceKey.create(
                    Registries.CREATIVE_MODE_TAB,
                    TreeofYorishiroMod.id(
                            "tree_of_yorishiro_group"
                    )
            );

    public static CreativeModeTab
            TREE_OF_YORISHIRO_GROUP;


    private static boolean fabricRegistered;


    public static void registerFabric() {
        if (fabricRegistered) {
            return;
        }

        fabricRegistered = true;

        if (ModItems.YORISHIRO_STONE
                == null) {
            throw new IllegalStateException(
                    "ModItems must be registered before ModItemGroups"
            );
        }

        TREE_OF_YORISHIRO_GROUP =
                Registry.register(
                        BuiltInRegistries
                                .CREATIVE_MODE_TAB,
                        TREE_OF_YORISHIRO_GROUP_KEY
                                .identifier(),
                        createTab()
                );

        TreeofYorishiroMod.LOGGER.info(
                "[TreeOfYorishiro] Fabric item group registered"
        );
    }


    public static void bindNeoForge(
            CreativeModeTab tab
    ) {
        TREE_OF_YORISHIRO_GROUP =
                tab;
    }


    /*
     * FabricとNeoForgeの両方で
     * 同じタブ内容を使用できます。
     */
    public static CreativeModeTab createTab() {
        return CreativeModeTab.builder(
                        CreativeModeTab.Row.TOP,
                        0
                )
                .title(
                        Component.translatable(
                                "itemGroup.tree_of_yorishiro.group"
                        )
                )
                .icon(
                        () -> new ItemStack(
                                ModItems
                                        .YORISHIRO_STONE
                        )
                )
                .displayItems(
                        (
                                parameters,
                                output
                        ) -> {
                            output.accept(
                                    ModItems.YORISHIRO_STONE
                            );

                            output.accept(
                                    ModItems.RAINBOW_SEED
                            );

                            output.accept(
                                    ModItems
                                            .TREE_OF_YORISHIRO_ITEM
                            );

                            output.accept(
                                    ModItems
                                            .YORISYOKUNIN_SUMMON
                            );

                            output.accept(
                                    ModItems.STUDY_BOOK
                            );

                            output.accept(
                                    ModItems.STUDY_SET
                            );

                            output.accept(
                                    ModItems.HARD_STUDY_SET
                            );

                            output.accept(
                                    ModItems.HEADBAND
                            );

                            output.accept(
                                    ModItems.PUNCHING_SET
                            );

                            output.accept(
                                    ModItems.RUNNING_SET
                            );

                            output.accept(
                                    ModItems.BALL
                            );

                            output.accept(
                                    ModItems.BUBBLE_SET
                            );

                            output.accept(
                                    ModItems.GAME
                            );

                            output.accept(
                                    ModItems.GLASSES_AND_PEN
                            );

                            output.accept(
                                    ModItems.PUNCHING_MACHINE
                            );

                            output.accept(
                                    ModItems.RUNNING_MACHINE
                            );

                            output.accept(
                                    ModItems.STUDY_DESK
                            );

                            output.accept(
                                    ModItems
                                            .DEBUG_TREE_OF_YORISHIRO_ITEM
                            );
                        }
                )
                .build();
    }

    private ModItemGroups() {
    }
}