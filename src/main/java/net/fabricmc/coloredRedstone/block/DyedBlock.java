package net.fabricmc.coloredRedstone.block;

import net.minecraft.util.DyeColor;

public interface DyedBlock {

  public DyeColor getColor();

  public void setColor(DyeColor color);

}
