package wordcount;

import java.io.*;
import java.util.Scanner;

public class main {

    private static String File;
    public static int line =0;//行数
    public static int charC =0;//字符数
    public static int word =0;//单词数

    
    //计数
    public static void count(String filePath){
        try {
            File file=new File(filePath);
            if (!file.exists()){
                return;
            }
            File=file.getName();

            FileInputStream fis=new FileInputStream(file);
            InputStreamReader isr=new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String s="";
            while ((s=br.readLine())!=null) {
                if(s.length()>1){
                    for (int i=1;i<s.length();i++){
                        if ((s.charAt(i)==' '&&s.charAt(i-1)!=' '&&s.charAt(i-1)!=','&&s.charAt(i-1)!='，')||
                                (s.charAt(i)==','&&s.charAt(i-1)!=' '&&s.charAt(i-1)!=','&&s.charAt(i-1)!='，')
                                ||(s.charAt(i)=='，'&&s.charAt(i-1)!=' '&&s.charAt(i-1)!=','&&s.charAt(i-1)!=',')){
                            word++;
                        }
                        if (s.charAt(i)!=' '&&s.charAt(i)!='，'&&s.charAt(i)!=','&&i==s.length()-1) word++;
                    }
                }else {
                    if(s.equals(" ")||s.equals(","))continue;
                    word++;
                }
                line++;
                charC += s.length();
            }
            br.close();
            isr.close();
            fis.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //输出
    public static void ouput(File filePath,String[] arr){

        BufferedWriter bw=null;
        try {
            FileWriter fw = new FileWriter(filePath.getAbsoluteFile());
            bw = new BufferedWriter(fw);
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String c:arr){
            switch (c){
                case "c":
                    try {
                        bw.write(File+",字符数:"+ charC +'\n');
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                
                case "w":
                    try {
                        bw.write(File+",单词数:"+ word +'\n');
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                    
                case "l":
                    try {
                        bw.write(File+",行数:"+ line +'\n');
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                default:break;
            }
        }
        try {
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner scanner=new Scanner(System.in);
        System.out.print("请输入目标文件地址:");
        String filePath=scanner.nextLine();

        System.out.print("请输入想要进行的操作:");
        String[] charArr=scanner.nextLine().split(" ");

        System.out.print("请输入输出文件地址:");
        String outputFile=scanner.nextLine();

        File file=new File(outputFile);
        count(filePath);
        if (file.exists()){
            ouput(file,charArr);
        }
        for (String c:charArr){
            switch (c){
                case "c":
                    System.out.println("字符数:"+ charC);
                    break;
                case "l":
                    System.out.println("行数:"+ line);
                    break;
                case "w":
                    System.out.println("单词数:"+ word);
                    break;
                default:break;
            }
        }
    }

}