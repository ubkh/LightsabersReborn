package dev.ubayd.lightsabersreborn.item;

import dev.ubayd.lightsabersreborn.block.LightsaberBlockEntity;
import dev.ubayd.lightsabersreborn.block.LightsaberSconce;
import dev.ubayd.lightsabersreborn.registry.LightsaberBlocks;
import dev.ubayd.lightsabersreborn.registry.LightsaberItems;
import dev.ubayd.lightsabersreborn.registry.LightsaberSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;

public class LightsaberHiltItem extends Item {

    public LightsaberHiltItem(Item.Properties properties){
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn){

        ItemStack itemStackIn = playerIn.getItemInHand(handIn);
        Item item = itemStackIn.getItem();
        CompoundTag tag = item.getShareTag(itemStackIn);

        ItemStack red = new ItemStack(LightsaberItems.red_lightsaber.get());
        red.setTag(tag);

        ItemStack blue = new ItemStack(LightsaberItems.blue_lightsaber.get());
        blue.setTag(tag);

        ItemStack green = new ItemStack(LightsaberItems.green_lightsaber.get());
        green.setTag(tag);

        ItemStack yellow = new ItemStack(LightsaberItems.yellow_lightsaber.get());
        yellow.setTag(tag);

        ItemStack purple = new ItemStack(LightsaberItems.purple_lightsaber.get());
        purple.setTag(tag);

        ItemStack white = new ItemStack(LightsaberItems.white_lightsaber.get());
        white.setTag(tag);

        ItemStack dark = new ItemStack(LightsaberItems.darksaber.get());
        dark.setTag(tag);

        if(item == LightsaberItems.red_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, red);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, red);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.blue_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, blue);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, blue);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.green_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, green);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, green);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.yellow_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, yellow);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, yellow);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.purple_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, purple);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, purple);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.white_lightsaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, white);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, white);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.lightsaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }
        if(item == LightsaberItems.darksaber_hilt.get()){
            if (ItemStack.isSameItem(playerIn.getOffhandItem(), itemStackIn)) {
                playerIn.setItemInHand(InteractionHand.OFF_HAND, dark);
            } else {
                playerIn.setItemInHand(InteractionHand.MAIN_HAND, dark);
            }
            worldIn.playSound(null, playerIn.blockPosition(), LightsaberSoundEvents.darksaber_on.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        }

        worldIn.gameEvent(playerIn, GameEvent.EQUIP, playerIn.blockPosition());
        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, itemStackIn);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        Level worldIn = context.getLevel();
//        Player player = context.getPlayer();
//
//        if (player == null) return InteractionResult.PASS;
//
//        InteractionHand hand = context.getHand();
//        Direction clickedFace = context.getClickedFace();
//        BlockPos placePos = context.getClickedPos().relative(clickedFace);
//        ItemStack hiltStack = player.getItemInHand(hand);
//
//        // We only want to place sconces on the sides of blocks, not the top or bottom
//        if (clickedFace.getAxis().isVertical()) {
//            return InteractionResult.PASS;
//        }
//
//        // Check if we can actually place a block here (e.g., we aren't clicking inside a solid block)
//        if (!worldIn.getBlockState(placePos).canBeReplaced()) {
//            return InteractionResult.PASS;
//        }
//
//        // Check if the area is underwater
//        boolean isWaterlogged = worldIn.getFluidState(placePos).getType() == Fluids.WATER;
//
//        // Set the Sconce block, using the clicked face for direction
//        BlockState sconceState = LightsaberBlocks.sconce.get().defaultBlockState()
//                .setValue(LightsaberSconce.FACING, clickedFace)
//                .setValue(LightsaberSconce.DARKSABER, this == LightsaberItems.darksaber_hilt.get())
//                .setValue(BlockStateProperties.WATERLOGGED, isWaterlogged);
//
//        worldIn.setBlock(placePos, sconceState, 3);
//
//        // Play the placement sound
//        worldIn.playSound(null, placePos, sconceState.getSoundType(worldIn, placePos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
//
//        // Get the newly created BlockEntity to store the hilt's NBT data
//        LightsaberBlockEntity sconceTE = (LightsaberBlockEntity) worldIn.getBlockEntity(placePos);
//
//        // Null check the block entity before setting the stack
//        if (sconceTE != null) {
//            sconceTE.setStack(hiltStack.save(new CompoundTag()));
//        }
//
//        // Consume the item if the player is not in creative mode
//        if (!player.isCreative()) {
//            hiltStack.shrink(1);
//        }
//
//        return InteractionResult.sidedSuccess(worldIn.isClientSide);
//    }
}