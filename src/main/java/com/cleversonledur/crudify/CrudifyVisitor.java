package com.cleversonledur.crudify;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class CrudifyVisitor implements ElementVisitor<Void, Void> {

    @Override
    public Void visit(Element e, Void aVoid) {
        return null;
    }

    @Override
    public Void visit(Element e) {
        return null;
    }

    @Override
    public Void visitPackage(PackageElement e, Void aVoid) {
        return null;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        return null;
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        return null;
    }

    @Override
    public Void visitExecutable(ExecutableElement e, Void aVoid) {
        return null;
    }

    @Override
    public Void visitTypeParameter(TypeParameterElement e, Void aVoid) {
        return null;
    }

    @Override
    public Void visitUnknown(Element e, Void aVoid) {
        return null;
    }
}
