package com.enonic.cms.plugin.github.document;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.TestCase;

public class V1StaticticsDocumentTest
        extends TestCase
{
    @Test
    public void testCtor()
    {
        V1StaticticsDocument v1StaticticsDocument =
                new V1StaticticsDocument( "4.4 4.5 4.6 5.0", "4.4 4.5 4.6 5.0", "", "4.6" );

        Assert.assertEquals( v1StaticticsDocument.getBranches().length, 4);
        Assert.assertFalse( v1StaticticsDocument.getBranches()[3].getVisible() );
    }
}
