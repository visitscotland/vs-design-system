package com.visitscotland.brmx.translation.difference.ui.model;

import com.visitscotland.brmx.translation.difference.FieldValue;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SingleFieldDifferenceModel implements FieldDifferenceModel {
    private String fieldName;
    private List<DiffMatchPatch.Diff> diffList;
    private boolean isFile = false;

    public SingleFieldDifferenceModel(String fieldName, FieldValue previousValue, FieldValue latestValue) {
        this.fieldName = fieldName;
        if ( null != previousValue.getDocBase() && null != latestValue.getDocBase()) {
            // If the field is a file, treat is like a replacement
            diffList = new LinkedList<>();
            diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, previousValue.getValue()));
            diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, latestValue.getValue()));
            isFile = true;
        } else {
            DiffMatchPatch dmp = new DiffMatchPatch();
            diffList = dmp.diffMain(previousValue.getValue(), latestValue.getValue(), false);
        }
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean isMultiple() {
        return false;
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    @Override
    public List<DiffMatchPatch.Diff> getDiffList() {
        return diffList;
    }
}
