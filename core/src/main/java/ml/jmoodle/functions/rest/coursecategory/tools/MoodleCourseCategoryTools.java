package ml.jmoodle.functions.rest.coursecategory.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.functions.converters.MoodleCourseCategoryConverter;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;


public class MoodleCourseCategoryTools extends MoodleCourseCategoryConverter {
	public String serialize(MoodleCourseCategory course) {
		return serialize(new MoodleCourseCategory[]{course});
	}

	public String serialize(MoodleCourseCategory[] courses) {
		return serialize(Arrays.stream(courses).collect(Collectors.toList()));
	}

	public String serialize(Collection<MoodleCourseCategory> courses) {
		return serialize(new ArrayList<>(courses));
	}

	public String serialize(List<MoodleCourseCategory> entities) {
		return IntStream.range(0, entities.size()).boxed()
			.map(i -> {
				try {
					MoodleParamMap map = MoodleRestFunctionTools.entity2MoodleParamMap(
						entities.get(i), "categories["+ i +"]"
					);
					return map.toParamString();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.joining("\n"));
	}

	public Set<MoodleCourseCategory> deSerialize(NodeList nodeList) {
		
		Set<MoodleCourseCategory> result = new LinkedHashSet<MoodleCourseCategory>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node singleNode = nodeList.item(i);
			Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
			result.add(toEntity(singleValuesMap));
		}

		return result;
	}
	
	// @Override
	// @SuppressWarnings("unchecked")
	// public MoodleCourseCategory toEntity(Map<String, Object> valuesMap) {
	// 	MoodleCourseCategory mc = super.toEntity(valuesMap);
	// 	mc.setId(Long.parseLong((String) valuesMap.get("id")));

	// 	if (valuesMap.containsKey("courseformatoptions")){
	// 		OptionParameterConverter opc = new OptionParameterConverter();
	// 		Set<Map<String, Object>> options = (Set<Map<String, Object>>) valuesMap.get("courseformatoptions");
	// 		for (Map<String, Object> optMap : options) {
	// 			mc.addCourseCategoryformatoptions(opc.toEntity(optMap));
	// 		}
	// 	}
	// 	return mc;
	// }

	public String serializeCourseCategorysId(String prefix, Set<Long> coursesIds) throws UnsupportedEncodingException {
		if (coursesIds.size() == 0)
			return null;

		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		for (Long courseId : coursesIds) {
			sb.append(MoodleTools.encode(prefix)).append(MoodleTools.encode("["))
				.append(i++).append(MoodleTools.encode("]")).append("=").append(courseId)
				.append("&");
		}

		return sb.substring(0, sb.length() - 1);
	}

}
