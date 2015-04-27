/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.taglibs.i18n;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;


/** This tag defines a {@link java.util.Locale} context for use by other
  * inner JSP tags.
  *
  * If no locale has been configured directly via the "locale" property
  * then the language, country and optional varient properties are used
  * to create a new Locale instance.
  * If these properties are not specified then the Locale is taken from
  * {@link javax.servlet.ServletRequest} is used.
  * If still no {@link java.util.Locale} could be found then the default JVM
  * {@link java.util.Locale} is used.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public class LocaleTag extends TagSupport
{
    /** Holds value of property locale. */
    private Locale locale;
    /** Holds value of property localeRef. */
    private String localeRef;
    /** Holds value of property language. */
    private String language;
    /** Holds value of property country. */
    private String country;
    /** Holds value of property variant. */
    private String variant;
    /** specifies whether or not the response locale should be changed to match
     *  the locale used by this tag */
    private boolean changeResponseLocale = true;


    // Tag interface
    //-------------------------------------------------------------------------
    public int doStartTag() throws JspException
    {
        // set the locale as a variable in the page
        if ( this.getId() != null ) {
            pageContext.setAttribute(getId(),getLocale());
        }
    
    return EVAL_BODY_INCLUDE;
    }

    /**
     *  Sets the response locale if the changeResponseLocale attribute was set
     *  to true, OR if changeResponseLocale was unset and the tag was empty
     */
    public int doEndTag() throws JspException
    {
        if (this.changeResponseLocale) {
            // set the locale for the response
            pageContext.getResponse().setLocale(getLocale());
        }
        
    return EVAL_PAGE;
    }

    public void setChangeResponseLocale(boolean value)
    {
        changeResponseLocale = value;
    }

    public void release()
    {
        super.release();
        locale = null;
        language = null;
        country = null;
        variant = null;
        changeResponseLocale = true;
    }

    // Properties
    //-------------------------------------------------------------------------
    protected final Locale getLocale()
    {
        if (localeRef != null) {
            locale = (Locale)pageContext.findAttribute(localeRef);
        }
        if (locale == null) {
            locale = createLocale();
        }
        return locale;
    }

    public final void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    /**
     *  Provides a key to retrieve a locale via findAttribute()
     */
    public final void setLocaleRef(String value)
    {
        this.localeRef = value;
    }

    public final void setLanguage(String language)
    {
        this.language = language;
    }

    public final void setCountry(String country)
    {
        this.country = country;
    }

    public final void setVariant(String variant)
    {
        this.variant = variant;
    }

    // Implementation methods
    //-------------------------------------------------------------------------
    private Locale createLocale()
    {
        // let's try use the language & country properties first...
        Locale locale = null;
        if (language == null) {
            locale = pageContext.getResponse().getLocale();
        } else if (country == null) {
            locale = new Locale(language,"");
        } else if (variant == null) {
            locale = new Locale(language, country);
        } else {
            locale = new Locale(language, country, variant);
        }

        return locale;
    }

}
