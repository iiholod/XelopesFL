package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;

/**
 * Created by Ivan on 20.11.2016.
 */
public class MiningModelElementRule {
    private final MiningModelElementPredicate predicates[];

    public MiningModelElementRule(MiningModelElementPredicate... predicates) {
        this.predicates = predicates;
    }


    public boolean verify(MiningModelElement element) throws MiningException {
        boolean value = predicates[0].verify(element);
        for (int i =1; i < predicates.length; i++){
            boolean v = predicates[i].verify(element);
            switch(predicates[i].getBoolOperator())
            {
                case OR: value = value || v; break;
                case AND: value = value && v; break;
                default: throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "MiningModelElementPredicate: unknown bool operator");
            }
        }

        return value;

    }



}
