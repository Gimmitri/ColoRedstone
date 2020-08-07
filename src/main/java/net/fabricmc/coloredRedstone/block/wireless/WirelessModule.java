package net.fabricmc.coloredRedstone.block.wireless;

public interface WirelessModule {

  int getFrequency();

  void setFrequency(int frequency);

  boolean isTransmitter();

  boolean isReceiver();

  int getRedstonePower();

}
