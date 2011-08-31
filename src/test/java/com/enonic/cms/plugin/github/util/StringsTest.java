package com.enonic.cms.plugin.github.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringsTest
        extends TestCase
{
    public void testSplit()
            throws Exception
    {
        String[] split = Strings.split( "1 , 2, 3; 4 5  6" );
        Assert.assertEquals( split.length, 6 );
    }
}
