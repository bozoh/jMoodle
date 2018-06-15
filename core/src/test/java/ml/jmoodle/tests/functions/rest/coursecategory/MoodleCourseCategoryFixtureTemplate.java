package ml.jmoodle.tests.functions.rest.coursecategory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import ml.jmoodle.commons.MoodleCourseCategory;
import ml.jmoodle.tests.tools.TestTools;

public class MoodleCourseCategoryFixtureTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(MoodleCourseCategory.class).addTemplate("valid", new Rule(){{

			add("id", random(Long.class));
			add("name", regex("[A-Za-z0-9_ ]{3,50}"));
			add("idnumber", regex("\\w{3,5}"));
			add("description", regex("[A-Za-z0-9_ ]{3,150}"));
			add("descriptionformat", random(DescriptionFormat.class, 
				DescriptionFormat.MOODLE, 
				DescriptionFormat.HTML, 
				DescriptionFormat.PLAIN, 
				DescriptionFormat.MARKDOWN 
			));
			add("parent", random(Long.class));
			add("sortorder", random(Integer.class));
			add("coursecount", random(Integer.class));
			add("visible", random(Boolean.class, 
				MoodleCourseCategory.CATEGORY_VISIBLE_YES,
				MoodleCourseCategory.CATEGORY_VISIBLE_NO
			));
			add("visibleold", random(Boolean.class, 
				MoodleCourseCategory.CATEGORY_VISIBLE_YES,
				MoodleCourseCategory.CATEGORY_VISIBLE_NO
			));
			add("timemodified", random(Long.class));
			add("depth", random(Integer.class));
			add("path", regex("\\w{3,5}"));
			add("theme", regex("\\w{3,6}"));
		}});
	}


	public static Document getValidResponseOnRetrive(Set<MoodleCourseCategory> entities) throws SAXException, IOException, ParserConfigurationException {
		// <?xml version="1.0" encoding="UTF-8" ?>
		// <RESPONSE>
		// 	<MULTIPLE>
		// 		<SINGLE>
		// 			<KEY name="id">
		// 				<VALUE>int</VALUE>
		// 			</KEY>
		// 			<KEY name="name">
		// 				<VALUE>string</VALUE>
		// 			</KEY>
		// ...
		// 			<KEY name="theme">
		// 				<VALUE>string</VALUE>
		// 			</KEY>
		// 		</SINGLE>
		// 	</MULTIPLE>
		// </RESPONSE>

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
			.append("<RESPONSE>")
			.append("<MULTIPLE>");

		for (MoodleCourseCategory entity : entities) {
			sb.append(TestTools.entityToXmlResponse(entity));
		}
		sb.append("</MULTIPLE>").append("</RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
		xmlResponse.getDocumentElement().normalize();
		return xmlResponse;
	}

	

	public static Document getValidResponseOnCreate(Set<MoodleCourseCategory> entities) throws SAXException, IOException, ParserConfigurationException {
		//
		// <?xml version="1.0" encoding="UTF-8" ?>
		// <RESPONSE>
		// 	<MULTIPLE>
		// 		<SINGLE>
		// 			<KEY name="id">
		// 				<VALUE>int</VALUE>
		// 			</KEY>
		// 			<KEY name="name">
		// 				<VALUE>string</VALUE>
		// 			</KEY>
		// 		</SINGLE>
		// 	</MULTIPLE>
		// </RESPONSE>
		
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
				.append("<RESPONSE>")
				.append("<MULTIPLE>");
			int i = 0;
			for (MoodleCourseCategory entity : entities) {
				sb.append("<SINGLE>")
					.append("<KEY name=\"id\">")
					.append("<VALUE>").append(i++).append("</VALUE>")
					.append("</KEY>")
					.append("<KEY name=\"name\">")
					.append("<VALUE>").append(entity.getName()).append("</VALUE>")
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