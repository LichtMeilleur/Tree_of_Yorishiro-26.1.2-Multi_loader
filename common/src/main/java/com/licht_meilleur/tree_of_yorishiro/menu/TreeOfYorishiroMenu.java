package com.licht_meilleur.tree_of_yorishiro.menu;

import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.screen.ModScreenHandlers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class TreeOfYorishiroMenu extends AbstractContainerMenu {

    private final TreeOfYorishiroPartBlockEntity blockEntity;

    public TreeOfYorishiroMenu(int syncId, Inventory inventory) {
        super(ModScreenHandlers.TREE_OF_YORISHIRO, syncId);
        this.blockEntity = null;
    }

    public TreeOfYorishiroMenu(int syncId, Inventory inventory, TreeOfYorishiroPartBlockEntity blockEntity) {
        super(ModScreenHandlers.TREE_OF_YORISHIRO, syncId);
        this.blockEntity = blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public TreeOfYorishiroPartBlockEntity getBlockEntity() {
        return blockEntity;
    }
}