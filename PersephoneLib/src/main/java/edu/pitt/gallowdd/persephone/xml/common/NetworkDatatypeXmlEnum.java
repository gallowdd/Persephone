//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 11:32:27 AM EDT 
//


package edu.pitt.gallowdd.persephone.xml.common;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for networkDatatypeXmlEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="networkDatatypeXmlEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Dynamic"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "networkDatatypeXmlEnum")
@XmlEnum
public enum NetworkDatatypeXmlEnum {

    @XmlEnumValue("Dynamic")
    DYNAMIC("Dynamic");
    private final String value;

    NetworkDatatypeXmlEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NetworkDatatypeXmlEnum fromValue(String v) {
        for (NetworkDatatypeXmlEnum c: NetworkDatatypeXmlEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}