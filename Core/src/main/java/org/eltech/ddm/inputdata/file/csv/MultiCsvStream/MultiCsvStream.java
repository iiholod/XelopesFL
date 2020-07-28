package org.eltech.ddm.inputdata.file.csv.MultiCsvStream;

import com.opencsv.exceptions.CsvException;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;

import java.io.IOException;

interface MultiCsvStream {
     MiningVector next() throws CsvException, IOException, MiningException;
     MiningVector getVector(int position) throws CsvException, IOException, MiningException;
}
