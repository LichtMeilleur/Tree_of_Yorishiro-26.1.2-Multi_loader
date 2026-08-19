package com.licht_meilleur.tree_of_yorishiro.block.entity;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.licht_meilleur.tree_of_yorishiro.block.SyokuninDeskBlock;
import com.licht_meilleur.tree_of_yorishiro.entity.YorisyokuninEntity;
import com.licht_meilleur.tree_of_yorishiro.recipe.YorisyokuninRecipeDef;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlockEntities;
import com.licht_meilleur.tree_of_yorishiro.registry.ModEntities;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeMenuData;
import com.licht_meilleur.tree_of_yorishiro.screen.YorisyokuninTradeScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SyokuninDeskBlockEntity extends BlockEntity
        implements GeoAnimatable, MenuProvider {

    private boolean working = false;
    private int workTicks = 0;

    private ItemStack pendingOutput = ItemStack.EMPTY;
    private UUID syokuninUuid;

    private static final int DATA_VERSION = 1;

    private final SimpleContainer inventory = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            SyokuninDeskBlockEntity.this.setChanged();
        }
    };

    private static final int MAX_WORK_TICKS = 115;

    public SyokuninDeskBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SYOKUNIN_DESK, pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SyokuninDeskBlockEntity be) {
        if (level.isClientSide()) return;

        if (be.working) {
            be.workTicks++;

            if (be.workTicks >= MAX_WORK_TICKS) {
                be.finishWork();
            }

            be.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        }
    }

    // =========================
    // 🔽 NBT（ValueOutput）
    // =========================

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("Version", DATA_VERSION);

        output.putBoolean("Working", working);
        output.putInt("WorkTicks", workTicks);

        if (syokuninUuid != null) {
            output.putString("SyokuninUuid", syokuninUuid.toString());
        }

        if (!pendingOutput.isEmpty()) {
            output.store("PendingOutput", ItemStack.CODEC, pendingOutput);
        }



        for (int i = 0; i < 3; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                output.store("Slot" + i, ItemStack.CODEC, stack);
            }
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        int version = input.getIntOr("Version", 0);

        working = input.getBooleanOr("Working", false);
        workTicks = input.getIntOr("WorkTicks", 0);


        String uuidString = input.getStringOr("SyokuninUuid", "");
        if (!uuidString.isEmpty()) {
            try {
                syokuninUuid = UUID.fromString(uuidString);
            } catch (Exception ignored) {
                syokuninUuid = null;
            }
        } else {
            syokuninUuid = null;
        }

        pendingOutput = input.read("PendingOutput", ItemStack.CODEC)
                .orElse(ItemStack.EMPTY);

        for (int i = 0; i < 3; i++) {
            ItemStack stack = input.read("Slot" + i, ItemStack.CODEC)
                    .orElse(ItemStack.EMPTY);
            inventory.setItem(i, stack);
        }

        if (version == 0) {
            // 旧データ補正が必要になったらここに書く
        }
    }

    public Container getInventory() {
        return inventory;
    }

    public boolean isWorking() {
        return working;
    }

    public int getWorkTicks() {
        return workTicks;
    }

    public void tryStartWork(YorisyokuninRecipeDef recipe) {
        if (level == null || level.isClientSide()) return;
        if (working || recipe == null) return;

        var inputs = java.util.List.of(
                inventory.getItem(0),
                inventory.getItem(1),
                inventory.getItem(2)
        );

        if (!recipe.matches(inputs)) return;

        for (int i = 0; i < 3; i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }
            }
        }

        this.pendingOutput = recipe.getOutput();
        this.working = true;
        YorisyokuninEntity syokunin = getSyokunin();
        if (syokunin != null) {
            syokunin.beginDeskWork(getWorkLookTarget());
            syokunin.setHeldWorkItem(ItemStack.EMPTY);
        }

        setChanged();

        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation NORMAL =
            RawAnimation.begin().thenLoop("animation.normal");

    private static final RawAnimation OPERATION =
            RawAnimation.begin().thenLoop("animation.operation");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("main", 0, state -> {
            state.setAndContinue(NORMAL);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public void spawnYorisyokunin() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;

        if (this.syokuninUuid != null) {
            Entity e = serverLevel.getEntity(this.syokuninUuid);
            if (e instanceof YorisyokuninEntity && e.isAlive()) {
                return;
            }
        }

        YorisyokuninEntity entity = new YorisyokuninEntity(ModEntities.YORISYOKUNIN, serverLevel);

        // 中央に配置（ここ重要）
        double x = this.worldPosition.getX() + 0.5;
        double y = this.worldPosition.getY();
        double z = this.worldPosition.getZ() + 0.5;

        entity.setPos(x, y, z);
        entity.setDeskPos(this.worldPosition);
        entity.setInvulnerable(true);
        entity.setNoGravity(true);

        // 向きは机のFACINGに合わせる
        Direction facing = this.getBlockState().getValue(SyokuninDeskBlock.FACING);
        float yaw = facing.toYRot();

        entity.setYRot(yaw);
        entity.setYHeadRot(yaw);
        entity.setYBodyRot(yaw);

        entity.setDeskPos(this.worldPosition);

        serverLevel.addFreshEntity(entity);

        this.syokuninUuid = entity.getUUID();
        setChanged();
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("よりしょくにん");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new YorisyokuninTradeScreenHandler(syncId, playerInventory, this, this.worldPosition);
    }

    public void discardYorisyokunin() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;
        if (this.syokuninUuid == null) return;

        Entity entity = serverLevel.getEntity(this.syokuninUuid);
        if (entity instanceof YorisyokuninEntity syokunin) {
            syokunin.discard();
        }

        this.syokuninUuid = null;
        setChanged();
    }

    private void finishWork() {
        if (level == null || level.isClientSide()) return;

        YorisyokuninEntity syokunin = getSyokunin();
        if (syokunin != null) {
            syokunin.stopDeskWork();
            syokunin.setHeldWorkItem(ItemStack.EMPTY);
        }

        if (!pendingOutput.isEmpty()) {


            if (syokunin != null) {
                BlockPos dropPos = syokunin.blockPosition().relative(syokunin.getDirection());

                Block.popResource(level, dropPos, pendingOutput.copy());
            } else {
                Block.popResource(level, worldPosition.above(), pendingOutput.copy());
            }
        }

        pendingOutput = ItemStack.EMPTY;
        working = false;
        workTicks = 0;

        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public Vec3 getWorkLookTarget() {
        Direction facing = this.getBlockState().getValue(SyokuninDeskBlock.FACING);

        BlockPos targetPos = this.worldPosition.relative(facing);

        return new Vec3(
                targetPos.getX() + 0.5,
                targetPos.getY() + 1.0,
                targetPos.getZ() + 0.5
        );
    }

    @Nullable
    public YorisyokuninEntity getSyokunin() {
        if (!(level instanceof ServerLevel serverLevel)) return null;
        if (syokuninUuid == null) return null;

        Entity entity = serverLevel.getEntity(syokuninUuid);
        return entity instanceof YorisyokuninEntity syokunin ? syokunin : null;
    }


}