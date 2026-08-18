package com.licht_meilleur.tree_of_yorishiro.client.block;

import com.licht_meilleur.tree_of_yorishiro.block.SyokuninDeskBlock;
import com.licht_meilleur.tree_of_yorishiro.item.YorisyokuninSummonItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

public class SyokuninDeskPreviewRenderer {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick(client));
    }

    private static void tick(Minecraft mc) {
        if (mc.player == null || mc.level == null) return;

        ItemStack stack = mc.player.getMainHandItem();
        if (!(stack.getItem() instanceof YorisyokuninSummonItem)) return;

        if (!(mc.hitResult instanceof BlockHitResult hit)) return;

        BlockPos basePos = hit.getBlockPos().above();
        Direction facing = mc.player.getDirection().getOpposite();

        // 中央
        drawBoxParticles(mc, basePos, true);

        // コリジョン
        for (BlockPos offset : SyokuninDeskBlock.NORTH_COLLISIONS) {
            BlockPos worldPos = basePos.offset(
                    SyokuninDeskBlock.rotateOffset(offset, facing)
            );

            drawBoxParticles(mc, worldPos, false);
        }
    }

    // ★これを追加する
    private static void drawBoxParticles(Minecraft mc, BlockPos pos, boolean center) {
        double y = pos.getY() + 0.08;
        double step = 0.125;

        for (double t = 0.0; t <= 1.001; t += step) {
            spawn(mc, pos.getX() + t, y, pos.getZ(), center);
            spawn(mc, pos.getX() + t, y, pos.getZ() + 1.0, center);
            spawn(mc, pos.getX(), y, pos.getZ() + t, center);
            spawn(mc, pos.getX() + 1.0, y, pos.getZ() + t, center);
        }
    }

    // ★これも追加
    private static void spawn(Minecraft mc, double x, double y, double z, boolean center) {
        mc.level.addParticle(
                center ? ParticleTypes.HAPPY_VILLAGER : ParticleTypes.END_ROD,
                x,
                y,
                z,
                0.0,
                0.0,
                0.0
        );
    }
}