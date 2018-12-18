package com.cleversonledur.crudify;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;


import com.google.auto.service.AutoService;
import com.sun.org.apache.bcel.internal.classfile.LocalVariable;

@SupportedAnnotationTypes(
                "Crudify")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {

            Element enclosing = annotation.getEnclosingElement();

            processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.WARNING,
                                            "Crudify Message: getNestingKind " + annotation.getNestingKind()) ;
            processingEnv.getMessager()
                            .printMessage(Diagnostic.Kind.WARNING,
                                            "Crudify Message: ENCLOSING ELEMENT " + enclosing) ;

            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            annotatedElements.forEach(element -> {

                ElementKind elementKind = element.getKind();

                VariableElement localVariable = (VariableElement) element;
                TypeMirror type = ((VariableElement) element).asType();
                String elementName = element.getSimpleName().toString();
                Class<? extends Element> elementClass = element.getClass();
                String elementClassName = elementClass.getName();

                generateRepository(elementName, elementClassName);
                generateService(elementName, elementClassName);
                generateDtos(elementName, elementClassName);
                generateEndpoints(element);


                processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.WARNING,
                                                "Crudify Message: ClassName " + elementClassName) ;

                processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.WARNING,
                                                "Crudify Message: ElementName " + elementName) ;

                processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.WARNING,
                                                "Crudify Message: Element " + element) ;

                processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.WARNING,
                                                "Crudify Message: Modifiers " + localVariable.getModifiers()) ;

                processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.WARNING,
                                                "Crudify Message: Modifiers " + type) ;
//GETTING CLASS NAME
//                String className = null;
//                Set<? extends Element> rootElements = roundEnv.getRootElements();
//                for (Element rootElement : rootElements) {
//                    rootElement.
//                    List<? extends Element> rootElementEnclosingElements = rootElement.getEnclosedElements();
//                    for (Element rootElementEnclosingElement : rootElementEnclosingElements) {
//                        processingEnv.getMessager()
//                                        .printMessage(Diagnostic.Kind.WARNING,
//                                                        "Crudify Message: INSIDE " + rootElementEnclosingElement.getSimpleName()) ;
//                        if(rootElementEnclosingElement.getSimpleName().equals(elementName)){
//                            className = rootElementEnclosingElement.getSimpleName().toString();
//                        }
//                    }
//                }

//                processingEnv.getMessager()
//                                .printMessage(Diagnostic.Kind.WARNING,
//                                                "Crudify Message: ClassName " + className) ;


            });




        }
        return true;
    }

    private void generateEndpoints(Element element) {
    }

    private void generateRepository(String elementName, String elementClassName) {
    }

    private void generateService(String elementName, String elementClassName) {
    }

    private void generateDtos(String elementName, String elementClassName) {
    }
    
    

//    private void writeBuilderFile(String className) throws IOException {
//
//        String packageName = null;
//        int lastDot = className.lastIndexOf('.');
//        if (lastDot > 0) {
//            packageName = className.substring(0, lastDot);
//        }
//
//        String simpleClassName = className.substring(lastDot + 1);
//        String builderClassName = className + "Builder";
//        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
//
//        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
//        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
//
//            if (packageName != null) {
//                out.print("package ");
//                out.print(packageName);
//                out.println(";");
//                out.println();
//            }
//
//            out.print("public class ");
//            out.print(builderSimpleClassName);
//            out.println(" {");
//            out.println();
//
//            out.print("    private ");
//            out.print(simpleClassName);
//            out.print(" object = new ");
//            out.print(simpleClassName);
//            out.println("();");
//            out.println();
//
//            out.print("    public ");
//            out.print(simpleClassName);
//            out.println(" build() {");
//            out.println("        return object;");
//            out.println("    }");
//            out.println();
//
//
//            out.println("}");
//
//        }
//    }
}


