package com.company.ImageHoleFill.FillingStrategies;

import com.company.ImageHoleFill.ImageWithHole;
import com.company.ImageHoleFill.Pixel;
import com.company.ImageHoleFill.Weighable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * this strategy gives the best results using all boundaries to determine each hole pixel value.
 * run time: O(n * m) where n = # of hole pixels, m = # of boundary pixels
 */

public class AccurateHoleFiller extends HoleFiller {

    private final Set<Pixel> holePixels = new LinkedHashSet<>();
    private final Set<Pixel> boundaryPixels = new LinkedHashSet<>();

    public AccurateHoleFiller(ImageWithHole image) {
        super(image);
    }

    @Override
    public void fillHole(Weighable weightFunction) {

        mapHole();
        mapBoundaries();

        for (Pixel pixel : holePixels)
            image.put(pixel.getRow(), pixel.getCol(), calculateFormula(weightFunction, pixel, boundaryPixels));

    }

    private void mapHole() {

        for (int i = 0; i < image.rows(); i++)
            for (int j = 0; j < image.cols(); j++)
                if (image.get(i, j)[0] == -1)
                    holePixels.add(new Pixel(i, j));
    }

    private void mapBoundaries() {

        for (int i = 0; i < image.rows(); i++)
        {
            for (int j = 0; j < image.cols(); j++)
            {
                if (isBoundary(i, j))
                    boundaryPixels.add(new Pixel(i, j));
            }
        }
    }


}
