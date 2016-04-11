package javalibrary.lib;

import java.io.File;

public class OSIdentifier {
	
	public static enum OS {
		LINUX,
		SOLARIS,
		WINDOWS,
    	MACOS,
    	UNKNOWN;
	}
	
    public static OS getPlatform() {
        String systemOs = System.getProperty("os.name").toLowerCase();
        if(systemOs.contains("win")) return OS.WINDOWS;
        if(systemOs.contains("mac")) return OS.MACOS;
        if(systemOs.contains("solaris") || systemOs.contains("sunos")) return OS.SOLARIS;
        if(systemOs.contains("linux") || systemOs.contains("unix")) return OS.LINUX;
        return OS.UNKNOWN;
    }
    
    public static File getMyDataFolder(String module) {
    	String userHome = System.getProperty("user.home", ".");
        File localFile;
        switch (getPlatform()) {
        	case LINUX:
        	case SOLARIS:
        		localFile = new File(userHome, "percivalalb/" + module + "/");
        		break;
	        case WINDOWS:
	        	String appdata = System.getenv("APPDATA");
	        	String finalPath = appdata != null ? appdata : userHome;
	        	localFile = new File(finalPath, "percivalalb/" + module + "/");
	        	break;
	        case MACOS:
	        	localFile = new File(userHome, "Library/Application Support/percivalalb/" + module + "/");
	        	break;
	        default:
	        	localFile = new File(userHome, "percivalalb/" + module + "/");
        }
        
        if (!localFile.exists() && !localFile.mkdirs())
        	throw new RuntimeException("The module directory could not be created: " + localFile);
        
        return localFile;
    }
    
    public static File getAppdataFolder() {
    	String userHome = System.getProperty("user.home", ".");
        File localFile;
        switch (getPlatform()) {
        	case LINUX:
        	case SOLARIS:
        		localFile = new File(userHome);
        		break;
	        case WINDOWS:
	        	String appdata = System.getenv("APPDATA");
	        	String finalPath = appdata != null ? appdata : userHome;
	        	localFile = new File(finalPath);
	        	break;
	        case MACOS:
	        	localFile = new File(userHome, "Library/Application Support");
	        	break;
	        default:
	        	localFile = new File(userHome);
        }
        
        if (!localFile.exists() && !localFile.mkdirs())
        	throw new RuntimeException("The appdata directory could not be created: " + localFile);
        
        return localFile;
    }
    
    public static File getMinecraftFolder() {
    	String userHome = System.getProperty("user.home", ".");
        File localFile;
        switch (getPlatform()) {
        	case LINUX:
        	case SOLARIS:
        		localFile = new File(userHome, ".minecraft/");
        		break;
	        case WINDOWS:
	        	String appdata = System.getenv("APPDATA");
	        	String finalPath = appdata != null ? appdata : userHome;
	        	localFile = new File(finalPath, ".minecraft/");
	        	break;
	        case MACOS:
	        	localFile = new File(userHome, "Library/Application Support/minecraft");
	        	break;
	        default:
	        	localFile = new File(userHome, "minecraft/");
        }
        
        if (!localFile.exists() && !localFile.mkdirs())
        	throw new RuntimeException("The minecraft directory could not be created: " + localFile);
        
        return localFile;
    }
}
