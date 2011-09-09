/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.writer;

import java.util.List;
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
        documentEl.addContent( branchesEl );



        Element ticketsEl = new Element( "tickets" );

        createTicketElement( ticketsEl, StatisticsDocument.UNRECOGNIZED_TICKET, statisticsDocument.getTickets().get( StatisticsDocument.UNRECOGNIZED_TICKET ) );

        for ( Map.Entry<String, List<Commit>[]> entry : statisticsDocument.getTickets().entrySet() )
        {
            if ( StatisticsDocument.UNRECOGNIZED_TICKET.equals( entry.getKey() ) )
            {
                continue;
            }

            createTicketElement( ticketsEl, entry.getKey(), entry.getValue() );
        }

        documentEl.addContent( ticketsEl );



        return new Document( documentEl );
    }

    private void createTicketElement( Element ticketsEl, String ticket, List<Commit>[] commitsList )
    {
        if (commitsList == null)
        {
            return;
        }

        Element ticketEl = new Element( "ticket" );
        ticketEl.setAttribute( "code", ticket );

        for ( List<Commit> commits : commitsList )
        {
            if ( commits == null )
            {
                continue;
            }

            String message = null;

            for (Commit commit : commits)
            {
                if ( message == null || !commit.getMessage().startsWith( "Merge branch" ) )
                {
                    message = commit.getShortMessage();
                }

                Element commitEl = new Element( "commit" );
                commitEl.setAttribute( "id", commit.getId() );
                commitEl.setAttribute( "branch", commit.getAddress() );
                commitEl.setAttribute( "url", commit.getUrl() );
                commitEl.setAttribute( "author", commit.getAuthor().getName() );
                commitEl.setAttribute( "date", commit.getCommitted_date() );
                commitEl.setAttribute( "login", commit.getAuthor().getLogin() );
                commitEl.setText( commit.getMessage() );
                ticketEl.addContent( commitEl );
            }

            ticketEl.setAttribute( "message", message );
        }

        ticketsEl.addContent( ticketEl );
    }
}
