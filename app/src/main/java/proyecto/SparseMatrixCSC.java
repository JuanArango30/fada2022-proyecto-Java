package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class SparseMatrixCSC {
    private LoadFile loader = LoadFile.getInstance();
    @Setter
    private int[][] matrix;
    @Getter
    @Setter
    private int[] rows;
    @Getter
    @Setter
    private int[] columns;
    @Getter
    @Setter
    private int[] values;

    public void createRepresentation(String inputFile) throws OperationNotSupportedException, FileNotFoundException {
        // Load data
        loader.loadFile(inputFile);
        matrix = loader.getMatrix();

        // Count elements different to zero
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

    public int getElement(int i, int j) throws OperationNotSupportedException{
        if (columns[j] != columns[j+1]) {
            for (int row = columns[j]; row < columns[j+1]; row++){
                if (rows[row] == i) {
                    return values[row];
                }
            }
        }
        return 0;
    }

    public int[] getRow(int i) throws OperationNotSupportedException{
        int[] row = new int[columns.length-1];
        boolean rowExist = false;

        for (int r: rows){
            if (r == i){
                rowExist = true;
                break;
            }
        }
        if (rowExist){
            for (int j = 0; j < row.length; j++) {
                if (columns[j] != columns[j+1]) {
                    for (int r = columns[j]; r < columns[j+1]; r++){
                        if (rows[r] == i) {
                            row[j] = values[r];
                        }
                    }
                }
            }
        }
        return row;
    }

    public int[] getColumn(int j) throws OperationNotSupportedException{
        int[] column = new int[matrix.length];

        // row es la posición del arreglo rows desde donde empieza la columna
        if (columns[j] != columns[j+1]) {
            for (int row = columns[j]; row < columns[j+1]; row++){
                column[rows[row]] = values[row];
            }
        }
        return column;
    }

    public void setValue(int i, int j, int value) {

        //implementación desde vectores a otra matriz
        int[][] matrix2;

        int sizeRow = columns[0];

        for (int k = 0; k < rows.length; k++) {
            if (rows[k] > sizeRow){
                sizeRow = rows[k];
            }
        }

        sizeRow++;

        matrix2 = new int[sizeRow][columns.length-1];

        int contColum = 0;
        int contVal=0;

        for (int k = 0; k < columns.length-1; k++) {
            for (int l = 0; l < rows.length; l++) {
                if (columns[contColum] == l) {
                    contColum++;
                    for (int m = 0; m < (columns[contColum] - columns[contColum-1]); m++) {
                        matrix2[rows[contVal]][k] = values[contVal];
                        contVal++;
                    }

                    break;
                }
            }
        }

        //---------------------------------------
        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if(k == i && l == j){
                    matrix2[k][l] = value;
                }
            }
        }

        int size = 0;

        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if (matrix2[k][l] != 0) {
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
        for (int l = 0; l < matrix2[0].length; l++) {    //Columns
            for (int k = 0; k < matrix2.length; k++){    //Rows
                if (matrix2[k][l] != 0){
                    values[size] = matrix2[k][l];
                    rows[size] = k;
                    size++;
                }
            }
            columns[l+1] = size;
        }



    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() throws OperationNotSupportedException{
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());

        int[] newValues = new int[getValues().length];
        for (int i = 0; i < getValues().length; i++) {
            newValues[i] = getValues()[i] * getValues()[i];
        }
        squaredMatrix.setValues(newValues);
        return squaredMatrix;
    }
    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSC getTransposedMatrix() throws OperationNotSupportedException{
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();

        int matrix2[][] = new int[matrix[0].length][matrix.length];

        for (int j = 0; j < matrix[0].length; j++) {

            for (int i = 0; i < matrix.length; i++) {

                matrix2[j][i] = matrix[i][j];

            }
        }
        squaredMatrix.setMatrix(matrix2); //como ya se calculo la nueva matriz transpuesta
        //volvemos a crear las representaciones (el mismo codigo de la creacion de representacion)
        int tamano = 0;

        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                if (matrix2[i][j] != 0) {
                    tamano++;
                }
            }
        }
        int[] nuevasFilas = new int[tamano];
        int[] nuevosValores = new int[tamano];
        int[] nuevasColumnas = new int[matrix2[0].length + 1];
        tamano = 0;

        for (int j = 0; j < matrix2[0].length; j++) {    //Columns
            for (int i = 0; i < matrix2.length; i++){    //Rows
                if (matrix2[i][j] != 0) {
                    nuevosValores[tamano] = matrix2[i][j];
                    nuevasFilas[tamano] = i;
                    tamano++;
                }
            }
            nuevasColumnas[j+1] = tamano;
        }
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasFilas);
        squaredMatrix.setColumns(nuevasColumnas);
        return squaredMatrix;
    }
}
