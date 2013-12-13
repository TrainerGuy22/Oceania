package oceania.net;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class NetworkHandler implements IPacketHandler
{
	public static final String MOD_CHANNEL = "oceania";

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
			{
				
				break;
			}
			case SERVER:
			{
				
				
			}
		}
	}
}
