package mk.LoanAnalyzer.util;

/**
 * Helps you with strongly typed return values.
 *
 * Created by fja on 13.10.2015.
 */
public class Tuple<A,B> {

    private final A a;
    private final B b;


    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }


    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (a != null ? !a.equals(tuple.a) : tuple.a != null) return false;
        return b != null ? b.equals(tuple.b) : tuple.b == null;

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
