package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ENumericalAttributeProperties;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Преобразование логического типа данных
 */
public class DefBooleanConvert extends FieldDBConvert {
    //список значений
    private List<Integer> values = new ArrayList<>();

    @Override
    public void setTableName(String tableName) {
        super.setTableName(tableName);
    }

    /**
     * Получение категориальных характеристик
     * @param con объект соединения с БД
     * @param nameField название столбца
     * @return объект, содержащий категориальные характеристики
     */
    @Override
    public ECategoricalAttributeProperties getCategoricalProperties(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical = new ECategoricalAttributeProperties();
        getProperties(con, nameField);

        for (int value : values) {
            if (value == 0) {
                categorical.addCategory("false", CategoryProperty.valid);
            } else {
                categorical.addCategory("true", CategoryProperty.valid);
            }
        }

        return categorical;
    }

    /**
     * Получение числовых характеристик
     * @param con объект соединения с БД
     * @param nameField название столбца
     * @return объект, содержащий числовые характеристики
     */
    @Override
    public ENumericalAttributeProperties getNumericalProperties(Connection con, String nameField) {
        ENumericalAttributeProperties numerical = new ENumericalAttributeProperties();

        if (values.size() == 1) {
            numerical.setLowerBound(values.get(0));
            numerical.setUpperBound(values.get(0));
            numerical.setDiscrete(false);
        } else {
            numerical.setLowerBound(0);
            numerical.setUpperBound(1);
            numerical.setDiscrete(true);
            numerical.setDiscreteStepSize(1);
        }

        return numerical;
    }

    /**
     * Получение свойств столбца
     * @param con объект соединения с БД
     * @param nameField название столбца
     */
    private void getProperties(Connection con, String nameField) throws MiningDataException {
        ResultSet result;

        try {
            result = getValues(con, nameField);
            while (result.next()) {
                values.add(result.getBoolean(nameField) ? 1 : 0);
            }
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }
    }
}
