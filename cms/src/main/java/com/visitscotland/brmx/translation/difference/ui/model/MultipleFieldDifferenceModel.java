package com.visitscotland.brmx.translation.difference.ui.model;

import com.visitscotland.brmx.translation.difference.FieldValue;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.util.LinkedList;
import java.util.List;

public class MultipleFieldDifferenceModel implements FieldDifferenceModel {
    private String fieldName;
    private List<DiffMatchPatch.Diff> diffList;

    public MultipleFieldDifferenceModel(String fieldName, List<FieldValue> previous, List<FieldValue> latest) {
        this.fieldName = fieldName;
        diffList = linkedListDiff(previous, latest);
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean isMultiple() {
        return true;
    }

    @Override
    public List<DiffMatchPatch.Diff> getDiffList() {
        return diffList;
    }

    protected List<DiffMatchPatch.Diff> linkedListDiff(List<FieldValue> previous, List<FieldValue> latest) {
        // Produces a difference of an ordered list
        // Relies on the implementation of equals and hashcode in the FieldValue class
        LinkedList<FieldValue> previousList = new LinkedList<>(previous);
        LinkedList<FieldValue> latestList = new LinkedList<>(latest);

        List<DiffMatchPatch.Diff> diffList = new LinkedList<>();
        while ( previousList.size() > 0 ) {
            FieldValue firstValue = previousList.pollFirst();
            int indexInLatest = latestList.indexOf(firstValue);
            if ( indexInLatest < 0 ) {
                // cannot be found in the latest, has been deleted
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, firstValue.getValue()));
            } else if ( indexInLatest > 0 ) {
                // have items that have been inserted before the value
                FieldValue inserted;
                while (!(inserted = latestList.pollFirst()).equals(firstValue)) {
                    diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, inserted.getValue()));
                }
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, firstValue.getValue()));
            } else {
                // We have found the value in the same place.
                latestList.pollFirst();
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, firstValue.getValue()));
            }
        }

        while (latestList.size() > 0) {
            // Any item at the end of the latest list have been inserted
            FieldValue inserted = latestList.pollFirst();
            diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, inserted.getValue()));
        }

        return diffList;
    }
}
