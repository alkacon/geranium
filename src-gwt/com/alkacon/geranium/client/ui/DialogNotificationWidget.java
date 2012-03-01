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

import com.alkacon.geranium.client.ui.Notification.Type;
import com.alkacon.geranium.client.ui.css.I_LayoutBundle;
import com.alkacon.geranium.client.util.FadeAnimation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

/**
 * The toolbar notification widget.<p>
 * 
 * @since 8.0.0
 */
public class DialogNotificationWidget extends A_NotificationWidget {

    // TODO: improve for dialog

    /**
     * @see com.google.gwt.uibinder.client.UiBinder
     */
    protected interface I_CmsNotificationWidgetUiBinder extends UiBinder<Widget, DialogNotificationWidget> {
        // GWT interface, nothing to do here
    }

    /** The ui-binder instance for this class. */
    private static I_CmsNotificationWidgetUiBinder uiBinder = GWT.create(I_CmsNotificationWidgetUiBinder.class);

    /** The message. */
    @UiField
    protected Element m_container;

    /** The message. */
    @UiField
    protected Element m_message;

    /**
     * Constructor.<p>
     */
    public DialogNotificationWidget() {

        initWidget(uiBinder.createAndBindUi(this));
        restore();
    }

    /**
     * @see com.alkacon.geranium.client.ui.I_NotificationWidget#setBlocking()
     */
    public void setBlocking() {

        getElement().addClassName(I_LayoutBundle.INSTANCE.notificationCss().blocking());
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_NotificationWidget#animateHide()
     */
    @Override
    protected void animateHide() {

        setAnimation(FadeAnimation.fadeOut(getElement(), new Command() {

            /**
             * @see com.google.gwt.user.client.Command#execute()
             */
            public void execute() {

                onHideComplete();
            }
        }, 200));
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_NotificationWidget#animateShow()
     */
    @Override
    protected void animateShow() {

        getElement().getStyle().clearDisplay();
        setAnimation(FadeAnimation.fadeIn(getElement(), new Command() {

            /**
             * @see com.google.gwt.user.client.Command#execute()
             */
            public void execute() {

                onShowComplete();
            }
        }, 200));
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_NotificationWidget#getMessage()
     */
    @Override
    protected String getMessage() {

        return m_message.getInnerHTML();
    }

    /**
     * Should be called by the hide animation on complete.<p>
     */
    @Override
    protected void onHideComplete() {

        setAnimation(null);
        restore();
    }

    /**
     * Should be called by the show animation on complete.<p>
     */
    @Override
    protected void onShowComplete() {

        setAnimation(null);
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_NotificationWidget#setClassForType(com.alkacon.geranium.client.ui.Notification.Type)
     */
    @Override
    protected void setClassForType(Type type) {

        if (type == null) {
            return;
        }
        // set the right class
        getElement().addClassName(classForType(type));
    }

    /**
     * @see com.alkacon.geranium.client.ui.A_NotificationWidget#setMessage(java.lang.String)
     */
    @Override
    protected void setMessage(String message) {

        m_message.setInnerHTML(message);
    }

    /**
     * Returns the class name for the given type.<p>
     * 
     * @param type the type
     * 
     * @return the class name
     */
    private String classForType(Type type) {

        switch (type) {
            case ERROR:
                return I_LayoutBundle.INSTANCE.notificationCss().notificationError();
            case NORMAL:
                return I_LayoutBundle.INSTANCE.notificationCss().notificationNormal();
            case WARNING:
                return I_LayoutBundle.INSTANCE.notificationCss().notificationWarning();
            default:
                return null;
        }
    }

    /**
     * Restores the initial state.<p>
     */
    private void restore() {

        setVisible(false);
        // back to plain style without error or warning
        getElement().setClassName("");
        m_container.setClassName(I_LayoutBundle.INSTANCE.notificationCss().notificationContainer());
    }
}