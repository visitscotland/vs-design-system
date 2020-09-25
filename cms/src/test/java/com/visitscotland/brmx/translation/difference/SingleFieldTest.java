package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleFieldTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void symmetric_equlals() {
        SingleField valid1 = createSingleField( "docBase", "value");
        assertThat(valid1.equals(valid1)).isTrue();
        assertThat(valid1.hashCode()).isEqualTo(valid1.hashCode());
        assertThat(valid1.equals(new Object())).isFalse();
        assertThat(valid1.equals(null)).isFalse();

        SingleField valid2 = createSingleField( "docBase", "value");
        assertThat(valid1.equals(valid2)).isTrue();
        assertThat(valid2.equals(valid1)).isTrue();
        assertThat(valid1.hashCode()).isEqualTo(valid2.hashCode());

        SingleField fieldNull = new SingleField();
        assertThat(valid1.equals(fieldNull)).isFalse();
        assertThat(fieldNull.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(fieldNull.hashCode());

        SingleField fieldDiff = createSingleField("notSame", "notSame");
        assertThat(valid1.equals(fieldDiff)).isFalse();
        assertThat(fieldDiff.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(fieldDiff.hashCode());
    }

    public SingleField createSingleField(String docBase, String value) {
        SingleField field = new SingleField();
        FieldValue fieldValue = new FieldValue(docBase, value);
        field.setField(fieldValue);
        return field;
    }
}
