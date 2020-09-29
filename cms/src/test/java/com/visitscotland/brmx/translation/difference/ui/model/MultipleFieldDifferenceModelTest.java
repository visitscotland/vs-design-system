package com.visitscotland.brmx.translation.difference.ui.model;

import com.visitscotland.brmx.translation.difference.FieldValue;
import org.assertj.core.api.Assertions;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MultipleFieldDifferenceModelTest {
    @Test
    public void emptyToFull() {
        List<FieldValue> previous = new LinkedList<>();
        List<FieldValue> latest = new LinkedList<>();
        latest.add(new FieldValue("one"));
        latest.add(new FieldValue("two"));
        latest.add(new FieldValue("three"));
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "three"));
    }

    @Test
    public void fullToEmpty() {
        List<FieldValue> previous = new LinkedList<>();
        previous.add(new FieldValue("one"));
        previous.add(new FieldValue("two"));
        previous.add(new FieldValue("three"));
        List<FieldValue> latest = new LinkedList<>();
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "three"));
    }

    @Test
    public void same() {
        List<FieldValue> previous = new LinkedList<>();
        previous.add(new FieldValue("one"));
        previous.add(new FieldValue("two"));
        previous.add(new FieldValue("three"));
        List<FieldValue> latest = new LinkedList<>();
        latest.add(new FieldValue("one"));
        latest.add(new FieldValue("two"));
        latest.add(new FieldValue("three"));
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "three"));
    }

    @Test
    public void insert() {
        List<FieldValue> previous = new LinkedList<>();
        previous.add(new FieldValue("one"));
        previous.add(new FieldValue("two"));
        previous.add(new FieldValue("three"));
        List<FieldValue> latest = new LinkedList<>();
        latest.add(new FieldValue("zero"));
        latest.add(new FieldValue("one"));
        latest.add(new FieldValue("one point five"));
        latest.add(new FieldValue("two"));
        latest.add(new FieldValue("two point five"));
        latest.add(new FieldValue("three"));
        latest.add(new FieldValue("three point five"));
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "zero"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "one point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "two point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "three"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "three point five"));
    }

    @Test
    public void delete() {
        List<FieldValue> previous = new LinkedList<>();
        previous.add(new FieldValue("zero"));
        previous.add(new FieldValue("one"));
        previous.add(new FieldValue("one point five"));
        previous.add(new FieldValue("two"));
        previous.add(new FieldValue("two point five"));
        previous.add(new FieldValue("three"));
        previous.add(new FieldValue("three point five"));
        List<FieldValue> latest = new LinkedList<>();
        latest.add(new FieldValue("one"));
        latest.add(new FieldValue("two"));
        latest.add(new FieldValue("three"));
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "zero"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "one point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "two point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "three"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "three point five"));
    }

    @Test
    public void replacement() {
        List<FieldValue> previous = new LinkedList<>();
        previous.add(new FieldValue("zero"));
        previous.add(new FieldValue("one"));
        previous.add(new FieldValue("one point five"));
        previous.add(new FieldValue("two"));
        previous.add(new FieldValue("two point five"));
        previous.add(new FieldValue("three"));
        previous.add(new FieldValue("three point five"));
        List<FieldValue> latest = new LinkedList<>();
        latest.add(new FieldValue("zero point one"));
        latest.add(new FieldValue("one"));
        latest.add(new FieldValue("one point six"));
        latest.add(new FieldValue("two"));
        latest.add(new FieldValue("two point six"));
        latest.add(new FieldValue("three"));
        latest.add(new FieldValue("three point six"));
        MultipleFieldDifferenceModel model = new MultipleFieldDifferenceModel("fieldName", previous, latest);
        List<DiffMatchPatch.Diff> diffList = model.getDiffList();

        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "zero"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "zero point one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "one"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "one point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "one point six"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "two"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "two point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "two point six"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "three"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "three point five"));
        assertThat(diffList).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "three point six"));
    }
}
