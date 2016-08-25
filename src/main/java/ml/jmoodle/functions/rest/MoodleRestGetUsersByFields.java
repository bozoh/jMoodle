package ml.jmoodle.functions.rest;

import java.io.Serializable;
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
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.MoodleWSFunctionCall;
import ml.jmoodle.functions.exceptions.MoodleRestGetUsersByFieldsException;
import ml.jmoodle.functions.exceptions.MoodleRestUsersCommonsErrorMessages;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.tools.MoodleRestUserFunctionsTools;

/**
 * Get User function by fields
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users_by_fields" })
public class MoodleRestGetUsersByFields extends MoodleWSBaseFunction {

	private Field field;
	private MoodleRestUserFunctionsTools userFunctionsTools;
	private Set<String> fieldValues;
	private static final String SINCE_VERSION = "2.5.0";

	public MoodleRestGetUsersByFields(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.userFunctionsTools = new MoodleRestUserFunctionsTools();
		this.fieldValues = new HashSet<String>();
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (getField() == null)
			throw new MoodleRestGetUsersByFieldsException(MoodleRestUsersCommonsErrorMessages.notSet("Field"));

		if (getValues() == null || getValues().isEmpty())
			throw new MoodleRestGetUsersByFieldsException(MoodleRestUsersCommonsErrorMessages.notSet("Field Values"));

		try {
			StringBuilder data = new StringBuilder(super.getFunctionData());
			data.append(getUserFuntionsTools().serliazeFields(getField(), getValues()));
			return data.toString();
		} catch (UnsupportedEncodingException e) {
			throw new MoodleRestGetUsersByFieldsException(e);
		}
	};

	public Set<String> getValues() {
		return this.fieldValues;
	}

	public Field getField() {
		return this.field;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		return "core_user_get_users_by_fields";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}

	@Override
	public Set<MoodleUser> processResponse(Document response) throws MoodleRestGetUsersByFieldsException {
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.compile("/RESPONSE/MULTIPLE/SINGLE").evaluate(response,
					XPathConstants.NODESET);
			return getUserFuntionsTools().unSerializeUsers(nodeList);
		} catch (XPathExpressionException e) {
			throw new MoodleRestGetUsersByFieldsException(e);
		}

	}

	public void addValue(String value) throws MoodleRestGetUsersByFieldsException {
		if (value == null || value.trim().isEmpty())
			throw new MoodleRestGetUsersByFieldsException(MoodleRestUsersCommonsErrorMessages.notSet("Field Value"));
		this.fieldValues.add(value);

	}

	public void setValues(Set<String> values) throws MoodleRestGetUsersByFieldsException {
		for (String value : values) {
			this.addValue(value);
		}
	}

	public void setField(Field field) throws MoodleRestGetUsersByFieldsException {
		if (this.field != null && !this.field.equals(field))
			throw new MoodleRestGetUsersByFieldsException(
					MoodleRestGetUsersByFieldsException.fieldAlredySeted(this.field, field));
		this.field = field;

	}

	/**
	 * @return the userFunctionsTools
	 */
	private MoodleRestUserFunctionsTools getUserFuntionsTools() {
		return userFunctionsTools;
	}

	public enum Field implements Serializable {
		ID("id"), IDNUMBER("idnumber"), USERNAME("username"), EMAIL("email");

		private String fieldName;

		private Field(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public String toString() {
			return fieldName;
		}

	}

}
