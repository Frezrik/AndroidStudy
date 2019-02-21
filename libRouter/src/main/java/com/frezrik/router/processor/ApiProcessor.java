package com.frezrik.router.processor;

import com.frezrik.router.annotation.ApiImpl;
import com.frezrik.router.utils.Log;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.frezrik.router.annotation.ApiImpl"})
public class ApiProcessor extends AbstractProcessor {
    private Log log;
    private File api_tab;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        log = new Log(processingEnv.getMessager());

        api_tab = createApiTables();

        log.i(">>>>> ApiProcessor init <<<<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> apiNode = roundEnv.getElementsAnnotatedWith(ApiImpl.class);
        if (apiNode != null && apiNode.size() != 0) {
            log.i(">>>>> ApiProcessor process <<<<<");

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(api_tab, true));
                for (Element e : apiNode) {
                    if(e.getKind().isClass()) {
                        //bw.write(e);
                    }
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
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
}
