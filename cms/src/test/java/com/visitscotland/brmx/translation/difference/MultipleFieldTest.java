package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultipleFieldTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void symmetrical_equals() {
        MultipleField value1 = createMultipleField("displayName",
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertTrue(value1.equals(value1));

        MultipleField value2 = createMultipleField("displayName",
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertTrue(value1.equals(value2));
        assertTrue(value2.equals(value1));
        assertEquals(value1.hashCode(), value2.hashCode());

        MultipleField nullValue = createMultipleField("displayName");
        assertFalse(value1.equals(nullValue));
        assertFalse(nullValue.equals(value1));
        assertNotEquals(value1.hashCode(), nullValue.hashCode());

        MultipleField emptyValue = new MultipleField();
        emptyValue.setDisplayName("displayName");
        emptyValue.setField(new ArrayList<>());
        assertFalse(value1.equals(emptyValue));
        assertFalse(emptyValue.equals(value1));
        assertNotEquals(value1.hashCode(), emptyValue.hashCode());

        MultipleField diffSizeValue = createMultipleField("displayName",
                new FieldValue("doc1", "value1"));
        assertFalse(value1.equals(diffSizeValue));
        assertFalse(diffSizeValue.equals(value1));
        assertNotEquals(value1.hashCode(), diffSizeValue.hashCode());

        MultipleField diffValue = createMultipleField("displayName",
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2diff", "value2"));
        assertFalse(value1.equals(diffValue));
        assertFalse(diffValue.equals(value1));
        assertNotEquals(value1.hashCode(), diffValue.hashCode());

        MultipleField displayNameNull = createMultipleField(null,
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertFalse(value1.equals(displayNameNull));
        assertFalse(displayNameNull.equals(value1));
        assertNotEquals(value1.hashCode(), displayNameNull.hashCode());

        MultipleField displayNameDiff = createMultipleField("diff",
                new FieldValue("doc1", "value1"),
                new FieldValue("doc2", "value2"));
        assertFalse(value1.equals(displayNameDiff));
        assertFalse(displayNameDiff.equals(value1));
        assertNotEquals(value1.hashCode(), displayNameDiff.hashCode());

    }

    public MultipleField createMultipleField(String displayName, FieldValue... values) {
        MultipleField field = new MultipleField();
        field.setDisplayName(displayName);
        if (values.length > 0) {
            field.setField(Arrays.asList(values));
        }
        return field;
    }
}