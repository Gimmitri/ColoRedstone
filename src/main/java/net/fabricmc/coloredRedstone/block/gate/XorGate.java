package net.fabricmc.coloredRedstone.block.gate;

public class XorGate extends AbstractGateBase {

  public XorGate(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft) {
    boolean active = false;
    if (inputLeft.isConnected() && inputLeft.getRedstonePowerStrength() > 0) {
      active = true;
    }
    if (inputOpposite.isConnected() && inputOpposite.getRedstonePowerStrength() > 0 && !active) {
      active = true;
    } else if (inputOpposite.isConnected() && inputOpposite.getRedstonePowerStrength() > 0
        && active) {
      return false;
    }
    if (inputRight.isConnected() && inputRight.getRedstonePowerStrength() > 0 && !active) {
      active = true;
    } else if (inputRight.isConnected() && inputRight.getRedstonePowerStrength() > 0 && active) {
      return false;
    }

    return active;
  }

}
