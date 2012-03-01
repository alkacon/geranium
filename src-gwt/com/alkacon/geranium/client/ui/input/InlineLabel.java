/*
 * This library is part of Geranium -
 * an open source UI library for GWT.
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)-
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.alkacon.geranium.client.ui.input;

import com.alkacon.geranium.client.ui.css.I_InputCss;
import com.alkacon.geranium.client.ui.css.I_InputLayoutBundle;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * A convenience class for creating inline labels.<p>
 * 
 * @since 8.0.0
 */
public class InlineLabel extends Label {

    /** The CSS bundle instance used for this widget.<p> */
    @SuppressWarnings("hiding")
    protected static final I_InputCss CSS = I_InputLayoutBundle.INSTANCE.inputCss();

    /**
     * Creates a new label instance.<p>
     */
    public InlineLabel() {

        super(createElement());
    }

    /**
     * Creates a DIV element with display=inline/inlineBlock.<p>
     * 
     * @return a DIV element (inline or inline-block)
     */
    private static Element createElement() {

        Element element = DOM.createDiv();
        element.addClassName(CSS.inlineBlock());
        return element;
    }

}
