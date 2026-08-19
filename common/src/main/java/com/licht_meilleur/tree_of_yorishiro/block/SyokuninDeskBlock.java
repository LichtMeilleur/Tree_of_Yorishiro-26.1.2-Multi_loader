package com.licht_meilleur.tree_of_yorishiro.block;

import com.licht_meilleur.tree_of_yorishiro.block.entity.SyokuninDeskBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlockEntities;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlocks;
import com.licht_meilleur.tree_of_yorishiro.screen.MenuOpeningBridge;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SyokuninDeskBlock extends BaseEntityBlock {

    public static final MapCodec<SyokuninDeskBlock> CODEC = simpleCodec(SyokuninDeskBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final BlockPos[] NORTH_COLLISIONS = new BlockPos[]{
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, -1),

            new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 1),

            new BlockPos(1, 0, 1),
            new BlockPos(1, 1, 1),
            new BlockPos(1, 2, 1),
    };

    public static void placeCollisionBlocks(Level level, BlockPos pos, BlockState state) {
        removeNearbyCollisionBlocks(level, pos);

        Direction facing = state.getValue(FACING);

        for (BlockPos baseOffset : NORTH_COLLISIONS) {
            BlockPos targetPos = pos.offset(rotateOffset(baseOffset, facing));

            if (targetPos.equals(pos)) {
                continue; // 中心には絶対置かない
            }

            if (level.getBlockState(targetPos).isAir()) {
                level.setBlock(
                        targetPos,
                        ModBlocks.SYOKUNIN_DESK_COLLISION.defaultBlockState(),
                        Block.UPDATE_ALL
                );
            }
        }
    }

    public static void removeCollisionBlocks(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);

        for (BlockPos baseOffset : NORTH_COLLISIONS) {
            BlockPos targetPos = pos.offset(rotateOffset(baseOffset, facing));

            if (level.getBlockState(targetPos).is(ModBlocks.SYOKUNIN_DESK_COLLISION)) {
                level.removeBlock(targetPos, false);
            }
        }
    }

    public SyokuninDeskBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SyokuninDeskBlockEntity(pos, state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.CONSUME;
        }

        BlockEntity blockEntity =
                level.getBlockEntity(pos);

        if (!(blockEntity
                instanceof SyokuninDeskBlockEntity desk)) {
            return InteractionResult.PASS;
        }

        MenuOpeningBridge.openTrade(
                serverPlayer,
                desk
        );

        return InteractionResult.CONSUME;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (!level.isClientSide()) {
            placeCollisionBlocks(level, pos, state);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof SyokuninDeskBlockEntity be) {
                be.discardYorisyokunin();
            }

            removeCollisionBlocks(level, pos, state);
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type
    ) {
        if (level.isClientSide()) return null;

        return type == ModBlockEntities.SYOKUNIN_DESK
                ? (lvl, p, s, be) -> SyokuninDeskBlockEntity.tick(lvl, p, s, (SyokuninDeskBlockEntity) be)
                : null;
    }

    public static BlockPos rotateOffset(BlockPos offset, Direction facing) {
        return switch (facing) {
            case NORTH -> offset;
            case EAST -> new BlockPos(-offset.getZ(), offset.getY(), offset.getX());
            case SOUTH -> new BlockPos(-offset.getX(), offset.getY(), -offset.getZ());
            case WEST -> new BlockPos(offset.getZ(), offset.getY(), -offset.getX());
            default -> offset;
        };
    }

    public static void removeNearbyCollisionBlocks(Level level, BlockPos pos) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos check = pos.offset(dx, dy, dz);

                    if (level.getBlockState(check).is(ModBlocks.SYOKUNIN_DESK_COLLISION)) {
                        level.removeBlock(check, false);
                    }
                }
            }
        }
    }


}