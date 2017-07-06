package com.example;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 06/07/2017.
 */

public class AnnotatedView {

    private Element mElement;
    private final String DOT = ".";
    private String mFullClassName;

    public AnnotatedView(Element element) {
        mElement = element;
        TypeElement typeElement = ((TypeElement) mElement.getEnclosingElement());
        mFullClassName = typeElement.getQualifiedName().toString();
    }

    public String getName() {
        return mElement.getSimpleName().toString();
    }

    public String getType() {
        return mElement.asType().toString();
    }

    public String getFullClassName() {
        if (mElement.getAnnotation(Test.class) == null) {
            return mFullClassName;
        }
        return mFullClassName + "_";
    }

    public TypeMirror getClassType() {
        return mElement.getEnclosingElement().asType();
    }

    public String getId() {
        return String.valueOf(mElement.getAnnotation(ViewById.class).value());
    }

    public String getClassName() {
        return mFullClassName.substring(mFullClassName.lastIndexOf(DOT) + 1);
    }

    public String getPackage() {
        return mFullClassName.substring(0, mFullClassName.lastIndexOf(DOT));
    }

    public Element getElement() {
        return mElement;
    }
}
