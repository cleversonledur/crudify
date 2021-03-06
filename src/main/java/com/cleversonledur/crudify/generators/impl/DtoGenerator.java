package com.cleversonledur.crudify.generators.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.PrintWriter;
import java.util.List;

import com.cleversonledur.crudify.generators.Generator;

public class DtoGenerator implements Generator {

    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        List<? extends Element> members = processingEnv.getElementUtils().getAllMembers(element);

        String className = element.getSimpleName().toString();

        String builderClassName = "Crudify" + className + "Dto";

        String fullClassName = element.getQualifiedName().toString();


        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(className, out);
                printImportInfo(fullClassName, out);
                printClassHeader(className, builderClassName, out);
                generateVariableMembers(members, out);
                generateGetters(members, out);
                generateSetters(members, out);

                generateToModel(className, out);

                printFooterClass(out);

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }

    }

    private void generateToModel(String className, PrintWriter out) {
        out.println("public " + className + " getModel() {");

        out.println("    ModelMapper mapper = new ModelMapper();");
        out.println("    " + className + " " + className.toLowerCase() + " = mapper.map(this, " + className + ".class);");
        out.println("    return " + className.toLowerCase() + ";");
        out.println("}");
    }

    private void generateVariableMembers(List<? extends Element> members, PrintWriter out) {
        for (Element member : members) {
            String memberName = member.getSimpleName().toString();
            String memberType = member.asType().toString();
            if (ElementKind.FIELD.equals(member.getKind()))
                out.println("private " + memberType + " " + memberName + ";");
        }
    }

    private void printImportInfo(String fullClassName, PrintWriter out) {
        out.println("import " + fullClassName + ";");
        out.println("import java.io.Serializable;");
        out.println("import org.modelmapper.ModelMapper;");
    }

    private void generateSetters(List<? extends Element> members, PrintWriter out) {
        for (Element member : members) {
            String memberName = member.getSimpleName().toString();
            String memberType = member.asType().toString();
            if (ElementKind.FIELD.equals(member.getKind())) {
                out.println("public void set" + firstUpperCase(memberName) + "(" + memberType + " member){");
                out.println("this." + memberName + " = member;");
                out.println("}");
            }

        }
    }

    private void generateGetters(List<? extends Element> members, PrintWriter out) {
        for (Element member : members) {
            String memberName = member.getSimpleName().toString();
            String memberType = member.asType().toString();
            if (ElementKind.FIELD.equals(member.getKind())) {
                out.println("public " + memberType + " get" + firstUpperCase(memberName) + "(){");
                out.println(" return this." + memberName + ";");
                out.println("}");
            }

        }
    }

    private String firstUpperCase(String memberName) {
        return memberName.substring(0, 1).toUpperCase() + memberName.substring(1);
    }

    private void printFooterClass(PrintWriter out) {
        out.println("}");
    }

    private void printClassHeader(String className, String builderClassName, PrintWriter out) {
        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
        out.print("public class ");
        out.print(builderSimpleClassName);
        out.println(" implements Serializable{");
        out.println();
    }

    private void printPackageInfo(String className, PrintWriter out) {
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
