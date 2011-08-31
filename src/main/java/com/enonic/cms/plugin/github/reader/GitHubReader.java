/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.reader;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.enonic.cms.plugin.github.document.StatisticsDocument;
import com.enonic.cms.plugin.github.model.Branch;
import com.enonic.cms.plugin.github.model.Commit;
import com.enonic.cms.plugin.github.model.Commits;
import com.enonic.cms.plugin.github.util.Base64Coder;

/**
 * reads git branches using GitHub API
 */
public class GitHubReader
        implements Reader
{
    private static final Logger LOGGER = Logger.getLogger( "github-plugin" );

    private static final String GITHUB_API_LIST = "http://github.com/api/v2/json/commits/list/";

    private static final String PAGE_PARAM = "?page=";

    private final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

    private final String credentials;

    private final int pages;

    public GitHubReader( String username, String password, int depth )
    {
        mapper.configure( DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false );
        credentials = encode( username + ":" + password );
        this.pages = ( depth / 35 ) + 1;
    }

    /**
     * reads branches and populate document
     *
     * ignores errors
     *
     * @param document document
     */
    public void readBranches( StatisticsDocument document )
    {
        int branchNum = 0;
        for ( Branch branch : document.getBranches() )
        {
            for ( int page = 1; page < pages + 1; page++ )
            {
                String address = GITHUB_API_LIST + branch.getAddress() + PAGE_PARAM + page;

                List<Commit> commits = readBranchByAddress( address );

                if ( commits.isEmpty() )
                {
                    break;
                }
                document.addCommits( commits, branchNum );
            }
            branchNum++;
        }
    }

    /**
     * reads branch by URI address
     *
     * @param address address
     * @return commit list
     */
    private List<Commit> readBranchByAddress( String address )
    {
        try
        {
            URL url = new URL( address );

            URLConnection uc = url.openConnection();
            uc.setRequestProperty( "Authorization", "Basic " + credentials );
            InputStream content = uc.getInputStream();
            Commits commits = mapper.readValue( content, Commits.class );
            content.close();

            return commits.getCommits();
        }
        catch ( Exception e )
        {
            LOGGER.log( Level.SEVERE, "URI " + address + " reading or parsing failed !", e );

            return Arrays.<Commit>asList();
        }
    }

    /**
     * return base64 encoded string
     *
     * @param data - data to base64 encode
     * @return encoded string
     */
    private static String encode( String data )
    {
        // return new sun.misc.BASE64Encoder().encode( userPassword.getBytes() );
        return Base64Coder.encodeString( data );
    }

}
