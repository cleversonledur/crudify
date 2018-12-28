package com.cleversonledur.crudify.generators.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.PrintWriter;
import java.util.List;

import com.cleversonledur.crudify.generators.Generator;

public class ServiceGenerator implements Generator {

    private String className;
    private String builderClassName;
    private String fullClassName;


    @Override
    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        List<? extends Element> members = processingEnv.getElementUtils().getAllMembers(element);

        className = element.getSimpleName().toString();
        builderClassName = "Crudify" + className + "Service";
        fullClassName = element.getQualifiedName().toString();

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(out);
                printImportInfo(out);
                printClassHeader(out);
                printFooterClass(out);

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }
    }

    private void printClassHeader(PrintWriter out) {
        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        out.println("@Service public class " + builderSimpleClassName + " {");
    }

    private String firstUpperCase(String memberName) {
        return memberName.substring(0, 1).toUpperCase() + memberName.substring(1);
    }

    private void printImportInfo(PrintWriter out) {

        out.println("import java.util.Collection;");
        out.println("import java.util.Optional;");
        out.println("import org.springframework.beans.factory.annotation.Autowired;");
        out.println("import org.springframework.stereotype.Service;");
        out.println("import org.springframework.util.StringUtils;");
    }

    private void printFooterClass(PrintWriter out) {
        out.println("}");
    }

    private void printPackageInfo(PrintWriter out) {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        if (packageName != null) {
            out.print("package ");
            out.print(packageName);
            out.println(";");
            out.println();
        }
    }
}
