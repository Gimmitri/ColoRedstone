package net.fabricmc.coloredRedstone.block.wireless;


import net.minecraft.block.entity.BlockEntityType;

public class Receiver extends AbstractWirelessModule {


  public Receiver(BlockEntityType<?> type) {
    super(type);
  }

  @Override
  public boolean isTransmitter() {
    return false;
  }

  @Override
  public boolean isReceiver() {
    return true;
  }

  @Override
  public int getRedstonePower() {
    return 0;
  }
}
