package com.frezrik.router.compiler;

import com.frezrik.router.annotation.ApiImpl;
import com.frezrik.router.annotation.BindApi;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({Constants.ANNOTATION_APIIMPL, Constants.ANNOTATION_BINDAPI})
public class ApiProcessor extends AbstractProcessor {

    //private Map<String, Creat> mProxyMap = new HashMap<>();
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
        collectInfo(roundEnv);

        writeToFile(roundEnv);

        return true;
    }


    private void collectInfo(RoundEnvironment roundEnv) {
        Set<? extends Element> apiNode = roundEnv.getElementsAnnotatedWith(ApiImpl.class);
        if (apiNode != null && apiNode.size() != 0) {
            log.i(">>>>> ApiProcessor process <<<<<");

            for (Element element : apiNode) {
                if (element.getKind().isClass()) {
                    String impl = elementUtils.getPackageOf(element).getQualifiedName().toString() + "." + element.getSimpleName().toString();
                    log.i("======>>>>>" + impl);
                }
            }
        }
    }

    /**
     * 生成$$BindApi
     */
    private void writeToFile(RoundEnvironment roundEnv) {
// 获取源文件中带有 BindView 注解的域
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindApi.class);
        log.i("======>>>>>" + elements.size());
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String fullClassName = classElement.getQualifiedName().toString();

            log.i("======>>>>>" + fullClassName);
            /*Creator proxy = mProxyMap.get(fullClassName);
            if (proxy == null) {
                proxy = new Creator(mElementUtils, classElement);
                mProxyMap.put(fullClassName, proxy);
            }
            BindView bindAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindAnnotation.value();
            proxy.putFieldElement(id, variableElement);*/
        }

        // 生成java代码
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
