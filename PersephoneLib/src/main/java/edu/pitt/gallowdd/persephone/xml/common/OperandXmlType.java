//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 11:32:27 AM EDT 
//


package edu.pitt.gallowdd.persephone.xml.common;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operandXmlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operandXmlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="constant_value"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/xml/common}datatypeXmlEnum"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="default_agent_attribute" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dynamic_agent_attribute" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="global_attribute" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="unary_numerical_operation" type="{http://persephone.gallowdd.pitt.edu/xml/common}unaryNumericalOperationXmlType"/&gt;
 *         &lt;element name="binary_numerical_operation" type="{http://persephone.gallowdd.pitt.edu/xml/common}binaryNumericalOperationXmlType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operandXmlType", propOrder = {
    "constantValue",
    "defaultAgentAttribute",
    "dynamicAgentAttribute",
    "globalAttribute",
    "unaryNumericalOperation",
    "binaryNumericalOperation"
})
public class OperandXmlType {

    @XmlElement(name = "constant_value")
    protected OperandXmlType.ConstantValue constantValue;
    @XmlElement(name = "default_agent_attribute")
    protected String defaultAgentAttribute;
    @XmlElement(name = "dynamic_agent_attribute")
    protected String dynamicAgentAttribute;
    @XmlElement(name = "global_attribute")
    protected String globalAttribute;
    @XmlElement(name = "unary_numerical_operation")
    protected UnaryNumericalOperationXmlType unaryNumericalOperation;
    @XmlElement(name = "binary_numerical_operation")
    protected BinaryNumericalOperationXmlType binaryNumericalOperation;

    /**
     * Gets the value of the constantValue property.
     * 
     * @return
     *     possible object is
     *     {@link OperandXmlType.ConstantValue }
     *     
     */
    public OperandXmlType.ConstantValue getConstantValue() {
        return constantValue;
    }

    /**
     * Sets the value of the constantValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperandXmlType.ConstantValue }
     *     
     */
    public void setConstantValue(OperandXmlType.ConstantValue value) {
        this.constantValue = value;
    }

    /**
     * Gets the value of the defaultAgentAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultAgentAttribute() {
        return defaultAgentAttribute;
    }

    /**
     * Sets the value of the defaultAgentAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultAgentAttribute(String value) {
        this.defaultAgentAttribute = value;
    }

    /**
     * Gets the value of the dynamicAgentAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDynamicAgentAttribute() {
        return dynamicAgentAttribute;
    }

    /**
     * Sets the value of the dynamicAgentAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDynamicAgentAttribute(String value) {
        this.dynamicAgentAttribute = value;
    }

    /**
     * Gets the value of the globalAttribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalAttribute() {
        return globalAttribute;
    }

    /**
     * Sets the value of the globalAttribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalAttribute(String value) {
        this.globalAttribute = value;
    }

    /**
     * Gets the value of the unaryNumericalOperation property.
     * 
     * @return
     *     possible object is
     *     {@link UnaryNumericalOperationXmlType }
     *     
     */
    public UnaryNumericalOperationXmlType getUnaryNumericalOperation() {
        return unaryNumericalOperation;
    }

    /**
     * Sets the value of the unaryNumericalOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnaryNumericalOperationXmlType }
     *     
     */
    public void setUnaryNumericalOperation(UnaryNumericalOperationXmlType value) {
        this.unaryNumericalOperation = value;
    }

    /**
     * Gets the value of the binaryNumericalOperation property.
     * 
     * @return
     *     possible object is
     *     {@link BinaryNumericalOperationXmlType }
     *     
     */
    public BinaryNumericalOperationXmlType getBinaryNumericalOperation() {
        return binaryNumericalOperation;
    }

    /**
     * Sets the value of the binaryNumericalOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryNumericalOperationXmlType }
     *     
     */
    public void setBinaryNumericalOperation(BinaryNumericalOperationXmlType value) {
        this.binaryNumericalOperation = value;
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
     *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/xml/common}datatypeXmlEnum"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value",
        "dataType"
    })
    public static class ConstantValue {

        @XmlElement(required = true)
        protected String value;
        @XmlElement(name = "data_type", required = true)
        @XmlSchemaType(name = "string")
        protected DatatypeXmlEnum dataType;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the dataType property.
         * 
         * @return
         *     possible object is
         *     {@link DatatypeXmlEnum }
         *     
         */
        public DatatypeXmlEnum getDataType() {
            return dataType;
        }

        /**
         * Sets the value of the dataType property.
         * 
         * @param value
         *     allowed object is
         *     {@link DatatypeXmlEnum }
         *     
         */
        public void setDataType(DatatypeXmlEnum value) {
            this.dataType = value;
        }

    }

}