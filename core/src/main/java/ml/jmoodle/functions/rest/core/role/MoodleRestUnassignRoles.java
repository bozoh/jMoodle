package ml.jmoodle.functions.rest.core.role;

import java.util.Set;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUserRoleContext;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.configs.expections.MoodleConfigException;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.core.role.exceptions.MoodleRestUnassignRolesException;
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
@MoodleWSFunction(names = { "core_role_unassign_roles", "moodle_role_unassign" })
public class MoodleRestUnassignRoles extends MoodleRestAssignRoles {

	public MoodleRestUnassignRoles(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
	}

	@Override
	public String getFunctionData() throws MoodleRestUnassignRolesException {
		
			try {
				return super.getFunctionData();
			} catch (MoodleWSFucntionException e) {
				throw new MoodleRestUnassignRolesException(e);
			}
	}

	

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		try {
			if ((MoodleTools.compareVersion(mdlConfig.getVersion(), "2.2.0") < 0))
				return "moodle_role_unassign";
		} catch (MoodleConfigException e) {
			throw new MoodleRestUnassignRolesException(e);
		}
		return "core_role_unassign_roles";
	}
	
	
	@Override
	public void setRoles(Set<MoodleUserRoleContext> entities) throws MoodleRestUnassignRolesException {
		try {
			super.setRoles(entities);
		} catch (MoodleWSFucntionException e) {
			throw new MoodleRestUnassignRolesException(e);
		}

	}

	@Override
	public void addRole(MoodleUserRoleContext entity) throws MoodleRestUnassignRolesException {
		try {
			super.addRole(entity);
		} catch (MoodleWSFucntionException e) {
			throw new MoodleRestUnassignRolesException(e);
		}
	}

	@Override
	protected void verifyEntity(MoodleUserRoleContext entity) throws MoodleRestUnassignRolesException {
		if (entity == null)
			throw new MoodleRestUnassignRolesException(
				MoodleCommonsErrorMessages.notSet("MoodleUserRoleContext")
			);

		if (MoodleTools.isEmpty(entity.getUserId()))
			throw new MoodleRestUnassignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "userid", entity)
		);

		if (MoodleTools.isEmpty(entity.getRoleId()))
			throw new MoodleRestUnassignRolesException(
				MoodleCommonsErrorMessages.mustHave("MoodleUserRoleContext", "roleid", entity)
		);
	}
}
