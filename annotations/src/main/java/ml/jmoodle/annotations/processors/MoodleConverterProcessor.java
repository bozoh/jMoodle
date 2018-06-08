package ml.jmoodle.annotations.processors;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.CodeBlock.Builder;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class MoodleConverterProcessor {
    private static final String PACKAGE_NAME = "ml.jmoodle.functions.converters";
    private ProcessingEnvironment pe;
    


    public MoodleConverterProcessor(ProcessingEnvironment pe) {
        this.pe = pe;
    }

    public void processElement(TypeElement e) throws IOException {
        String className = e.getSimpleName() + "Converter";
		TypeSpec.Builder codeBuilder = TypeSpec.classBuilder(className)
			.addModifiers(Modifier.PUBLIC)
			.addJavadoc("Generated class DO NOT CHANGE")
			.addJavadoc("\n")
			.addMethod(createToEntityMethod(e));
				
		JavaFile jf = JavaFile.builder(PACKAGE_NAME, codeBuilder.build()).build();
		
		jf.writeTo(this.pe.getFiler());
    }

    private MethodSpec createToEntityMethod(TypeElement e) {
		TypeMirror integertType = this.pe.getElementUtils()
			.getTypeElement(Integer.class.getCanonicalName()).asType();
		TypeMirror longType = this.pe.getElementUtils()
			.getTypeElement(Long.class.getCanonicalName()).asType();
		TypeMirror stringType = this.pe.getElementUtils()
			.getTypeElement(String.class.getCanonicalName()).asType();
		MethodSpec.Builder builder = MethodSpec.methodBuilder("toEntity")
			.addModifiers(Modifier.PUBLIC)
			.addParameter(ParameterizedTypeName
				.get(Map.class, String.class, Object.class), "valuesMap")
			.returns(ClassName.get(e));
		
		CodeBlock.Builder cbBuilder = CodeBlock.builder()
			.addStatement("$T entity = new $T()", ClassName.get(e), ClassName.get(e));
		
		for (Element element : e.getEnclosedElements()) {
			if (element.getKind() == ElementKind.METHOD && 
				element.getSimpleName().toString().startsWith("set") &&
				element.getModifiers().contains(Modifier.PUBLIC)
			) {
				
				//Ignoring 0 and multi value sets
				if (((ExecutableElement) element).getParameters().size() != 1)
					continue;

				String methodName = element.getSimpleName().toString().substring(3);

				TypeMirror returnType = ((ExecutableElement) element).getParameters().get(0).asType(); 

				if (returnType.getKind() == TypeKind.ARRAY)
					continue;

				if (this.pe.getTypeUtils().isSameType(returnType, integertType) 
					|| returnType.getKind() == TypeKind.INT) {
                    cbBuilder = createCodeForInteger(cbBuilder, methodName);
				} else if (this.pe.getTypeUtils().isSameType(returnType, longType)
					|| returnType.getKind() == TypeKind.LONG) {
                    cbBuilder = createCodeForLong(cbBuilder, methodName);
				} else if (this.pe.getTypeUtils().isSameType(returnType, stringType)) {
                    cbBuilder = createCodeForString(cbBuilder, methodName);
				}
			}
		}
		cbBuilder.addStatement("return entity");
		builder.addCode(cbBuilder.build());

		return builder.build();
	}

	private CodeBlock.Builder createCodeForInteger(Builder cbBuilder, String methodName) {
        return cbBuilder
            .beginControlFlow("if (valuesMap.containsKey($S))", methodName.toLowerCase())
			.addStatement("entity.set$L(Integer.valueOf((String) valuesMap.get($S)))", methodName, methodName.toLowerCase())
			.endControlFlow();
    }
    
    private CodeBlock.Builder createCodeForLong(Builder cbBuilder, String methodName) {
        return cbBuilder
            .beginControlFlow("if (valuesMap.containsKey($S))", methodName.toLowerCase())
			.addStatement("entity.set$L(Long.valueOf((String) valuesMap.get($S)))", methodName, methodName.toLowerCase())
			.endControlFlow();
    }
    
    private CodeBlock.Builder createCodeForString(Builder cbBuilder, String methodName) {
        return cbBuilder
            .beginControlFlow("if (valuesMap.containsKey($S))", methodName.toLowerCase())
			.addStatement("entity.set$L((String) valuesMap.get($S))", methodName, methodName.toLowerCase())
			.endControlFlow();
	}
}