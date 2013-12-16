package oceania.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.util.DataWatcherTypes;

public class EntitySubmarine extends Entity
{
	private static final float	ENT_WIDTH		= 3.0f;
	private static final float	ENT_LENGTH		= 4.0f;
	private static final float	ENT_HEIGHT		= 2.35f;
	private static final int	INDEX_HEALTH	= 17;
	private static final int	INDEX_OWNER		= 18;
	private static final int	MAX_HEALTH		= 300;
	
	private int					ticksUntilHeal;
	
	public EntitySubmarine(World world)
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
		this.ticksUntilHeal = 0;
	}
	
	public EntitySubmarine(World world, double x, double y, double z)
	{
		this(world);
		this.setPosition(x, y + (double) yOffset, z);
		this.setPreviousPosition(x, y, z);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
	}
	
	@Override
	public void setPosition(double x, double y, double z)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		float hWidth = ENT_WIDTH / 2.0f;
		float hLength = ENT_LENGTH / 2.0f;
		this.boundingBox.setBounds(x - hWidth, y - this.yOffset + ENT_HEIGHT, z - hLength, x + hWidth, y - this.yOffset + ENT_HEIGHT + this.height, z + hLength);
	}
	
	public void setPreviousPosition(double x, double y, double z)
	{
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		this.ticksUntilHeal = 2 * 20; // 2 seconds
		
		if (source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) source.getEntity();
			if (this.getOwner().isEmpty() || player.getEntityName().equalsIgnoreCase(this.getOwner()))
			{
				ItemStack hand = player.getCurrentEquippedItem();
				if (hand == null) // Empty hand
				{
					this.dropItem(Items.itemSubmarine.itemID, 1);
					this.setDead();
				}
			}
		}
		
		return true;
	}
	
	public int getHealth()
	{
		return this.dataWatcher.getWatchableObjectInt(INDEX_HEALTH);
	}
	
	public void setHealth(int health)
	{
		if (health < 0 || health > MAX_HEALTH)
			return;
		this.dataWatcher.updateObject(INDEX_HEALTH, (Integer) health);
	}
	
	public String getOwner()
	{
		return this.dataWatcher.getWatchableObjectString(INDEX_OWNER);
	}
	
	public void setOwner(String owner)
	{
		this.dataWatcher.updateObject(INDEX_OWNER, (String) owner);
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity other)
	{
		return other.boundingBox;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}
	
	@Override
	public boolean canBePushed()
	{
		return true;
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.getHealth() < 0)
			this.setDead();
		
		if (this.getHealth() > MAX_HEALTH)
			this.setHealth(MAX_HEALTH);
	}
	
	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObjectByDataType(INDEX_HEALTH, DataWatcherTypes.INTEGER.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_OWNER, DataWatcherTypes.STRING.ordinal());
		
		setHealth(MAX_HEALTH);
		setOwner("");
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		setHealth(tag.getInteger("subHealth"));
		setOwner(tag.getString("subOwner"));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("subHealth", getHealth());
		tag.setString("subOwner", getOwner());
	}
	
}
