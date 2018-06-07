package ml.jmoodle.annotations.processors;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import ml.jmoodle.annotations.MoodleWSFunction;

public class MoodleWSFunctionProcessor {
	private static final String PACKAGE_NAME = "ml.jmoodle.functions";
	private static final String CLASS_NAME = "MoodleWSFunctions";

	private ProcessingEnvironment pe;
	private TypeSpec.Builder codeBuilder = null;
    

    public MoodleWSFunctionProcessor(ProcessingEnvironment pe) {
        this.pe = pe;
    }

    public void processElement(TypeElement e) {
		this.codeBuilder = TypeSpec.enumBuilder(CLASS_NAME)
			.addSuperinterface(ClassName.get(Serializable.class))
			.addModifiers(Modifier.PUBLIC);

		MoodleWSFunction mdlWsFnc = e.getAnnotation(MoodleWSFunction.class);
		for (String fncName : mdlWsFnc.names()) {
			codeBuilder.addEnumConstant(fncName.toUpperCase(), 
				TypeSpec.anonymousClassBuilder("$S", e.getQualifiedName()).build());
		}
		
		codeBuilder.addField(String.class, "className", Modifier.PRIVATE, Modifier.FINAL)
			.addMethod(MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addParameter(String.class, "className")
				.addStatement("this.$N = $N", "className", "className").build()
			)
			.addMethod(MethodSpec.methodBuilder("getValue")
				.addModifiers(Modifier.PUBLIC)
				.addStatement("return this.$N", "className")
				.returns(String.class).build()
		);
		
		
	}
	
	public void saveGeneratedFile() throws IOException {
		if (this.codeBuilder != null) {
			JavaFile jf = JavaFile.builder(PACKAGE_NAME, this.codeBuilder.build()).build();
			jf.writeTo(this.pe.getFiler());
		}
	}
}