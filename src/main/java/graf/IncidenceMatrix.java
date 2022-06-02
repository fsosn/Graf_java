package graf;

public class IncidenceMatrix {

    int CountElements(int x, int y) {
        int n = x * y;
        int i;
        int j = 0;

        int NonZeroElements = 0;
        int k = 1;
        int q = 1;

        for (i = 0; i < n; i++) {
            if (i == 0) //pierwszy węzeł
            {
                if (j + 1 < n)
                    NonZeroElements++;

                if (j + x < n)
                    NonZeroElements++;
            } else if (i == n - 1) //ostatni węzeł
            {
                if (j - 1 < n)
                    NonZeroElements++;

                if (j - x >= 0)
                    NonZeroElements++;
            } else if (i == k * x) //pierwsza kolumna (od drugiego do przedostatniego wiersza)
            {
                if (y == 2) {
                    if (j - x >= 0)
                        NonZeroElements++;

                    if (j + 1 < n)
                        NonZeroElements++;
                } else {
                    if (j - x >= 0)
                        NonZeroElements++;

                    if (j + 1 < n)
                        NonZeroElements++;

                    if (j + x < n)
                        NonZeroElements++;

                    k++;
                }
            } else if (i == x - 1) //prawy górny węzeł
            {
                if (j - 1 >= 0)
                    NonZeroElements++;

                if (j + x < n)
                    NonZeroElements++;
            } else if (i == x - 1 + q * x && i != n - 1) //ostatnia kolumna (od drugiego do przedostatniego wiersza)
            {
                if (j - x >= 0)
                    NonZeroElements++;

                if (j - 1 >= 0)
                    NonZeroElements++;

                if (j + x < n)
                    NonZeroElements++;

                q++;
            } else //pozostałe węzły
            {
                if (j - x >= 0)
                    NonZeroElements++;

                if (j - 1 < n)
                    NonZeroElements++;

                if (j + 1 < n)
                    NonZeroElements++;

                if (j + x < n)
                    NonZeroElements++;
            }
            j++;
        }
        return NonZeroElements;
    }

    void FillArrays(int x, int y, double[] value, int[] row, int[] column) {
        int n = x * y;
        int k = 1;
        int q = 1;

        int j = 0;
        int index_of_arrays = 0;

        for (int i = 0; i < n; i++) {
            if (i == 0) //pierwszy węzeł
            {
                if (j + 1 < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + 1;
                    index_of_arrays++;
                }

                if (j + x < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + x;
                    index_of_arrays++;
                }
            } else if (i == n - 1) //ostatni węzeł
            {
                if (j - 1 < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - 1;
                    index_of_arrays++;
                }
                if (j - x >= 0) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - x;
                    index_of_arrays++;
                }
            } else if (i == k * x) //pierwsza kolumna (od drugiego do przedostatniego wiersza)
            {
                if (y == 2) {
                    if (j - x >= 0) {
                        value[index_of_arrays] = 1;
                        row[index_of_arrays] = i;
                        column[index_of_arrays] = j - x;
                        index_of_arrays++;
                    }
                    if (j + 1 < n) {
                        value[index_of_arrays] = 1;
                        row[index_of_arrays] = i;
                        column[index_of_arrays] = j + 1;
                        index_of_arrays++;
                    }
                } else {
                    if (j - x >= 0) {
                        value[index_of_arrays] = 1;
                        row[index_of_arrays] = i;
                        column[index_of_arrays] = j - x;
                        index_of_arrays++;
                    }
                    if (j + 1 < n) {
                        value[index_of_arrays] = 1;
                        row[index_of_arrays] = i;
                        column[index_of_arrays] = j + 1;
                        index_of_arrays++;
                    }
                    if (j + x < n) {
                        value[index_of_arrays] = 1;
                        row[index_of_arrays] = i;
                        column[index_of_arrays] = j + x;
                        index_of_arrays++;
                    }
                    k++;
                }
            } else if (i == x - 1) //prawy górny węzeł
            {
                if (j - 1 >= 0) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - 1;
                    index_of_arrays++;
                }
                if (j + x < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + x;
                    index_of_arrays++;
                }
            } else if (i == x - 1 + q * x && i != n - 1) //ostatnia kolumna (od drugiego do przedostatniego wiersza)
            {
                if (j - x >= 0) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - x;
                    index_of_arrays++;
                }
                if (j - 1 >= 0) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - 1;
                    index_of_arrays++;
                }
                if (j + x < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + x;
                    index_of_arrays++;
                }
                q++;
            } else //pozostałe węzły
            {
                if (j - x >= 0) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - x;
                    index_of_arrays++;
                }
                if (j - 1 < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j - 1;
                    index_of_arrays++;
                }
                if (j + 1 < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + 1;
                    index_of_arrays++;
                }
                if (j + x < n) {
                    value[index_of_arrays] = 1;
                    row[index_of_arrays] = i;
                    column[index_of_arrays] = j + x;
                    index_of_arrays++;
                }
            }
            j++;
        }
    }
}
