package ml.jmoodle.functions.rest.enrol.manual.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import ml.jmoodle.commons.MoodleManualEnrolment;
import ml.jmoodle.functions.converters.MoodleManualEnrolmentConverter;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleParamMap;

public class MoodleManualEnrolmentTools extends MoodleManualEnrolmentConverter{

	public String serialize(Collection<MoodleManualEnrolment> entities) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, UnsupportedEncodingException {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (MoodleManualEnrolment entity : entities) {
            MoodleParamMap map = MoodleRestFunctionTools.entity2MoodleParamMap(
                entity, "enrolments[" + i + "]"
            );
            sb.append(map.toParamString());
            sb.append("&");
            i++;
        }
		return sb.substring(0, sb.length() - 1);
	}
}