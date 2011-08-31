/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github;

import java.util.logging.Logger;

import org.jdom.Document;

import com.enonic.cms.plugin.github.document.V1StaticticsDocument;
import com.enonic.cms.plugin.github.reader.GitHubReader;
import com.enonic.cms.plugin.github.writer.DocumentCreator;
import com.enonic.cms.plugin.github.document.StatisticsDocument;
import com.enonic.cms.plugin.github.reader.Reader;
import com.enonic.cms.plugin.github.writer.XMLDocumentCreator;

public final class GitHubPlugin
{
    private static final Logger LOGGER = Logger.getLogger( "github-plugin" );

    /**
     * Returns document with commit statistics.
     *
     * @param username user name in VCS
     * @param password password
     * @param branches comma separated short names of branches
     * @param depth of analysis - count of commits to analyze for each branch. multiple unit is equal to 35
     * @param ticketsFilter - comma separated filter for tickets
     * @return XML document
     */
    public Document getStatistics( String username, String password, String names, String branches, int depth, String ticketsFilter, String branchesFilter )
    {
        LOGGER.info( "parameters: username: " + username + ", password: ***, branches: "
                             + branches + ", depth: " + depth + ", tickets-filter: '" + ticketsFilter + "', branches-filter: '" + branchesFilter + "'");

        long time = System.currentTimeMillis();

        StatisticsDocument statisticsDocument = new V1StaticticsDocument( names, branches, ticketsFilter, branchesFilter );

        Reader reader = new GitHubReader( username, password, depth );
        reader.readBranches( statisticsDocument );

        // dump document to console
        // System.out.println( statisticsDocument.toString() );

        DocumentCreator documentCreator = new XMLDocumentCreator();
        Document document = documentCreator.createDocument( statisticsDocument );

        LOGGER.info( "created XML document in " + (System.currentTimeMillis() - time) + " msec.");

        return document;
    }


}
