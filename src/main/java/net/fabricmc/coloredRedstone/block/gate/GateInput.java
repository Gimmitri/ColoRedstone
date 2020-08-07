package net.fabricmc.coloredRedstone.block.gate;

import lombok.Data;
import net.minecraft.util.math.Direction;

@Data
public class GateInput {

  private Direction inputDirection;
  private boolean connected;
  private int redstonePowerStrength;

}
