package ml.jmoodle.functions;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ml.jmoodle.authentications.MoodleAuthentication;
import ml.jmoodle.configs.MoodleConfig;
import ml.jmoodle.functions.exceptions.MoodleWSFunctionCallException;
import ml.jmoodle.functions.rest.fixtures.UsersFixture;

/**
 * 
 * @author Carlos Alexandre S. da Fonseca
 * @copyrigth © 2016 Carlos Alexandre S. da Fonseca
 * @license https://opensource.org/licenses/MIT - MIT License
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ MoodleWSFunctionCall.class })
public class MoodleWSFunctionCallTest {
	private static final String CHARACTER_MNEMONIC_ENTITIES="<!DOCTYPE RESPONSE [<!--================ Character mnemonic entities =========================--><!-- Portions (C) International Organization for Standardization 1986Permission to copy in any form is granted for use withconforming SGML systems and applications as defined inISO 8879, provided this notice is included in all copies.--><!-- Character entity set. Typical invocation:<!ENTITY % HTMLlat1 PUBLIC\"-//W3C//ENTITIES Latin 1 for XHTML//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml-lat1.ent\">%HTMLlat1;--><!ENTITY nbsp   \"&#160;\"> <!-- no-break space = non-breaking space,U+00A0 ISOnum --><!ENTITY iexcl  \"&#161;\"> <!-- inverted exclamation mark, U+00A1 ISOnum --><!ENTITY cent   \"&#162;\"> <!-- cent sign, U+00A2 ISOnum --><!ENTITY pound  \"&#163;\"> <!-- pound sign, U+00A3 ISOnum --><!ENTITY curren \"&#164;\"> <!-- currency sign, U+00A4 ISOnum --><!ENTITY yen    \"&#165;\"> <!-- yen sign = yuan sign, U+00A5 ISOnum --><!ENTITY brvbar \"&#166;\"> <!-- broken bar = broken vertical bar,U+00A6 ISOnum --><!ENTITY sect   \"&#167;\"> <!-- section sign, U+00A7 ISOnum --><!ENTITY uml    \"&#168;\"> <!-- diaeresis = spacing diaeresis,U+00A8 ISOdia --><!ENTITY copy   \"&#169;\"> <!-- copyright sign, U+00A9 ISOnum --><!ENTITY ordf   \"&#170;\"> <!-- feminine ordinal indicator, U+00AA ISOnum --><!ENTITY laquo  \"&#171;\"> <!-- left-pointing double angle quotation mark= left pointing guillemet, U+00AB ISOnum --><!ENTITY not    \"&#172;\"> <!-- not sign = angled dash,U+00AC ISOnum --><!ENTITY shy    \"&#173;\"> <!-- soft hyphen = discretionary hyphen,U+00AD ISOnum --><!ENTITY reg    \"&#174;\"> <!-- registered sign = registered trade mark sign,U+00AE ISOnum --><!ENTITY macr   \"&#175;\"> <!-- macron = spacing macron = overline= APL overbar, U+00AF ISOdia --><!ENTITY deg    \"&#176;\"> <!-- degree sign, U+00B0 ISOnum --><!ENTITY plusmn \"&#177;\"> <!-- plus-minus sign = plus-or-minus sign,U+00B1 ISOnum --><!ENTITY sup2   \"&#178;\"> <!-- superscript two = superscript digit two= squared, U+00B2 ISOnum --><!ENTITY sup3   \"&#179;\"> <!-- superscript three = superscript digit three= cubed, U+00B3 ISOnum --><!ENTITY acute  \"&#180;\"> <!-- acute accent = spacing acute,U+00B4 ISOdia --><!ENTITY micro  \"&#181;\"> <!-- micro sign, U+00B5 ISOnum --><!ENTITY para   \"&#182;\"> <!-- pilcrow sign = paragraph sign,U+00B6 ISOnum --><!ENTITY middot \"&#183;\"> <!-- middle dot = Georgian comma= Greek middle dot, U+00B7 ISOnum --><!ENTITY cedil  \"&#184;\"> <!-- cedilla = spacing cedilla, U+00B8 ISOdia --><!ENTITY sup1   \"&#185;\"> <!-- superscript one = superscript digit one,U+00B9 ISOnum --><!ENTITY ordm   \"&#186;\"> <!-- masculine ordinal indicator,U+00BA ISOnum --><!ENTITY raquo  \"&#187;\"> <!-- right-pointing double angle quotation mark= right pointing guillemet, U+00BB ISOnum --><!ENTITY frac14 \"&#188;\"> <!-- vulgar fraction one quarter= fraction one quarter, U+00BC ISOnum --><!ENTITY frac12 \"&#189;\"> <!-- vulgar fraction one half= fraction one half, U+00BD ISOnum --><!ENTITY frac34 \"&#190;\"> <!-- vulgar fraction three quarters= fraction three quarters, U+00BE ISOnum --><!ENTITY iquest \"&#191;\"> <!-- inverted question mark= turned question mark, U+00BF ISOnum --><!ENTITY Agrave \"&#192;\"> <!-- latin capital letter A with grave= latin capital letter A grave,U+00C0 ISOlat1 --><!ENTITY Aacute \"&#193;\"> <!-- latin capital letter A with acute,U+00C1 ISOlat1 --><!ENTITY Acirc  \"&#194;\"> <!-- latin capital letter A with circumflex,U+00C2 ISOlat1 --><!ENTITY Atilde \"&#195;\"> <!-- latin capital letter A with tilde,U+00C3 ISOlat1 --><!ENTITY Auml   \"&#196;\"> <!-- latin capital letter A with diaeresis,U+00C4 ISOlat1 --><!ENTITY Aring  \"&#197;\"> <!-- latin capital letter A with ring above= latin capital letter A ring,U+00C5 ISOlat1 --><!ENTITY AElig  \"&#198;\"> <!-- latin capital letter AE= latin capital ligature AE,U+00C6 ISOlat1 --><!ENTITY Ccedil \"&#199;\"> <!-- latin capital letter C with cedilla,U+00C7 ISOlat1 --><!ENTITY Egrave \"&#200;\"> <!-- latin capital letter E with grave,U+00C8 ISOlat1 --><!ENTITY Eacute \"&#201;\"> <!-- latin capital letter E with acute,U+00C9 ISOlat1 --><!ENTITY Ecirc  \"&#202;\"> <!-- latin capital letter E with circumflex,U+00CA ISOlat1 --><!ENTITY Euml   \"&#203;\"> <!-- latin capital letter E with diaeresis,U+00CB ISOlat1 --><!ENTITY Igrave \"&#204;\"> <!-- latin capital letter I with grave,U+00CC ISOlat1 --><!ENTITY Iacute \"&#205;\"> <!-- latin capital letter I with acute,U+00CD ISOlat1 --><!ENTITY Icirc  \"&#206;\"> <!-- latin capital letter I with circumflex,U+00CE ISOlat1 --><!ENTITY Iuml   \"&#207;\"> <!-- latin capital letter I with diaeresis,U+00CF ISOlat1 --><!ENTITY ETH    \"&#208;\"> <!-- latin capital letter ETH, U+00D0 ISOlat1 --><!ENTITY Ntilde \"&#209;\"> <!-- latin capital letter N with tilde,U+00D1 ISOlat1 --><!ENTITY Ograve \"&#210;\"> <!-- latin capital letter O with grave,U+00D2 ISOlat1 --><!ENTITY Oacute \"&#211;\"> <!-- latin capital letter O with acute,U+00D3 ISOlat1 --><!ENTITY Ocirc  \"&#212;\"> <!-- latin capital letter O with circumflex,U+00D4 ISOlat1 --><!ENTITY Otilde \"&#213;\"> <!-- latin capital letter O with tilde,U+00D5 ISOlat1 --><!ENTITY Ouml   \"&#214;\"> <!-- latin capital letter O with diaeresis,U+00D6 ISOlat1 --><!ENTITY times  \"&#215;\"> <!-- multiplication sign, U+00D7 ISOnum --><!ENTITY Oslash \"&#216;\"> <!-- latin capital letter O with stroke= latin capital letter O slash,U+00D8 ISOlat1 --><!ENTITY Ugrave \"&#217;\"> <!-- latin capital letter U with grave,U+00D9 ISOlat1 --><!ENTITY Uacute \"&#218;\"> <!-- latin capital letter U with acute,U+00DA ISOlat1 --><!ENTITY Ucirc  \"&#219;\"> <!-- latin capital letter U with circumflex,U+00DB ISOlat1 --><!ENTITY Uuml   \"&#220;\"> <!-- latin capital letter U with diaeresis,U+00DC ISOlat1 --><!ENTITY Yacute \"&#221;\"> <!-- latin capital letter Y with acute,U+00DD ISOlat1 --><!ENTITY THORN  \"&#222;\"> <!-- latin capital letter THORN,U+00DE ISOlat1 --><!ENTITY szlig  \"&#223;\"> <!-- latin small letter sharp s = ess-zed,U+00DF ISOlat1 --><!ENTITY agrave \"&#224;\"> <!-- latin small letter a with grave= latin small letter a grave,U+00E0 ISOlat1 --><!ENTITY aacute \"&#225;\"> <!-- latin small letter a with acute,U+00E1 ISOlat1 --><!ENTITY acirc  \"&#226;\"> <!-- latin small letter a with circumflex,U+00E2 ISOlat1 --><!ENTITY atilde \"&#227;\"> <!-- latin small letter a with tilde,U+00E3 ISOlat1 --><!ENTITY auml   \"&#228;\"> <!-- latin small letter a with diaeresis,U+00E4 ISOlat1 --><!ENTITY aring  \"&#229;\"> <!-- latin small letter a with ring above= latin small letter a ring,U+00E5 ISOlat1 --><!ENTITY aelig  \"&#230;\"> <!-- latin small letter ae= latin small ligature ae, U+00E6 ISOlat1 --><!ENTITY ccedil \"&#231;\"> <!-- latin small letter c with cedilla,U+00E7 ISOlat1 --><!ENTITY egrave \"&#232;\"> <!-- latin small letter e with grave,U+00E8 ISOlat1 --><!ENTITY eacute \"&#233;\"> <!-- latin small letter e with acute,U+00E9 ISOlat1 --><!ENTITY ecirc  \"&#234;\"> <!-- latin small letter e with circumflex,U+00EA ISOlat1 --><!ENTITY euml   \"&#235;\"> <!-- latin small letter e with diaeresis,U+00EB ISOlat1 --><!ENTITY igrave \"&#236;\"> <!-- latin small letter i with grave,U+00EC ISOlat1 --><!ENTITY iacute \"&#237;\"> <!-- latin small letter i with acute,U+00ED ISOlat1 --><!ENTITY icirc  \"&#238;\"> <!-- latin small letter i with circumflex,U+00EE ISOlat1 --><!ENTITY iuml   \"&#239;\"> <!-- latin small letter i with diaeresis,U+00EF ISOlat1 --><!ENTITY eth    \"&#240;\"> <!-- latin small letter eth, U+00F0 ISOlat1 --><!ENTITY ntilde \"&#241;\"> <!-- latin small letter n with tilde,U+00F1 ISOlat1 --><!ENTITY ograve \"&#242;\"> <!-- latin small letter o with grave,U+00F2 ISOlat1 --><!ENTITY oacute \"&#243;\"> <!-- latin small letter o with acute,U+00F3 ISOlat1 --><!ENTITY ocirc  \"&#244;\"> <!-- latin small letter o with circumflex,U+00F4 ISOlat1 --><!ENTITY otilde \"&#245;\"> <!-- latin small letter o with tilde,U+00F5 ISOlat1 --><!ENTITY ouml   \"&#246;\"> <!-- latin small letter o with diaeresis,U+00F6 ISOlat1 --><!ENTITY divide \"&#247;\"> <!-- division sign, U+00F7 ISOnum --><!ENTITY oslash \"&#248;\"> <!-- latin small letter o with stroke,= latin small letter o slash,U+00F8 ISOlat1 --><!ENTITY ugrave \"&#249;\"> <!-- latin small letter u with grave,U+00F9 ISOlat1 --><!ENTITY uacute \"&#250;\"> <!-- latin small letter u with acute,U+00FA ISOlat1 --><!ENTITY ucirc  \"&#251;\"> <!-- latin small letter u with circumflex,U+00FB ISOlat1 --><!ENTITY uuml   \"&#252;\"> <!-- latin small letter u with diaeresis,U+00FC ISOlat1 --><!ENTITY yacute \"&#253;\"> <!-- latin small letter y with acute,U+00FD ISOlat1 --><!ENTITY thorn  \"&#254;\"> <!-- latin small letter thorn,U+00FE ISOlat1 --><!ENTITY yuml   \"&#255;\"> <!-- latin small letter y with diaeresis,U+00FF ISOlat1 --><!-- Special characters for XHTML --><!-- Character entity set. Typical invocation:<!ENTITY % HTMLspecial PUBLIC\"-//W3C//ENTITIES Special for XHTML//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml-special.ent\">%HTMLspecial;--><!-- Portions (C) International Organization for Standardization 1986:Permission to copy in any form is granted for use withconforming SGML systems and applications as defined inISO 8879, provided this notice is included in all copies.--><!-- Relevant ISO entity set is given unless names are newly introduced.New names (i.e., not in ISO 8879 list) do not clash with anyexisting ISO 8879 entity names. ISO 10646 character numbersare given for each character, in hex. values are decimalconversions of the ISO 10646 values and refer to the documentcharacter set. Names are Unicode names. --><!-- C0 Controls and Basic Latin --><!ENTITY quot    \"&#34;\"> <!--  quotation mark, U+0022 ISOnum --><!ENTITY amp     \"&#38;#38;\"> <!--  ampersand, U+0026 ISOnum --><!ENTITY lt      \"&#38;#60;\"> <!--  less-than sign, U+003C ISOnum --><!ENTITY gt      \"&#62;\"> <!--  greater-than sign, U+003E ISOnum --><!ENTITY apos	 \"&#39;\"> <!--  apostrophe = APL quote, U+0027 ISOnum --><!-- Latin Extended-A --><!ENTITY OElig   \"&#338;\"> <!--  latin capital ligature OE,U+0152 ISOlat2 --><!ENTITY oelig   \"&#339;\"> <!--  latin small ligature oe, U+0153 ISOlat2 --><!-- ligature is a misnomer, this is a separate character in some languages --><!ENTITY Scaron  \"&#352;\"> <!--  latin capital letter S with caron,U+0160 ISOlat2 --><!ENTITY scaron  \"&#353;\"> <!--  latin small letter s with caron,U+0161 ISOlat2 --><!ENTITY Yuml    \"&#376;\"> <!--  latin capital letter Y with diaeresis,U+0178 ISOlat2 --><!-- Spacing Modifier Letters --><!ENTITY circ    \"&#710;\"> <!--  modifier letter circumflex accent,U+02C6 ISOpub --><!ENTITY tilde   \"&#732;\"> <!--  small tilde, U+02DC ISOdia --><!-- General Punctuation --><!ENTITY ensp    \"&#8194;\"> <!-- en space, U+2002 ISOpub --><!ENTITY emsp    \"&#8195;\"> <!-- em space, U+2003 ISOpub --><!ENTITY thinsp  \"&#8201;\"> <!-- thin space, U+2009 ISOpub --><!ENTITY zwnj    \"&#8204;\"> <!-- zero width non-joiner,U+200C NEW RFC 2070 --><!ENTITY zwj     \"&#8205;\"> <!-- zero width joiner, U+200D NEW RFC 2070 --><!ENTITY lrm     \"&#8206;\"> <!-- left-to-right mark, U+200E NEW RFC 2070 --><!ENTITY rlm     \"&#8207;\"> <!-- right-to-left mark, U+200F NEW RFC 2070 --><!ENTITY ndash   \"&#8211;\"> <!-- en dash, U+2013 ISOpub --><!ENTITY mdash   \"&#8212;\"> <!-- em dash, U+2014 ISOpub --><!ENTITY lsquo   \"&#8216;\"> <!-- left single quotation mark,U+2018 ISOnum --><!ENTITY rsquo   \"&#8217;\"> <!-- right single quotation mark,U+2019 ISOnum --><!ENTITY sbquo   \"&#8218;\"> <!-- single low-9 quotation mark, U+201A NEW --><!ENTITY ldquo   \"&#8220;\"> <!-- left double quotation mark,U+201C ISOnum --><!ENTITY rdquo   \"&#8221;\"> <!-- right double quotation mark,U+201D ISOnum --><!ENTITY bdquo   \"&#8222;\"> <!-- double low-9 quotation mark, U+201E NEW --><!ENTITY dagger  \"&#8224;\"> <!-- dagger, U+2020 ISOpub --><!ENTITY Dagger  \"&#8225;\"> <!-- double dagger, U+2021 ISOpub --><!ENTITY permil  \"&#8240;\"> <!-- per mille sign, U+2030 ISOtech --><!ENTITY lsaquo  \"&#8249;\"> <!-- single left-pointing angle quotation mark,U+2039 ISO proposed --><!-- lsaquo is proposed but not yet ISO standardized --><!ENTITY rsaquo  \"&#8250;\"> <!-- single right-pointing angle quotation mark,U+203A ISO proposed --><!-- rsaquo is proposed but not yet ISO standardized --><!-- Currency Symbols --><!ENTITY euro   \"&#8364;\"> <!--  euro sign, U+20AC NEW --><!-- Mathematical, Greek and Symbolic characters for XHTML --><!-- Character entity set. Typical invocation:<!ENTITY % HTMLsymbol PUBLIC\"-//W3C//ENTITIES Symbols for XHTML//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml-symbol.ent\">%HTMLsymbol;--><!-- Portions (C) International Organization for Standardization 1986:Permission to copy in any form is granted for use withconforming SGML systems and applications as defined inISO 8879, provided this notice is included in all copies.--><!-- Relevant ISO entity set is given unless names are newly introduced.New names (i.e., not in ISO 8879 list) do not clash with anyexisting ISO 8879 entity names. ISO 10646 character numbersare given for each character, in hex. values are decimalconversions of the ISO 10646 values and refer to the documentcharacter set. Names are Unicode names. --><!-- Latin Extended-B --><!ENTITY fnof     \"&#402;\"> <!-- latin small letter f with hook = function= florin, U+0192 ISOtech --><!-- Greek --><!ENTITY Alpha    \"&#913;\"> <!-- greek capital letter alpha, U+0391 --><!ENTITY Beta     \"&#914;\"> <!-- greek capital letter beta, U+0392 --><!ENTITY Gamma    \"&#915;\"> <!-- greek capital letter gamma,U+0393 ISOgrk3 --><!ENTITY Delta    \"&#916;\"> <!-- greek capital letter delta,U+0394 ISOgrk3 --><!ENTITY Epsilon  \"&#917;\"> <!-- greek capital letter epsilon, U+0395 --><!ENTITY Zeta     \"&#918;\"> <!-- greek capital letter zeta, U+0396 --><!ENTITY Eta      \"&#919;\"> <!-- greek capital letter eta, U+0397 --><!ENTITY Theta    \"&#920;\"> <!-- greek capital letter theta,U+0398 ISOgrk3 --><!ENTITY Iota     \"&#921;\"> <!-- greek capital letter iota, U+0399 --><!ENTITY Kappa    \"&#922;\"> <!-- greek capital letter kappa, U+039A --><!ENTITY Lambda   \"&#923;\"> <!-- greek capital letter lamda,U+039B ISOgrk3 --><!ENTITY Mu       \"&#924;\"> <!-- greek capital letter mu, U+039C --><!ENTITY Nu       \"&#925;\"> <!-- greek capital letter nu, U+039D --><!ENTITY Xi       \"&#926;\"> <!-- greek capital letter xi, U+039E ISOgrk3 --><!ENTITY Omicron  \"&#927;\"> <!-- greek capital letter omicron, U+039F --><!ENTITY Pi       \"&#928;\"> <!-- greek capital letter pi, U+03A0 ISOgrk3 --><!ENTITY Rho      \"&#929;\"> <!-- greek capital letter rho, U+03A1 --><!-- there is no Sigmaf, and no U+03A2 character either --><!ENTITY Sigma    \"&#931;\"> <!-- greek capital letter sigma,U+03A3 ISOgrk3 --><!ENTITY Tau      \"&#932;\"> <!-- greek capital letter tau, U+03A4 --><!ENTITY Upsilon  \"&#933;\"> <!-- greek capital letter upsilon,U+03A5 ISOgrk3 --><!ENTITY Phi      \"&#934;\"> <!-- greek capital letter phi,U+03A6 ISOgrk3 --><!ENTITY Chi      \"&#935;\"> <!-- greek capital letter chi, U+03A7 --><!ENTITY Psi      \"&#936;\"> <!-- greek capital letter psi,U+03A8 ISOgrk3 --><!ENTITY Omega    \"&#937;\"> <!-- greek capital letter omega,U+03A9 ISOgrk3 --><!ENTITY alpha    \"&#945;\"> <!-- greek small letter alpha,U+03B1 ISOgrk3 --><!ENTITY beta     \"&#946;\"> <!-- greek small letter beta, U+03B2 ISOgrk3 --><!ENTITY gamma    \"&#947;\"> <!-- greek small letter gamma,U+03B3 ISOgrk3 --><!ENTITY delta    \"&#948;\"> <!-- greek small letter delta,U+03B4 ISOgrk3 --><!ENTITY epsilon  \"&#949;\"> <!-- greek small letter epsilon,U+03B5 ISOgrk3 --><!ENTITY zeta     \"&#950;\"> <!-- greek small letter zeta, U+03B6 ISOgrk3 --><!ENTITY eta      \"&#951;\"> <!-- greek small letter eta, U+03B7 ISOgrk3 --><!ENTITY theta    \"&#952;\"> <!-- greek small letter theta,U+03B8 ISOgrk3 --><!ENTITY iota     \"&#953;\"> <!-- greek small letter iota, U+03B9 ISOgrk3 --><!ENTITY kappa    \"&#954;\"> <!-- greek small letter kappa,U+03BA ISOgrk3 --><!ENTITY lambda   \"&#955;\"> <!-- greek small letter lamda,U+03BB ISOgrk3 --><!ENTITY mu       \"&#956;\"> <!-- greek small letter mu, U+03BC ISOgrk3 --><!ENTITY nu       \"&#957;\"> <!-- greek small letter nu, U+03BD ISOgrk3 --><!ENTITY xi       \"&#958;\"> <!-- greek small letter xi, U+03BE ISOgrk3 --><!ENTITY omicron  \"&#959;\"> <!-- greek small letter omicron, U+03BF NEW --><!ENTITY pi       \"&#960;\"> <!-- greek small letter pi, U+03C0 ISOgrk3 --><!ENTITY rho      \"&#961;\"> <!-- greek small letter rho, U+03C1 ISOgrk3 --><!ENTITY sigmaf   \"&#962;\"> <!-- greek small letter final sigma,U+03C2 ISOgrk3 --><!ENTITY sigma    \"&#963;\"> <!-- greek small letter sigma,U+03C3 ISOgrk3 --><!ENTITY tau      \"&#964;\"> <!-- greek small letter tau, U+03C4 ISOgrk3 --><!ENTITY upsilon  \"&#965;\"> <!-- greek small letter upsilon,U+03C5 ISOgrk3 --><!ENTITY phi      \"&#966;\"> <!-- greek small letter phi, U+03C6 ISOgrk3 --><!ENTITY chi      \"&#967;\"> <!-- greek small letter chi, U+03C7 ISOgrk3 --><!ENTITY psi      \"&#968;\"> <!-- greek small letter psi, U+03C8 ISOgrk3 --><!ENTITY omega    \"&#969;\"> <!-- greek small letter omega,U+03C9 ISOgrk3 --><!ENTITY thetasym \"&#977;\"> <!-- greek theta symbol,U+03D1 NEW --><!ENTITY upsih    \"&#978;\"> <!-- greek upsilon with hook symbol,U+03D2 NEW --><!ENTITY piv      \"&#982;\"> <!-- greek pi symbol, U+03D6 ISOgrk3 --><!-- General Punctuation --><!ENTITY bull     \"&#8226;\"> <!-- bullet = black small circle,U+2022 ISOpub  --><!-- bullet is NOT the same as bullet operator, U+2219 --><!ENTITY hellip   \"&#8230;\"> <!-- horizontal ellipsis = three dot leader,U+2026 ISOpub  --><!ENTITY prime    \"&#8242;\"> <!-- prime = minutes = feet, U+2032 ISOtech --><!ENTITY Prime    \"&#8243;\"> <!-- double prime = seconds = inches,U+2033 ISOtech --><!ENTITY oline    \"&#8254;\"> <!-- overline = spacing overscore,U+203E NEW --><!ENTITY frasl    \"&#8260;\"> <!-- fraction slash, U+2044 NEW --><!-- Letterlike Symbols --><!ENTITY weierp   \"&#8472;\"> <!-- script capital P = power set= Weierstrass p, U+2118 ISOamso --><!ENTITY image    \"&#8465;\"> <!-- black-letter capital I = imaginary part,U+2111 ISOamso --><!ENTITY real     \"&#8476;\"> <!-- black-letter capital R = real part symbol,U+211C ISOamso --><!ENTITY trade    \"&#8482;\"> <!-- trade mark sign, U+2122 ISOnum --><!ENTITY alefsym  \"&#8501;\"> <!-- alef symbol = first transfinite cardinal,U+2135 NEW --><!-- alef symbol is NOT the same as hebrew letter alef,U+05D0 although the same glyph could be used to depict both characters --><!-- Arrows --><!ENTITY larr     \"&#8592;\"> <!-- leftwards arrow, U+2190 ISOnum --><!ENTITY uarr     \"&#8593;\"> <!-- upwards arrow, U+2191 ISOnum--><!ENTITY rarr     \"&#8594;\"> <!-- rightwards arrow, U+2192 ISOnum --><!ENTITY darr     \"&#8595;\"> <!-- downwards arrow, U+2193 ISOnum --><!ENTITY harr     \"&#8596;\"> <!-- left right arrow, U+2194 ISOamsa --><!ENTITY crarr    \"&#8629;\"> <!-- downwards arrow with corner leftwards= carriage return, U+21B5 NEW --><!ENTITY lArr     \"&#8656;\"> <!-- leftwards double arrow, U+21D0 ISOtech --><!-- Unicode does not say that lArr is the same as the 'is implied by' arrowbut also does not have any other character for that function. So lArr canbe used for 'is implied by' as ISOtech suggests --><!ENTITY uArr     \"&#8657;\"> <!-- upwards double arrow, U+21D1 ISOamsa --><!ENTITY rArr     \"&#8658;\"> <!-- rightwards double arrow,U+21D2 ISOtech --><!-- Unicode does not say this is the 'implies' character but does not have another character with this function so rArr can be used for 'implies'as ISOtech suggests --><!ENTITY dArr     \"&#8659;\"> <!-- downwards double arrow, U+21D3 ISOamsa --><!ENTITY hArr     \"&#8660;\"> <!-- left right double arrow,U+21D4 ISOamsa --><!-- Mathematical Operators --><!ENTITY forall   \"&#8704;\"> <!-- for all, U+2200 ISOtech --><!ENTITY part     \"&#8706;\"> <!-- partial differential, U+2202 ISOtech  --><!ENTITY exist    \"&#8707;\"> <!-- there exists, U+2203 ISOtech --><!ENTITY empty    \"&#8709;\"> <!-- empty set = null set, U+2205 ISOamso --><!ENTITY nabla    \"&#8711;\"> <!-- nabla = backward difference,U+2207 ISOtech --><!ENTITY isin     \"&#8712;\"> <!-- element of, U+2208 ISOtech --><!ENTITY notin    \"&#8713;\"> <!-- not an element of, U+2209 ISOtech --><!ENTITY ni       \"&#8715;\"> <!-- contains as member, U+220B ISOtech --><!ENTITY prod     \"&#8719;\"> <!-- n-ary product = product sign,U+220F ISOamsb --><!-- prod is NOT the same character as U+03A0 'greek capital letter pi' thoughthe same glyph might be used for both --><!ENTITY sum      \"&#8721;\"> <!-- n-ary summation, U+2211 ISOamsb --><!-- sum is NOT the same character as U+03A3 'greek capital letter sigma'though the same glyph might be used for both --><!ENTITY minus    \"&#8722;\"> <!-- minus sign, U+2212 ISOtech --><!ENTITY lowast   \"&#8727;\"> <!-- asterisk operator, U+2217 ISOtech --><!ENTITY radic    \"&#8730;\"> <!-- square root = radical sign,U+221A ISOtech --><!ENTITY prop     \"&#8733;\"> <!-- proportional to, U+221D ISOtech --><!ENTITY infin    \"&#8734;\"> <!-- infinity, U+221E ISOtech --><!ENTITY ang      \"&#8736;\"> <!-- angle, U+2220 ISOamso --><!ENTITY and      \"&#8743;\"> <!-- logical and = wedge, U+2227 ISOtech --><!ENTITY or       \"&#8744;\"> <!-- logical or = vee, U+2228 ISOtech --><!ENTITY cap      \"&#8745;\"> <!-- intersection = cap, U+2229 ISOtech --><!ENTITY cup      \"&#8746;\"> <!-- union = cup, U+222A ISOtech --><!ENTITY int      \"&#8747;\"> <!-- integral, U+222B ISOtech --><!ENTITY there4   \"&#8756;\"> <!-- therefore, U+2234 ISOtech --><!ENTITY sim      \"&#8764;\"> <!-- tilde operator = varies with = similar to,U+223C ISOtech --><!-- tilde operator is NOT the same character as the tilde, U+007E,although the same glyph might be used to represent both  --><!ENTITY cong     \"&#8773;\"> <!-- approximately equal to, U+2245 ISOtech --><!ENTITY asymp    \"&#8776;\"> <!-- almost equal to = asymptotic to,U+2248 ISOamsr --><!ENTITY ne       \"&#8800;\"> <!-- not equal to, U+2260 ISOtech --><!ENTITY equiv    \"&#8801;\"> <!-- identical to, U+2261 ISOtech --><!ENTITY le       \"&#8804;\"> <!-- less-than or equal to, U+2264 ISOtech --><!ENTITY ge       \"&#8805;\"> <!-- greater-than or equal to,U+2265 ISOtech --><!ENTITY sub      \"&#8834;\"> <!-- subset of, U+2282 ISOtech --><!ENTITY sup      \"&#8835;\"> <!-- superset of, U+2283 ISOtech --><!ENTITY nsub     \"&#8836;\"> <!-- not a subset of, U+2284 ISOamsn --><!ENTITY sube     \"&#8838;\"> <!-- subset of or equal to, U+2286 ISOtech --><!ENTITY supe     \"&#8839;\"> <!-- superset of or equal to,U+2287 ISOtech --><!ENTITY oplus    \"&#8853;\"> <!-- circled plus = direct sum,U+2295 ISOamsb --><!ENTITY otimes   \"&#8855;\"> <!-- circled times = vector product,U+2297 ISOamsb --><!ENTITY perp     \"&#8869;\"> <!-- up tack = orthogonal to = perpendicular,U+22A5 ISOtech --><!ENTITY sdot     \"&#8901;\"> <!-- dot operator, U+22C5 ISOamsb --><!-- dot operator is NOT the same character as U+00B7 middle dot --><!-- Miscellaneous Technical --><!ENTITY lceil    \"&#8968;\"> <!-- left ceiling = APL upstile,U+2308 ISOamsc  --><!ENTITY rceil    \"&#8969;\"> <!-- right ceiling, U+2309 ISOamsc  --><!ENTITY lfloor   \"&#8970;\"> <!-- left floor = APL downstile,U+230A ISOamsc  --><!ENTITY rfloor   \"&#8971;\"> <!-- right floor, U+230B ISOamsc  --><!ENTITY lang     \"&#9001;\"> <!-- left-pointing angle bracket = bra,U+2329 ISOtech --><!-- lang is NOT the same character as U+003C 'less than sign' or U+2039 'single left-pointing angle quotation mark' --><!ENTITY rang     \"&#9002;\"> <!-- right-pointing angle bracket = ket,U+232A ISOtech --><!-- rang is NOT the same character as U+003E 'greater than sign' or U+203A 'single right-pointing angle quotation mark' --><!-- Geometric Shapes --><!ENTITY loz      \"&#9674;\"> <!-- lozenge, U+25CA ISOpub --><!-- Miscellaneous Symbols --><!ENTITY spades   \"&#9824;\"> <!-- black spade suit, U+2660 ISOpub --><!-- black here seems to mean filled as opposed to hollow --><!ENTITY clubs    \"&#9827;\"> <!-- black club suit = shamrock,U+2663 ISOpub --><!ENTITY hearts   \"&#9829;\"> <!-- black heart suit = valentine,U+2665 ISOpub --><!ENTITY diams    \"&#9830;\"> <!-- black diamond suit, U+2666 ISOpub -->]>";
	
	private final static String ERROR_RESPONSE_WITH_DEBUG_INFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ CHARACTER_MNEMONIC_ENTITIES
				+ "<EXCEPTION class=\"invalid_parameter_exception\">"
				+ "<MESSAGE>Valor inválido de parâmetro detectado</MESSAGE>"
				+ "<DEBUGINFO>FOO BAR</DEBUGINFO>"
				+ "</EXCEPTION>";

	private static final String DEFAULT_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
			+ CHARACTER_MNEMONIC_ENTITIES
			+ "<RESPONSE>"
			+ "<MULTIPLE>"
			+ "<SINGLE>" 
			+ "<KEY name=\"id\">" 
			+ "<VALUE>int</VALUE>" 
			+ "</KEY>"
			+ "<KEY name=\"username\">" 
			+ "<VALUE>string</VALUE>" 
			+ "</KEY>" 
			+ "</SINGLE>"
			+ "</MULTIPLE>"
			+ "</RESPONSE>";

	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Mock
	MoodleConfig cfgMock;

	@Mock
	MoodleWSFunction fncMock;

	@Mock
	MoodleAuthentication mdlAuthMock;

	MoodleWSFunctionCall fcntCall;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		when(cfgMock.getMoodleURL()).thenReturn(new URL("http://test.test"));
		when(fncMock.getFunctionData()).thenReturn(UsersFixture.CREATE_USER_DATA);
		when(mdlAuthMock.getAuthentication()).thenReturn("?wstoken=MYTOKEN");
		fcntCall = PowerMockito.spy(MoodleWSFunctionCall.getInstance(cfgMock));
		//The CHARACTER_MNEMONIC_ENTITIES is always injected
		PowerMockito.doReturn("\n"+CHARACTER_MNEMONIC_ENTITIES).when(fcntCall, "doCall", any(URL.class), anyString());

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testIfMoodleConfigGetMoodleURLIsCalled() throws Exception {
		fcntCall.call(fncMock);

		verify(cfgMock).getMoodleURL();
	}

	@Test
	public final void testIfMoodelWSFunctionGetFunctionDataIsCalled() throws Exception {
		fcntCall.call(fncMock);

		verify(fncMock).getFunctionData();
	}

	@Test
	public final void testCallWithResponse() throws Exception {
		
		PowerMockito.doReturn(DEFAULT_RESPONSE).when(fcntCall, "doCall", any(URL.class), anyString());
		Document response = fcntCall.call(fncMock);

		assertEquals(response.getElementsByTagName("RESPONSE").getLength(), 1);

	}

	@Test
	public final void testCallWithOutResponse() throws Exception {
		Document response = fcntCall.call(fncMock);
		assertThat(response, CoreMatchers.nullValue());
	}

	@Test(expected = MoodleWSFunctionCallException.class)
	public final void testIfErroResponseThrowsException() throws MoodleWSFunctionCallException {
		try {
			PowerMockito.doReturn(ERROR_RESPONSE_WITH_DEBUG_INFO).when(fcntCall, "doCall", any(URL.class), anyString());
		} catch (Exception e) {
			fail(e.getMessage()); 
		} 
		
		fcntCall.call(fncMock);
	}

	public final void testIfErroResponseExceptionHaveTheRightMessage() {
		try {
			PowerMockito.doReturn(ERROR_RESPONSE_WITH_DEBUG_INFO).when(fcntCall, "doCall", any(URL.class), anyString());
		} catch (Exception e) {
			fail(e.getMessage());
		}

		try {
			fcntCall.call(fncMock);
		} catch (MoodleWSFunctionCallException e) {
			assertThat(e.getMessage(), CoreMatchers.containsString(
					"invalid_parameter_exception-Valor inválido de parâmetro detectado\nDEBUGINFO:\nFOO BAR"));

		}
	}

}
