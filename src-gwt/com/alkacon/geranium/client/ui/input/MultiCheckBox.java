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
import com.alkacon.geranium.client.util.ClientStringUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * A form widget consisting of a group of checkboxes.<p>
 */
public class MultiCheckBox extends Composite implements I_FormWidget, I_HasInit {

    /** The type string for this widget. */
    public static final String WIDGET_TYPE = "multiselect";

    /** The list of checkboxes. */
    private List<CheckBox> m_checkboxes = new ArrayList<CheckBox>();

    /** Error display for this widget. */
    private ErrorWidget m_error = new ErrorWidget();

    /** The select options of the multi check box. */
    private Map<String, String> m_items = new LinkedHashMap<String, String>();

    /** Panel which contains all the components of the widget. */
    private Panel m_panel = new FlowPanel();

    /**
     * Constructs a new checkbox group from a map from strings to strings.<p>
     * 
     * The keys of the map are used as the selection values of the checkboxes, while the value
     * for a given key in the map is used as the label for the checkbox which is displayed to the user. 
     * 
     * @param items the map of checkbox options 
     */
    public MultiCheckBox(Map<String, String> items) {

        super();
        init(items);
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

        return I_FormWidget.FieldType.STRING_LIST;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValue()
     */
    public Object getFormValue() {

        return new ArrayList<String>(getSelected());
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getFormValueAsString()
     */
    public String getFormValueAsString() {

        List<String> selected = new ArrayList<String>(getSelected());
        return ClientStringUtil.listAsString(selected, "|");

    }

    /**
     * Returns the set of values of the selected checkboxes.<p>
     * 
     * @return a set of strings
     */
    public Set<String> getSelected() {

        Set<String> result = new HashSet<String>();
        int i = 0;
        for (Map.Entry<String, String> entry : m_items.entrySet()) {
            String key = entry.getKey();
            CheckBox checkBox = m_checkboxes.get(i);
            if (checkBox.isChecked()) {
                result.add(key);
            }
            i += 1;
        }
        return result;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#isEnabled()
     */
    public boolean isEnabled() {

        boolean result = true;
        for (CheckBox checkbox : m_checkboxes) {
            if (!checkbox.isEnabled()) {
                result = false;
            }
        }
        return result;
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#reset()
     */
    public void reset() {

        for (CheckBox checkbox : m_checkboxes) {
            checkbox.setChecked(false);
        }
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

        for (CheckBox checkbox : m_checkboxes) {
            checkbox.setEnabled(enabled);
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
    @SuppressWarnings("unchecked")
    public void setFormValue(Object value) {

        if (value instanceof List<?>) {
            List<String> keys = (List<String>)value;
            Set<String> keySet = new HashSet<String>(keys);
            int i = 0;
            for (Map.Entry<String, String> entry : m_items.entrySet()) {
                String key = entry.getKey();
                CheckBox checkbox = m_checkboxes.get(i);
                checkbox.setChecked(keySet.contains(key));
                i += 1;
            }
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setFormValueAsString(java.lang.String)
     */
    public void setFormValueAsString(String formValue) {

        List<String> values = ClientStringUtil.splitAsList(formValue, "|");
        setFormValue(values);

    }

    /**
     * Initializes the widget given a map of select options.<p>
     * 
     * The keys of the map are the values of the select options, while the values of the map 
     * are the labels which should be used for the checkboxes.
     * 
     * @param items the map of select options
     */
    protected void init(Map<String, String> items) {

        initWidget(m_panel);
        m_items = new LinkedHashMap<String, String>(items);
        m_panel.setStyleName(I_InputLayoutBundle.INSTANCE.inputCss().multiCheckBox());
        m_panel.addStyleName(I_LayoutBundle.INSTANCE.generalCss().textMedium());
        for (Map.Entry<String, String> entry : items.entrySet()) {
            String value = entry.getValue();
            CheckBox checkbox = new CheckBox(value);
            // wrap the check boxes in FlowPanels to arrange them vertically 
            FlowPanel checkboxWrapper = new FlowPanel();
            checkboxWrapper.add(checkbox);
            m_panel.add(checkboxWrapper);
        }
        m_panel.add(m_error);
    }

}
