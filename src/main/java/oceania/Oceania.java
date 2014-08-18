package oceania;

import java.io.File;

import lib.enderwizards.sandstone.mod.SandstoneMod;
import lib.enderwizards.sandstone.mod.config.Config;
import oceania.common.CommonProxy;
import oceania.net.NetworkHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "Oceania", modid = Oceania.MOD_ID, version = Oceania.VERSION)
@SandstoneMod(basePackage = "oceania")
public class Oceania {
	
	public static final String MOD_NAME = "Oceania";
	public static final String MOD_ID = "oceania";
	public static final String VERSION = "1.1.0";
	
	@Instance(Oceania.MOD_ID)
	public static Oceania INSTANCE;
	
	@SidedProxy(clientSide = "oceania.client.ClientProxy", serverSide = "oceania.common.CommonProxy")
	public static CommonProxy PROXY;
	
	public static Config CONFIG;
	public static CreativeTabOceania TAB;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CONFIG = Config.toml(new File(event.getModConfigurationDirectory(), Oceania.MOD_ID + ".toml"));
		Oceania.PROXY.init();		
	}
	
}
