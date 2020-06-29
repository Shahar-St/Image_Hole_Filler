package com.company.ImageHoleFill;

import java.awt.geom.Point2D;

/**
 * the default weight function W(u,v)z,e = 1 / |u-v|^z + e
 */
public class DefaultWeightFunction implements Weighable {

    private final int z;
    private final float epsilon;

    public DefaultWeightFunction(int z, float epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    @Override
    public float weight(Pixel pixelA, Pixel pixelB) {

        double distance = Point2D.distance(pixelA.getCol(), pixelA.getRow(), pixelB.getCol(), pixelB.getRow());
        return (float) (1 / (Math.pow(distance, z) + epsilon));
    }

}
