package com.visitscotland.brmx.translation.difference;

import java.util.ArrayList;
import java.util.List;

public class DocumentDifference {
    private List<FieldDifference> differences = new ArrayList<>();

    public List<FieldDifference> getDifferences() {
        return differences;
    }

    public void setDifferences(List<FieldDifference> differences) {
        this.differences = differences;
    }
}
