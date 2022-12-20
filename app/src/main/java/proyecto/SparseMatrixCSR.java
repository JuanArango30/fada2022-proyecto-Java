package proyecto;

import lombok.Getter;
import lombok.Setter;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class SparseMatrixCSR {
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
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(columns));
        System.out.println(Arrays.toString(rows));
    }

    public int getElement(int i, int j) {

        int jb = rows[i];
        int pos = columns[jb];


        while (jb < rows[i + 1]) {

            if (pos == j) {

                System.out.println("el pos es " + pos + "y el jb es: " + jb);
                return values[jb];

            } else {

                if (jb != columns.length - 1) {

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

    public int[] getColumn(int j) {
        int[] vecC = new int[rows.length - 1];

        for (int i = 0; i < rows.length - 1; i++) {

            for (int k = rows[i]; k < rows[i + 1]; k++) {

                if (columns[k] == j) {
                    vecC[i] = values[k];
                }
            }
        }
        System.out.println(Arrays.toString(vecC));
        return vecC;
    }

    public void setValue(int i, int j, int value) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSR getSquareMatrix() {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();

        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());

        int[] newValores = new int[getValues().length];

        for (int i = 0; i < getValues().length; i++) {

            newValores[i] = getValues()[i] * getValues()[i];
        }
        squaredMatrix.setValues(newValores);


        return squaredMatrix;
    }

    /*
     * This method returns a representation of the transposed matrix
     * @return object that contests the transposed matrix;
     */
    public SparseMatrixCSR getTransposedMatrix() {
        SparseMatrixCSR squaredMatrix = new SparseMatrixCSR();


        int[][] nuevaMatriz = new int[matrix[0].length][matrix.length];


        for (int j = 0; j < matrix[0].length; j++) {

            for (int i = 0; i < matrix.length; i++) {

                nuevaMatriz[j][i] = matrix[i][j];

            }
        }

        squaredMatrix.setMatrix(nuevaMatriz); //como ya se calculo la nueva matriz transpuesta

        //volvemos a crear las representaciones (el mismo codigo de la creacion de representacion)

        int tamano = 0;

        for (int i = 0; i < nuevaMatriz.length; i++) {
            for (int j = 0; j < nuevaMatriz[0].length; j++) {
                if (nuevaMatriz[i][j] != 0) {
                    tamano++;
                }
            }
        }

        int[] nuevasColumnas = new int[tamano];
        int[] nuevosValores = new int[tamano];
        int[] nuevasfilas = new int[nuevaMatriz.length + 1];
        tamano = 0;

        for (int i = 0; i < nuevaMatriz.length; i++) {
            for (int j = 0; j < nuevaMatriz[0].length; j++) {

                if (nuevaMatriz[i][j] != 0) {
                    nuevosValores[tamano] = nuevaMatriz[i][j];
                    nuevasColumnas[tamano] = j;
                    tamano++;
                }
            }

            nuevasfilas[i + 1] = tamano;

        }


        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasfilas);
        squaredMatrix.setColumns(nuevasColumnas);


        return squaredMatrix;
    }

}