import java.util.Objects;

public class Word {
    private int typeNumber;     //我查不到种别码的英文是什么
    private String word;        //symbol of a word
    private int line;           //To storage the line that word in

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
        print();
    }
    public void set(int typeNumber, String word){
        this.typeNumber = typeNumber;
        this.word = word;
        this.line = 0;
        print();
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

    public boolean equals(String str) {
        //TODO ERROR NOTIFICATION
        return this.typeNumber == Utils.wordsList.get(str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeNumber);
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
