import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class TransCipherBreaker {

    static Scanner scn = new Scanner(System.in);
    static int i = 0, j = 0,accuracy,diff;
    static int count = 0;
    static int row = 5, col = 28;
    public static int permutation[];
    public static int changedOrder[] = {3 , 1 ,5 ,2 ,4};
    public static char twoD[][];
    public static char transpose[][];
    public static char encrypted[][];
    public static String plaintext = new String();
    public static String cipherText = new String();
    public static String myCipherText = new String();

    public static void readFile() throws Exception {
        count = 0;
        FileReader fr
                = new FileReader("src/input.txt");

        int k;
        while ((k = fr.read()) != -1) {
            count++;
            //twoD[i][j] = (char) k;
            cipherText+= (char) k ;
            //j++;
            /*if (j == col) {
                j = 0;
                i++;
            }*/
        }
    }
    
    public static void init() {
        col = count / row;
        permutation = new int[row];
        
        twoD = new char[row][col];
        transpose = new char[col][row];
        encrypted = new char[col][row];
        loadInArray();
    }
    
    public static void loadInArray(){
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    twoD[i][j] = cipherText.charAt(i*col+j);
                }
        }
    }

    public static void transpose(char[][] matrix, int row, int col) {
        char[][] transposeMatrix = new char[col][row];
        for (int k = 0; k < col; k++) {
            for (int l = 0; l < row; l++) {
                transpose[k][l] = twoD[l][k];
            }
        }
    }

    public static void swapCols(char[][] matrix, int row, int swCol1, int swCol2) {
        char temp;
        swCol1--;
        swCol2--;
        for (int k = 0; k < row; k++) {
            temp = matrix[k][swCol1];
            matrix[k][swCol1] = matrix[k][swCol2];
            matrix[k][swCol2] = temp;
        }
    }

    public static void printMatrix(char[][] matrix, int row, int col) {
        for (int k = 0; k < row; k++) {
            for (int l = 0; l < col; l++) {
                System.out.print(matrix[k][l]);
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("\n\n");
    }

    public static void permuteMatrix(char[][] matrix, int row, int col) {
        char[][] temp = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp[i][j] = Character.toLowerCase(matrix[i][permutation[j] - 1]);
                plaintext += Character.toLowerCase(matrix[i][permutation[j] - 1]);
            }
        }
        transpose = temp;
    }
    
    public static void permuteMatrix1(char[][] matrix, int row, int col) {
        char[][] temp = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp[i][j] = matrix[i][changedOrder[j] - 1];
            }
        }
        encrypted = temp;
    }
    

    public static void decrypt() {

        int col1 = 0, col2 = 0;
        transpose(twoD, row, col);
        //printMatrix(twoD, 10, 14);
        printMatrix(transpose, col, row);
        System.out.println("Enter the permutation: ");
        for (int i = 0; i < row; i++) {
            permutation[i] = scn.nextInt();
        }
        System.out.println("\n\n");
        permuteMatrix(transpose, col, row);
        //printMatrix(transpose, col, row);

        /*while (true) {
            System.out.println("Enter Columns to swap(1-col) <Col1 Col2>");
            col1 = scn.nextInt();
            col2 = scn.nextInt();
            swapCols(transpose, col, col1, col2);
            printMatrix(transpose, col, row);
        }*/
    }

    public static void encrypt() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                encrypted[i][j] = Character.toUpperCase(plaintext.charAt(i * row + j));
            }
        }
        permuteMatrix1(encrypted, col, row);
        //printMatrix(encrypted, col, row);
        for (int k = 0; k < row; k++) {
            for (int l = 0; l < col; l++) {
                myCipherText+=encrypted[l][k];
            }
        }
        System.out.println("\nCiphertext: "+myCipherText);
        checkAccuracy();
    }
    public static void checkAccuracy(){
        for (int i = 0; i < count; i++) {
            if(cipherText.charAt(i)!=myCipherText.charAt(i))
                diff++;
        }
        accuracy=  (100*( count - diff )) / count;
        System.out.println("\nAccuracy: "+accuracy+"%");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Enter Key Length: ");
        row = scn.nextInt();
       // System.out.println("Enter num of chracters: ");
       // count = scn.nextInt();
        
        readFile();
        init();

        decrypt();
        //BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        PrintStream o = new PrintStream(new File("output.txt"));

        // Store current System.out before assigning a new value 
        PrintStream console = System.out;

        // Assign o to output stream 
        System.setOut(o);
        //System.out.println("This will be written to the text file");
        //FileOutputStream outputStream = new FileOutputStream("c:/temp/samplefile5.txt");
        //DataOutputStream writer = new DataOutputStream(new BufferedOutputStream(outputStream));
        System.out.println("Plaintext: " + plaintext);
        //writer.writeUTF("Plaintext: " +plaintext);
        System.out.println("\nKey Length: " + row);
        //writer.writeUTF("\nKey Length: " + row);
        System.out.print("\nOrder: ");
        for (int k = 0; k < row; k++) {
            System.out.print(changedOrder[k]+ " ");
        }
        System.out.println();
        //writer.writeUTF("\nOrder: ");
        /*for (int i = 0; i < row; i++) {
            System.out.print(permutation[i] + " ");
            //writer.writeUTF(permutation[i] + " ");
        }*/

        //System.out.println("\n\nEncryption:\n");
        encrypt();
    }
}
