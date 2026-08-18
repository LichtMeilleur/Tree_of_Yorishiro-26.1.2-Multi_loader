package com.licht_meilleur.tree_of_yorishiro.entity.ai;

import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class YorisyokuninStayAtDeskGoal extends Goal {

    private final YorisyokuninEntity mob;

    public YorisyokuninStayAtDeskGoal(YorisyokuninEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.noneOf(Goal.Flag.class));
    }

    @Override
    public boolean canUse() {
        return mob.getDeskPosValue() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return mob.getDeskPosValue() != null;
    }

    @Override
    public void tick() {
        BlockPos deskPos = mob.getDeskPosValue();
        if (deskPos == null) return;

        double targetX = deskPos.getX() + 0.5;
        double targetY = deskPos.getY();
        double targetZ = deskPos.getZ() + 0.5;

        double dx = targetX - mob.getX();
        double dz = targetZ - mob.getZ();
        double distSq = dx * dx + dz * dz;

        mob.getNavigation().stop();
        mob.setDeltaMovement(0.0, mob.getDeltaMovement().y, 0.0);

        // 大きくズレたら瞬間補正
        if (distSq > 0.25D) {
            mob.setPos(targetX, targetY, targetZ);
            return;
        }

        // 小さなズレはなめらかに戻す
        if (distSq > 0.0004D) {
            mob.setPos(
                    mob.getX() + dx * 0.25D,
                    targetY,
                    mob.getZ() + dz * 0.25D
            );
        }
    }
}