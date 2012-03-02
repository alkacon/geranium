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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * Provides a simple progress bar.<p>
 */
public class ProgressBar extends FlowPanel {

    /** The widget for the completed part of the process. */
    private FlowPanel m_complete = new FlowPanel();

    /** The div element for the percentage text. */
    private HTML m_text = new HTML();

    /**
     * Creates a progress bar.<p>
     * 
     * Initializes the progress bar with 0 percent.<p>
     */
    public ProgressBar() {

        m_text.setStyleName(I_LayoutBundle.INSTANCE.progressBarCss().meterText());

        m_complete.setStyleName(I_LayoutBundle.INSTANCE.progressBarCss().meterValue());
        m_complete.addStyleName(I_LayoutBundle.INSTANCE.progressBarCss().colorComplete());
        m_complete.add(m_text);

        add(m_complete);
        setStyleName(I_LayoutBundle.INSTANCE.progressBarCss().meterWrap());
        addStyleName(I_LayoutBundle.INSTANCE.progressBarCss().colorIncomplete());

        setValue(0);
    }

    /**
     * Sets the progress.<p>
     * 
     * @param percent the percent to set
     */
    public void setValue(int percent) {

        if (percent <= 100) {
            m_text.setText(percent + "%");
            m_complete.setWidth(percent + "%");
        }
    }
}
