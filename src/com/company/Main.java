package com.company;

import com.company.ImageHoleFill.DefaultWeightFunction;
import com.company.ImageHoleFill.FillingStrategies.AccurateHoleFiller;
import com.company.ImageHoleFill.FillingStrategies.ApproxHoleFiller;
import com.company.ImageHoleFill.FillingStrategies.HoleFiller;
import com.company.ImageHoleFill.ImageWithHole;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;

import static org.opencv.core.CvType.*;

public class Main {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Scanner scanner = new Scanner(System.in);

        String samplePath = "sample.jpg";
        String inputPath = "input.jpg";
        String outputPath = "output.jpg";

        Mat matrix = Imgcodecs.imread(samplePath, Imgcodecs.IMREAD_GRAYSCALE);
        prepareInput(matrix, inputPath);

        System.out.println("Welcome! please follow the prompts.");
        String commandInput;
        boolean inputError = true;

        System.out.print("Enter Z: ");
        int z = -1;
        while (inputError)
        {
            commandInput = scanner.nextLine();

            try
            {
                inputError = false;
                z = Integer.parseInt(commandInput);
            }
            catch (NumberFormatException e)
            {
                inputError = true;
            }

            if (inputError || z < 0)
            {
                System.out.println("Z should be positive integer, please try again.");
                inputError = true;
            }
        }

        System.out.print("Enter epsilon: ");
        inputError = true;
        float epsilon = -1;
        while (inputError)
        {
            commandInput = scanner.nextLine();
            try
            {
                inputError = false;
                epsilon = Float.parseFloat(commandInput);
            }
            catch (NumberFormatException e)
            {
                inputError = true;
            }

            if (inputError || epsilon <= 0 || epsilon > 1)
            {
                System.out.println("Epsilon should be within (0,1], please try again.");
                inputError = true;
            }
        }

        System.out.println("Enter image connectivity: ");
        inputError = true;
        int connectivity = -1;
        while (inputError)
        {
            commandInput = scanner.nextLine();
            try
            {
                inputError = false;
                connectivity = Integer.parseInt(commandInput);
            }
            catch (NumberFormatException e)
            {
                inputError = true;
            }

            if (inputError || (connectivity != 4 && connectivity != 8))
            {
                System.out.println("Currently supporting only 4 and 8 connected images, please try again.");
                inputError = true;
            }
        }

        System.out.println("Choose filling strategy:");
        System.out.println("\t0 : Exact filling algorithm - runs in O(n^2) WC");
        System.out.println("\t1 : Approximate filling algorithm - runs in O(n) WC");
        int strategy = -1;
        inputError = true;
        while (inputError)
        {
            commandInput = scanner.nextLine();
            try
            {
                inputError = false;
                strategy = Integer.parseInt(commandInput);
            }
            catch (NumberFormatException e)
            {
                inputError = true;
            }

            if (inputError || (strategy != 0 && strategy != 1))
            {
                System.out.println("Please enter 0 or 1.");
                inputError = true;
            }
        }

        ImageWithHole image = new ImageWithHole(matrix, connectivity);
        System.out.println("Processing...");
        HoleFiller filler;
        if (strategy == 0)
            filler = new AccurateHoleFiller(image);
        else
            filler = new ApproxHoleFiller(image);

        filler.fillHole(new DefaultWeightFunction(z, epsilon));

        convertTo256(matrix);
        Imgcodecs.imwrite(outputPath, matrix);
        System.out.println("Done! check the results out!");
    }

    private static void prepareInput(Mat matrix, String inputPath) {

        convertTo01(matrix);
        generateHole(matrix);
        convertTo256(matrix);
        Imgcodecs.imwrite(inputPath, matrix);
        convertTo01(matrix);
    }

    private static void convertTo01(Mat matrix) {

        matrix.convertTo(matrix, CV_32F);
        for (int i = 0; i < matrix.rows(); i++)
            for (int j = 0; j < matrix.cols(); j++)
                matrix.put(i, j, matrix.get(i, j)[0] / 255);
    }

    public static void convertTo256(Mat matrix) {

        for (int i = 0; i < matrix.rows(); i++)
            for (int j = 0; j < matrix.cols(); j++)
                matrix.put(i, j, matrix.get(i, j)[0] * 255);
    }

    public static void generateHole(Mat matrix) {

        for (int i = matrix.rows() / 3; i < 2 * matrix.rows() / 3; i++)
            for (int j = matrix.cols() / 3; j < 2 * matrix.cols() / 3; j++)
                matrix.put(i, j, -1);
    }
}
