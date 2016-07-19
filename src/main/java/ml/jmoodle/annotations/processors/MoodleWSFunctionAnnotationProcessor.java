package ml.jmoodle.annotations.processors;

import java.awt.dnd.DnDConstants;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import ml.jmoodle.annotations.MoodleWSFunction;

public class MoodleWSFunctionAnnotationProcessor extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

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
				codeGen.append(fncName.toUpperCase())
					.append("(\"")
					.append(classElement.getQualifiedName())
					.append("\")");
			}
		}
		try {
			codeGen.append(";\n}");
			JavaFileObject jfo = this.filer.createSourceFile("ml.jmoodle.functions.rest.MoodleWSFunctions");
			BufferedWriter bw = new BufferedWriter(jfo.openWriter());
			bw.write(codeGen.toString().toCharArray());
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	private StringBuilder getHeader() {

		StringBuilder builder = new StringBuilder("package ml.jmoodle.functions.rest;\n\n");
		builder.append("import java.io.Serializable;\n\n")
				.append("public enum MoodleWSFunctions implements Serializable {\n");

		
		return builder;
	}

}
