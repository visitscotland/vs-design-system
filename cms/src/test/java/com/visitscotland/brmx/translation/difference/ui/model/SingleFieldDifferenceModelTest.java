package com.visitscotland.brmx.translation.difference.ui.model;

import com.visitscotland.brmx.translation.difference.FieldValue;
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
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", new FieldValue("value"), new FieldValue("value"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(1);
        assertThat(model.getDiffList().get(0).operation).isEqualTo(DiffMatchPatch.Operation.EQUAL);
        assertThat(model.getDiffList().get(0).text).isEqualTo("value");
    }

    @DisplayName("constructor - all characters have changed")
    @Test
    public void allDifferent() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", new FieldValue("abcde"), new FieldValue("fghij"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(2);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "abcde"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "fghij"));
    }

    @DisplayName("constructor - one replacement")
    @Test
    public void replacement() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName", new FieldValue("abcde"), new FieldValue("abfghde"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(4);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "ab"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "c"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "fgh"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "de"));
    }

    @DisplayName("constructor - with file")
    @Test
    public void file() {
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName",
                new FieldValue("abcd", "file1"), new FieldValue("ghjy", "file2"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(2);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "file1"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "file2"));
    }

    @DisplayName("constructor - with file but missing docBase on previous")
    @Test
    public void file_previousNoDocBase() {
        // Should be treated as a text comparison
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName",
                new FieldValue("file1"), new FieldValue("ghjy", "file2"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(3);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "file"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "1"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "2"));
    }

    @DisplayName("constructor - with file but missing docBase on latest")
    @Test
    public void file_latestNoDocBase() {
        // Should be treated as a text comparison
        SingleFieldDifferenceModel model = new SingleFieldDifferenceModel("fieldName",
                new FieldValue("abcd", "file1"), new FieldValue("file2"));

        assertThat(model.getFieldName()).isEqualTo("fieldName");
        assertThat(model.getDiffList()).isNotNull().isNotEmpty().hasSize(3);

        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "file"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "1"));
        assertThat(model.getDiffList()).contains(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "2"));
    }
}
