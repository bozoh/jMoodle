package ml.jmoodle.tools;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MoodleParamMap extends HashMap<String, Object> {

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
    public MoodleParamMap(Map<? extends String,? extends Object> m){
        super(m);
    }
    
    public String toParamString() throws UnsupportedEncodingException {
        return convertToParamString(this);
    }

    private String convertToParamString(MoodleParamMap map, String... parentKey) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        String pkey = parentKey.length == 1 ? parentKey[0] : "";
        for(String key: map.keySet()) {
            sb.append(MoodleTools.encode(pkey));
            if (map.get(key) instanceof MoodleParamMap) {
                sb.append(convertToParamString((MoodleParamMap) map.get(key), key));
            } else {
                sb.append(MoodleTools.encode(key));
                sb.append("=");
                sb.append(MoodleTools.encode(map.get(key).toString()));
            }
            sb.append("&");
        }
        return sb.substring(0, sb.length() -1);

    }

}