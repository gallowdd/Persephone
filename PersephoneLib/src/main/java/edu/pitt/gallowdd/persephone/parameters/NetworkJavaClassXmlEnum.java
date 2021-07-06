//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.01 at 10:52:58 AM EST 
//


package edu.pitt.gallowdd.persephone.parameters;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for networkJavaClassXmlEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="networkJavaClassXmlEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="edu.pitt.gallowdd.persephone.location.Household"/&gt;
 *     &lt;enumeration value="edu.pitt.gallowdd.persephone.location.Workplace"/&gt;
 *     &lt;enumeration value="edu.pitt.gallowdd.persephone.location.School"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "networkJavaClassXmlEnum")
@XmlEnum
public enum NetworkJavaClassXmlEnum {

    @XmlEnumValue("edu.pitt.gallowdd.persephone.location.Household")
    EDU_PITT_GALLOWDD_PERSEPHONE_LOCATION_HOUSEHOLD("edu.pitt.gallowdd.persephone.location.Household"),
    @XmlEnumValue("edu.pitt.gallowdd.persephone.location.Workplace")
    EDU_PITT_GALLOWDD_PERSEPHONE_LOCATION_WORKPLACE("edu.pitt.gallowdd.persephone.location.Workplace"),
    @XmlEnumValue("edu.pitt.gallowdd.persephone.location.School")
    EDU_PITT_GALLOWDD_PERSEPHONE_LOCATION_SCHOOL("edu.pitt.gallowdd.persephone.location.School");
    private final String value;

    NetworkJavaClassXmlEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NetworkJavaClassXmlEnum fromValue(String v) {
        for (NetworkJavaClassXmlEnum c: NetworkJavaClassXmlEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}