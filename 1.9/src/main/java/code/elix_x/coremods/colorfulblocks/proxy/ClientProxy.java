package code.elix_x.coremods.colorfulblocks.proxy;

import java.util.Map;

import code.elix_x.coremods.colorfulblocks.client.color.ColorfulBlocksBlockColor;
import code.elix_x.coremods.colorfulblocks.client.events.LastRenderWorldEvent;
import code.elix_x.coremods.colorfulblocks.color.tool.IColoringTool;
import code.elix_x.coremods.colorfulblocks.gui.GuiSelectColor;
import code.elix_x.excore.utils.reflection.AdvancedReflectionHelper.AField;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IColorfulBlocksProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event){

	}

	@Override
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new LastRenderWorldEvent());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event){
		Map<net.minecraftforge.fml.common.registry.RegistryDelegate<Block>, IBlockColor> blockColorMap = new AField<Map<net.minecraftforge.fml.common.registry.RegistryDelegate<Block>, IBlockColor>>(BlockColors.class, "blockColorMap").setAccessible(true).get(Minecraft.getMinecraft().getBlockColors());
		for(Block block : Block.REGISTRY){
			blockColorMap.put(block.delegate, new ColorfulBlocksBlockColor(blockColorMap.get(block.delegate)));
		}
	}

	@Override
	public void displayGuiSelectColor(ItemStack coloringTool){
		Minecraft.getMinecraft().displayGuiScreen(new GuiSelectColor(((IColoringTool) coloringTool.getItem()).getCurrentColor(coloringTool)));
	}

}
