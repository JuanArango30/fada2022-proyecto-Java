package proyecto;

import lombok.Getter;
import lombok.Setter;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class SparseMatrixCoordinateFormat {

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

        int nfilas = 0;
        int ncolumnas = 0;
        int nelemento = 0; // Para iniciar lo arrays al valor necesario
        int nvalor = 0;

        // Contar elementos diferentes de cero
        for (int[] filas: matrix) {
            for (int elemento : filas) {
                if (elemento != 0) {
                    nelemento++;
                }
            }
        }

        // Fijar los tamaños de los arrays

        rows = new int[nelemento];
        columns = new int[nelemento];
        values = new int[nelemento];

        // Fijar los valores de los arrays, se recorre por columnas

        for (int i = 0; i < matrix.length; i++) {   //Filas
            for (int j = 0; j < matrix[0].length; j++) {    //Columnas
                if (matrix[i][j] != 0) {
                    rows[nfilas] = i;
                    columns[ncolumnas] = j;
                    values[nvalor] = matrix[i][j];
                    nfilas++;
                    ncolumnas++;
                    nvalor++;
                }
            }
        }
    }


    public int buscarElemento(int[] filas, int[] columnas, int[] valores, int i, int j) {
        int indexFila = 0;
        int finalFila = filas.length;
        int finalColumna = columnas.length;
        int finalValores = valores.length;
        boolean busquedaExitosa = false;

        for (int k = 0; k < filas.length; k++) {
            if (filas[k] == i) {
                indexFila = k;
                busquedaExitosa = true;
                break;
            }
        }
        if (busquedaExitosa) {
            if (columnas[indexFila] == j) {
                return valores[indexFila];
            } else {
                if (indexFila != finalFila && (indexFila != finalFila - 1)) {
                    int[] nfilas = Arrays.copyOfRange(filas, indexFila + 1, finalFila);
                    int[] ncolumnas = Arrays.copyOfRange(columnas, indexFila + 1, finalColumna);
                    int[] nvalores = Arrays.copyOfRange(valores, indexFila + 1, finalValores);
                    return buscarElemento(nfilas, ncolumnas, nvalores, i, j);
                }
            }
        }
        return 0;
    }

    public int getElement(int i, int j) {
        //No usar this.matrix aqui.
        System.out.println("elemento a buscar es fila: " + i);
        System.out.println("elemento a buscar es columna: " + j);

        int elementoF = buscarElemento(rows, columns, values, i, j);
        return elementoF;
    }

    public int[] getRow(int i){
        int cColumnas = 0;
        int maximoColumnas = columns[0];

        for (int numeroColumna : columns) {
            if (numeroColumna > maximoColumnas) {
                maximoColumnas = numeroColumna;
            }
        }
        int[] listaDevolver = new int[maximoColumnas + 1];

        for (int j = 0; j < rows.length; j++) {
            if (rows[j] == i) {
                cColumnas = columns[j];
                listaDevolver[cColumnas] = values[j];
            }
        }
        System.out.println(Arrays.toString(listaDevolver));
        return listaDevolver;
    }

    public int[] getColumn(int j){
        int cFilas = 0;
        int maximoFilas = rows[0];

        for (int numeroFila : rows) {
            if (numeroFila > maximoFilas) {
                maximoFilas = numeroFila;
            }
        }
        int[] listaDevolver = new int[maximoFilas + 1];

        for (int k = 0; k < columns.length; k++) {
            if (columns[k] == j) {
                cFilas = rows[k];
                listaDevolver[cFilas] = values[k];
            }
        }
        System.out.println(Arrays.toString(listaDevolver));
        return listaDevolver;
    }

    public void setValue(int i, int j, int value){
        for (int k = 0; k < matrix.length; k++) {
            for (int l = 0; l < matrix[0].length; l++) {
                if (k == i && l == j) {
                    matrix[k][l] = value;
                }
            }
        }

        int nfilas = 0;
        int ncolumnas = 0;
        int nelemento = 0; //para iniciar lo arrays al valor necesario
        int nvalor = 0;

        for (int[] filas: matrix) {
            for (int elemento : filas) {
                if (elemento != 0) {
                    nelemento++;
                }
            }
        }
        //  nelemento --;

        rows = new int[nelemento];
        columns = new int[nelemento];
        values = new int[nelemento];

        for (int k = 0; k < matrix.length; k++) {
            for (int l = 0; l < matrix[0].length; l++) {
                if (matrix[k][l] != 0) {
                    rows[nfilas] = k;
                    columns[ncolumnas] = l;
                    values[nvalor] = matrix[k][l];

                    nfilas++;
                    ncolumnas++;
                    nvalor++;
                }
            }
        }
    }

    public SparseMatrixCoordinateFormat getSquareMatrix(){
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
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
    public SparseMatrixCoordinateFormat getTransposedMatrix(){
        SparseMatrixCoordinateFormat squaredMatrix = new SparseMatrixCoordinateFormat();
        int[][] nuevaMatriz = new int[matrix[0].length][matrix.length];

        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                nuevaMatriz[j][i] = matrix[i][j];
            }
        }
        squaredMatrix.setMatrix(nuevaMatriz);

        ///SE CALCULA OTRA VEZ :)
        int nfilas = 0;
        int ncolumnas = 0;
        int nelemento = 0; //para iniciar lo arrays al valor necesario
        int nvalor = 0;

        //le damos el tamaño a cada uno de los vectores
        for (int[] filas: nuevaMatriz) {
            for (int elemento : filas) {
                if (elemento != 0) {
                    nelemento++;
                }
            }
        }
        //  nelemento --;
        int[] nuevasfilas = new int[nelemento];
        int[]  nuevasColumnas = new int[nelemento];
        int[] nuevosValores = new int[nelemento];

        for (int i = 0; i < nuevaMatriz.length; i++) {
            for (int j = 0; j < nuevaMatriz[0].length; j++) {
                if (nuevaMatriz[i][j] != 0) {
                    nuevasfilas[nfilas] = i;
                    nuevasColumnas[ncolumnas] = j;
                    nuevosValores[nvalor] = nuevaMatriz[i][j];

                    nfilas++;
                    ncolumnas++;
                    nvalor++;
                }
            }
        }
        squaredMatrix.setValues(nuevosValores);
        squaredMatrix.setRows(nuevasfilas);
        squaredMatrix.setColumns(nuevasColumnas);
        return squaredMatrix;
    }
}
