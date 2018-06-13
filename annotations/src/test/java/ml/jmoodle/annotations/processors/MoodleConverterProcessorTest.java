package ml.jmoodle.annotations.processors;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Before;
import org.junit.Test;

import ml.jmoodle.annotations.MoodleConverter;

@MoodleConverter
public class MoodleConverterProcessorTest  {

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
				"import ml.jmoodle.annotations.MoodleConverter;",
				"@MoodleConverter",
				"public class Teapot {",
				"private String testeString;",
				"private Long testeLong;",
				"private Integer testeInteger;",
				"private String readOnly;",
				"public String getTesteString() {",
				"return this.testeString;",
				"}",
				"public Long getTesteLong() {",
				"return this.testeLong;",
				"}",
				"public Integer getTesteInteger() {",
				"return this.testeInteger;",
				"}",
				"public String getReadOnly() {",
				"return this.readOnly;",
				"}",
				"public void setTesteString(String s) {",
				"this.testeString = s;",
				"}",
				"public void setTesteLong(Long l) {",
				"this.testeLong = l;",
				"}",
				"public void setTesteBoolean(Boolean b) {",
				"}",
				"public void setTesteBln(boolean b) {",
				"}",
				"public void setTesteInteger(Integer i) {",
				"this.testeInteger = i;",
				"}",
				"public void setTesteInt(int i) {",
				"this.testeInteger = i;",
				"}",
				"public void setTesteLng(long l) {",
				"this.testeLong = l;",
				"}",
				"public void setTesteIntegerTesteString(Integer i, String s) {",
				"this.testeInteger = i;",
				"this.testeString = s;",
				"}",
				"private void setReadOnly(String readOnly) {",
				"this.readOnly = readOnly;",
				"}",
				"}"
				)
			)
            .processedWith(processor)
			.compilesWithoutError()
			.and()
			.generatesSources(JavaFileObjects
				.forSourceLines("ml.jmoodle.functions.converters.TeapotConverter",
					"package ml.jmoodle.functions.converters;",
					"import demo.Teapot;",
					"import java.lang.Object;",
					"import java.lang.String;",
					"import java.util.Map;",
					"public class TeapotConverter {",
					"public Teapot toEntity(Map<String, Object> valuesMap) {",
					"Teapot entity = new Teapot();",
					"if(valuesMap.containsKey(\"testestring\")) {",
					"entity.setTesteString((String)valuesMap.get(\"testestring\"));",
					"}",
					"if(valuesMap.containsKey(\"testelong\")) {",
					"entity.setTesteLong(Long.valueOf((String)valuesMap.get(\"testelong\")));",
					"}",
					"if(valuesMap.containsKey(\"testeboolean\")) {",
					"entity.setTesteBoolean(\"1\".equals((String)valuesMap.get(\"testeboolean\")));",
					"}",
					"if(valuesMap.containsKey(\"testebln\")) {",
					"entity.setTesteBln(\"1\".equals((String)valuesMap.get(\"testebln\")));",
					"}",
					"if(valuesMap.containsKey(\"testeinteger\")) {",
					"entity.setTesteInteger(Integer.valueOf((String)valuesMap.get(\"testeinteger\")));",
					"}",
					"if(valuesMap.containsKey(\"testeint\")) {",
					"entity.setTesteInt(Integer.valueOf((String)valuesMap.get(\"testeint\")));",
					"}",
					"if(valuesMap.containsKey(\"testelng\")) {",
					"entity.setTesteLng(Long.valueOf((String)valuesMap.get(\"testelng\")));",
					"}",
					"return entity;",
					"}}"
				)
			);
	}

	


}
