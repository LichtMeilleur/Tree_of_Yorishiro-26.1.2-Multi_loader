package com.licht_meilleur.tree_of_yorishiro.neoforge.registry;

import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.registry.ModItemGroups;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NeoForgeModItemGroups {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(
                    Registries.CREATIVE_MODE_TAB,
                    TreeofYorishiroMod.MOD_ID
            );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab>
            TREE_OF_YORISHIRO_GROUP =
            CREATIVE_MODE_TABS.register(
                    "tree_of_yorishiro_group",
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                            .title(Component.translatable("itemGroup.tree_of_yorishiro.group"))
                            .icon(() -> new ItemStack(NeoForgeModItems.YORISHIRO_STONE.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(NeoForgeModItems.YORISHIRO_STONE.get());
                                output.accept(NeoForgeModItems.RAINBOW_SEED.get());
                                output.accept(NeoForgeModItems.TREE_OF_YORISHIRO_ITEM.get());
                                output.accept(NeoForgeModItems.YORISYOKUNIN_SUMMON.get());
                                output.accept(NeoForgeModItems.STUDY_BOOK.get());
                                output.accept(NeoForgeModItems.STUDY_SET.get());
                                output.accept(NeoForgeModItems.HARD_STUDY_SET.get());
                                output.accept(NeoForgeModItems.HEADBAND.get());
                                output.accept(NeoForgeModItems.PUNCHING_SET.get());
                                output.accept(NeoForgeModItems.RUNNING_SET.get());
                                output.accept(NeoForgeModItems.BALL.get());
                                output.accept(NeoForgeModItems.BUBBLE_SET.get());
                                output.accept(NeoForgeModItems.GAME.get());
                                output.accept(NeoForgeModItems.GLASSES_AND_PEN.get());
                                output.accept(NeoForgeModItems.PUNCHING_MACHINE.get());
                                output.accept(NeoForgeModItems.RUNNING_MACHINE.get());
                                output.accept(NeoForgeModItems.STUDY_DESK.get());
                                output.accept(NeoForgeModItems.DEBUG_TREE_OF_YORISHIRO_ITEM.get());
                            })
                            .build()
            );

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }

    public static void bindCommonReferences() {
        ModItemGroups.bindNeoForge(TREE_OF_YORISHIRO_GROUP.get());
    }

    private NeoForgeModItemGroups() {
    }
}