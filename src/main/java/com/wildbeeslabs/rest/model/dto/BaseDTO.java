package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.rest.utils.DateUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;
import java.util.Objects;

/**
 *
 * BaseDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseDTO implements IBaseDTO {

    @JacksonXmlProperty(localName = "createdAt")
    @JsonProperty("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date createdAt;

    @JacksonXmlProperty(localName = "modifiedAt")
    @JsonProperty("modifiedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date modifiedAt;

    @JacksonXmlProperty(localName = "createdBy")
    private String createdBy;

    @JacksonXmlProperty(localName = "modifiedBy")
    private String modifiedBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(final Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

//    public Date getCreatedDate() {
//        return (Objects.nonNull(this.createdDate)) ? DateUtils.strToDate(this.createdDate) : null;
//    }
//
//    public void setCreatedDate(final Date date) {
//        this.createdDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
//
//    public Date getModifiedDate() {
//        return (Objects.nonNull(this.modifiedDate)) ? DateUtils.strToDate(this.modifiedDate) : null;
//    }
//
//    public void setModifiedDate(final Date date) {
//        this.modifiedDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final BaseDTO other = (BaseDTO) obj;
        if (!Objects.equals(this.createdBy, other.createdBy)) {
            return false;
        }
        if (!Objects.equals(this.modifiedBy, other.modifiedBy)) {
            return false;
        }
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return Objects.equals(this.modifiedAt, other.modifiedAt);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.createdAt);
        hash = 53 * hash + Objects.hashCode(this.modifiedAt);
        hash = 53 * hash + Objects.hashCode(this.createdBy);
        hash = 53 * hash + Objects.hashCode(this.modifiedBy);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("BaseDTO {createdAt: %s, createdAt: %s, createdBy: %s, modifiedBy: %s}", this.createdAt, this.modifiedAt, this.createdBy, this.modifiedBy);
    }
}
