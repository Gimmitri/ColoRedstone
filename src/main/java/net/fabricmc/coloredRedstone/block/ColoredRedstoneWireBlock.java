package net.fabricmc.coloredRedstone.block;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.util.DyeColor;

@Getter
@Setter
public class ColoredRedstoneWireBlock extends RedstoneWireBlock implements DyedBlock {

  private DyeColor color;

  public ColoredRedstoneWireBlock(Settings settings, DyeColor color) {
    super(settings);
    this.setDefaultState(this.getStateManager().getDefaultState()
        .with(RedstoneWireBlock.WIRE_CONNECTION_NORTH, WireConnection.NONE)
        .with(RedstoneWireBlock.WIRE_CONNECTION_EAST, WireConnection.NONE)
        .with(RedstoneWireBlock.WIRE_CONNECTION_SOUTH, WireConnection.NONE)
        .with(RedstoneWireBlock.WIRE_CONNECTION_WEST, WireConnection.NONE)
        .with(RedstoneWireBlock.POWER, 0));

    this.color = color;
  }
}
