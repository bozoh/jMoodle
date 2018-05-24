package ml.jmoodle.functions.rest.user;

import java.util.Set;

import org.w3c.dom.Document;

import ml.jmoodle.annotations.MoodleWSFunction;
import ml.jmoodle.commons.MoodleUser;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.MoodleWSBaseFunction;
import ml.jmoodle.functions.exceptions.MoodleWSFucntionException;
import ml.jmoodle.functions.rest.user.exceptions.MoodleRestGetUsersByIdException;



/**
 * Get User function by user id
 *
 *
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@MoodleWSFunction(names = { "core_user_get_users_by_id", "moodle_user_get_users_by_id" })
public class MoodleRestGetUsersById extends MoodleWSBaseFunction {

	public MoodleRestGetUsersById(MoodleConfig moodleConfig) throws MoodleWSFucntionException {
		super(moodleConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSinceVersion() throws MoodleWSFucntionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFunctionName() throws MoodleWSFucntionException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<MoodleUser> doCall() throws MoodleWSFucntionException {
		return (Set<MoodleUser>) super.doCall();
	}

	
	@Override
	public String getFunctionData() throws MoodleWSFucntionException {
		//TODO
		return null;
	}
	
	@Override
	protected Set<MoodleUser> processResponse(Document response) throws MoodleWSFucntionException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addId(Long id) throws MoodleRestGetUsersByIdException{
		// TODO Auto-generated method stub
		
	}

	public void setIds(Set<Long> ids) throws MoodleRestGetUsersByIdException {
		for (Long id : ids) {
			this.addId(id);
		}
	}

}
