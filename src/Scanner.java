import java.util.ArrayList;
import java.util.List;

public class Scanner {
    public static Word word = new Word();
    public static List<Word> words = new ArrayList<Word>();
    public static int line = 1;
    public static int startLoc = 0;
    public static int endLoc = 0;
    public static String str;
    public static int length;
    public static char ch;
    public static void scanner(){
        word = new Word();
        str = Main.getStr();
        length = str.length() - 1;
        //System.out.println(str + startLoc);
        //startLoc = Utils.getInitialCharLoc();       //get recent word's location
        ch = next();            //get the first character
        startLoc = endLoc-1;      //Store the start location
        //endLoc = startLoc + 1;
        if (Utils.isLetter(ch)) {
            //Find the whole word
            while (Utils.isLetter(ch) || Utils.isDigit(ch)){
                ch = str.charAt(endLoc++);
            }
            ch=back();
            String tempStr = str.substring(startLoc, endLoc);
            //if it is a key word
            if(Utils.wordsList.get(tempStr)!=null) {
                word.set(tempStr);
            }else{
                word.set(10,tempStr);           //变量是10
            }
        }else if(Utils.isDigit(ch)){       //is Digit
            //Find the whole number
            boolean point = false;
            while (Utils.isDigit(ch)){
                ch = str.charAt(endLoc++);
                if(ch == '.' && !point){
                    point = true;
                    ch = str.charAt(endLoc++);
                    if(!Utils.isDigit(ch)){     //not a number after point
                        ch = back();
                    }
                }
            }
            ch=back();
            String tempStr = str.substring(startLoc, endLoc);
            //if it is a key word
            if(Utils.wordsList.get(tempStr)!=null) {
                word.set(tempStr);
            }else{
                word.set(20,tempStr);       //数字是20
            }
        } else {       //数字不会是key word, 判定完毕
            switch(ch){
                case '+','-','*','(',')',':',';','{','}',',':
                    word.set(Character.toString(ch));
                    break;
                case '/':
                    ch=next();  //判定下一位
                    if(ch == '/'){
                        ch = str.charAt(endLoc++);
                        while(ch != 10){
                            ch = str.charAt(endLoc++);
                        }
                        line++;
                        break;
                    }else if(ch == '*'){
                        ch = next();
                        while(ch != '*'){
                            ch = next();
                            if(ch == '*'){
                                ch = next();
                                if(ch == '/')
                                    break;
                                ch = back();
                            }
                        }
                    }
                    //TODO Error
                    ch = back();
                    word.set("/");
                    break;
                case '<':
                    ch = next();
                    if(ch == '='){
                        word.set("<=");
                        break;
                    }
                    ch = back();
                    word.set("<");
                    break;
                case '>':
                    ch = next();
                    if(ch == '='){
                        word.set(">=");
                        break;
                    }
                    ch = back();
                    word.set(">");
                    break;
                case '=':
                    ch = next();
                    if(ch == '='){
                        word.set("==");
                        break;
                    }
                    ch = back();
                    word.set("=");
                    break;
                case '!':
                    ch = next();
                    if(ch == '='){
                        word.set("!=");
                        break;
                    }
                    ch = back();
                    Parser.errorNotification("!=", line);
                    break;
                case '\0':
                    word.set("\\0");
                    System.out.println(str.length() + " + " + endLoc);
                    return;
                default:
                    Parser.errorNotification(Character.toString(ch), line);

            }
        }
        //startLoc = endLoc++;
        if(word.getTypeNumber()!=0)
            words.add(word);
        return;
    }

    /**
     * read next character
     * @return
     */
    public static char next(){
        while((str.charAt(endLoc) == ' ' || str.charAt(endLoc)=='\n')&& length < endLoc) {
            while (str.charAt(endLoc) == '\n' && length < endLoc) {
                endLoc++;
                line++;
            }
            endLoc++;
        }
        if(length <= endLoc)
            return '\0';
        return str.charAt(endLoc++);
    }

    /**
     * drawback
     * @return
     */
    public static char back(){
        endLoc = endLoc - 2;
        return str.charAt(endLoc++);
    }

}
