package ml.jmoodle.functions.rest.core.course.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.functions.converters.MoodleCourseConverter;
import ml.jmoodle.functions.converters.OptionParameterConverter;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

public class MoodleCourseTools extends MoodleCourseConverter {
	public String serialize(MoodleCourse course) {
		return serialize(new MoodleCourse[]{course});
	}

	public String serialize(MoodleCourse[] courses) {
		return serialize(Arrays.stream(courses).collect(Collectors.toList()));
	}

	public String serialize(Set<MoodleCourse> courses) {
		return serialize(new ArrayList<>(courses));
	}

	public String serialize(List<MoodleCourse> entities) {
		return IntStream.range(0, entities.size()).boxed()
			.map(i -> {
				try {
					MoodleParamMap map = MoodleRestFunctionTools.entity2MoodleParamMap(
						entities.get(i), "courses["+ i +"]"
					);
					return map.toParamString();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.joining("\n"));
	}

	public Set<MoodleCourse> deSerialize(NodeList nodeList) {
		
		Set<MoodleCourse> result = new LinkedHashSet<MoodleCourse>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node singleNode = nodeList.item(i);
			Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
			result.add(toEntity(singleValuesMap));
		}

		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MoodleCourse toEntity(Map<String, Object> valuesMap) {
		MoodleCourse mc = super.toEntity(valuesMap);
		mc.setId(Long.parseLong((String) valuesMap.get("id")));

		if (valuesMap.containsKey("courseformatoptions")){
			OptionParameterConverter opc = new OptionParameterConverter();
			Set<Map<String, Object>> options = (Set<Map<String, Object>>) valuesMap.get("courseformatoptions");
			for (Map<String, Object> optMap : options) {
				mc.addCourseformatoptions(opc.toEntity(optMap));
			}
		}
		return mc;
	}

	public String serializeCoursesId(String prefix, Set<Long> coursesIds) throws UnsupportedEncodingException {
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
