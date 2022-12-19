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
            System.out.println(tamano);

        }
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(columns));
        System.out.println(Arrays.toString(rows));
    }

    public int getElement(int i, int j) {
        int jb = rows[i];
        int pos = columns[jb];

        while (pos != j) {

            jb++;
            if (jb > columns.length) {
                pos = 0;
                break;
            }
        }
        return values[pos];
    }

    public int[] getRow(int i) {
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
