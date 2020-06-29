package com.company.ImageHoleFill.FillingStrategies;

import com.company.ImageHoleFill.ImageWithHole;
import com.company.ImageHoleFill.Pixel;
import com.company.ImageHoleFill.Weighable;

import java.util.Set;

/**
 * The abstract strategy to handle hole filling.
 * provides an abstract skeleton and commonly used methods
 */

public abstract class HoleFiller {

    ImageWithHole image;

    public HoleFiller(ImageWithHole image) {
        this.image = image;
    }

    public abstract void fillHole(Weighable weightFunction);

    boolean isHole(int row, int col) {
        return image.get(row, col)[0] == -1;
    }

    double calculateFormula(Weighable weightFunction, Pixel hole, Set<Pixel> comparators) {

        double numerator = 0, denominator = 0;

        for (Pixel comparator : comparators)
        {
            numerator += weightFunction.weight(hole, comparator)
                    * image.get(comparator.getRow(), comparator.getCol())[0];

            denominator += weightFunction.weight(hole, comparator);
        }
        return numerator / denominator;
    }

    boolean isBoundary(int row, int col) {

        if (row <= 0 || row >= image.rows() - 1 || col <= 0 || col >= image.cols() - 1 || isHole(row, col))
            return false;

        boolean fourConnected = isHole(row, col + 1) ||
                isHole(row, col - 1) ||
                isHole(row - 1, col) ||
                isHole(row + 1, col);

        if (image.getConnectivity() == 8)
            return fourConnected ||
                    isHole(row - 1, col - 1) ||
                    isHole(row - 1, col + 1) ||
                    isHole(row + 1, col - 1) ||
                    isHole(row + 1, col + 1);

        return fourConnected;
    }

}
