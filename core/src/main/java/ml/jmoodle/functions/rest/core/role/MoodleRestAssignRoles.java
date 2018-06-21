package ml.jmoodle.functions.rest.core.role;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.role.exceptions.MoodleRestAssignRolesException;
import ml.jmoodle.functions.rest.core.role.tools.MoodleUserRoleContextTools;
import ml.jmoodle.tools.MoodleCommonsErrorMessages;
import ml.jmoodle.tools.MoodleTools;

/**
 * Detete User(s) Function
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_role_assign_roles", "moodle_role_assign" })
public class MoodleRestAssignRoles extends MoodleWSBaseFunction {

	private Set<MoodleUserRoleContext> roles;
	private MoodleUserRoleContextTools tool;
	private static final String SINCE_VERSION = "2.0.0";

	public MoodleRestAssignRoles(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		this.roles = new HashSet<MoodleUserRoleContext>();
		this.tool = new MoodleUserRoleContextTools();
	
	}

	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		if (this.roles==null || this.roles.isEmpty())
			throw new MoodleRestAssignRolesException(MoodleCommonsErrorMessages.notSet("Users"));
		try {
			StringBuilder fnctData = new StringBuilder(super.getFunctionData());
			fnctData.append(tool.serialize(this.roles));
			return fnctData.toString();
		} catch (UnsupportedEncodingException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new MoodleRestAssignRolesException(e);
		}
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		return SINCE_VERSION;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_role_assign";
		} catch (MoodleConfigException e) {
			throw new MoodleRestAssignRolesException(e);
		}
		return "core_role_assign_roles";
	}
	
	
	@Override
	public Object  doCall() throws MoodleWSFucntionException {
		return super.doCall();
	}
	
	@Override
	protected Object processResponse(Document response) throws MoodleWSFucntionException {
		return null;
	}

	

	public void setRoles(Set<MoodleUserRoleContext> entities) throws MoodleWSFucntionException {
		for (MoodleUserRoleContext moodleUser : entities) {
			addRole(moodleUser);
		}

	}

	public void addRole(MoodleUserRoleContext entity) throws MoodleWSFucntionException {
		verifyEntity(entity);
		this.roles.add(entity);
	}

	protected void verifyEntity(MoodleUserRoleContext entity) throws MoodleWSFucntionException {
		if (entity == null)
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.notSet("MoodleUserRoleContext")
			);

		if (MoodleTools.isEmpty(entity.getUserId()))
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "userid", entity)
		);

		if (MoodleTools.isEmpty(entity.getContextId()))
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "contextid", entity)
		);

		if (MoodleTools.isEmpty(entity.getInstanceId()))
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "instanceid", entity)
		);

		if (MoodleTools.isEmpty(entity.getRoleId()))
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "roleid", entity)
		);

		if (entity.getContextLevel() == null)
			throw new MoodleRestAssignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "contextlevel", entity)
		);
	}

}
