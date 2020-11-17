package com.visitscotland.brmx.mock;

import com.visitscotland.brmx.beans.Megalinks;
import org.mockito.Mockito;

public class MegalinksMockBuilder {

    private Megalinks megalinks;

    public MegalinksMockBuilder() {
        megalinks = Mockito.mock(Megalinks.class);
    }

    public Megalinks build() {
        return megalinks;
    }
}
