package ml.jmoodle.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ml.jmoodle.configs.MoodleConfig;

public class MoodleParamMap extends HashMap<String, String> {

	private static final long serialVersionUID = 350566779195839610L;

    /**
     * Constructs an empty HashMap with the default initial capacity (16) and the default load factor (0.75).
     */
    public MoodleParamMap(){
        super();
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and the default load factor (0.75).
     */
    public MoodleParamMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty HashMap with the specified initial capacity and load factor.
     */
    public MoodleParamMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new HashMap with the same mappings as the specified Map.
     */
    public MoodleParamMap(Map<? extends String,? extends String> m){
        super(m);
    }
    
    public String toParamString() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        
        for(String key: keySet()) {
            sb.append(URLEncoder.encode(key, MoodleConfig.DEFAULT_ENCODING));
            sb.append("=");
            sb.append(URLEncoder.encode(get(key), MoodleConfig.DEFAULT_ENCODING));
            sb.append("&");
        }
        return sb.substring(0, sb.length() -1);
    }

}