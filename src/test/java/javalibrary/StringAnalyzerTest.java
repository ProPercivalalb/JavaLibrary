package javalibrary;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import javalibrary.string.StringAnalyzer;

public class StringAnalyzerTest {

    @Test
    public void testAnalyzer() {
        Assert.assertEquals(createMap('A', 2, 'B', 1, 'C', 2, 'F', 1, 'E', 1), StringAnalyzer.getEmbeddedStrings("ABCFECA", 1, 1));
        Assert.assertEquals(createMap("AB", 3, "BA", 2, "BC", 1), StringAnalyzer.getEmbeddedStrings("ABABABC", 2, 2));
        Assert.assertEquals(createMap("AB", 3, "BA", 2, "BC", 1, "ABA", 2, "BAB", 2, "ABC", 1), StringAnalyzer.getEmbeddedStrings("ABABABC", 2, 3));
        Assert.assertEquals(createMap("AB", 3, "ABA", 1, "BAB", 1), StringAnalyzer.getEmbeddedStrings("ABABABC", 2, 3, false));
    }
    
    private Map<String, Integer> createMap(Object... key_values) {
        if (key_values.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < key_values.length; i += 2) {
            if (!(key_values[i + 1] instanceof Integer))
                throw new IllegalArgumentException();

            if (key_values[i] instanceof Character || key_values[i] instanceof CharSequence) {
                map.put(key_values[i].toString(), (Integer) key_values[i + 1]);
            } else {
                throw new IllegalArgumentException();
            }
        }
        return map;
    }
}
