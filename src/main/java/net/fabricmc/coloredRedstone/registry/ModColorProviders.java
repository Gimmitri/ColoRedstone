package net.fabricmc.coloredRedstone.registry;

import lombok.extern.log4j.Log4j2;
import net.fabricmc.coloredRedstone.block.DyedBlock;
import net.fabricmc.coloredRedstone.util.ColorUtils;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.DyeColor;

@Log4j2
public class ModColorProviders {

  public static void register() {
    ModBlocks.coloredRedstoneWireBlock
        .forEach(block -> registerColoredBlock(block, ((DyedBlock) block).getColor(), true));
    ModBlocks.coloredRedstoneBlock
        .forEach(block -> registerColoredBlock(block, ((DyedBlock) block).getColor(), true));
  }

  public static void registerColoredBlock(Block block, DyeColor color, boolean withBlockItem) {
    ColorProviderRegistry.BLOCK
        .register((state, view, pos, tintIndex) -> ColorUtils.parseDyeColorToHex(color), block);
    if (withBlockItem) {
      registerColoredItem(block, color);
    }
  }

  public static void registerColoredItem(ItemConvertible item, DyeColor color) {
    ColorProviderRegistry.ITEM
        .register((stack, tintIndex) -> ColorUtils.parseDyeColorToHex(color), item);
  }

}
