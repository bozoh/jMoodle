package ml.jmoodle.annotations.processors;

import static com.google.common.truth.Truth.assert_;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

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
		List<JavaFileObject> sources = new ArrayList<JavaFileObject>();
		sources.add(JavaFileObjects.forSourceLines("demo.Teapot",
			"package demo;",
			"import ml.jmoodle.annotations.MoodleWSFunction;",
			"@MoodleWSFunction(names={\"Teste1\", \"Teste2\"}) public class Teapot {}")
		);

		sources.add(JavaFileObjects.forSourceLines("demo.Teacup",
			"package demo;",
			"import ml.jmoodle.annotations.MoodleWSFunction;",
			"@MoodleWSFunction(names={\"Teste3\", \"Teste4\"}) public class Teacup {}")
		);

        assert_().about(JavaSourcesSubjectFactory.javaSources())
        	.that(sources)
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
					"TESTE2(\"demo.Teapot\"),",
					"TESTE3(\"demo.Teacup\"),",
					"TESTE4(\"demo.Teacup\");",
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
