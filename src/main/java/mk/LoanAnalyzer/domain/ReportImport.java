package mk.LoanAnalyzer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="report_imports")
public class ReportImport {

    @Id
    @Column(name = "report_import_id")
    @GeneratedValue(generator = "ReportImportIdSq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ReportImportIdSq", sequenceName = "sq_report_imports", allocationSize = 1)
    private long reportImportId;

    private String fileName;

    private LocalDateTime import_started_at;

    private LocalDateTime import_finished_at;

    private LocalDateTime report_generated_at;

    private int numberOfItems;

    private int newLoansCount;

    private BigDecimal newLoansAmount;

    private int closedLoansCount;

    private BigDecimal closedLoansAmount;

    private int paymentsCount;

    private BigDecimal paymentsAmount;

    protected ReportImport() {
    }

    public ReportImport(String fileName, LocalDateTime import_started_at, LocalDateTime report_generated_at) {
        this.fileName = fileName;
        this.import_started_at = import_started_at;
        this.report_generated_at = report_generated_at;
    }

    public long getReportImportId() {
        return reportImportId;
    }

    public String getFileName() {
        return fileName;
    }

    public LocalDateTime getImport_started_at() {
        return import_started_at;
    }

    public LocalDateTime getImport_finished_at() {
        return import_finished_at;
    }

    public LocalDateTime getReport_generated_at() {
        return report_generated_at;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int getNewLoansCount() {
        return newLoansCount;
    }

    public BigDecimal getNewLoansAmount() {
        return newLoansAmount;
    }

    public int getClosedLoansCount() {
        return closedLoansCount;
    }

    public BigDecimal getClosedLoansAmount() {
        return closedLoansAmount;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    public BigDecimal getPaymentsAmount() {
        return paymentsAmount;
    }


}
