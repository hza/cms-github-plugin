/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.document;

import java.util.List;
import java.util.Map;

import com.enonic.cms.plugin.github.model.Branch;
import com.enonic.cms.plugin.github.model.Commit;

public interface StatisticsDocument
{
    String UNRECOGNIZED_TICKET = "B-00000";

    void addCommits( List<Commit> commits, int branch );

    Map<String, List<Commit>[]> getTickets();

    Branch[] getBranches();
}
