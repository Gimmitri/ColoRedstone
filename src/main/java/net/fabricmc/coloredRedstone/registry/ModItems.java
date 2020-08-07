package net.fabricmc.coloredRedstone.registry;

import lombok.extern.log4j.Log4j2;
import net.fabricmc.coloredRedstone.ColoredRedstone;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Log4j2
public class ModItems {

  public static Item AND_LOGIC_CORE = new Item(new Settings().group(ColoredRedstone.ITEM_GROUP));
  public static Item OR_LOGIC_CORE = new Item(new Settings().group(ColoredRedstone.ITEM_GROUP));
  public static Item XOR_LOGIC_CORE = new Item(new Settings().group(ColoredRedstone.ITEM_GROUP));
  public static Item NOT_LOGIC_CORE = new Item(new Settings().group(ColoredRedstone.ITEM_GROUP));

  public static void register() {
    log.debug("Item registration started");
    register("and_logic_core", AND_LOGIC_CORE);
    register("or_logic_core", OR_LOGIC_CORE);
    register("xor_logic_core", XOR_LOGIC_CORE);
    register("not_logic_core", NOT_LOGIC_CORE);

  }

  private static void register(String path, Item item) {
    Registry.register(Registry.ITEM, new Identifier(ColoredRedstone.MODID, path), item);
  }

  private static Item simpleItem() {
    return new Item(new Item.Settings().group(ColoredRedstone.ITEM_GROUP));
  }


}
