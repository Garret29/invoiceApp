<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output
            encoding='UTF-8'
            indent='yes'
            method='html'
            version='1.0'/>
    <xsl:decimal-format name="pln" decimal-separator="," grouping-separator=" "/>
    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
            </head>
            <body style="font-family:Liberation Sans">
                <xsl:for-each select="faktura">
                    <p align="right">
                        Data wystawienia:
                        <xsl:if test="data_wystawienia !='' ">
                            <xsl:value-of select="data_wystawienia"/>
                        </xsl:if>
                        <br/>
                        Miejsce wystawienia:
                        <xsl:if test="miejscowosc_wystawienia !='' ">
                            <xsl:value-of select="miejscowosc_wystawienia"/>
                        </xsl:if>
                    </p>
                    <h2>Faktura nr&#160;
                        <xsl:if test="id !='' ">
                            <xsl:value-of select="id"/>
                        </xsl:if>
                    </h2>
                </xsl:for-each>

                <table border="1" align="left" width="600">
                    <tr>
                        <td>
                            <p align="left">Sprzedawca:</p>
                            <br/>
                            <p align="center">
                                <xsl:for-each select="faktura/dostawca">
                                    <xsl:if test="nazwa !='' ">
                                        <xsl:value-of select="nazwa"/>
                                    </xsl:if>
                                    <br/>
                                    <xsl:if test="kod !='' ">
                                        <xsl:value-of select="kod"/>&#160;
                                    </xsl:if>
                                    <xsl:if test="miasto !='' ">
                                        <xsl:value-of select="miasto"/>,
                                        <br/>
                                    </xsl:if>
                                    <xsl:if test="ulica !='' ">
                                        <xsl:value-of select="ulica"/>&#160;
                                    </xsl:if>
                                    <xsl:if test="dom !='' ">
                                        <xsl:value-of select="dom"/>
                                    </xsl:if>
                                    <xsl:if test="lokal !='' ">
                                        /<xsl:value-of select="lokal"/>
                                    </xsl:if>
                                    <br/>
                                    NIP:
                                    <xsl:if test="NIP_PESEL !='' ">
                                        <xsl:value-of select="NIP_PESEL"/>
                                    </xsl:if>
                                    <br/>
                                </xsl:for-each>
                            </p>
                        </td>
                        <td>
                            <p align="left">Nabywca:</p>
                            <br/>
                            <p align="center">
                                <xsl:for-each select="faktura/nabywca">
                                    <xsl:if test="nazwa !='' ">
                                        <xsl:value-of select="nazwa"/>
                                    </xsl:if>
                                    <br/>
                                    <xsl:if test="kod !='' ">
                                        <xsl:value-of select="kod"/>&#160;
                                    </xsl:if>
                                    <xsl:if test="miasto !='' ">
                                        <xsl:value-of select="miasto"/>,
                                        <br/>
                                    </xsl:if>
                                    <xsl:if test="ulica !='' ">
                                        <xsl:value-of select="ulica"/>&#160;
                                    </xsl:if>
                                    <xsl:if test="dom !='' ">
                                        <xsl:value-of select="dom"/>
                                    </xsl:if>
                                    <xsl:if test="lokal !='' ">
                                        /<xsl:value-of select="lokal"/>
                                    </xsl:if>
                                    <br/>
                                    NIP:
                                    <xsl:if test="NIP_PESEL !='' ">
                                        <xsl:value-of select="NIP_PESEL"/>
                                    </xsl:if>
                                    <br/>
                                </xsl:for-each>
                            </p>
                        </td>
                    </tr>
                </table>
                <br/>
                <br/>
                <table
                        align="left"
                        border="1"
                        width="600">
                    <tr>
                        <th align="center">L.p</th>
                        <th align="center">Nazwa usługi</th>
                        <th align="center">J.m.</th>
                        <th align="center">Ilość</th>
                        <th align="center">Cena jedn.
                            <br/>
                            [zł]
                        </th>
                        <th align="center">Wartość
                            <br/>
                            [zł]
                        </th>
                    </tr>
                    <xsl:for-each select="faktura/towary/towar">
                        <tr>
                            <td align="right">
                                <xsl:value-of select="position()"/>
                            </td>
                            <td align="right">
                                <xsl:value-of select="nazwa"/>
                            </td>
                            <td align="right">
                                sztuka
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(ilosc, "## ###,##", "pln")'/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(cena_netto_jednost, "## ###,00", "pln")'/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(netto, "## ###,00", "pln")'/>
                            </td>
                        </tr>
                    </xsl:for-each>
                    <tr>
                        <td/>
                        <td/>
                        <td/>
                        <td align="right">RAZEM:</td>
                        <td align="right">
                            <xsl:for-each select="faktura">
                                <xsl:value-of select='format-number(netto, "## ###,00", "pln")'/>
                            </xsl:for-each>
                        </td>
                    </tr>
                </table>

                <br/>
                <br/>
                Sprzedawca zwolniony podmiotowo z podatku VAT
                <br/>
                na podstawie art. 113 ust 1 ustawy o podatku od towarów i usług (Dz.U z 2011 Nr 177 poz 1054 z póżn. zm)
                <br/>
                <br/>
                <xsl:for-each select="faktura">
                    Sposób zapłaty:
                    <xsl:if test="sposob_platnosci !='' ">
                        <xsl:value-of select="sposob_platnosci"/>
                    </xsl:if>
                    <br/>
                    <xsl:if test="dostawca/rachunek !='' ">
                        Numer konta:
                        <xsl:value-of select="dostawca/rachunek"/>
                    </xsl:if>
                </xsl:for-each>
                <br/>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>