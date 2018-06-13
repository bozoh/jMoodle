package ml.jmoodle.tests.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.MoodleWarning;


public class MoodleWarningFixtureTemplate implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(MoodleWarning.class).addTemplate("valid", new Rule(){{
			add("item", regex("[A-Za-z0-9_ ]{3,10}"));
			add("itemid", random(Integer.class));
			add("warningcode", regex("[A-Za-z0-9_ ]{3,50}"));
			add("message", regex("[A-Za-z0-9_ ]{3,150}"));
		}});
	}


	public static Document getWarnigResponse(Collection<MoodleWarning> warnings) throws SAXException, IOException, ParserConfigurationException {
		// <?xml version="1.0" encoding="UTF-8" ?>
		// <RESPONSE>
		// 	<SINGLE>
		// 		<KEY name="warnings">
		// 			<MULTIPLE>
		// 				<SINGLE>
		// 					<KEY name="item">
		// 						<VALUE>string</VALUE>
		// 					</KEY>
		// 					<KEY name="itemid">
		// 						<VALUE>int</VALUE>
		// 					</KEY>
		// 					<KEY name="warningcode">
		// 						<VALUE>string</VALUE>
		// 					</KEY>
		// 					<KEY name="message">
		// 						<VALUE>string</VALUE>
		// 					</KEY>
		// 				</SINGLE>
		// 			</MULTIPLE>
		// 		</KEY>
		// 	</SINGLE>
		// </RESPONSE>

		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
			.append("<RESPONSE>")
			.append("<SINGLE>")
			.append("<KEY name=\"warnings\">")
            .append("<MULTIPLE>");

		for (MoodleWarning mw : warnings) {
			sb.append(TestTools.entityToXmlResponse(mw));
		}

		sb.append("</MULTIPLE>").append("</KEY>")
			.append("</SINGLE>").append("</RESPONSE>");
		

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
		xmlResponse.getDocumentElement().normalize();
		return xmlResponse;
	}

	

	

}