import java.io.File;
import java.util.*;
public class Main {
    private static String str;
    public static String getStr(){
        return str;
    }
    public static void main(String[] args){
        Utils.initialize();
        java.util.Scanner sc = new java.util.Scanner(System.in);
        //str = sc.nextLine();
        String fileSrc = System.getProperty("user.dir") + File.separator + "test.txt";
        str = Utils.input(fileSrc);
        Scanner.str = str;
        Scanner.length = str.length() - 1;
        while(Scanner.endLoc < str.length()-1){
            Scanner.scanner();
        }
        System.out.println("词法分析结果：");
        System.out.println("***************************");
        int i=0;
        for(Word word : Scanner.words) {
            i++;
            if(i%10==0)
                System.out.println();
            System.out.print(word + ", ");
        }
        System.out.println();
        System.out.println("***************************");
        Parser.parse();
    }

}
