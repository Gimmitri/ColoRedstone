package net.fabricmc.coloredRedstone.block.gate;

public class NotGate extends AbstractGateBase {

  public NotGate(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft) {
    return inputOpposite.getRedstonePowerStrength() == 0;
  }
}
