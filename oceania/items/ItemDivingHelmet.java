package oceania.items;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import oceania.OUtil;
import oceania.Oceania;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDivingHelmet extends ItemArmor implements ISpecialArmor
{	
	private ArmorProperties	_armorProperties;
	
	public ItemDivingHelmet(int itemID)
	{
		super(itemID, EnumArmorMaterial.IRON, 2, 0);
		setCreativeTab(Oceania.CREATIVE_TAB);
		setMaxDamage(1024);
		setMaxStackSize(1);
		setUnlocalizedName("itemDivingHelmet");
		this._armorProperties = new ArmorProperties(2, 0.5, 4);
	}
	
	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{
			itemStack.setTagCompound(new NBTTagCompound());
			itemStack.getTagCompound().setInteger("inWater", 0);
			itemStack.getTagCompound().setBoolean("hadEffect", false);
			itemStack.getTagCompound().setInteger("effectTime", 0);
		}
		
		if (player.isInsideOfMaterial(Material.water))
		{
			if (player.isPotionActive(Potion.nightVision) && itemStack.getTagCompound().getInteger("inWater") <= 0)
			{
				itemStack.getTagCompound().setBoolean("hadEffect", true);
				itemStack.getTagCompound().setInteger("effectTime", player.getActivePotionEffect(Potion.nightVision).duration);
			}
			player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 250, 1));
			int ticksInWater = itemStack.getTagCompound().getInteger("inWater");
			if (ticksInWater < 20 * 10 /* 10 seconds */)
			{
				ticksInWater++;
				itemStack.getTagCompound().setInteger("inWater", ticksInWater);
				player.setAir(300);
			}
		}
		else
		{
			if (itemStack.getTagCompound().getBoolean("hadEffect"))
			{
				itemStack.getTagCompound().setBoolean("hadEffect", false);
				int dur = itemStack.getTagCompound().getInteger("effectTime");
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, dur, 1));
				itemStack.getTagCompound().setInteger("effectTime", 0);
			}
			else
			{
				if (itemStack.getTagCompound().getInteger("inWater") > 0)
				{
					ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
					for (Object ef : player.getActivePotionEffects())
					{
						if (((PotionEffect) ef).getPotionID() != Potion.nightVision.id)
							effects.add((PotionEffect) ef);
					}
					player.clearActivePotions();
					for (PotionEffect ef : effects)
						player.addPotionEffect(ef);
				}
			}
			itemStack.getTagCompound().setInteger("inWater", 0);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.itemIcon = registry.registerIcon(Oceania.MOD_ID + ":divingHelmet");
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return stack.itemID == this.itemID && armorType == 0; // Helmet
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return "oceania:textures/models/armor/divingHelmet.png";
	}
	
	/**
	 * Retrieves the modifiers to be used when calculating armor damage.
	 * 
	 * Armor will higher priority will have damage applied to them before
	 * lower priority ones. If there are multiple pieces of armor with the
	 * same priority, damage will be distributed between them based on there
	 * absorption ratio.
	 * 
	 * @param player
	 *            The entity wearing the armor.
	 * @param armor
	 *            The ItemStack of the armor item itself.
	 * @param source
	 *            The source of the damage, which can be used to alter armor
	 *            properties based on the type or source of damage.
	 * @param damage
	 *            The total damage being applied to the entity
	 * @param slot
	 *            The armor slot the item is in.
	 * @return A ArmorProperties instance holding information about how the armor effects damage.
	 */
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
		return this._armorProperties;
	}
	
	/**
	 * Get the displayed effective armor.
	 * 
	 * @param player
	 *            The player wearing the armor.
	 * @param armor
	 *            The ItemStack of the armor item itself.
	 * @param slot
	 *            The armor slot the item is in.
	 * @return The number of armor points for display, 2 per shield.
	 */
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
		return 4;
	}
	
	/**
	 * Applies damage to the ItemStack. The mod is responsible for reducing the
	 * item durability and stack size. If the stack is depleted it will be cleaned
	 * up automatically.
	 * 
	 * @param entity
	 *            The entity wearing the armor
	 * @param stack
	 *            The ItemStack of the armor item itself.
	 * @param source
	 *            The source of the damage, which can be used to alter armor
	 *            properties based on the type or source of damage.
	 * @param damage
	 *            The amount of damage being applied to the armor
	 * @param slot
	 *            The armor slot the item is in.
	 */
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot)
	{
		stack.damageItem(damage, entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			descriptionList.add("Allows the wearer");
			descriptionList.add("to see underwater.");
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}
	
}
