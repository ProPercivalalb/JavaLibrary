package javalibrary;

import java.util.HashMap;

/**
 * @author Alex Barter (10AS)
 */
public class EncryptionData {
	
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	public EncryptionData() {}
	
	public void putData(String key, Object value) {
		this.map.put(key, value);
	}
	
	public <T> T getData(String key, Class<T> type) {
		Object x = map.get(key);
		if(type.isInstance(x))
			return (T)x;
		return null;
	}

	public static EncryptionData createNew() {
		return new EncryptionData();
	}
}
