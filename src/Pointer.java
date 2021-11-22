public class Pointer {
    private int value;
    private int bound;
    Pointer(int value, int bound){
        this.value = value;
        this.bound = bound-1;
    }
    int get(){
        return value;
    }
    int getpp() {
        if (value < bound) {
            value++;
            return value - 1;
        }
        else
            return value;
    }
    int minus(){
        return --value;
    }
}
