package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingleFieldTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void symmetric_equlals() {
        SingleField valid1 = createSingleField( "docBase", "value");
        assertTrue(valid1.equals(valid1));

        SingleField valid2 = createSingleField( "docBase", "value");
        assertTrue(valid1.equals(valid2));
        assertTrue(valid2.equals(valid1));
        assertEquals(valid1.hashCode(), valid2.hashCode());

        SingleField displayNameNull = createSingleField( "docBase", "value");
        assertFalse(valid1.equals(displayNameNull));
        assertFalse(displayNameNull.equals(valid1));
        assertNotEquals(valid1.hashCode(), displayNameNull.hashCode());

        SingleField displayNameDiff = createSingleField( "docBase", "value");
        assertFalse(valid1.equals(displayNameDiff));
        assertFalse(displayNameDiff.equals(valid1));
        assertNotEquals(valid1.hashCode(), displayNameDiff.hashCode());

        SingleField fieldNull = new SingleField();
        assertFalse(valid1.equals(fieldNull));
        assertFalse(fieldNull.equals(valid1));
        assertNotEquals(valid1.hashCode(), fieldNull.hashCode());

        SingleField fieldDiff = createSingleField("notSame", "notSame");
        assertFalse(valid1.equals(fieldDiff));
        assertFalse(fieldDiff.equals(valid1));
        assertNotEquals(valid1.hashCode(), fieldDiff.hashCode());
    }

    public SingleField createSingleField(String docBase, String value) {
        SingleField field = new SingleField();
        FieldValue fieldValue = new FieldValue(docBase, value);
        field.setField(fieldValue);
        return field;
    }
}
