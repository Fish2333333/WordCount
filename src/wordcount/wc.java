package wordcount;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wc {
	
	//�ݹ鴦���ļ�Ŀ¼
	   
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
    

    //ͳ������
   
   public static int countNumber(String str) {
       int count = 0;
       Pattern p = Pattern.compile("\\d");
       Matcher m = p.matcher(str);
       while(m.find()){
           count++;
       }
       return count;
   }
   
 //ͳ����ĸ
   
   public static int countLetter(String str) {
       int count = 0;
       Pattern p = Pattern.compile("[a-zA-Z]");
       Matcher m = p.matcher(str);
       while(m.find()){
           count++;
       }
       return count;
   }
   

   //ͳ�ƺ���
  
  public static int countChinese(String str) {
      int count = 0;
      Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
      Matcher m = p.matcher(str);
      while(m.find()){
          count++;
      }
      return count;
  }
  
   //ͳ������
  
  public static long[] coutDiff(String line) {
      long notLine = 0;//��ע��

      // �ж��Ƿ�Ϊע����
      boolean comment = false;
      int whiteLines = 0;
      int commentLines = 0;
      int normalLines = 0;


      if (line.matches("^[//s&&[^//n]]*$") || line.equals("{") || line.equals("}")) {
          // ����
          whiteLines++;
      }
      else if (line.startsWith("/*") && !line.endsWith("*/") || ((line.startsWith("{/*") || line.startsWith("}/*")) && !line.endsWith("*/"))) {
          // �ж�Ϊ"/*"��ͷ
          commentLines++;
          comment = true;
      } else if (comment == true && !line.endsWith("*/") && !line.startsWith("*/")) {
          // Ϊ����ע���е�һ�У����ǿ�ͷ�ͽ�β��
          notLine++;
          commentLines++;
      } else if (comment == true && (line.endsWith("*/") || line.startsWith("*/"))) {
          // Ϊ����ע�͵Ľ�����
          commentLines++;
          comment = false;
      } else if (line.startsWith("//") || line.startsWith("}//") || line.startsWith("{//") ||
              ((line.startsWith("{/*") || line.startsWith("}/*") || line.startsWith("/*")) && line.endsWith("*/"))) {
          // ����ע����
          commentLines++;
      } else {
          // ����������
          normalLines++;
      }
      long[] args={normalLines+notLine,whiteLines,commentLines- notLine};
      return args;
  }
  
//ͳ�ƿո�
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


        System.out.println("��������"+num);
        System.out.println("��ĸ��"+letter);
        System.out.println("������"+word);
        System.out.println("�ո���"+space);
        System.out.println("����"+line);
    }

 //������ļ�
    
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
     
     //����stopLists
     
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
        //ͳ��stop�������Ŀ

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
    
    