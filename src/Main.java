import java.io.File;
import java.util.*;
public class Main {
    private static String str = new String();
    //防止不小心改掉str
    public static String getStr(){
        return str;
    }
    public static void main(String[] args){
        Utils.initialize();
        java.util.Scanner sc = new java.util.Scanner(System.in);
        //str = sc.nextLine();
        String fileSrc = System.getProperty("user.dir") + File.separator + "test.txt";
        str = Utils.input(fileSrc)/*+"\0"*/;
        Scanner.str = str;
        Scanner.length = str.length() - 1;
        while(Scanner.endLoc < str.length()-1){
            Scanner.scanner();
        }
        /*
        System.out.println("***************************");
        for(Word word : Scanner.words) {

            System.out.println(word);
        }

         */
        Parser.parse();
    }

}
