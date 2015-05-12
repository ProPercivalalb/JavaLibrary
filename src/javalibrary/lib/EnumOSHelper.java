package javalibrary.lib;

/**
 * @author Alex
 */
public class EnumOSHelper {
	
	public static enum EnumOS {
		LINUX,
		SOLARIS,
		WINDOWS,
    	MACOS,
    	UNKNOWN;
	}
    
	/**
	 * Get the system operating system and returns it in a {@link EnumOS.class()}
	 * @return The system type
	 */
    public static EnumOS getPlatform() {
        String systemOs = SystemProperties.SYSTEM_NAME.toLowerCase();
        if(systemOs.contains("win")) return EnumOS.WINDOWS;
        if(systemOs.contains("mac")) return EnumOS.MACOS;
        if(systemOs.contains("solaris") || systemOs.contains("sunos")) return EnumOS.SOLARIS;
        if(systemOs.contains("linux") || systemOs.contains("unix")) return EnumOS.LINUX;
        return EnumOS.UNKNOWN;
    }
}
