package ml.jmoodle.functions.rest.role.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.functions.converters.MoodleUserRoleContextConverter;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;

public class MoodleUserRoleContextTools extends MoodleUserRoleContextConverter{

	public String serialize(Collection<MoodleUserRoleContext> entities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (MoodleUserRoleContext entity : entities) {
            MoodleParamMap map = MoodleRestFunctionTools.entity2MoodleParamMap(
                entity, "assignments[" + i + "]"
            );
            sb.append(map.toParamString());
            sb.append("&");
            i++;
        }
		return sb.substring(0, sb.length() - 1);
	}
}