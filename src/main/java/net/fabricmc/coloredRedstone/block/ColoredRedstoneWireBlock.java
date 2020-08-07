package net.fabricmc.coloredRedstone.block;

import static net.minecraft.client.render.WorldRenderer.DIRECTIONS;

import java.util.Iterator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@Log4j2
@Getter
@Setter
public class ColoredRedstoneWireBlock extends RedstoneWireBlock implements DyedBlock {

  private DyeColor color;
  private boolean wiresGivePower = true;


  public ColoredRedstoneWireBlock(Settings settings, DyeColor color) {
    super(settings);
    this.setDefaultState(this.getStateManager().getDefaultState()
        .with(ColoredRedstoneWireBlock.WIRE_CONNECTION_NORTH, WireConnection.NONE)
        .with(ColoredRedstoneWireBlock.WIRE_CONNECTION_EAST, WireConnection.NONE)
        .with(ColoredRedstoneWireBlock.WIRE_CONNECTION_SOUTH, WireConnection.NONE)
        .with(ColoredRedstoneWireBlock.WIRE_CONNECTION_WEST, WireConnection.NONE)
        .with(ColoredRedstoneWireBlock.POWER, 0));

    this.color = color;
  }

  protected static boolean connectsTo(BlockState thisBlockState, BlockState connectsToBlockState) {
    return connectsTo(thisBlockState, connectsToBlockState, (Direction) null);
  }

  protected static boolean connectsTo(BlockState thisBlockState, BlockState connectsToBlockState,
      Direction dir) {
    if (isColoredRedstoneWireBlock(thisBlockState) && isColoredRedstoneWireBlock(
        connectsToBlockState)) {
      return hasSameColor(thisBlockState, connectsToBlockState);
    } else if (connectsToBlockState.isOf(Blocks.REDSTONE_WIRE)) {
      return false;
    } else if (connectsToBlockState.isOf(Blocks.REPEATER)) {
      Direction direction = (Direction) connectsToBlockState.get(RepeaterBlock.FACING);
      return direction == dir || direction.getOpposite() == dir;
    } else if (connectsToBlockState.isOf(Blocks.OBSERVER)) {
      return dir == connectsToBlockState.get(ObserverBlock.FACING);
    } else {
      return connectsToBlockState.emitsRedstonePower() && dir != null;
    }
  }

  protected static boolean isColoredRedstoneWireBlock(BlockState state) {
    return isColoredRedstoneWireBlock(state.getBlock());
  }

  protected static boolean isColoredRedstoneWireBlock(Block block) {
    return block instanceof ColoredRedstoneWireBlock;
  }

  protected static boolean hasSameColor(BlockState state1, BlockState state2) {
    return hasSameColor(state1.getBlock(), state2.getBlock());
  }

  protected static boolean hasSameColor(Block block1, Block block2) {
    if (isColoredRedstoneWireBlock(block1) && isColoredRedstoneWireBlock(block2)) {
      return hasSameColor((ColoredRedstoneWireBlock) block1, (ColoredRedstoneWireBlock) block2);
    }
    return false;
  }

  protected static boolean hasSameColor(ColoredRedstoneWireBlock block1,
      ColoredRedstoneWireBlock block2) {
    return block1.getColor() == block2.getColor();
  }

  @Override
  protected WireConnection method_27841(BlockView blockView, BlockPos blockPos, Direction direction,
      boolean isBlockAboveNonSolid) {
    return getWireConnection(blockView, blockPos, direction, isBlockAboveNonSolid);
  }

  protected WireConnection getWireConnection(BlockView blockView, BlockPos blockPos,
      Direction direction, boolean isBlockAboveNonSolid) {
    BlockState thisBlockState = blockView.getBlockState(blockPos);
    BlockPos blockPos2 = blockPos.offset(direction);
    BlockState blockState2 = blockView.getBlockState(blockPos2);
    WireConnection wireConnection = null;
    if (isBlockAboveNonSolid) {
      wireConnection = getWireConnectionUp(blockView, direction, thisBlockState, blockPos2,
          blockState2);
    }
    if (wireConnection == null) {
      wireConnection = getWireConnectionSide(blockView, direction, thisBlockState, blockPos2,
          blockState2);
    }
    return wireConnection;
  }

  protected WireConnection getWireConnectionSide(BlockView blockView, Direction direction,
      BlockState thisBlockState, BlockPos blockPos2, BlockState blockState2) {
    boolean connectsInDirection = connectsTo(thisBlockState, blockState2, direction);
    boolean solidBlock = blockState2.isSolidBlock(blockView, blockPos2);
    boolean connectsDown = connectsTo(thisBlockState, blockView.getBlockState(blockPos2.down()));
    return !connectsInDirection && (!solidBlock || !connectsDown) ? WireConnection.NONE
        : WireConnection.SIDE;
  }

  protected WireConnection getWireConnectionUp(BlockView blockView, Direction direction,
      BlockState thisBlockState, BlockPos blockPos2, BlockState blockState2) {
    boolean canRunOnTop = this.canRunOnTop(blockView, blockPos2, blockState2);
    if (canRunOnTop && connectsTo(thisBlockState, blockView.getBlockState(blockPos2.up()))) {
      if (blockState2.isSideSolidFullSquare(blockView, blockPos2, direction.getOpposite())) {
        return WireConnection.UP;
      }
      return WireConnection.SIDE;
    }
    return null;
  }

  private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
    return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
  }

  @Override
  protected int method_27842(World world, BlockPos blockPos) {
    this.wiresGivePower = false;
    int i = this.getReceivedRedstonePower(world, blockPos);
    this.wiresGivePower = true;
    int j = 0;
    if (i < 15) {
      Iterator var5 = Direction.Type.HORIZONTAL.iterator();

      while (true) {
        while (var5.hasNext()) {
          Direction direction = (Direction) var5.next();
          BlockPos blockPos2 = blockPos.offset(direction);
          BlockState blockState = world.getBlockState(blockPos2);
          j = Math.max(j, this.increasePower(blockState));
          BlockPos blockPos3 = blockPos.up();
          if (blockState.isSolidBlock(world, blockPos2) && !world.getBlockState(blockPos3)
              .isSolidBlock(world, blockPos3)) {
            j = Math.max(j, this.increasePower(world.getBlockState(blockPos2.up())));
          } else if (!blockState.isSolidBlock(world, blockPos2)) {
            j = Math.max(j, this.increasePower(world.getBlockState(blockPos2.down())));
          }
        }

        return Math.max(i, j - 1);
      }
    } else {
      return Math.max(i, j - 1);
    }
  }

  protected int getReceivedRedstonePower(World world, BlockPos pos) {
    Direction[] var3 = DIRECTIONS;

    for (Direction direction : var3) {
      BlockPos blockPos = pos.offset(direction);
      BlockState blockState = world.getBlockState(blockPos);
      int j = world.getEmittedRedstonePower(blockPos, direction);

      if (isColoredRedstoneWireBlock(blockState)
          && ((ColoredRedstoneWireBlock) blockState.getBlock()).getColor() != this.getColor()) {
        j = 0;
      } else if ((blockState.getBlock() instanceof RedstoneWireBlock)) {
        j = 0;
      } else if (blockState.getBlock() instanceof ColoredRedstoneBlock
          && ((ColoredRedstoneBlock) blockState.getBlock()).getColor() != this.getColor()) {
        j = 0;
      }
      if (j >= 15) {
        return 15;
      }
    }
    return 0;
  }

  @Override
  public int getWeakRedstonePower(BlockState thisBlockState, BlockView blockView, BlockPos blockPos,
      Direction direction) {
    int redstonePower = 0;
    if (this.wiresGivePower && direction != Direction.DOWN) {
      BlockPos blockPos2 = blockPos.offset(direction);
      BlockState connectsToBlockState = blockView.getBlockState(blockPos2);

      if (isColoredRedstoneWireBlock(thisBlockState) && isColoredRedstoneWireBlock(
          connectsToBlockState) && !hasSameColor(thisBlockState, connectsToBlockState)) {
        redstonePower = 0;
      } else if (isColoredRedstoneWireBlock(thisBlockState) && isColoredRedstoneWireBlock(
          connectsToBlockState) && hasSameColor(thisBlockState, connectsToBlockState)) {

        redstonePower = super.getWeakRedstonePower(thisBlockState, blockView, blockPos, direction);
      }
    }
    return redstonePower;
  }

  private int increasePower(BlockState state) {
    return state.isOf(this) ? (Integer) state.get(POWER) : 0;
  }
}
