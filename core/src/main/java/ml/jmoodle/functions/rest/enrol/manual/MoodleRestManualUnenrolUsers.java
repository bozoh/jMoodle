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
import ml.jmoodle.functions.rest.enrol.manual.exceptions.MoodleRestManualUnenrolUsersException;
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
@MoodleWSFunction(names = { "enrol_manual_unenrol_users"})
public class MoodleRestManualUnenrolUsers extends MoodleWSBaseFunction {
	private static final String SINCE_VERSION = "3.0.0";

	private Set<MoodleManualEnrolment> enrolments = null;

	public MoodleRestManualUnenrolUsers(MoodleConfig moodleConfig) throws MoodleWSFucntionException, MoodleConfigException {
		super(moodleConfig);
	}


	
	@Override
	public String getFunctionData() throws MoodleRestManualUnenrolUsersException {
		if (enrolments == null || enrolments.isEmpty()) 
			throw new MoodleRestManualUnenrolUsersException(MoodleCommonsErrorMessages
				.notSet("Manual Enrolments"));
		
		MoodleManualEnrolmentTools tool = new MoodleManualEnrolmentTools();
		try {
			StringBuilder sb = new StringBuilder(super.getFunctionData());
			sb.append(tool.serialize(enrolments));
			return sb.toString();
		} catch (UnsupportedEncodingException | MoodleWSFucntionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestManualUnenrolUsersException(e);
		}

	}

	@Override
	public String getSinceVersion() {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() {
		return "enrol_manual_unenrol_users";
	}

	@Override
	public Object doCall() throws MoodleWSFucntionException {
		return super.doCall();
	}

	protected Object processResponse(Document response)  {
		return null;
	}


	public void setEnrolments(Set<MoodleManualEnrolment> enrolments) throws MoodleRestManualUnenrolUsersException {
		for (MoodleManualEnrolment e : enrolments) {
			this.addEnrolment(e);
		}
	}

	public void addEnrolment(MoodleManualEnrolment enrolment) throws MoodleRestManualUnenrolUsersException {
		verify(enrolment);
		if (this.enrolments == null) {
			this.enrolments = new HashSet<>();
		}
		enrolment.setTimeStart(null);
		enrolment.setTimeEnd(null);
		enrolment.setSuspend(null);
		this.enrolments.add(enrolment);
	}



	private void verify(MoodleManualEnrolment enrolment) throws MoodleRestManualUnenrolUsersException {
		if(enrolment == null)
			throw new MoodleRestManualUnenrolUsersException(MoodleCommonsErrorMessages
				.notSet("Manual Enrolment"));
		
		if(MoodleTools.isEmpty(enrolment.getCourseId()))
			throw new MoodleRestManualUnenrolUsersException(MoodleCommonsErrorMessages
				.mustHave("Manual Enrolment", "courseId", enrolment));

		if(MoodleTools.isEmpty(enrolment.getUserId()))
			throw new MoodleRestManualUnenrolUsersException(MoodleCommonsErrorMessages
				.mustHave("Manual Enrolment", "userId", enrolment));
	}

}
