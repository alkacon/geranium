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
import com.alkacon.geranium.client.ui.I_Button.ButtonColor;
import com.alkacon.geranium.client.ui.I_Button.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Provides a confirmation dialog with ok and cancel button.<p>
 */
public class ConfirmDialog extends AlertDialog {

    /** The action handler. */
    private I_ConfirmDialogHandler m_handler;

    /** The 'Ok' button. */
    private PushButton m_okButton;

    /** 
     * Constructor.<p>
     * 
     * @param caption the title for this dialog 
     */
    public ConfirmDialog(String caption) {

        this(caption, "");
    }

    /**
     * The constructor.<p>
     * 
     * @param caption the title and heading of the dialog
     * @param content the content text
     */
    public ConfirmDialog(String caption, String content) {

        super(caption, content, Messages.get().key(Messages.GUI_CANCEL_0), null);
        m_okButton = new PushButton();
        m_okButton.setText(Messages.get().key(Messages.GUI_OK_0));
        m_okButton.setUseMinWidth(true);
        m_okButton.setButtonStyle(ButtonStyle.TEXT, ButtonColor.RED);
        m_okButton.addClickHandler(new ClickHandler() {

            /**
             * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
             */
            public void onClick(ClickEvent event) {

                onOk();
            }
        });
        addButton(m_okButton);
    }

    /**
     * @see com.alkacon.geranium.client.ui.Popup#center()
     */
    @Override
    public void center() {

        super.center();
        getOkButton().setEnabled(true);
    }

    /**
     * Returns the 'OK' button widget.<p>
     * 
     * @return the 'OK' button
     */
    public PushButton getOkButton() {

        return m_okButton;
    }

    /**
     * Sets the dialog handler.<p>
     * 
     * @param handler the handler to set
     */
    public void setHandler(I_ConfirmDialogHandler handler) {

        m_handler = handler;
        super.setHandler(handler);
    }

    /**
     * Sets the accept button icon class.<p>
     * 
     * @param iconClass the icon class
     */
    public void setOkIconClass(String iconClass) {

        m_okButton.setImageClass(iconClass);
    }

    /**
     * Sets the accept button text.<p>
     * 
     * @param text the button text
     */
    public void setOkText(String text) {

        m_okButton.setText(text);
    }

    /**
     * @see com.alkacon.geranium.client.ui.Popup#show()
     */
    @Override
    public void show() {

        super.show();
        getOkButton().setEnabled(true);
    }

    /**
     * @see com.alkacon.geranium.client.ui.AlertDialog#getHandler()
     */
    @Override
    protected I_ConfirmDialogHandler getHandler() {

        return m_handler;
    }

    /**
     * Executed on 'ok' click.<p>
     */
    protected void onOk() {

        getOkButton().setEnabled(false);
        if (getHandler() != null) {
            getHandler().onOk();
        }
        hide();
    }
}
