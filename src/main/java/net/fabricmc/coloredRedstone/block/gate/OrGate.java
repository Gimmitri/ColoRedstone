package net.fabricmc.coloredRedstone.block.gate;

public class OrGate extends AbstractGateBase {

  public OrGate(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft) {
    if (inputLeft.isConnected() && inputLeft.getRedstonePowerStrength() > 0) {
      return true;
    }
    if (inputOpposite.isConnected() && inputOpposite.getRedstonePowerStrength() > 0) {
      return true;
    }
    if (inputRight.isConnected() && inputRight.getRedstonePowerStrength() > 0) {
      return true;
    }
    return false;
  }

}
