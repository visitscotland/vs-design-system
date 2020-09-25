package com.visitscotland.brmx.translation.difference.ui.model;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.List;

public class SingleFieldDifferenceModel implements FieldDifferenceModel {
    private String fieldName;
    private List<DiffMatchPatch.Diff> diffList;

    public SingleFieldDifferenceModel(String fieldName, String previousValue, String latestValue) {
        this.fieldName = fieldName;
        DiffMatchPatch dmp = new DiffMatchPatch();
        diffList = dmp.diffMain(previousValue, latestValue, false);
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
    public List<DiffMatchPatch.Diff> getDiffList() {
        return diffList;
    }
}
