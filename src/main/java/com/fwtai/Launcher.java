package com.fwtai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 需要输入有3个参数;第1个是文件夹路径;第2个为:1或2;1表示需要删除前缀;当为2时是第2次执行;一般前执行要1步骤是再执行第2个参数问2的情况;2表示要删除指定的字符串,也就是第3个参数
 * 示例:
 * "F:\bilibili\下载的目录" 1
 * "F:\bilibili\下载的目录" 2 "替换的字符串"
 * 若需要删除以数字开头的则先执行 "F:\bilibili\下载的目录" 1,若执行完后还需要替换指定的字符串再执行 "F:\bilibili\下载的目录" 2 "被替换的字符串"
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2020/12/7 15:22
 * @QQ号码 444141300
 * @Email service@yinlz.com
 * @官网 <url>http://www.yinlz.com</url>
*/
@SpringBootApplication
public class Launcher{

  public static void main(final String[] args){
    SpringApplication.run(Launcher.class,args);
    if(args.length > 0){
      final String path = args[0];
      System.out.println(path);
      replace(path);
      if(args.length > 1){
        final int type = Integer.parseInt(args[1]);
        switch (type){
            case 1:// "F:\bilibili\xxx" 1
              delPrefix(path);
              break;
            case 2:// "F:\bilibili\xxx" 2 "4-";
              if(args.length > 2){
                final String expression = args[2];
                if(expression != null && expression.length() >0)
                  delExpression(path,expression);
              }
              break;
            default:
              break;
        }
      }
    }
  }

  protected static void replace(final String pathDir){
    final File dir = new File(pathDir);
    if(dir.isDirectory()){
      final File[] childFiles = dir.listFiles();
      for(int i = 0; i < childFiles.length; i++){
        final String name = childFiles[i].getPath();
        final int prefix = name.lastIndexOf("(A");
        final int suffix = name.lastIndexOf(").")+1;
        try {
          final String expression = name.substring(prefix,suffix);
          String fileName = name.replaceAll(expression,"").replaceAll("\\(\\)","");
          final Path sourcePath = FileSystems.getDefault().getPath(pathDir+"\\"+name);
          final String numberPrefix = fileName.substring(0,2);
          if(numberPrefix.endsWith(".")){
            final int number = Integer.parseInt(numberPrefix.substring(0,1));
            if(number <10){
              fileName = "0"+fileName;
            }
          }
          final Path targetPath = FileSystems.getDefault().getPath(pathDir+"\\"+fileName);
          Files.move(sourcePath,targetPath);
        } catch (final Exception ignored){}
      }
    }
  }

  private static void delPrefix(final String pathDir){
    final File dir = new File(pathDir);
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
        } catch (final Exception ignored){}
      }
    }
  }

  private static void delExpression(final String pathDir,final String expression){
    final File dir = new File(pathDir);
    if(dir.isDirectory()){
      final File[] childFiles = dir.listFiles();
      for(int i = 0; i < childFiles.length; i++){
        final String name = childFiles[i].getPath();
        final String separator = File.separator;
        final int last = name.lastIndexOf(separator);
        final String file_name = name.substring(last + 1);
        final String file_dir = name.substring(0,last);
        try {
          final int length = expression.length();
          final int index = file_name.lastIndexOf(expression);
          final String sub = file_name.substring(index,length+index);
          final String file = file_name.replaceAll(sub,"");
          final String fileName = file_dir + separator + file;
          new File(name).renameTo(new File(fileName));
        } catch (final Exception ignored){}
      }
    }
  }
}