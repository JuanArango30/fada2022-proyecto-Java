package proyecto;

import lombok.Getter;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class SparseMatrixCSR {
    private LoadFile loader = LoadFile.getInstance();
    private int[][] matrix;
    @Getter
    private int[] rows;
    @Getter
    private int[] columns;
    @Getter
    private int[] values;

    public void createRepresentation(String inputFile) throws FileNotFoundException {
        //Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        int tamano = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    tamano++;
                }
            }
        }

        columns = new int[tamano];
        values = new int[tamano];
        rows = new int[matrix.length + 1];
        tamano = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                if (matrix[i][j] != 0) {
                    values[tamano] = matrix[i][j];
                    columns[tamano] = j;
                    tamano++;
                }
            }

            rows[i + 1] = tamano;

        }
    }

    public int getElement(int i, int j) {

        int jb = rows[i];
        int pos = columns[jb];


        while (jb <rows[i+1]) {

            if( pos ==j) {

                System.out.println("el pos es " + pos + "y el jb es: " + jb);
                return values[jb];

            } else {

                if(jb != columns.length-1) {

                    jb++;
                    pos = columns[jb];

                } else {

                    return 0;
                }
            }

        }

        return 0;


    }

    public int[] getRow(int i) {
        int maxC = columns[0];

        for (int j : columns) {
            if (j > maxC)
                maxC = j;
        }
        int[] filaReturn = new int[maxC + 1];

        int jini = rows[i];


        for (int jini1 = jini; jini1 < rows[i + 1]; jini1++) {
            int var = columns[jini1];
            filaReturn[var] = values[jini1];
        }
        System.out.println(Arrays.toString(filaReturn));
        return filaReturn;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSR getSquareMatrix() throws OperationNotSupportedException {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSR getTransposedMatrix() throws OperationNotSupportedException {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();
        throw new OperationNotSupportedException();
    }

}
