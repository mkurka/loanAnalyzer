package mk.LoanAnalyzer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import mk.LoanAnalyzer.infrastructure.auditing.ChangeLog;
import mk.LoanAnalyzer.infrastructure.persistence.hibernate.LoanStatusUt;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="loans")
public class Loan {
    @Id
    @Column(name = "loan_id")
    @GeneratedValue(generator = "LoanIdSq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LoanIdSq", sequenceName = "sq_loans", allocationSize = 1)
    private long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_client_id")
    private LoanClient loanClient;

    private BigDecimal loanAmount;

    private BigDecimal interestRate;

    /* Dní po splatnosti */
    private Integer daysPastDue;

    @Type(LoanStatusUt.class)
    private LoanStatus status;

    /* Číslo smlouvy o postoupení */
    private String agreementNumber;

    /* Číslo investice */
    private String investmentNumber;

    private BigDecimal investmentAmount;

    /* Datum zafinancování */
    private LocalDate investmentDate;

    private BigDecimal returnedAmount;

    private BigDecimal paidInterest;

    private BigDecimal paidPenalty;

    private int remainingInstallments;

    private BigDecimal remainingInstallmentAmount;

    private BigDecimal expectedInterestAmount;

    private LocalDate nextInstallmentDate;

    private boolean sellable;

    private BigDecimal currentValue;

    private BigDecimal discountAmount;

    private LocalDate paidAt;

    private BigDecimal paidAmount;

    protected Loan() {
    }

    public Loan(LoanClient loanClient, BigDecimal loanAmount, BigDecimal interestRate, Integer daysPastDue, LoanStatus status, String agreementNumber, String investmentNumber, BigDecimal investmentAmount, LocalDate investmentDate, BigDecimal returnedAmount, BigDecimal paidInterest, BigDecimal paidPenalty, int remainingInstallments, BigDecimal remainingInstallmentAmount, BigDecimal expectedInterestAmount, LocalDate nextInstallmentDate, boolean sellable, BigDecimal currentValue, BigDecimal discountAmount, LocalDate paidAt, BigDecimal paidAmount) {
        this.loanClient = loanClient;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.daysPastDue = daysPastDue;
        this.status = status;
        this.agreementNumber = agreementNumber;
        this.investmentNumber = investmentNumber;
        this.investmentAmount = investmentAmount;
        this.investmentDate = investmentDate;
        this.returnedAmount = returnedAmount;
        this.paidInterest = paidInterest;
        this.paidPenalty = paidPenalty;
        this.remainingInstallments = remainingInstallments;
        this.remainingInstallmentAmount = remainingInstallmentAmount;
        this.expectedInterestAmount = expectedInterestAmount;
        this.nextInstallmentDate = nextInstallmentDate;
        this.sellable = sellable;
        this.currentValue = currentValue;
        this.discountAmount = discountAmount;
        this.paidAt = paidAt;
        this.paidAmount = paidAmount;
    }

    public long getLoanId() {
        return loanId;
    }

    public LoanClient getLoanClient() {
        return loanClient;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Integer getDaysPastDue() {
        return daysPastDue;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public String getInvestmentNumber() {
        return investmentNumber;
    }

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public BigDecimal getReturnedAmount() {
        return returnedAmount;
    }

    public BigDecimal getPaidInterest() {
        return paidInterest;
    }

    public BigDecimal getPaidPenalty() {
        return paidPenalty;
    }

    public int getRemainingInstallments() {
        return remainingInstallments;
    }

    public BigDecimal getRemainingInstallmentAmount() {
        return remainingInstallmentAmount;
    }

    public BigDecimal getExpectedInterestAmount() {
        return expectedInterestAmount;
    }

    public boolean isSellable() {
        return sellable;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public LocalDate getPaidAt() {
        return paidAt;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void update(ChangeLog changeLog, LoanStatus status, BigDecimal returnedAmount, BigDecimal paidInterest, Integer daysPastDue, BigDecimal paidPenalty, int remainingInstallments, BigDecimal remainingInstallmentAmount, BigDecimal expectedInterestAmount, LocalDate nextInstallmentDate, boolean sellable, BigDecimal currentValue, BigDecimal discountAmount, LocalDate paidAt, BigDecimal paidAmount) {
        this.daysPastDue = changeLog.track("daysPastDue", this.daysPastDue, daysPastDue);
        this.status = changeLog.track("status", this.status, status);
        this.returnedAmount = changeLog.track("returnedAmount", this.returnedAmount, returnedAmount);
        this.paidInterest = changeLog.track("paidInterest", this.paidInterest, paidInterest);
        this.paidPenalty = changeLog.track("paidPenalty", this.paidPenalty, paidPenalty);
        this.remainingInstallments = changeLog.track("remainingInstallments", this.remainingInstallments, remainingInstallments);
        this.remainingInstallmentAmount = changeLog.track("remainingInstallmentAmount", this.remainingInstallmentAmount, remainingInstallmentAmount);
        this.expectedInterestAmount = changeLog.track("expectedInterestAmount", this.expectedInterestAmount, expectedInterestAmount);
        this.nextInstallmentDate = changeLog.track("nextInstallmentDate", this.nextInstallmentDate, nextInstallmentDate);
        this.sellable = changeLog.track("sellable", this.sellable, sellable);
        this.currentValue = changeLog.track("currentValue", this.currentValue, currentValue);
        this.discountAmount = changeLog.track("discountAmount", this.discountAmount, discountAmount);
        this.paidAt = changeLog.track("paidAt", this.paidAt, paidAt);
        this.paidAmount = changeLog.track("paidAmount", this.paidAmount, paidAmount);
    }


    public static final class LoanBuilder {
        private LoanClient loanClient;
        private String loanNumber;
        private BigDecimal loanAmount;
        private BigDecimal interestRate;
        private Integer daysPastDue;
        private LoanStatus status;
        private String agreementNumber;
        private String investmentNumber;
        private BigDecimal investmentAmount;
        private LocalDate investmentDate;
        private BigDecimal returnedAmount;
        private BigDecimal paidInterest;
        private BigDecimal paidPenalty;
        private int remainingInstallments;
        private BigDecimal remainingInstallmentAmount;
        private BigDecimal expectedInterestAmount;
        private LocalDate nextInstallmentDate;
        private boolean sellable;
        private BigDecimal currentValue;
        private BigDecimal discountAmount;
        private LocalDate paidAt;
        private BigDecimal paidAmount;

        private LoanBuilder() {
        }

        public static LoanBuilder aLoan() {
            return new LoanBuilder();
        }

        public LoanBuilder withLoanClient(LoanClient loanClient) {
            this.loanClient = loanClient;
            return this;
        }

        public LoanBuilder withLoanNumber(String loanNumber) {
            this.loanNumber = loanNumber;
            return this;
        }

        public LoanBuilder withLoanAmount(BigDecimal loanAmount) {
            this.loanAmount = loanAmount;
            return this;
        }

        public LoanBuilder withInterestRate(BigDecimal interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public LoanBuilder withDaysPastDue(Integer daysPastDue) {
            this.daysPastDue = daysPastDue;
            return this;
        }

        public LoanBuilder withStatus(LoanStatus status) {
            this.status = status;
            return this;
        }

        public LoanBuilder withAgreementNumber(String agreementNumber) {
            this.agreementNumber = agreementNumber;
            return this;
        }

        public LoanBuilder withInvestmentNumber(String investmentNumber) {
            this.investmentNumber = investmentNumber;
            return this;
        }

        public LoanBuilder withInvestmentAmount(BigDecimal investmentAmount) {
            this.investmentAmount = investmentAmount;
            return this;
        }

        public LoanBuilder withInvestmentDate(LocalDate investmentDate) {
            this.investmentDate = investmentDate;
            return this;
        }

        public LoanBuilder withReturnedAmount(BigDecimal returnedAmount) {
            this.returnedAmount = returnedAmount;
            return this;
        }

        public LoanBuilder withPaidInterest(BigDecimal paidInterest) {
            this.paidInterest = paidInterest;
            return this;
        }

        public LoanBuilder withPaidPenalty(BigDecimal paidPenalty) {
            this.paidPenalty = paidPenalty;
            return this;
        }

        public LoanBuilder withRemainingInstallments(int remainingInstallments) {
            this.remainingInstallments = remainingInstallments;
            return this;
        }

        public LoanBuilder withRemainingInstallmentAmount(BigDecimal remainingInstallmentAmount) {
            this.remainingInstallmentAmount = remainingInstallmentAmount;
            return this;
        }

        public LoanBuilder withExpectedInterestAmount(BigDecimal expectedInterestAmount) {
            this.expectedInterestAmount = expectedInterestAmount;
            return this;
        }

        public LoanBuilder withNextInstallmentDate(LocalDate nextInstallmentDate) {
            this.nextInstallmentDate = nextInstallmentDate;
            return this;
        }

        public LoanBuilder withSellable(boolean sellable) {
            this.sellable = sellable;
            return this;
        }

        public LoanBuilder withCurrentValue(BigDecimal currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public LoanBuilder withDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public LoanBuilder withPaidAt(LocalDate paidAt) {
            this.paidAt = paidAt;
            return this;
        }

        public LoanBuilder withPaidAmount(BigDecimal paidAmount) {
            this.paidAmount = paidAmount;
            return this;
        }

        public Loan build() {
            return new Loan(loanClient, loanAmount, interestRate, daysPastDue, status, agreementNumber, investmentNumber, investmentAmount, investmentDate, returnedAmount, paidInterest, paidPenalty, remainingInstallments, remainingInstallmentAmount, expectedInterestAmount, nextInstallmentDate, sellable, currentValue, discountAmount, paidAt, paidAmount);
        }
    }
}
