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
    void addCommits( List<Commit> commits, int branch );

    Map<String, Commit[]> getTickets();

    Branch[] getBranches();
}
