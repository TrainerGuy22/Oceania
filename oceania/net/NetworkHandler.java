package oceania.net;

import net.minecraft.entity.player.EntityPlayer;
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
		if (!(player instanceof EntityPlayer)) // Only interested in packets for a real player
			return;
		EntityPlayer ePlayer = (EntityPlayer) player;
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
			{
				onClientPacket(packet, ePlayer);
				break;
			}
			case SERVER:
			{
				onServerPacket(packet, ePlayer);
				break;
			}
		}
	}
	
	public void onClientPacket(Packet250CustomPayload packet, EntityPlayer player)
	{
		
	}
	
	public void onServerPacket(Packet250CustomPayload packet, EntityPlayer player)
	{
		
	}
}
