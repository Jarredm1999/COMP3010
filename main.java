import java.io.*;


 class main {
   public static File text = new File("C:\\Users\\jarre\\Desktop\\test.txt");
   public static FileInputStream keyboard;
   public static int charClass;
   public static char lexeme[] = new char[100];
   public static char nextChar;
   public static int lexLen;
   public static int token;
   public static int nextToken;
   public static final int LETTER = 0;
   public static final int DIGIT = 1;
   public static final int UNKNOWN = 99;
   public static final int INT_LIT = 10;
   public static final int IDENT = 11;
   public static final int ASSIGN_OP = 20;
   public static final int ADD_OP = 21;
   public static final int SUB_OP = 22;
   public static final int MULT_OP = 23;
   public static final int DIV_OP = 24;
   public static final int LEFT_PAREN = 25;
   public static final int RIGHT_PAREN = 26;
   public static final int EOF = -1;

    public static void main(String[] args) throws Exception{
            keyboard = new FileInputStream(text);
            getChar();
            if (keyboard.read() != -1) {
                do {
                    lex();
                } while (keyboard.available() > 0);
            }
    }

    public static int lookup(char ch) {
        switch (ch) {
            case '(' :
                addChar();
                nextToken = LEFT_PAREN;
                break;

            case ')' :
                addChar();
                nextToken = RIGHT_PAREN;
                break;

            case '+' :
                addChar();
                nextToken = ADD_OP;
                break;

            case '-' :
                addChar();
                nextToken = SUB_OP;
                break;

            case '*' :
                addChar();
                nextToken = MULT_OP;
                break;

            case '/' :
                addChar();
                nextToken = DIV_OP;
                break;

            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    public static void addChar() {
        if (lexLen <= 98) {
            lexeme [lexLen++] = nextChar;
            lexeme [lexLen] = 0;
        } else {
            System.out.println("Error - lexeme is too long \n");
        }
    }

    public static void getChar() throws IOException{
        if ((nextChar = (char) keyboard.read()) != EOF) {
            if (Character.isLetter(nextChar))
                charClass = LETTER;
            else if (Character.isDigit(nextChar))
                charClass = DIGIT;
            else charClass = UNKNOWN;
        }
        else
            charClass = EOF;
    }

    public static void getNonBlank() throws IOException{
        while (Character.isSpace(nextChar)) {
            getChar();
        }
    }

    public static int lex() throws IOException{
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;

            case DIGIT:
                addChar();
                getChar();
                while (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            case EOF:
                nextToken = EOF;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
        }
        System.out.printf("Next token is: %d, Next lexeme is %s\n", nextToken, new String(lexeme));
        return nextToken;
    }

}

