package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class FieldDifferenceTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void equals_symmetric() {
        FieldDifference diff1 = new FieldDifference();
        diff1.setProperty("property");
        diff1.setCaption("caption");
        diff1.setPrevious(createField("previous"));
        diff1.setLatest(createField("latest"));

        assertThat(diff1.equals(diff1)).isTrue();
        assertThat(diff1.hashCode()).isEqualTo(diff1.hashCode());
        assertThat(diff1.equals(null)).isFalse();
        assertThat(diff1.equals(new Object())).isFalse();

        FieldDifference diff2 = new FieldDifference();
        diff2.setProperty("property");
        diff2.setCaption("caption");
        diff2.setPrevious(createField("previous"));
        diff2.setLatest(createField("latest"));

        assertThat(diff1.equals(diff2)).isTrue();
        assertThat(diff2.equals(diff1)).isTrue();
        assertThat(diff1.hashCode()).isEqualTo(diff2.hashCode());

        // When the property is different
        FieldDifference diffNullProperty = new FieldDifference();
        diffNullProperty.setProperty(null);
        diffNullProperty.setCaption("caption");
        diffNullProperty.setPrevious(createField("previous"));
        diffNullProperty.setLatest(createField("latest"));

        assertThat(diff1.equals(diffNullProperty)).isFalse();
        assertThat(diffNullProperty.equals(diff1)).isFalse();
        assertThat(diff1.hashCode()).isNotEqualTo(diffNullProperty.hashCode());

        // When the caption is different
        FieldDifference diffCaptionProperty = new FieldDifference();
        diffCaptionProperty.setProperty("property");
        diffCaptionProperty.setCaption(null);
        diffCaptionProperty.setPrevious(createField("previous"));
        diffCaptionProperty.setLatest(createField("latest"));

        assertThat(diff1.equals(diffCaptionProperty)).isFalse();
        assertThat(diffCaptionProperty.equals(diff1)).isFalse();
        assertThat(diff1.hashCode()).isNotEqualTo(diffCaptionProperty.hashCode());

        // When the previous is not equal
        FieldDifference diffPreviousNotEqual = new FieldDifference();
        diffPreviousNotEqual.setProperty("property");
        diffPreviousNotEqual.setCaption("caption");
        diffPreviousNotEqual.setPrevious(createField("previousDiff"));
        diffPreviousNotEqual.setLatest(createField("latest"));

        assertThat(diff1.equals(diffPreviousNotEqual)).isFalse();
        assertThat(diffPreviousNotEqual.equals(diff1)).isFalse();
        assertThat(diff1.hashCode()).isNotEqualTo(diffPreviousNotEqual.hashCode());

        // When the latest is not equal
        FieldDifference diffLatestNotEqual = new FieldDifference();
        diffLatestNotEqual.setProperty("property");
        diffLatestNotEqual.setCaption("caption");
        diffLatestNotEqual.setPrevious(createField("previous"));
        diffLatestNotEqual.setLatest(createField("latestDiff"));

        assertThat(diff1.equals(diffLatestNotEqual)).isFalse();
        assertThat(diffLatestNotEqual.equals(diff1)).isFalse();
        assertThat(diff1.hashCode()).isNotEqualTo(diffLatestNotEqual.hashCode());
    }

    public Field createField(String value) {
        SingleField field = new SingleField();
        field.setField(new FieldValue(value));
        return field;
    }

    @Test
    public void isMultiple() {
        FieldDifference latestIsNull = new FieldDifference();
        assertThat(latestIsNull.isMultiple()).isFalse();

        FieldDifference latestIsMultiple = new FieldDifference();
        latestIsMultiple.setLatest(new MultipleField());
        assertThat(latestIsMultiple.isMultiple()).isTrue();

        FieldDifference latestIsSingle = new FieldDifference();
        latestIsSingle.setLatest(new SingleField());
        assertThat(latestIsSingle.isMultiple()).isFalse();
    }
}
