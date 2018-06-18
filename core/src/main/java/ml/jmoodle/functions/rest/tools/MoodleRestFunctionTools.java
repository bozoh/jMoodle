package ml.jmoodle.functions.rest.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.commons.Criteria;
import ml.jmoodle.tools.MoodleParamMap;

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

	public static MoodleParamMap entity2MoodleParamMap(Object entity, String parentName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		MoodleParamMap map = new MoodleParamMap();
		
		Method[] methods = entity.getClass().getDeclaredMethods();
		for(Method m: methods) {
			if (!m.getName().startsWith("get")) {
				continue;
			}
			
			Object o = m.invoke(entity);
			if (o == null) {
				continue;
			}
			
			String methodName = parentName.toLowerCase() +
				"[" + m.getName().toLowerCase().substring(3)+"]";

			if (m.getReturnType().isArray()) {
				Object[] nestedEntities =  (Object[]) o;
				for (int i=0; i < nestedEntities.length; i++) {
					String nestedMethodName = methodName+"["+i+"]";
					map.put(nestedMethodName, 
						entity2MoodleParamMap(nestedEntities[i], ""));
				}
				continue;
			}
			if (m.getReturnType().equals(Boolean.class)) {
				o = ((Boolean) o) ? 1 : 0;
			} 

			if (m.getReturnType().isEnum()) {
				Method enumMethod = o.getClass().getMethod("getValue");
				o = enumMethod.invoke(o);
				
				
			} 
			map.put(methodName, o);
			
		}

		return map;
	}

	public static String serializeCriterias(List<Criteria> criterias) {
		// criteria[0][key]= string
		// criteria[0][value]= string		
		return IntStream.range(0, criterias.size()).boxed()
			.map(i -> {
				try {
					MoodleParamMap map = entity2MoodleParamMap(
						criterias.get(i), "criteria["+ i +"]"
					);
					return map.toParamString();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.joining(""));
	}

}
