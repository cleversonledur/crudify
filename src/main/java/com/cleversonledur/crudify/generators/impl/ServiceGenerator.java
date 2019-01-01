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
    private String variableName;

    @Override
    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        List<? extends Element> members = processingEnv.getElementUtils().getAllMembers(element);

        loadBasicInfo(element);

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(out);
                printImportInfo(out);
                printClassHeader(out);

                printRepository(out);
                printCreateMethod(out);
                printDeleteMethod(out);
                printUpdateMethod(out);
                printFindById(out);

                printFooterClass(out);

            } catch (Exception e) {

            }
        } catch (Exception e) {

        }
    }

    private void printFindById(PrintWriter out) {
        out.println("public Optional<" + className + "> find" + className + "ById(String id) {");
        out.println("        return repository.findById(id);");
        out.println("}");
    }

    private void printUpdateMethod(PrintWriter out) {
        out.println("public " + className + " update" + className + "(Crudify" + className + "Dto " + variableName
                        + "Dto) throws Exception {");

        out.println("        if (StringUtils.isEmpty(" + variableName + "Dto.getId())) {");
        out.println("            throw new Exception(\"" + className + " id was not informed for update: \" + " + variableName
                        + "Dto.toString());");
        out.println("        }");

        out.println("        " + className + " " + variableName + " = " + variableName + "Dto.getModel();");

        out.println("        " + variableName + " = repository.save(" + variableName + ");");

        out.println("        return " + variableName + ";");
        out.println("    }");

    }

    private void loadBasicInfo(TypeElement element) {
        this.className = element.getSimpleName().toString();
        this.builderClassName = "Crudify" + className + "Service";
        this.fullClassName = element.getQualifiedName().toString();
        this.variableName = className.toLowerCase();
    }

    private void printDeleteMethod(PrintWriter out) {
        out.println("public void delete" + className + "(String " + variableName + "Id) throws Exception {");
        out.println("        if (StringUtils.isEmpty(" + variableName + "Id)) {");
        out.println("            throw new Exception(\" " + className + " id was not informed for deletion. \");");
        out.println("        }");
        out.println("        Optional<" + className + "> " + variableName + " = find" + className + "ById(" + variableName + "Id);");
        out.println("        if (" + variableName + ".isPresent()) {");
        out.println("            repository.delete(" + variableName + ".get());");
        out.println("        }");
        out.println("}");

    }

    private void printRepository(PrintWriter out) {
        out.println("@Autowired private Crudify" + className + "Repository repository;");
    }

    private void printCreateMethod(PrintWriter out) {
        out.println("public " + className + " create" + className + "(Crudify" + className + "Dto " + variableName + "Dto) {");
        out.println("        " + className + " " + variableName + " = " + variableName + "Dto.getModel();");
        out.println("        " + variableName + " = repository.save(" + variableName + ");");
        out.println("        return " + variableName + ";");
        out.println("}");
    }

    private void printClassHeader(PrintWriter out) {
        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);
        out.println("@Service public class " + builderSimpleClassName + " {");
    }

    private void printImportInfo(PrintWriter out) {
        out.println("import " + fullClassName + ";");
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

//    public Optional<Pacient> findPacientById(String id) {
//        return repository.findById(id);
//    }
//
//    public Collection<Pacient> listByName(String name) {
//        return repository.listByName(name);
//    }
//
//    public Pacient updatePacient(PacientDto pacientDto) throws Exception {
//
//        if (StringUtils.isEmpty(pacientDto.getId())) {
//            throw new Exception("Pacient id was not informed for update: " + pacientDto.toString());
//        }
//
//        Pacient pacient = pacientDto.getModel();
//
//        pacient = repository.save(pacient);
//
//        return pacient;
//    }
