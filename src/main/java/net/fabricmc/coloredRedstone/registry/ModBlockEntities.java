package net.fabricmc.coloredRedstone.registry;

import lombok.extern.log4j.Log4j2;
import net.fabricmc.coloredRedstone.ColoredRedstone;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Log4j2
public class ModBlockEntities {

  public static void register() {
    log.debug("BlockEntity registration started");
  }

  private static void register(String typeName, BlockEntityType<?> type) {
    Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ColoredRedstone.MODID, typeName),
        type);
  }
}
