package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here

        checkInitialList(inputNumbers);

        Collections.sort(inputNumbers);

        return getMatrix(inputNumbers, getHeigth(inputNumbers));

    }

    private void checkInitialList(List<Integer> inputNumbers) {
        if ((inputNumbers.size() > 50000) || inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }
    }

    private int getHeigth(List<Integer> inputNumbers) {

        int matrixHeight = 0;
        int matrixWidth = inputNumbers.size();
        while (matrixWidth > 0) {
            matrixWidth = matrixWidth - (matrixHeight + 1);
            if (matrixWidth < 0) {
                throw new CannotBuildPyramidException();
            } else {
                matrixHeight++;
            }
        }
        return matrixHeight;
    }

    private int[][] getMatrix(List<Integer> inputNumbers, int height) {

        int width = height * 2 - 1;
        int[][] piramidMatr = new int[height][width];
        int link = 0;
        for (int i = 0; i < height; i++) {
            int l = height - i - 1;
            for (int j = 0; j <= i; j++) {
                piramidMatr[i][l] = inputNumbers.get(link);
                link++;
                l += 2;
            }
        }

        return piramidMatr;
    }

}
