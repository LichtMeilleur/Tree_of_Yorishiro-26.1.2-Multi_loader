package com.licht_meilleur.tree_of_yorishiro.block.entity;

import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import com.licht_meilleur.tree_of_yorishiro.TreeofYorishiroMod;
import com.licht_meilleur.tree_of_yorishiro.block.TreeOfYorishiroPartBlock;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroColor;
import com.licht_meilleur.tree_of_yorishiro.entity.ChibishiroEntity;
import com.licht_meilleur.tree_of_yorishiro.registry.ModBlockEntities;
import com.licht_meilleur.tree_of_yorishiro.registry.ModEntities;
import com.licht_meilleur.tree_of_yorishiro.screen.TreeOfYorishiroScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TreeOfYorishiroPartBlockEntity
        extends BlockEntity
        implements GeoBlockEntity, MenuProvider {

    private final AnimatableInstanceCache cache =
            GeckoLibUtil.createInstanceCache(this);

    private final List<TreeChibishiroData> chibis =
            new ArrayList<>();

    private boolean initialized;
    private int summonCheckCooldown;
    private UUID treeId = UUID.randomUUID();

    private final SimpleContainer trainingInventory =
            new SimpleContainer(4);

    private final SimpleContainer adventureInventory =
            new SimpleContainer(9);

    private ChibishiroColor selectedColor =
            ChibishiroColor.WHITE;

    private static final TagKey<Item> ADVENTURE_COMMON =
            TagKey.create(
                    Registries.ITEM,
                    TreeofYorishiroMod.id(
                            "adventure_common"
                    )
            );

    private static final TagKey<Item> ADVENTURE_UNCOMMON =
            TagKey.create(
                    Registries.ITEM,
                    TreeofYorishiroMod.id(
                            "adventure_uncommon"
                    )
            );

    private static final TagKey<Item> ADVENTURE_RARE =
            TagKey.create(
                    Registries.ITEM,
                    TreeofYorishiroMod.id(
                            "adventure_rare"
                    )
            );

    public TreeOfYorishiroPartBlockEntity(
            BlockPos pos,
            BlockState state
    ) {
        super(
                ModBlockEntities.TREE_PART,
                pos,
                state
        );
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(
                "Yorishiro Tree"
        );
    }

    @Override
    public AbstractContainerMenu createMenu(
            int syncId,
            Inventory playerInventory,
            Player player
    ) {
        return new TreeOfYorishiroScreenHandler(
                syncId,
                playerInventory,
                this.worldPosition
        );
    }


    public static void tick(Level level, BlockPos pos, BlockState state, TreeOfYorishiroPartBlockEntity be) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (level.isClientSide()) return;
        if (be.getPart() != TreeOfYorishiroPartBlock.Part.UNDER) return;

        be.initDefaultChibisIfNeeded();

        if (be.summonCheckCooldown > 0) {
            be.summonCheckCooldown--;
            return;
        }

        be.cleanupOrphanChibis(serverLevel);
        be.summonCheckCooldown = 40;
        be.ensureChibishiros();
        be.tickChibiStates(serverLevel);
    }

    public TreeOfYorishiroPartBlock.Part getPart() {
        if (this.getBlockState().getBlock() instanceof TreeOfYorishiroPartBlock partBlock) {
            return partBlock.getPart();
        }
        return TreeOfYorishiroPartBlock.Part.UNDER;
    }

    public UUID getTreeId() {
        return this.treeId;
    }

    public List<TreeChibishiroData> getChibis() {
        return this.chibis;
    }

    public void initDefaultChibisIfNeeded() {
        if (initialized) return;

        if (!chibis.isEmpty()) {
            initialized = true;
            syncToClient();
            return;
        }

        chibis.add(new TreeChibishiroData(ChibishiroColor.WHITE));
        chibis.add(new TreeChibishiroData(ChibishiroColor.RED));
        chibis.add(new TreeChibishiroData(ChibishiroColor.BLUE));
        chibis.add(new TreeChibishiroData(ChibishiroColor.YELLOW));
        chibis.add(new TreeChibishiroData(ChibishiroColor.PURPLE));

        initialized = true;
        syncToClient();
    }

    public void ensureChibishiros() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;

        cleanupOrphanChibis(serverLevel);

        boolean changed = false;

        for (TreeChibishiroData data : this.chibis) {
            if (data.isAdventuring()) continue;

            ChibishiroEntity found = null;

            if (data.getEntityUuid() != null) {
                Entity entity = serverLevel.getEntity(data.getEntityUuid());

                if (entity instanceof ChibishiroEntity chibi && chibi.isAlive()) {
                    if (this.treeId.equals(chibi.getHomeTreeUuid())
                            && this.worldPosition.equals(chibi.getHomeTreePos())) {
                        found = chibi;
                    } else {
                        data.setEntityUuid(null);
                        changed = true;
                    }
                }
            }

            if (found == null) {
                spawnOneChibi(serverLevel, data);
                changed = true;
            }
        }

        if (changed) {
            syncToClient();
        }
    }

    private void cleanupOrphanChibis(ServerLevel level) {
        List<ChibishiroEntity> list = level.getEntitiesOfClass(
                ChibishiroEntity.class,
                new AABB(this.worldPosition).inflate(48.0D)
        );

        for (ChibishiroEntity chibi : list) {
            BlockPos homePos = chibi.getHomeTreePos();
            UUID homeUuid = chibi.getHomeTreeUuid();

            if (homePos == null || homeUuid == null) {
                chibi.discard();
                continue;
            }

            if (homePos.equals(this.worldPosition) && !homeUuid.equals(this.treeId)) {
                chibi.discard();
                continue;
            }

            if (homeUuid.equals(this.treeId) && !homePos.equals(this.worldPosition)) {
                chibi.discard();
            }
        }
    }

    private void spawnOneChibi(ServerLevel level, TreeChibishiroData data) {
        ChibishiroEntity chibi = new ChibishiroEntity(ModEntities.CHIBISHIRO, level);

        chibi.setColor(data.getColor());
        chibi.setHomeTreePos(this.worldPosition);
        chibi.setHomeTreeUuid(this.treeId);

        double x = this.worldPosition.getX() + 0.5 + (level.getRandom().nextDouble() - 0.5) * 2.0;
        double y = this.worldPosition.getY() + 1.0;
        double z = this.worldPosition.getZ() + 0.5 + (level.getRandom().nextDouble() - 0.5) * 2.0;

        chibi.setPos(x, y, z);
        level.addFreshEntity(chibi);

        data.setEntityUuid(chibi.getUUID());
    }

    public void removeAllChibishiros() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;

        List<ChibishiroEntity> list = serverLevel.getEntitiesOfClass(
                ChibishiroEntity.class,
                new AABB(this.worldPosition).inflate(48.0D)
        );

        for (ChibishiroEntity chibi : list) {
            BlockPos homePos = chibi.getHomeTreePos();
            UUID homeUuid = chibi.getHomeTreeUuid();

            if (homePos == null || homeUuid == null) {
                chibi.discard();
                continue;
            }

            if (homePos.equals(this.worldPosition) || homeUuid.equals(this.treeId)) {
                chibi.discard();
            }
        }

        for (TreeChibishiroData data : this.chibis) {
            data.setEntityUuid(null);
        }

        syncToClient();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        output.putInt("version", 1);
        output.putString("tree_id", this.treeId.toString());
        output.putBoolean("initialized", this.initialized);

        ValueOutput.ValueOutputList list = output.childrenList("chibis");

        for (TreeChibishiroData data : this.chibis) {
            ValueOutput child = list.addChild();
            data.saveToValue(child);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        String treeIdString = input.getStringOr("tree_id", "");
        if (!treeIdString.isEmpty()) {
            try {
                this.treeId = UUID.fromString(treeIdString);
            } catch (Exception ignored) {
                this.treeId = UUID.randomUUID();
            }
        } else {
            this.treeId = UUID.randomUUID();
        }

        this.initialized = input.getBooleanOr("initialized", false);
        this.chibis.clear();

        for (ValueInput child : input.childrenListOrEmpty("chibis")) {
            TreeChibishiroData data = TreeChibishiroData.loadFromValue(child);
            this.chibis.add(data);
        }

        if (!this.chibis.isEmpty()) {
            this.initialized = true;
        }
    }

    public TreeChibishiroData getChibiDataByColor(ChibishiroColor color) {
        if (color == null) return null;

        for (TreeChibishiroData data : this.chibis) {
            if (data.getColor() == color) {
                return data;
            }
        }

        return null;
    }

    public boolean isAnyChibiAdventuring() {
        for (TreeChibishiroData data : this.chibis) {
            if (data.isAdventuring()) return true;
        }
        return false;
    }

    public SimpleContainer getAdventureInventory() {
        return adventureInventory;
    }

    public SimpleContainer getTrainingInventory() {
        return trainingInventory;
    }

    private ChibishiroEntity getChibiEntityByColor(ChibishiroColor color) {
        if (!(this.level instanceof ServerLevel serverLevel)) return null;

        for (ChibishiroEntity chibi : serverLevel.getEntitiesOfClass(
                ChibishiroEntity.class,
                new AABB(this.worldPosition).inflate(48.0D)
        )) {
            if (chibi.getColor() == color
                    && this.worldPosition.equals(chibi.getHomeTreePos())
                    && this.treeId.equals(chibi.getHomeTreeUuid())) {
                return chibi;
            }
        }

        return null;
    }

    public void startTrainingFromScreen(String pageName, int slotIndex, ItemStack consumedStack) {
        TreeChibishiroData data = getChibiDataByColor(this.selectedColor);
        if (data == null) return;

        if (data.isTraining() || data.isAdventuring() || isAnyChibiAdventuring()) return;

        int level = switch (pageName) {
            case "MEAL" -> 1;
            case "STUDY", "EXERCISE", "PLAY" -> switch (slotIndex) {
                case 1 -> 1;
                case 2 -> 2;
                case 3 -> 3;
                default -> 0;
            };
            default -> 0;
        };

        if (level <= 0) return;

        if ("MEAL".equals(pageName)) {
            ChibishiroEntity chibi = getChibiEntityByColor(data.getColor());
            if (chibi != null) {
                chibi.setDisplayFoodStack(consumedStack);
            }
        }

        long now = this.level != null ? this.level.getGameTime() : 0L;

        data.setTraining(true);
        data.setTrainingCompleted(false);
        data.setTrainingType(pageName);
        data.setTrainingLevel(level);

        // テストしやすいように短め。長くしたいなら 5000L に戻してください。
        data.setTrainingEndTick(now + 5000L);

        data.setTrainingLastRewardTick(now);
        data.setSleeping(false);
        data.setSleepingSinceTick(0L);

        syncToClient();
    }

    public void startAdventureFromScreen() {
        if (!(this.level instanceof ServerLevel serverLevel)) return;

        initDefaultChibisIfNeeded();
        ensureChibishiros();

        if (!canStartAdventure()) return;

        long now = serverLevel.getGameTime();

        for (TreeChibishiroData data : this.chibis) {
            data.setTraining(false);
            data.setTrainingCompleted(false);
            data.setTrainingType("");
            data.setTrainingLevel(0);

            data.setAdventuring(true);
            data.setAdventureEndTick(now + 8000L);

            ChibishiroEntity chibi = getChibiEntityByColor(data.getColor());
            if (chibi != null) {
                chibi.startTreasureAndVanish();
                data.setEntityUuid(null);
            }
        }

        syncToClient();
    }

    public void claimAdventureRewards(ServerPlayer player) {
        for (int i = 0; i < this.adventureInventory.getContainerSize(); i++) {
            ItemStack stack = this.adventureInventory.getItem(i);
            if (stack.isEmpty()) continue;

            ItemStack copy = stack.copy();

            if (!player.getInventory().add(copy)) {
                player.drop(copy, false);
            }

            this.adventureInventory.setItem(i, ItemStack.EMPTY);
        }

        syncToClient();
    }

    public void setSelectedColor(ChibishiroColor color) {
        this.selectedColor = color != null ? color : ChibishiroColor.WHITE;
        syncToClient();
    }

    private void tickChibiStates(ServerLevel level) {
        long now = level.getGameTime();
        boolean changed = false;
        boolean needEnsureChibis = false;

        for (TreeChibishiroData data : this.chibis) {

            // ===== トレーニング終了 =====
            if (data.isTraining() && now >= data.getTrainingEndTick()) {
                data.setTraining(false);
                data.setTrainingCompleted(true);
                data.setTrainingEndTick(0L);

                applyTrainingResult(data);

                System.out.println("[Yorishiro] training result "
                        + data.getColor()
                        + " genki=" + data.getGenki()
                        + " kashikosa=" + data.getKashikosa()
                        + " chikara=" + data.getChikara()
                        + " stress=" + data.getStress());

                if ("MEAL".equals(data.getTrainingType())) {
                    ChibishiroEntity chibi = getChibiEntityByColor(data.getColor());
                    if (chibi != null) {
                        chibi.setDisplayFoodStack(ItemStack.EMPTY);
                    }
                }

                changed = true;
            }

            // ===== 冒険終了 =====
            if (data.isAdventuring() && now >= data.getAdventureEndTick()) {
                data.setAdventuring(false);
                data.setAdventureEndTick(0L);

                // 念のため、とっくん系も完全リセット
                data.setTraining(false);
                data.setTrainingCompleted(false);
                data.setTrainingType("");
                data.setTrainingLevel(0);
                data.setTrainingEndTick(0L);
                data.setTrainingLastRewardTick(0L);

                data.setSleeping(false);
                data.setSleepingSinceTick(0L);

                // 冒険後の消耗
                data.setGenki(Math.max(0, data.getGenki() - 20));
                data.setStress(Math.min(100, data.getStress() + 15));

                int rewardCount = getAdventureRewardCount(level, data);

                for (int i = 0; i < rewardCount; i++) {
                    ItemStack reward = rollAdventureReward(level, data);
                    if (!reward.isEmpty()) {
                        addAdventureReward(reward);
                    }
                }

                // entityUuid は冒険開始時に null にしているので、帰還後に再召喚が必要
                needEnsureChibis = true;
                changed = true;
            }
        }

        if (needEnsureChibis) {
            ensureChibishiros();
        }

        if (changed) {
            syncToClient();
        }
    }

    private void addAdventureReward(ItemStack stack) {
        if (stack.isEmpty()) return;

        ItemStack remaining = stack.copy();

        for (int i = 0; i < adventureInventory.getContainerSize(); i++) {
            ItemStack existing = adventureInventory.getItem(i);

            if (existing.isEmpty()) {
                adventureInventory.setItem(i, remaining.copy());
                return;
            }

            if (ItemStack.isSameItemSameComponents(existing, remaining)
                    && existing.getCount() < existing.getMaxStackSize()) {

                int move = Math.min(
                        remaining.getCount(),
                        existing.getMaxStackSize() - existing.getCount()
                );

                existing.grow(move);
                remaining.shrink(move);

                if (remaining.isEmpty()) return;
            }
        }
    }

    private int getAdventureRewardCount(ServerLevel level, TreeChibishiroData data) {
        int chikara = data.getChikara();

        int count = 1;

        if (chikara >= 20) count++;
        if (chikara >= 50) count++;
        if (chikara >= 80 && level.getRandom().nextFloat() < 0.5F) count++;

        return count;
    }

    public boolean isSelectedChibiBusy() {
        TreeChibishiroData data = getChibiDataByColor(this.selectedColor);
        return data != null && (data.isTraining() || data.isAdventuring());
    }

    public boolean canStartTrainingForSelected() {
        TreeChibishiroData data = getChibiDataByColor(this.selectedColor);
        if (data == null) return false;

        if (data.isTraining()) return false;
        if (data.isAdventuring()) return false;
        if (isAnyChibiAdventuring()) return false;

        if (data.getGenki() < 10) return false;
        if (data.getStress() >= 90) return false;

        return true;
    }

    public boolean canStartAdventure() {
        if (this.chibis.isEmpty()) return false;
        if (hasAdventureRewards()) return false;

        for (TreeChibishiroData data : this.chibis) {
            if (data.isTraining()) return false;
            if (data.isAdventuring()) return false;
            if (data.getGenki() < 30) return false;
            if (data.getStress() >= 80) return false;
        }

        return true;
    }

    public boolean hasAdventureRewards() {
        for (int i = 0; i < this.adventureInventory.getContainerSize(); i++) {
            if (!this.adventureInventory.getItem(i).isEmpty()) return true;
        }
        return false;
    }

    private ItemStack rollAdventureReward(ServerLevel level, TreeChibishiroData data) {
        int kashikosa = data.getKashikosa();
        float roll = level.getRandom().nextFloat();

        TagKey<Item> tag;

        if (kashikosa >= 80 && roll < 0.20F) {
            tag = ADVENTURE_RARE;
        } else if (kashikosa >= 40 && roll < 0.50F) {
            tag = ADVENTURE_UNCOMMON;
        } else {
            tag = ADVENTURE_COMMON;
        }

        var optionalTag = BuiltInRegistries.ITEM.get(tag);
        if (optionalTag.isEmpty()) {
            return ItemStack.EMPTY;
        }

        List<Holder<Item>> items = optionalTag.get().stream().toList();
        if (items.isEmpty()) {
            return ItemStack.EMPTY;
        }

        Holder<Item> picked = items.get(level.getRandom().nextInt(items.size()));
        return new ItemStack(picked.value());
    }

    private void applyTrainingResult(TreeChibishiroData data) {
        int level = Math.max(1, data.getTrainingLevel());

        switch (data.getTrainingType()) {
            case "MEAL" -> {
                data.setGenki(Math.min(100, data.getGenki() + 20));
                data.setStress(Math.max(0, data.getStress() - 5));
            }
            case "STUDY" -> {
                data.setKashikosa(data.getKashikosa() + level * 3);
                data.setGenki(Math.max(0, data.getGenki() - level * 3));
                data.setStress(Math.min(100, data.getStress() + level * 4));
            }
            case "EXERCISE" -> {
                data.setChikara(data.getChikara() + level * 3);
                data.setGenki(Math.max(0, data.getGenki() - level * 4));
                data.setStress(Math.min(100, data.getStress() + level * 4));
            }
            case "PLAY" -> {
                data.setGenki(Math.max(0, data.getGenki() - level));
                data.setStress(Math.max(0, data.getStress() - level * 8));
            }
        }
    }

    public Component getSelectedTrainingBlockReason() {
        TreeChibishiroData data = getChibiDataByColor(this.selectedColor);
        if (data == null) return Component.translatable("message.tree_of_yorishiro.no_chibi");

        if (data.isTraining()) return Component.translatable("message.tree_of_yorishiro.already_training");
        if (data.isAdventuring()) return Component.translatable("message.tree_of_yorishiro.already_adventuring");
        if (isAnyChibiAdventuring()) return Component.translatable("message.tree_of_yorishiro.someone_adventuring");
        if (data.getGenki() < 10) return Component.translatable("message.tree_of_yorishiro.low_genki");
        if (data.getStress() >= 90) return Component.translatable("message.tree_of_yorishiro.high_stress");

        return Component.empty();
    }

    public Component getAdventureBlockReason() {
        if (hasAdventureRewards()) return Component.translatable("message.tree_of_yorishiro.rewards_not_claimed");

        for (TreeChibishiroData data : this.chibis) {
            if (data.isTraining()) return Component.translatable("message.tree_of_yorishiro.someone_training");
            if (data.isAdventuring()) return Component.translatable("message.tree_of_yorishiro.already_adventuring");
            if (data.getGenki() < 30) return Component.translatable("message.tree_of_yorishiro.someone_low_genki");
            if (data.getStress() >= 80) return Component.translatable("message.tree_of_yorishiro.someone_high_stress");
        }

        return Component.empty();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void syncToClient() {
        setChanged();

        if (this.level != null && !this.level.isClientSide()) {
            this.level.sendBlockUpdated(
                    this.worldPosition,
                    this.getBlockState(),
                    this.getBlockState(),
                    3
            );
        }
    }
}