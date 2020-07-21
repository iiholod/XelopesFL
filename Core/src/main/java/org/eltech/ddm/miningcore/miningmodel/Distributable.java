package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.environment.DataDistribution;

public interface Distributable {
    void setDistributionType(DataDistribution dist);

    DataDistribution getDistributionType();
}
