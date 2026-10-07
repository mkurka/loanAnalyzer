package mk.LoanAnalyzer.infrastructure.auditing;

/**
 * Markup interface which allows composition of changes (object tree changes).
 *
 * Created by fja on 12.10.2015.
 */
public interface IChange {

    String OPERATION_PROPERTY_NAME = "operation";

    /**
     * Tribute to Jameson whiskey.
     * @param sb String builder
     * @return JSON serialized object.
     */
    StringBuilder toJameson(StringBuilder sb);


    Object[] getFlags();
}
