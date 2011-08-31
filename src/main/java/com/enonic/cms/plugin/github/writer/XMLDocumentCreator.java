/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.writer;

import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.enonic.cms.plugin.github.model.Commit;
import com.enonic.cms.plugin.github.model.Branch;
import com.enonic.cms.plugin.github.document.StatisticsDocument;

public class XMLDocumentCreator
        implements DocumentCreator
{
    public Document createDocument( StatisticsDocument statisticsDocument )
    {
        Element documentEl = new Element( "github-plugin-document" );

        Element branchesEl = new Element( "branches" );

        Branch[] branches = statisticsDocument.getBranches();

        for ( Branch branch : branches )
        {
            Element branchEl = new Element( "branch" );
            branchEl.setAttribute( "name", branch.getName() );
            branchEl.setAttribute( "address", branch.getAddress() );
            branchEl.setAttribute( "visible", "" + branch.getVisible() );
            branchesEl.addContent( branchEl );
        }

        Element ticketsEl = new Element( "tickets" );

        for ( Map.Entry<String, Commit[]> entry : statisticsDocument.getTickets().entrySet() )
        {
            Element ticketEl = new Element( "ticket" );
            ticketEl.setAttribute( "code", entry.getKey() );

            for ( Commit commit : entry.getValue() )
            {
                if (commit != null)
                {
                    ticketEl.setAttribute( "message", commit.getShortMessage() );

                    Element commitEl = new Element( "commit" );
                    commitEl.setAttribute( "branch", commit.getAddress() );
                    commitEl.setAttribute( "url", commit.getUrl() );
                    commitEl.setAttribute( "author", commit.getAuthor().getName() );
                    commitEl.setAttribute( "date", commit.getCommitted_date() );
                    commitEl.setAttribute( "login", commit.getAuthor().getLogin() );
                    commitEl.setText( commit.getMessage() );
                    ticketEl.addContent( commitEl );
                }
            }

            ticketsEl.addContent( ticketEl );
        }

        documentEl.addContent( branchesEl );
        documentEl.addContent( ticketsEl );


        return new Document( documentEl );
    }
}
