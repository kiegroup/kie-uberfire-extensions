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

import java.io.IOException;
import java.text.Format;
import java.text.DateFormat;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/** An abstract base class for the formatting tags to provide 
  * implementation inheritence.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public abstract class FormatTagSupport extends TagSupport {
    
    protected static String _tagname = "i18n:";

    /** the value to be formatted */
    private Object value;    
    /** the locale used to format the value */
    private Locale locale;
    /** the text output if the value is null */
    private String defaultText = "";


    // Tag interface
    //-------------------------------------------------------------------------
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }
    
    public int doEndTag() throws JspException {
        try {
            Object value = getValue();
            JspWriter out = pageContext.getOut();
            String text = null;
            if ( value != null ) {
                Format formatter = getFormat();
                if ( formatter == null ) {
                    throw new JspTagException(
                        this._tagname +
                        " tag, could not find valid Format instance");
                }
                text = formatter.format( value );
            }
            else {
                text = getDefaultText();
            }
            if ( text != null ) {
                out.print( text );
            }
        }
        catch ( IOException e ) {
            handleIOException( e );
        }
        return EVAL_PAGE;
    }
    
    public void release() {
        super.release();
        value = null;
        locale = null;
        defaultText = "";
    }
    
    // Properties
    //-------------------------------------------------------------------------    
    public Object getValue() {
        return value;
    }
    
    public void setValue( Object value ) {
        this.value = value;
    }

    /** If no {@link java.util.Locale} has been explicitly configured then use the
      * parent LocaleTag if present else the Locale from the ServletRequest
      * else use the default JVM {@link java.util.Locale}.
      */
    public Locale getLocale() {
        if ( locale == null ) {
            return findLocale();
        }
        return locale;
    }
    
    public void setLocale( Locale locale ) {
        this.locale = locale;
    }

    public String getDefaultText() {
        return defaultText;
    }
    
    public void setDefaultText( String defaultText ) {
        this.defaultText = defaultText;
    }

    
    // Implementation methods
    //-------------------------------------------------------------------------
    
    /** Abstract class to return the value formatter 
      */
    protected abstract Format getFormat();    
    
    protected void handleIOException( IOException e ) throws JspException {
        pageContext.getServletContext().log( "Caught: IOException: " + e );
        throw new JspTagException(
            this._tagname + " tag, IOException: " + e );
    }

    /** finds the current locale from either an outer LocaleTag or the 
      * current SerlvetRequest or the current JVM.
      *
      * @return a Locale instance
      */
    private Locale findLocale() {
        // lets try find a LocaleTag first
        LocaleTag localeTag = (LocaleTag) findAncestorWithClass( this, LocaleTag.class );
        if ( localeTag != null ) {
            return localeTag.getLocale();
        }
        else {
            return pageContext.getResponse().getLocale();
        }
    }
    
    
    /** A helper method for date, datetime & time based formatting tags.
      * This method converts a string into a DateFormat style code.
      */
    protected int getStyleCode( String style ) {
        if ( "short".equalsIgnoreCase( style ) ) {
            return DateFormat.SHORT; 
        }
        else if ( "medium".equalsIgnoreCase( style ) ) {
            return DateFormat.MEDIUM; 
        }
        else if ( "long".equalsIgnoreCase( style ) ) {
            return DateFormat.LONG; 
        }
        else if ( "full".equalsIgnoreCase( style ) ) {
            return DateFormat.FULL; 
        }
        else {
            return DateFormat.SHORT;
        }
    }
}


