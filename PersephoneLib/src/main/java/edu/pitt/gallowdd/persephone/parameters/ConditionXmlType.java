//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.01 at 10:52:58 AM EST 
//


package edu.pitt.gallowdd.persephone.parameters;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for conditionXmlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="conditionXmlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transmission_mode" type="{http://persephone.gallowdd.pitt.edu/parameters}transmissionModeXmlEnum"/&gt;
 *         &lt;element name="exposed_state" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="state" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conditionXmlType", propOrder = {
    "transmissionMode",
    "exposedState",
    "state"
})
public class ConditionXmlType {

    @XmlElement(name = "transmission_mode", required = true)
    @XmlSchemaType(name = "string")
    protected TransmissionModeXmlEnum transmissionMode;
    @XmlElement(name = "exposed_state", required = true)
    protected String exposedState;
    @XmlElement(required = true)
    protected List<ConditionXmlType.State> state;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the transmissionMode property.
     * 
     * @return
     *     possible object is
     *     {@link TransmissionModeXmlEnum }
     *     
     */
    public TransmissionModeXmlEnum getTransmissionMode() {
        return transmissionMode;
    }

    /**
     * Sets the value of the transmissionMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmissionModeXmlEnum }
     *     
     */
    public void setTransmissionMode(TransmissionModeXmlEnum value) {
        this.transmissionMode = value;
    }

    /**
     * Gets the value of the exposedState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExposedState() {
        return exposedState;
    }

    /**
     * Sets the value of the exposedState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExposedState(String value) {
        this.exposedState = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the state property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ConditionXmlType.State }
     * 
     * 
     */
    public List<ConditionXmlType.State> getState() {
        if (state == null) {
            state = new ArrayList<ConditionXmlType.State>();
        }
        return this.state;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "convertToEnumFunction"
    })
    public static class State {

        @XmlElement(name = "convert_to_enum_function")
        protected String convertToEnumFunction;
        @XmlAttribute(name = "name", required = true)
        protected String name;

        /**
         * Gets the value of the convertToEnumFunction property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getConvertToEnumFunction() {
            return convertToEnumFunction;
        }

        /**
         * Sets the value of the convertToEnumFunction property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setConvertToEnumFunction(String value) {
            this.convertToEnumFunction = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

    }

}