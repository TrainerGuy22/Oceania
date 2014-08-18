package oceania.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.IIcon;

public class IconRegistry {
	
	public static Map<String, IIcon> ICONS = new HashMap<String, IIcon>();
	
	public static boolean hasIcon(String value) {
		return ICONS.containsKey(value);
	}
	
	public static void setIcon(String value, IIcon icon)  {
		ICONS.put(value, icon);
	}
	
	public static IIcon getIcon(String value) {
		return ICONS.get(value);
	}

}
