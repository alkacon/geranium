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

package com.alkacon.geranium.client.ui;

import com.alkacon.geranium.client.ui.css.I_LayoutBundle;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;

/**
 * Event handler to toggle the {@link com.alkacon.geranium.client.ui.css.I_LayoutBundle.I_StateCss#cmsHovering()} class on mouse out/over.<p>
 */
public class ClassHoverHandler extends A_HoverHandler {

    /** The owner element. */
    protected Element m_owner;

    /**
     * Constructor.<p>
     * 
     * @param owner the owner element
     */
    public ClassHoverHandler(Element owner) {

        m_owner = owner;
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_HoverHandler#onHoverIn(com.google.gwt.event.dom.client.MouseOverEvent)
     */
    @Override
    protected void onHoverIn(MouseOverEvent event) {

        m_owner.addClassName(I_LayoutBundle.INSTANCE.stateCss().cmsHovering());
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_HoverHandler#onHoverOut(com.google.gwt.event.dom.client.MouseOutEvent)
     */
    @Override
    protected void onHoverOut(MouseOutEvent event) {

        m_owner.removeClassName(I_LayoutBundle.INSTANCE.stateCss().cmsHovering());
    }
}
