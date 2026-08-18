package com.licht_meilleur.tree_of_yorishiro.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.licht_meilleur.tree_of_yorishiro.entity.ai.YorisyokuninStayAtDeskGoal;
import com.licht_meilleur.tree_of_yorishiro.entity.ai.YorisyokuninWorkGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class YorisyokuninEntity extends PathfinderMob implements GeoEntity {

    private static final int DATA_VERSION = 1;

    public static final String ANIM_IDLE = "animation.model.idle";
    public static final String ANIM_WORK = "animation.model.work";
    public static final String ANIM_SLEEP = "animation.model.sleep";
    public static final String ANIM_SLEEP_END = "animation.model.sleep_end";

    private static final EntityDataAccessor<Integer> ANIM_STATE =
            SynchedEntityData.defineId(YorisyokuninEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<ItemStack> HELD_WORK_ITEM =
            SynchedEntityData.defineId(YorisyokuninEntity.class, EntityDataSerializers.ITEM_STACK);

    private static final EntityDataAccessor<Boolean> WORKING =
            SynchedEntityData.defineId(YorisyokuninEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private BlockPos deskPos;

    @Nullable
    private Vec3 workLookTarget;

    public enum AnimState {
        IDLE,
        WORK,
        SLEEP,
        SLEEP_END
    }

    public YorisyokuninEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.setInvulnerable(true);
        this.setNoGravity(true);
    }






    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIM_STATE, AnimState.IDLE.ordinal());
        builder.define(HELD_WORK_ITEM, ItemStack.EMPTY);
        builder.define(WORKING, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new YorisyokuninStayAtDeskGoal(this));
        this.goalSelector.addGoal(1, new YorisyokuninWorkGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D);
    }

    public boolean isWorking() {
        return this.entityData.get(WORKING);
    }

    public boolean isWorkAnimationActive() {
        return this.entityData.get(ANIM_STATE) == AnimState.WORK.ordinal();
    }

    public void beginDeskWork(Vec3 lookTarget) {
        this.entityData.set(WORKING, true);
        this.entityData.set(ANIM_STATE, AnimState.IDLE.ordinal());
        this.workLookTarget = lookTarget;
        this.getNavigation().stop();
    }

    public void playWorkAnimation() {
        this.entityData.set(ANIM_STATE, AnimState.WORK.ordinal());
    }

    public void stopDeskWork() {
        this.entityData.set(ANIM_STATE, AnimState.IDLE.ordinal());
        this.entityData.set(WORKING, false);
        this.workLookTarget = null;
        this.getNavigation().stop();
    }

    public boolean hasWorkLookTarget() {
        return this.workLookTarget != null;
    }

    @Nullable
    public Vec3 getWorkLookTarget() {
        return this.workLookTarget;
    }

    public void clearWorkLookTarget() {
        this.workLookTarget = null;
    }

    public void setDeskPos(BlockPos pos) {
        this.deskPos = pos;
    }

    @Nullable
    public BlockPos getDeskPosValue() {
        return deskPos;
    }

    public void setHeldWorkItem(ItemStack stack) {
        ItemStack copy = stack == null ? ItemStack.EMPTY : stack.copy();
        if (!copy.isEmpty()) {
            copy.setCount(1);
        }
        this.entityData.set(HELD_WORK_ITEM, copy);
    }

    public ItemStack getHeldWorkItem() {
        return this.entityData.get(HELD_WORK_ITEM);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            long dayTime = this.level().getOverworldClockTime() % 24000L;
            boolean night = dayTime >= 12541L && dayTime <= 23458L;

            if (night && !isWorking() && this.entityData.get(ANIM_STATE) != AnimState.SLEEP_END.ordinal()) {
                this.entityData.set(ANIM_STATE, AnimState.SLEEP.ordinal());
            }

            if (!night && this.entityData.get(ANIM_STATE) == AnimState.SLEEP.ordinal()) {
                this.entityData.set(ANIM_STATE, AnimState.IDLE.ordinal());
            }
        }

        if (!this.level().isClientSide() && deskPos != null) {
            double x = deskPos.getX() + 0.5;
            double y = deskPos.getY() + 0.0;
            double z = deskPos.getZ() + 0.5;

            if (this.distanceToSqr(x, y, z) > 1.0D) {
                float yaw = this.getYRot();
                float pitch = this.getXRot();

                this.setPos(x, y, z);
                this.setYRot(yaw);
                this.setXRot(pitch);
            }
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (this.entityData.get(ANIM_STATE) == AnimState.SLEEP.ordinal()) {
            this.entityData.set(ANIM_STATE, AnimState.SLEEP_END.ordinal());
            return InteractionResult.CONSUME;
        }

        if (this.deskPos == null) {
            return InteractionResult.PASS;
        }

        if (this.level().getBlockEntity(this.deskPos) instanceof MenuProvider provider) {
            player.openMenu(provider);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }



    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(
                "controller",
                0,
                state -> {
                    int anim = this.entityData.get(ANIM_STATE);

                    if (anim == AnimState.WORK.ordinal()) {
                        state.setAnimation(RawAnimation.begin().thenLoop(ANIM_WORK));
                    } else if (anim == AnimState.SLEEP.ordinal()) {
                        state.setAnimation(RawAnimation.begin().thenLoop(ANIM_SLEEP));
                    } else if (anim == AnimState.SLEEP_END.ordinal()) {
                        state.setAnimation(RawAnimation.begin().thenPlay(ANIM_SLEEP_END).thenLoop(ANIM_IDLE));
                    } else {
                        state.setAnimation(RawAnimation.begin().thenLoop(ANIM_IDLE));
                    }

                    return PlayState.CONTINUE;
                }
        ));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    @Override
    public boolean isInvulnerableTo(net.minecraft.server.level.ServerLevel level, DamageSource source) {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        output.putInt("Version", DATA_VERSION);

        if (this.deskPos != null) {
            output.putBoolean("HasDeskPos", true);
            output.putInt("DeskX", this.deskPos.getX());
            output.putInt("DeskY", this.deskPos.getY());
            output.putInt("DeskZ", this.deskPos.getZ());
        } else {
            output.putBoolean("HasDeskPos", false);
        }

        output.putInt("AnimState", this.entityData.get(ANIM_STATE));
        output.putBoolean("Working", this.entityData.get(WORKING));

        ItemStack held = this.entityData.get(HELD_WORK_ITEM);
        if (!held.isEmpty()) {
            output.store("HeldWorkItem", ItemStack.CODEC, held);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        int version = input.getIntOr("Version", 0);

        if (input.getBooleanOr("HasDeskPos", false)) {
            this.deskPos = new BlockPos(
                    input.getIntOr("DeskX", 0),
                    input.getIntOr("DeskY", 0),
                    input.getIntOr("DeskZ", 0)
            );
        } else {
            this.deskPos = null;
        }

        int animState = input.getIntOr("AnimState", AnimState.IDLE.ordinal());
        AnimState[] values = AnimState.values();

        if (animState < 0 || animState >= values.length) {
            animState = AnimState.IDLE.ordinal();
        }

        this.entityData.set(ANIM_STATE, animState);
        this.entityData.set(WORKING, input.getBooleanOr("Working", false));

        ItemStack held = input.read("HeldWorkItem", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        this.entityData.set(HELD_WORK_ITEM, held);
    }
}