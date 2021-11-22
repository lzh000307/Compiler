public class Four {
    private String op;
    private String argv1;
    private String argv2;
    private String result;

    public Four(String op, String argv1, String argv2, String result) {
        this.op = op;
        this.argv1 = argv1;
        this.argv2 = argv2;
        this.result = result;
    }

    public String getOp() {
        return op;
    }

    public String getArgv1() {
        return argv1;
    }

    public String getArgv2() {
        return argv2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString(){
        return "(" + op + "," + argv1 + "," + argv2 + "," + result + ")";
    }
}