package net.fabricmc.coloredRedstone;

import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.coloredRedstone.registry.ModBlocks;
import net.fabricmc.coloredRedstone.registry.ModColorProviders;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class ColoredRedstoneClient implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    initializeBlockRenderLayerMap(ModBlocks.coloredRedstoneWireBlock);
    register();
  }

  private void register() {
    ModColorProviders.register();
  }


  private void initializeBlockRenderLayerMap(List<Block> blockList) {
    blockList
        .forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
  }
}
