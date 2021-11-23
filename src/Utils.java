import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Utils {
    public static HashMap<String, Integer> wordsList = new HashMap<>();
    public static void initialize() {
        wordsList.put("main", 1);
        wordsList.put("int", 2);
        wordsList.put("char", 3);
        wordsList.put("if", 4);
        wordsList.put("else", 5);
        //wordsList.put("for", 6);
        wordsList.put("while", 7);
        //wordsList.put("else", 9);     //optional
        //wordsList.put("do", 10);      //optional
        //运算符 21-25
        wordsList.put("=", 21);
        wordsList.put("+", 22);
        wordsList.put("-", 23);
        wordsList.put("*", 24);
        wordsList.put("/", 25);
        //
        wordsList.put("(", 26);
        wordsList.put(")", 27);
        wordsList.put("[", 28);
        wordsList.put("]", 29);
        wordsList.put("{", 30);
        wordsList.put("}", 31);
        wordsList.put(",", 32);
        wordsList.put(":", 33);
        wordsList.put(";", 34);
        //关系运算符 35-40
        wordsList.put(">", 35);
        wordsList.put("<", 36);
        wordsList.put(">=", 37);
        wordsList.put("<=", 38);
        wordsList.put("==", 39);
        wordsList.put("!=", 40);
        //还有注释
        wordsList.put("\\0", 1000);
        wordsList.put("ERROR", -1);
        wordsList.put("NOTIFICATION", -2);
        // 单词是10, 数字是20
    }

    /**
     * 获取单词首字母地址
     * @return Position of the initial character after a blank
     */
    private static int location = 0;
    public static int getInitialCharLoc(){
        return location;
    }

    /**
     *
     * @param ch
     * @return
     */
    public static boolean isLetter(char ch) {
        if ((ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z'))
            return true;
        else
            return false;
    }

    // 判断是否数字
    public static boolean isDigit(char ch) {
        if (ch >= '0' && ch <= '9')
            return true;
        else
            return false;
    }

    public static void inputFromTerminal(){

    }

    /**
     *
     * @param fileSrc
     * @return
     */
    public static String input(String fileSrc) {
        StringBuffer bf = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileSrc));
            String temp;
            while ((temp = br.readLine()) != null) {
                bf.append(temp);
                bf.append("\n"); //ASCII=10
            }
            bf.append("\0");    //ASCII=0
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bf);
    }

    /**
     * 出错提示
     */
    /*
    public static String errorNotification(int errorType){
        switch (errorType){
            case GRAMMER_ERROR:
                return "Grammer Error 语法错误: ";
                break;
        }
    }

     */
}
