package ml.jmoodle.annotations.processors;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;

import ml.jmoodle.annotations.MoodleConverter;
import ml.jmoodle.annotations.MoodleWSFunction;

@AutoService(Processor.class)
public class JMoodleAnnotationProcessor extends AbstractProcessor {

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotataions = new LinkedHashSet<String>();
		annotataions.add(MoodleWSFunction.class.getCanonicalName());
		annotataions.add(MoodleConverter.class.getCanonicalName());
		return annotataions;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_8;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
	
		MoodleWSFunctionProcessor mwsfp = new MoodleWSFunctionProcessor(processingEnv);
		for (Element e : roundEnv.getElementsAnnotatedWith(MoodleWSFunction.class)) {
			try {
				mwsfp.processElement((TypeElement) e);
			} catch (IOException e1) {
				processingEnv.getMessager()
					.printMessage(Diagnostic.Kind.ERROR, 
					"Error in processing", e);
				e1.printStackTrace();
				return false;
			}
			
		}
	
		MoodleConverterProcessor mcp = new MoodleConverterProcessor(processingEnv);
		for (Element e : roundEnv.getElementsAnnotatedWith(MoodleConverter.class)) {
			try {
				mcp.processElement((TypeElement) e);
			} catch (IOException e1) {
				processingEnv.getMessager()
					.printMessage(Diagnostic.Kind.ERROR, 
					"Error in processing", e);
				e1.printStackTrace();
				return false;
			}
		}
	
		return true;
	}
}
