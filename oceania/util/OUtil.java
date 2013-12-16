package oceania.util;

public class OUtil
{
	public static final String COLOR_CODE = "\u00A7";
	
	public static String colorString(String input)
	{
		return input.replaceAll("&&", COLOR_CODE);
	}
}
