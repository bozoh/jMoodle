package ml.jmoodle.tests.functions.rest.enrol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.MoodleCourse;

public class EnrolFunctionsFixtureTemplate implements TemplateLoader {

	@Override
	public void load() {
		
	}

    public static Document getEnrolledCoursesResponse(Collection<MoodleCourse> mdlUsers) throws ParserConfigurationException, SAXException, IOException {
        // <RESPONSE>
        //     <MULTIPLE>
        //         <SINGLE>
        //             <KEY name="id">
        //                 <VALUE>int</VALUE>
        //             </KEY>
        //             <KEY name="shortname">
        //                 <VALUE>string</VALUE>
        //             </KEY>
        //             <KEY name="fullname">
        //                 <VALUE>string</VALUE>
        //             </KEY>
        //             <KEY name="enrolledusercount">
        //                 <VALUE>int</VALUE>
        //             </KEY>
        //             <KEY name="idnumber">
        //                 <VALUE>string</VALUE>
        //             </KEY>
        //             <KEY name="visible">
        //                 <VALUE>int</VALUE>
        //             </KEY>
        //         </SINGLE>
        //     </MULTIPLE>
        // </RESPONSE>
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<RESPONSE>").append("<MULTIPLE>");

        for (MoodleCourse entity : mdlUsers) {
            sb.append("<SINGLE>");
            sb.append("<KEY name=\"id\">").append("<VALUE>")
                .append(entity.getId()).append("</VALUE></KEY>");
            sb.append("<KEY name=\"shortname\">").append("<VALUE>")
                .append(entity.getShortname()).append("</VALUE></KEY>");
            sb.append("<KEY name=\"fullname\">").append("<VALUE>")
                .append(entity.getFullname()).append("</VALUE></KEY>");
            sb.append("<KEY name=\"idnumber\">").append("<VALUE>")
                .append(entity.getIdNumber()).append("</VALUE></KEY>");
            sb.append("<KEY name=\"visible\">").append("<VALUE>")
                .append(entity.getVisible()).append("</VALUE></KEY>");
            sb.append("<KEY name=\"enrolledusercount\">").append("<VALUE>")
                .append(entity.getEnrolledUserCount()).append("</VALUE></KEY>");
            sb.append("</SINGLE>");
            
        }
        sb.append("</MULTIPLE>").append("</RESPONSE>");

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
		xmlResponse.getDocumentElement().normalize();
		return xmlResponse;
    }
}