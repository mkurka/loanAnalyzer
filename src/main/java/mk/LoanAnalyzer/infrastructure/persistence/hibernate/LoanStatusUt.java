package mk.LoanAnalyzer.infrastructure.persistence.hibernate;

import mk.LoanAnalyzer.domain.LoanStatus;

public class LoanStatusUt extends CharEnumUserType<LoanStatus> {
    public LoanStatusUt() {
        super(LoanStatus.class);
    }

    @Override
    public LoanStatus getValue(char id) {
        return LoanStatus.byId(id);
    }
}
