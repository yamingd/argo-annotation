package com.argo.processor;

import com.argo.annotation.RmiService;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * write service to META-INF/rmis
 * Created by yamingd on 9/14/15.
 */
public class RmiServiceCatalogGenerator {

    private ArgoAnnotationProcessor processor;
    private ProcessingEnvironment processingEnvironment;
    private Set<String> serviceList = new HashSet<String>();
    private String prefix = "META-INF/rmis/";

    public RmiServiceCatalogGenerator(ArgoAnnotationProcessor processor, ProcessingEnvironment processingEnvironment) {
        this.processor = processor;
        this.processingEnvironment = processingEnvironment;
    }

    /**
     *
     * @param annotations
     * @param roundEnv
     */
    public void process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set elements = roundEnv.getElementsAnnotatedWith(RmiService.class);
        //processor.log(annotations.toString());
        //processor.log(elements.toString());
        Iterator itor = elements.iterator();

        while(itor.hasNext()) {
            Element e = (Element)itor.next();
            TypeElement typeElement = (TypeElement)e;
            processor.log("RmiService: " + typeElement);
            serviceList.add(typeElement.getQualifiedName().toString());
        }
    }

    /**
     *
     * @throws IOException
     */
    public void writeFile() throws IOException {
        Filer filer = processingEnvironment.getFiler();
        String fileName = null;
        try{

            //processor.log("New service file contents: " + serviceList);

            Iterator itor = serviceList.iterator();
            Set<String> tmp = new HashSet<String>();
            while (itor.hasNext()){
                tmp.clear();
                String service = (String)itor.next();
                fileName = prefix + service;
                FileObject fileObject1 = filer.createResource(StandardLocation.CLASS_OUTPUT, "", fileName, new Element[0]);
                OutputStream out = fileObject1.openOutputStream();

                tmp.add(service);
                ServicesFiles.writeServiceInterfaceFile(tmp, out);

                out.close();

                //processor.log("Wrote to: " + fileObject1.toUri());

            }

        } catch (IOException var10) {
            processor.fatalError("Unable to create " + fileName + ", " + var10);
            return;
        }

    }

    public static String getRootPackageName(List<String> list) {
        String packageName = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).length() < packageName.length()){
                packageName = list.get(i);
            }
        }
        String[] pattern = packageName.split("\\.");
        int len = pattern.length - 1;
        pattern[len] = null;
        //len = len - 1;

        for (int i = 0; i < list.size(); i++) {
            String[] tmp = list.get(i).split("\\.");
            for (int j = tmp.length-1; j >= 0; j--) {
                if (j <= len){
                    if (pattern[j] != null && !pattern[j].equals(tmp[j])){
                        pattern[j] = null;
                        break;
                    }
                }
            }
        }

        packageName = "";
        for (int i = 0; i < len; i++) {
            if (pattern[i] != null) {
                packageName += pattern[i] + ".";
            }
        }
        packageName = packageName.substring(0, packageName.length()-1);
        return packageName;
    }

    public static String serviceVarName(String fullName){
        String[] tmp = fullName.split("\\.");
        String name = tmp[tmp.length-1];
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static void main(String[] args){

        List<String> list = new ArrayList<String>();
        list.add("com.test.prj.m1.s1");
        list.add("com.test.prj.m1.m11.s2");
        list.add("com.test.prj.m1.s3");
        list.add("com.test.prj.m1.m12.s4");

        Collections.sort(list);
        String packageName = getRootPackageName(list);
        System.out.println(packageName);

    }

}
