package mk.LoanAnalyzer.infrastructure.auditing;

import mk.LoanAnalyzer.util.JsonUtil;

import java.util.Arrays;

/**
 * Change of property value.
 *
 * Created by fja on 12.10.2015.
 */
class PropertyChange implements IChange {

    private final String property;
    private final String formerValue;
    private final String newValue;
    private final Object[] flags;


    public PropertyChange(String property, String formerValue, String newValue, Object[] flags) {
        this.property = property;
        this.formerValue = formerValue;
        this.newValue = newValue;
        this.flags = flags;
    }


    @Override
    public String toString() {
        return toJameson(new StringBuilder()).toString();
    }

    @Override
    public StringBuilder toJameson(StringBuilder sb) {
        sb.append("{ ");
        JsonUtil.appendPropetyValue(sb, "property", property);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "formerValue", formerValue);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "newValue", newValue);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "flags", flags!= null ? Arrays.deepToString(flags) : "");
        sb.append(" }");
        return sb;
    }

    @Override
    public Object[] getFlags() {
        return flags;
    }
}
