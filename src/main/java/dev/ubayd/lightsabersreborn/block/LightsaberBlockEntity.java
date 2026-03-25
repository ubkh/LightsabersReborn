package dev.ubayd.lightsabersreborn.block;

import dev.ubayd.lightsabersreborn.registry.LightsaberBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LightsaberBlockEntity extends BlockEntity {

    // Starts completely empty!
    private ItemStack hiltStack = ItemStack.EMPTY;

    public LightsaberBlockEntity(BlockPos pos, BlockState state) {
        super(LightsaberBlockEntities.SCONCE_BE.get(), pos, state);
    }

    public ItemStack getStack() {
        return this.hiltStack;
    }

    public void setStack(ItemStack stack) {
        this.hiltStack = stack;
        this.setChanged();

        // Tells the client to visually update the block model
        if (this.level != null && !this.level.isClientSide) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("HiltItem")) {
            this.hiltStack = ItemStack.of(compound.getCompound("HiltItem"));
        } else {
            this.hiltStack = ItemStack.EMPTY;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.hiltStack.isEmpty()) {
            compound.put("HiltItem", this.hiltStack.save(new CompoundTag()));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}