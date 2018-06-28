package ml.jmoodle.tests.functions.rest.enrol.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import ml.jmoodle.commons.MoodleManualEnrolment;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.rest.enrol.manual.tools.MoodleManualEnrolmentTools;

/**
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2018 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 **/
public class MoodleManualEnrolmentToolsTest {
    

    MoodleManualEnrolmentTools tool;
    @BeforeClass
	public static void setUp() {
		FixtureFactoryLoader.loadTemplates("ml.jmoodle.tests");
    }
    
    @Before
    public void beforeEach() {
        tool = new MoodleManualEnrolmentTools();
    }
    
    @Test
	public void serialize_test() throws UnsupportedEncodingException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<MoodleManualEnrolment> expected = Fixture.from(MoodleManualEnrolment.class).gimme(106,"valid");
		
		String actual = URLDecoder.decode(
			tool.serialize(expected), MoodleConfig.DEFAULT_ENCODING
        );
        
        doSerilazationAsserts(expected, actual);

	}



	private void doSerilazationAsserts(List<MoodleManualEnrolment> expected, String actual) {
    // enrolments[0][roleid]= int
    // enrolments[0][userid]= int
    // enrolments[0][courseid]= int
    // enrolments[0][timestart]= int
    // enrolments[0][timeend]= int
    // enrolments[0][suspend]= int
        int i = 0;
        for (MoodleManualEnrolment entity : expected) {
            assertThat(actual).contains("enrolments["+i+"][roleid]=");
            assertThat(actual).contains(entity.getRoleId().toString());
            assertThat(actual).contains("enrolments["+i+"][userid]=");
            assertThat(actual).contains(entity.getUserId().toString());
            assertThat(actual).contains("enrolments["+i+"][courseid]=");
            assertThat(actual).contains(entity.getCourseId().toString());
            assertThat(actual).contains("enrolments["+i+"][timestart]=");
            assertThat(actual).contains(entity.getTimeStart().toString());
            assertThat(actual).contains("enrolments["+i+"][timeend]=");
            assertThat(actual).contains(entity.getTimeEnd().toString());
            assertThat(actual).contains("enrolments["+i+"][suspend]=");
            assertThat(actual).contains(entity.getSuspend().toString());
            i++;
        }
	}

}
