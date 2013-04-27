package org.slstudio.acs.tr069.xml;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:02
 */
/**
 * This interface declares the constants used in this API.
 * Numbers in the range 0 to 256 are reserved for the specification,
 * user defined events must use event codes outside that range.
 */

public interface IMultipleXMLStreamConstants {

    public static final int START_MULTIPLE_DOCUMENT=1;
    public static final int END_MULTIPLE_DOCUMENT=2;

    public static final int START_SINGLE_DOCUMENT=3;
    public static final int END_SINGLE_DOCUMENT=4;

    public static final int START_ELEMENT=5;
    public static final int END_ELEMENT=6;

    public static final int PROCESSING_INSTRUCTION=7;
    public static final int CHARACTERS=8;
    public static final int COMMENT=9;
    public static final int SPACE=10;
    public static final int ENTITY_REFERENCE=11;
    public static final int ATTRIBUTE=12;
    public static final int DTD=13;
    public static final int CDATA=14;
    public static final int NAMESPACE=15;
    public static final int NOTATION_DECLARATION=16;
    public static final int ENTITY_DECLARATION=17;
}
