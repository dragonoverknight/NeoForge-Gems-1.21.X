package net.dragonoverknight.beginnermod.block.entity.renderer;

import net.dragonoverknight.beginnermod.item.ModItems;
import net.dragonoverknight.beginnermod.screen.custom.GemPolisherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

public class GemPolisherBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged (int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    public GemPolisherBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.GEM_POLISHER.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> GemPolisherBlockEntity.this.progress;
                    case 1 -> GemPolisherBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> GemPolisherBlockEntity.this.progress = value;
                    case 1 -> GemPolisherBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.beginnermod.tool_blocks.gem_polisher");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new GemPolisherMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("gem_polisher_progress", progress);
        pTag.putInt("gem_polisher_max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("gem_polisher_progress");
        maxProgress = pTag.getInt("gem_polisher_max_progress");
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (hasRecipe() && isOutputSlotAvailable()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private boolean hasRecipe() {
        for (DeferredItem<Item> uncut_gem : ModItems.RAW_GEMS) {
            if (itemHandler.getStackInSlot(INPUT_SLOT).is(uncut_gem.get())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutputSlotAvailable() {
        return isOutputSlotEmpty() || isOutputSlotReceivable();
    }

    private boolean isOutputSlotEmpty() {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean isOutputSlotReceivable() {
        Item inputItem = itemHandler.getStackInSlot(INPUT_SLOT).getItem();
        ItemStack outputStack = itemHandler.getStackInSlot(OUTPUT_SLOT);

        // Find the polished item that corresponds to the raw input
        Item polishedGem = getPolishedForRaw(inputItem);
        if (polishedGem == null) return false;

        if (outputStack.isEmpty()) return true;
        if (!outputStack.is(polishedGem)) return false;

        return outputStack.getCount() < outputStack.getMaxStackSize();
    }

    private @Nullable Item getPolishedForRaw(Item raw) {
        // Assumes RAW_GEMS and GEMS are parallel lists in the same order
        for (int i = 0; i < ModItems.RAW_GEMS.size(); i++) {
            if (ModItems.RAW_GEMS.get(i).get() == raw) {
                return ModItems.GEMS.get(i).get();
            }
        }
        return null;
    }

    private void increaseCraftingProgress() {
        this.progress += 1;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void craftItem() {
        Item inputItem = itemHandler.getStackInSlot(INPUT_SLOT).getItem();
        Item polishedGem = getPolishedForRaw(inputItem);
        if (polishedGem == null) return;

        ItemStack outputStack = itemHandler.getStackInSlot(OUTPUT_SLOT);
        if (outputStack.isEmpty()) {
            itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(polishedGem, 1));
            itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
        } else if (outputStack.getCount() < outputStack.getMaxStackSize()) {
            outputStack.grow(1);
            itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
