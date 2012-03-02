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

import com.alkacon.geranium.client.ui.I_Button.ButtonColor;
import com.alkacon.geranium.client.ui.I_Button.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Provides a confirmation dialog with yes, no and cancel button.<p>
 */
public class AcceptDeclineCancelDialog extends AlertDialog {

    /** The 'accept' button. */
    private PushButton m_acceptButton;

    /** The 'decline' button. */
    private PushButton m_declineButton;

    /** The dialog handler. */
    private I_AcceptDeclineCancelHandler m_handler;

    /**
     * Constructor.<p>
     * 
     * @param title the dialog title
     * @param content the dialog content
     */
    public AcceptDeclineCancelDialog(String title, String content) {

        super(title, content);
        m_acceptButton = new PushButton();
        m_acceptButton.setUseMinWidth(true);
        m_acceptButton.setButtonStyle(ButtonStyle.TEXT, ButtonColor.GREEN);
        m_acceptButton.addClickHandler(new ClickHandler() {

            /**
             * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
             */
            public void onClick(ClickEvent event) {

                onAccept();
            }
        });
        m_declineButton = new PushButton();
        m_declineButton.setUseMinWidth(true);
        m_declineButton.setButtonStyle(ButtonStyle.TEXT, ButtonColor.RED);
        m_declineButton.addClickHandler(new ClickHandler() {

            /**
             * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
             */
            public void onClick(ClickEvent event) {

                onDecline();
            }
        });
        addButton(m_declineButton);
        addButton(m_acceptButton);
    }

    /**
     * @see com.alkacon.geranium.client.ui.AlertDialog#center()
     */
    @Override
    public void center() {

        activateButtons(true);
        super.center();
    }

    /**
     * Sets the accept button icon class.<p>
     * 
     * @param iconClass the icon class
     */
    public void setAcceptIconClass(String iconClass) {

        m_acceptButton.setImageClass(iconClass);
    }

    /**
     * Sets the accept button text.<p>
     * 
     * @param text the button text
     */
    public void setAcceptText(String text) {

        m_acceptButton.setText(text);
    }

    /**
     * Sets the decline button icon class.<p>
     * 
     * @param iconClass the icon class
     */
    public void setDeclineIconClass(String iconClass) {

        m_declineButton.setImageClass(iconClass);
    }

    /**
     * Sets the decline button text.<p>
     * 
     * @param text the button text
     */
    public void setDeclineText(String text) {

        m_declineButton.setText(text);
    }

    /**
     * Sets the dialog handler.<p>
     * 
     * @param handler the handler to set
     */
    public void setHandler(I_AcceptDeclineCancelHandler handler) {

        m_handler = handler;
        super.setHandler(handler);
    }

    /**
     * Sets the buttons enabled.<p>
     * 
     * @param activate <code>true</code> to activate, <code>false</code> to deactivate
     */
    protected void activateButtons(boolean activate) {

        m_acceptButton.setEnabled(activate);
        m_declineButton.setEnabled(activate);
        getCloseButton().setEnabled(activate);
    }

    /**
     * @see com.alkacon.geranium.client.ui.AlertDialog#getHandler()
     */
    @Override
    protected I_AcceptDeclineCancelHandler getHandler() {

        return m_handler;
    }

    /**
     * Executed on accept click.<p>
     */
    protected void onAccept() {

        activateButtons(false);
        if (getHandler() != null) {
            getHandler().onAccept();
        }
        hide();
    }

    /**
     * @see com.alkacon.geranium.client.ui.AlertDialog#onClose()
     */
    @Override
    protected void onClose() {

        activateButtons(false);
        if (getHandler() != null) {
            getHandler().onClose();
        }
        hide();
    }

    /**
     * Executed on decline click.<p>
     */
    protected void onDecline() {

        activateButtons(false);
        if (getHandler() != null) {
            getHandler().onDecline();
        }
        hide();
    }
}
