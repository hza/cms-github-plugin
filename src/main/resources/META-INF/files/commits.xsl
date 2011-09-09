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


        <style type="text/css">
            .plus {
                border-left: 1px solid #cccccc;
                border-right: 1px solid #cccccc;
                cursor: pointer;
                background: #f2f2f2;
                border-top: 1px solid #cccccc;
                padding: 3px;
                clear: both;
            }

            .row {
                border-left: 1px solid #cccccc;
                border-right: 1px solid #cccccc;
                display: none;
            }

            .cb {
                clear: both;
            }

            .marker {
                float: left;
                margin: 0 4px 0 4px;
                width: 20px;
            }

            .ticket {
                float:left;
                margin-left: 4px;
            }

            .txt {
                float: left;
                margin: 0 8px 0 8px;
                height: 18px;
                width: 700px;
                overflow: hidden;
            }

            .rtable {
                overflow: auto;
                margin-top: 20px;
                border-bottom: 1px solid #cccccc;
            }

            .bold {
                font-weight: bold;
            }

            .gray {
                color: gray;
            }

            .branch {
                margin-left: 20px;
                font-weight: bold;
                font-size: 120%;
            }

            .msg {
                white-space: pre-wrap;
                margin-left: 40px;
                font-family: 'Bitstream Vera Sans Mono','Courier New',monospace;
                font-size: 12px;
                padding-bottom: 8px;
            }
        </style>

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

                $('.plus').click(function(){
                    $(this).next().toggle();
                    return false;
                });
            });
        </script>


        <form name="v1" action="" method="get">
            <input id="branches_id" name="branches" type="hidden" value="" disabled="disabled"/>
            <div style="clear: both">
                <div style="float:left; font-weight: bold; font-size: 150%; width: 180px;">
                    <xsl:text>Search in commits : </xsl:text>
                </div>
                <input style="float:left; font-size: 150%; padding: 2px; margin-left: 20px;" type="text" name="tickets">
                    <xsl:attribute name="value"><xsl:value-of select="//querystring/parameter[@name='tickets']"/></xsl:attribute>
                </input>
                <input id="search" style="float:left; font-size: 150%; margin-left: 4px;" type="submit" value="I'm feeling Lucky"/>
            </div>
        </form>

        <div style="font-size: 95%; color: gray; padding-bottom: 10px; clear:both;">
            <xsl:text>You can use comma or space separated search subwords in format </xsl:text>
            <a href="#" style="border-bottom: 1px dashed blue; text-decoration: none;"
               onclick="document.forms.v1.tickets.value = 'user panel'; return false;">
                <xsl:text>user panel</xsl:text>
            </a>
        </div>

        <div class="cb">
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
            <div class="cb"><xsl:text> </xsl:text></div>
        </div>

        <div class="rtable">
            <xsl:for-each select="//github-plugin-document/tickets/ticket">
                <div class="plus">

                    <div class="ticket">
                        <a target="_blank" style="white-space: nowrap;">
                            <xsl:attribute name="href">
                                <xsl:text>https://www6.v1host.com/ENONIC01/assetdetail.v1?number=</xsl:text><xsl:value-of
                                    select="@code"/>
                            </xsl:attribute>

                            <xsl:value-of select="@code"/>
                        </a>
                    </div>

                    <xsl:call-template name="branches-output">
                        <xsl:with-param name="ticket" select="."/>
                    </xsl:call-template>

                    <div class="txt">
                        <xsl:value-of select="@message"/>
                    </div>

                    <div class="cb"><xsl:text> </xsl:text></div>
                </div>

                <xsl:call-template name="tickets-output">
                    <xsl:with-param name="ticket" select="."/>
                </xsl:call-template>

            </xsl:for-each>
        </div>
    </xsl:template>

    <xsl:template name="tickets-output">
        <xsl:param name="ticket"/>

        <div class="row">
            <xsl:for-each select="//github-plugin-document/branches/branch[@visible='true']">
                <xsl:call-template name="ticket-output">
                    <xsl:with-param name="ticket" select="$ticket"/>
                    <xsl:with-param name="branch" select="."/>
                </xsl:call-template>
            </xsl:for-each>
        </div>

    </xsl:template>

    <xsl:template name="ticket-output">
        <xsl:param name="ticket"/>
        <xsl:param name="branch"/>

        <xsl:variable name="commits" select="$ticket/commit[@branch = $branch/@address]" />

        <xsl:if test="$commits">
            <div class="branch">branch: <xsl:value-of select="$branch/@name"/></div>

            <xsl:for-each select="$commits">
                 <div class="msg"><b>Commit:</b> <xsl:text> </xsl:text>
                     <a target="_blank">
                         <xsl:attribute name="href">
                             <xsl:text>https://github.com</xsl:text><xsl:value-of select="@url"/>
                         </xsl:attribute>
                         <xsl:value-of select="@id"/>
                     </a>
                     <br/><b>Author:</b> <xsl:text> </xsl:text> <xsl:value-of select="@author"/>
                     <br/><b>Date:</b> <xsl:text> </xsl:text> <xsl:value-of select="@date"/><br/>
                     <br/>
                    <xsl:value-of select="."/>
                 </div>
            </xsl:for-each>

        </xsl:if>

    </xsl:template>

    <xsl:template name="branches-output">
        <xsl:param name="ticket"/>

        <div style="float: right">
            <xsl:for-each select="//github-plugin-document/branches/branch[@visible='true']">
                <xsl:call-template name="branch-output">
                    <xsl:with-param name="address" select="@address"/>
                    <xsl:with-param name="name" select="@name"/>
                    <xsl:with-param name="ticket" select="$ticket"/>
                </xsl:call-template>
            </xsl:for-each>
        </div>

    </xsl:template>

    <xsl:template name="branch-output">
        <xsl:param name="address"/>
        <xsl:param name="ticket"/>
        <xsl:param name="name"/>

        <div>
            <xsl:choose>
                <xsl:when test="$ticket/commit[@branch = $address]">
                    <xsl:attribute name="class">marker bold</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:attribute name="class">marker gray</xsl:attribute>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:value-of select="$name"/>
        </div>

    </xsl:template>
</xsl:stylesheet>