package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class SparseMatrixCSC {
    private LoadFile loader = LoadFile.getInstance();
    private int[][] matrix;
    @Getter
    private int[] rows;
    @Getter
    private int[] columns;
    @Getter
    private int[] values;

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        // Count elements different from zero
        int size = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    size++;
                }
            }
        }

        // Set array's sizes
        values = new int[size];
        rows = new int[size];
        columns = new int[matrix[0].length + 1];
        size = 0;

        // Set array's values
        for (int j = 0; j < matrix[0].length; j++) {    //Columns
            for (int i = 0; i < matrix.length; i++){    //Rows
                if (matrix[i][j] != 0){
                    values[size] = matrix[i][j];
                    rows[size] = i;
                    size++;
                }
            }
            columns[j+1] = size;
        }
    }

    public int getElement(int i, int j) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    public int[] getRow(int i) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    public int[] getColumn(int j) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException
    {
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSC getTransposedMatrix() throws OperationNotSupportedException
    {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        throw new OperationNotSupportedException();
    }
}
