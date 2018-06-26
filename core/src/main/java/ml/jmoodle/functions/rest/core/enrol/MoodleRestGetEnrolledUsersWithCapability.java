package ml.jmoodle.functions.rest.core.enrol;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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
import ml.jmoodle.commons.Capability;
import ml.jmoodle.commons.MoodleCourse;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.commons.OptionParameter;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.core.enrol.exceptions.MoodleRestGetEnrolledUsersWithCapabilityException;
import ml.jmoodle.functions.rest.core.user.tools.MoodleUserTools;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleParamMap;
import ml.jmoodle.tools.MoodleTools;

/**
 * Create Course(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_enrol_get_enrolled_users_with_capability" })
public class MoodleRestGetEnrolledUsersWithCapability extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.4.0";

	
	private Map<Long, Set<Capability>> courseCapabilities = null;
	private Set<OptionParameter> options = null;

	public MoodleRestGetEnrolledUsersWithCapability(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}

	@Override
	public String getFunctionData() throws MoodleRestGetEnrolledUsersWithCapabilityException {
		if (courseCapabilities==null || courseCapabilities.isEmpty())
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(MoodleCommonsErrorMessages
				.notSet("Course Capabilities"));
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData()
				.substring(0, super.getFunctionData().length() -1)
			);
			
			int i = 0;
			for (Long courseId : courseCapabilities.keySet()) {
				sb.append("&");
				MoodleParamMap courseMap = new MoodleParamMap();
				courseMap.put("coursecapabilities["+i+"][courseid]", courseId);
				sb.append(courseMap.toParamString());
				int y = 0;
				for (Capability cap : courseCapabilities.get(courseId)) {
					MoodleParamMap capMap = new MoodleParamMap();
					sb.append("&");
					capMap.put("coursecapabilities["+i+"][capabilities]["+y+"]", cap.getValue());
					sb.append(capMap.toParamString());
					y++;
				}
				i++;
			}
			if (this.options!=null && !this.options.isEmpty()) {
				sb.append("&");
				sb.append(MoodleRestFunctionTools.serializeOptionParameters(options));
			}
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() {
		return "core_enrol_get_enrolled_users_with_capability";
	}

	/**
	 * Call Create Course WS Function
	 * 
	 * @return A set of MoodleUser
	 * @throws MoodleWSFunctionCallException
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Map<Capability,Set<MoodleUser>>> doCall() throws MoodleWSFucntionException {
		return (Map<Long,Map <Capability, Set<MoodleUser>>>) super.doCall();
	}

	protected Map<Long, Map<Capability,Set<MoodleUser>>> processResponse(Document response) throws MoodleRestGetEnrolledUsersWithCapabilityException {
		Map<Long,Map <Capability, Set<MoodleUser>>> retVal = new HashMap<>();
		MoodleUserTools mut = new MoodleUserTools();
		try {
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				Long courseId = Long.parseLong((String) singleValuesMap.get("courseid"));
				Capability capability = Capability.fromValue((String) singleValuesMap.get("capability"));
				String xpathStr = String.format("/RESPONSE/MULTIPLE/SINGLE/KEY[@name='courseid']/VALUE[.='%s']" +
					"/../following-sibling::KEY[@name='capability']/VALUE[.='%s']/../" + 
					"following-sibling::KEY[@name='users']/MULTIPLE/SINGLE", 
					courseId, capability.getValue()
				);
				NodeList nodeList2 = (NodeList) xPath.compile(xpathStr).evaluate(response,
					XPathConstants.NODESET);
				
				Map<Capability, Set<MoodleUser>> capUsers = new HashMap<>();
				capUsers.put(capability, mut.deSerialize(nodeList2));
				retVal.put(courseId, capUsers);
			}

		} catch (XPathExpressionException e) {
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(
					MoodleWSFucntionException.errorProcessingResponseMsg(response.toString()));
		}

		return retVal;
	}

	public void addOption(OptionParameter optionParameter) {
		if (this.options == null) {
			this.options = new HashSet<>();
		}
		this.options.add(optionParameter);
	}

	public void setOptions(Set<OptionParameter> options) {
		this.options = options;
	}



	public void addCourseCapabilities(MoodleCourse course, Set<Capability> capabilities) throws MoodleRestGetEnrolledUsersWithCapabilityException {
		for (Capability cap : capabilities) {
			this.addCourseCapability(course, cap);
			
		}
	}

	public void addCourseCapability(MoodleCourse course, Capability capability) throws MoodleRestGetEnrolledUsersWithCapabilityException {
		verifyCourse(course);
		verifyCapability(capability);
		if (courseCapabilities == null) {
			courseCapabilities = new HashMap<>();
		}
		Set<Capability> capabilities = courseCapabilities.get(course.getId());
		if (capabilities == null) {
			capabilities =  new HashSet<Capability>();
		}
		capabilities.add(capability);
		courseCapabilities.put(course.getId(), capabilities);
	}

	private void verifyCapability(Capability capability) throws MoodleRestGetEnrolledUsersWithCapabilityException {
		if (capability==null) {
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(MoodleCommonsErrorMessages
				.notSet("Capability"));
		}
	}

	/**
	 * Checks "must have" properties
	 */
	private void verifyCourse(MoodleCourse entity) throws MoodleRestGetEnrolledUsersWithCapabilityException{
		
		if (entity==null)
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(MoodleCommonsErrorMessages
				.notSet("Course"));

		if (MoodleTools.isEmpty(entity.getId()))
			throw new MoodleRestGetEnrolledUsersWithCapabilityException(MoodleCommonsErrorMessages
				.mustHave("Course", "id", entity));
		
	}

}
