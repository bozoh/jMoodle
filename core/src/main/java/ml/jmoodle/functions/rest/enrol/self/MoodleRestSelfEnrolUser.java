package ml.jmoodle.functions.rest.enrol.self;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleWarning;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.converters.MoodleWarningConverter;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.self.exceptions.MoodleRestSelfEnrolUserException;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

/**
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "enrol_self_enrol_user" })
public class MoodleRestSelfEnrolUser extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "3.0.0";

	private Long courseId = null;
	private Long instanceId = null;
	private String password = null;

	private Set<MoodleWarning> warnings;

	public MoodleRestSelfEnrolUser(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	
	@Override
	public String getFunctionData() throws MoodleRestSelfEnrolUserException {
		if (MoodleTools.isEmpty(courseId)) 
			throw new MoodleRestSelfEnrolUserException(MoodleCommonsErrorMessages
				.notSet("Course id"));
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			MoodleParamMap params = new MoodleParamMap();
			params.put("courseid", courseId);
			if (!MoodleTools.isEmpty(instanceId)) {
				params.put("instanceid", instanceId);
			}
			if (!MoodleTools.isEmpty(password)) {
				params.put("password", password);
			}
			sb.append(params.toParamString());
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException  e) {
			throw new MoodleRestSelfEnrolUserException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() {
		return "enrol_self_enrol_user";
	}

	@Override
	public Boolean doCall() throws MoodleWSFucntionException {
		return (Boolean) super.doCall();
	}

	@SuppressWarnings("unchecked")
	protected Boolean processResponse(Document response)  {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength();) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				if (singleValuesMap.containsKey("warnings")) {
					processWarnings((Set<Map<String, Object>>) singleValuesMap.get("warnings"));
				}
				return "1".equals((String) singleValuesMap.get("status"));
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}


	private void processWarnings(Set<Map<String, Object>> warningsMap) {
		if (this.warnings == null) {
			this.warnings = new HashSet<MoodleWarning>();
		}
		MoodleWarningConverter converter = new MoodleWarningConverter();
		for (Map<String, Object> warningMap : warningsMap) {
			this.warnings.add(converter.toEntity(warningMap));
		}
	}

	public void setCourse(MoodleCourse course) throws MoodleRestSelfEnrolUserException {
		verify(course);
		courseId = course.getId();
	}

	private void verify(MoodleCourse course) throws MoodleRestSelfEnrolUserException {
		if(course == null)
			throw new MoodleRestSelfEnrolUserException(MoodleCommonsErrorMessages
				.notSet("Course"));
		
		if(MoodleTools.isEmpty(course.getId()))
			throw new MoodleRestSelfEnrolUserException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", course));
	}
	public void setPassword(String password) {
		this.password = password;
	}



	/**
	 * Instance id of self enrolment plugin.
	 */
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public boolean hasWarnings() {
		return (this.warnings != null && !this.warnings.isEmpty());
	}

	public Set<MoodleWarning> getWarnings() {
		return this.warnings;
	}


}
