package oceania.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OUtil {
	public static final float	PI_OVER_180	= (float) Math.PI / 180.0f;
	public static final String	COLOR_CODE	= "\u00A7";
	
	public static String colorString(String input) {
		return input.replaceAll("&&", COLOR_CODE);
	}
	
	@SideOnly(Side.CLIENT)
	public static void bindTexture(ResourceLocation textureLocation)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(textureLocation);
	}
	
	public static Side getSide()
	{
		return FMLCommonHandler.instance().getEffectiveSide();
	}
}
