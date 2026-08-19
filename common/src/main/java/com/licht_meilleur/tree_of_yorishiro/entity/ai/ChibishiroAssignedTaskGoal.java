package com.licht_meilleur.tree_of_yorishiro.entity.ai;

import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeChibishiroData;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroAnimState;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChibishiroAssignedTaskGoal extends Goal {

    private final ChibishiroEntity chibi;
    private boolean startedAnimation = false;

    public ChibishiroAssignedTaskGoal(ChibishiroEntity chibi) {
        this.chibi = chibi;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        TreeChibishiroData data = getTaskData();
        return data != null && data.isTraining();
    }

    @Override
    public boolean canContinueToUse() {
        TreeChibishiroData data = getTaskData();
        return data != null && data.isTraining();
    }

    @Override
    public void start() {
        this.startedAnimation = false;
    }

    @Override
    public void stop() {
        this.startedAnimation = false;
        this.chibi.getNavigation().stop();

        if (!chibi.level().isClientSide()) {
            chibi.setAnimState(ChibishiroAnimState.IDLE);
            chibi.setAnimTicks(0);
            chibi.setDisplayFoodStack(net.minecraft.world.item.ItemStack.EMPTY);
        }
    }

    @Override
    public void tick() {
        TreeChibishiroData data = getTaskData();
        if (data == null) return;

        BlockPos home = chibi.getHomeTreePos();
        if (home == null) return;

        double tx = home.getX() + 0.5;
        double ty = home.getY() + 1.0;
        double tz = home.getZ() + 0.5;

        double distSq = chibi.distanceToSqr(tx, ty, tz);

        if (distSq > 6.25D) {
            /*
             * 作業場所へ移動中はWALK。
             * 以前のPLAYなどが残っている場合も解除する。
             */
            if (chibi.getAnimState()
                    != ChibishiroAnimState.WALK) {

                chibi.setAnimTicks(0);
                chibi.setAnimState(
                        ChibishiroAnimState.WALK
                );
            }

            chibi.getNavigation().moveTo(
                    tx,
                    ty,
                    tz,
                    1.0D
            );

            return;
        }

        chibi.getNavigation().stop();

        Vec3 movement =
                chibi.getDeltaMovement();

        chibi.setDeltaMovement(
                0.0D,
                movement.y,
                0.0D
        );

        chibi.getLookControl().setLookAt(
                tx,
                ty,
                tz
        );

        if (!startedAnimation) {
            startedAnimation = true;
            startTaskAnimation(data);
        }
    }

    private void startTaskAnimation(TreeChibishiroData data) {
        String type = data.getTrainingType();
        int level = data.getTrainingLevel();

        switch (type) {
            case "MEAL" -> chibi.startMealTask();

            case "STUDY" -> {
                if (level == 1) chibi.startStudy1Task();
                else if (level == 2) chibi.startStudy2Task();
                else if (level == 3) chibi.startStudy3Task();
            }

            case "EXERCISE" -> {
                if (level == 1) chibi.startTraining1Task();
                else if (level == 2) chibi.startTraining2Task();
                else if (level == 3) chibi.startTraining3Task();
            }

            case "PLAY" -> {
                if (level == 1) chibi.startGame1Task();
                else if (level == 2) chibi.startGame2Task();
                else if (level == 3) chibi.startGame3Task();
            }

            default -> {
            }
        }
    }

    private TreeChibishiroData getTaskData() {
        BlockPos home = chibi.getHomeTreePos();
        if (home == null) return null;

        if (!(chibi.level().getBlockEntity(home) instanceof TreeOfYorishiroPartBlockEntity be)) {
            return null;
        }

        return be.getChibiDataByColor(chibi.getColor());
    }
}