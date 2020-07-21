package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ENumericalAttributeProperties;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DefStringConvert extends FieldDBConvert {
    private Map<String, Integer> categoriesIndices = new HashMap<>();

    //процент уникальных значений от общего числа записей
    private float percentValueUnique = 0.05f;

    //true - если больше или равно, чем percentValueUnique, то категориальный
    //false - если меньше или равно, чем  percentValueUnique, то категориальный
    private boolean switchUniquePercent = true;

    public Map<String, Integer> getCategoriesIndices() {
        return categoriesIndices;
    }

    @Override
    public void setTableName(String tableName) {
        super.setTableName(tableName);
    }

    public float getPercentValueUnique() {
        return percentValueUnique;
    }

    public void setPercentValueUnique(float percentValueUnique) {
        this.percentValueUnique = percentValueUnique / 100;
    }

    public boolean isSwitchUniquePercent() {
        return switchUniquePercent;
    }

    public void setSwitchUniquePercent(boolean switchUniquePercent) {
        this.switchUniquePercent = switchUniquePercent;
    }

    @Override
    public ECategoricalAttributeProperties getCategoricalProperties(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical = new ECategoricalAttributeProperties();

        if (isCategorical(con, nameField)) {
            ResultSet categories;
            try {
                categories = getValues(con, nameField);
                if (categories != null) {
                    categories.next();
                    if (categories.getString(nameField) == null)
                        categoriesIndices.put("null", categorical.addCategory("null", CategoryProperty.missing));
                    else
                        categories.previous();

                    while (categories.next()) {
                        categoriesIndices.put(categories.getString(nameField), categorical.addCategory(categories.getString(nameField), CategoryProperty.valid));
                    }
                }
            } catch (SQLException ex) {
                throw new MiningDataException("Cannot fill CategoricalProperties" + ex.getMessage());
            }
        }

        return categorical;
    }

    @Override
    public ENumericalAttributeProperties getNumericalProperties(Connection con, String nameField) throws MiningException {
        ENumericalAttributeProperties numerical = new ENumericalAttributeProperties();
        ResultSet rs;
        try {
            rs = getValues(con, nameField);
            rs.next();
            if (rs.getString(nameField) == null)
                rs.next();
            numerical.setLowerBound(rs.getString(nameField).hashCode());//хешкод для первой по алфавиту строки
            rs.last();
            if (rs.getString(nameField) == null)
                rs.previous();
            numerical.setUpperBound(rs.getString(nameField).hashCode());//хешкод для последней по алфавиту строки
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }
        numerical.setDiscrete(true);
        numerical.setDiscreteStepSize(1);

        return numerical;
    }

    private boolean isCategorical(Connection con, String nameField) throws MiningDataException {
        double allRowCount = getAllRowCount(con, nameField);
        double uniqueRowCount = getUniqueRowCountQuery(con, nameField);

        return (switchUniquePercent) ? (float) uniqueRowCount / allRowCount >= percentValueUnique :
                (float) uniqueRowCount / allRowCount <= percentValueUnique;
    }
}
