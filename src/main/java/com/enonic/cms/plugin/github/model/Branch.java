package com.enonic.cms.plugin.github.model;

public class Branch
{
    private String name;
    private String address;
    private boolean visible;

    public Branch()
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public boolean getVisible()
    {
        return visible;
    }

    public void setVisible( boolean visible )
    {
        this.visible = visible;
    }
}
