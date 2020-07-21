package org.eltech.ddm.miningcore;

public class VerificationReport //implements javax.datamining.VerificationReport 
{

	private String reportText;
	
	private  ReportType reportType;
	
	public VerificationReport(ReportType type, String text){
		reportText = text;
		reportType = type;
	}
	
	/**
	 * Returns a string representation of the verification report. 
	 * @return
	 */
	public String getReportText() {
		return reportText;
	}

	/**
	 * Returns the type of the report.
	 * @return
	 */
	public ReportType getReportType() {
		return reportType;
	}

}
