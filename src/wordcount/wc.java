package wordcount;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wc {
	
	//递归处理文件目录
	   
    public static ArrayList<File> findALLFile(File file,ArrayList<File> fileArrayList) {
        if(file.isDirectory())
        {
            File[] lists = file.listFiles();
            if(lists!=null)
            {
                for(int i=0;i<lists.length;i++)
                {
                    findALLFile(lists[i],fileArrayList);
                }
            }
        }
        fileArrayList.add(file);
        return fileArrayList;
    }
    

    //统计数字
   
   public static int countNumber(String str) {
       int count = 0;
       Pattern p = Pattern.compile("\\d");
       Matcher m = p.matcher(str);
       while(m.find()){
           count++;
       }
       return count;
   }
   
 //统计字母
   
   public static int countLetter(String str) {
       int count = 0;
       Pattern p = Pattern.compile("[a-zA-Z]");
       Matcher m = p.matcher(str);
       while(m.find()){
           count++;
       }
       return count;
   }
   

   //统计汉字
  
  public static int countChinese(String str) {
      int count = 0;
      Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
      Matcher m = p.matcher(str);
      while(m.find()){
          count++;
      }
      return count;
  }
  
   //统计行数
  
  public static long[] coutDiff(String line) {
      long notLine = 0;//假注释

      // 判断是否为注释行
      boolean comment = false;
      int whiteLines = 0;
      int commentLines = 0;
      int normalLines = 0;


      if (line.matches("^[//s&&[^//n]]*$") || line.equals("{") || line.equals("}")) {
          // 空行
          whiteLines++;
      }
      else if (line.startsWith("/*") && !line.endsWith("*/") || ((line.startsWith("{/*") || line.startsWith("}/*")) && !line.endsWith("*/"))) {
          // 判断为"/*"开头
          commentLines++;
          comment = true;
      } else if (comment == true && !line.endsWith("*/") && !line.startsWith("*/")) {
          // 为多行注释中的一行（不是开头和结尾）
          notLine++;
          commentLines++;
      } else if (comment == true && (line.endsWith("*/") || line.startsWith("*/"))) {
          // 为多行注释的结束行
          commentLines++;
          comment = false;
      } else if (line.startsWith("//") || line.startsWith("}//") || line.startsWith("{//") ||
              ((line.startsWith("{/*") || line.startsWith("}/*") || line.startsWith("/*")) && line.endsWith("*/"))) {
          // 单行注释行
          commentLines++;
      } else {
          // 正常代码行
          normalLines++;
      }
      long[] args={normalLines+notLine,whiteLines,commentLines- notLine};
      return args;
  }
  
//统计空格
  public static int countSpace(String str) {
      int count = 0;
      Pattern p = Pattern.compile("\\s");
      Matcher m = p.matcher(str);
      while(m.find()){
          count++;
      }
      return count;
  }
  
    public static void main(String[] args){

        int line=0;
        int num=0;
        int letter=0;
        int space=0;
        int word=0;
        String inPath=null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-' && args[i].length() < 3) {
                switch (args[i].charAt(1)) {
                    case 't':
					break;
                    case 'w':
					break;
                    case 'l':
					break;
                    case 'c':
					break;
                    case 'o':
					i++;             
                    break;
                    case 'a':
					break;
                    case 's':
					break;
                    case 'e':
					i++;
                        if (i<args.length){
                        } else {
                            System.out.println("lacking path of out put file");
                        }
                        break;
                    default:
                        break;
                }
            } else {
                inPath = args[i];
            }
        }
        try{
            @SuppressWarnings("resource")
			BufferedReader br= new BufferedReader(new FileReader(inPath));
            String str = null;
            while((str=br.readLine())!=null){
                line++;
                num += countNumber(str);
                letter += countLetter(str);
                word += countChinese(str);
                space += countSpace(str);
            }
           
        }catch (IOException e2){
            e2.printStackTrace();
        }


        System.out.println("数字数："+num);
        System.out.println("字母数"+letter);
        System.out.println("汉字数"+word);
        System.out.println("空格数"+space);
        System.out.println("行数"+line);
    }

 //输出到文件
    
    public static void printToFile(String filepath,String str){
        File file= new File(filepath);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(filepath, true);
            PrintWriter out =new PrintWriter(fw);
            out.println(str);
            fw.flush();
            out.close();
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
     
     //包含stopLists
     
    static int countStopWords( BufferedReader thefile,String txt){
        int stopcount=0;
        int wordcount=0;
        File stopfile=new File(txt);
        ArrayList<String> stop=new ArrayList<String>(3);
        if(stopfile.exists()){
            try{
                FileInputStream fis=new FileInputStream(stopfile);
                InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                String line=new String("");
                StringBuffer sb=new StringBuffer();
                TreeMap<String, Integer> map = new TreeMap<>();
                String[] split =null;
                while((line=br.readLine())!=null){
                    sb.append(line);
                    split = line.split("\\s+");
                    for (int i = 0; i < split.length; i++) {
                        Integer integer = map.get(split[i]);
                        if(null==integer){
                            map.put(split[i], 1);
                        }
                    }
                }
                Set<String> keySet = map.keySet();
                for (String string : keySet) {
                    stop.add(string);
                }
                br.close();
                isr.close();
                fis.close();
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        //统计stop表的总数目

            try{

                BufferedReader br= thefile;
                String line=new String("");
                StringBuffer sb=new StringBuffer();
                TreeMap<String, Integer> map = new TreeMap<>();
                while((line=br.readLine())!=null){
                    String[] split = line.split("\\s++|\\.|,|\\;|\\(|\\)|\\[|\\]|\\<|\\>|\\=|\\-|\\+|\\*|\\/|\\{|\\}");
                    for (int i = 0; i < split.length; i++) {
                        Integer integer = map.get(split[i]);
                        if(null==integer){
                            map.put(split[i], 1);
                        }else{
                            map.put(split[i], ++integer);
                        }
                    }
                }
                Set<String> keySet = map.keySet();
                for (String string : keySet) {
                    int i=0;
                    if(!(string.equals(""))){
                        wordcount+=map.get(string);
                        while(i<stop.size()){
                            if(string.equalsIgnoreCase(stop.get(i++)))
                            {
                                stopcount+=map.get(string);
                                System.out.println(string+":"+map.get(string));
                            }}

                    }
                }
                sb.append(line);
                br.close();

            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
            catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

        return (wordcount-stopcount);
    }
}
    
    