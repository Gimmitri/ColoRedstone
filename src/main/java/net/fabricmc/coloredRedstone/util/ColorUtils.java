package net.fabricmc.coloredRedstone.util;

import net.minecraft.util.DyeColor;

public class ColorUtils {

  public static int parseDyeColorToHex(DyeColor color) {
    float[] fs = color.getColorComponents();
    int r = (int) (fs[0] * 255.0F);
    int g = (int) (fs[1] * 255.0F);
    int b = (int) (fs[2] * 255.0F);
    return (255 << 24) | (r << 16) | (g << 8) | b;
  }

}
