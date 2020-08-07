package net.fabricmc.coloredRedstone.block.sensor;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BlockSensor extends AbstractSensor {

  public BlockSensor(Settings settings) {
    super(settings);
  }

  @Override
  protected int getPower(World world, BlockPos pos, BlockState state) {
    Direction direction = (Direction) state.get(FACING);
    BlockPos blockPos = pos.offset(direction);
    BlockState blockState = world.getBlockState(blockPos);
    return blockState.isAir() ? 0 : 15;
  }

}
