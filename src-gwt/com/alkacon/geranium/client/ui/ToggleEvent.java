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

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event to be fired by a widget being activated or deactivated.<p>
 */
public class ToggleEvent extends GwtEvent<I_ToggleHandler> {

    /** Handler type. */
    private static Type<I_ToggleHandler> TYPE;

    /** If the event source has been activated. */
    private boolean m_isActivated;

    /**
     * Creates a new open event.<p>
     * 
     * @param isActivated if the source has been activated
     */
    protected ToggleEvent(boolean isActivated) {

        m_isActivated = isActivated;
    }

    /**
     * Fires a toggle event on all registered handlers in the handler manager.If no
     * such handlers exist, this method will do nothing.<p>
     * 
     * @param source the event source
     * @param isActivated if the source has been activated
     */
    public static void fire(I_HasToggleHandlers source, boolean isActivated) {

        if (TYPE != null) {
            ToggleEvent event = new ToggleEvent(isActivated);
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     * 
     * @return returns the handler type
     */
    public static com.google.gwt.event.shared.GwtEvent.Type<I_ToggleHandler> getType() {

        if (TYPE == null) {
            TYPE = new Type<I_ToggleHandler>();
        }
        return TYPE;
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
     */
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<I_ToggleHandler> getAssociatedType() {

        return ToggleEvent.getType();
    }

    /**
     * Returns if the source has been activated.<p>
     * 
     * @return if the source has been activated
     */
    public boolean isActivated() {

        return m_isActivated;
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
     */
    @Override
    protected void dispatch(I_ToggleHandler handler) {

        handler.onToggle(this);

    }

}
