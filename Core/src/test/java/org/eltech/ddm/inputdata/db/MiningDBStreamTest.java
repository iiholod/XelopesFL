package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.junit.Before;
import org.junit.Test;

public class MiningDBStreamTest {
    private String url;
    private String user;
    private String password;
    private String tableName;

    @Before
    public void setUp() throws Exception {
        url = "jdbc:postgresql://localhost:5432/kddcup";
        user = "postgres";
        password = "qwerty";
        tableName = "iris2";
    }

    @Test
    public void connectionTest() throws MiningException {
        MiningDBStream miningDBStream = new MiningDBStream(url, user, password, tableName);
        MiningVector vector = miningDBStream.movePhysicalRecord(6);
        System.out.println(vector);
    }
}
