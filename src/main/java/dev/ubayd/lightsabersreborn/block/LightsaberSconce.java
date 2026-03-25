package dev.ubayd.lightsabersreborn.block;

import dev.ubayd.lightsabersreborn.item.LightsaberHiltItem;
import dev.ubayd.lightsabersreborn.item.LightsaberItem;
import dev.ubayd.lightsabersreborn.registry.LightsaberBlockEntities;
import dev.ubayd.lightsabersreborn.registry.LightsaberItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class LightsaberSconce extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty DARKSABER = BooleanProperty.create("darksaber");
    public static final BooleanProperty HAS_LIGHTSABER = BooleanProperty.create("has_lightsaber");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape SCONCE_LIGHTSABER_NORTH_AABB = Block.box(5.9D, 1.810752D, 8.579952D, 10.1D, 14.554848D, 16.0D);
    protected static final VoxelShape SCONCE_LIGHTSABER_SOUTH_AABB = Block.box(5.9D, 1.810752D, 0.0D, 10.1D, 14.554848D, 7.420048D);
    protected static final VoxelShape SCONCE_LIGHTSABER_WEST_AABB = Block.box(8.579952D, 1.810752D, 5.9D, 16.0D, 14.554848D, 10.1D);
    protected static final VoxelShape SCONCE_LIGHTSABER_EAST_AABB = Block.box(0.0D, 1.810752D, 5.9D, 7.420048D, 14.554848D, 10.1D);

    protected static final VoxelShape SCONCE_DARKSABER_NORTH_AABB = Block.box(5.9D, 1.810752D, 9.437424D, 10.1D, 12.744992D, 16.0D);
    protected static final VoxelShape SCONCE_DARKSABER_SOUTH_AABB = Block.box(5.9D, 1.810752D, 0.0D, 10.1D, 12.744992D, 6.562576D);
    protected static final VoxelShape SCONCE_DARKSABER_WEST_AABB = Block.box(9.437424D, 1.810752D, 5.9D, 16.0D, 12.744992D, 10.1D);
    protected static final VoxelShape SCONCE_DARKSABER_EAST_AABB = Block.box(0.0D, 1.810752D, 5.9D, 6.562576D, 12.744992D, 10.1D);

    public LightsaberSconce(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(DARKSABER, false)
                .setValue(HAS_LIGHTSABER, false) // Starts empty!
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof LightsaberBlockEntity blockEntity) {
            ItemStack handStack = player.getItemInHand(hand);

            boolean isHoldingSaber = handStack.getItem() instanceof LightsaberHiltItem || handStack.getItem() instanceof LightsaberItem;

            if (!state.getValue(HAS_LIGHTSABER) && isHoldingSaber) {
                if (!level.isClientSide) {
                    blockEntity.setStack(handStack.copy().split(1));
                    if (!player.isCreative()) handStack.shrink(1);

                    boolean isDarksaber = handStack.getItem() == LightsaberItems.darksaber_hilt.get() || handStack.getItem() == LightsaberItems.darksaber.get();
                    level.setBlock(pos, state.setValue(HAS_LIGHTSABER, true).setValue(DARKSABER, isDarksaber), 3);

                    level.playSound(null, pos, net.minecraft.sounds.SoundEvents.LANTERN_PLACE, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            else if (state.getValue(HAS_LIGHTSABER) && handStack.isEmpty()) {
                if (!level.isClientSide) {
                    player.setItemInHand(hand, blockEntity.getStack().copy());
                    blockEntity.setStack(ItemStack.EMPTY);
                    level.setBlock(pos, state.setValue(HAS_LIGHTSABER, false).setValue(DARKSABER, false), 3);
                    level.playSound(null, pos, net.minecraft.sounds.SoundEvents.LANTERN_BREAK, net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        // If the Sconce is empty, it uses the generic Lightsaber shape collision
        if (state.getValue(DARKSABER)) {
            return switch (state.getValue(FACING)) {
                case WEST -> SCONCE_DARKSABER_WEST_AABB;
                case SOUTH -> SCONCE_DARKSABER_SOUTH_AABB;
                case NORTH -> SCONCE_DARKSABER_NORTH_AABB;
                default -> SCONCE_DARKSABER_EAST_AABB;
            };
        } else {
            return switch (state.getValue(FACING)) {
                case WEST -> SCONCE_LIGHTSABER_WEST_AABB;
                case SOUTH -> SCONCE_LIGHTSABER_SOUTH_AABB;
                case NORTH -> SCONCE_LIGHTSABER_NORTH_AABB;
                default -> SCONCE_LIGHTSABER_EAST_AABB;
            };
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos pos, @NotNull BlockPos fromPos) {
        if (state.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(state, facing, facingState, worldIn, pos, fromPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return LightsaberBlockEntities.SCONCE_BE.get().create(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, DARKSABER, HAS_LIGHTSABER, WATERLOGGED);
    }
}