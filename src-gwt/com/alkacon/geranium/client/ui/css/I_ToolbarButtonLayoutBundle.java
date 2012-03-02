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
import com.google.gwt.resources.client.CssResource.Shared;

/**
 * Resource bundle to access CSS and image resources for tool-bar buttons.<p>
 */
public interface I_ToolbarButtonLayoutBundle extends I_BigIconBundle {

    /** The button CSS. */
    @Shared
    public interface I_ToolbarButtonCss extends I_LayoutBundle.I_StateCss {

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarAdd();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarBack();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarClipboard();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarContext();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarDelete();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarEdit();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarInherited();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarMove();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarNew();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarProperties();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarPublish();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarRemove();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarReset();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarSave();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarSelection();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarShowSmall();

        /** Access method.<p>
         * 
         * @return the CSS class name
         */
        String toolbarSitemap();
    }

    /** The bundle instance. */
    I_ToolbarButtonLayoutBundle INSTANCE = GWT.create(I_ToolbarButtonLayoutBundle.class);

    /**
     * Access method.<p>
     * 
     * @return the button CSS
     */
    @Source("toolbarButton.css")
    I_ToolbarButtonCss toolbarButtonCss();

}
