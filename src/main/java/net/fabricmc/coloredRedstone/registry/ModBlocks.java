package net.fabricmc.coloredRedstone.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import net.fabricmc.coloredRedstone.ColoredRedstone;
import net.fabricmc.coloredRedstone.block.ColoredRedstoneBlock;
import net.fabricmc.coloredRedstone.block.ColoredRedstoneWireBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Log4j2
public class ModBlocks {

  public static List<Block> coloredRedstoneBlock;
  public static List<Block> coloredRedstoneWireBlock;

  public static void register() {
    log.debug("Block registration started");
    coloredRedstoneBlock = registerColoredBlock("colored_redstone_block",
        color -> new ColoredRedstoneBlock(
            FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE), color), true);
    coloredRedstoneWireBlock = registerColoredBlock("colored_redstone_wire_block",
        color -> new ColoredRedstoneWireBlock(FabricBlockSettings.copyOf(
            Blocks.REDSTONE_WIRE), color), true);
  }

  private static void register(String blockName, Block block) {
    register(blockName, block, true);
  }

  private static void register(String blockName, Block block, boolean withBlockItem) {
    Registry.register(Registry.BLOCK, new Identifier(ColoredRedstone.MODID, blockName), block);
    if (withBlockItem) {
      registerBlockItem(blockName, block);
    }
  }

  private static List<Block> registerColoredBlock(String blockName,
      Function<DyeColor, Block> blockFactory, boolean withBlockItem) {
    DyeColor[] colors = DyeColor.values();
    List<Block> coloredBlocks = new ArrayList<>();
    for (DyeColor color : colors) {
      Block block = blockFactory.apply(color);
      coloredBlocks.add(block);
      String blockNameWithColorEnding = blockName + "_" + color.name().toLowerCase();
      log.debug(blockNameWithColorEnding);
      register(blockNameWithColorEnding, block, true);
    }
    return coloredBlocks;
  }

  private static void registerBlockItem(String blockName, Block block) {
    Registry.register(Registry.ITEM, new Identifier(ColoredRedstone.MODID, blockName),
        new BlockItem(block, new Item.Settings().group(
            ColoredRedstone.ITEM_GROUP)));
  }


}

