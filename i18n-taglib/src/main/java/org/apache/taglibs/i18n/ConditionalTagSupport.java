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

import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;


/**
 *  This class provides the base implementation for the ifdef and ifndef tags.
 *  Subclasses must provide an implementation of the shouldEvaluate() method.
 *
 *  @author <a href="mailto:tdawson@wamnet.com">Tim Dawson</a>
 *
 */
public abstract class ConditionalTagSupport extends BodyTagSupport
{

    protected static String _tagname = "i18n:";

    // instance variables used during processing
    private String               _key = null;
    private ResourceBundle       _bundle = null;
    private String               _bundleRef = null;

    /**
     *  sets the key to use when looking up the value in the bundle
     */
    public void setKey(String key)
    {
        _key = key;
    }

    /**
     *  sets the bundle based on a variable defined in the page<br>
     *  if neither bundle or bundleRef are specified, uses the first bundle
     *  defined on the page by the i18n:bundle tag
     */
    public void setBundleRef(String varName)
    {
        _bundleRef = varName;
    }

    /**
     *  sets the bundle to an actual ResourceBundle object<br>
     *  if neither bundle or bundleRef are specified, uses the bundle most recently
     *  defined on the page by the i18n:bundle tag
     */
    public void setBundle(ResourceBundle aBundle)
    {
        _bundle = aBundle;
    }

    /**
     *  @return the bundle to use
     */
    public ResourceBundle getBundle()
    {
        if ( _bundleRef != null ) {
            _bundle = (ResourceBundle)pageContext.getAttribute(_bundleRef);
        }
        if ( _bundle == null ) {
            BundleTag bundleTag = (BundleTag)this.findAncestorWithClass(this,BundleTag.class);
            if (bundleTag != null) {
                _bundle = bundleTag.getBundle();
            }
        }
        if ( _bundle == null ) {
            _bundle = ResourceHelper.getBundle(pageContext);
        }
        return _bundle;
    }

    /**
     *  clears out the key for the next usage
     */
    public void release()
    {
        super.release();
        _key = null;
        _bundle = null;
    }

    /**
     *  must be overridden by a subclass to return a boolean value
     */
    public abstract boolean shouldEvaluate() throws JspException;

    /**
     *  returns (if any) the value specified for the key in the bundle.
     *
     *  if no value is specified, traps the MissingResourceException and returns null
     */
    protected String getValue() throws JspException
    {
        // ensure we have a key
        if ( _key == null) {
            throw new JspTagException(
                this._tagname + " tag, requires a key attribute.");
        }

        // ensure a bundle was defined
        ResourceBundle bundle = this.getBundle();
        if ( this.getBundle() == null) {
            throw new JspTagException(
                this._tagname + " tag, no bundle found for use with tag");
        }

        String value = null;
        try {
            value = bundle.getString(_key);
        } catch (java.util.MissingResourceException e) {
            value = null;
        }
        return value;
    }

    /**
     *  locates the bundle and tests whether the key has a value
     */
    public int doStartTag() throws JspException
    {
        int returnValue;

        // evaluate the body if a value was found, otherwise skip it
        if ( this.shouldEvaluate() ) {
            returnValue = BodyTag.EVAL_BODY_TAG;
        } else {
            returnValue = BodyTag.SKIP_BODY;
        }

        return returnValue;
    }

    /**
     * Prints the body of the tag.
     * <P>
     * Always return the flag for processing the rest of the JSP page.
     *
     * @return the flag for processing the rest of the JSP page.
     * @exception JspException if the writing out of the body fails.
     */
    public int doEndTag() throws JspException
    {
        try {
            if (bodyContent != null) {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            }
        } catch (java.io.IOException ioe) {
            throw new JspTagException(
                this._tagname + " tag, IO Error: " + ioe.getMessage());
        }

        // This is not a loop, so always process the rest of the JSP page
        return BodyTag.EVAL_PAGE;
    }

}
