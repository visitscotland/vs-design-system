package com.visitscotland.brmx.translation.difference;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldValueTest {

    @Test
    @DisplayName("Symmetrical equals and hashcode")
    public void equals_symmetric() {
        FieldValue valid1 = new FieldValue("docBase", "value");
        assertTrue(valid1.equals(valid1));

        FieldValue valid2 = new FieldValue("docBase", "value");
        assertTrue(valid1.equals(valid2));
        assertTrue(valid2.equals(valid1));
        assertEquals(valid1.hashCode(), valid2.hashCode());

        FieldValue docBAseNull = new FieldValue(null, "value");
        assertFalse(valid1.equals(docBAseNull));
        assertFalse(docBAseNull.equals(valid1));
        assertNotEquals(valid1.hashCode(), docBAseNull.hashCode());

        FieldValue docBaseDiff = new FieldValue("diff", "value");
        assertFalse(valid1.equals(docBaseDiff));
        assertFalse(docBaseDiff.equals(valid1));
        assertNotEquals(valid1.hashCode(), docBaseDiff.hashCode());

        FieldValue valueNull = new FieldValue("docBase", null);
        assertFalse(valid1.equals(valueNull));
        assertFalse(valueNull.equals(valid1));
        assertNotEquals(valid1.hashCode(), valueNull.hashCode());

        FieldValue valueDiff = new FieldValue("docBase", "diff");
        assertFalse(valid1.equals(valueDiff));
        assertFalse(valueDiff.equals(valid1));
        assertNotEquals(valid1.hashCode(), valueDiff.hashCode());


        FieldValue allNull = new FieldValue(null, null);
        assertFalse(valid1.equals(allNull));
        assertFalse(allNull.equals(valid1));
        assertNotEquals(valid1.hashCode(), allNull.hashCode());
    }
}
