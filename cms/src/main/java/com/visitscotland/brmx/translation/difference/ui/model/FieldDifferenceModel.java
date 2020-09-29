package com.visitscotland.brmx.translation.difference.ui.model;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.List;

public interface FieldDifferenceModel {
    String getFieldName();
    boolean isMultiple();
    boolean isFile();
    List<DiffMatchPatch.Diff> getDiffList();
}
