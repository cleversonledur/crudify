package com.cleversonledur.crudify;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import java.util.List;
import java.util.Set;

import com.cleversonledur.crudify.generators.Generator;
import com.cleversonledur.crudify.generators.GeneratorUtils;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("com.cleversonledur.crudify.Crudify")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (TypeElement annotation : annotations) {

            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            List<Generator> generators = GeneratorUtils.getGenerators();

            annotatedElements.forEach(element -> {
                if (element instanceof TypeElement) {
                    generators.forEach(generator -> generator.run((TypeElement) element, processingEnv));
                }
            });
        }
        return true;
    }

}
