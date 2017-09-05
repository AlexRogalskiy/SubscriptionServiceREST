package com.wildbeeslabs.rest.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import javax.persistence.MappedSuperclass;

/**
 *
 * BaseDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@MappedSuperclass
public abstract class BaseDTO {

    /**
     * Default date formatter
     */
    protected static final SimpleDateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @JacksonXmlProperty(localName = "createdAt")
    private String createdDate;
    @JacksonXmlProperty(localName = "modifiedAt")
    private String modifiedDate;
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

    public Date getCreatedDateConverted(final String timezone) throws ParseException {
        DEFAULT_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(timezone));
        return DEFAULT_DATE_FORMATTER.parse(this.createdDate);
    }

    public void setCreatedDate(final Date date, final String timezone) {
        DEFAULT_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(timezone));
        this.createdDate = DEFAULT_DATE_FORMATTER.format(date);
    }

    public Date getModifiedDateConverted(final String timezone) throws ParseException {
        DEFAULT_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(timezone));
        return DEFAULT_DATE_FORMATTER.parse(this.modifiedDate);
    }

    public void setModifiedDate(final Date date, final String timezone) {
        DEFAULT_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone(timezone));
        this.modifiedDate = DEFAULT_DATE_FORMATTER.format(date);
    }

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
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        return Objects.equals(this.modifiedDate, other.modifiedDate);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.createdDate);
        hash = 53 * hash + Objects.hashCode(this.modifiedDate);
        hash = 53 * hash + Objects.hashCode(this.createdBy);
        hash = 53 * hash + Objects.hashCode(this.modifiedBy);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("BaseDTO {createdAt: %s, createdAt: %s, createdBy: %s, modifiedBy: %s}", this.createdDate, this.modifiedDate, this.createdBy, this.modifiedBy);
    }
}
