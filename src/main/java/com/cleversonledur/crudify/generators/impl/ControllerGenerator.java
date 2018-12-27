package com.cleversonledur.crudify.generators.impl;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;

import com.cleversonledur.crudify.generators.Generator;

public class ControllerGenerator implements Generator {

    @Override
    public void run(TypeElement element, ProcessingEnvironment processingEnv) {

        String className = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String builderClassName = "Crudify" + className + "Controller";
        String builderSimpleClassName = builderClassName.substring(lastDot + 1);

        try {
            JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);

            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

                if (packageName != null) {
                    out.print("package ");
                    out.print(packageName);
                    out.println(";");
                    out.println();
                }

                out.print("public class ");
                out.print(builderSimpleClassName);
                out.println(" {");
                out.println();

                out.println("@ApiOperation(\"Create an " + builderSimpleClassName + " record.\")");
                out.println("@PostMapping");
                out.println("public ResponseEntity<%TYPE%Dto> create(@RequestBody final %TYPE%Dto " + builderSimpleClassName + "Dto) {");
                out.println("%TYPE% " + builderSimpleClassName + " = " + builderSimpleClassName + "Service.create%TYPE%("
                                + builderSimpleClassName + "Dto);");
                out.println("%TYPE%Dto " + builderSimpleClassName + "Response = modelMapper.map(" + builderSimpleClassName
                                + ", %TYPE%Dto.class);");
                out.println("    return ResponseEntity.ok().body(" + builderSimpleClassName + "Response);");
                out.println("}");

                out.println("@ApiOperation(\"Get an " + builderSimpleClassName + " record.\")");
                out.println("@GetMapping");
                out.println("public ResponseEntity<%TYPE%Dto> get(@RequestParam final String " + builderSimpleClassName + "Id) {");
                out.println("    Optional<%TYPE%> " + builderSimpleClassName + " = " + builderSimpleClassName + "Service.find%TYPE%ById("
                                + builderSimpleClassName + "Id);");
                out.println("    if (" + builderSimpleClassName + ".isPresent()) {");
                out.println("%TYPE%Dto " + builderSimpleClassName + "Response = modelMapper.map(" + builderSimpleClassName
                                + ".get(), %TYPE%Dto.class);");
                out.println("        return ResponseEntity.ok().body(" + builderSimpleClassName + "Response);");
                out.println("    } else {");
                out.println("return ResponseEntity.noContent().build();");
                out.println("    }");
                out.println("}");

                out.println("@ApiOperation(\"Update an " + builderSimpleClassName + " record.\")");
                out.println("@PutMapping");
                out.println("                public ResponseEntity<%TYPE%Dto> update(@RequestBody final %TYPE%Dto " + builderSimpleClassName
                                + "Dto) throws Exception {");
                out.println("%TYPE% " + builderSimpleClassName + " = " + builderSimpleClassName + "Service.update%TYPE%("
                                + builderSimpleClassName + "Dto);");
                out.println("%TYPE%Dto " + builderSimpleClassName + "Response = modelMapper.map(" + builderSimpleClassName
                                + ", %TYPE%Dto.class);");
                out.println("return ResponseEntity.ok().body(" + builderSimpleClassName + "Response);");
                out.println("}");

                out.println("@ApiOperation(\"Delete an " + builderSimpleClassName + " record.\")");
                out.println("@DeleteMapping");
                out.println("public ResponseEntity<%TYPE%Dto> delete(@RequestParam final String " + builderSimpleClassName
                                + "Id) throws Exception {");
                out.println("" + builderSimpleClassName + "Service.delete%TYPE%(" + builderSimpleClassName + "Id);");
                out.println("return ResponseEntity.ok().build();");
                out.println("}");

                out.println("}");

            } catch (IOException e) {

            }
        } catch (Exception e) {

        }


    }
}
