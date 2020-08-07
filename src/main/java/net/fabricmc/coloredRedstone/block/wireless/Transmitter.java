package net.fabricmc.coloredRedstone.block.wireless;

import net.minecraft.block.entity.BlockEntityType;

public class Transmitter extends AbstractWirelessModule {

  public Transmitter(BlockEntityType<?> type) {
    super(type);
  }

  @Override
  public boolean isTransmitter() {
    return false;
  }

  @Override
  public boolean isReceiver() {
    return false;
  }

  @Override
  public int getRedstonePower() {
    return 0;
  }
}
