package graf;

public class Generate {

    public void generateGraph(int x, int y, double min, double max, int n, String output)
    {
        // zliczenie ile niezerowych elementow wystapi w macierzy incydencji
        IncidenceMatrix count = new IncidenceMatrix();
        int NonZeroElements = count.CountElements(x, y);

        //utworzenie tablic z wartościami przejść, wierszy i kolumn
        double[] value = new double[NonZeroElements];
        int[] row = new int[NonZeroElements];
        int[] column = new int[NonZeroElements];

        //utworzenie macierzy incydencji
        IncidenceMatrix fill = new IncidenceMatrix();
        fill.FillArrays(x, y, value, row, column);

        //losowe wartości wag krawędzi z przedziału [min, max]
        MatrixValues random = new MatrixValues();
        random.RandomizeMatrixValues(min, max, value, row, column);

        ToFile tf = new ToFile();
        tf.writeToFile(output, x, y, value, row, column);
    }
}
