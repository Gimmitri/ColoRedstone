package net.fabricmc.coloredRedstone.block.gate;

public class AndGate extends AbstractGateBase {

  public AndGate(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft) {
    if (inputLeft.isConnected() && inputLeft.getRedstonePowerStrength() <= 0) {
      return false;
    }
    if (inputOpposite.isConnected() && inputOpposite.getRedstonePowerStrength() <= 0) {
      return false;
    }
    if (inputRight.isConnected() && inputRight.getRedstonePowerStrength() <= 0) {
      return false;
    }
    return true;
  }

}
