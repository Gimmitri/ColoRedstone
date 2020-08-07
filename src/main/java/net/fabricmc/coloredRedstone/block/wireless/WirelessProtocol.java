package net.fabricmc.coloredRedstone.block.wireless;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class WirelessProtocol {

  private static final WirelessProtocol wirelessProtocol = new WirelessProtocol();

  private static final Set<WirelessModule> receivers = new HashSet<>();
  private static final Set<WirelessModule> transmitters = new HashSet<>();

  private WirelessProtocol() {

  }

  public static WirelessProtocol getInstance() {
    return wirelessProtocol;
  }

  public void register(WirelessModule wirelessModule) {
    if (wirelessModule.isReceiver()) {
      receivers.add(wirelessModule);
    }
    if (wirelessModule.isTransmitter()) {
      transmitters.add(wirelessModule);
    }
  }

  public int getEmittedRedstonePower(int frequency) {
    return transmitters.stream()
        .filter(wirelessModule -> wirelessModule.getFrequency() == frequency)
        .max(Comparator.comparing(WirelessModule::getRedstonePower))
        .map(WirelessModule::getRedstonePower).orElse(0);
  }


}
