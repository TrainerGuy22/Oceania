package oceania;

import net.minecraftforge.common.Configuration;
import oceania.net.NetworkHandler;
import oceania.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(name = "Oceania", modid = Oceania.MOD_ID, version = Oceania.VERSION)
@NetworkMod(channels = { NetworkHandler.MOD_CHANNEL }, clientSideRequired = true, serverSideRequired = false, packetHandler = NetworkHandler.class)
public class Oceania
{
	// Constants
	public static final String	MOD_ID	= "oceania";
	public static final String	VERSION	= "1.0.0";
	
	// Singletons
	@Instance(Oceania.MOD_ID)
	public static Oceania INSTANCE;
	@SidedProxy(clientSide = "oceania.proxy.ClientProxy", serverSide = "oceania.proxy.CommonProxy")
	public static CommonProxy PROXY;
	public static Configuration CONFIG;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Oceania.CONFIG = new Configuration(event.getSuggestedConfigurationFile());
		Oceania.CONFIG.load();
		
		/** Begin config loading */
		
		/** End config loading */
		
		Oceania.CONFIG.save();
	}
	
}
