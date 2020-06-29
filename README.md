# Image_Hole_Filler

## What is this project?

This program is a small image processing library that fills holes in images, along with a small command line utility that uses that library.
The library supports filling holes in grayscale images, where each pixel value is a float in the range [0, 1] and hole (missing) values which are marked with the value -1.

The library supports two filling algorithms:

*n represents the number of missing pixels*
1) Gives the best results - run in O(n^2).
2) Approximates the result in O(n) to a high degree of accuracy.

## How can I run it?

The input image is being generated from sample.jpg in the main folder and a hole is being generated. To check out different images, simply change sample.jpg with your own.
