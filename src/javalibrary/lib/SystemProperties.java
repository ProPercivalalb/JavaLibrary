package javalibrary.lib;

/**
 * @author Alex
 */
public class SystemProperties {

	/** The operation system name **/
	public static final String SYSTEM_NAME = System.getProperty("os.name");
	/** The operation system version **/
	public static final String SYSTEM_VERSION = System.getProperty("os.version");
	public static final String SYSTEM_ARCHITECTURE = System.getProperty("os.arch");
	/** JRE version number **/
	public static final String JAVA_VERSION = System.getProperty("java.version");
	/** JRE vendor name **/
	public static final String JAVA_VENDOR = System.getProperty("java.vendor");
	/** The system bit version, e.g. 32 or 64 bit **/
	public static final String OS_BIT = System.getProperty("sun.arch.data.model");
	/** Character that separates components of a file path. This could be "/" or "\". **/
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	/** User working directory **/
	public static final String USER_WORKING_DIR = System.getProperty("user.dir");
	/** User home directory **/
	public static final String USER_HOME_DIR = System.getProperty("user.home");
	/** User account name **/
	public static final String USER_NAME = System.getProperty("user.name");
}
