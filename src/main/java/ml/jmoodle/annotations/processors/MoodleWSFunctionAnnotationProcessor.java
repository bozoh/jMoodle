package ml.jmoodle.annotations.processors;

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
		// TODO Auto-generated method stub

		JavaFileObject jfo = null;
		BufferedWriter bw = null;
		try {
			for (Element e : roundEnv.getElementsAnnotatedWith(MoodleWSFunction.class)) {
				if (e.getKind() == ElementKind.CLASS) {
					if (jfo == null) {
						jfo = this.filer.createSourceFile("ml.jmoodle.functions.rest.Observer");

						bw = new BufferedWriter(jfo.openWriter());
						bw.write(getHeader());
					}
					this.messager.printMessage(Diagnostic.Kind.NOTE, e.getSimpleName());

					MoodleWSFunction mdlWsFnc = e.getAnnotation(MoodleWSFunction.class);

					TypeElement classElement = (TypeElement) e;
					bw.append("this.functions.put(\"");
					bw.append(mdlWsFnc.name());
					bw.append("\", \"");
					bw.append(classElement.getQualifiedName());
					bw.append("\");");
					bw.newLine();
				}

			}
			if (bw != null) {
				bw.append("}");
				bw.append("}");
				bw.flush();
				bw.close();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub

		return true;

		// return false;
	}

	private char[] getHeader() {
		
		

		StringBuilder builder = new StringBuilder("package ml.jmoodle.functions.rest;");
		builder.append("\n\nimport java.util.HashMap;\n").append("import java.util.Map;")
				.append("\n\npublic class Observer {\n")
				.append("Map<String, String> functions=new HashMap<String, String>();\n\n")
				.append("public Observer() {\n");
		return builder.toString().toCharArray();
	}

}
