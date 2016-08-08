package ml.jmoodle.authentications;


/**
 * Interface for Moodle WS authentication methods
 * 
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
public interface MoodleAuthentication {
	/**
	 * Get the authentication string
	 * @return Authentication String
	 */
	String getAuthentication();
}
