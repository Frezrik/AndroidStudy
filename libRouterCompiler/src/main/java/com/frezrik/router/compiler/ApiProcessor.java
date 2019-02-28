package com.frezrik.router.compiler;

import com.frezrik.router.annotation.ApiImpl;
import com.frezrik.router.annotation.BindApi;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.io.*;
import java.lang.annotation.Annotation;
import java.nio.Buffer;
import java.util.*;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({Constants.ANNOTATION_APIIMPL, Constants.ANNOTATION_BINDAPI})
public class ApiProcessor extends AbstractProcessor {

    private Filer filer;
    private Log log;
    private Elements elementUtils;
    private Map<ClassName, ClassName> tempTables;

    private static final String API_PATH = "api_tables";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        filer = processingEnv.getFiler();

        log = new Log(processingEnv.getMessager());

        elementUtils = processingEnv.getElementUtils();

        log.i(">>>>> ApiProcessor init <<<<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.i(">>>>> ApiProcessor process <<<<<");

        // 获取ApiTables
        Set<? extends Element> apiNode = roundEnv.getElementsAnnotatedWith(ApiImpl.class);
        for (Element element : apiNode) {
            if(element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                String implName = typeElement.getQualifiedName().toString();
                List<? extends TypeMirror> interfaces = typeElement.getInterfaces();

                if (interfaces != null && interfaces.size() > 0) {
                    String ifName = interfaces.get(0).toString();
                    writeApiTables(implName, ifName);
                    //log.i("======>>>>>" + implName + ":" + ifName);
                }
            }
        }

        tempTables = readApiTables();
        if (tempTables.size() != 0) {
            log.i("     >>>>> ParserApiTables <<<<<");
            Map<ClassName, Set<BindingSet>> bindTables = new HashMap<>();
            // 获取BindApi
            Set<BindingSet> fieldSet;
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindApi.class);
            for (Element element : elements) {
                boolean hasError = isInaccessibleViaGeneratedCode(BindApi.class, "fields", element)
                        || isBindingInWrongPackage(BindApi.class, element);
                if(hasError) {
                    break;
                }

                if (element instanceof VariableElement) {
                    VariableElement variableElement = (VariableElement) element;
                    TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();

                    ClassName targetName = ClassName.bestGuess(classElement.getQualifiedName().toString());

                    if (bindTables.containsKey(targetName)) {
                        fieldSet = bindTables.get(targetName);
                    } else {
                        fieldSet = new HashSet<>();
                    }

                    String fieldName = variableElement.getSimpleName().toString();
                    ClassName fieldType = ClassName.bestGuess(element.asType().toString());

                    if (!fieldSet.contains(fieldName) && tempTables.containsKey(fieldType)) {
                        BindingSet bs = new BindingSet();
                        bs.fieldType = fieldType;
                        bs.field = fieldName;
                        bs.fieldTypeImpl = tempTables.get(fieldType);
                        bs.target = targetName;

                        fieldSet.add(bs);
                        bindTables.put(targetName, fieldSet);

                        log.i("          >>>>>" + targetName + "#" + fieldType + "#" + bs.fieldTypeImpl + "#" + fieldName);
                    }

                }
            }

            deleteApiTables();

            if (bindTables.size() != 0) {
                log.i("     >>>>> WriteToFile <<<<<");
                writeToFile(bindTables);
            }

            bindTables.clear();
        }

        return true;
    }

    private void writeToFile(Map<ClassName,Set<BindingSet>> bindTables) {
        ClassName router = ClassName.get("com.frezrik.router", "Router");
        for(Map.Entry<ClassName, Set<BindingSet>> entry : bindTables.entrySet()) {
            ClassName targetName = entry.getKey();
            String bindName = targetName.simpleName() + "$$BindApi";
            MethodSpec.Builder cons = MethodSpec.constructorBuilder()
                    .addModifiers(PUBLIC)
                    .addParameter(TypeName.OBJECT, "target");

            Set<BindingSet> bss = entry.getValue();
            for(BindingSet bs : bss) {
                cons.addStatement("$T.registerService($T.class, new $T())", router, bs.fieldType, bs.fieldTypeImpl);
                cons.addStatement("(($T) target).$L = $T.service($T.class)", targetName, bs.field, router, bs.fieldType);
            }

            TypeSpec bindNameType = TypeSpec.classBuilder(bindName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(cons.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(targetName.packageName(), bindNameType)
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> annotationClass,
                                                   String targetThing, Element element) {
        boolean hasError = false;
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // Verify method modifiers.
        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(PRIVATE) || modifiers.contains(STATIC)) {
            log.e(element, "@%s %s must not be private or static. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing type.
        if (enclosingElement.getKind() != CLASS) {
            log.e(enclosingElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        // Verify containing class visibility is not private.
        if (enclosingElement.getModifiers().contains(PRIVATE)) {
            log.e(enclosingElement, "@%s %s may not be contained in private classes. (%s.%s)",
                    annotationClass.getSimpleName(), targetThing, enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            hasError = true;
        }

        return hasError;
    }

    private boolean isBindingInWrongPackage(Class<? extends Annotation> annotationClass,
                                            Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String qualifiedName = enclosingElement.getQualifiedName().toString();

        if (qualifiedName.startsWith("android.")) {
            log.e(element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }
        if (qualifiedName.startsWith("java.")) {
            log.e(element, "@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return true;
        }

        return false;
    }

    private void deleteApiTables() {
        File file = new File(API_PATH);
        if (file.exists()) {
            file.delete();
        }
    }


    private void writeApiTables(String implName, String ifName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(API_PATH, true));
            bw.write(implName + ":" + ifName);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private HashMap<ClassName, ClassName> readApiTables() {
        HashMap<ClassName, ClassName> tables = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(API_PATH));
            String s;
            while((s = br.readLine()) != null) {
                if(s.contains(":")) {
                    String[] split = s.split(":");
                    tables.put(ClassName.bestGuess(split[1]), ClassName.bestGuess(split[0]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

}