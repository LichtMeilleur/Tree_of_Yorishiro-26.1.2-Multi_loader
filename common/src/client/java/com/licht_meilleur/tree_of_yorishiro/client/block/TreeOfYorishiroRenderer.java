package com.licht_meilleur.tree_of_yorishiro.client.block;

import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.licht_meilleur.tree_of_yorishiro.block.TreeOfYorishiroPartBlock;
import com.licht_meilleur.tree_of_yorishiro.block.entity.TreeOfYorishiroPartBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TreeOfYorishiroRenderer<R extends BlockEntityRenderState & GeoRenderState>
        extends GeoBlockRenderer<TreeOfYorishiroPartBlockEntity, R> {

    public TreeOfYorishiroRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx, new TreeOfYorishiroGeoModel());
    }
    @Override
    public void addRenderData(
            TreeOfYorishiroPartBlockEntity animatable,
            @Nullable Void relatedObject,
            R renderState,
            float partialTick
    ) {
        super.addRenderData(animatable, relatedObject, renderState, partialTick);

        renderState.addGeckolibData(
                TreeOfYorishiroRenderTickets.TREE_PART,
                animatable.getPart()
        );

    }

    /*
     * NeoForgeでは描画カリング範囲として
     * 自動的に呼ばれる。
     *
     * Fabricではこのメソッドを持たないため、
     * 通常の追加メソッドとして扱われる。
     *
     * 両方でコンパイルするため
     * @Overrideは付けない。
     */
    @SuppressWarnings("unused")
    public AABB getRenderBoundingBox(
            TreeOfYorishiroPartBlockEntity blockEntity
    ) {
        BlockPos pos =
                blockEntity.getBlockPos();

        return new AABB(
                pos.getX() - 32.0D,
                pos.getY() - 8.0D,
                pos.getZ() - 32.0D,

                pos.getX() + 33.0D,
                pos.getY() + 57.0D,
                pos.getZ() + 33.0D
        );
    }

    /*
     * Fabric側の画面外カリング対策。
     * NeoForge側でも補助的に機能する。
     */
    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 192;
    }
}