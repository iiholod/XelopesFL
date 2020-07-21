package org.eltech.ddm.inputdata.db;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ECategoricalAttributeProperties;
import org.eltech.ddm.miningcore.miningdata.ENumericalAttributeProperties;

import java.sql.Connection;

public class DefDateTimeConvert extends FieldDBConvert {

    @Override
    public void setTableName(String tableName) {
        super.setTableName(tableName);
    }

    @Override
    public ECategoricalAttributeProperties getCategoricalProperties(Connection con, String nameField) throws MiningException {
        return null;
    }

    @Override
    public ENumericalAttributeProperties getNumericalProperties(Connection con, String nameField) throws MiningException {
        ENumericalAttributeProperties numerical = new ENumericalAttributeProperties();
        long min, max;

        min = getEarliestDate(con, nameField);
        numerical.setLowerBound(min);
        max = getOldestDate(con, nameField);
        numerical.setUpperBound(max);

        if (max - min > 0) {
            numerical.setDiscrete(true);
            numerical.setDiscreteStepSize(getDiscreteStep());
        } else {
            numerical.setDiscrete(false);
        }

        return numerical;
    }

    private double getDiscreteStep() throws MiningException {
        double step;//seconds

        switch (columnType) {
            case "TIME":
                step = 0.000001;//100 nanoseconds
                break;
            case "TIMESTAMP":
                step = 0.000001;//100 nanoseconds
                break;
            case "DATE":
                step = 24 * 60 * 60;//1 day
                break;
            case "DATETIME":
                step = 1;//1 seconds
                break;
            default:
                throw new MiningException(MiningErrorCode.INVALID_DATA_TYPE, "Type " + columnType + " is not supported");
        }

        return step;
    }
}
