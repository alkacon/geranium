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

package com.alkacon.geranium.client.ui.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * Layout bundle.<p>
 */
public interface I_InputLayoutBundle extends ClientBundle {

    /** The bundle instance. */
    I_InputLayoutBundle INSTANCE = GWT.create(I_InputLayoutBundle.class);

    /**
     * Image bundle accessor.<p>
     * 
     * @return an image bundle 
     */
    I_BackgroundImageBundle backgrounds();

    /**
     * Image bundle accessor.<p>
     * 
     * @return an image bundle 
     */
    I_ImageBundle baseImages();

    /**
     * The CSS constants bundle.<p>
     * 
     * @return a bundle of CSS constants 
     */
    I_ConstantsBundle constants();

    /**
     * Accessor for css resource.<p>
     *  
     * @return the css resource
     */
    @Source("input.css")
    I_InputCss inputCss();

    /**
     * Returns the image bundle for the input package.<p>
     * 
     * @return an image bundle
     */
    I_InputImageBundle inputImages();
}
