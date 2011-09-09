package com.enonic.cms.plugin.github.document;

import java.util.regex.Matcher;

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
                new V1StaticticsDocument( "4.4 4.5 4.6 5.0", "4.4 4.5 4.6 5.0", "100;100;100;100", "", "4.6" );

        Assert.assertEquals( v1StaticticsDocument.getBranches().length, 4);
        Assert.assertFalse( v1StaticticsDocument.getBranches()[3].getVisible() );
    }

    @Test
    public void testRE()
    {
        Matcher matcher;

        matcher = V1StaticticsDocument.PATTERN.matcher( "heloo\nD-01782: Admin: Image and File form" );
        assertTrue ( matcher.matches() );

        matcher = V1StaticticsDocument.PATTERN.matcher( "D-01782: Admin: Image and File form\nforgot dfvfd dfv" );
        assertTrue ( matcher.matches() );



    }

}
