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

import com.alkacon.geranium.client.Messages;
import com.alkacon.geranium.client.ui.css.I_LayoutBundle;
import com.alkacon.geranium.client.ui.css.I_ToolbarButtonLayoutBundle;
import com.alkacon.geranium.client.ui.css.I_ToolbarButtonLayoutBundle.I_ToolbarButtonCss;

/**
 * Interface to hold button related enumerations. To be used with {@link com.alkacon.geranium.client.ui.PushButton}
 * and {@link com.alkacon.geranium.client.ui.ToggleButton}.<p>
 */
public interface I_Button {

    /** Available button colors. */
    public enum ButtonColor {

        /** Button color. */
        BLACK(I_LayoutBundle.INSTANCE.buttonCss().black()),
        /** Button color. */
        BLUE(I_LayoutBundle.INSTANCE.buttonCss().blue()),
        /** Button color. */
        GRAY(I_LayoutBundle.INSTANCE.buttonCss().gray()),
        /** Button color. */
        GREEN(I_LayoutBundle.INSTANCE.buttonCss().green()),
        /** Button color. */
        RED(I_LayoutBundle.INSTANCE.buttonCss().red()),
        /** Button color. */
        YELLOW(I_LayoutBundle.INSTANCE.buttonCss().yellow());

        /** The list of additional style class names for this button style. */
        private String m_className;

        /**
         * Constructor.<p>
         * 
         * @param className the additional classes
         */
        private ButtonColor(String className) {

            m_className = className;
        }

        /**
         * Returns the additional classes.<p>
         *
         * @return the additional classes
         */
        public String getClassName() {

            return m_className;
        }
    }

    /** Available button icons. */
    public enum ButtonData {

        /** Toolbar button. */
        ADD(BUTTON_CSS.toolbarAdd(), Messages.get().key(Messages.GUI_TOOLBAR_ADD_0)),

        /** Toolbar button. */
        ADD_TO_FAVORITES(BUTTON_CSS.toolbarClipboard(), Messages.get().key(Messages.GUI_TOOLBAR_ADD_TO_FAVORITES_0)),

        /** Toolbar button. */
        CLIPBOARD(BUTTON_CSS.toolbarClipboard(), Messages.get().key(Messages.GUI_TOOLBAR_CLIPBOARD_0)),

        /** Toolbar button. */
        CONTEXT(BUTTON_CSS.toolbarContext(), Messages.get().key(Messages.GUI_TOOLBAR_CONTEXT_0)),

        /** Toolbar button. */
        DELETE(BUTTON_CSS.toolbarDelete(), Messages.get().key(Messages.GUI_TOOLBAR_DELETE_0)),

        /** Toolbar button. */
        EDIT(BUTTON_CSS.toolbarEdit(), Messages.get().key(Messages.GUI_TOOLBAR_EDIT_0)),

        /** Toolbar button. */
        MOVE(BUTTON_CSS.toolbarMove(), Messages.get().key(Messages.GUI_TOOLBAR_MOVE_IN_0)),

        /** Toolbar button. */
        NEW(BUTTON_CSS.toolbarNew(), Messages.get().key(Messages.GUI_TOOLBAR_NEW_0)),

        /** Toolbar button. */
        PROPERTIES(BUTTON_CSS.toolbarProperties(), Messages.get().key(Messages.GUI_TOOLBAR_PROPERTIES_0)),

        /** Toolbar button. */
        PUBLISH(BUTTON_CSS.toolbarPublish(), Messages.get().key(Messages.GUI_TOOLBAR_PUBLISH_0)),

        /** Toolbar button. */
        REMOVE(BUTTON_CSS.toolbarRemove(), Messages.get().key(Messages.GUI_TOOLBAR_REMOVE_0)),

        /** Toolbar button. */
        RESET(BUTTON_CSS.toolbarReset(), Messages.get().key(Messages.GUI_TOOLBAR_RESET_0)),

        /** Toolbar button. */
        SAVE(BUTTON_CSS.toolbarSave(), Messages.get().key(Messages.GUI_TOOLBAR_SAVE_0)),

        /** Toolbar button. */
        SELECTION(BUTTON_CSS.toolbarSelection(), Messages.get().key(Messages.GUI_TOOLBAR_SELECTION_0)),

        /** Toolbar button. */
        SITEMAP(BUTTON_CSS.toolbarSitemap(), Messages.get().key(Messages.GUI_TOOLBAR_SITEMAP_0)),

        /** Toolbar button. */
        BACK(BUTTON_CSS.toolbarBack(), Messages.get().key(Messages.GUI_TOOLBAR_BACK_0)),

        /** Shows formerly hidden elements. */
        SHOW(BUTTON_CSS.toolbarNew(), Messages.get().key(Messages.GUI_TOOLBAR_ADD_0)),

        /** Inherited element button. */
        INHERITED(BUTTON_CSS.toolbarInherited(), "Inherited"),

        /** Toolbar button. */
        SHOWSMALL(BUTTON_CSS.toolbarShowSmall(), Messages.get().key(Messages.GUI_TOOLBAR_SHOWSMALL_0));

        /** The icon class name. */
        private String m_iconClass;

        /** The title. */
        private String m_title;

        /**
         * Constructor.<p>
         * 
         * @param iconClass the icon class name
         * @param title the title
         */
        private ButtonData(String iconClass, String title) {

            m_iconClass = iconClass;
            m_title = title;
        }

        /**
         * Returns the CSS class name.<p>
         * 
         * @return the CSS class name
         */
        public String getIconClass() {

            return m_iconClass;
        }

        /**
         * Returns the title.<p>
         * 
         * @return the title
         */
        public String getTitle() {

            return m_title;
        }
    }

    /** Available button styles. */
    public enum ButtonStyle {

        /** Menu button. */
        IMAGE(I_LayoutBundle.INSTANCE.buttonCss().cmsImageButton(), I_LayoutBundle.INSTANCE.generalCss().cornerAll()),

        /** Menu button. */
        MENU(I_LayoutBundle.INSTANCE.buttonCss().cmsMenuButton(), I_LayoutBundle.INSTANCE.generalCss().cornerAll()),

        /** Default button. */
        TEXT(I_LayoutBundle.INSTANCE.buttonCss().cmsTextButton(),
        I_LayoutBundle.INSTANCE.generalCss().buttonCornerAll()),

        /** Transparent button. */
        TRANSPARENT(I_LayoutBundle.INSTANCE.buttonCss().cmsTransparentButton(),
        I_LayoutBundle.INSTANCE.generalCss().cornerAll());

        /** The list of additional style class names for this button style. */
        private String[] m_additionalClasses;

        /**
         * Constructor.<p>
         * 
         * @param additionalClasses the additional classes
         */
        private ButtonStyle(String... additionalClasses) {

            m_additionalClasses = additionalClasses;
        }

        /**
         * Returns the additional classes.<p>
         *
         * @return the additional classes
         */
        public String[] getAdditionalClasses() {

            return m_additionalClasses;
        }

        /**
         * Returns the classes stored in the array as space separated list.<p>
         * 
         * @return the classes stored in the array as space separated list
         */
        public String getCssClassName() {

            StringBuffer sb = new StringBuffer();
            for (String addClass : m_additionalClasses) {
                sb.append(addClass + " ");
            }
            return sb.toString();
        }
    }

    /** CSS style variants. */
    public static enum Size {

        /** Big button style. */
        big(I_LayoutBundle.INSTANCE.buttonCss().cmsButtonBig()),

        /** Medium button style. */
        medium(I_LayoutBundle.INSTANCE.buttonCss().cmsButtonMedium()),

        /** Small button style. */
        small(I_LayoutBundle.INSTANCE.buttonCss().cmsButtonSmall());

        /** The CSS class name. */
        private String m_cssClassName;

        /**
         * Constructor.<p>
         * 
         * @param cssClassName the CSS class name
         */
        Size(String cssClassName) {

            m_cssClassName = cssClassName;
        }

        /**
         * Returns the CSS class name of this style.<p>
         * 
         * @return the CSS class name
         */
        public String getCssClassName() {

            return m_cssClassName;
        }
    }

    /** The CSS bundle for the toolbar buttons. */
    I_ToolbarButtonCss BUTTON_CSS = I_ToolbarButtonLayoutBundle.INSTANCE.toolbarButtonCss();
}
