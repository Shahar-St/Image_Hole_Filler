package com.company.ImageHoleFill.FillingStrategies;

import com.company.ImageHoleFill.ImageWithHole;
import com.company.ImageHoleFill.Pixel;
import com.company.ImageHoleFill.Weighable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * this strategy gives approximate results but runs in O(n) where n = # of hole pixels
 * it goes in a circular way around the image and fills hole pixels using only their immediate non-hole neighbours
 */

public class ApproxHoleFiller extends HoleFiller {


    public ApproxHoleFiller(ImageWithHole image) {
        super(image);
    }

    @Override
    public void fillHole(Weighable weightFunction) {

        int rows = image.rows(), cols = image.cols();
        int rowsOffset = 0, colsOffset = 0;
        int r = rowsOffset, c = colsOffset;

        // this loop defines the circular traversal around the image pixels
        while (rows > 1 && cols > 1)
        {
            while (c < cols)
            {
                paintHolePixel(r, c, weightFunction);
                c++;
            }
            c--;
            r++;

            while (r < rows)
            {
                paintHolePixel(r, c, weightFunction);
                r++;
            }
            r--;
            c--;

            while (c > colsOffset)
            {
                paintHolePixel(r, c, weightFunction);
                c--;
            }
            c++;
            r--;

            while (r > rowsOffset)
            {
                paintHolePixel(r, c, weightFunction);
                r--;
            }
            r++;
            c++;

            rows = rows - 1;
            cols = cols - 1;
            colsOffset++;
            rowsOffset++;
        }
    }

    private void paintHolePixel(int row, int col, Weighable weightFunction) {

        if (isHole(row, col))
        {
            Set<Pixel> neighbours = getNeighbours(row, col);
            image.put(row, col, calculateFormula(weightFunction, new Pixel(row, col), neighbours));
        }
    }

    private Set<Pixel> getNeighbours(int row, int col) {

        Set<Pixel> neighbours = new LinkedHashSet<>();

        addNeighbour(row, col + 1, neighbours);
        addNeighbour(row, col - 1, neighbours);
        addNeighbour(row + 1, col, neighbours);
        addNeighbour(row - 1, col, neighbours);

        if (image.getConnectivity() == 8)
        {
            addNeighbour(row - 1, col - 1, neighbours);
            addNeighbour(row + 1, col - 1, neighbours);
            addNeighbour(row - 1, col + 1, neighbours);
            addNeighbour(row + 1, col + 1, neighbours);
        }

        return neighbours;
    }

    private void addNeighbour(int row, int col, Set<Pixel> neighbours) {
        if (!isHole(row, col))
            neighbours.add(new Pixel(row, col));
    }


}
