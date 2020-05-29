package com.fwtai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class Launch{

    public static void main(final String[] args){
        SpringApplication.run(Launch.class,args);
        if(args.length > 0){
            final String path = args[0];
            System.out.println(path);
            bilibili(path);
        }
    }

    protected final static boolean dirRename(final String pathDir,final String expression,final String target){
        final File dir = new File(pathDir);
        if (expression == null) {
            return false;
        }
        try {
            if(dir.isDirectory()){
                final File[] childFiles = dir.listFiles();
                for(int i = 0; i < childFiles.length; i++){
                    try {
                        final String name = childFiles[i].getPath();
                        final String fileName = name.replaceAll(expression,(target==null ? "" : target));
                        new File(name).renameTo(new File(fileName));
                    } catch (Exception e) {}
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    protected final static boolean bilibili(final String pathDir){
        final File dir = new File(pathDir);
        try {
            if(dir.isDirectory()){
                final File[] childFiles = dir.listFiles();
                for(int i = 0; i < childFiles.length; i++){
                    final String name = childFiles[i].getPath();
                    int prefix = name.lastIndexOf("(A");
                    int suffix = name.lastIndexOf(").")+1;
                    try {
                        final String expression = name.substring(prefix,suffix);
                        final String fileName = name.replaceAll(expression,"");
                        new File(name).renameTo(new File(fileName));
                        dirRename(pathDir,"\\(\\)","");
                    } catch (Exception e){}
                }
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }
}