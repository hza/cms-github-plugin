package com.enonic.cms.plugin.github.util;

import java.util.Arrays;

public final class Strings
{
    private static final String SEPARATORS = "[\\s;,]";

    public static String[] split( String comma_separated_string )
    {
        String[] strings = comma_separated_string.split( SEPARATORS );

        int j = 0;

        for ( int i = 0; i < strings.length; i++ )
        {
            String trim = strings[i].trim();

            if ( !"".equals( trim ) )
            {
                strings[j++] = trim;
            }
        }

        return Arrays.copyOf( strings, j ) ;
    }

}
