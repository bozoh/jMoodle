package ml.jmoodle.annotations.processors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import ml.jmoodle.annotations.MoodleWSFunction;

public class MoodleWSFunctionAnnotationProcessor extends AbstractProcessor {


	private final String PACKAGE_NAME = "ml.jmoodle.functions";
	private final String CLASS_NAME = "MoodleWSFunctions";

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotataions = new LinkedHashSet<String>();
		annotataions.add(MoodleWSFunction.class.getCanonicalName());
		return annotataions;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_6;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		StringBuilder codeGen = getHeader();

		boolean first = true;

		for (Element e : roundEnv.getElementsAnnotatedWith(MoodleWSFunction.class)) {
			TypeElement classElement = (TypeElement) e;
			MoodleWSFunction mdlWsFnc = e.getAnnotation(MoodleWSFunction.class);

			for (String fncName : mdlWsFnc.names()) {
				if (first) {
					first = false;
				} else {
					codeGen.append(",\n");
				}
				codeGen.append(fncName.toUpperCase()).append("(\"").append(classElement.getQualifiedName())
						.append("\")");
			}
		}
		try {
			//Prevents javax.annotation.processing.FilerException: Attempt to recreate a file, because 
			//process is calling more then once
			if (!first) {
				codeGen.append(";\n\n");
				JavaFileObject jfo = processingEnv.getFiler().createSourceFile(PACKAGE_NAME + "." + CLASS_NAME);
				BufferedWriter bw = new BufferedWriter(jfo.openWriter());
				bw.write(codeGen.toString().toCharArray());
				bw.write(getFooter().toCharArray());
				bw.flush();
				bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	private StringBuilder getHeader() {

		StringBuilder builder = new StringBuilder("package ");
		builder.append(PACKAGE_NAME).append(";\n\n")
			.append("import java.io.Serializable;\n\n")
			.append("public enum ").append(CLASS_NAME).append(" implements Serializable {\n");

		return builder;
	}

	private String getFooter() {
		StringBuilder builder = new StringBuilder("String className;\n\n");
		builder.append("private ").append(CLASS_NAME).append("(String className) {\n")
				.append("this.className = className;\n").append("}\n\n").append("public String getValue() {\n")
				.append("return this.className;\n").append("}\n}\n");
//				.append("public Object getMoodleWSFunction() throws ClassNotFoundException {\n")
//				.append("return Class.forName(className);\n").append("}\n}");
		return builder.toString();
	}

	// package ml.jmoodle.functions.rest;
	//
	// import java.io.Serializable;
	//
	// public enum MoodleWSFunctions implements Serializable {
	// JUST_A_TEST("ml.jmoodle.functions.rest.AddUser"),
	// JUST_A_TST("ml.jmoodle.functions.rest.AddUser"),
	// MOD_WIKI_OVERRIDELOCK("mod/wiki:overridelock");
	//
	// String className;
	//
	// private MoodleWSFunctions(String className) {
	// this.className = className;
	// }
	//
	// private String className() {
	// return this.className;
	// }
	//
	// public Object getMoodleWSFunction() throws ClassNotFoundException {
	// return Class.forName(className);
	// }
	// }

}
