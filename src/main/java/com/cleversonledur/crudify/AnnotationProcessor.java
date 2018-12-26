package com.cleversonledur.crudify;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Set;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("com.cleversonledur.crudify.Crudify")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for (TypeElement annotation : annotations) {

      processingEnv.getMessager()
                      .printMessage(Diagnostic.Kind.WARNING, "@BuilderProperty must be applied to a setXxx method with a single argument");

      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, annotatedElements.toString());

      Set<VariableElement> fields = ElementFilter.fieldsIn(annotatedElements);

      for (VariableElement field : fields) {
        TypeMirror fieldType = field.asType();

        for (Field field1 : field.getClass().getFields()) {

          processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "CLASS FIELDS" + field1.getName());
        }

        String fullTypeClassName = fieldType.toString();

        if (!String.class.getName().equals(fullTypeClassName)) {
          processingEnv.getMessager()
                          .printMessage(Diagnostic.Kind.WARNING,
                                          "Field type must be java.lang.String" + fullTypeClassName + " " + fieldType);

          processingEnv.getMessager()
                          .printMessage(Diagnostic.Kind.WARNING,
                                          "FIELD DECLARED FIELDS" + fieldType.getClass().getDeclaredFields().toString());
        }
      }

      Set<? extends Element> rootE = roundEnv.getRootElements();
      for (Element e : rootE) {
        for (Element subElement : e.getEnclosedElements()) {
          subElement.accept(new CrudifyVisitor(), null); // implement ExampleVisitor
        }
      }

      annotatedElements.forEach(element -> {

        generateDtos(element);
        //        generateEndpoints(element);
        //        generateRepository(element);
        //        generateService(element);

            });
    }
    return true;
  }

  private void generateRepository(Element element) {
  }

  private void generateService(Element element) {
  }

  private void generateDtos(Element element) {

    processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING, "element.getEnclosingElement(): " + element.getEnclosingElement().toString());

    processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING,
                                    "element.getEnclosingElement().getEnclosedElements(): " + element.getEnclosingElement()
                                                    .getEnclosedElements()
                                                    .toString());

    processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING, "element.getEnclosedElements(): " + element.getEnclosedElements().toString());

    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getKind(): " + element.getKind().toString());

    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getModifiers(): " + element.getModifiers().toString());

    processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.WARNING, "element.getAnnotationMirrors(): " + element.getAnnotationMirrors().toString());

    //    TypeElement field = (TypeElement) element;
    //
    //    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getNestingKind(): " + element.getNestingKind().toString());
    //
    //    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getQualifiedName(): " + element.getQualifiedName().toString());
    //
    //    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getSuperclass(): " + element.getSuperclass().toString());

    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "element.getClass(): " + element.getClass().toString());
  }

  private void generateEndpoints(Element element) {

    String className = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();

    String packageName = null;
    int lastDot = className.lastIndexOf('.');
    if (lastDot > 0) {
      packageName = className.substring(0, lastDot);
    }

    String simpleClassName = className.substring(lastDot + 1);
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
        out.println("%TYPE% " + builderSimpleClassName + " = " + builderSimpleClassName + "Service.create%TYPE%(" + builderSimpleClassName
                        + "Dto);");
        out.println("%TYPE%Dto " + builderSimpleClassName + "Response = modelMapper.map(" + builderSimpleClassName + ", %TYPE%Dto.class);");
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
        out.println("%TYPE% " + builderSimpleClassName + " = " + builderSimpleClassName + "Service.update%TYPE%(" + builderSimpleClassName
                        + "Dto);");
        out.println("%TYPE%Dto " + builderSimpleClassName + "Response = modelMapper.map(" + builderSimpleClassName + ", %TYPE%Dto.class);");
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
