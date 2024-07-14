package Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Utilities {
    public static final Scanner sc = new Scanner(System.in);
    public static final String SEPARATOR=",";
    // Checking whether str matches a pattern or not 
    // Use the method string.matches (regEx)
    public static boolean validStr(String str,String regEx){
        str = str.trim();
        return str.matches(regEx);
    }
    
    // Checking a password with minLen in which it contains at least a character,
    // a number and 1 specific character
    // .*: there may be one or more any character 
    //  \\d: digit  \\W: [^a-zA-Z0-9]: it is not a character and not a digit
    public static boolean validPassword(String str,int minLen){
        str = str.trim();
        return str.length()>=minLen &&
                str.matches(".*[\\d]+.*") && 
                str.matches(".*[\\W]+.*") && 
                str.matches(".*[a-zA-Z]+.*");  
    }
    
    //Date format: DD/MM/YYYY, MM/DD/YYYY, YYYY/MM/DD ...
    public static Date parseDate(String dateStr, String dateFormat){
        SimpleDateFormat dF = (SimpleDateFormat)SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        try{
            long t = dF.parse(dateStr).getTime();
            return new Date(t);
        }catch(ParseException e){
            System.out.println(e);
        }
        return null;
    }
    
    // Use the class SimpleDateFormat 
    // Use the method applyPattern(str) to apply a specific format 
    // Use the method format(date) to convert date -> String 
    
    public static String dateToStr(Date date, String dateFormat){
        SimpleDateFormat dF = new SimpleDateFormat();
        dF.applyPattern(dateFormat);
        return dF.format(date);        
    }
    
    // Tools for inputting data 
    public static String readNonBlank(String message){
        String input="";
        do{
            System.out.print(message+": ");
            input = sc.nextLine().trim();
            
        }while(input.isEmpty());
        return input;
    }
    
    public static String readPattern(String message,String pattern){
        String input="";
        boolean valid;
        do{
            System.out.print(message+": ");
            input = sc.nextLine().trim();
            valid = validStr(input,pattern);
        }while(!valid);
        return input;            
    }
    public static boolean readBool(String message){
        String input;
        System.out.print(message +"[1/0-Y/N-T/F]: ");
        input = sc.nextLine().trim();
        if(input.isEmpty())return false;
        char c = Character.toUpperCase(input.charAt(0));
        return(c=='1' || c=='Y' || c=='T');
    }
    /* Method for reading lines from binart file 
    Create an array list, named as list 
    while(still read succesfully a line in the file){
        trim the line 
        if line is not empty, add line to the list
    }
    close file 
    return list
    */
    public static List<String> readLinesFromFile(String filename){
        ArrayList<String> list = new ArrayList();
        File f = new File(filename);
        if (!f.exists()) return list;
        try{
            //RandomAccessFile rf = new RandomAccessFile(f,"r");
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String line;
            while((line=bf.readLine())!=null){
                //System.out.println(line);
                list.add(line);
            }
            bf.close();fr.close();
            //rf.close();
        }catch(IOException e){
            System.out.println(e);
        }
        return list;
    }
    
    /* Method for writing a list to a binary file line by line 
    open file for writing 
    For each object in the list, write to binary file
    */
    public static void writeFile(String filename,List list ){
        try{
            PrintWriter pw  = new PrintWriter(filename);
            Iterator it = list.iterator();
            for(Object data:list){
                //String data = String.format();
                pw.println(data.toString());
            }
            pw.close();
        }catch(IOException e){
            System.out.println("Error in writeFile");
            System.out.println(e);
        }
    }
    
    //Input double number 
    static public double readDouble(String mess,double min){
        double result=0;
        boolean cont = true;
        do{
            try{
                System.out.print(mess+": ");
                result = Double.parseDouble(sc.nextLine());
                if(result>=min) cont=false;
            }catch(Exception e){};
        }while(cont);
        return result;
    }
    
    static public int readInt(String mess,int min,int max){
        int result=0;
        boolean cont=true;
        do{
            try{
                System.out.print(mess+": ");
                result = Integer.parseInt(sc.nextLine());
                if(result>=min && result<=max)cont=false;
            }catch(Exception e){};
        }while(cont);
        return result;
    }
    
    // Test- It is optional 
    public static void main(String[] args){
        // Phone: 9 or 11 digits - OK
        System.out.println("Test with phone numbers:");
        System.out.println(validStr("012345678","\\d{9}|\\d{11}"));//true
        System.out.println(validStr("01234567891","\\d{9}|\\d{11}"));//true
        System.out.println(validStr("12345678","\\d{9}|\\d{11}"));//false
        
        // Test password - OK
        System.out.println("Test with password:");
        System.out.println(validPassword("qwerty",8)); //false 
        System.out.println(validPassword("qwertyABC",8));//false 
        System.out.println(validPassword("12345678",8));//false 
        System.out.println(validPassword("qbc123456",8));//false
        System.out.println(validPassword("qbc@123456",8));//true
        
        // ID format D000 - OK 
        System.out.println("Test with IDs:");
        System.out.println(validStr("A0001","D\\d{3}"));//false
        System.out.println(validStr("10001","D\\d{3}"));//false 
        System.out.println(validStr("D0001","D\\d{3}"));//false
        System.out.println(validStr("D101","D\\d{3}"));//true 
        
        //Test write, read file - OK
        ArrayList<String> list= new ArrayList();
        list.add("Andy,1,xxx");list.add("Cook,2,yyy");
        list.add("David,3,zzz");list.add("Tim,4,aaa");
        writeFile("test.dat",list);
        System.out.println(readLinesFromFile("test.dat"));
        
        //Test date format -OK
        Date d = parseDate("2002:12:07","yyyy:MM:dd");
        System.out.println(d);
        System.out.println(dateToStr(d,"dd/MM/yyyy"));
        d = parseDate("12/07/2022","MM/dd/yyyy");
        System.out.println(d);
        d= parseDate("2022/07/12","yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/29/02","yyyy/dd/MM");
        System.out.println(d);
        d= parseDate("2000/30/02","yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/40/16","yyyy/dd/MM");
        System.out.println(d);
        String input = readNonBlank("Input a non-blank string");
        System.out.println(input);
        input = readPattern("ID- format X00000","X\\d{5}");
        System.out.println(input);
        boolean b = readBool("Input boolean");
        System.out.println(b);
    }
}

