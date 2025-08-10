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
                <table align="left" border="1" width="800">
                    <tr>
                        <th align="center">L.p</th>
                        <th align="center">Nazwa towaru/usługi</th>
                        <th align="center">J.m.</th>
                        <th align="center">Ilość</th>
                        <th align="center">Cena jedn. netto<br/>[zł]</th>
                        <th align="center">Wartość netto<br/>[zł]</th>
                        <th align="center">Stawka VAT<br/>[%]</th>
                        <th align="center">Kwota VAT<br/>[zł]</th>
                        <th align="center">Wartość brutto<br/>[zł]</th>
                    </tr>
                    <xsl:for-each select="faktura/towary/towar">
                        <tr>
                            <td align="right">
                                <xsl:value-of select="position()"/>
                            </td>
                            <td align="left">
                                <xsl:value-of select="nazwa"/>
                            </td>
                            <td align="center">
                                <xsl:value-of select="jednostka"/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(ilosc, "## ###,##", "pln")'/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(cena_netto_jednost_po_rabacie, "## ###,00", "pln")'/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(netto, "## ###,00", "pln")'/>
                            </td>
                            <td align="center">
                                <xsl:value-of select="stawka_VAT"/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(VAT, "## ###,00", "pln")'/>
                            </td>
                            <td align="right">
                                <xsl:value-of select='format-number(brutto, "## ###,00", "pln")'/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <br/>
                <table align="left" border="1" width="400">
                    <tr>
                        <th align="center">Stawka VAT</th>
                        <th align="center">Netto [zł]</th>
                        <th align="center">VAT [zł]</th>
                        <th align="center">Brutto [zł]</th>
                    </tr>
                    <xsl:for-each select="faktura">
                        <xsl:if test="netto_0 != 0 or vat_0 != 0 or gross_0 != 0">
                            <tr>
                                <td align="center">0%</td>
                                <td align="right"><xsl:value-of select='format-number(netto_0, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(vat_0, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(gross_0, "## ###,00", "pln")'/></td>
                            </tr>
                        </xsl:if>
                        <xsl:if test="netto_5 != 0 or vat_5 != 0 or gross_5 != 0">
                            <tr>
                                <td align="center">5%</td>
                                <td align="right"><xsl:value-of select='format-number(netto_5, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(vat_5, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(gross_5, "## ###,00", "pln")'/></td>
                            </tr>
                        </xsl:if>
                        <xsl:if test="netto_8 != 0 or vat_8 != 0 or gross_8 != 0">
                            <tr>
                                <td align="center">8%</td>
                                <td align="right"><xsl:value-of select='format-number(netto_8, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(vat_8, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(gross_8, "## ###,00", "pln")'/></td>
                            </tr>
                        </xsl:if>
                        <xsl:if test="netto_23 != 0 or vat_23 != 0 or gross_23 != 0">
                            <tr>
                                <td align="center">23%</td>
                                <td align="right"><xsl:value-of select='format-number(netto_23, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(vat_23, "## ###,00", "pln")'/></td>
                                <td align="right"><xsl:value-of select='format-number(gross_23, "## ###,00", "pln")'/></td>
                            </tr>
                        </xsl:if>
                        <tr>
                            <td align="center">RAZEM</td>
                            <td align="right"><xsl:value-of select='format-number(netto, "## ###,00", "pln")'/></td>
                            <td align="right"><xsl:value-of select='format-number(VAT, "## ###,00", "pln")'/></td>
                            <td align="right"><xsl:value-of select='format-number(gross, "## ###,00", "pln")'/></td>
                        </tr>
                    </xsl:for-each>
                </table>
                <br/>
                <br/>
                <xsl:for-each select="faktura">
                    Sposób zapłaty:
                    <xsl:if test="sposob_platnosci !='' ">
                        <xsl:value-of select="sposob_platnosci"/>
                    </xsl:if>
                    <br/>
                    Termin płatności:
                    <xsl:if test="termin_platnosci !='' ">
                        <xsl:value-of select="termin_platnosci"/>
                    </xsl:if>
                    <br/>
                    <xsl:if test="dostawca/rachunek !='' ">
                        Numer konta:
                        <xsl:value-of select="dostawca/rachunek"/>
                    </xsl:if>
                </xsl:for-each>
                <br/>
                <xsl:for-each select="faktura">
                    <xsl:if test="brutto_slownie !='' ">
                        Kwota brutto słownie: <xsl:value-of select="brutto_slownie"/>
                    </xsl:if>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>