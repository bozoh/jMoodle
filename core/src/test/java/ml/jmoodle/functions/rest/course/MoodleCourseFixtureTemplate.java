package ml.jmoodle.functions.rest.course;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.DescriptionFormat;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.tools.TestTools;

public class MoodleCourseFixtureTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(MoodleCourse.class).addTemplate("valid", new Rule(){{
			add("id", random(Long.class));
			add("shortname", regex("[A-Za-z0-9_ ]{3,10}"));
			add("categoryid", random(Long.class));
			add("categorysortorder", random(Integer.class));
			add("fullname", regex("[A-Za-z0-9_ ]{3,50}"));
			add("idnumber", regex("\\w{3,5}"));
			add("summary", regex("[A-Za-z0-9_ ]{3,150}"));
			add("summaryformat", random(DescriptionFormat.class, 
				DescriptionFormat.MOODLE, 
				DescriptionFormat.HTML, 
				DescriptionFormat.PLAIN, 
				DescriptionFormat.MARKDOWN 
			));
			add("format", random(String.class, 
				MoodleCourse.FORMAT_WEEKS, 
				MoodleCourse.FORMAT_TOPICS,
				MoodleCourse.FORMAT_SOCIAL,
				MoodleCourse.FORMAT_SCORM
			));
			add("showgrades", random(Integer.class, 
				MoodleCourse.SHOW_GRADES_YES,
				MoodleCourse.SHOW_GRADES_NO
			));
			add("newsitems", random(Integer.class));
			add("startdate", random(Long.class));
			add("numsections", random(Integer.class));
			add("maxbytes", random(Long.class));
			add("showreports", random(Integer.class, 
				MoodleCourse.SHOW_REPORTS_YES,
				MoodleCourse.SHOW_REPORTS_NO
			));
			add("visible", random(Integer.class, 
				MoodleCourse.COURSE_VISIBLE_TO_STUDENTS_YES,
				MoodleCourse.COURSE_VISIBLE_TO_STUDENTS_NO
			));
			add("hiddensections", random(Integer.class, 
				MoodleCourse.HIDDEN_SECTIONS_COLLAPSED,
				MoodleCourse.HIDDEN_SECTIONS_INVISIBLE
			));
			add("groupmode", random(Integer.class, 
				MoodleCourse.GROUP_MODE_NO_GROUPS,
				MoodleCourse.GROUP_MODE_SEPARATE_GROUPS,
				MoodleCourse.GROUP_MODE_VISIBLE_GROUPS
			));
			add("groupmodeforce", random(Integer.class, 
				MoodleCourse.GROUP_MODE_FORCE_YES,
				MoodleCourse.GROUP_MODE_FORCE_NO
			));
			add("defaultgroupingid", random(Long.class));
			add("timecreated", random(Long.class));
			add("timemodified", random(Long.class));
			add("enablecompletion", random(Integer.class, 
				MoodleCourse.COMPLETION_ENABLED,
				MoodleCourse.COMPLETION_DISABLED
			));
			add("completionnotify", random(Integer.class,
				MoodleCourse.COMPLETION_NOTIFY_ENABLED,
				MoodleCourse.COMPLETION_NOTIFY_DISABLED
			));               
			add("lang", regex("\\w{2,4}")); 
			add("forcetheme", regex("\\w{3,6}"));
			add("courseformatoptions", has(3).of(OptionParameter.class, "valid"));
	
		}});
		Fixture.of(OptionParameter.class).addTemplate("valid", new Rule(){{
			add("name", regex("\\w{3,6}"));
			add("value", regex("\\w{3,10}"));
		}});
	}


	public static Document getValidResponseOnRetrive(Set<MoodleCourse> mcs) throws SAXException, IOException, ParserConfigurationException {
		// <?xml version="1.0" encoding="UTF-8" ?>
		// <RESPONSE>
		// 	<MULTIPLE>
		// 		<SINGLE>
		// 			<KEY name="id">
		// 				<VALUE>int</VALUE>
		// 			</KEY>
		// 			<KEY name="shortname">
		// 				<VALUE>string</VALUE>
		// 			</KEY>
		// 			<KEY name="categoryid">
		// 				<VALUE>int</VALUE>
		// 			</KEY>
		// ...
		// 			<KEY name="forcetheme">
		// 				<VALUE>string</VALUE>
		// 			</KEY>
		// 			<KEY name="courseformatoptions">
		// 				<MULTIPLE>
		// 					<SINGLE>
		// 						<KEY name="name">
		// 							<VALUE>string</VALUE>
		// 						</KEY>
		// 						<KEY name="value">
		// 							<VALUE>string</VALUE>
		// 						</KEY>
		// 					</SINGLE>
		// 				</MULTIPLE>
		// 			</KEY>
		// 		</SINGLE>
		// 	</MULTIPLE>
		// </RESPONSE>

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
			.append("<RESPONSE>")
			.append("<MULTIPLE>");

		for (MoodleCourse mc : mcs) {
			sb.append(TestTools.entityToXmlResponse(mc));
		}
		sb.append("</MULTIPLE>").append("</RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
		xmlResponse.getDocumentElement().normalize();
		return xmlResponse;
	}

	

	public static Document getValidResponseOnCreate(Set<MoodleCourse> mcs) throws SAXException, IOException, ParserConfigurationException {
		//
			// <?xml version="1.0" encoding="UTF-8" ?>
			// <RESPONSE>
			// <MULTIPLE>
			// <SINGLE>
			// <KEY name="id">
			// <VALUE>int</VALUE>
			// </KEY>
			// <KEY name="shortname">
			// <VALUE>string</VALUE>
			// </KEY>
			// </SINGLE>
			// </MULTIPLE>
			// </RESPONSE>
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
				.append("<RESPONSE>")
				.append("<MULTIPLE>");
			int i = 0;
			for (MoodleCourse mc : mcs) {
				sb.append("<SINGLE>")
					.append("<KEY name=\"id\">")
					.append("<VALUE>").append(i++).append("</VALUE>")
					.append("</KEY>")
					.append("<KEY name=\"shortname\">")
					.append("<VALUE>").append(mc.getShortname()).append("</VALUE>")
					.append("</KEY>")
					.append("</SINGLE>");
			}

			sb.append("</MULTIPLE>")
				.append("</RESPONSE>");

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
			xmlResponse.getDocumentElement().normalize();
			return xmlResponse;
	}

}