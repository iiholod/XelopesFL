package org.eltech.ddm.inputdata.image;

import org.junit.Test;

import java.util.Arrays;

public class MiningImageStreamTest {

    private MiningImageStream stream;

    @Test
    public void matrixTest() {

        stream = new MiningImageStream("../data/png/yandex_img.png");
        System.out.println(Arrays.deepToString(stream.toMatrix()));
    }
}
