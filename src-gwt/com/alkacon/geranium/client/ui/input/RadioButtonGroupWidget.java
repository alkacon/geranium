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
import com.alkacon.geranium.client.ui.css.I_InputLayoutBundle;
import com.alkacon.geranium.client.ui.css.I_LayoutBundle;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * Widget class consisting of a group of radio buttons, of which at most one may be active.<p>
 * 
 * This is mostly a 'convenience widget' for creating and handling multiple radio buttons as a single widget.
 * The radio buttons will be layed out vertically. If you need more control about the layout of the radio 
 * buttons, use multiple {@link RadioButton} instances and link them with a {@link RadioButtonGroup}.<p>
 */
public class RadioButtonGroupWidget extends Composite
implements I_FormWidget, HasValueChangeHandlers<String>, I_HasInit {

    /** The widget type identifier. */
    public static final String WIDGET_TYPE = "radio";

    /** The event bus. */
    protected SimpleEventBus m_eventBus;

    /** The error display used by this widget. */
    private ErrorWidget m_error = new ErrorWidget();

    /** The radio button group for this radio button. */
    private RadioButtonGroup m_group = new RadioButtonGroup();

    /** The root panel containing all other components of this widget. */
    private Panel m_panel = new FlowPanel();

    /** A map which stores all radio buttons using their value as keys. */
    private Map<String, RadioButton> m_radioButtons;

    /**
     * Creates a new instance from a map of strings.<p>
     * 
     * The keys of the map are used as the values of the radio buttons, and the values of the map are used as labels 
     * for the radio buttons.
     *  
     * @param items the string map containing the select options 
     */
    public RadioButtonGroupWidget(Map<String, String> items) {

        init(items);

    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<String> handler) {

        return m_eventBus.addHandlerToSource(ValueChangeEvent.getType(), this, handler);
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#fireEvent(com.google.gwt.event.shared.GwtEvent)
     */
    @Override
    public void fireEvent(GwtEvent<?> event) {

        m_eventBus.fireEventFromSource(event, this);
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

        RadioButton button = m_group.getSelectedButton();
        if (button == null) {
            return "";
        } else {
            return button.getName();
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValueAsString()
     */
    public String getFormValueAsString() {

        return (String)getFormValue();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#isEnabled()
     */
    public boolean isEnabled() {

        boolean result = true;
        for (Map.Entry<String, RadioButton> entry : m_radioButtons.entrySet()) {
            if (!entry.getValue().isEnabled()) {
                result = false;
            }
        }
        return result;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#reset()
     */
    public void reset() {

        m_group.deselectButton();
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

        for (Map.Entry<String, RadioButton> entry : m_radioButtons.entrySet()) {
            entry.getValue().setEnabled(enabled);
        }
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
            if (strValue.equals("")) {
                // interpret empty string as "no radio button selected"
                reset();
            } else {
                RadioButton button = m_radioButtons.get(value);
                m_group.selectButton(button);
            }
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setFormValueAsString(java.lang.String)
     */
    public void setFormValueAsString(String formValue) {

        setFormValue(formValue);
    }

    /**
     * Internal method for initializing the widget with a list of select options.<p>
     * 
     * @param items the list of select options 
     */
    protected void init(Map<String, String> items) {

        initWidget(m_panel);
        m_eventBus = new SimpleEventBus();
        m_radioButtons = new HashMap<String, RadioButton>();
        for (Map.Entry<String, String> entry : items.entrySet()) {

            final RadioButton button = new RadioButton(entry.getKey(), entry.getValue());
            button.setGroup(m_group);
            m_radioButtons.put(entry.getKey(), button);
            FlowPanel wrapper = new FlowPanel();
            wrapper.add(button);
            m_panel.add(wrapper);
        }
        m_panel.add(m_error);
        m_panel.setStyleName(I_InputLayoutBundle.INSTANCE.inputCss().radioButtonGroup());
        m_panel.addStyleName(I_LayoutBundle.INSTANCE.generalCss().textMedium());
        m_group.setValueChangeTarget(this);
    }

    /**
     * Fires a ValueChangedEvent on this widget.<p>
     *  
     * @param newValue the new value of this widget
     */
    void fireValueChangedEvent(String newValue) {

        ValueChangeEvent.fire(this, newValue);
    }

}
