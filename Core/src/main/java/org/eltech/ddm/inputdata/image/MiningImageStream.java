package org.eltech.ddm.inputdata.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MiningImageStream {

    /** The image of the user. */
    private BufferedImage image;

    /** The matrix of pixels of the image. */
    private double[][] matrix;

    // -----------------------------------------------------------------------
    //  Methods for changing the stream state
    // -----------------------------------------------------------------------

    public MiningImageStream(BufferedImage image) {

        super();
        this.image = image;
        initMatrix();
    }

    public MiningImageStream(String path) {

        super();
        try {
            this.image = ImageIO.read(new File(path));
            initMatrix();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------
    //  Methods for converting images to matrix
    // -----------------------------------------------------------------------

    private void initMatrix() {

        matrix = new double[image.getWidth()][image.getHeight()];

        for (int x = 0; x < image.getWidth(); x++)
            for (int y = 0; x < image.getHeight(); y++) {
                matrix[x][y] = image.getRGB(x,y);
            }
    }

    // -----------------------------------------------------------------------
    //  Methods for converting images
    // -----------------------------------------------------------------------

    public double[][] toMatrix() {

        return matrix;
    }
}
