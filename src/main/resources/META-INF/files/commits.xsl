<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ Copyright (c) 2011, Enonic and/or its affiliates. All rights reserved.
  ~ ENONIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  -->

<!DOCTYPE xsl:stylesheet [<!ENTITY nbsp '&#160;'>
        ]>
<xsl:stylesheet version="2.0" xmlns="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">
        <form name="v1" action="" method="get">
            <input id="branches_id" name="branches" type="hidden" value="" disabled="disabled"/>
            <div style="clear: both">
                <div style="float:left; font-weight: bold; font-size: 150%; width: 180px;">
                    <xsl:text>Search for V1 tickets : </xsl:text>
                </div>
                <input style="float:left; font-size: 150%; padding: 2px; margin-left: 20px;" type="text" name="tickets">
                    <xsl:attribute name="value"><xsl:value-of select="//querystring/parameter[@name='tickets']"/></xsl:attribute>
                </input>
                <input id="search" style="float:left; font-size: 150%; margin-left: 4px;" type="submit" value="I'm feeling Lucky"/>
            </div>
        </form>

        <div style="font-size: 95%; color: gray; padding-bottom: 10px; clear:both;">
            <xsl:text>You can use comma or space separated tickets in format </xsl:text>
            <a href="#" style="border-bottom: 1px dashed blue; text-decoration: none;"
               onclick="document.forms.v1.tickets.value = 'D-01784 B-01758'; return false;">
                <xsl:text>D-01784 B-01758</xsl:text>
            </a>
        </div>

        <div style="clear: both">
            <div style="float:left; font-weight: bold; font-size: 100%; width: 200px;">
                <xsl:text>Branches : </xsl:text>
            </div>

            <div style="float:left">
                <xsl:for-each select="//github-plugin-document/branches/branch">

                    <input style="float: none; margin: 0; " type="checkbox" class="branch">
                        <xsl:attribute name="id"><xsl:text>b</xsl:text><xsl:value-of select="replace(@name, '\.', '')" /></xsl:attribute>
                        <xsl:attribute name="value"><xsl:value-of select="@name" /></xsl:attribute>
                        <xsl:if test="@visible = 'true'">
                            <xsl:attribute name="checked"><xsl:text>checked</xsl:text></xsl:attribute>
                        </xsl:if>
                    </input>

                    <label style="float: none;  font-weight: normal; padding: 0px 12px 0 4px;">
                        <xsl:attribute name="for"><xsl:text>b</xsl:text><xsl:value-of select="replace(@name, '\.', '')" /></xsl:attribute>
                        <xsl:value-of select="@name"/>
                    </label>

                </xsl:for-each>
            </div>
        </div>

        <script type="text/javascript">
            jQuery(function($){
                $('#search').click(function(){
                    var branches = [];
                    var length = $('.branch').each(function(){
                        if (this.checked){
                            branches.push(this.value);
                        }
                    }).length;

                    if (length != branches.length) {
                        $('#branches_id').val(branches.join(' ')).attr('disabled', '');
                    }
                });
            });
        </script>

        <br/>
        <br/>


        <table style="border: 1px dashed blue; ">
            <tr>
                <th>Ticket</th>
                <th>Description</th>

                <xsl:for-each select="//github-plugin-document/branches/branch[@visible='true']">
                    <th><xsl:value-of select="@name"/></th>
                </xsl:for-each>

            </tr>
            <xsl:for-each select="//github-plugin-document/tickets/ticket">
                <tr>
                    <td>
                        <a target="_blank" style="white-space: nowrap;">
                            <xsl:attribute name="href">
                                <xsl:text>https://www6.v1host.com/ENONIC01/assetdetail.v1?number=</xsl:text><xsl:value-of
                                    select="@code"/>
                            </xsl:attribute>
                            <xsl:value-of select="@code"/>
                        </a>
                    </td>

                    <td>
                        <xsl:value-of select="@message"/>
                    </td>

                    <xsl:call-template name="branches-output">
                        <xsl:with-param name="ticket" select="."/>
                    </xsl:call-template>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template name="branches-output">
        <xsl:param name="ticket"/>

        <xsl:for-each select="//github-plugin-document/branches/branch[@visible='true']">
            <xsl:call-template name="branch-output">
                <xsl:with-param name="address" select="@address"/>
                <xsl:with-param name="ticket" select="$ticket"/>
            </xsl:call-template>
        </xsl:for-each>

    </xsl:template>

    <xsl:template name="branch-output">
        <xsl:param name="address"/>
        <xsl:param name="ticket"/>

        <td>
            <xsl:for-each select="$ticket/commit[@branch = $address]">
                <a target="_blank">
                    <xsl:attribute name="href">
                        <xsl:text>https://github.com</xsl:text><xsl:value-of select="@url"/>
                    </xsl:attribute>
                    <xsl:attribute name="title">
                        <xsl:text> Author: </xsl:text><xsl:value-of select="@author"/>
                        <xsl:text> </xsl:text>
                        <xsl:text> Date: </xsl:text><xsl:value-of select="@date"/>
                        <xsl:text> </xsl:text>
                        <xsl:text> </xsl:text><xsl:value-of select="."/>
                    </xsl:attribute>
                    <xsl:text>yes</xsl:text>
                </a>
            </xsl:for-each>

            <xsl:if test="not($ticket/commit[@branch = $address])">
                <xsl:text>no</xsl:text>
            </xsl:if>
        </td>

    </xsl:template>
</xsl:stylesheet>