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

public class MiningImageStreamTest {

    private MiningImageStream stream;
    private final String[] images = {
            "../data/jpg/jamie.jpg",
            "../data/jpg/white_black.jpg"
    };

    @Before
    public void setup() throws MiningException {

        stream = new MiningImageStream("../data/jpg");
        stream.open();
    }

    @Test
    public void printVectorsTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            System.out.println(Arrays.toString(stream.next().getValues()));
        }
    }

    @Test
    public void nextTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            BufferedImage image = getImage(i);
            double[] vector = stream.next().getValues();
            assertVectorAndRGB(image, vector);
        }
    }

    @Test
    public void getVectorTest() throws MiningException {

        for(int i = 0; i < stream.getImageNumber(); i++) {
            BufferedImage image = getImage(i);
            double[] vector = stream.getVector(i).getValues();
            assertVectorAndRGB(image, vector);
        }
    }

    private BufferedImage getImage(int index) {

        try {
            return ImageIO.read(new File(images[index]));
        } catch (IOException ex) {
            throw new NullPointerException(ex.getMessage());
        }
    }

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

    @After
    public void close() {
        stream.close();
    }
}