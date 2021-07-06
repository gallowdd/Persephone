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
 * <p>Java class for locationXmlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="locationXmlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="java_class" type="{http://persephone.gallowdd.pitt.edu/parameters}locationJavaClassXmlEnum"/&gt;
 *         &lt;element name="location_attribute" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/parameters}dataTypeXmlEnum"/&gt;
 *                   &lt;element name="source" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;choice&gt;
 *                             &lt;element name="initial_file_link"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="init_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="link"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="link_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="link_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/choice&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="attr_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "locationXmlType", propOrder = {
    "javaClass",
    "locationAttribute"
})
public class LocationXmlType {

    @XmlElement(name = "java_class", required = true)
    @XmlSchemaType(name = "string")
    protected LocationJavaClassXmlEnum javaClass;
    @XmlElement(name = "location_attribute")
    protected List<LocationXmlType.LocationAttribute> locationAttribute;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the javaClass property.
     * 
     * @return
     *     possible object is
     *     {@link LocationJavaClassXmlEnum }
     *     
     */
    public LocationJavaClassXmlEnum getJavaClass() {
        return javaClass;
    }

    /**
     * Sets the value of the javaClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationJavaClassXmlEnum }
     *     
     */
    public void setJavaClass(LocationJavaClassXmlEnum value) {
        this.javaClass = value;
    }

    /**
     * Gets the value of the locationAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the locationAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocationXmlType.LocationAttribute }
     * 
     * 
     */
    public List<LocationXmlType.LocationAttribute> getLocationAttribute() {
        if (locationAttribute == null) {
            locationAttribute = new ArrayList<LocationXmlType.LocationAttribute>();
        }
        return this.locationAttribute;
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
     *         &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/parameters}dataTypeXmlEnum"/&gt;
     *         &lt;element name="source" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;choice&gt;
     *                   &lt;element name="initial_file_link"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="init_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="link"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="link_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="link_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/choice&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="attr_name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dataType",
        "source"
    })
    public static class LocationAttribute {

        @XmlElement(name = "data_type", required = true)
        @XmlSchemaType(name = "string")
        protected DataTypeXmlEnum dataType;
        protected LocationXmlType.LocationAttribute.Source source;
        @XmlAttribute(name = "attr_name", required = true)
        protected String attrName;

        /**
         * Gets the value of the dataType property.
         * 
         * @return
         *     possible object is
         *     {@link DataTypeXmlEnum }
         *     
         */
        public DataTypeXmlEnum getDataType() {
            return dataType;
        }

        /**
         * Sets the value of the dataType property.
         * 
         * @param value
         *     allowed object is
         *     {@link DataTypeXmlEnum }
         *     
         */
        public void setDataType(DataTypeXmlEnum value) {
            this.dataType = value;
        }

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link LocationXmlType.LocationAttribute.Source }
         *     
         */
        public LocationXmlType.LocationAttribute.Source getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link LocationXmlType.LocationAttribute.Source }
         *     
         */
        public void setSource(LocationXmlType.LocationAttribute.Source value) {
            this.source = value;
        }

        /**
         * Gets the value of the attrName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAttrName() {
            return attrName;
        }

        /**
         * Sets the value of the attrName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAttrName(String value) {
            this.attrName = value;
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
         *       &lt;choice&gt;
         *         &lt;element name="initial_file_link"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="init_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="link"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="link_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="link_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *       &lt;/choice&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "initialFileLink",
            "link"
        })
        public static class Source {

            @XmlElement(name = "initial_file_link")
            protected LocationXmlType.LocationAttribute.Source.InitialFileLink initialFileLink;
            protected LocationXmlType.LocationAttribute.Source.Link link;

            /**
             * Gets the value of the initialFileLink property.
             * 
             * @return
             *     possible object is
             *     {@link LocationXmlType.LocationAttribute.Source.InitialFileLink }
             *     
             */
            public LocationXmlType.LocationAttribute.Source.InitialFileLink getInitialFileLink() {
                return initialFileLink;
            }

            /**
             * Sets the value of the initialFileLink property.
             * 
             * @param value
             *     allowed object is
             *     {@link LocationXmlType.LocationAttribute.Source.InitialFileLink }
             *     
             */
            public void setInitialFileLink(LocationXmlType.LocationAttribute.Source.InitialFileLink value) {
                this.initialFileLink = value;
            }

            /**
             * Gets the value of the link property.
             * 
             * @return
             *     possible object is
             *     {@link LocationXmlType.LocationAttribute.Source.Link }
             *     
             */
            public LocationXmlType.LocationAttribute.Source.Link getLink() {
                return link;
            }

            /**
             * Sets the value of the link property.
             * 
             * @param value
             *     allowed object is
             *     {@link LocationXmlType.LocationAttribute.Source.Link }
             *     
             */
            public void setLink(LocationXmlType.LocationAttribute.Source.Link value) {
                this.link = value;
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
             *         &lt;element name="init_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "initFileCsvFieldName",
                "convertToEnumFunction"
            })
            public static class InitialFileLink {

                @XmlElement(name = "init_file_csv_field_name", required = true)
                protected String initFileCsvFieldName;
                @XmlElement(name = "convert_to_enum_function")
                protected String convertToEnumFunction;

                /**
                 * Gets the value of the initFileCsvFieldName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getInitFileCsvFieldName() {
                    return initFileCsvFieldName;
                }

                /**
                 * Sets the value of the initFileCsvFieldName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setInitFileCsvFieldName(String value) {
                    this.initFileCsvFieldName = value;
                }

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
             *         &lt;element name="link_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="link_file_csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="convert_to_enum_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
                "linkFileName",
                "linkFileCsvFieldName",
                "convertToEnumFunction"
            })
            public static class Link {

                @XmlElement(name = "link_file_name", required = true)
                protected String linkFileName;
                @XmlElement(name = "link_file_csv_field_name", required = true)
                protected String linkFileCsvFieldName;
                @XmlElement(name = "convert_to_enum_function")
                protected String convertToEnumFunction;

                /**
                 * Gets the value of the linkFileName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLinkFileName() {
                    return linkFileName;
                }

                /**
                 * Sets the value of the linkFileName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLinkFileName(String value) {
                    this.linkFileName = value;
                }

                /**
                 * Gets the value of the linkFileCsvFieldName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getLinkFileCsvFieldName() {
                    return linkFileCsvFieldName;
                }

                /**
                 * Sets the value of the linkFileCsvFieldName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setLinkFileCsvFieldName(String value) {
                    this.linkFileCsvFieldName = value;
                }

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

            }

        }

    }

}