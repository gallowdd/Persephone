//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.13 at 11:32:18 AM EDT 
//


package edu.pitt.gallowdd.persephone.xml.runtime;

import java.util.ArrayList;
import java.util.List;
import edu.pitt.gallowdd.persephone.xml.common.DatatypeXmlEnum;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for userDefinedNetworkXmlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="userDefinedNetworkXmlType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="network_attribute" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/xml/common}datatypeXmlEnum"/&gt;
 *                   &lt;element name="source" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;choice&gt;
 *                             &lt;element name="initial_file"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
 *                                       &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                                       &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
@XmlType(name = "userDefinedNetworkXmlType", propOrder = {
    "networkAttribute"
})
public class UserDefinedNetworkXmlType {

    @XmlElement(name = "network_attribute")
    protected List<UserDefinedNetworkXmlType.NetworkAttribute> networkAttribute;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * Gets the value of the networkAttribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the networkAttribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNetworkAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserDefinedNetworkXmlType.NetworkAttribute }
     * 
     * 
     */
    public List<UserDefinedNetworkXmlType.NetworkAttribute> getNetworkAttribute() {
        if (networkAttribute == null) {
            networkAttribute = new ArrayList<UserDefinedNetworkXmlType.NetworkAttribute>();
        }
        return this.networkAttribute;
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
     *         &lt;element name="data_type" type="{http://persephone.gallowdd.pitt.edu/xml/common}datatypeXmlEnum"/&gt;
     *         &lt;element name="source" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;choice&gt;
     *                   &lt;element name="initial_file"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
     *                             &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                             &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    public static class NetworkAttribute {

        @XmlElement(name = "data_type", required = true)
        @XmlSchemaType(name = "string")
        protected DatatypeXmlEnum dataType;
        protected UserDefinedNetworkXmlType.NetworkAttribute.Source source;
        @XmlAttribute(name = "attr_name", required = true)
        protected String attrName;

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

        /**
         * Gets the value of the source property.
         * 
         * @return
         *     possible object is
         *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source }
         *     
         */
        public UserDefinedNetworkXmlType.NetworkAttribute.Source getSource() {
            return source;
        }

        /**
         * Sets the value of the source property.
         * 
         * @param value
         *     allowed object is
         *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source }
         *     
         */
        public void setSource(UserDefinedNetworkXmlType.NetworkAttribute.Source value) {
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
         *         &lt;element name="initial_file"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
         *                   &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *                   &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
            "initialFile",
            "link"
        })
        public static class Source {

            @XmlElement(name = "initial_file")
            protected UserDefinedNetworkXmlType.NetworkAttribute.Source.InitialFile initialFile;
            protected UserDefinedNetworkXmlType.NetworkAttribute.Source.Link link;

            /**
             * Gets the value of the initialFile property.
             * 
             * @return
             *     possible object is
             *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source.InitialFile }
             *     
             */
            public UserDefinedNetworkXmlType.NetworkAttribute.Source.InitialFile getInitialFile() {
                return initialFile;
            }

            /**
             * Sets the value of the initialFile property.
             * 
             * @param value
             *     allowed object is
             *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source.InitialFile }
             *     
             */
            public void setInitialFile(UserDefinedNetworkXmlType.NetworkAttribute.Source.InitialFile value) {
                this.initialFile = value;
            }

            /**
             * Gets the value of the link property.
             * 
             * @return
             *     possible object is
             *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source.Link }
             *     
             */
            public UserDefinedNetworkXmlType.NetworkAttribute.Source.Link getLink() {
                return link;
            }

            /**
             * Sets the value of the link property.
             * 
             * @param value
             *     allowed object is
             *     {@link UserDefinedNetworkXmlType.NetworkAttribute.Source.Link }
             *     
             */
            public void setLink(UserDefinedNetworkXmlType.NetworkAttribute.Source.Link value) {
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
             *         &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "dir",
                "filename",
                "csvFieldName"
            })
            public static class InitialFile {

                @XmlElement(required = true)
                protected String dir;
                @XmlElement(required = true)
                protected String filename;
                @XmlElement(name = "csv_field_name", required = true)
                protected String csvFieldName;

                /**
                 * Gets the value of the dir property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDir() {
                    return dir;
                }

                /**
                 * Sets the value of the dir property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDir(String value) {
                    this.dir = value;
                }

                /**
                 * Gets the value of the filename property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFilename() {
                    return filename;
                }

                /**
                 * Sets the value of the filename property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFilename(String value) {
                    this.filename = value;
                }

                /**
                 * Gets the value of the csvFieldName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCsvFieldName() {
                    return csvFieldName;
                }

                /**
                 * Sets the value of the csvFieldName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCsvFieldName(String value) {
                    this.csvFieldName = value;
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
             *         &lt;element name="dir" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
             *         &lt;element name="csv_field_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
                "dir",
                "filename",
                "csvFieldName"
            })
            public static class Link {

                @XmlElement(required = true)
                protected String dir;
                @XmlElement(required = true)
                protected String filename;
                @XmlElement(name = "csv_field_name", required = true)
                protected String csvFieldName;

                /**
                 * Gets the value of the dir property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDir() {
                    return dir;
                }

                /**
                 * Sets the value of the dir property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDir(String value) {
                    this.dir = value;
                }

                /**
                 * Gets the value of the filename property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFilename() {
                    return filename;
                }

                /**
                 * Sets the value of the filename property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFilename(String value) {
                    this.filename = value;
                }

                /**
                 * Gets the value of the csvFieldName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCsvFieldName() {
                    return csvFieldName;
                }

                /**
                 * Sets the value of the csvFieldName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCsvFieldName(String value) {
                    this.csvFieldName = value;
                }

            }

        }

    }

}
