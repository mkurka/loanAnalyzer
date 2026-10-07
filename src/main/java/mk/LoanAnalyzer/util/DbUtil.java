package mk.LoanAnalyzer.util;

import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class DbUtil {

    private static final int LARGE_WINDOW_SIZE = 10000;

    public static PageRequest createPageRequest(Integer page, Integer size) {
        if (size != null && size < 1) {
            throw new IllegalStateException("Limit must not be less than one");
        }
        return PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(LARGE_WINDOW_SIZE));

    }
}