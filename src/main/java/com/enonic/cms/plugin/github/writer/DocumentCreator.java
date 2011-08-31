/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.writer;

import org.jdom.Document;

import com.enonic.cms.plugin.github.document.StatisticsDocument;

public interface DocumentCreator
{
    Document createDocument( StatisticsDocument statisticsDocument );
}
