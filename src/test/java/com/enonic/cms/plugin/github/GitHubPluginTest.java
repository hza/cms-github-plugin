/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitHubPluginTest
{
    private static final Logger LOGGER = Logger.getLogger( "test" );

    private final Properties properties = new Properties();

    @Test
    public void testGetDocument()
            throws Exception
    {
        GitHubPlugin gitHubPlugin = new GitHubPlugin();

        Document document = gitHubPlugin.getStatistics(
              properties.getProperty( "username" ),
              properties.getProperty( "password" ),
              properties.getProperty( "branchNames" ),
              properties.getProperty( "branchAddresses" ),
              100,
              "",
              "4.4 5.0"
        );

        Assert.assertNotNull( document );
/*
        XMLOutputter output = new XMLOutputter( Format.getPrettyFormat());
        String xmlString = output.outputString(document);
        System.out.println(xmlString);
*/
    }

    @Before
    public void load()
    {
        InputStream is = getClass().getClassLoader().getResourceAsStream( "github-plugin.properties" );

        try
        {
            properties.load(new BufferedInputStream(is));
        }
        catch ( Exception e )
        {
            LOGGER.info( "using default setting !" );

            properties.put( "username", "anonymous" );
            properties.put( "password", "" );
            properties.put( "branchNames", "4.6;5.0" );
            properties.put( "branchAddresses", "enonic/cms-ce/master;enonic/cms-ce/5.0" );
        }
    }
}
