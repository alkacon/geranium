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

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;

/**
 * Fade animation. Fading the element into view or fading it out.<p>
 * 
 * @since 8.0.0
 */
public class FadeAnimation extends A_Animation {

    /** The element to animate. */
    private Element m_element;

    /** Show or hide flag. */
    private boolean m_show;

    /**
     * Constructor.<p>
     * 
     * @param element the element to animate
     * @param show <code>true</code> to show the element, <code>false</code> to hide it away
     * @param callback the callback executed after the animation is completed
     */
    public FadeAnimation(Element element, boolean show, Command callback) {

        super(callback);
        m_show = show;
        m_element = element;
    }

    /**
     * Fades the given element into view executing the callback afterwards.<p>
     * 
     * @param element the element to fade in
     * @param callback the callback
     * @param duration the animation duration
     * 
     * @return the running animation object
     */
    public static FadeAnimation fadeIn(Element element, Command callback, int duration) {

        FadeAnimation animation = new FadeAnimation(element, true, callback);
        animation.run(duration);
        return animation;
    }

    /**
     * Fades the given element out of view executing the callback afterwards.<p>
     * 
     * @param element the element to fade out
     * @param callback the callback
     * @param duration the animation duration
     * 
     * @return the running animation object
     */
    public static FadeAnimation fadeOut(Element element, Command callback, int duration) {

        FadeAnimation animation = new FadeAnimation(element, false, callback);
        animation.run(duration);
        return animation;
    }

    /**
     * @see com.google.gwt.animation.client.Animation#onComplete()
     */
    @Override
    protected void onComplete() {

        super.onComplete();

        // using own implementation as GWT won't do it properly on IE7-8
        DomUtil.clearOpacity(m_element);
    }

    /**
     * @see com.google.gwt.animation.client.Animation#onUpdate(double)
     */
    @Override
    protected void onUpdate(double progress) {

        if (m_show) {
            m_element.getStyle().setOpacity(progress);
        } else {
            m_element.getStyle().setOpacity(1 - progress);
        }
    }
}
