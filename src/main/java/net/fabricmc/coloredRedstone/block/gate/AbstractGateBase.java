package net.fabricmc.coloredRedstone.block.gate;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class AbstractGateBase extends AbstractRedstoneGateBlock {

  protected static final int updateDelayInternal = 2;

  public AbstractGateBase(Settings settings) {
    super(settings);
  }

  @Override
  protected int getUpdateDelayInternal(BlockState state) {
    return updateDelayInternal;
  }

  @Override
  protected int getPower(World world, BlockPos pos, BlockState state) {
    System.out.println("getPower");
    Direction inputDirectionOpposite = (Direction) state.get(FACING);
    Direction inputDirectionLeft = inputDirectionOpposite.rotateYClockwise();
    Direction inputDirectionRight = inputDirectionOpposite.rotateYCounterclockwise();
    BlockPos blockPosOpposite = pos.offset(inputDirectionOpposite);
    BlockPos blockPosLeft = pos.offset(inputDirectionLeft);
    BlockPos blockPosRight = pos.offset(inputDirectionRight);
    GateInput inputOpposite = getGateInputForSide(world, inputDirectionOpposite, blockPosOpposite);
    GateInput inputLeft = getGateInputForSide(world, inputDirectionLeft, blockPosLeft);
    GateInput inputRight = getGateInputForSide(world, inputDirectionRight, blockPosRight);
    if (!inputLeft.isConnected() && !inputOpposite.isConnected() && !inputRight.isConnected()) {
      return 0;
    }
    return gateFunction(inputRight, inputOpposite, inputLeft) ? 15 : 0;
  }

  protected abstract boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft);

  protected GateInput getGateInputForSide(World world, Direction direction1, BlockPos blockPos) {
    BlockState blockState = world.getBlockState(blockPos);
    GateInput input = new GateInput();
    input.setInputDirection(direction1);
    int redstonePower = 0;
    if (blockState.getBlock() instanceof RedstoneWireBlock) {
      input.setConnected(true);
      redstonePower = blockState.get(RedstoneWireBlock.POWER);
    } else if (world.getEmittedRedstonePower(blockPos, direction1) >= 15) {
      input.setConnected(true);
      redstonePower = 15;
    }
    input.setRedstonePowerStrength(redstonePower);
    return input;
  }

  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, POWERED);
  }

}
