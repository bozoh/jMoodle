package ml.jmoodle.functions.rest.tools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * Rest functions tools
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 **/
public class MoodleRestFunctionTools {
	public static Map<String, Object> getSingleAttributes(Node singleNode) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		NodeList keysNode = singleNode.getChildNodes();
		for (int i = 0; i < keysNode.getLength(); i++) {
			Node key = keysNode.item(i);
			if (key.getNodeName().equals("KEY") && key.getFirstChild().getNodeName().equals("VALUE")) {
				Element nodeKey = (Element) key;
				returnMap.put(nodeKey.getAttribute("name"), key.getFirstChild().getFirstChild().getNodeValue());
			} else if (key.getNodeName().equals("KEY") && key.getFirstChild().getNodeName().equals("MULTIPLE")) {
				Element nodeKey = (Element) key;
				Set<Map<String,Object>> multipleValues=new HashSet<Map<String,Object>>();
				NodeList singlesNode=key.getFirstChild().getChildNodes();
				for (int j = 0; j < singlesNode.getLength(); j++) {
					multipleValues.add(getSingleAttributes(singlesNode.item(j)));
				}
				returnMap.put(nodeKey.getAttribute("name"), multipleValues);
			}
		}
		return returnMap;

	}

}
