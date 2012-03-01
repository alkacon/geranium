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
import com.alkacon.geranium.client.util.ClientStringUtil;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Basic text box class for forms.
 * 
 * @since 8.0.0
 * 
 */
public class TextBox extends Composite
implements I_FormWidget, I_HasInit, HasFocusHandlers, HasBlurHandlers, HasValueChangeHandlers<String>,
HasKeyPressHandlers, HasClickHandlers, I_HasBlur, I_HasGhostValue {

    /**
     * Event handler for this text box.<p>
     */
    private class TextBoxHandler
    implements MouseOverHandler, MouseOutHandler, FocusHandler, BlurHandler, ValueChangeHandler<String>,
    KeyPressHandler {

        /** The current text box value. */
        private String m_currentValue;

        /**
         * Constructor.<p>
         * 
         * @param currentValue the current text box value
         */
        protected TextBoxHandler(String currentValue) {

            m_currentValue = currentValue;
        }

        /**
         * @see com.google.gwt.event.dom.client.BlurHandler#onBlur(com.google.gwt.event.dom.client.BlurEvent)
         */
        public void onBlur(BlurEvent event) {

            if (!m_ghostMode && m_textbox.getText().equals("")) {
                setGhostValue(m_ghostValue, true);
            } else if (m_ghostMode) {
                setGhostStyleEnabled(true);
            }
            checkForChange();
        }

        /**
         * @see com.google.gwt.event.dom.client.FocusHandler#onFocus(com.google.gwt.event.dom.client.FocusEvent)
         */
        public void onFocus(FocusEvent event) {

            setGhostStyleEnabled(false);
        }

        /**
         * @see com.google.gwt.event.dom.client.KeyPressHandler#onKeyPress(com.google.gwt.event.dom.client.KeyPressEvent)
         */
        public void onKeyPress(KeyPressEvent event) {

            int keyCode = event.getNativeEvent().getKeyCode();
            if (!isNavigationKey(keyCode)) {
                setGhostMode(false);
            }
            if (isTriggerChangeOnKeyPress()) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                    public void execute() {

                        checkForChange();
                    }
                });

            }
        }

        /**
         * @see com.google.gwt.event.dom.client.MouseOutHandler#onMouseOut(com.google.gwt.event.dom.client.MouseOutEvent)
         */
        public void onMouseOut(MouseOutEvent event) {

            hideError();
        }

        /**
         * @see com.google.gwt.event.dom.client.MouseOverHandler#onMouseOver(com.google.gwt.event.dom.client.MouseOverEvent)
         */
        public void onMouseOver(MouseOverEvent event) {

            if (!isPreventShowError()) {
                showError();
            }
        }

        /**
         * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(ValueChangeEvent event) 
         */
        public void onValueChange(ValueChangeEvent<String> event) {

            setGhostMode(false);
            if ((m_ghostValue != null) && "".equals(m_textbox.getValue())) {
                m_ghostMode = true;
                setGhostStyleEnabled(true);
                m_textbox.setValue(m_ghostValue);
            }
            if (!event.getValue().equals(m_currentValue)) {
                m_currentValue = event.getValue();
                fireValueChangedEvent();
            }
        }

        /**
         * Sets the current value.<p>
         * 
         * @param value the current value 
         */
        public void setValue(String value) {

            m_currentValue = value;
        }

        /**
         * Checks if the current text box value has changed and fires the value changed event.<p>
         */
        protected void checkForChange() {

            if (!m_textbox.getValue().equals(m_currentValue)) {

                m_currentValue = getFormValueAsString();
                fireValueChangedEvent();
            }
        }

    }

    /** The CSS bundle used for this widget. */
    public static final I_InputCss CSS = I_InputLayoutBundle.INSTANCE.inputCss();
    /** The widget type identifier for this widget. */
    public static final String WIDGET_TYPE = "string";

    /** Key codes for functional keys. */
    protected static final int[] NAVIGATION_CODES = {
        KeyCodes.KEY_ALT,
        KeyCodes.KEY_CTRL,
        KeyCodes.KEY_DOWN,
        KeyCodes.KEY_END,
        KeyCodes.KEY_ENTER,
        KeyCodes.KEY_ESCAPE,
        KeyCodes.KEY_HOME,
        KeyCodes.KEY_LEFT,
        KeyCodes.KEY_RIGHT,
        KeyCodes.KEY_SHIFT,
        KeyCodes.KEY_TAB,
        KeyCodes.KEY_UP};

    /** Default pseudo-padding for text boxes. */
    private static final int DEFAULT_PADDING = 4;

    /** A counter used for giving text box widgets ids. */
    private static int idCounter;

    /** Flag for ghost mode. */
    protected boolean m_ghostMode;

    /** The ghost value. */
    protected String m_ghostValue;

    /** The text box used internally by this widget. */
    protected com.google.gwt.user.client.ui.TextBox m_textbox = new com.google.gwt.user.client.ui.TextBox();

    /** Flag indicating if the text box should be cleared when leaving the ghost mode. */
    private boolean m_clearOnChangeMode;

    /** A list of the click handler registrations for this text box. */
    private List<HandlerRegistration> m_clickHandlerRegistrations = new ArrayList<HandlerRegistration>();

    /** A list of the click handlers for this text box. */
    private List<ClickHandler> m_clickHandlers = new ArrayList<ClickHandler>();

    /** Stores the enable/disable state of the textbox. */
    private boolean m_enabled;

    /** The error display for this widget. */
    private ErrorWidget m_error = new ErrorWidget();

    /** The width of the error message. */
    private String m_errorMessageWidth;

    /** The text box handler instance. */
    private TextBoxHandler m_handler;

    /** The container for the textbox container and error widget. */
    private FlowPanel m_panel = new FlowPanel();

    /** Signals whether the error message will be shown on mouse over. */
    private boolean m_preventShowError;

    /** The container for the text box. */
    private PaddedPanel m_textboxContainer = new PaddedPanel(DEFAULT_PADDING);

    /** Flag indicating if the value change event should also be fired after key press events. */
    private boolean m_triggerChangeOnKeyPress;

    /**
     * Constructs a new instance of this widget.
     */
    public TextBox() {

        setEnabled(true);
        m_textbox.setStyleName(CSS.textBox());
        m_textbox.getElement().setId("CmsTextBox_" + (idCounter++));

        TextBoxHandler handler = new TextBoxHandler("");
        m_textbox.addMouseOverHandler(handler);
        m_textbox.addMouseOutHandler(handler);
        m_textbox.addFocusHandler(handler);
        m_textbox.addBlurHandler(handler);
        m_textbox.addValueChangeHandler(handler);
        m_textbox.addKeyPressHandler(handler);

        m_handler = handler;

        m_textboxContainer.setStyleName(CSS.textBoxPanel());
        m_textboxContainer.addStyleName(I_LayoutBundle.INSTANCE.generalCss().cornerAll());
        m_textboxContainer.addStyleName(I_LayoutBundle.INSTANCE.generalCss().textMedium());
        m_panel.add(m_textboxContainer);
        m_panel.add(m_error);
        m_textboxContainer.add(m_textbox);
        m_textboxContainer.setPaddingX(4);
        sinkEvents(Event.ONPASTE);
        initWidget(m_panel);
    }

    /**
     * @see com.google.gwt.event.dom.client.HasBlurHandlers#addBlurHandler(com.google.gwt.event.dom.client.BlurHandler)
     */
    public HandlerRegistration addBlurHandler(BlurHandler handler) {

        return m_textbox.addBlurHandler(handler);
    }

    /**
     * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
     */
    public HandlerRegistration addClickHandler(ClickHandler handler) {

        HandlerRegistration registration = addDomHandler(handler, ClickEvent.getType());
        m_clickHandlerRegistrations.add(registration);
        m_clickHandlers.add(handler);
        return registration;
    }

    /**
     * @see com.google.gwt.event.dom.client.HasFocusHandlers#addFocusHandler(com.google.gwt.event.dom.client.FocusHandler)
     */
    public HandlerRegistration addFocusHandler(FocusHandler handler) {

        return m_textbox.addFocusHandler(handler);
    }

    /**
     * @see com.google.gwt.event.dom.client.HasKeyPressHandlers#addKeyPressHandler(com.google.gwt.event.dom.client.KeyPressHandler)
     */
    public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {

        return addDomHandler(handler, KeyPressEvent.getType());
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

        return addHandler(handler, ValueChangeEvent.getType());

    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_HasBlur#blur()
     */
    public void blur() {

        m_textbox.getElement().blur();
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getApparentValue()
     */
    public String getApparentValue() {

        String result = m_textbox.getValue();
        if (ClientStringUtil.isEmpty(result)) {
            result = null;
        }
        return result;
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

        String result = m_textbox.getText();
        if (result.equals("")) {
            result = null;
        }
        return result;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValueAsString()
     */
    public String getFormValueAsString() {

        if (m_ghostMode) {
            return null;
        }
        return (String)getFormValue();
    }

    /** 
     * Returns the HTML id of the internal textbox used by this widget.<p>
     * 
     * @return the HTML id of the internal textbox used by this widget
     */
    public String getId() {

        return m_textbox.getElement().getId();
    }

    /**
     * Returns the text in the text box.<p>
     * 
     * @return the text 
     */
    public String getText() {

        return m_textbox.getText();
    }

    /**
     * Returns <code>true</code> if this textbox has an error set.<p>
     * 
     * @return <code>true</code> if this textbox has an error set
     */
    public boolean hasError() {

        return m_error.hasError();
    }

    /**
     * Gets whether this widget is enabled.
     * 
     * @return <code>true</code> if the widget is enabled
     */
    public boolean isEnabled() {

        return m_enabled;
    }

    /**
     * Returns the preventShowError.<p>
     *
     * @return the preventShowError
     */
    public boolean isPreventShowError() {

        return m_preventShowError;
    }

    /**
     * Returns the read only flag.<p>
     * 
     * @return <code>true</code> if this text box is only readable
     */
    public boolean isReadOnly() {

        return m_textbox.isReadOnly();
    }

    /**
     * Returns if the text box is set to trigger the value changed event on key press and not on blur only.<p>
     * 
     * @return <code>true</code> if the text box is set to trigger the value changed event on key press
     */
    public boolean isTriggerChangeOnKeyPress() {

        return m_triggerChangeOnKeyPress;
    }

    /**
     * @see com.google.gwt.user.client.ui.Composite#onBrowserEvent(com.google.gwt.user.client.Event)
     */
    @Override
    public void onBrowserEvent(Event event) {

        super.onBrowserEvent(event);
        /* 
         * In IE8, the change event is not fired if we switch to another application window after having 
         * pasted some text into the text box, so we need to turn off ghost mode manually 
         */
        if (event.getTypeInt() == Event.ONPASTE) {
            setGhostMode(false);
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#reset()
     */
    public void reset() {

        m_textbox.setText("");
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setAutoHideParent(com.alkacon.geranium.client.ui.I_AutoHider)
     */
    public void setAutoHideParent(I_AutoHider autoHideParent) {

        // nothing to do
    }

    /**
     * Sets the changed style on the text box.<p>
     */
    public void setChangedStyle() {

        m_textbox.addStyleName(CSS.changed());
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {

        if (!m_enabled && enabled) {
            // if the state changed to enable then add the stored handlers
            // copy the stored handlers into a new list to avoid concurred access to the list
            List<ClickHandler> handlers = new ArrayList<ClickHandler>(m_clickHandlers);
            m_clickHandlers.clear();
            for (ClickHandler handler : handlers) {
                addClickHandler(handler);
            }
            m_textboxContainer.removeStyleName(CSS.textBoxPanelDisabled());
            m_enabled = true;
        } else if (m_enabled && !enabled) {
            // if state changed to disable then remove all click handlers
            for (HandlerRegistration registration : m_clickHandlerRegistrations) {
                registration.removeHandler();
            }
            m_clickHandlerRegistrations.clear();
            m_textboxContainer.addStyleName(CSS.textBoxPanelDisabled());
            setErrorMessage(null);
            m_enabled = false;
        }
        m_textbox.setEnabled(m_enabled);
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setErrorMessage(java.lang.String)
     */
    public void setErrorMessage(String errorMessage) {

        if (ClientStringUtil.isNotEmptyOrWhitespaceOnly(errorMessage)) {
            if (ClientStringUtil.isNotEmptyOrWhitespaceOnly(m_errorMessageWidth)) {
                m_error.setWidth(m_errorMessageWidth);
            } else {
                m_error.setWidth((getOffsetWidth() - 8) + Unit.PX.toString());
            }
            m_textboxContainer.removeStyleName(CSS.textBoxPanel());
            m_textboxContainer.addStyleName(CSS.textBoxPanelError());
        } else {
            m_textboxContainer.removeStyleName(CSS.textBoxPanelError());
            m_textboxContainer.addStyleName(CSS.textBoxPanel());
        }
        m_error.setText(errorMessage);
    }

    /**
     * Sets the width of the error message for this textbox.<p>
     * 
     * @param width the object's new width, in CSS units (e.g. "10px", "1em")
     */
    public void setErrorMessageWidth(String width) {

        m_errorMessageWidth = width;
    }

    /**
     * Sets the focus on the text box.<p>
     * 
     * @param focused signals if the focus should be set
     */
    public void setFocus(boolean focused) {

        m_textbox.setFocus(focused);
    }

    /**
     * Sets the value of the widget.<p>
     * 
     * @param value the new value 
     */
    public void setFormValue(Object value) {

        if (value instanceof String) {
            String strValue = (String)value;
            setText(strValue);
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setFormValueAsString(java.lang.String)
     */
    public void setFormValueAsString(String newValue) {

        if (newValue == null) {
            newValue = "";
        }
        if ("".equals(newValue) && (m_ghostValue != null)) {
            m_ghostMode = true;
            setGhostStyleEnabled(true);
            m_textbox.setValue(m_ghostValue);
        } else {
            setFormValue(newValue);
        }
    }

    /**
     * Enables or disables ghost mode.<p>
     * 
     * @param ghostMode if true, enables ghost mode, else disables it 
     */
    public void setGhostMode(boolean ghostMode) {

        //setGhostStyleEnabled(ghostMode);
        m_ghostMode = ghostMode;

    }

    /**
     * Sets if the input field should be cleared when leaving the ghost mode.<p>
     * 
     * @param clearOnChangeMode <code>true</code> to clear on leaving the ghost mode
     */
    public void setGhostModeClear(boolean clearOnChangeMode) {

        m_clearOnChangeMode = clearOnChangeMode;
    }

    /**
     * Enables or disables the "ghost mode" style.<p>
     * 
     * This *only* changes the style, not the actual mode.
     * 
     * @param enabled true if the ghost mode style should be enabled, false if it should be disabled 
     */
    public void setGhostStyleEnabled(boolean enabled) {

        if (enabled) {
            m_textbox.addStyleName(CSS.textboxGhostMode());
        } else {
            m_textbox.removeStyleName(CSS.textboxGhostMode());
            if (m_clearOnChangeMode) {
                setText("");
            }
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_HasGhostValue#setGhostValue(java.lang.String, boolean)
     */
    public void setGhostValue(String value, boolean ghostMode) {

        m_ghostValue = value;
        if (!ghostMode) {
            return;
        }
        m_textbox.setValue(value);
        setGhostMode(true);
        setGhostStyleEnabled(true);
    }

    /**
     * Sets the preventShowError.<p>
     *
     * @param preventShowError the preventShowError to set
     */
    public void setPreventShowError(boolean preventShowError) {

        m_preventShowError = preventShowError;
        if (preventShowError) {
            m_error.setErrorVisible(false);
        }
    }

    /**
     * Enables or disables read-only mode.<p>
     * 
     * @param readOnly if true, enables read-only mode, else disables it
     */
    public void setReadOnly(boolean readOnly) {

        m_textbox.setReadOnly(readOnly);
        if (readOnly) {
            addStyleName(CSS.textBoxReadOnly());
        } else {
            removeStyleName(CSS.textBoxReadOnly());
        }
    }

    /**
     * Sets if the value changed event should be triggered on key press and not on blur only.<p>
     * 
     * @param triggerOnKeyPress <code>true</code> if the value changed event should be triggered on key press
     */
    public void setTriggerChangeOnKeyPress(boolean triggerOnKeyPress) {

        m_triggerChangeOnKeyPress = triggerOnKeyPress;
    }

    /**
     * Updates the layout of the text box.<p>
     */
    public void updateLayout() {

        m_textboxContainer.updatePadding();

    }

    /** 
     * Helper method for firing a 'value changed' event.<p>
     */
    protected void fireValueChangedEvent() {

        ValueChangeEvent.fire(this, getFormValueAsString());
    }

    /**
     * Hides the error for this textbox.<p>
     */
    protected void hideError() {

        m_error.hideError();
    }

    /**
     * Checks if the given key code represents a functional key.<p>
     * 
     * @param keyCode the key code to check
     * 
     * @return <code>true</code> if the given key code represents a functional key
     */
    protected boolean isNavigationKey(int keyCode) {

        for (int i = 0; i < NAVIGATION_CODES.length; i++) {
            if (NAVIGATION_CODES[i] == keyCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shows the error for this textbox.<p>
     */
    protected void showError() {

        m_error.showError();
    }

    /**
     * Sets the text in the text box.<p>
     * 
     * @param text the new text
     */
    private void setText(String text) {

        m_textbox.setText(text);
        m_handler.setValue(text);
    }

}