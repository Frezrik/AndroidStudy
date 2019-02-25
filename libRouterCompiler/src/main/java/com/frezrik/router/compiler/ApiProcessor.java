package com.frezrik.router.compiler;

import com.frezrik.router.annotation.ApiImpl;
import com.frezrik.router.annotation.BindApi;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({Constants.ANNOTATION_APIIMPL, Constants.ANNOTATION_BINDAPI})
public class ApiProcessor extends AbstractProcessor {

    private Log log;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        log = new Log(processingEnv.getMessager());

        elementUtils = processingEnv.getElementUtils();

        log.i(">>>>> ApiProcessor init <<<<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.i(">>>>> ApiProcessor process <<<<<");

        Map<String, String> apiTables = new HashMap<>();
        Set<String> implSet;
        // 获取ApiTables
        Set<? extends Element> apiNode = roundEnv.getElementsAnnotatedWith(ApiImpl.class);
        for (Element element : apiNode) {
            if(element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                String implName = typeElement.getQualifiedName().toString();

                if(!apiTables.containsKey(implName)) {
                    List<? extends TypeMirror> interfaces = typeElement.getInterfaces();

                    if (interfaces != null && interfaces.size() > 0) {
                        String ifName = interfaces.get(0).toString();
                        apiTables.put(implName, ifName);
                        log.i("======>>>>>" + implName + ":" + ifName);
                    }
                }
            }
        }

        // 获取BindApi
        Map<String, Set<String>> bindTables = new HashMap<>();
        Set<String> fieldSet;
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindApi.class);
        for (Element element : elements) {
            if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                TypeMirror fieldType = element.asType();
                TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();

                String className = classElement.getQualifiedName().toString();

                if (bindTables.containsKey(className)) {
                    fieldSet = bindTables.get(className);
                } else {
                    fieldSet = new HashSet<>();
                }

                String fieldName = variableElement.getSimpleName().toString();

                if (!fieldSet.contains(fieldName)) {
                    fieldSet.add(fieldName);
                    bindTables.put(className, fieldSet);
                    log.i("======>>>>>" + className + "#" + fieldType + "#" + fieldName);
                }

            }
        }

        //writeToFile(apiTables, bindTables);

        return true;
    }

    private void writeToFile(Map<String,Set<String>> apiTables, Map<String,Set<String>> bindTables) {
        //com.frezrik.common.utils.ConvertUtilImpl:com.frezrik.core.api.ConvertUtil
        //com.frezrik.androidstudy.service.DeviceManagerService#convertUtil
        for(Map.Entry<String, Set<String>> entry : apiTables.entrySet()) {
            String className = entry.getKey();
            Set<String> fieldSet = entry.getValue();


        }

        // 生成java代码
        //JavaFile javaFile = JavaFile.builder(bindingClassName.packageName(), bindingConfiguration);
        /*for (String key : mProxyMap.keySet()) {
            Creator proxyInfo = mProxyMap.get(key);
            JavaFile javaFile = JavaFile.builder(proxyInfo.getPackageName(), proxyInfo.generateJavaCode())
                    .indent("    ")
                    .addFileComment("generate file, do not modify!")
                    .build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private File createApiTables() {
        File file = new File("router_tables/api_tables");
        if (!file.exists()) {
            File dir = new File(file.getParent());
            if (!dir.exists())
                dir.mkdirs();
        } else {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /*public static <T> T service(Class<T> serviceClass) {
        return (T) serviceLruCache.get(serviceClass);
    }

    public static <T> void registerService(Class<T> serviceClass) {
        serviceLruCache.put(serviceClass, "");
    }

    public static <T> void unRegisterService(Class<T> serviceClass) {
        serviceLruCache.remove(serviceClass);
    }*/
}
