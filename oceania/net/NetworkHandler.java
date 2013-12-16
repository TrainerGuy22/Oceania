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
import oceania.entity.EntitySubmarine;
import oceania.util.OUtil;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler implements IPacketHandler
{
	public static NetworkHandler	INSTANCE;
	
	public static final String		MOD_CHANNEL			= "oceania";
	
	public static final int			PACKET_SUB_VELOCITY	= 0;
	
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
				case PACKET_SUB_VELOCITY:
				{
					int entID = in.readInt();
					double velX, velY, velZ;
					float yawSpeed;
					velX = in.readDouble();
					velY = in.readDouble();
					velZ = in.readDouble();
					yawSpeed = in.readFloat();
					onSubmarineVelocity(ePlayer.worldObj, entID, velX, velY, velZ, yawSpeed);
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
	
	public void sendSubmarineVelocity(int entityID, double velX, double velY, double velZ, float yawSpeed)
	{
		if (OUtil.getSide() != Side.SERVER)
			return;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bos);
		
		try
		{
			out.writeInt(PACKET_SUB_VELOCITY); // Packet ID
			out.writeInt(entityID);
			out.writeDouble(velX);
			out.writeDouble(velY);
			out.writeDouble(velZ);
			out.writeFloat(yawSpeed);
			sendPacketData(bos.toByteArray());
		}
		catch (Exception ex)
		{
			System.out.println("[Oceania] Severe error while sending packet for Oceania: ");
			ex.printStackTrace();
		}
	}
	
	private void onSubmarineVelocity(World world, int entityID, double velX, double velY, double velZ, float yawSpeed)
	{
		Entity ent = world.getEntityByID(entityID);
		if (ent instanceof EntitySubmarine)
		{
			EntitySubmarine sub = (EntitySubmarine) ent;
			sub.motionX = velX;
			sub.motionY = velY;
			sub.motionZ = velZ;
			sub.velTurning = yawSpeed;
		}
	}
}
