import java.util.ArrayList;
import java.util.List;

public class Parser_bak {
    public static final int VARIABLE    = 10;
    public static final int FIGURE      = 20;
    public static final int IF          = 4;
    public static final int FOR         = 6;
    public static final int WHILE       = 7;
    public static final int CONDITION_START = 35;
    public static final int CONDITION_END   = 40;

    public static ArrayList<Four> pQuad = new ArrayList<>();
    public static int nSuffix = 0, nNXQ = 0, ntc = 1, nfc = 1;
    public static int nChain = 1;
    public static List<Word> words = Scanner.words;
    public static int current=0;

    /**
     * 匹配开头
     */
    public static void parse() {
        //int nChain = 1 /* new int[1]= {1} */;
        words.get(current++).equals("int");
        words.get(current++).equals("main");
        words.get(current++).equals("(");
        words.get(current++).equals(")");
        statementBlock(nChain);
        printQuaternion();
    }

    /**
     * TODO Rewrite
     */
    public static void printQuaternion() {
        //int nLoop;
        //for (nLoop = 1; nLoop < nNXQ; nLoop++) {
        for (int loop = 0; loop < pQuad.size(); loop++) {
            System.out.println(loop + 1 + ":" + pQuad.get(loop));
        }
    }

    /**
     * 匹配{}
     * @param nChain
     */
    public static void statementBlock(int nChain) {
        words.get(current++).equals("{");
        statementSequence(nChain);
        words.get(current++).equals("}");
    }

    /**
     * 匹配 if & while
     * TODO
     * @param nChain
     */
    public static void statementSequence(int nChain) {
        statement(nChain);
        while (words.get(current).isVariable() || words.get(current).equals("if") || words.get(current).equals("while")) {
            bp(nChain, nNXQ);
            statement(nChain);
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
            //w = Integer.parseInt(pQuad.get(q).result);
            //TODO
            pQuad.get(q).setResult(t+"");
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
     * @param nChain
     */
    public static void statement(int nChain) {
        String strTemp, eplace;
        int nChainTemp = 1/*new int[1]/*{1}*/;
        int nWQUAD;
        switch (words.get(current).getTypeNumber()) {
            case VARIABLE:
                strTemp = words.get(current).getWord();
                //变量后面跟 =
                words.get(++current).equals("=");
                current++;
                //判断后面跟不跟 +-*/
                eplace = expression();
                //是否有结束分号
                //TODO 出错提示
                words.get(current++).equals(";");

                gen("=", eplace, "", strTemp);
                nChain = 0;
                break;
            case IF:
                //if起始判断
                words.get(current++).equals("if");
                words.get(current++).equals("(");
                //
                condition(ntc, nfc);//
                bp(ntc, nNXQ);
                words.get(current++).equals(")");
                statementBlock(nChainTemp);
                nChain = merg(nChainTemp, nfc);
                break;
            case WHILE:
                words.get(current++).equals("while");
                nWQUAD = nNXQ;
                words.get(current++).equals("(");
                condition(ntc, nfc);
                int nfcInt = nfc;//这里加这句，是因为while里有if时，while里的nfc会被覆盖，那么下方nChain[0] = nfcInt;就得到错误的nChain[0]
                bp(ntc, nNXQ);
                words.get(current++).equals(")");
                statementBlock(nChainTemp);
                bp(nChainTemp, nWQUAD);
                strTemp = nWQUAD + "";
                gen("j", "", "", strTemp);
                nChain = nfcInt;
                break;
        }
        return;
    }


    private static void gen(String op, String argv1, String argv2, String result) {
        Four four = new Four(op, argv1, argv2, result);
        pQuad.add(four);
        nNXQ++;
    }

    /**
     * 判断关系运算符
     * @param etc
     * @param efc
     */
    private static void condition(int etc, int efc) {
        String op, val1, val2;
        String strTemp;
        //读出左边表达式，得到变量
        val1 = expression();
        // 匹配判断符
        if (words.get(current).getTypeNumber() >= CONDITION_START && words.get(current).getTypeNumber() <= CONDITION_END) {
            //<>之分
            /*
            if (words.get(current).equals("<") || words.get(current).equals(">")) {
                opp = words.get(current).getWord();
            } else {
                opp = words.get(current).getWord();
            }

            */
            //读出判断符号
            op = words.get(current++).getWord();
            //读出右边表达式，得到变量
            val2 = expression();
            //TODO delete?
            /*
            etc[0] = nNXQ;
            efc[0] = nNXQ + 1;
             */
            // TODO j
            strTemp = "j" + op;
            // TODO why
            gen(strTemp, val1, val2, "0");
            gen("j", "", "", "0");
        } else {
            //TODO ERROR OUTPUT
            //error("关系运算符");
        }
    }

    private static int merg(int p1, int p2) {
        int p, nResult;
        if (p2 == 0) {
            nResult = p1;
        } else {
            nResult = p = p2;
            while (!isNumeric(pQuad.get(p).getResult()) && Integer.parseInt(pQuad.get(p).getResult()) != 0) {
                p = Integer.parseInt(pQuad.get(p).getResult());
                //TODO
               // pQuad.get(p).setResult(p1 + "");
            }
        }
        return nResult;
    }

    /**
     * 加减
     * @return
     */
    public static String expression() {
        String opp, eplace, eplace1, eplace2;
        eplace1 = term();
        eplace = eplace1;       //eplace1;
        while (words.get(current).equals("+") || words.get(current).equals("-")) {
            opp = words.get(current).getWord();             //+ or -
            current++;
            eplace2 = term();
            eplace = newtemp();
            gen(opp, eplace1, eplace2, eplace);
            eplace1 = eplace;   //这里strcpy()
        }
        return eplace;
    }

    private static String newtemp() {
        nSuffix++;
        return "T" + nSuffix;
    }

    public static String term() {
        String opp, eplace, eplace1, eplace2;
        eplace = eplace1 = factor();
        while (words.get(current).equals("*") || words.get(current).equals("/")) {
            opp = words.get(current).getWord();
            current++;
            eplace2 = factor();
            eplace = newtemp();
            gen(opp, eplace1, eplace2, eplace);
            eplace1 = eplace;
        }
        return eplace;
    }

    /**
     * 左右中括号
     * @return
     */
    public static String factor() {
        String eplace = "";
        if (words.get(current).isVariable() || words.get(current).isFigure()) //为标识符或整常数时，读下一个单词符号
        {
            eplace = words.get(current).getWord();
            current++;
        } else if (words.get(current++).equals("(")) {
            //match(symbol.get("("), "(");
            eplace = expression();
            if (!words.get(current++).equals(")")) {
                //match(symbol.get(")"), ")");
                //TODO ERROR NOTIFICATION
            }
        }
        return eplace;
    }

    /**
     * TODO
     * @param errorWord
     * @param line
     */
    public static void errorNotification(String errorWord, int line){

    }
}
