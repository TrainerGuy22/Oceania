package oceania.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.Icon;

public class IconRegistry 
{
	
	public static Map<String, Icon> icons = new HashMap<String, Icon>();
	
	public static boolean hasIcon(String value)
	{
		return icons.containsKey(value);
	}
	
	public static void setIcon(String value, Icon icon) 
	{
		icons.put(value, icon);
	}
	
	public static Icon getIcon(String value)
	{
		return icons.get(value);
	}

}
