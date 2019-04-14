package com.cayuse.coding.geremora;


import com.cayuse.coding.geremora.helpers.ValidationHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;


public class HelperTest {

    @Test
    public void testValidateZipCode()
    {

        Assert.assertTrue(ValidationHelper.validateZipCode("33101"));
        Assert.assertTrue(ValidationHelper.validateZipCode("83701"));
        Assert.assertFalse(ValidationHelper.validateZipCode("122"));
        Assert.assertFalse(ValidationHelper.validateZipCode(""));
        Assert.assertFalse(ValidationHelper.validateZipCode("343439"));
    }


}
