package ml.jmoodle.functions.rest.user;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.Field;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.user.exceptions.MoodleRestGetUsersByFieldException;
import ml.jmoodle.functions.rest.user.tools.MoodleUserTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;


/**
 * Get User function by fields
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users_by_field" })
public class MoodleRestGetUsersByField extends MoodleWSBaseFunction {

	private Field field;
	private MoodleUserTools tool;
	private Set<String> fieldValues;
	private static final String SINCE_VERSION = "2.5.0";

	public MoodleRestGetUsersByField(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.tool = new MoodleUserTools();
		this.fieldValues = new HashSet<String>();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (field == null)
			throw new MoodleRestGetUsersByFieldException(MoodleCommonsErrorMessages.notSet("Field"));

		if (fieldValues == null || fieldValues.isEmpty())
			throw new MoodleRestGetUsersByFieldException(MoodleCommonsErrorMessages.notSet("Field Values"));
			StringBuilder sb = new StringBuilder(super.getFunctionData());
		try {
			sb.append(serializeFields());
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestGetUsersByFieldException(e);
		}
	};

	private String serializeFields() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder(MoodleTools.encode("field"));
		sb.append("=").append(MoodleTools.encode(field.getValue())).append("&");
		int i = 0;
		for (String value : this.fieldValues) {
			sb.append(MoodleTools.encode("values[")).append(i++)
				.append(MoodleTools.encode("]")).append("=")
				.append(MoodleTools.encode(value)).append("&");
			
		}
		return sb.substring(0, sb.length() - 1);
	}

	
	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_user_get_users_by_field";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}

	@Override
	public Set<MoodleUser> processResponse(Document response) throws MoodleRestGetUsersByFieldException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
				XPathConstants.NODESET);
			return tool.deSerialize(nodeList);
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetUsersByFieldException(e);
		}

	}

	
	public void setFiledValues(Field field, Set<String> values) throws MoodleRestGetUsersByFieldException {
		verifyFiled(field);
		this.field = field;
		for (String value : values) {
			verifyValue(value);
			this.fieldValues.add(value);
		}
	}
	
	private void verifyFiled(Field field) throws MoodleRestGetUsersByFieldException {
		if (this.field != null && this.field != field)
			throw new MoodleRestGetUsersByFieldException(
					MoodleRestGetUsersByFieldException.fieldAlredySeted(this.field, field));
	}

	private void verifyValue(String value) throws MoodleRestGetUsersByFieldException {
		if (MoodleTools.isEmpty(value))
			throw new MoodleRestGetUsersByFieldException(MoodleCommonsErrorMessages.notSet("Field Value"));
	}
}
