package ml.jmoodle.tests.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestTools {
    public static String entityToXmlResponse(Object mc) {
		StringBuilder sb = new StringBuilder();
		sb.append("<SINGLE>");
		Method m[] = mc.getClass().getDeclaredMethods();
		Arrays.stream(m)
			.filter(mtd ->  mtd.getName().startsWith("get"))
			.forEach(mtd -> { 
				try {
					Object value = mtd.invoke(mc);
					if (value != null) {
						if (mtd.getReturnType().isArray()) {
							Object values[] = (Object[]) value;
							if (values.length > 0) {
								sb.append("<KEY name=\"").append(
									mtd.getName().substring(3).toLowerCase()
								).append("\">")
								.append("<MULTIPLE>");
								for(int i=0; i<values.length; i++) {
									sb.append(entityToXmlResponse(values[i]));
								}
								sb.append("</MULTIPLE>");
							}
						} else {
							sb.append("<KEY name=\"").append(
								mtd.getName().substring(3).toLowerCase()
							).append("\">").append("<VALUE>");
							if (mtd.getReturnType().getTypeName().equals(
									Boolean.class.getCanonicalName())
								) {
								sb.append(
									((Boolean) value).booleanValue() ? "1" : "0"
								);
							} else {
								sb.append(value);
							}
							sb.append("</VALUE>");
						}
						sb.append("</KEY>");
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
		});
		sb.append("</SINGLE>");
		return sb.toString();
	}
}