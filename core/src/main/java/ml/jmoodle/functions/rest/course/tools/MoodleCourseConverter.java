package ml.jmoodle.functions.rest.course.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.management.RuntimeErrorException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.functions.rest.course.exceptions.MoodleRestCreateCoursesException;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;

public class MoodleCourseConverter {
	public String serialize(MoodleCourse course) throws MoodleRestCreateCoursesException {
		return serialize(new MoodleCourse[]{course});
	}

	public String serialize(MoodleCourse[] courses) throws MoodleRestCreateCoursesException {
		return serialize(Arrays.stream(courses).collect(Collectors.toList()));
	}

	public String serialize(List<MoodleCourse> entities) throws MoodleRestCreateCoursesException{
		try {
			return IntStream.range(0, entities.size()).boxed()
				.map(i -> {
					try {
						MoodleParamMap map = MoodleRestFunctionTools.Entity2MoodleParamMap(
							entities.get(i), "courses["+ i +"]"
						);
						return map.toParamString();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
				}).collect(Collectors.joining("\n"));
		} catch(RuntimeException e) {
			throw new MoodleRestCreateCoursesException(e);
		}
	}

	public Set<MoodleCourse> deSerialize(NodeList nodeList) {
		Set<MoodleCourse> result = new LinkedHashSet<MoodleCourse>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node singleNode = nodeList.item(i);
			Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
			result.add(map2course(singleValuesMap));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private MoodleCourse map2course(Map<String, Object> valuesMap) {
		return null;
// 		if (valuesMap == null || valuesMap.isEmpty())
// 			return null;

// 		MoodleCourse course = new MoodleCourse();

// 		//Must have attributes
// 		course.setId(Long.valueOf((String) valuesMap.get("id")));
// 		course.setShortname((String) valuesMap.get("shortname"));
// 		course.setCategoryId(Long.valueOf((String) valuesMap.get("categoryid")));
// 		course.setFullname((String) valuesMap.get("fullname"));

// 		if (valuesMap.containsKey("categorysortorder")){
// 			course.setCategorySortOrder(
// 				Integer.valueOf(
// 					(String) valuesMap.get("categorysortorder")
// 				)
// 			);
// 		}
            
// 		if (valuesMap.containsKey("summary")){
// 			course.setSummary(valuesMap.get("summary"));
// 		}
// 		if (valuesMap.containsKey("summaryformat")){
// 			course.setSummaryFormat(
// 				Integer.valueOf(
// 					(String)valuesMap.get("summaryformat")
// 				)
// 			);
// 		}
// 		if (valuesMap.containsKey("format")){
// 			course.setFormat(valuesMap.get("format"));
// 		}
// 		if (valuesMap.containsKey("showgrades")){
// 			course.setShowGrades(
// 				Integer.valueOf(
// 					(String)valuesMap.get("showgrades")
// 				)
// 			);
// 		}
// 		if (valuesMap.containsKey("newsitems")){
// 			course.setNewsItems(Integer.valueOf((String)valuesMap.get("newsitems")));
// 		}
// // 		if (valuesMap.containsKey("startdate")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("startdate"
// // 		}
// // 		if (valuesMap.containsKey("numsections")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("numsections"
// // 		}
// // 		if (valuesMap.containsKey("maxbytes")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("maxbytes"
// // 		}
// // 		if (valuesMap.containsKey("showreports")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("showreports"
// // 		}
// // 		if (valuesMap.containsKey("visible")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("visible"
// // 		}
// // 		if (valuesMap.containsKey("hiddensections")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("hiddensections"
// // 		}
// // 		if (valuesMap.containsKey("groupmode")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("groupmode"
// // 		}
// // 		if (valuesMap.containsKey("groupmodeforce")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("groupmodeforce"
// // 		}
// // 		if (valuesMap.containsKey("defaultgroupingid")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("defaultgroupingid"
// // 		}
// // 		if (valuesMap.containsKey("timecreated")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("timecreated"
// // 		}
// // 		if (valuesMap.containsKey("timemodified")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("timemodified"
// // 		}
// // 		if (valuesMap.containsKey("enablecompletion")){
// // 			course.set(Integer.valueOf((String) valuesMap.get("enablecompletion"
// // 		}
// 		if (valuesMap.containsKey("completionnotify")){
// 			course.set(Integer.valueOf((String) valuesMap.get("completionnotify"
// 		}
// // 		if (valuesMap.containsKey("lang")){
// // 			course.set"lang"
// // 		}
// // 		if (valuesMap.containsKey("forcetheme")){
// // 			course.set"forcetheme"
// // 		}
// // 		if (valuesMap.containsKey("courseformatoptions")){
// // 			<MULTIPLE>
// // 				<SINGLE>
// // 					if (valuesMap.containsKey("name")){
// // 						course.set
// // 					}
// // 					if (valuesMap.containsKey("value")){
// // 						course.set
// // 					}
// // 				</SINGLE>
// // 			</MULTIPLE>
// // 		}
//         </SINGLE>
//     </MULTIPLE>
// </RESPONSE>


		// Must Have attributes
		

		// Optional Values
		

		// return course;
	}

}
