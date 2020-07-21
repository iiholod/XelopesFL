package org.eltech.ddm.classification.naivebayes.continious;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.inputdata.file.csv.CsvFileWriter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvFileNormalizier {
    private static final String[][] DEFAULT_HEADERS = {
            {
                    "state",
                    "rural",
                    "stratum_code",
                    "year_of_intr",
                    "month_of_intr",
                    "result_of_interview",
                    "age",
                    "marital_status",
                    "delivered_any_baby",
                    "born_alive_female",
                    "born_alive_male",
                    "born_alive_total",
                    "surviving_female",
                    "surviving_male",
                    "surviving_total",
                    "outcome_pregnancy",
                    "mother_age_when_baby_was_born",
                    "is_tubectomy",
                    "is_vasectomy",
                    "is_copper_t",
                    "is_pills_daily",
                    "is_piils_weekly",
                    "is_emergency_contraceptive",
                    "is_condom",
                    "is_moder_methods",
                    "is_contraceptive",
                    "is_periodic_abstinence",
                    "is_withdrawal",
                    "is_amenorrahoea",
                    "is_other_traditional_method",
                    "is_currently_pregnant",
                    "pregnant_month",
                    "outcome_pregnancy"
            },
            {
                    "is_anc_registered",
                    "willing_to_get_pregnant",
                    "is_currently_menstruating",
                    "when_you_bcome_mother_last_time",
                    "is_any_fp_methos_used",
                    "source_of_treatment_for_fp",
                    "how_long_using_this_method",
                    "method_obtain_last_time",
                    "reason_for_not_using_fp_method",
                    "method_type_used_in_last_5_yrs",
                    "reason_for_discontinuation",
                    "intend_to_use_fp_method_in_future",
                    "when_method_is_going_to_use",
                    "which_method_going_to_pefer_for_fp",
                    "want_more_childern",
                    "next_child_preference",
                    "outcome_pregnancy",
                    "time_for_next_child",
                    "anm_in_last_3_months",
                    "aware_abt_rti",
                    "aware_abt_hiv",
                    "aware_of_haf",
                    "aware_of_the_danger_signs",
                    "religion",
                    "social_group_code",
                    "currently_attending_school",
                    "reason_for_not_attending_school",
                    "highest_qualification",
                    "disability_status",
                    "injury_treatment_type",
                    "illness_type",
                    "treatment_source",
                    "symptoms_pertaining_illness",
                    "sought_medical_care",
                    "diagnosed_for",
                    "diagnosis_source",
                    "regular_treatment",
                    "outcome_pregnancy",
            }
    };

    private CsvParserSettings settings;
    private CsvParser parser;
    private InputStream stream;


    public CsvFileNormalizier settings(CsvParserSettings settings, String... headers) {
        this.settings = settings;
        this.settings.setHeaderExtractionEnabled(true);
        this.settings.setColumnReorderingEnabled(true);
        this.settings.setDelimiterDetectionEnabled(true);
        if (headers.length != 0) {
            settings.setHeaders(headers);
            settings.selectFields(headers);
        }
        return this;
    }

    public CsvFileNormalizier build(InputStream stream) {
        this.parser = new CsvParser(settings);
        this.stream = stream;
        return this;
    }


    public void normilize(String[] headers, String target) throws FileNotFoundException {
        this.parser.beginParsing(new InputStreamReader(stream));
        CsvFileWriter writer = new CsvFileWriter(headers);
        String[] row;
        while ((row = this.parser.parseNext()) != null) {
            writer.write(row, target);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int i = 1;
        for (String[] headers : DEFAULT_HEADERS) {
            CsvFileNormalizier normalizier = new CsvFileNormalizier()
                    .settings(new CsvParserSettings(), headers)
                    .build(CsvFileNormalizier.class.getClassLoader().getResourceAsStream("DataSet.csv"));
            normalizier.normilize(headers, "VDataSet" + i++);
        }
    }


}
