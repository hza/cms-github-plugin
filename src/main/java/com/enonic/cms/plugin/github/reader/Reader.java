/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.reader;

import com.enonic.cms.plugin.github.document.StatisticsDocument;

public interface Reader
{
    void readBranches( StatisticsDocument document );
}
