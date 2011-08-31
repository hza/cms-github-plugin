/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.model;

public class Author
{
    private String name;

    private String login;

    public Author()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }
}
