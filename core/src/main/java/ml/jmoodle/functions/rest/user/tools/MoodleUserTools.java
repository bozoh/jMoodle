package ml.jmoodle.functions.rest.user.tools;

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

import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.functions.converters.MoodleUserConverter;
import ml.jmoodle.functions.converters.UserCustomFieldConverter;
import ml.jmoodle.functions.converters.UserPreferenceConverter;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

public class MoodleUserTools extends MoodleUserConverter {
	public String serialize(MoodleUser user) {
		return serialize(new MoodleUser[]{user});
	}

	public String serialize(MoodleUser[] users) {
		return serialize(Arrays.stream(users).collect(Collectors.toList()));
	}

	public String serialize(Set<MoodleUser> users) {
		return serialize(new ArrayList<>(users));
	}

	public String serialize(List<MoodleUser> entities) {
		return IntStream.range(0, entities.size()).boxed()
			.map(i -> {
				try {
					MoodleParamMap map = MoodleRestFunctionTools.entity2MoodleParamMap(
						entities.get(i), "users["+ i +"]"
					);
					return map.toParamString();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.joining("\n"));
	}

	public Set<MoodleUser> deSerialize(NodeList nodeList) {
		
		Set<MoodleUser> result = new LinkedHashSet<MoodleUser>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node singleNode = nodeList.item(i);
			Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
			result.add(toEntity(singleValuesMap));
		}

		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public MoodleUser toEntity(Map<String, Object> valuesMap) {
		MoodleUser entity = super.toEntity(valuesMap);
		entity.setId(Long.parseLong((String) valuesMap.get("id")));

		if (valuesMap.containsKey("preferences")){
			UserPreferenceConverter upc = new UserPreferenceConverter();
			Set<Map<String, Object>> options = (Set<Map<String, Object>>) valuesMap.get("preferences");
			for (Map<String, Object> optMap : options) {
				entity.addPreference(upc.toEntity(optMap));
			}
		}

		if (valuesMap.containsKey("customfields")){
			UserCustomFieldConverter ucfc = new UserCustomFieldConverter();
			Set<Map<String, Object>> ucfs = (Set<Map<String, Object>>) valuesMap.get("customfields");
			for (Map<String, Object> map : ucfs) {
				entity.addCustomfield(ucfc.toEntity(map));
			}
		}

		return entity;
	}

	public String serializeUsersId(String prefix, Set<Long> usersIds) throws UnsupportedEncodingException {
		if (usersIds.size() == 0)
			return null;

		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		for (Long userId : usersIds) {
			sb.append(MoodleTools.encode(prefix)).append(MoodleTools.encode("["))
				.append(i++).append(MoodleTools.encode("]")).append("=").append(userId)
				.append("&");
		}

		return sb.substring(0, sb.length() - 1);
	}

}
