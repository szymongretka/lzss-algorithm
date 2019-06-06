public class Output {

    boolean match;
    int offset;
    int length;
    char symbol;

    public Output(boolean match, int offset, int length){
        this.match = match;
        this.offset = offset;
        this.length = length;
    }

    public Output(boolean match, char symbol){
        this.match = match;
        this.symbol = symbol;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
