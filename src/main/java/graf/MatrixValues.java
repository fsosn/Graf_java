package graf;

import java.util.Random;

public class MatrixValues {

    private double RandomValue(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }

    void RandomizeMatrixValues(double min, double max, double[] value, int[] row, int[] column) {
        double newValue;
        int currentRow;
        int currentColumn;
        int arrayLenght = value.length;

        for (int i = 0; i < arrayLenght; i++)
        {
            currentRow = row[i];
            currentColumn = column[i];

            newValue = RandomValue(min, max);
            value[i] = newValue;

            for (int j = 0; j < arrayLenght; j++)
            {
                if (row[j] == currentColumn && column[j] == currentRow)
                {
                    value[j] = newValue;
                }
            }
        }
    }
}
