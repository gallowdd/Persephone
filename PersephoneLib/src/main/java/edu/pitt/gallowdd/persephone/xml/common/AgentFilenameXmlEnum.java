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
 * <p>Java class for agentFilenameXmlEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="agentFilenameXmlEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="people.csv"/&gt;
 *     &lt;enumeration value="gq_people.csv"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "agentFilenameXmlEnum")
@XmlEnum
public enum AgentFilenameXmlEnum {

    @XmlEnumValue("people.csv")
    PEOPLE_CSV("people.csv"),
    @XmlEnumValue("gq_people.csv")
    GQ_PEOPLE_CSV("gq_people.csv");
    private final String value;

    AgentFilenameXmlEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgentFilenameXmlEnum fromValue(String v) {
        for (AgentFilenameXmlEnum c: AgentFilenameXmlEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
