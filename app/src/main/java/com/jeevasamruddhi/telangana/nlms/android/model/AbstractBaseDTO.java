package com.jeevasamruddhi.telangana.nlms.android.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by jayalakshmi on 4/2/2018.
 */

public abstract class AbstractBaseDTO extends GsonObject {
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
