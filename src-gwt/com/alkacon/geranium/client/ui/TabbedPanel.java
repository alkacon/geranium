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

import com.alkacon.geranium.client.ui.css.I_LayoutBundle;
import com.alkacon.geranium.client.util.ClientStringUtil;
import com.alkacon.geranium.client.util.DomUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wrapper class for @see com.google.user.client.ui.TabLayoutPanel.<p>
 * 
 * Layout class for a panel with several tabs. The tabbed panel should be set inside a widget with given width and height.
 * For table based layouts the height of the parent cell should be set explicitly.
 * 
 * As layout options two height for the tabbar are provided: 32px("standard") and 25px("small").
 * 
 * @param <E> the tab widget type
 */
public class TabbedPanel<E extends Widget> extends Composite {

    /** Enumeration with layout keys. */
    public enum TabbedPanelStyle {

        /** Button style. */
        buttonTabs(25, I_LayoutBundle.INSTANCE.tabbedPanelCss().buttonTabs(),
        I_LayoutBundle.INSTANCE.tabbedPanelCss().black()),

        /** Classic style. */
        classicTabs(25, I_LayoutBundle.INSTANCE.tabbedPanelCss().classicTabs(), null);

        /** The default tabbar height. */
        public static final TabbedPanelStyle DEFAULT = buttonTabs;

        /** Property name. */
        private int m_barHeight;

        /** The general panel style name. */
        private String m_styleClass;

        /** The tab color style name. */
        private String m_tabColorClass;

        /** 
         * Constructor.<p>
         * 
         * @param barHeight the height of the bar
         * @param styleClass the tab style class
         * @param tabColorClass the tab color
         */
        private TabbedPanelStyle(int barHeight, String styleClass, String tabColorClass) {

            m_barHeight = barHeight;
            m_styleClass = styleClass;
            m_tabColorClass = tabColorClass;
        }

        /**
         * Returns the barHeight.<p>
         *
         * @return the barHeight
         */
        public int getBarHeight() {

            return m_barHeight;
        }

        /**
         * Returns the styleClass.<p>
         *
         * @return the styleClass
         */
        public String getStyleClass() {

            return m_styleClass;
        }

        /**
         * Returns the tabColorClass.<p>
         *
         * @return the tabColorClass
         */
        public String getTabColorClass() {

            return m_tabColorClass;
        }
    }

    /** Stores the indexes and the title of disabled tabs. */
    private Map<Integer, String> m_disabledTabIndexes = new HashMap<Integer, String>();

    /** The tab panel style. */
    private TabbedPanelStyle m_panelStyle;

    /** The TabLayoutPanel widget. */
    private TabLayoutPanel m_tabPanel;

    /** A map from ids to tabs. */
    private Map<String, E> m_tabsById = new HashMap<String, E>();

    /**
     * The default constructor for an empty tabbed panel. <p>
     */
    public TabbedPanel() {

        this(TabbedPanelStyle.DEFAULT);
    }

    /**
     * The constructor for an empty tabbed panel. <p>
     * 
     * @param tabbedPanelStyle the pre-defined height of the tabbar, can be "small" or "standard"      
     */
    public TabbedPanel(TabbedPanelStyle tabbedPanelStyle) {

        m_tabPanel = new TabLayoutPanel(tabbedPanelStyle.getBarHeight(), Unit.PX);
        m_panelStyle = tabbedPanelStyle;

        // All composites must call initWidget() in their constructors.
        initWidget(m_tabPanel);

        Element tabRootEl = m_tabPanel.getElement();
        // set an additional css class for the parent element of the .gwt-TabLayoutPanelTabs element
        List<Element> tabBarDivs = DomUtil.getElementsByClass(
            I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanelTabs(),
            DomUtil.Tag.div,
            tabRootEl);
        if (tabBarDivs.size() == 1) {
            tabBarDivs.get(0).getParentElement().setClassName(
                I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanelTabBar()
                    + " "
                    + I_LayoutBundle.INSTANCE.generalCss().cornerAll());
            if (m_panelStyle.getTabColorClass() != null) {
                tabBarDivs.get(0).getParentElement().addClassName(m_panelStyle.getTabColorClass());
            }
        }

        m_tabPanel.setStyleName(m_panelStyle.getStyleClass());
        m_tabPanel.addStyleName(I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanel());
        m_tabPanel.addStyleName(I_LayoutBundle.INSTANCE.generalCss().cornerAll()
            + " "
            + I_LayoutBundle.INSTANCE.generalCss().textMedium());

        m_tabPanel.addAttachHandler(new AttachEvent.Handler() {

            /**
             * @see com.google.gwt.event.logical.shared.AttachEvent.Handler#onAttachOrDetach(com.google.gwt.event.logical.shared.AttachEvent)
             */
            public void onAttachOrDetach(AttachEvent event) {

                setOverflowVisibleToContent();
            }
        });
    }

    /**
     * Returns the tab widget.<p>
     * This will not be the tab content but the tab itself.<p>
     * 
     * @param index the tab index
     * 
     * @return the tab widget
     */
    public Widget getTabWidget(int index) {

        return m_tabPanel.getTabWidget(index);
    }

    /**
     * Returns the index of the tab to the given child element.<p>
     * 
     * @param child the tab child
     * 
     * @return the tab index
     */
    public int getTabIndex(Element child) {

        int index = 0;
        for (Widget tab : m_tabPanel) {
            if (tab.getElement().isOrHasChild(child)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Add a new tab with the provided name and content.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#add(Widget, String)}
     * 
     * @param tabContent the widget to add as a tab 
     * @param tabName the name of the tab to display in the tabbar
     */
    public void add(E tabContent, String tabName) {

        tabContent.addStyleName(I_LayoutBundle.INSTANCE.generalCss().cornerAll());
        m_tabPanel.add(tabContent, tabName);

        Element tabRootEl = m_tabPanel.getElement();
        // set an additional css class for the parent element of the .gwt-TabLayoutPanelTabs element
        List<Element> tabDivs = DomUtil.getElementsByClass(
            I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanelTab(),
            DomUtil.Tag.div,
            tabRootEl);

        Iterator<Element> it = tabDivs.iterator();
        boolean first = true;
        while (it.hasNext()) {

            Element e = it.next();
            e.removeClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().cornerLeft());
            e.removeClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().cornerRight());
            if (first) {
                e.addClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().cornerLeft());
                first = false;
            }
            if (!it.hasNext()) {
                e.addClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().cornerRight());
            }
        }
    }

    /**
     * Add the before selection handler to the tabbed panel.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#addBeforeSelectionHandler(BeforeSelectionHandler)}
     * 
     * @param handler the before selection handler
     * @return the registration for the event
     */
    public HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<Integer> handler) {

        return m_tabPanel.addBeforeSelectionHandler(handler);
    }

    /**
     * Adds a tab with a user-defined id.<p>
     * 
     * @param tabContent
     * @param tabName
     * @param tabId
     */
    public void addNamed(E tabContent, String tabName, String tabId) {

        add(tabContent, tabName);
        m_tabsById.put(tabId, tabContent);
    }

    /**
     * Adds a SelectionEvent handler to the tabbed panel.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#addSelectionHandler(SelectionHandler)}
     * 
     * @param handler the selection handler
     * @return the registration for the event
     */
    public HandlerRegistration addSelectionHandler(SelectionHandler<Integer> handler) {

        return m_tabPanel.addSelectionHandler(handler);
    }

    /**
     * Add a new tab with the provided name and content and additional left margin.<p>
     * 
     * @param tabContent the widget to add as a tab 
     * @param tabName the name of the tab to display in the tabbar
     */
    public void addWithLeftMargin(E tabContent, String tabName) {

        tabContent.addStyleName(I_LayoutBundle.INSTANCE.generalCss().cornerAll());
        m_tabPanel.add(tabContent, tabName);

        int tabIndex = m_tabPanel.getWidgetIndex(tabContent);
        Element tabElement = getTabElement(tabIndex);
        if (tabElement != null) {
            tabElement.addClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().tabLeftMargin());
            if (!m_panelStyle.equals(TabbedPanelStyle.classicTabs)) {
                tabElement.addClassName(I_LayoutBundle.INSTANCE.generalCss().buttonCornerAll());
                tabElement.addClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().borderAll());
            }
        }
    }

    /**
     * Disables the tab with the given index.<p>
     * 
     * @param tabContent the content of the tab that should be disabled
     * @param reason the reason why the tab is disabled
     */
    public void disableTab(E tabContent, String reason) {

        Integer index = new Integer(m_tabPanel.getWidgetIndex(tabContent));
        Element tab = getTabElement(index.intValue());
        if ((tab != null) && !m_disabledTabIndexes.containsKey(index)) {
            if (ClientStringUtil.isNotEmptyOrWhitespaceOnly(tab.getTitle())) {
                m_disabledTabIndexes.put(index, tab.getTitle());
            } else {
                m_disabledTabIndexes.put(index, "");
            }
            tab.addClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().tabDisabled());
            tab.setTitle(reason);
        }
    }

    /**
     * Enables the tab with the given index.<p>
     * 
     * @param tabContent the content of the tab that should be enabled
     */
    public void enableTab(E tabContent) {

        Integer index = new Integer(m_tabPanel.getWidgetIndex(tabContent));
        Element tab = getTabElement(index.intValue());
        if ((tab != null) && m_disabledTabIndexes.containsKey(index)) {
            tab.removeClassName(I_LayoutBundle.INSTANCE.tabbedPanelCss().tabDisabled());
            tab.setTitle(m_disabledTabIndexes.get(index));
            m_disabledTabIndexes.remove(index);
        }
    }

    /**
     * Gets the index of the currently-selected tab.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#getSelectedIndex()}
     * 
     * @return the selected index, or -1 if none is selected.
     */
    public int getSelectedIndex() {

        return m_tabPanel.getSelectedIndex();

    }

    /**
     * Finds a tab with a given id.<p>
     * 
     * @param tabId a tab id
     *  
     * @return the tab with the given id 
     */
    public E getTabById(String tabId) {

        return m_tabsById.get(tabId);
    }

    /**
     * Gets the number of child widgets in this panel.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#getWidgetCount()}
     * 
     * @return the number of children
     */
    public int getTabCount() {

        return m_tabPanel.getWidgetCount();
    }

    /**
     * Returns the tab text for a given tab.<p>
     * 
     * @param pos the index of the tab 
     * 
     * @return the text of the tab 
     */
    public String getTabText(int pos) {

        return m_tabPanel.getTabWidget(pos).getElement().getInnerText();
    }

    /**
     * Gets the child widget at the specified index.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#getWidget(int)}
     * 
     * @param tabIndex the child widget's index
     * @return the child widget
     */
    @SuppressWarnings("unchecked")
    public E getWidget(int tabIndex) {

        return (E)m_tabPanel.getWidget(tabIndex);
    }

    /**
     * Inserts a widget into the panel. If the Widget is already attached, it will be moved to the requested index.<p>
     * 
     * Wrapper function for {@link com.google.gwt.user.client.ui.TabLayoutPanel#insert(Widget, String, int)}
     * 
     * @param tabContent the widget to be added
     * @param tabName the text to be shown on its tab
     * @param beforeIndex  the index before which it will be inserted
     */
    public void insert(E tabContent, String tabName, int beforeIndex) {

        m_tabPanel.insert(tabContent, tabName, beforeIndex);
    }

    /**
     * Returns <code>true</code> if the tab with the given index is disabled, <code>false</code> otherwise.<p>
     * 
     * @param tabIndex the tab index
     * 
     * @return <code>true</code> if the tab with the given index is disabled, <code>false</code> otherwise
     */
    public boolean isDisabledTab(int tabIndex) {

        return m_disabledTabIndexes.containsKey(new Integer(tabIndex));
    }

    /**
     * Returns an iterator over all tabs.<p>
     * 
     * @return the iterator
     */
    @SuppressWarnings("unchecked")
    public Iterator<E> iterator() {

        return (Iterator<E>)m_tabPanel.iterator();
    }

    /**
     * Removes the tab with the given index.<p>
     * 
     * @param tabIndex the index of the tab which should be removed 
     */
    public void removeTab(int tabIndex) {

        m_tabPanel.remove(tabIndex);
    }

    /**
     * Delegate method.<p>
     * 
     * @see com.google.gwt.user.client.ui.TabLayoutPanel#selectTab(Widget index)
     * 
     * @param tabWidget the tab widget to select
     */
    public void selectTab(E tabWidget) {

        m_tabPanel.selectTab(tabWidget);
    }

    /**
     * Delegate method.<p>
     * 
     * @see com.google.gwt.user.client.ui.TabLayoutPanel#selectTab(Widget index)
     * 
     * @param tabWidget the tab widget to select
     * @param fireEvent <code>true</code> to fire the tab event
     */
    public void selectTab(E tabWidget, boolean fireEvent) {

        m_tabPanel.selectTab(tabWidget, fireEvent);
    }

    /**
     * Delegate method.<p>
     * 
     * @see com.google.gwt.user.client.ui.TabLayoutPanel#selectTab(int index)
     * 
     * @param tabIndex the index of the tab to be selected
     */
    public void selectTab(int tabIndex) {

        m_tabPanel.selectTab(tabIndex);
    }

    /**
     * Delegate method.<p>
     * 
     * @see com.google.gwt.user.client.ui.TabLayoutPanel#selectTab(int index)
     * 
     * @param tabIndex the index of the tab to be selected
     * @param fireEvent <code>true</code> to fire the tab event
     */
    public void selectTab(int tabIndex, boolean fireEvent) {

        m_tabPanel.selectTab(tabIndex, fireEvent);
    }

    /**
     * Sets the text of a given tab.<p>
     * 
     * @param pos the index of the tab 
     * @param text the new text for the tab 
     */
    public void setTabText(int pos, String text) {

        m_tabPanel.setTabText(pos, text);

    }

    /**
     * Returns the tab layout panel.<p>
     * 
     * @return the tab layout panel
     */
    protected TabLayoutPanel getTabPanel() {

        return m_tabPanel;
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {

        super.onLoad();
        // force layout after insertion into DOM to deal with IE layout problems
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            public void execute() {

                getTabPanel().forceLayout();

            }
        });

    }

    /**
     * Sets the overflow of the tab layout content's parent to visible.<p>  
     */
    protected void setOverflowVisibleToContent() {

        Element tabRoot = m_tabPanel.getElement();
        // set an additional css class for the parent element of the .gwt-TabLayoutPanelTabs element
        List<Element> tabContentDivs = DomUtil.getElementsByClass(
            I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanelContent(),
            DomUtil.Tag.div,
            tabRoot);
        tabContentDivs.addAll(DomUtil.getElementsByClass("gwt-TabLayoutPanelContentContainer", DomUtil.Tag.div, tabRoot));
        for (Element e : tabContentDivs) {
            e.getParentElement().getStyle().setOverflow(Overflow.VISIBLE);
        }
    }

    /**
     * Returns the tab element for the given index.<p>
     * 
     * @param tabIndex the tab index to get the tab element for
     * 
     * @return the tab element for the given index
     */
    private Element getTabElement(int tabIndex) {

        Element tabRootEl = m_tabPanel.getElement();
        // set an additional css class for the parent element of the .gwt-TabLayoutPanelTabs element
        List<Element> tabDivs = DomUtil.getElementsByClass(
            I_LayoutBundle.INSTANCE.tabbedPanelCss().cmsTabLayoutPanelTab(),
            DomUtil.Tag.div,
            tabRootEl);
        if ((tabDivs != null) && (tabDivs.size() > tabIndex)) {
            return tabDivs.get(tabIndex);
        }
        return null;
    }
}
