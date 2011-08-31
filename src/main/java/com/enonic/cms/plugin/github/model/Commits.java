/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.model;

import java.util.List;

public class Commits
{
    private List<Commit> commits;

    public Commits()
    {
    }

    public List<Commit> getCommits()
    {
        return commits;
    }

    public void setCommits( List<Commit> commits )
    {
        this.commits = commits;
    }
}
