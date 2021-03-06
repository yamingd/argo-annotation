package com.argo.processor;

import com.google.common.base.Charsets;
import com.google.common.io.Closer;

import java.io.*;
import java.util.*;

/**
 * Created by yamingd on 9/11/15.
 */
public class ServicesFiles {

    public static final String SERVICES_PATH = "META-INF/services";

    private ServicesFiles() {
    }

    static String getPath(String serviceName) {
        return "META-INF/services/" + serviceName;
    }

    static Set<String> readServiceFile(InputStream input) throws IOException {
        HashSet serviceClasses = new HashSet();
        Closer closer = Closer.create();

        try {
            BufferedReader t = (BufferedReader)closer.register(new BufferedReader(new InputStreamReader(input, Charsets.UTF_8)));

            String line;
            while((line = t.readLine()) != null) {
                int commentStart = line.indexOf(35);
                if(commentStart >= 0) {
                    line = line.substring(0, commentStart);
                }

                line = line.trim();
                if(!line.isEmpty()) {
                    serviceClasses.add(line);
                }
            }

            HashSet commentStart1 = serviceClasses;
            return commentStart1;
        } catch (Throwable var9) {
            throw closer.rethrow(var9);
        } finally {
            closer.close();
        }
    }

    static void writeServiceFile(Collection<String> services, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charsets.UTF_8));
        Iterator itor = services.iterator();

        while(itor.hasNext()) {
            String service = (String)itor.next();
            writer.write(service);
            writer.newLine();
        }

        writer.flush();
    }

    static void writeServiceInterfaceFile(Collection<String> services, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charsets.UTF_8));
        Iterator itor = services.iterator();

        while(itor.hasNext()) {
            String service = (String)itor.next();
            writer.write(service);
            writer.newLine();
        }

        writer.flush();
    }

    static void writeYamlListFile(Collection<String> services, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, Charsets.UTF_8));
        Iterator itor = services.iterator();
        writer.write(String.format("# generated by processor. %s", new Date()));
        writer.newLine();
        writer.write("list:");
        writer.newLine();
        while(itor.hasNext()) {
            String service = (String)itor.next();
            writer.write(String.format(" - %s", service));
            writer.newLine();
        }

        writer.flush();
    }
}
