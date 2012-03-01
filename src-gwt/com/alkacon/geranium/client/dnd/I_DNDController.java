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

package com.alkacon.geranium.client.dnd;

/**
 * Drag and drop controller.<p>
 * 
 * Implement and assign to the {@link DNDHandler} to control the drag process as well as the underlying model.<p>
 * 
 * @since 8.0.0
 */
public interface I_DNDController {

    /**
     * Executed when end animation starts.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     */
    void onAnimationStart(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed before drop.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     * 
     * @return <code>false</code> to cancel dropping 
     */
    boolean onBeforeDrop(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed on drag cancel.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     */
    void onDragCancel(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed on drag start.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     * 
     * @return <code>false</code> to cancel dragging
     */
    boolean onDragStart(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed on drop.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     */
    void onDrop(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed after the placeholder has been positioned inside a drop target.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     */
    void onPositionedPlaceholder(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed when the helper is dragged into a drop target.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     * 
     * @return <code>false</code> to cancel entering target (placeholder will not positioned inside target)
     */
    boolean onTargetEnter(I_Draggable draggable, I_DropTarget target, DNDHandler handler);

    /**
     * Executed when the helper is dragged out of a drop target.<p>
     * 
     * @param draggable the draggable item
     * @param target the current drop target
     * @param handler the drag and drop handler instance
     */
    void onTargetLeave(I_Draggable draggable, I_DropTarget target, DNDHandler handler);
}
