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

import com.alkacon.geranium.client.ui.Notification.Mode;
import com.alkacon.geranium.client.ui.Notification.Type;
import com.alkacon.geranium.client.util.A_Animation;
import com.alkacon.geranium.client.util.ClientStringUtil;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;

/**
 * The notification widget.<p>
 */
public abstract class A_NotificationWidget extends Composite implements I_NotificationWidget {

    /** The current animation. */
    private A_Animation m_animation;

    /** The current mode. */
    private Mode m_mode;

    /** The timer to remove the last notification. */
    private Timer m_timer;

    /** The current type. */
    private Type m_type;

    /**
     * Returns the mode.<p>
     *
     * @return the mode
     */
    public Mode getMode() {

        return m_mode;
    }

    /**
     * Returns the timer.<p>
     *
     * @return the timer
     */
    public Timer getTimer() {

        return m_timer;
    }

    /**
     * Returns the type.<p>
     *
     * @return the type
     */
    public Type getType() {

        return m_type;
    }

    /**
     * @see com.alkacon.geranium.client.ui.I_NotificationWidget#hide()
     */
    public void hide() {

        m_mode = null;
        hide(true);
    }

    /**
     * @see com.alkacon.geranium.client.ui.I_NotificationWidget#show(com.alkacon.geranium.client.ui.Notification.Mode, com.alkacon.geranium.client.ui.Notification.Type, java.lang.String)
     */
    public void show(Mode mode, Type type, String message) {

        if (mode == null) {
            mode = Mode.NORMAL;
        }
        final String restoreMessage;
        if (Notification.shouldRestoreMessage(m_mode, mode, m_type, type)) {
            restoreMessage = getMessage();
        } else {
            restoreMessage = null;
        }
        // remove last notification if still shown
        hide(true);

        // keep state
        final Type oldType = m_type;
        m_type = type;
        m_mode = mode;

        // set the new notification message
        setMessage(message);

        // set the right class
        setClassForType(type);

        // show the widget
        animateShow();

        // set timer to hide non sticky messages or restore superior message
        if (Mode.NORMAL.equals(m_mode) || ClientStringUtil.isNotEmptyOrWhitespaceOnly(restoreMessage)) {
            m_timer = new Timer() {

                /**
                 * @see com.google.gwt.user.client.Timer#run()
                 */
                @Override
                public void run() {

                    hide(false);
                    if (ClientStringUtil.isNotEmptyOrWhitespaceOnly(restoreMessage)) {
                        show(Mode.STICKY, oldType, restoreMessage);
                    }
                }
            };
            m_timer.schedule(3000 * (type == Type.NORMAL ? 1 : 2));
        }

    }

    /**
     * Animates the notification out of view.<p>
     */
    protected abstract void animateHide();

    /**
     * Animates the notification into view.<p>
     */
    protected abstract void animateShow();

    /**
     * Returns the currently running animation.<p>
     * 
     * @return the animation
     */
    protected A_Animation getAnimation() {

        return m_animation;
    }

    /**
     * Returns the current notification message.<p>
     * 
     * @return the notification message
     */
    protected abstract String getMessage();

    /**
     * Hides the notification message.<p>
     * 
     * @param force if <code>true</code> will also hide the message if mode is fixed
     */
    protected void hide(boolean force) {

        // remove last notification if still shown
        if (m_timer != null) {
            m_timer.cancel();
            m_timer = null;
        }
        if ((m_mode == Mode.STICKY) && !force) {
            return;
        }
        m_type = null;
        if (getAnimation() != null) {
            getAnimation().cancel();
            setAnimation(null);
        }
        if (getElement().getStyle().getVisibility().equalsIgnoreCase(Visibility.HIDDEN.getCssName())) {
            return;
        }

        if (!force) {
            animateHide();
        } else {
            onHideComplete();
        }
    }

    /**
     * Execute when hiding is complete.<p>
     */
    protected abstract void onHideComplete();

    /**
     * Execute when notification is completely visible.<p>
     */
    protected abstract void onShowComplete();

    /**
     * Sets the currently running animation.<p>
     * 
     * @param animation the animation
     */
    protected void setAnimation(A_Animation animation) {

        m_animation = animation;
    }

    /**
     * Sets the appropriate css class for the given type.<p>
     *  
     * @param type the message type
     */
    protected abstract void setClassForType(Type type);

    /**
     * Sets the current notification message.<p>
     * 
     * @param message the notification message
     */
    protected abstract void setMessage(String message);

}
