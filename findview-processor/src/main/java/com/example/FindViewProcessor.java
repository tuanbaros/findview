package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@SupportedAnnotationTypes("com.example.ViewById")
@AutoService(Processor.class)
public class FindViewProcessor extends AbstractProcessor {

    private static final String SUFFIX = "_FindView";
    private static final String CONST_PARAM_TARGET_NAME = "target";
    private static final String TARGET_STATEMENT_FORMAT =
            "target.%1$s = (%2$s) target.findViewById(%3$s)";

    private static final String TARGET_STATEMENT_FORMAT_2 =
            "target.%1$s = (%2$s) view.findViewById(%3$s)";

    private final String DOT = ".";

    private Filer mFiler;
    private ProcessingEnvironment mProcessingEnvironment;
    private Elements mElements;
    private Types mTypes;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mProcessingEnvironment = processingEnv;
        mElements = processingEnv.getElementUtils();
        mTypes = processingEnv.getTypeUtils();
        //        Element        e   = processingEnv.getTypeUtils().asElement(type);
        //        PackageElement pkg = processingEnv.getElementUtils().getPackageOf(e);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        Map<String, List<AnnotatedView>> annotatedElementMap = new LinkedHashMap<>();
        for (Element element : env.getElementsAnnotatedWith(ViewById.class)) {
            AnnotatedView annotatedView = new AnnotatedView(element);
            if (mTypes.isSubtype(annotatedView.getElement().asType(),
                    mElements.getTypeElement("android.view.View").asType())) {
                addAnnotatedElement(annotatedElementMap, annotatedView);
            }
        }
        if (annotatedElementMap.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, List<AnnotatedView>> entry : annotatedElementMap.entrySet()) {
            MethodSpec constructor = createConstructor(entry.getValue());
            TypeSpec binder = createClass(getClassName(entry.getKey()), constructor);
            JavaFile javaFile =
                    JavaFile.builder(getPackage(entry.getValue().get(0).getElement()), binder)
                            .build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private MethodSpec createConstructor(List<AnnotatedView> annotatedViews) {
        AnnotatedView firstElement = annotatedViews.get(0);
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(firstElement.getClassType()), CONST_PARAM_TARGET_NAME);
        boolean check = checkGenType(firstElement);
        if (check) {
            builder.addParameter(
                    TypeName.get(mElements.getTypeElement("android.view.View").asType()), "view");
        }
        for (int i = 0; i < annotatedViews.size(); i++) {
            AnnotatedView annotatedView = annotatedViews.get(i);
            builder.addStatement(
                    String.format(check ? TARGET_STATEMENT_FORMAT_2 : TARGET_STATEMENT_FORMAT,
                            annotatedView.getName(), annotatedView.getType(),
                            annotatedView.getId()));
        }
        // mProcessingEnvironment.getElementUtils().getPackageOf(annotatedView.getElement())
        return builder.build();
    }

    private boolean checkGenType(AnnotatedView annotatedView) {
        return (annotatedView.getFullClassName()
                .replace(getPackage(annotatedView.getElement()) + DOT, "")).contains(DOT);
    }

    private TypeSpec createClass(String className, MethodSpec constructor) {
        return TypeSpec.classBuilder(className + SUFFIX)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(constructor)
                .build();
    }

    private String getPackage(Element element) {
        return mProcessingEnvironment.getElementUtils().getPackageOf(element).toString();
    }

    private String getPackage(String qualifier) {
        return qualifier.substring(0, qualifier.lastIndexOf(DOT)).toLowerCase();
    }

    private String getClassName(String qualifier) {
        return qualifier.substring(qualifier.lastIndexOf(DOT) + 1);
    }

    private void addAnnotatedElement(Map<String, List<AnnotatedView>> map,
            AnnotatedView annotatedView) {
        String qualifier = annotatedView.getFullClassName();
        if (map.get(qualifier) == null) {
            map.put(qualifier, new ArrayList<AnnotatedView>());
        }
        map.get(qualifier).add(annotatedView);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
