package mk.LoanAnalyzer.util;

import org.hibernate.proxy.HibernateProxy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author fja
 */
public class ObjectUtil {

    /**
     * @param o1
     * @param o2
     * @return
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }

    /**
     *
     * @param o
     * @return
     */
    public static String nullSafeToString(Object o) {
        return (o == null) ? null : o.toString();
    }

    /**
     *
     * @param o value to String
     * @param nullValue null value
     * @return String value
     */
    public static String nullSafeToString(Object o, String nullValue) {
        return (o == null) ? nullValue : o.toString();
    }

    /**
     * Java C#'s "as" operator emulation
     *
     * @param clazz
     * @param o
     * @return
     */
    public static <T> T as(Class<T> clazz, Object o) {
        if (clazz.isInstance(o)) {
            return clazz.cast(o);
        }
        return null;
    }

    /**
     * Java Oracle's "nvl" function emulation
     * @param value
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T valueOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static <T extends Comparable> T min(T t1, T t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        return t1.compareTo(t2) <= 0 ? t1 : t2;
    }

    public static <T extends Comparable> T max(T t1, T t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        return t1.compareTo(t2) >= 0 ? t1 : t2;
    }

    public static Class getProxyClass(Object o) {
        if (o == null) return null;
        if ( o instanceof HibernateProxy) {
            return ((HibernateProxy) o).getHibernateLazyInitializer()
//                .getImplementation().getClass();
                    .getPersistentClass(); // TODO je to ok?
        }
        else {
            return o.getClass();
        }
    }

    public static <T> List<T> nullOrSingleton(T item) {
        return Optional.ofNullable(item).map(Collections::singletonList).orElse(null);
    }
}
