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

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Enumeration;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpSession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


/**
 *  This class implements an empty tag that allows you to use a resource
 *  bundle to internationalize content in a web page. If a specific locale
 *  is not provided, the user's locale (language and country) are determined
 *  based on the browser settings. The response content-type is automatically
 *  set based on the locale.<br>
 *  This tag must be used very early in the page, prior to any html output.
 *  <P>
 *  <H2>Examples</H2>
 *  <PRE>
 *  &lt;i18n:bundle basename="test"/&gt;
 *  etc...
 *  </PRE>
 *  <P>
 *
 *  @author <a href="mailto:tdawson@wamnet.com">Tim Dawson</a>
 */
public class BundleTag extends TagSupport
{
    private String         _baseName = null;
    private String         _localeAttribute = null;
    private Locale         _locale = null;
    private boolean        _changeResponseLocale = true;
    private ResourceBundle _bundle = null;
    private int            _scope = PageContext.PAGE_SCOPE;
    private boolean        _debug = false;
    
    /**
     *  Sets the baseName of the bundle that the MessageTags will use when
     *  retrieving keys for this page.
     */
    public final void setBaseName(String value)
    {
        _baseName = value;
    }

    /**
     *  Set to true to enable debug logging.
     */
    public final void setDebug(boolean value)
    {
        _debug = value;
    }
      
      /**
       *  Sets the locale of the bundle that the MessageTags will use when
       *  retrieving keys for this page.
       */
      public void setLocale(Locale value)
      {
          _locale = value;
      }

    /**
     *  Provides a key to search the page context for in order to get the
     *  locale of the bundle that the MessageTags will use when retrieving
     *  keys for this page.
     *  @deprecated
     */
    public void setLocaleAttribute(String value)
    {
        _localeAttribute = value;
    }

    /**
     *  Provides a key to search the page context for in order to get the
     *  locale of the bundle that the MessageTags will use when retrieving
     *  keys for this page.
     */
    public void setLocaleRef(String value)
    {
        _localeAttribute = value;
    }

    /**
     *  Sets the scope that the bundle will be available to message tags.
     *  By default page scope is used.
     */
    public void setScope(String value) throws JspException
    {
        if (value == null)
            {
            throw new JspTagException("i18n:bundle tag invalid scope attribute of null");
            }
        else if (value.toLowerCase().equals("application"))
            {
            _scope = PageContext.APPLICATION_SCOPE;
            }
        else if (value.toLowerCase().equals("session"))
            {
            _scope = PageContext.SESSION_SCOPE;
            }
        else if (value.toLowerCase().equals("request"))
            {
            _scope = PageContext.REQUEST_SCOPE;
            }
        else if (value.toLowerCase().equals("page"))
            {
            _scope = PageContext.PAGE_SCOPE;
            }
        else
            {
            throw new JspTagException("i18n:bundle tag invalid scope attribute=" + value);
            }
    }

    /**
     *  Specifies whether or not the response locale should be changed to match
     *  the locale used by this tag.
     */
    public void setChangeResponseLocale(boolean value)
    {
        _changeResponseLocale = value;
    }

    public void release()
    {
        super.release();
        _baseName = null;
        _localeAttribute = null;
        _locale = null;
        _changeResponseLocale = true;
        _bundle = null;
        _scope = PageContext.PAGE_SCOPE;
        _debug = false;
    }

    /**
     * Get the bundle created by this bundle tag, used by nested tags.
     *
     * @return ResourceBundle bundle
     */
    protected final ResourceBundle getBundle()
    {
        return _bundle;
    }

    /**
     *  Locates and prepares a ResourceBundle for use within this request by
     *  message tags.  The bundle is located as specified by the given bundle
     *  name and the user's browser locale settings. The first preferred
     *  locale available that matches at least on basis of language is
     *  used. If an exact match is found for both locale and country, it is used.
     *
     *  Once a preferred locale has been determined for the given bundle name,
     *  the locale is cached in the user's session, so that the above computation
     *  can be avoided the next time the user requests a page with this bundle.
     *  (The entire bundle could be cached in the session, but large bundles would
     *  be problematic in servlet containers that serialize the session to provide
     *  clustering.)
     */
    private void findBundle() throws JspException
    {
        // the bundle name is required. if not provided, throw an exception
        if (_baseName == null) {
            throw new JspTagException(
                "i18n:bundle tag, a baseName attribute must be specified.");
        }

        // Add a local variable to keep _locale unchanged.
        Locale locale = _locale;

        // if locale not provided, but an attribute was, search for it
        if (locale == null && _localeAttribute != null) {
            locale = (Locale)pageContext.findAttribute(_localeAttribute);
        }

        // if we now have a locale, grab the resource bundle
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (locale != null) {
            _bundle = ResourceBundle.getBundle(_baseName,locale,cl);
            if (_debug) {
                ServletContext sc = pageContext.getServletContext();
                sc.log("i18n:bundle tag debug: found locale " + locale);
            }
        }
        
        // attempt to load the bundle and compute the locale by looping through
        // the browser's preferred locales; cache the final locale for future use
        else {
            Enumeration localeEnumerator = pageContext.getRequest().getLocales();
            while (localeEnumerator.hasMoreElements()) {
                locale = (Locale)localeEnumerator.nextElement();

                if (_debug) {
                    ServletContext sc = pageContext.getServletContext();
                    sc.log("i18n:bundle tag debug: enumerating locale = " + locale);    
                }
                    
                // get a bundle and see whether it has a good locale
                ResourceBundle test = 
                    ResourceBundle.getBundle(_baseName,locale,cl);
                String language = test.getLocale().getLanguage();
                String country = test.getLocale().getCountry();

                // if the requested locale isn't available, the above call will
                // return a bundle with a locale for the same language, or will
                // return the default locale, so we need to compare the locale
                // of the bundle we got back with the one we asked for
                if (test.getLocale().equals(locale)) {
                    // exactly what we were looking for - stop here and use this
                    _bundle = test;
                    break;                
                } else if (locale.getLanguage().equals(language)) {
                    // the language matches; see if the country matches as well
                    if (locale.getCountry().equals(country)) {
                        // keep looking but this is a good option. it only gets
                        // better if the variant matches too!  (note: we will only
                        // get to this statement if a variant has been provided)
                        _bundle = test;
                        continue;
                    } else {
                        // if we don't already have a candidate, this is a good
                        // one otherwise, don't change it because the current
                        // candidate might match the country but not the variant
                        if (_bundle == null) {
                             _bundle = test;
                        }
                        continue;
                    }
                } else if (_debug) {
                    ServletContext sc = pageContext.getServletContext();
                    sc.log("i18n:bundle tag requested locale not available: " + locale);
                }
            }

            // bundle should never be null at this point - if the last locale on
            // the list wasn't available, the default locale should have kicked
            // in, but I like being safe, hence the code below.
            if (_bundle == null) {
                _bundle = ResourceBundle.getBundle(_baseName);
            }

            if (_debug) {
                ServletContext sc = pageContext.getServletContext();
                sc.log("i18n:bundle tag debug: bundle (sensed locale) = " + _bundle);
            }
            
            // if the localeAttribute was provided, but didn't have a value,
            // go ahead and remember the value for next time
            if (_localeAttribute != null) {
                HttpSession session = pageContext.getSession();
                session.setAttribute(_localeAttribute,_bundle.getLocale());
            }
        }

    }

    /**
     *  Called upon invocation of the tag. If an id is specified, sets the
     *  bundle into the page context.
     */
    public int doStartTag() throws JspException
    {
    	// initialize internal member variables
    	_bundle = null;
        findBundle();

        if (_debug) {
            ServletContext sc = pageContext.getServletContext();
            sc.log("i18n:bundle tag debug: basename =" + _baseName);
        }    

        // set the bundle as a variable in the page
        if (this.getId() != null) {
            pageContext.setAttribute(this.getId(),_bundle);
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     *  Sets the response locale if changeResponseLocale attribute is true
     */
    public int doEndTag() throws JspException
    {
        if (_changeResponseLocale) {
            // set the locale for the response
            Locale bundleLocale = _bundle.getLocale();
            if ((bundleLocale != null) && !("".equals(bundleLocale.getLanguage()))) {
                pageContext.getResponse().setLocale(bundleLocale);
            }
        }

        // cache the bundle for use by message tags within this request
        ResourceHelper.setBundle(pageContext,_bundle,_scope);

        return EVAL_PAGE;
    }

}
