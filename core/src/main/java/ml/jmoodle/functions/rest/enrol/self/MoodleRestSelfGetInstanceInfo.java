package ml.jmoodle.functions.rest.enrol.self;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleEnrolInstanceInfo;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.converters.MoodleEnrolInstanceInfoConverter;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.self.exceptions.MoodleRestSelfGetInstanceInfoException;
import ml.jmoodle.functions.rest.tools.MoodleRestFunctionTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleParamMap;

/**
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "enrol_self_get_instance_info" })
public class MoodleRestSelfGetInstanceInfo extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "3.0.0";

	private Long instanceId = null;

	public MoodleRestSelfGetInstanceInfo(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}
	
	@Override
	public String getFunctionData() throws MoodleRestSelfGetInstanceInfoException {
		if (instanceId == null) 
			throw new MoodleRestSelfGetInstanceInfoException(MoodleCommonsErrorMessages
				.notSet("Instance id"));
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			MoodleParamMap params = new MoodleParamMap();
			params.put("instanceid", instanceId);
			sb.append(params.toParamString());
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException  e) {
			throw new MoodleRestSelfGetInstanceInfoException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() {
		return "enrol_self_get_instance_info";
	}

	@Override
	public MoodleEnrolInstanceInfo doCall() throws MoodleWSFucntionException {
		return (MoodleEnrolInstanceInfo) super.doCall();
	}

	protected MoodleEnrolInstanceInfo processResponse(Document response) throws MoodleRestSelfGetInstanceInfoException  {
		try {
			MoodleEnrolInstanceInfoConverter converter = new MoodleEnrolInstanceInfoConverter();
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength();) {
				Node singleNode = nodeList.item(i);
				Map<String, Object> singleValuesMap = MoodleRestFunctionTools.getSingleAttributes(singleNode);
				MoodleEnrolInstanceInfo entity = converter.toEntity(singleValuesMap);
				entity.setPasswordRequired(null);
				return entity;
			}
		} catch (XPathExpressionException e) {
			throw new MoodleRestSelfGetInstanceInfoException(e);
		}
		return null;
	}

	/**
	 * Instance id of self enrolment plugin.
	 */
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

}
