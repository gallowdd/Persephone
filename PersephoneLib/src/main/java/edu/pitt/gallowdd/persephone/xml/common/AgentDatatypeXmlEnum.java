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
 * <p>Java class for agentDatatypeXmlEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="agentDatatypeXmlEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Person"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "agentDatatypeXmlEnum")
@XmlEnum
public enum AgentDatatypeXmlEnum {

    @XmlEnumValue("Person")
    PERSON("Person");
    private final String value;

    AgentDatatypeXmlEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AgentDatatypeXmlEnum fromValue(String v) {
        for (AgentDatatypeXmlEnum c: AgentDatatypeXmlEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}