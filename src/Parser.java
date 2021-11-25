import java.util.ArrayList;
import java.util.List;
public class Parser {
    public static final int VARIABLE    = 10;
    public static final int FIGURE      = 20;
    public static final int IF          = 4;
    public static final int WHILE       = 7;
    public static final int CONDITION_START = 35;
    public static final int CONDITION_END   = 40;

    public static ArrayList<Four> pQuad = new ArrayList<>();
    public static int nSuffix = 0, nNXQ = 0, ntc = 1, nfc = 1;
    public static int nChain = 1;
    public static List<Word> words = Scanner.words;
    public static Pointer p;
    /**
     * 匹配开头
     */
    public static void parse() {
        p = new Pointer(0,words.size());
        words.get(p.getpp()).check("int");
        words.get(p.getpp()).check("main");
        words.get(p.getpp()).check("(");
        words.get(p.getpp()).check(")");
        statementBlock();
        printQuaternion();
    }

    /**
     * TODO Rewrite
     */
    public static void printQuaternion() {
        for (int loop = 0; loop < pQuad.size(); loop++) {
            System.out.println(loop + 1 + ":" + pQuad.get(loop));
        }
    }

    /**
     * 匹配{}
     */
    public static void statementBlock() {
        words.get(p.getpp()).check("{");
        statementSequence();
        words.get(p.getpp()).check("}");
    }

    /**
     * 匹配 if & while
     * TODO
     */
    public static void statementSequence() {
        statement();
        while (words.get(p.get()).isVariable() || words.get(p.get()).equals("if") || words.get(p.get()).equals("while")) {
            bp(nChain, nNXQ);
            statement();
        }
        bp(nChain, nNXQ);
    }

    /**
     * 将T回填到四元式中
     * @param p
     * @param t
     */
    public static void bp(int p, int t) {
        int w, q = p;
        while (q != 0 && q < pQuad.size()) {
            if (isNumeric(pQuad.get(q).getResult())) {
                w = Integer.parseInt(pQuad.get(q).getResult());
            } else {
                w = 0;
            }
            //语句行数从1开始，因此要+1
            pQuad.get(q).setResult(t+1+"");
            q = w;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++){
            //System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO
     */
    public static void statement() {
        String result, var1;
        int temp = 1;
        int nWQUAD;
        switch (words.get(p.get()).getTypeNumber()) {
            case VARIABLE:
                result = words.get(p.get()).getWord();
                //变量后面跟 =
                p.getpp();
                words.get(p.getpp()).check("=");
                //判断后面跟不跟 +-*/
                var1 = expression();
                //是否有结束分号
                //TODO 出错提示
                words.get(p.getpp()).check(";");
                gen("=", var1, "", result);
                nChain = 0;
                break;
            case IF:
                //if起始判断
                words.get(p.getpp()).check("if");
                words.get(p.getpp()).check("(");
                //
                condition();//
                bp(ntc, nNXQ);
                words.get(p.getpp()).check(")");
                nChain = temp;
                statementBlock();
                nChain = merg(nChain, nfc);
                break;
            case WHILE:
                words.get(p.getpp()).check("while");
                nWQUAD = nNXQ;
                words.get(p.getpp()).check("(");
                condition();
                int tmp = nfc;          //while里有if时
                bp(ntc, nNXQ);
                words.get(p.getpp()).check(")");
                nChain = temp;
                statementBlock();
                bp(nChain, nWQUAD);
                //由于第一行算1，因此要+1
                result = nWQUAD + 1 + "";
                gen("jmp", "", "", result);
                nChain = tmp;
                break;
        }
        return;
    }


    private static void gen(String op, String var1, String var2, String result) {
        Four four = new Four(op, var1, var2, result);
        pQuad.add(four);
        nNXQ++;
    }

    /**
     * 判断关系运算符
     */
    private static void condition() {
        String op, val1, val2;
        String strTemp;
        //读出左边表达式，得到变量
        val1 = expression();
        //匹配关系运算符
        if (words.get(p.get()).getTypeNumber() >= CONDITION_START && words.get(p.get()).getTypeNumber() <= CONDITION_END) {
            //读出关系运算符
            op = words.get(p.getpp()).getWord();
            //读出右边表达式，得到变量
            val2 = expression();
            ntc = nNXQ;
            nfc = nNXQ + 1;
            strTemp = "jmpIF" + op;
            gen(strTemp, val1, val2, "0");
            gen("jmp", "", "", "0");
        } else {
            //ERROR OUTPUT
            words.get(p.get()).equals("<");
        }
    }

    private static int merg(int p1, int p2) {
        int p, result;
        if (p2 == 0) {
            result = p1;
        } else {
            result = p = p2;
            while (!isNumeric(pQuad.get(p).getResult()) && Integer.parseInt(pQuad.get(p).getResult()) != 0) {
                p = Integer.parseInt(pQuad.get(p).getResult());
                pQuad.get(p).setResult(p1 + "");
            }
        }
        return result;
    }

    /**
     * 加减
     * @return
     */
    public static String expression() {
        String op, result, var1, var2;
        var1 = term();
        result = var1;       //eplace1;
        while (words.get(p.get()).equals("+") || words.get(p.get()).equals("-")) {
            op = words.get(p.get()).getWord();             //+ or -
            p.getpp();
            var2 = term();
            result = newtemp();
            gen(op, var1, var2, result);
            var1 = result;
        }
        return result;
    }

    private static String newtemp() {
        nSuffix++;
        return "T" + nSuffix;
    }

    public static String term() {
        String op, result, var1, var2;
        result = var1 = factor();
        while (words.get(p.get()).equals("*") || words.get(p.get()).equals("/")) {
            op = words.get(p.get()).getWord();
            p.getpp();
            var2 = factor();
            result = newtemp();
            gen(op, var1, var2, result);
            var1 = result;
        }
        return result;
    }

    /**
     * 左右中括号
     * @return
     */
    public static String factor() {
        String var = "";
        if (words.get(p.get()).isVariable() || words.get(p.get()).isFigure()) //为标识符或整常数时，读下一个单词符号
        {
            var = words.get(p.get()).getWord();
            p.getpp();
        } else if (words.get(p.getpp()).check("(")) {
            var = expression();
            if (!words.get(p.getpp()).check(")")) {
            }
        }
        return var;
    }

    /**
     * TODO
     * @param errorWord
     * @param line
     */
    public static void errorNotification(String errorWord, int line){

    }
}
