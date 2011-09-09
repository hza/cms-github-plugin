/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.document;

import java.util.ArrayList;
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
    private static final String PATTERN_STRING = ".*?([BbDdS]-\\d{5,6})\\s*:?\\s*([^\\n]{8,})\\n?.*?";

    static final Pattern PATTERN =
            Pattern.compile( PATTERN_STRING, Pattern.MULTILINE | Pattern.UNIX_LINES | Pattern.DOTALL );

    private final Branch[] branches;

    private final Set<String> ticketsFilter;
    private final Set<String> branchesFilter;

    // ticket -> github in branches. map hold most fresh commit
    private final Map<String, List<Commit>[]> tickets = new HashMap<String, List<Commit>[]>();


    public V1StaticticsDocument( String comma_separated_names,
                                 String comma_separated_addresses,
                                 String comma_separated_depthes,
                                 String comma_separated_tickets_filters,
                                 String comma_separated_branches_filters )
    {
        ticketsFilter = new HashSet<String>( Arrays.asList( Strings.split( comma_separated_tickets_filters ) ) );
        branchesFilter = new HashSet<String>( Arrays.asList( Strings.split( comma_separated_branches_filters ) ) );

        String[] branchNames = Strings.split( comma_separated_names );
        String[] branchAdresses = Strings.split( comma_separated_addresses );
        String[] branchDepthes = Strings.split( comma_separated_depthes );

        branches = new Branch[branchAdresses.length];

        for (int i = 0; i < branches.length; i++)
        {
            int depth = Integer.parseInt(branchDepthes[i]);
            int pages = 1 + depth / 35;

            Branch branch = new Branch();
            branch.setName( branchNames[i] );
            branch.setAddress(branchAdresses[i]);
            branch.setPages(pages);
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

            String ticket;
            String shortMessage;

            if ( matcher.matches() )
            {
                ticket = matcher.group( 1 );
                shortMessage = matcher.group( 2 );
            }
            else
            {
                ticket = StatisticsDocument.UNRECOGNIZED_TICKET;
                shortMessage = "*** Commits that belong to unrecognized tickets ***";
            }

            boolean found = true;

            for (String filter : ticketsFilter)
            {
                found = commit.getMessage().contains( filter );

                if ( !found )
                {
                    break;
                }
            }

            if (!found)
            {
                continue;
            }

            List<Commit>[] commitArray = tickets.get( ticket );

            if ( commitArray == null )
            {
                commitArray = new List[branches.length];
                tickets.put( ticket, commitArray );
            }

            List<Commit> commits = commitArray[branch] == null ? new ArrayList<Commit>() : commitArray[branch];

            commit.setShortMessage( shortMessage );
            commit.setDate( new GitHubDate( commit.getCommitted_date() ).toDate() );
            commit.setName( branches[branch].getName() );
            commit.setAddress( branches[branch].getAddress() );

            commits.add( commit );

            commitArray[branch] = commits;

        }

    }

    @Override
    public Branch[] getBranches()
    {
        return branches;
    }

    @Override
    public Map<String, List<Commit>[]> getTickets()
    {
        return tickets;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for ( Map.Entry<String, List<Commit>[]> stat : tickets.entrySet() )
        {
            builder.append(stat.getKey());

            for ( List<Commit> commit : stat.getValue() )
            {
                builder.append(commit == null || commit.isEmpty() ? "  -  " : "  +  ");
            }

            builder.append("\n");
        }

        return builder.toString();
    }

}
