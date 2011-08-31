/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * parses dates returned from github.
 *
 * notes: SimpleDateFormat in Java 7 supports github format directly. ( X instead of Z )
 */
public class GitHubDate
{
    private final Date date;

    public GitHubDate( String dateStr )
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );

        String[] parts = dateStr.split( ":" );
        String date2 = parts[0] + ":" + parts[1] + ":" + parts[2] + parts[3];

        Date tmpDate;

        try
        {
            tmpDate = simpleDateFormat.parse( date2 );
        }
        catch ( ParseException e )
        {
            tmpDate = null;
        }

        date = tmpDate;
    }

    public Date toDate()
    {
        return date;
    }
}
