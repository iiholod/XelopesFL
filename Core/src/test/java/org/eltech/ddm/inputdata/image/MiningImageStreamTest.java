package org.eltech.ddm.inputdata.image;

import org.eltech.ddm.miningcore.MiningException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * A class for testing reading images from a catalog and converting them to vectors.
 * @author Maxim Kolpaschikov
 */
public class MiningImageStreamTest {

    private MiningImageStream stream;
    private final String[] images = {
            "../data/jpg/jamie.jpg",
            "../data/jpg/white_black.jpg"
    };

    /**
     * Opens the stream.
     */
    @Before
    public void setup() throws MiningException {

        stream = new MiningImageStream("../data/jpg");
        stream.open();
    }

    /**
     * The output vectors of the images.
     * @throws MiningException
     */
    @Test
    public void printVectorsTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            System.out.println(Arrays.toString(stream.next().getValues()));
        }
    }

    /**
     * Checking the method 'next()'
     */
    @Test
    public void nextTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            BufferedImage image = getImage(i);
            double[] vector = stream.next().getValues();
            assertVectorAndRGB(image, vector);
        }
    }

    /**
     * Checking the method 'getVector()'
     */
    @Test
    public void getVectorTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            BufferedImage image = getImage(i);
            double[] vector = stream.getVector(i).getValues();
            assertVectorAndRGB(image, vector);
        }
    }

    /**
     * Checking the method 'reset()'
     */
    @Test
    public void resetTest() throws MiningException {

        stream.next();
        stream.next();
        stream.reset();

        BufferedImage image = getImage(0);
        double[] vector = stream.next().getValues();
        assertVectorAndRGB(image, vector);
    }

    /**
     * Returns an image by index.
     * @param position - number of image
     * @return image
     */
    private BufferedImage getImage(int position) {

        try {
            return ImageIO.read(new File(images[position]));
        } catch (IOException ex) {
            throw new NullPointerException(ex.getMessage());
        }
    }

    /**
     * Comparison of colors of received vectors and images.
     */
    private void assertVectorAndRGB(BufferedImage image, double[] vector) {

        int vecIndex = 0;
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                Color vectorColor = new Color((int) vector[vecIndex++]);
                Color imageColor = new Color(image.getRGB(x, y));

                Assert.assertEquals(vectorColor.getRed(), imageColor.getRed());
                Assert.assertEquals(vectorColor.getBlue(), imageColor.getBlue());
                Assert.assertEquals(vectorColor.getGreen(), imageColor.getGreen());
                Assert.assertEquals(vectorColor.getAlpha(), imageColor.getAlpha());
            }
        }
    }

    /**
     * Closes the stream.
     */
    @After
    public void close() {
        stream.close();
    }
}