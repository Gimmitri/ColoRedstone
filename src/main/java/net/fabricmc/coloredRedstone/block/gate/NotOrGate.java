package net.fabricmc.coloredRedstone.block.gate;

public class NotOrGate extends OrGate {

  public NotOrGate(Settings settings) {
    super(settings);
  }

  @Override
  protected boolean gateFunction(GateInput inputRight, GateInput inputOpposite,
      GateInput inputLeft) {
    return !super.gateFunction(inputRight, inputOpposite, inputLeft);
  }
}
