package ml.jmoodle.functions.rest.tools;

import java.util.HashMap;
import java.util.Map;

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
	public static Map<String, String> getSingleAttributes(Node singleNode) {
		Map<String, String> returnMap = new HashMap<String, String>();
		NodeList keysNode = singleNode.getChildNodes();
		for (int i = 0; i < keysNode.getLength(); i++) {
			Node key = keysNode.item(i);
			if (key.getNodeName().equals("KEY")) {
				Element nodeKey = (Element) key;
				returnMap.put(nodeKey.getAttribute("name"), key.getFirstChild().getNodeValue());
			}

		}
		return returnMap;

	}

}
