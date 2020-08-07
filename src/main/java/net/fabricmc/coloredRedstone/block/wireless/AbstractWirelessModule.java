package net.fabricmc.coloredRedstone.block.wireless;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

@Getter
@Setter
public abstract class AbstractWirelessModule extends BlockEntity implements WirelessModule {

  protected final static WirelessProtocol wirelessProtocol = WirelessProtocol.getInstance();
  protected int frequency;

  public AbstractWirelessModule(BlockEntityType<?> type) {
    super(type);
    wirelessProtocol.register(this);
  }
}
