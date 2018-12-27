package com.cleversonledur.crudify.generators;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public interface Generator {

    public void run(TypeElement element, ProcessingEnvironment processingEnv);
}
