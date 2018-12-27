package com.cleversonledur.crudify.generators;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.PrintWriter;
import java.util.List;

public class DtoGenerator {

    public static void generateDtos(TypeElement element, ProcessingEnvironment processingEnv) {

        List<? extends Element> members = processingEnv.getElementUtils().getAllMembers(element);

        String className = element.getSimpleName().toString();

        String builderClassName = "Crudify" + className + "Dto";

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(className, out);
                printImportInfo(out);
                printClassHeader(className, builderClassName, out);
                generateVariableMembers(members, out);
                generateGetters(members, out);
                generateSetters(members, out);

                printFooterClass(out);

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }

    } 

    static void generateVariableMembers(List<? extends Element> members, PrintWriter out) {
        for (Element member : members) {
            String memberName = member.getSimpleName().toString();
            String memberType = member.asType().toString();
            if (ElementKind.FIELD.equals(member.getKind()))
                out.println("private " + memberType + " " + memberName + ";");
        }
    }

    static void printImportInfo(PrintWriter out) {
        out.println("import java.io.Serializable;");
    }

    static void generateSetters(List<? extends Element> members, PrintWriter out) {
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

    static void generateGetters(List<? extends Element> members, PrintWriter out) {
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

    static String firstUpperCase(String memberName) {
        return memberName.substring(0, 1).toUpperCase() + memberName.substring(1);
    }

    static void printFooterClass(PrintWriter out) {
        out.println("}");
    }

    static void printClassHeader(String className, String builderClassName, PrintWriter out) {
        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
        out.print("public class ");
        out.print(builderSimpleClassName);
        out.println(" implements Serializable{");
        out.println();
    }

    static void printPackageInfo(String className, PrintWriter out) {
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
