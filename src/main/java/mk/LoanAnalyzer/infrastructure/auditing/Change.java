package mk.LoanAnalyzer.infrastructure.auditing;

import mk.LoanAnalyzer.util.JsonUtil;

/**
 * General change.
 *
 * Created by fja on 12.10.2015.
 */
class Change implements IChange {

    private final String operation;
    private final String value;
    private final Object[] flags;


    public Change(String operation, String value, Object[] flags) {
        this.operation = operation;
        this.value = value;
        this.flags = flags;
    }


    @Override
    public String toString() {
        return toJameson(new StringBuilder()).toString();
    }

    @Override
    public StringBuilder toJameson(StringBuilder sb) {
        sb.append("{ ");
        JsonUtil.appendPropetyValue(sb, "operationDescription", operation);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "value", value);
        sb.append(" }");
        return sb;
    }

    @Override
    public Object[] getFlags() {
        return flags;
    }
}
