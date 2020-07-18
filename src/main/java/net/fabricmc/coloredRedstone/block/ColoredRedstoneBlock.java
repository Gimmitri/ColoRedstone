package net.fabricmc.coloredRedstone.block;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.util.DyeColor;


@Getter
@Setter
public class ColoredRedstoneBlock extends RedstoneBlock implements DyedBlock {

  private DyeColor color;

  public ColoredRedstoneBlock(Settings settings, DyeColor color) {
    super(settings);
    this.color = color;
  }

}
