package com.visitscotland.brxm.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks methods that cannot be tested because they use functionality that requires from the CMS to be running
 */
@Target(ElementType.METHOD)
public @interface NonTestable {}
