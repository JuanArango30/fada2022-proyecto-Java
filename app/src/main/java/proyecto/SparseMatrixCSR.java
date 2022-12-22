package proyecto;

import lombok.Getter;
import lombok.Setter;
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

        // Contar elementos diferentes de cero

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    tamano++;
                }
            }
        }

        // Fijar los tamaños de los arrays

        columns = new int[tamano];
        values = new int[tamano];
        rows = new int[matrix.length + 1];
        tamano = 0;

        // Fijar los valores de los arrays, se recorre por filas

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                if (matrix[i][j] != 0) {
                    values[tamano] = matrix[i][j];
                    columns[tamano] = j;
                    tamano++;
                }
            }
            rows[i + 1] = tamano;   // Almacenar los inicios de fila
        }
        System.out.println(Arrays.toString(values));
        System.out.println(Arrays.toString(columns));
        System.out.println(Arrays.toString(rows));
    }

    public int getElement(int i, int j) {
        int jb = rows[i];   // Indice de columna donde comienza la fila
        int pos = columns[jb];  // Valor de la columna en la fila i

        while (jb < rows[i + 1]) {  // Recorrer los elementos de toda la fila
            if (pos == j) {     // Retornar el valor si la columna coincide con j
                System.out.println("el pos es " + pos + "y el jb es: " + jb);
                return values[jb];
            } else {
                if (jb != columns.length - 1) {     // Seguir recorriendo la fila
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

        for (int j : columns) {     // Determinar el tamaño de la fila
            if (j > maxC)
                maxC = j;
        }

        int[] filaReturn = new int[maxC + 1];
        int jini = rows[i];

        // Buscar los valores de las columnas en fila i y almacenarlos

        for (int jini1 = jini; jini1 < rows[i + 1]; jini1++) {
            int var = columns[jini1];
            filaReturn[var] = values[jini1];
        }
        System.out.println(Arrays.toString(filaReturn));
        return filaReturn;
    }

    public int[] getColumn(int j) {
        int[] vecC = new int[rows.length - 1];

        // Recorrer la representación por filas/columnas

        for (int i = 0; i < rows.length - 1; i++) {
            for (int k = rows[i]; k < rows[i + 1]; k++) {
                if (columns[k] == j) {  // Si la columna coincide, almacenar el valor
                    vecC[i] = values[k];
                }
            }
        }
        System.out.println(Arrays.toString(vecC));
        return vecC;
    }

    public void setValue(int i, int j, int value) {

        //implementación desde vectores a otra matriz
        int[][] matrix2;
        int sizeCol = columns[0];

        for (int k = 1; k < columns.length; k++) { //tomamos el maximo valor del vector columnas
            if (columns[k] > sizeCol) {
                sizeCol = columns[k];
            }
        }
        sizeCol++;

        matrix2 = new int[rows.length - 1][sizeCol]; //Iniciamos la nueva matriz

        int contRows = 0;
        int contVal = 0;

        for (int k = 0; k < rows.length - 1; k++) { //pasamos por todas las filas
            for (int l = 0; l < columns.length; l++) { //pasamos por todas las columnas
                if (rows[contRows] == l) {
                    contRows++;
                    for (int m = 0; m < (rows[contRows] - rows[contRows - 1]); m++) {
                        matrix2[k][columns[contVal]] = values[contVal];
                        contVal++;
                    }

                    break;
                }
            }
        }

        //---------------------------Representacion nueva----------------------------

        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if (k == i && l == j) {
                    matrix2[k][l] = value;
                }
            }
        }

        int cont = 0;

        // Contamos cuantos valores hay en la matriz nueva

        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if (matrix2[k][l] != 0) {
                    cont++;
                }
            }
        }
        // Fijar los tamaños de los arrays
        columns = new int[cont];
        values = new int[cont];
        rows = new int[matrix2.length + 1];
        cont = 0;

        // Fijar los valores de los arrays, se recorre por filas

        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {

                if (matrix2[k][l] != 0) {
                    values[cont] = matrix2[k][l];
                    columns[cont] = l;
                    cont++;
                }
            }

            rows[k + 1] = cont; // Almacenar los inicios de fila

        }
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

        // Elevar al cuadrado los valores de la matriz y almacenarlos en un nuevo array

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
        int[][] nuevaMatriz = new int[matrix[0].length][matrix.length]; // Intercambiar filas/columnas por columnas/filas

        // Crear matriz transpuesta

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                nuevaMatriz[j][i] = matrix[i][j];
            }
        }
        squaredMatrix.setMatrix(nuevaMatriz);

        // Volvemos a crear las representaciones (el mismo codigo de la creacion de representacion)

        int tamano = 0;

        // Contar elementos diferentes de cero

        for (int i = 0; i < nuevaMatriz.length;
             i++) {
            for (int j = 0; j < nuevaMatriz[0].length; j++) {
                if (nuevaMatriz[i][j] != 0) {
                    tamano++;
                }
            }
        }

        // Fijar los tamaños de los arrays

        int[] nuevasColumnas = new int[tamano];
        int[] nuevosValores = new int[tamano];
        int[] nuevasfilas = new int[nuevaMatriz.length + 1];
        tamano = 0;

        // Fijar los valores de los arrays, se recorre por filas

        for (int i = 0; i < nuevaMatriz.length; i++) {
            for (int j = 0; j < nuevaMatriz[0].length; j++) {

                if (nuevaMatriz[i][j] != 0) {
                    nuevosValores[tamano] = nuevaMatriz[i][j];
                    nuevasColumnas[tamano] = j;
                    tamano++;
                }
            }
            nuevasfilas[i + 1] = tamano;    // Almacenar los inicios de fila
        }
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasfilas);
        squaredMatrix.setColumns(nuevasColumnas);
        return squaredMatrix;
    }

}