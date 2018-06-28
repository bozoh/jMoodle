package ml.jmoodle.functions.rest.enrol.manual;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleManualEnrolment;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.enrol.manual.exceptions.MoodleRestManualEnrolUsersException;
import ml.jmoodle.functions.rest.enrol.manual.tools.MoodleManualEnrolmentTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "enrol_manual_enrol_users", "moodle_enrol_manual_enrol_users" })
public class MoodleRestManualEnrolUsers extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "2.0.0";

	private Set<MoodleManualEnrolment> enrolments = null;

	public MoodleRestManualEnrolUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	
	@Override
	public String getFunctionData() throws MoodleRestManualEnrolUsersException {
		if (enrolments == null || enrolments.isEmpty()) 
			throw new MoodleRestManualEnrolUsersException(MoodleCommonsErrorMessages
				.notSet("Manual Enrolments"));
		
		MoodleManualEnrolmentTools tool = new MoodleManualEnrolmentTools();
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			sb.append(tool.serialize(enrolments));
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestManualEnrolUsersException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleRestManualEnrolUsersException {
		// this funtcions changes the name in 2.2.0
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_enrol_manual_enrol_users";
		} catch (MoodleConfigException e) {
			throw new MoodleRestManualEnrolUsersException(e);
		}
		return "enrol_manual_enrol_users";
	}

	@Override
	public Object doCall() throws MoodleWSFucntionException {
		return super.doCall();
	}

	protected Object processResponse(Document response)  {
		return null;
	}


	public void setEnrolments(Set<MoodleManualEnrolment> enrolments) throws MoodleRestManualEnrolUsersException {
		for (MoodleManualEnrolment e : enrolments) {
			this.addEnrolment(e);
		}
	}

	public void addEnrolment(MoodleManualEnrolment enrolment) throws MoodleRestManualEnrolUsersException {
		verify(enrolment);
		if (this.enrolments == null) {
			this.enrolments = new HashSet<>();
		}
		this.enrolments.add(enrolment);
	}



	private void verify(MoodleManualEnrolment enrolment) throws MoodleRestManualEnrolUsersException {
		if(enrolment == null)
			throw new MoodleRestManualEnrolUsersException(MoodleCommonsErrorMessages
				.notSet("Manual Enrolment"));
		
		if(MoodleTools.isEmpty(enrolment.getRoleId()))
			throw new MoodleRestManualEnrolUsersException(MoodleCommonsErrorMessages
				.mustHave("Manual Enrolment", "roleId", enrolment));

		if(MoodleTools.isEmpty(enrolment.getCourseId()))
			throw new MoodleRestManualEnrolUsersException(MoodleCommonsErrorMessages
				.mustHave("Manual Enrolment", "courseId", enrolment));

		if(MoodleTools.isEmpty(enrolment.getUserId()))
			throw new MoodleRestManualEnrolUsersException(MoodleCommonsErrorMessages
				.mustHave("Manual Enrolment", "userId", enrolment));
	}

}
