package com.argo.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

/**
 * Created by yamingd on 9/11/15.
 */
@SupportedOptions({"debug", "verify"})
@SupportedAnnotationTypes({
        "com.argo.annotation.RmiService",
        "org.springframework.stereotype.Component"
})
public class ArgoAnnotationProcessor extends AbstractProcessor {

    private RmiServiceCatalogGenerator rmiServiceCatalogGenerator;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public void log(String msg) {
        if(this.processingEnv.getOptions().containsKey("debug")) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public void error(String msg, Element element, AnnotationMirror annotation) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element, annotation);
    }

    public void fatalError(String msg) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + msg);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        rmiServiceCatalogGenerator = new RmiServiceCatalogGenerator(this, processingEnv);
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            return this.processImpl(annotations, roundEnv);
        } catch (Exception ex) {
            StringWriter writer = new StringWriter();
            ex.printStackTrace(new PrintWriter(writer));
            this.fatalError(writer.toString());
            return true;
        }
    }

    private boolean processImpl(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(roundEnv.processingOver()) {
            try {
                rmiServiceCatalogGenerator.writeFile();
            } catch (IOException e) {
                fatalError(e.getMessage());
            }
        } else {
            rmiServiceCatalogGenerator.process(annotations, roundEnv);
        }
        return true;
    }

}
