package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningDataException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ENumericalAttributeProperties;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Преобразование числовых типов данных
 */
public class DefNumsConvert extends FieldDBConvert {

    //Список категорий
    private List<String> catsList;
    private Map<String, Double> catsMap;

    @Override
    public void setTableName(String tableName) {
        super.setTableName(tableName);
    }

    public List<String> getCatsList() {
        return catsList;
    }

    public void setCatsList(List<String> catsList) {
        this.catsList = catsList;
    }

    public Map<String, Double> getCatsMap() {
        return catsMap;
    }

    public void setCatsMap(Map<String, Double> catsMap) {
        this.catsMap = catsMap;
    }

    @Override
    public ECategoricalAttributeProperties getCategoricalProperties(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical;

        if (catsList != null) {
            categorical = addCategoriesFromList(con, nameField);
        } else if (catsMap != null) {
            categorical = addCategoriesFromMap(con, nameField);
        } else {
            categorical = addDefCategories(con, nameField);
        }

        return categorical;
    }

    private ECategoricalAttributeProperties addCategoriesFromList(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical = new ECategoricalAttributeProperties();

        if (getUniqueRowCountQuery(con, nameField) >= catsList.size())
            for (String cat : catsList) {
                categorical.addCategory(cat, CategoryProperty.valid);
            }

        return categorical;
    }

    private ECategoricalAttributeProperties addCategoriesFromMap(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical = new ECategoricalAttributeProperties();

        ResultSet rs;
        List<Double> values = new ArrayList<>();
        try {
            rs = getValues(con, nameField);

            rs.next();
            if (rs.getString(nameField) == null)
                rs.next();
            if (!rs.getString(nameField).equals(""))
                rs.previous();

            while (rs.next()) {
                if (!rs.isLast()) {
                    values.add(rs.getDouble(nameField));
                } else {
                    if (rs.getString(nameField) != null && !rs.getString(nameField).equals("")) {
                        values.add(rs.getDouble(nameField));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        //сортировка catsMap по значению (по возрастанию)
        List list = new ArrayList(catsMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> a, Map.Entry<String, Double> b) {
                return a.getValue().intValue() - b.getValue().intValue();
            }
        });
        //определение попадания значения в диапозон:
        //если первое значение из всех значений источника(заранее отсортированы по возрастанию)
        // меньше или равно значению первой категории (тоже заранее отсортированы по возрастанию),
        // то данная категория является значением категориального типа
        if (((Map.Entry<String, Double>) list.get(0)).getValue() >= values.get(0)) {
            categorical.addCategory(((Map.Entry<String, Double>) list.get(0)).getKey(), CategoryProperty.valid);
        }

        Map.Entry<String, Double> lastCatEntry = null;

        //определение поподания в диапазон между двумя категориями. Проверка выполняется до последней категории
        int k = 0;
        for (int i = 1; i < list.size() - 1; i++) {
            Map.Entry<String, Double> lowCatEntry = (Map.Entry<String, Double>) list.get(i - 1);
            Map.Entry<String, Double> upCatEntry = (Map.Entry<String, Double>) list.get(i);

            for (int j = k; j < values.size(); j++) {
                if (values.get(j) >= lowCatEntry.getValue() && values.get(j) <= upCatEntry.getValue()) {
                    categorical.addCategory(upCatEntry.getKey(), CategoryProperty.valid);
                    k = ++j;
                    break;
                }
                //если значение из источника стало больше верхней границы, то переходим к следующему диапазону
                if (values.get(j) >= upCatEntry.getValue()) {
                    k = j;
                    break;
                }
            }

            lastCatEntry = (Map.Entry<String, Double>) list.get(i + 1);
        }

        //Проверка последней категории: если значение из источника больше или равно значению последней категории,
        // то данная категория является категориальным значением
        if (lastCatEntry != null) {
            for (int j = k; j < values.size(); j++) {
                if (values.get(j) >= lastCatEntry.getValue()) {
                    categorical.addCategory(lastCatEntry.getKey(), CategoryProperty.valid);
                    break;
                }
            }
        }

        return categorical;
    }


    private ECategoricalAttributeProperties addDefCategories(Connection con, String nameField) throws MiningException {
        ECategoricalAttributeProperties categorical = new ECategoricalAttributeProperties();

        List<String> cats = new ArrayList<>(Arrays.asList("Cat 1", "Cat 2", "Cat 3"));
        int count = 0;
        try {
            ResultSet rs = getValues(con, nameField);
            rs.last();
            if (rs.getString(nameField) == null) {
                ++count;
            }

            rs.first();
            if (rs.getString(nameField) == null)
                rs.next();
            if (!rs.getString(nameField).equals(""))
                rs.previous();

            while (rs.next() && count < 3) {
                categorical.addCategory(cats.get(count), CategoryProperty.valid);
                ++count;
            }
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        return categorical;
    }

    @Override
    public ENumericalAttributeProperties getNumericalProperties(Connection con, String nameField) throws MiningException {
        ENumericalAttributeProperties numerical = new ENumericalAttributeProperties();

        /*double step = getDiscreteStep(con, nameField);
        if (step != 0) {
            numerical.setDiscrete(true);
            numerical.setDiscreteStepSize(step);
        } else
            numerical.setDiscrete(false);*/

        numerical.setDiscrete(false);

        numerical.setUpperBound(getMaxValue(con, nameField));
        numerical.setLowerBound(getMinValue(con, nameField));

        return numerical;
    }

   /* private double getDiscreteStep(Connection con, String nameField) throws MiningDataException {
        double num1, num2;

        try (ResultSet values = getValues(con, nameField)) {
            values.first();
            if (values.getString(nameField) == null)
                if (!values.next())
                    return 0;
            if (values.getString(nameField).equals(""))
                if (!values.next())
                    return 0;

            num1 = values.getDouble(nameField);
            if (values.next()) {
                num2 = values.getDouble(nameField);
            } else
                return 0;
        } catch (SQLException ex) {
            throw new MiningDataException(ex.getMessage());
        }

        return num2 - num1;
    }*/
}
