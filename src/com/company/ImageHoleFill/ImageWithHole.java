package com.company.ImageHoleFill;

import org.opencv.core.Mat;

/**
 * class to hold a picture with a hole inside
 */

public class ImageWithHole {

    private final Mat matrix;
    private final int connectivity;

    /**
     * @param grayScaleMat should be as described in the assignment, [0,1] scale with -1 represent a hole pixel.
     * @param connectivity 4 or 8, defines the pixel connectivity.
     */
    public ImageWithHole(Mat grayScaleMat, int connectivity) {

        this.matrix = grayScaleMat;
        this.connectivity = connectivity;
    }

    public int getConnectivity() {
        return connectivity;
    }

    public int rows() {
        return matrix.rows();
    }

    public int cols() {
        return matrix.cols();
    }

    public double[] get(int i, int j) {
        return matrix.get(i, j);
    }

    public void put(int row, int col, double data) {
        matrix.put(row, col, data);
    }
}
