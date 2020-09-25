package com.visitscotland.brmx.translation.difference.ui.model;

import org.assertj.core.api.Assertions;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleFieldDifferenceModelTest {
    @DisplayName("constructor - values are equal")
    @Test
    public void equalValues() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", "value", "value");

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(1);
        assertThat(model.getDiffList().get(0).operation).isEqualTo(DiffMatchPatch.Operation.EQUAL);
        assertThat(model.getDiffList().get(0).text).isEqualTo("value");
    }

    @DisplayName("constructor - all characters have changed")
    @Test
    public void allDifferent() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", "abcde", "fghij");

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(2);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "abcde"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "fghij"));
    }

    @DisplayName("constructor - one replacement")
    @Test
    public void replacement() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", "abcde", "abfghde");

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(4);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "ab"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "c"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "fgh"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "de"));
    }
}
