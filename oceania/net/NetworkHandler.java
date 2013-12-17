package oceania.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import oceania.entity.EntityOceaniaBoat;
import oceania.util.OUtil;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler implements IPacketHandler
{
	public static NetworkHandler	INSTANCE;
	
	public static final String		MOD_CHANNEL			= "oceania";
	
	public static final int			PACKET_BOAT_POSITION	= 0;
	
	public NetworkHandler()
	{
		INSTANCE = this;
	}
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if (!(player instanceof EntityPlayer)) // Only interested in packets for a real player
			return;
		EntityPlayer ePlayer = (EntityPlayer) player;
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
		DataInputStream in = new DataInputStream(bis);
		
		try
		{
			int pID = in.readInt();
			switch (pID)
			{
				case PACKET_BOAT_POSITION:
				{
					int entID = in.readInt();
					float velX, velY, velZ, posX, posY, posZ, yaw;
					velX = in.readFloat();
					velY = in.readFloat();
					velZ = in.readFloat();
					posX = in.readFloat();
					posY = in.readFloat();
					posZ = in.readFloat();
					yaw = in.readFloat();
					onBoatPosition(ePlayer.worldObj, entID, velX, velY, velZ, posX, posY, posZ, yaw);
					break;
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println("[Oceania] Severe error while receiving packet for Oceania: ");
			ex.printStackTrace();
		}
	}
	
	private void sendPacketData(byte[] data)
	{
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = NetworkHandler.MOD_CHANNEL;
		packet.length = data.length;
		packet.data = data;
		
		switch (OUtil.getSide())
		{
			case SERVER:
			{
				PacketDispatcher.sendPacketToAllPlayers(packet);
				break;
			}
			case CLIENT:
			{
				PacketDispatcher.sendPacketToServer(packet);
				break;
			}
		}
	}
	
	public void sendBoatPosition(int entityID, double velX, double velY, double velZ, double posX, double posY, double posZ, float yaw)
	{
		if (OUtil.getSide() != Side.SERVER)
			return;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bos);
		
		try
		{
			out.writeInt(PACKET_BOAT_POSITION); // Packet ID
			out.writeInt(entityID);
			out.writeFloat((float) velX);
			out.writeFloat((float) velY);
			out.writeFloat((float) velZ);
			out.writeFloat((float) posX);
			out.writeFloat((float) posY);
			out.writeFloat((float) posZ);
			out.writeFloat(yaw);
			sendPacketData(bos.toByteArray());
		}
		catch (Exception ex)
		{
			System.out.println("[Oceania] Severe error while sending packet for Oceania: ");
			ex.printStackTrace();
		}
	}
	
	private void onBoatPosition(World world, int entityID, float velX, float velY, float velZ, float posX, float posY, float posZ, float yaw)
	{
		Entity ent = world.getEntityByID(entityID);
		if (ent instanceof EntityOceaniaBoat)
		{
			EntityOceaniaBoat boat = (EntityOceaniaBoat) ent;
			//boat.setVelocity(velX, velY, velX);
			//boat.setPosition(posX, posY, posZ);
			boat.rotationYaw = yaw;
		}
	}
}
