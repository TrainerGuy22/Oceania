package oceania.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Icon;

public class IconRegistry 
{
	
	public static Map<String, Icon> ICONS = new HashMap<String, Icon>();
	
	public static boolean hasIcon(String value)
	{
		return ICONS.containsKey(value);
	}
	
	public static void setIcon(String value, Icon icon) 
	{
		ICONS.put(value, icon);
	}
	
	public static Icon getIcon(String value)
	{
		return ICONS.get(value);
	}

}
