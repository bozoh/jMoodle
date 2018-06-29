package ml.jmoodle.tests.functions.rest.enrol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ml.jmoodle.commons.Capability;
import ml.jmoodle.commons.DescriptionFormat;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleEnrolInstanceInfo;
import ml.jmoodle.commons.MoodleEnrolmentMethod;
import ml.jmoodle.commons.MoodleGroup;
import ml.jmoodle.commons.MoodleManualEnrolment;
import ml.jmoodle.commons.MoodleRole;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.tests.tools.TestTools;


public class EnrolFunctionsFixtureTemplate implements TemplateLoader {

	@Override
	public void load() {
        FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests.functions.rest.user");
        int testNum = (new Random()).ints(3, 10).findFirst().getAsInt();
        
        Fixture.of(MoodleUser.class).addTemplate("getEnrolledUsers")
            .inherits("MoodleRestGetUserByIdFunctionResponseEntities", new Rule(){
                {
                    add("roles", has(testNum).of(MoodleRole.class, "EnrolFunctionsValidRole"));
                    add("groups", has(testNum).of(MoodleGroup.class, "EnrolFunctionsValidGroup"));
                }
        });

        Fixture.of(MoodleRole.class).addTemplate("EnrolFunctionsValidRole", new Rule(){
            {
                add("roleid", random(Long.class, range(1, 300)));
                add("sortorder", random(Integer.class, range(10, 3000)));
                add("name", firstName()+" "+lastName());
                add("shortname", firstName());
            }
        });

        Fixture.of(MoodleGroup.class).addTemplate("EnrolFunctionsValidGroup", new Rule(){
            {
                add("id", random(Long.class, range(1, 300)));
                add("name", firstName()+" "+lastName());
                add("description", regex("\\w{33}"));
                add("descriptionFormat", random(DescriptionFormat.class, 
                    DescriptionFormat.HTML, DescriptionFormat.MARKDOWN,
                    DescriptionFormat.PLAIN, DescriptionFormat.MOODLE
                ));
            }
        });

        Fixture.of(MoodleEnrolmentMethod.class).addTemplate("getEnrolledMethods", new Rule(){
            {
                add("id", random(Long.class, range(1, 300)));
                add("courseId", random(Long.class, range(1, 300)));
                add("type", regex("\\w{3,5}"));
                add("name", regex("\\w{3,50}"));
                add("status", regex("\\w{3,5}"));
                add("wsFunction", regex("\\w{5,20}"));
            }
        });

        Fixture.of(MoodleManualEnrolment.class).addTemplate("valid", new Rule(){
            {
                add("roleId", random(Long.class, range(1, 30)));
                add("userId", random(Long.class, range(1, 3000)));
                add("courseId", random(Long.class, range(1, 300)));
                add("timeStart", random(Long.class, range(1520198751, 1530198754)));
                add("timeEnd", random(Long.class, range(1520198751, 1530198754)));
                add("suspend", random(Integer.class, range(0, 1)));
            }
        });

        Fixture.of(MoodleEnrolInstanceInfo.class).addTemplate("valid", new Rule(){
            {
                add("id", random(Long.class, range(1, 5000)));
                add("courseId", random(Long.class, range(1, 500)));
                add("type", regex("\\w{5,10}"));
                add("name", firstName());
                add("status", regex("\\w{3,10}"));
                add("enrolPassword", regex("\\w{0,8}"));
                add("passwordRequired", random(Boolean.class, Boolean.TRUE, Boolean.FALSE));
            }
        });
    }
    
    public static Document getEnrolledUsersResponse(Collection<MoodleUser> entities) throws ParserConfigurationException, SAXException, IOException {
        // <?xml version="1.0" encoding="UTF-8" ?>
        // <RESPONSE>
        //     <MULTIPLE>
        //         <SINGLE>
        //             <KEY name="id">
        //                 <VALUE>int</VALUE>
        //             </KEY>
        //             <KEY name="username">
        //                 <VALUE>string</VALUE>
        //             </KEY>
        //             <KEY name="customfields">
        //             </KEY>
        //             <KEY name="groups">
        //             </KEY>
        //             <KEY name="roles">
        //             </KEY>
        //             <KEY name="preferences">
        //             </KEY>
        //             <KEY name="enrolledcourses">
        //             </KEY>
        //         </SINGLE>
        //     </MULTIPLE>
        // </RESPONSE>
        StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
			.append("<RESPONSE>")
			.append("<MULTIPLE>");

		for (MoodleUser entity : entities) {
			sb.append(TestTools.entityToXmlResponse(entity));
		}
		sb.append("</MULTIPLE>").append("</RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
		xmlResponse.getDocumentElement().normalize();
		return xmlResponse;

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

	public static Document getEnrolledUsersWithCapabilityResponse(MoodleCourse course1, Set<Capability> capabilities1,
			Set<MoodleUser> users1, MoodleCourse course2, Set<Capability> capabilities2, Set<MoodleUser> users2) throws ParserConfigurationException, SAXException, IOException {

            //     KEY name="courseid">
            //     <VALUE>int</VALUE>
            // </KEY>
            // <KEY name="capability">
            //     <VALUE>string</VALUE>
            // </KEY>
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<RESPONSE>").append("<MULTIPLE>");
        for (Capability cap : capabilities1) {
            sb.append("<SINGLE>").append("<KEY name=\"courseid\">").append("<VALUE>")
            .append(course1.getId()).append("</VALUE>").append("</KEY>");
            
            sb.append("<KEY name=\"capability\">").append("<VALUE>")
                .append(cap.getValue()).append("</VALUE>").append("</KEY>");

            sb.append("<KEY name=\"users\">").append("<MULTIPLE>");
            for (MoodleUser user : users1) {
                sb.append(TestTools.entityToXmlResponse(user));
            }
            sb.append(cap.getValue()).append("</MULTIPLE>").append("</KEY>");
            sb.append("</SINGLE>");
        }
        
        for (Capability cap : capabilities2) {
            sb.append("<SINGLE>").append("<KEY name=\"courseid\">").append("<VALUE>")
            .append(course2.getId()).append("</VALUE>").append("</KEY>");
            
            sb.append("<KEY name=\"capability\">").append("<VALUE>")
                .append(cap.getValue()).append("</VALUE>").append("</KEY>");

            sb.append("<KEY name=\"users\">").append("<MULTIPLE>");
            for (MoodleUser user : users1) {
                sb.append(TestTools.entityToXmlResponse(user));
            }
            sb.append(cap.getValue()).append("</MULTIPLE>").append("</KEY>");
            sb.append("</SINGLE>");
        }
        sb.append("</MULTIPLE>").append("</RESPONSE>");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
        xmlResponse.getDocumentElement().normalize();
        return xmlResponse;
	}

	public static Document getCourseEnrolmentMethodsResponse(Set<MoodleEnrolmentMethod> mcs) throws ParserConfigurationException, SAXException, IOException {

        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<RESPONSE>").append("<MULTIPLE>");

        for (MoodleEnrolmentMethod mem : mcs) {
            sb.append(TestTools.entityToXmlResponse(mem));
        }
        sb.append("</MULTIPLE>").append("</RESPONSE>");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
        xmlResponse.getDocumentElement().normalize();
        return xmlResponse;
	}

	public static Document getEnrolSelfUserResponse(Collection<MoodleWarning> warns) throws ParserConfigurationException, SAXException, IOException {
        // <?xml version="1.0" encoding="UTF-8" ?>
        // <RESPONSE>
        //     <SINGLE>
        //         <KEY name="status">
        //             <VALUE>int</VALUE>
        //         </KEY>
        //         <KEY name="warnings">
        //             <MULTIPLE>
        //                 <SINGLE>
        //                     <KEY name="item">
        //                         <VALUE>string</VALUE>
        //                     </KEY>
        //                     <KEY name="itemid">
        //                         <VALUE>int</VALUE>
        //                     </KEY>
        //                     <KEY name="warningcode">
        //                         <VALUE>string</VALUE>
        //                     </KEY>
        //                     <KEY name="message">
        //                         <VALUE>string</VALUE>
        //                     </KEY>
        //                 </SINGLE>
        //             </MULTIPLE>
        //         </KEY>
        //     </SINGLE>
        // </RESPONSE>

		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<RESPONSE>").append("<SINGLE>").append("<KEY name=\"status\">")
            .append("<VALUE>").append("1").append("</VALUE>").append("</KEY>")
            .append("<KEY name=\"warnings\">").append("<MULTIPLE>");
        for (MoodleWarning mem : warns) {
            sb.append(TestTools.entityToXmlResponse(mem));
        }
        sb.append("</MULTIPLE>").append("</KEY>").append("</SINGLE>").append("</RESPONSE>");
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
        xmlResponse.getDocumentElement().normalize();
        return xmlResponse;
	}

	public static Document getEnrolSelfGetInstanceInfoResponse(MoodleEnrolInstanceInfo instInfo) throws ParserConfigurationException, SAXException, IOException {
        instInfo.setPasswordRequired(null);
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        sb.append("<RESPONSE>").append(TestTools.entityToXmlResponse(instInfo));
        sb.append("</RESPONSE>");

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlResponse = builder.parse(new ByteArrayInputStream(sb.toString().getBytes()));
        xmlResponse.getDocumentElement().normalize();
        return xmlResponse;
	}
}