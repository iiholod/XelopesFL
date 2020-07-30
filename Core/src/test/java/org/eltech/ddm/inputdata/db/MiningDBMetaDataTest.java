package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningException;
import org.junit.Before;
import org.junit.Test;
import org.omg.java.cwm.objectmodel.core.TaggedValue;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MiningDBMetaDataTest {
    private String url;
    private String user;
    private String password;
    private String tableName;


    @Before
    public void setUp() throws Exception {
        url = "jdbc:postgresql://localhost:5432/iris";
        user = "postgres";
        password = "qwerty";
        tableName = "iris";
    }


    @Test
    public void MetaDataTest() throws MiningException {
        MiningDBStream miningDBStream = new MiningDBStream(url, user, password, tableName);
Map<String, TaggedValue> a;

        // Проверка по типам данных столбцов
        assertEquals ("float8",miningDBStream.getColumnsInfo().getOrDefault("sepallength", "0"));
        assertEquals ("float8",miningDBStream.getColumnsInfo().getOrDefault("sepalwidth", "0"));
        assertEquals ("float8",miningDBStream.getColumnsInfo().getOrDefault("petallength", "0"));
        assertEquals ("float8",miningDBStream.getColumnsInfo().getOrDefault("petalwidth", "0"));
        assertEquals ("varchar",miningDBStream.getColumnsInfo().getOrDefault("class", "0"));


        // Проверка наименований столбцов
        assertEquals ("petalwidth",miningDBStream.getLogicalData().getAttribute(0).getName());
        assertEquals ("sepallength",miningDBStream.getLogicalData().getAttribute(1).getName());
        assertEquals ("sepalwidth",miningDBStream.getLogicalData().getAttribute(2).getName());
        assertEquals ("petallength",miningDBStream.getLogicalData().getAttribute(3).getName());
        assertEquals ("class",miningDBStream.getLogicalData().getAttribute(4).getName());

    }

}
