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

package com.alkacon.geranium.client.util;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Command;

/**
 * Abstract animation class.<p>
 */
public abstract class A_Animation extends Animation {

    /** The call-back function to execute on animation complete. */
    protected Command m_callback;

    /**
     * Constructor. Setting the call-back to be executed on animation complete.<p>
     * 
     * @param callback the call-back function
     */
    public A_Animation(Command callback) {

        m_callback = callback;
    }

    /**
     * @see com.google.gwt.animation.client.Animation#onComplete()
     */
    @Override
    protected void onComplete() {

        super.onComplete();
        if (m_callback != null) {
            m_callback.execute();
        }
    }

}
