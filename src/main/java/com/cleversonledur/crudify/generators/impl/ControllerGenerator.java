package com.cleversonledur.crudify.generators.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;

import com.cleversonledur.crudify.generators.Generator;

public class ControllerGenerator implements Generator {

    private String className;
    private String builderClassName;
    private String fullClassName;
    private String variableName;

    @Override
    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        loadBasicInfo(element);

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                printPackageInfo(out);
                printImportInfo(out);
                printClassHeader(out);

                printDeclarations(out);

                printControllers(out);
                printFooterClass(out);

            } catch (IOException e) {

            }
        } catch (Exception e) {

        }


    }

    private void printDeclarations(PrintWriter out) {

        out.println("@Autowired");
        out.println("Crudify" + className + "Service " + className.toLowerCase() + "Service;");

        out.println("@Autowired");
        out.println("ModelMapper modelMapper;");
    }

    private void printControllers(PrintWriter out) {

        out.println("@ApiOperation(\"Create an " + className.toLowerCase() + " record.\")");
        out.println("@PostMapping");
        out.println("public ResponseEntity<Crudify" + className + "Dto> create(@RequestBody final Crudify" + className + "Dto "
                        + className.toLowerCase() + "Dto) { ");
        out.println(className + " " + className.toLowerCase() + " = " + className.toLowerCase() + "Service.create" + className + " ("
                        + className.toLowerCase() + "Dto); ");
        out.println("    Crudify" + className + "Dto " + className.toLowerCase() + "Response = modelMapper.map(" + className.toLowerCase()
                        + ", Crudify" + className + "Dto.class);");
        out.println("    return ResponseEntity.ok().body(" + className.toLowerCase() + "Response);");
        out.println("}");

        out.println("@ApiOperation(\"Get an " + className.toLowerCase() + " record.\") ");
        out.println("@GetMapping");
        out.println("public ResponseEntity<Crudify" + className + "Dto> get(@RequestParam final String " + className.toLowerCase()
                        + "Id) {");
        out.println("    Optional<" + className + "> pacient = " + className.toLowerCase() + "Service.find" + className + "ById("
                        + className.toLowerCase() + "Id);");
        out.println("    if (" + className.toLowerCase() + ".isPresent()) {");
        out.println("        Crudify" + className + "Dto " + className.toLowerCase() + "Response = modelMapper.map("
                        + className.toLowerCase() + ".get(), Crudify" + className + "Dto.class);");
        out.println("        return ResponseEntity.ok().body(" + className.toLowerCase() + "Response);");
        out.println("    } else {");
        out.println("        return ResponseEntity.noContent().build();");
        out.println("    }");
        out.println("}");

        out.println(" @ApiOperation(\"Update an pacient record.\")");
        out.println("@PutMapping");
        out.println("public ResponseEntity<Crudify" + className + "Dto> update(@RequestBody final Crudify" + className + "Dto "
                        + className.toLowerCase() + "Dto) throws Exception {");
        out.println("    " + className + " " + className.toLowerCase() + " = " + className.toLowerCase() + "Service.update" + className
                        + "(" + className.toLowerCase() + "Dto);");
        out.println("    Crudify" + className + "Dto " + className.toLowerCase() + "Response = modelMapper.map(" + className.toLowerCase()
                        + ", Crudify" + className + "Dto.class);");
        out.println("    return ResponseEntity.ok().body(" + className.toLowerCase() + "Response);");
        out.println("}");

        out.println("@ApiOperation(\"Delete an " + className.toLowerCase() + " record.\")");
        out.println("@DeleteMapping");
        out.println("public ResponseEntity<Crudify" + className + "Dto> delete(@RequestParam final String " + className.toLowerCase()
                        + "Id) throws Exception {");
        out.println("    " + className.toLowerCase() + "Service.delete" + className + "(" + className.toLowerCase() + "Id);");
        out.println("    return ResponseEntity.ok().build();");
        out.println("}");
    }

    private void printFooterClass(PrintWriter out) {
        out.println("}");

    }

    private void printClassHeader(PrintWriter out) {

        int lastDot = className.lastIndexOf('.');
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        out.println("@Api(value = \"" + className + " CRUD\")");
        out.println("@RestController");
        out.println("@RequestMapping(\"/" + className.toLowerCase() + "\")");
        out.println("public class " + builderSimpleClassName + " {");

    }

    private void printImportInfo(PrintWriter out) {

        out.println("import java.util.Optional;");

        out.println("import org.modelmapper.ModelMapper;");
        out.println("import org.springframework.beans.factory.annotation.Autowired;");
        out.println("import org.springframework.http.ResponseEntity;");
        out.println("import org.springframework.web.bind.annotation.DeleteMapping;");
        out.println("import org.springframework.web.bind.annotation.GetMapping;");
        out.println("import org.springframework.web.bind.annotation.PostMapping;");
        out.println("import org.springframework.web.bind.annotation.PutMapping;");
        out.println("import org.springframework.web.bind.annotation.RequestBody;");
        out.println("import org.springframework.web.bind.annotation.RequestMapping;");
        out.println("import org.springframework.web.bind.annotation.RequestParam;");
        out.println("import org.springframework.web.bind.annotation.RestController;");

        out.println("import " + fullClassName + ";");

        out.println("import io.swagger.annotations.Api;");
        out.println("import io.swagger.annotations.ApiOperation;");
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

    private void loadBasicInfo(TypeElement element) {
        this.className = element.getSimpleName().toString();
        this.builderClassName = "Crudify" + className + "Controller";
        this.fullClassName = element.getQualifiedName().toString();
        this.variableName = className.toLowerCase();
    }
}
