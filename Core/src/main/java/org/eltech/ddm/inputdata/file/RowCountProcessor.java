package org.eltech.ddm.inputdata.file;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;

import java.util.Arrays;

public class RowCountProcessor extends AbstractRowProcessor {
    private int rowCount;

    @Override
    public void rowProcessed(String[] row, ParsingContext context) {
        if (!Arrays.equals(row, context.parsedHeaders())) {
            rowCount++;
        }
    }

    public int getRowCount() {
        return rowCount;
    }
}