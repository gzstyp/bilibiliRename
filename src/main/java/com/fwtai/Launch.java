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
      replace(path);
      if(args.length > 1){
        final String exist = args[1];
        delPrefix(path);
      }
    }
  }

  protected static boolean dirRename(final String pathDir,final String expression,final String target){
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
          } catch (Exception e) {return false;}
        }
      }
      return true;
    } catch (Exception e){
      return false;
    }
  }

  protected static boolean replace(final String pathDir){
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
          } catch (Exception e){return false;}
        }
      }
      return true;
    } catch (Exception e){
      return false;
    }
  }

  protected static boolean delPrefix(final String pathDir){
    final File dir = new File(pathDir);
    try {
      if(dir.isDirectory()){
        final File[] childFiles = dir.listFiles();
        for(int i = 0; i < childFiles.length; i++){
          final String name = childFiles[i].getPath();
          final String separator = File.separator;
          final int last = name.lastIndexOf(separator);
          final String file_name = name.substring(last + 1);
          final String file_dir = name.substring(0,last);
          try {
            final String less10 = file_name.substring(1,2);
            if(less10.equals(".")){
              final String sub = file_name.substring(2);
              final String fileName = file_dir + separator + sub;
              new File(name).renameTo(new File(fileName));
            }
            final String greater10 = file_name.substring(2,3);
            if(greater10.equals(".")){
              final String sub = file_name.substring(3);
              final String fileName = file_dir + separator + sub;
              new File(name).renameTo(new File(fileName));
            }
            final String greater100 = file_name.substring(3,4);
            if(greater100.equals(".")){
              final String sub = file_name.substring(4);
              final String fileName = file_dir + separator + sub;
              new File(name).renameTo(new File(fileName));
            }
          } catch (Exception e){
            return false;
          }
        }
      }
      return true;
    } catch (Exception e){
      return false;
    }
  }
}