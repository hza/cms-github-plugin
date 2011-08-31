/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.enonic.cms.plugin.github.model.Commit;
import com.enonic.cms.plugin.github.util.Strings;
import com.enonic.cms.plugin.github.model.Branch;
import com.enonic.cms.plugin.github.util.GitHubDate;

/**
 * collects messages in format 'B-XXXXX: message' in specified branches.
 * tickets may be filtered
 *
 */
public class V1StaticticsDocument
        implements StatisticsDocument
{
    private static final String PATTERN_STRING = "^\\s*([BbDd]-\\d{4,5})\\s*:?\\s*([^\\n]*)\\n?.*$";

    private static final Pattern PATTERN =
            Pattern.compile( PATTERN_STRING, Pattern.MULTILINE | Pattern.UNIX_LINES | Pattern.DOTALL );

    private final Branch[] branches;

    private final Set<String> ticketsFilter;
    private final Set<String> branchesFilter;

    // ticket -> github in branches. map hold most fresh commit
    private final Map<String, Commit[]> tickets = new HashMap<String, Commit[]>();


    public V1StaticticsDocument( String comma_separated_names, String comma_separated_addresses,
                                 String comma_separated_tickets_filters, String comma_separated_branches_filters )
    {
        ticketsFilter = new HashSet<String>( Arrays.asList( Strings.split( comma_separated_tickets_filters ) ) );
        branchesFilter = new HashSet<String>( Arrays.asList( Strings.split( comma_separated_branches_filters ) ) );

        String[] branchNames = Strings.split( comma_separated_names );
        String[] branchAdresses = Strings.split( comma_separated_addresses );

        branches = new Branch[branchAdresses.length];

        for (int i = 0; i < branches.length; i++)
        {
            Branch branch = new Branch();
            branch.setName( branchNames[i] );
            branch.setAddress( branchAdresses[i] );
            branch.setVisible( branchesFilter.isEmpty() ||  branchesFilter.contains( branchNames[i] ) );
            branches[i] = branch;
        }
    }

    public void addCommits( List<Commit> commitList, int branch )
    {
        for ( Commit commit : commitList )
        {
            String message = commit.getMessage();

            Matcher matcher = PATTERN.matcher( message );

            if ( matcher.matches() )
            {
                String ticket = matcher.group( 1 );
                String shortMessage = matcher.group( 2 );

                if ( !ticketsFilter.isEmpty() && !ticketsFilter.contains( ticket ))
                {
                    continue;
                }

                Commit[] commitArray = tickets.get( ticket );

                if ( commitArray == null )
                {
                    commitArray = new Commit[branches.length];
                    tickets.put( ticket, commitArray );
                }

                if ( commitArray[branch] == null )
                {
                    commit.setShortMessage( shortMessage );
                    commit.setDate( new GitHubDate( commit.getCommitted_date() ).toDate() );
                    commit.setName( branches[branch].getName() );
                    commit.setAddress( branches[branch].getAddress() );
                    commitArray[branch] = commit;
                }
            }
        }

    }

    @Override
    public Branch[] getBranches()
    {
        return branches;
    }

    @Override
    public Map<String, Commit[]> getTickets()
    {
        return tickets;
    }

    @Override
    public String toString()
    {
        StringBuffer stringBuffer = new StringBuffer();

        for ( Map.Entry<String, Commit[]> stat : tickets.entrySet() )
        {
            stringBuffer.append( stat.getKey() );

            for ( Commit commit : stat.getValue() )
            {
                stringBuffer.append( commit == null ? "  -  " : "  +  " );
            }

            stringBuffer.append( "\n" );
        }

        return stringBuffer.toString();
    }

}
