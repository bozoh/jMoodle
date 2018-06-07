package ml.jmoodle.annotations.processors;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Before;
import org.junit.Test;

public class MoodleWSFunctionProcessorTest {

	private JMoodleAnnotationProcessor processor;

    @Before
    public void init() {
        processor = new JMoodleAnnotationProcessor();
	}
	
	@Test
	public void generateEmptyStubbedClass() {
        assert_().about(javaSource())
        	.that(JavaFileObjects.forSourceLines("demo.Teapot",
				"package demo;",
				"import ml.jmoodle.annotations.MoodleWSFunction;",
				"@MoodleWSFunction(names={\"Teste1\", \"Teste2\"}) public class Teapot {}")
			)
            .processedWith(processor)
			.compilesWithoutError()
			.and()
			.generatesSources(JavaFileObjects
				.forSourceLines("ml.jmoodle.functions.MoodleWSFunctions",
					"package ml.jmoodle.functions;",
					"import java.io.Serializable;",
					"import java.lang.String;",
					"public enum MoodleWSFunctions implements Serializable {",
					"TESTE1(\"demo.Teapot\"),",
					"TESTE2(\"demo.Teapot\");",
					"private final String className;",
					"private MoodleWSFunctions(String className) {",
					"this.className = className;",
					"}",
					"public String getValue() {",
					"return this.className;",
					"}",
					"}"
				)
			);
	} 
	
}
