package com.cleversonledur.crudify.generators.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.PrintWriter;
import java.util.List;

import com.cleversonledur.crudify.generators.Generator;

public class RepositoryGenerator implements Generator {

    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        List<? extends Element> members = processingEnv.getElementUtils().getAllMembers(element);

        String className = element.getSimpleName().toString();

        String builderClassName = "Crudify" + className + "Repository";

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(className, out);
                printImportInfo(className, out);
                printClassHeader(className, builderClassName, out);
                //TODO: Create additional queries for special controllers based on the model annotations.

                printFooterClass(out);

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }

    }

    private void printFooterClass(PrintWriter out) {
        out.println("}");
    }

    private void printClassHeader(String className, String builderClassName, PrintWriter out) {
        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        out.println("public interface " + builderSimpleClassName + "Repository extends MongoRepository<" + builderClassName
                        + ", String> {");
    }

    private void printImportInfo(String className, PrintWriter out) {
        out.println("import org.springframework.data.mongodb.repository.MongoRepository;");
        out.println("import " + className);
    }

    private void printPackageInfo(String className, PrintWriter out) {
    }
}
