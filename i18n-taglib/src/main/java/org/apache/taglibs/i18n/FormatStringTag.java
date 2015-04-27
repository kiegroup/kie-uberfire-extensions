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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/** A simple tag that allows a String to be output with null handling.
  *
  * @author James Strachan
  * @version $Revision: 216689 $
  */
public class FormatStringTag extends TagSupport {
    
    protected static final String _tagname = "i18n:formatString";

    /** the value to be formatted */
    private String value;    
    /** the text output if the value is null */
    private String defaultText = "";


    // Tag interface
    //-------------------------------------------------------------------------
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }
    
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String text = getValue();
            if ( text == null ) {
                text = getDefaultText();
                if ( text == null ) {
                    text = "";
                }
            }
            out.print( text );
        }
        catch ( IOException e ) {
            handleIOException( e );
        }
        return EVAL_PAGE;
    }
    
    public void release() {
        super.release();
        value = null;
        defaultText = "";
    }
    
    // Properties
    //-------------------------------------------------------------------------    
    public String getValue() {
        return value;
    }
    
    public void setValue( String value ) {
        this.value = value;
    }

    public String getDefaultText() {
        return defaultText;
    }
    
    public void setDefaultText( String defaultText ) {
        this.defaultText = defaultText;
    }
    
    // Implementation methods
    //-------------------------------------------------------------------------    
    protected void handleIOException( IOException e ) throws JspException {
        pageContext.getServletContext().log( this._tagname + " tag, IOException: " + e );
        throw new JspException( this._tagname + " tag, IOException: " + e );
    }
}


