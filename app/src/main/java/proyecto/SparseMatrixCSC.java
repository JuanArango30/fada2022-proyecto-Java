package proyecto;

import javax.naming.OperationNotSupportedException;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;

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

        // Contar elementos diferentes de cero

        int size = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    size++;
                }
            }
        }

        // Fijar los tamaños de los arrays

        values = new int[size];
        rows = new int[size];
        columns = new int[matrix[0].length + 1];
        size = 0;

        // Fijar los valores de los arrays, se recorre por columnas

        for (int j = 0; j < matrix[0].length; j++) {    //Columnas
            for (int i = 0; i < matrix.length; i++) {    //Filas
                if (matrix[i][j] != 0) {
                    values[size] = matrix[i][j];
                    rows[size] = i;
                    size++;
                }
            }
            columns[j + 1] = size;  // Almacenar los inicios de columna
        }
    }

    public int getElement(int i, int j) {
        if (columns[j] != columns[j + 1]) { // Se verifica si la columna existe

            // Se recorren las posiciones del array rows que corresponden a la columna dada

            for (int row = columns[j]; row < columns[j + 1]; row++) {

                // Si el valor de rows[row] es igual al índice i de la fila a buscar, se retorna el valor

                if (rows[row] == i) {
                    return values[row];
                }
            }
        }

        // Si el valor no se encuentra, quiere decir que corresponde a un cero

        return 0;
    }

    public int[] getRow(int i) {

        // Tamaño de fila = número de columnas

        int[] row = new int[columns.length - 1];    // Array para almacenar la fila de salida

        boolean rowExist = false;

        // Se verifica si en el array rows[] se guardó el índice de fila proporcionado

        for (int r: rows) {
            if (r == i) {
                rowExist = true;
                break;
            }
        }
        if (rowExist) {
            for (int j = 0; j < row.length; j++) {  // Ciclo para iterar sobre el array de salida 'row' e indicar columnas
                if (columns[j] != columns[j + 1]) { // Verificar si la columna existe

                    // Llenar row con el valor correspondiente a la columna 'j' si el índice en 'rows' coincide con el 'i' dado

                    for (int r = columns[j]; r < columns[j + 1]; r++) {
                        if (rows[r] == i) {
                            row[j] = values[r];
                        }
                    }
                }
            }
        }
        return row;
    }

    public int[] getColumn(int j) {
        int[] column = new int[matrix.length];  // Array para almacenar la columna de salida

        // Row es la posición del arreglo rows desde donde empieza la columna

        if (columns[j] != columns[j + 1]) { // Verificar si la columna existe

            // Recorrer los índices de 'rows' donde esté la columna j

            for (int row = columns[j]; row < columns[j + 1]; row++) {
                column[rows[row]] = values[row];
            }
        }
        return column;
    }

    public void setValue(int i, int j, int value) {

        //Implementación desde vectores a otra matriz
        int[][] matrix2; //Creamos una nueva matriz

        int sizeRow = columns[0];

        for (int k = 0; k < rows.length; k++) { //Buscamos el numero mayor del vector filas
            if (rows[k] > sizeRow) {
                sizeRow = rows[k];
            }
        }

        sizeRow++;

        matrix2 = new int[sizeRow][columns.length - 1]; //Le damos el tamaño a la nueva matriz

        int contColum = 0;
        int contVal = 0;

        //Llenamos la matriz segun los vectores de la representacion
        for (int k = 0; k < columns.length - 1; k++) {
            for (int l = 0; l < rows.length; l++) {
                if (columns[contColum] == l) {
                    contColum++;
                    for (int m = 0; m < (columns[contColum] - columns[contColum - 1]); m++) {
                        matrix2[rows[contVal]][k] = values[contVal];
                        contVal++;
                    }

                    break;
                }
            }
        }

        //---------------------Ingresamos el nuevo valor------------------
        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if (k == i && l == j) {
                    matrix2[k][l] = value;
                }
            }
        }

        int size = 0;

        // Contamos cuantos valores hay en la matriz nueva

        for (int k = 0; k < matrix2.length; k++) {
            for (int l = 0; l < matrix2[0].length; l++) {
                if (matrix2[k][l] != 0) {
                    size++;
                }
            }
        }

        // Ingresamos el tamaño de los arrays
        values = new int[size];
        rows = new int[size];
        columns = new int[matrix[0].length + 1];
        size = 0;

        // Ingresamos los nuevos valores de los vectores
        for (int l = 0; l < matrix2[0].length; l++) {    //Columnas
            for (int k = 0; k < matrix2.length; k++) {    //Filas
                if (matrix2[k][l] != 0) {
                    values[size] = matrix2[k][l];
                    rows[size] = k;
                    size++;
                }
            }
            columns[l + 1] = size; // Almacenar los inicios de columna
        }
    }

    /*
     * This method returns a representation of the Squared matrix
     * @return object that contests the squared matrix;
     */
    public SparseMatrixCSC getSquareMatrix() {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        squaredMatrix.setRows(getRows());
        squaredMatrix.setColumns(getColumns());
        int[] newValues = new int[getValues().length];

        // Elevar al cuadrado los valores de la matriz y almacenarlos en un nuevo array

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
    public SparseMatrixCSC getTransposedMatrix() {
        SparseMatrixCSC squaredMatrix = new SparseMatrixCSC();
        int matrix2[][] = new int[matrix[0].length][matrix.length]; // Intercambiar filas/columnas por columnas/filas

        // Crear matriz transpuesta

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                matrix2[j][i] = matrix[i][j];
            }
        }
        squaredMatrix.setMatrix(matrix2);

        // Volvemos a crear las representaciones (el mismo codigo de la creacion de representacion)

        int tamano = 0;

        // Contar elementos diferentes de cero

        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                if (matrix2[i][j] != 0) {
                    tamano++;
                }
            }
        }

        // Fijar los tamaños de los arrays

        int[] nuevasFilas = new int[tamano];
        int[] nuevosValores = new int[tamano];
        int[] nuevasColumnas = new int[matrix2[0].length + 1];

        tamano = 0;

        // Fijar los valores de los arrays, se recorre por columnas

        for (int j = 0; j < matrix2[0].length; j++) {
            for (int i = 0; i < matrix2.length; i++) {
                if (matrix2[i][j] != 0) {
                    nuevosValores[tamano] = matrix2[i][j];
                    nuevasFilas[tamano] = i;
                    tamano++;
                }
            }
            nuevasColumnas[j + 1] = tamano; // Almacenar los inicios de columna
        }
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasFilas);
        squaredMatrix.setColumns(nuevasColumnas);
        return squaredMatrix;
    }
}
