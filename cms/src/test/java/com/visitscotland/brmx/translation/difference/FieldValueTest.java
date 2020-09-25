package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldValueTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void equals_symmetric() {
        FieldValue valid1 = new FieldValue("docBase", "value");
        assertThat(valid1.equals(valid1)).isTrue();
        assertThat(valid1.hashCode()).isEqualTo(valid1.hashCode());
        assertThat(valid1.equals(new Object())).isFalse();
        assertThat(valid1.equals(null)).isFalse();

        FieldValue valid2 = new FieldValue("docBase", "value");
        assertThat(valid1.equals(valid2)).isTrue();
        assertThat(valid2.equals(valid1)).isTrue();
        assertThat(valid1.hashCode()).isEqualTo(valid2.hashCode());

        FieldValue docBAseNull = new FieldValue(null, "value");
        assertThat(valid1.equals(docBAseNull)).isFalse();
        assertThat(docBAseNull.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(docBAseNull.hashCode());

        FieldValue docBaseDiff = new FieldValue("diff", "value");
        assertThat(valid1.equals(docBaseDiff)).isFalse();
        assertThat(docBaseDiff.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(docBaseDiff.hashCode());

        FieldValue valueNull = new FieldValue("docBase", null);
        assertThat(valid1.equals(valueNull)).isFalse();
        assertThat(valueNull.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(valueNull.hashCode());

        FieldValue valueDiff = new FieldValue("docBase", "diff");
        assertThat(valid1.equals(valueDiff)).isFalse();
        assertThat(valueDiff.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(valueDiff.hashCode());


        FieldValue allNull = new FieldValue(null, null);
        assertThat(valid1.equals(allNull)).isFalse();
        assertThat(allNull.equals(valid1)).isFalse();
        assertThat(valid1.hashCode()).isNotEqualTo(allNull.hashCode());
    }
}
