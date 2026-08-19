package com.licht_meilleur.tree_of_yorishiro.client.block;

import com.licht_meilleur.tree_of_yorishiro.block.SyokuninDeskBlock;
import com.licht_meilleur.tree_of_yorishiro.item.YorisyokuninSummonItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public final class SyokuninDeskPreviewRenderer {

    private SyokuninDeskPreviewRenderer() {
    }

    public static void clientTick() {
        Minecraft minecraft =
                Minecraft.getInstance();

        if (minecraft.player == null
                || minecraft.level == null) {
            return;
        }

        ItemStack stack =
                minecraft.player.getMainHandItem();

        if (!(stack.getItem()
                instanceof YorisyokuninSummonItem)) {
            return;
        }

        if (!(minecraft.hitResult
                instanceof BlockHitResult hit)) {
            return;
        }

        BlockPos basePosition =
                hit.getBlockPos().above();

        Direction facing =
                minecraft.player
                        .getDirection()
                        .getOpposite();

        drawBoxParticles(
                minecraft,
                basePosition,
                true
        );

        for (BlockPos offset
                : SyokuninDeskBlock.NORTH_COLLISIONS) {

            BlockPos worldPosition =
                    basePosition.offset(
                            SyokuninDeskBlock.rotateOffset(
                                    offset,
                                    facing
                            )
                    );

            drawBoxParticles(
                    minecraft,
                    worldPosition,
                    false
            );
        }
    }

    private static void drawBoxParticles(
            Minecraft minecraft,
            BlockPos position,
            boolean center
    ) {
        double y =
                position.getY() + 0.08D;

        double step =
                0.125D;

        for (double progress = 0.0D;
             progress <= 1.001D;
             progress += step) {

            spawn(
                    minecraft,
                    position.getX() + progress,
                    y,
                    position.getZ(),
                    center
            );

            spawn(
                    minecraft,
                    position.getX() + progress,
                    y,
                    position.getZ() + 1.0D,
                    center
            );

            spawn(
                    minecraft,
                    position.getX(),
                    y,
                    position.getZ() + progress,
                    center
            );

            spawn(
                    minecraft,
                    position.getX() + 1.0D,
                    y,
                    position.getZ() + progress,
                    center
            );
        }
    }

    private static void spawn(
            Minecraft minecraft,
            double x,
            double y,
            double z,
            boolean center
    ) {
        if (minecraft.level == null) {
            return;
        }

        minecraft.level.addParticle(
                center
                        ? ParticleTypes.HAPPY_VILLAGER
                        : ParticleTypes.END_ROD,
                x,
                y,
                z,
                0.0D,
                0.0D,
                0.0D
        );
    }
}