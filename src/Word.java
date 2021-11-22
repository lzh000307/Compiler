import java.util.Objects;

public class Word {
    private int typeNumber;         //我查不到种别码的英文是什么
    private String word;            //symbol of a word
    private int line;               //To storage the line that word in
    private int position;           //记录该单词位置
    private int lineStartPosition;  //该行开始的位置

    public Word(){}

    public Word(int typeNumber, String word, int line){
        this.typeNumber = typeNumber;
        this.word = word;
        this.line = line;
    }

    public int getTypeNumber() {
        return typeNumber;
    }


    public String getWord() {
        return word;
    }

    public int getLine() {
        return line;
    }

    public void set(String word){
        this.word = word;
        this.typeNumber = Utils.wordsList.get(word);
        //printPosition();
    }
    public void set(int typeNumber, String word){
        this.typeNumber = typeNumber;
        this.word = word;
        //this.line = 0;
        //printPosition();
    }

    /**
     * 打印该字段所在行所在位置
     */
    public void printPosition(){
        int point = lineStartPosition;
        int linePosition = position - lineStartPosition;
        System.out.println("Line: " + line);
        while(Scanner.getStr().charAt(point) != '\n') {
            System.out.print(Scanner.getStr().charAt(point++));
        }
        System.out.println();
        while(linePosition--!=0){
            System.out.print(" ");
        }
        for(int i=0; i<word.length(); i++){
            System.out.print("^");
        }
        System.out.println();
    }

    /**
     * 打印错误
     */
    public void errorThrowOut(String errorstr){
        int point = lineStartPosition;
        int linePosition = position - lineStartPosition;
        System.out.println("发生错误！在第 " + line + " 行");
        while(Scanner.getStr().charAt(point) != '\n') {
            System.out.print(Scanner.getStr().charAt(point++));
        }
        System.out.println();
        while(linePosition--!=0){
            System.out.print(" ");
        }
        for(int i=0; i<word.length(); i++){
            System.out.print("^");
        }
        System.out.println();
        switch(Utils.wordsList.get(errorstr)){
            case 35,36,37,38,39,40:
                System.out.println("\"" + errorstr + "\"" + "应为关系运算符。");
                break;
                /*
            case 27,29,31,34:
                System.out.println("缺少\"" + errorstr +"\"");
                break;

                 */
            default:
                //System.out.println("\"" + word + "\"应为\"" + errorstr +"\"");
                System.out.println("\"" + word + "\"前面缺少\"" + errorstr +"\"");
        }
        Parser.p.minus();
    }

    private void print(){
        System.out.println("(" + typeNumber + "," + word + ')');
    }

    public boolean isVariable(){
        return typeNumber == 10;
    }

    public boolean isFigure(){
        return typeNumber == 20;
    }

    @Override
    public String toString() {
        return "(" + typeNumber + "," + word + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return typeNumber == word.typeNumber;
    }

    /**
     * 判断两者是否相等
     * @param str
     * @return
     */
    public boolean equals(String str) {
        if(this.typeNumber == Utils.wordsList.get(str)){
            //System.out.println("equal true");
            return true;
        }
        //errorThrowOut(str);
        return false;
    }

    /**
     * 判断是否相等，如果不相等，则打印出错误
     * @param str
     * @return
     */
    public boolean check(String str) {
        if(this.typeNumber == Utils.wordsList.get(str)){
            //System.out.println("equal true");
            return true;
        }
        errorThrowOut(str);
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeNumber);
    }

    public void setLine(int line, int lineStartPosition) {
        this.line = line;
        this.lineStartPosition = lineStartPosition;
    }

    public void setPosition(int position) {
        this.position = position;
        //printPosition();
    }

    public void setLineStartPosition(int lineStartPosition) {
        this.lineStartPosition = lineStartPosition;
    }



    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return typeNumber == word1.typeNumber && word.equals(word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeNumber, word);
    }

 */
}
