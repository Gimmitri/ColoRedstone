package net.fabricmc.coloredRedstone;

import lombok.extern.log4j.Log4j2;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.coloredRedstone.registry.ModBlockEntities;
import net.fabricmc.coloredRedstone.registry.ModBlocks;
import net.fabricmc.coloredRedstone.registry.ModEntities;
import net.fabricmc.coloredRedstone.registry.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Log4j2
public class ColoredRedstone implements ModInitializer {

  public static final String MODID = "colored_redstone";
  public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder
      .build(new Identifier(MODID, "group"), () -> new ItemStack(Blocks.REDSTONE_BLOCK));

  @Override
  public void onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    log.info(MODID + " initialization started");
    ModBlocks.register();
    ModBlockEntities.register();
    ModItems.register();
    ModEntities.register();
  }
}
