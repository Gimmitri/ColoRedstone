package net.fabricmc.coloredRedstone.block.sensor;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public abstract class AbstractSensor extends FacingBlock {

  public static final BooleanProperty POWERED;

  static {
    POWERED = Properties.POWERED;
  }

  public AbstractSensor(Settings settings) {
    super(settings);
  }

  public static boolean isSensorBlock(BlockState state) {
    return state.getBlock() instanceof AbstractSensor;
  }

  public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
    boolean bl = (Boolean) state.get(POWERED);
    boolean bl2 = this.hasPower(world, pos, state);
    if (bl && !bl2) {
      world.setBlockState(pos, (BlockState) state.with(POWERED, false), 2);
    } else if (!bl) {
      world.setBlockState(pos, (BlockState) state.with(POWERED, true), 2);
      if (!bl2) {
        world.getBlockTickScheduler()
            .schedule(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
      }
    }
  }

  public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos,
      Direction direction) {
    return state.getWeakRedstonePower(world, pos, direction);
  }

  public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos,
      Direction direction) {
    if (!(Boolean) state.get(POWERED)) {
      return 0;
    } else {
      return state.get(FACING) == direction ? this.getOutputLevel(world, pos, state) : 0;
    }
  }

  public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block,
      BlockPos fromPos, boolean notify) {
    if (state.canPlaceAt(world, pos)) {
      this.updatePowered(world, pos, state);
    } else {
      BlockEntity blockEntity = this.hasBlockEntity() ? world.getBlockEntity(pos) : null;
      dropStacks(state, world, pos, blockEntity);
      world.removeBlock(pos, false);
      Direction[] var8 = Direction.values();
      int var9 = var8.length;

      for (int var10 = 0; var10 < var9; ++var10) {
        Direction direction = var8[var10];
        world.updateNeighborsAlways(pos.offset(direction), this);
      }

    }
  }

  protected void updatePowered(World world, BlockPos pos, BlockState state) {
    boolean bl = (Boolean) state.get(POWERED);
    boolean bl2 = this.hasPower(world, pos, state);
    if (bl != bl2 && !world.getBlockTickScheduler().isTicking(pos, this)) {
      TickPriority tickPriority = TickPriority.HIGH;
      if (this.isTargetNotAligned(world, pos, state)) {
        tickPriority = TickPriority.EXTREMELY_HIGH;
      } else if (bl) {
        tickPriority = TickPriority.VERY_HIGH;
      }

      world.getBlockTickScheduler()
          .schedule(pos, this, this.getUpdateDelayInternal(state), tickPriority);
    }
  }

  protected boolean hasPower(World world, BlockPos pos, BlockState state) {
    return this.getPower(world, pos, state) > 0;
  }

  protected abstract int getPower(World world, BlockPos pos, BlockState state);

  public boolean emitsRedstonePower(BlockState state) {
    return true;
  }

  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
  }

  public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer,
      ItemStack itemStack) {
    if (this.hasPower(world, pos, state)) {
      world.getBlockTickScheduler().schedule(pos, this, 1);
    }

  }

  public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState,
      boolean notify) {
    this.updateTarget(world, pos, state);
  }

  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState,
      boolean moved) {
    if (!moved && !state.isOf(newState.getBlock())) {
      super.onStateReplaced(state, world, pos, newState, moved);
      this.updateTarget(world, pos, state);
    }
  }

  protected void updateTarget(World world, BlockPos pos, BlockState state) {
    Direction direction = (Direction) state.get(FACING);
    BlockPos blockPos = pos.offset(direction.getOpposite());
    world.updateNeighbor(blockPos, this, pos);
    world.updateNeighborsExcept(blockPos, this, direction);
  }

  protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
    return 15;
  }

  public boolean isTargetNotAligned(BlockView world, BlockPos pos, BlockState state) {
    Direction direction = ((Direction) state.get(FACING)).getOpposite();
    BlockState blockState = world.getBlockState(pos.offset(direction));
    return isSensorBlock(blockState) && blockState.get(FACING) != direction;
  }

  protected int getUpdateDelayInternal(BlockState state) {
    return 2;
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, POWERED);
  }
}
