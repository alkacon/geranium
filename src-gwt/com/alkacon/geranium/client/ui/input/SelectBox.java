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
import com.alkacon.geranium.client.Messages;
import com.alkacon.geranium.client.ui.I_AutoHider;
import com.alkacon.geranium.client.util.ClientStringUtil;

import java.util.Map;

/**
 * Widget for selecting one of multiple items from a drop-down list which opens
 * after the user clicks on the widget.<p>
 * 
 * @since 8.0.0
 * 
 */
public class SelectBox extends A_SelectBox<LabelSelectCell> implements I_HasInit, I_HasGhostValue {

    /** Text metrics key. */
    private static final String TM_OPENER_LABEL = "OpenerLabel";

    /** The widget type identifier. */
    private static final String WIDGET_TYPE = "select";

    /** The key for the text which should be displayed if no option is available. */
    public static final String NO_SELECTION_TEXT = "%NO_SELECTION_TEXT%";

    /** The widget displayed in the opener. */
    protected Label m_openerWidget;

    /** The ghost value. */
    private String m_ghostValue;

    /** A map from select options to their label texts. */
    private Map<String, String> m_items;

    /** The text which should be displayed if there is no selection. */
    private String m_noSelectionText;

    /**
     * Default constructor.<p>
     */
    public SelectBox() {

        super();

    }

    /**
     * Constructs a new select box from a map.<p>
     * 
     * The keys of the map are the values of the select options, and the values of the map are the labels to be displayed
     * for each option.
     * 
     * @param items the map of select options 
     */
    public SelectBox(Map<String, String> items) {

        super();
        setItems(items);
    }

    /**
     * Creates a new select box, with the option of adding a "not selected" choice.<p>
     * 
     * @param items the map of select options 
     * @param addNullOption if true, a "not selected" option will be added to the select box 
     */
    public SelectBox(Map<String, String> items, boolean addNullOption) {

        super();
        if (items.containsKey(NO_SELECTION_TEXT)) {
            m_noSelectionText = items.get(NO_SELECTION_TEXT);
            items.remove(NO_SELECTION_TEXT);
        }
        if (addNullOption) {
            String text = Messages.get().key(Messages.GUI_SELECTBOX_EMPTY_SELECTION_0);
            items.put("", text);
        }
        setItems(items);
        if (addNullOption) {
            selectValue("");
        }
    }

    /**
     * Adds a new selection cell.<p>
     * 
     * @param value the value of the select option 
     * @param text the text to be displayed for the select option 
     */
    public void addOption(String value, String text) {

        LabelSelectCell cell = new LabelSelectCell(value, text);
        addOption(cell);
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#getApparentValue()
     */
    public String getApparentValue() {

        String val = getFormValueAsString();
        if (val == null) {
            val = m_ghostValue;
        }
        return val;

    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_FormWidget#setAutoHideParent(com.alkacon.geranium.client.ui.I_AutoHider)
     */
    public void setAutoHideParent(I_AutoHider autoHideParent) {

        // nothing to do

    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_HasGhostValue#setGhostMode(boolean)
     */
    public void setGhostMode(boolean ghostMode) {

        // do nothing for now 

    }

    /**
     * @see com.alkacon.geranium.client.ui.input.I_HasGhostValue#setGhostValue(java.lang.String, boolean)
     */
    public void setGhostValue(String value, boolean ghostMode) {

        if (value == null) {
            value = "";
        }
        String otherOptionText = m_items.get(value);
        String message = m_noSelectionText != null ? m_noSelectionText : Messages.get().key(
            Messages.GUI_SELECTBOX_EMPTY_SELECTION_1);
        message = com.alkacon.geranium.client.util.Messages.formatMessage(message, otherOptionText);
        setTextForNullSelection(message);
        if (ghostMode) {
            selectValue("");
            m_ghostValue = value;
        }
    }

    /**
     * Sets the items using a map from option values to label texts.<p>
     * 
     * @param items the map containing the select options
     */
    public void setItems(Map<String, String> items) {

        clearItems();
        m_items = items;
        for (Map.Entry<String, String> entry : items.entrySet()) {
            addOption(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets the text that is used for the "not selected" option.<p>
     * 
     * @param text the text which should be used for the "not selected" option 
     */
    public void setTextForNullSelection(String text) {

        // do nothing if there's no null option
        LabelSelectCell cell = m_selectCells.get("");
        if (cell == null) {
            return;
        }
        cell.setText(text);

        // if the null option is selected, we still need to update the opener 
        if (ClientStringUtil.isEmptyOrWhitespaceOnly(m_selectedValue)) {
            selectValue("");
        }
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.A_SelectBox#truncateOpener(java.lang.String, int)
     */
    @Override
    public void truncateOpener(String prefix, int width) {

        m_openerWidget.truncate(prefix + '_' + TM_OPENER_LABEL, width);
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.A_SelectBox#createUnknownOption(java.lang.String)
     */
    @Override
    protected LabelSelectCell createUnknownOption(String value) {

        LabelSelectCell cell = new LabelSelectCell(value, value);
        return cell;

    }

    /**
     * @see com.alkacon.geranium.client.ui.input.A_SelectBox#initOpener()
     */
    @Override
    protected void initOpener() {

        m_openerWidget = new Label();
        m_openerWidget.addStyleName(CSS.selectBoxOpener());
        m_opener.add(m_openerWidget);
    }

    /**
     * @see com.alkacon.geranium.client.ui.input.A_SelectBox#updateOpener(java.lang.String)
     */
    @Override
    protected void updateOpener(String newValue) {

        Label label = m_openerWidget;
        LabelSelectCell cell = m_selectCells.get(newValue);
        label.setText(cell.getText());
        label.setTitle(cell.getText());
    }

}
