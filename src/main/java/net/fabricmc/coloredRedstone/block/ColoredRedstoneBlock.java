package net.fabricmc.coloredRedstone.block;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;


@Getter
@Setter
public class ColoredRedstoneBlock extends RedstoneBlock implements DyedBlock {

  private DyeColor color;

  public ColoredRedstoneBlock(Settings settings, DyeColor color) {
    super(settings);
    this.color = color;
  }

  @Override
  public boolean emitsRedstonePower(BlockState state) {
    return false;
  }

  @Override
  public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos,
      Direction direction) {
    BlockPos blockPos2 = pos.offset(direction);
    BlockState connectsToBlockState = world.getBlockState(blockPos2);
    System.out.println(connectsToBlockState.getBlock());
    return super.getWeakRedstonePower(state, world, pos, direction);
  }
}
