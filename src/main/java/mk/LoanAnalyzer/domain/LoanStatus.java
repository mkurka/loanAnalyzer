package mk.LoanAnalyzer.domain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum LoanStatus implements ICharIdEnum {

    /* v poradku */
    OKAY('o'),
    /* zaplacena */
    PAID('p'),
    /* po splatnosti */
    OVERDUE('v'),
    /* zesplatnena */
    REPAID('r');

    private final char id;

    LoanStatus(char id) {
        this.id = id;
    }

    private static final Map<Character, LoanStatus> ALL_ITEMS = Stream.of(LoanStatus.values())
            .collect(Collectors.toMap(LoanStatus::getId, Function.identity()));

    @Override
    public char getId() {
        return id;
    }

    public static LoanStatus byId(char id) {
        return ALL_ITEMS.get(id);
    }
}
