package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.BoolOperator;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.Operator;

/**
 * Created by Ivan on 20.11.2016.
 */
public class MiningModelElementPredicate {

    /** Name of the evaluation property. */
    protected final int property;

    /** Value to compare with. */
    protected final Object value;

    /** Simple predicate operator. */
    protected final Operator op;

    /** bool operator between prev and this predicates */
    protected final BoolOperator boolOperator;

    public MiningModelElementPredicate(int property, Operator op, Object value) {
        this.property = property;
        this.op = op;
        this.value = value;
        boolOperator = null;
    }

    public MiningModelElementPredicate(int property, Operator op, Object value, BoolOperator boolOperator) {
        this.property = property;
        this.op = op;
        this.value = value;
        this.boolOperator = boolOperator;
    }

    public BoolOperator getBoolOperator() {
        return boolOperator;
    }

    public boolean verify(MiningModelElement element) throws MiningException {
//        Object elem_value = element.get(property);
//
//        switch(op)
//        {
//            case EQUAL: return value.equals(elem_value);
//            case NOT_EQUAL: return !value.equals(elem_value);
////            case LESS: return v < d;
////            case GREATER: return v > d;
////            case LESS_OR_EQUAL: return v <= d;
////            case GREATER_OR_EQUAL: return v >= d;
////            case IS_MISSING: return Category.isMissingValue(v);
////            case IS_NOT_MISSING: return !Category.isMissingValue(v);
//            default: throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "MiningModelElementPredicate: unknown operator");
//        }
        return true;
    }


}
