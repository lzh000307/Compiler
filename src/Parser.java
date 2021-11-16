import java.util.ArrayList;
import java.util.List;
public class Parser {
    public static final int VARIABLE = 10;      //50
    public static final int FIGURE = 20;    //51

    public static ArrayList<Four> pQuad = new ArrayList<>();
    public static int nSuffix = 0, nNXQ = 1, ntc[] = {1}, nfc[] = {1};

    public static List<Word> words = Scanner.words;
    public static int current=0;
    public static void parse() {
        int nChain[] = new int[1]/*= {1}*/;
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
        int nLoop = 0;
        for (nLoop = 1; nLoop < nNXQ; nLoop++) {
            System.out.println(nLoop + ":(" + pQuad.get(nLoop).op + "," + pQuad.get(nLoop).argv1 + "," + pQuad.get(nLoop).argv2 + "," + pQuad.get(nLoop).result + ")");
        }
    }

    public static void statementBlock(int[] nChain) {
        words.get(current++).equals("{");
        statementSequence(nChain);
        words.get(current++).equals("}");
    }


    public static void statementSequence(int[] nChain) {
        statement(nChain);
        while (words.get(current).isVariable() || words.get(current).equals("if") || words.get(current).equals("while")) {
            bp(nChain[0], nNXQ);
            statement(nChain);
        }
        bp(nChain[0], nNXQ);
    }


    public static void bp(int p, int t) {
        int w, q = p;
        while (q != 0 && q < pQuad.size()) {
            if (!isNumeric(pQuad.get(q).result)) {
                w = Integer.parseInt(pQuad.get(q).result);
            } else {
                w = 0;
            }
            //w = Integer.parseInt(pQuad.get(q).result);
            pQuad.get(q).result = t + "";
            q = w;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void statement(int[] nChain) {
        String strTemp, eplace;
        int nChainTemp[] = new int[1]/*{1}*/;
        int nWQUAD;
        switch (words.get(current).getTypeNumber()) {
            case 50:
                strTemp = words.get(current).getWord();
                words.get(++current).equals("=");
                eplace = expression();
                words.get(current++).equals(";");
                gen("=", eplace, "", strTemp);
                nChain[0] = 0;
                break;
            case 8://symbol.get("if"):
                words.get(current++).equals("if");
                words.get(current++).equals("(");
                condition(ntc, nfc);//
                bp(ntc[0], nNXQ);
                words.get(current++).equals(")");
                statementBlock(nChainTemp);
                nChain[0] = merg(nChainTemp[0], nfc[0]);
                break;
            case 6://symbol.get("while"):
                words.get(current++).equals("while");
                nWQUAD = nNXQ;
                words.get(current++).equals("(");
                condition(ntc, nfc);
                int nfcInt = nfc[0];//这里加这句，是因为while里有if时，while里的nfc会被覆盖，那么下方nChain[0] = nfcInt;就得到错误的nChain[0]
                bp(ntc[0], nNXQ);
                words.get(current++).equals(")");
                statementBlock(nChainTemp);
                bp(nChainTemp[0], nWQUAD);
                strTemp = nWQUAD + "";
                gen("j", "", "", strTemp);
                nChain[0] = nfcInt;
                break;
        }
        return;
    }


    private static void gen(String op, String argv1, String argv2, String result) {
        Four four = new Four(op, argv1, argv2, result);
        pQuad.add(four);
        nNXQ++;
    }

    private static void condition(int[] etc, int[] efc) {
        String opp, eplace1, eplace2;
        String strTemp;
        eplace1 = expression();
        // TODO 这里通配了，要看TypeNumber更改
        if (words.get(current).getTypeNumber() >= 32 /*symbol.get("<")*/ && words.get(current).getTypeNumber() <= 40 /*symbol.get("!=")*/) {
            if (words.get(current).equals("<") || words.get(current).equals(">")) {
                opp = words.get(current).getWord();
            } else {
                opp = words.get(current).getWord();
            }
            current++;
            eplace2 = expression();
            etc[0] = nNXQ;
            efc[0] = nNXQ + 1;
            strTemp = "j" + opp;
            gen(strTemp, eplace1, eplace2, "0");
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
            while (!isNumeric(pQuad.get(p).result) && Integer.parseInt(pQuad.get(p).result) != 0) {
                p = Integer.parseInt(pQuad.get(p).result);
                pQuad.get(p).result = p1 + "";
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
            opp = words.get(current).getWord();
            current++;
            eplace2 = term();
            eplace = newtemp();
            gen(opp, eplace1, eplace2, eplace);
            eplace1 = eplace;//这里strcpy()
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
