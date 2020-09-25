package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MultipleFieldTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void symmetrical_equals() {
        MultipleField value1 = createMultipleField(
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertThat(value1.equals(value1)).isTrue();
        assertThat(value1.hashCode()).isEqualTo(value1.hashCode());
        assertThat(value1.equals(new Object())).isFalse();
        assertThat(value1.equals(null)).isFalse();

        MultipleField value2 = createMultipleField(
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertThat(value1.equals(value2)).isTrue();
        assertThat(value2.equals(value1)).isTrue();
        assertThat(value1.hashCode()).isEqualTo(value2.hashCode());

        MultipleField nullValue = createMultipleField();
        assertThat(value1.equals(nullValue)).isFalse();
        assertThat(nullValue.equals(value1)).isFalse();
        assertThat(value1.hashCode()).isNotEqualTo(nullValue.hashCode());

        MultipleField emptyValue = new MultipleField();
        emptyValue.setField(new ArrayList<>());
        assertThat(value1.equals(emptyValue)).isFalse();
        assertThat(emptyValue.equals(value1)).isFalse();
        assertThat(value1.hashCode()).isNotEqualTo(emptyValue.hashCode());

        MultipleField diffSizeValue = createMultipleField(
                new FieldValue("doc1", "value1"));
        assertThat(value1.equals(diffSizeValue)).isFalse();
        assertThat(diffSizeValue.equals(value1)).isFalse();
        assertThat(value1.hashCode()).isNotEqualTo(diffSizeValue.hashCode());

        MultipleField diffValue = createMultipleField(
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2diff", "value2"));
        assertThat(value1.equals(diffValue)).isFalse();
        assertThat(diffValue.equals(value1)).isFalse();
        assertThat(value1.hashCode()).isNotEqualTo(diffValue.hashCode());
    }

    public MultipleField createMultipleField(FieldValue... values) {
        MultipleField field = new MultipleField();
        if (values.length > 0) {
            field.setField(Arrays.asList(values));
        }
        return field;
    }
}