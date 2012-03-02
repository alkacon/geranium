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

import com.alkacon.geranium.client.I_HasInit;
import com.alkacon.geranium.client.ui.I_AutoHider;
import com.alkacon.geranium.client.ui.css.I_InputCss;
import com.alkacon.geranium.client.ui.css.I_InputLayoutBundle;
import com.alkacon.geranium.client.ui.css.I_LayoutBundle;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * Basic text area widget for forms.<p>
 */
public class TextArea extends Composite implements I_FormWidget, I_HasInit {

    /** The CSS bundle for this widget. */
    private static I_InputCss CSS = I_InputLayoutBundle.INSTANCE.inputCss();

    /** Default padding for text areas. */
    private static final int DEFAULT_PADDING = 4;

    /** The widget type identifier for this widget. */
    private static final String WIDGET_TYPE = "textarea";

    /** The error display for this widget. */
    private ErrorWidget m_error = new ErrorWidget();

    /** The root panel containing the other components of this widget. */
    private Panel m_panel = new FlowPanel();

    /** The internal text area widet used by this widget. */
    private com.google.gwt.user.client.ui.TextArea m_textArea = new com.google.gwt.user.client.ui.TextArea();

    /** The container for the text area. */
    private PaddedPanel m_textAreaContainer = new PaddedPanel(DEFAULT_PADDING);

    /**
     * Text area widgets for ADE forms.<p>
     */
    public TextArea() {

        super();
        initWidget(m_panel);
        m_panel.add(m_textAreaContainer);
        m_textAreaContainer.add(m_textArea);
        m_panel.add(m_error);
        m_textArea.addStyleName(CSS.textArea());
        m_textAreaContainer.addStyleName(CSS.textAreaContainer());
        m_textAreaContainer.addStyleName(I_LayoutBundle.INSTANCE.generalCss().cornerAll());
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getApparentValue()
     */
    public String getApparentValue() {

        return getFormValueAsString();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFieldType()
     */
    public FieldType getFieldType() {

        return I_FormWidget.FieldType.STRING;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValue()
     */
    public Object getFormValue() {

        return m_textArea.getText();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValueAsString()
     */
    public String getFormValueAsString() {

        return (String)getFormValue();
    }

    /**
     * Returns the text contained in the text area.<p>
     * 
     * @return the text in the text area
     */
    public String getText() {

        return m_textArea.getText();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#isEnabled()
     */
    public boolean isEnabled() {

        return m_textArea.isEnabled();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#reset()
     */
    public void reset() {

        m_textArea.setText("");
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setAutoHideParent(com.alkacon.geranium.client.ui.I_AutoHider)
     */
    public void setAutoHideParent(I_AutoHider autoHideParent) {

        // nothing to do
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {

        m_textArea.setEnabled(enabled);
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setErrorMessage(java.lang.String)
     */
    public void setErrorMessage(String errorMessage) {

        m_error.setText(errorMessage);
    }

    /**
     * Sets the value of the widget.<p>
     * 
     * @param value the new value 
     */
    public void setFormValue(Object value) {

        if (value == null) {
            value = "";
        }
        if (value instanceof String) {
            String strValue = (String)value;
            m_textArea.setText(strValue);
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setFormValueAsString(java.lang.String)
     */
    public void setFormValueAsString(String newValue) {

        setFormValue(newValue);
    }

    /**
     * Sets the text in the text area.<p>
     * 
     * @param text the new text
     */
    public void setText(String text) {

        m_textArea.setText(text);
    }
}
