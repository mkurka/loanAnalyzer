package mk.LoanAnalyzer.infrastructure.auditing;

import mk.LoanAnalyzer.domain.ICharIdEnum;
import mk.LoanAnalyzer.util.JsonUtil;
import mk.LoanAnalyzer.util.MathUtil;
import mk.LoanAnalyzer.util.ObjectUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Helper class to track changes.
 *
 * Created by fja on 09.10.2015.
 */
public class ChangeLog implements IChange {

    private final Object entityId;
    private final String entityName;
    private final Operation operation;
    private final List<IChange> changes;

    public ChangeLog(String entityName, Object entityId, Operation operation) {
        this.operation = operation;
        this.entityId = entityId;
        this.entityName = entityName;
        this.changes = new ArrayList<>();
    }


    public Object getEntityId() {
        return entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public Operation getOperation() {
        return operation;
    }

    /**
     * To be called with every single property change.
     *
     * NOTE: For sets we uses Hibernate smart approach to clear the former persistent set and add new items to it.
     *
     * @param property Business name of the property (in Czech)
     * @param formerValue Former value to discard.
     * @param newValue New value to set.
     * @return The newValue for development convenience.
     */
    public <T> T track(String property, T formerValue, T newValue, Object ... flags) {

        if (formerValue == newValue) {
            return formerValue; // No change
        }

        String formerValueDescription = null;
        String newValueDescription = null;

        if (formerValue != null && newValue != null) {

            if (formerValue.equals(newValue)) {
                return formerValue; // No change
            }

            // Decimal cases
            if (formerValue instanceof BigDecimal && MathUtil.equals((BigDecimal) formerValue, (BigDecimal) newValue)) {
                return formerValue; // No change
            }
            if (formerValue instanceof Double && MathUtil.equals((Double) formerValue, (Double) newValue)) {
                return formerValue; // No change
            }
            if (formerValue instanceof Float && MathUtil.equals((Float) formerValue, (Float) newValue)) {
                return formerValue; // No change
            }

            // Set case
            if (formerValue instanceof Set) {
                Set formerSet = (Set) formerValue;
                Set newSet = (Set) newValue;
                if (formerSet.size() == newSet.size() && formerSet.containsAll(newSet)) {
                    return formerValue; // No change
                }

                // This is better approach for Hibernate (but not very clean, as it ads side effect)
                formerValueDescription = ObjectUtil.nullSafeToString(formerValue);
                newValueDescription = ObjectUtil.nullSafeToString(newValue);
                formerSet.clear();
                formerSet.addAll(newSet);
                newValue = (T)formerSet;
            }

            // TODO List case
        }

        // Now we are sure that there is a change
        if (formerValueDescription == null) {
            formerValueDescription = ObjectUtil.nullSafeToString(formerValue);
        }
        if (newValueDescription == null) {
            newValueDescription = ObjectUtil.nullSafeToString(newValue);
        }
        changes.add(new PropertyChange(property, formerValueDescription, newValueDescription, flags));
        return newValue;
    }

    /**
     * To be called for general operation, eg. add of some item.
     * @param operation
     * @param what
     */
    public void track(String operation, Object what) {
        changes.add(new Change(operation, ObjectUtil.nullSafeToString(what), null));
    }

    /**
     * Conditionally add subordinate object tracking.
     * @param subordinate
     */
    public ChangeLog track(ChangeLog subordinate) {
        if (subordinate.isChange()) {
            changes.add(subordinate);
        }
        return this;
    }

    /**
     *
     * @return True if anything has really changed. False if no change has been performed and former values matches the new values.
     */
    public boolean isChange() {
        return !changes.isEmpty();
    }

    public ChangeLog subordinateLog(String entityName, Object entityId) {
        ChangeLog cl = new ChangeLog(entityName, entityId, this.operation);
        return cl;
    }

    /**
     * @return
     */
    @Override
    public StringBuilder toJameson(StringBuilder sb) {
        sb.append("{ ");
        JsonUtil.appendPropetyValue(sb, "entityId", entityId);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "entityName", entityName);
        sb.append(", ");
        JsonUtil.appendPropetyValue(sb, "operation", operation.getId());
        sb.append(", \"changes\" : [");
        boolean first = true;
        for (IChange item : changes) {
            if ((item instanceof ChangeLog) && !((ChangeLog) item).isChange()) {
                continue;
            }
            if (first) {
                first = false;
            }
            else {
                sb.append(", ");
            }
            item.toJameson(sb);
        }
        sb.append(" ] }");
        return sb;
    }

    @Override
    public Object[] getFlags() {
        return null; // No flags in root changelog
    }

    public Map<Object, Set<Object>> getFlagsMap() {
        if (!isChange()) {
            return Collections.emptyMap();
        }
        Map<Object, Set<Object>> result = new HashMap<>();
        for (IChange item : changes) {
            if ((item instanceof ChangeLog) ) {
                FlagsUtils.mergeMaps(result, ((ChangeLog)item).getFlagsMap());
                continue;
            }
            if (item.getFlags() == null || item.getFlags().length == 0){
                continue;
            }
            for(Object flag : item.getFlags()) {
                if (flag == null) {
                    continue;
                }
                FlagsUtils.putToMap(result, flag, entityId);
            }
        }
        return result;
    }


    @Override
    public String toString() {
        return toJameson(new StringBuilder()).toString();
    }

    /**
     *
     */
    public enum Operation implements ICharIdEnum {

        CREATE('C', "Vytvoření"),

        UPDATE('U', "Změna"),

        DELETE('D', "Smazání");

        private final char id;
        private final String description;


        Operation(char id, String description) {
            this.id = id;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public char getId() {
            return id;
        }
    }
}
