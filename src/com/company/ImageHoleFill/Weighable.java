package com.company.ImageHoleFill;

/**
 * in order to define a specific weight function, one should implement this interface
 */

public interface Weighable {

    float weight(Pixel pixelA, Pixel pixelB);
}
