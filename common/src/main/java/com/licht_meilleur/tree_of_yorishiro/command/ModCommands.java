package com.licht_meilleur.tree_of_yorishiro.command;

import com.licht_meilleur.tree_of_yorishiro.block.entity
        .TreeOfYorishiroPartBlockEntity;
import com.licht_meilleur.tree_of_yorishiro.entity
        .ChibishiroEntity;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public final class ModCommands {

    private ModCommands() {
    }

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {
        dispatcher.register(
                Commands.literal(
                                "yorishiro_cleanup_orphan"
                        )
                        .requires(source -> {
                            var player =
                                    source.getPlayer();

                            return player != null
                                    && source.getServer()
                                    .getPlayerList()
                                    .isOp(
                                            player.nameAndId()
                                    );
                        })
                        .executes(context ->
                                cleanupOrphans(
                                        context.getSource()
                                )
                        )
        );
    }

    private static int cleanupOrphans(
            CommandSourceStack source
    ) {
        ServerLevel level =
                source.getLevel();

        int removed = 0;

        AABB searchArea =
                new AABB(
                        -30_000_000,
                        level.getMinY(),
                        -30_000_000,
                        30_000_000,
                        level.getMaxY(),
                        30_000_000
                );

        for (ChibishiroEntity chibi :
                level.getEntitiesOfClass(
                        ChibishiroEntity.class,
                        searchArea
                )) {

            var homeTreePos =
                    chibi.getHomeTreePos();

            boolean remove;

            if (homeTreePos == null) {
                remove = true;
            } else {
                BlockEntity blockEntity =
                        level.getBlockEntity(
                                homeTreePos
                        );

                remove =
                        !(blockEntity
                                instanceof
                                TreeOfYorishiroPartBlockEntity);
            }

            if (!remove) {
                continue;
            }

            chibi.remove(
                    Entity.RemovalReason.DISCARDED
            );

            removed++;
        }

        int finalRemoved = removed;

        source.sendSuccess(
                () -> Component.literal(
                        "Removed orphan chibishiro: "
                                + finalRemoved
                ),
                true
        );

        return finalRemoved;
    }
}