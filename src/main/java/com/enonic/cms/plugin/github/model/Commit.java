/*
 * Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
 * ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.enonic.cms.plugin.github.model;

import java.util.Date;

public class Commit
{
    private String id;

    private String message;

    private String committed_date; // json required Date for de-serialization

    private String url; // path to commit

    private Author author;


    // calculated fields
    private String name;    // branch name
    private String address; // branch address in format username/repository/branch

    private String shortMessage; // message without ticket and info after new line

    private Date date; // committed date


    public Commit()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public String getCommitted_date()
    {
        return committed_date;
    }

    public void setCommitted_date( String committed_date )
    {
        this.committed_date = committed_date;
    }

    public String getShortMessage()
    {
        return shortMessage;
    }

    public void setShortMessage( String shortMessage )
    {
        this.shortMessage = shortMessage;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor( Author author )
    {
        this.author = author;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }
}
