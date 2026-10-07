package mk.LoanAnalyzer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="loan_clients")
public class LoanClient {
    @Id
    @Column(name = "loan_client_id")
    @GeneratedValue(generator = "LoanClientIdSq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LoanClientIdSq", sequenceName = "sq_loan_clients", allocationSize = 1)
    private long loanClientId;

    private String clientNumber;

    protected LoanClient() {
    }

    public LoanClient(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public long getLoanClientId() {
        return loanClientId;
    }

    public String getClientNumber() {
        return clientNumber;
    }
}
