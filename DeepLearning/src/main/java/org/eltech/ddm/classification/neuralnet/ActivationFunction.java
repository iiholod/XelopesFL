package org.eltech.ddm.classification.neuralnet;

import static java.lang.Math.*;

public class ActivationFunction{

    public enum ActivationFunctions {
        threshold,
        logistic,
        tanh,
        identity,
        exponential,
        reciprocal,
        square,
        Gauss,
        sine,
        cosine,
        Elliott,
        arctan,
        radialBasis
    }

    public static double applyFunction(ActivationFunctions type, double input){
        double result = 0;
        switch (type){
            case threshold:
                int threshold = 1;
                result = (input > threshold)? 1 : 0; // Threshold ?
                break;

            case logistic:
                result = 1 / (1 + exp(input * (-1)));
                break;

            case tanh:
                result = (1 - exp(-2 * input))/(1 + exp(-2 * input));
                break;

            case identity:
                result = input;
                break;

            case exponential:
                result = exp(input);
                break;

            case reciprocal:
                result = 1 / input;
                break;

            case square:
                result = input * input;
                break;

            case Gauss:
                result = exp(-1 * input * input);
                break;

            case sine:
                result = sin(input);
                break;

            case cosine:
                result = cos(input);
                break;

            case Elliott:
                result = input / (1 + abs(input));
                break;

            case arctan:
                result = 2 * atan(input)/PI;
        }
        return result;
    }

    public static double applyRBFfunction(double input, double altitude, int f) {
        return exp(f * log(altitude) - input);
    }
}

