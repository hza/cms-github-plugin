/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.util;

import org.junit.Assert;
import org.junit.Test;

public class GitHubDateTest
{
    @Test
    public void testToDate()
            throws Exception
    {
        Assert.assertNotNull( new GitHubDate( "2010-12-09T13:50:17-08:00" ).toDate() );
    }
}
