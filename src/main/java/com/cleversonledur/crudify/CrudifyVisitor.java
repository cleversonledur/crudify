package com.cleversonledur.crudify;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class CrudifyVisitor implements ElementVisitor<Element, String> {

    private Element element;

    private String elementName;

    @Override
    public Element visit(Element e, String s) {
        this.element = e;
        this.elementName = s;
        System.out.println("STRINGGGGGGGGGGGGG" + s);
        return null;
    }

    @Override
    public Element visit(Element e) {
        this.element = e;
        return null;
    }

    @Override
    public Element visitPackage(PackageElement e, String s) {
        return null;
    }

    @Override
    public Element visitType(TypeElement e, String s) {
        return null;
    }

    @Override
    public Element visitVariable(VariableElement e, String s) {
        return null;
    }

    @Override
    public Element visitExecutable(ExecutableElement e, String s) {
        return null;
    }

    @Override
    public Element visitTypeParameter(TypeParameterElement e, String s) {
        return null;
    }

    @Override
    public Element visitUnknown(Element e, String s) {
        return null;
    }
}
