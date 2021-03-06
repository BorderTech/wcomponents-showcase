package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.showcase.common.Showcase;
import com.github.bordertech.wcomponents.showcase.util.ConfigUtil;
import com.github.bordertech.wcomponents.util.Util;
import java.util.Map;

/**
 *
 * @author jonathan
 */
public class PickerPanel extends WPanel {

	private final ShowcaseApp ctrl;
	private final WMenu menu = new WMenu(WMenu.MenuType.TREE);
	private final WContainer ajaxContainer = new WContainer();

	public PickerPanel(final ShowcaseApp ctrl) {
		this.ctrl = ctrl;

		menu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		add(menu);
		add(ajaxContainer);

		for (Map.Entry<String, Showcase[]> entry : ConfigUtil.getShowcases().entrySet()) {
			addExamples(entry.getKey(), entry.getValue());
		}

	}

	public Showcase<?> selectDefaultShowcase() {
		WSubMenu subMenu = null;
		for (MenuItem item : menu.getMenuItems(true)) {
			if (item instanceof WSubMenu) {
				subMenu = (WSubMenu) item;
			} else if (item instanceof WMenuItem) {
				WMenuItem menuItem = (WMenuItem) item;
				menu.setSelectedMenuItem(menuItem);
				subMenu.setOpen(true);
				Showcase showcase = (Showcase) menuItem.getActionObject();
				return showcase;
			}
		}
		return null;
	}

	public Showcase<?> selectShowcaseByWidgetClass(final Class clazz) {
		WSubMenu subMenu = null;
		for (MenuItem item : menu.getMenuItems(true)) {
			if (item instanceof WSubMenu) {
				subMenu = (WSubMenu) item;
			} else if (item instanceof WMenuItem) {
				WMenuItem menuItem = (WMenuItem) item;
				Showcase<?> showcase = (Showcase) menuItem.getActionObject();
				if (Util.equals(clazz, showcase.getWidgetClass())) {
					menu.setSelectedMenuItem(menuItem);
					subMenu.setOpen(true);
					return showcase;
				}
			}
		}
		return null;
	}

	public Showcase<?> getNextShowcase() {
		return selectNextPrevShowcase(true);
	}

	public Showcase<?> getPrevShowcase() {
		return selectNextPrevShowcase(false);
	}

	private Showcase<?> selectNextPrevShowcase(final boolean next) {

		// Check if an item is selected
		WMenuItem currentItem = (WMenuItem) menu.getSelectedMenuItem();
		if (currentItem == null) {
			// Use default
			return selectDefaultShowcase();
		}

		// Get the items sub,menu
		WSubMenu subMenu = getItemSubMenu(currentItem);

		// Current position
		int idx = subMenu.getMenuItems().indexOf(currentItem);
		if (next) {
			// Next Index
			idx++;
			// If end of Submenu go to beginning
			if (idx == subMenu.getMenuItems().size()) {
				idx = 0;
			}
		} else {
			// Prev Index
			idx--;
			// If start of Submenu go to end
			if (idx < 0) {
				idx = subMenu.getMenuItems().size() - 1;
			}
		}

		WMenuItem newItem = (WMenuItem) subMenu.getMenuItems().get(idx);
		Showcase<?> newShowcase = (Showcase) newItem.getActionObject();

		menu.setSelectedMenuItem(newItem);
		return newShowcase;
	}

	private WSubMenu getItemSubMenu(final WMenuItem item) {
		WSubMenu submenu = (WSubMenu) WebUtilities.getAncestorOfClass(WSubMenu.class, item);
		return submenu;
	}

	/**
	 * Adds a grouped set of examples to the menu.
	 *
	 * @param groupName the name of the group for the examples, or null to add to the menu directly.
	 * @param entries the examples to add to the group.
	 */
	private void addExamples(final String groupName, final Showcase[] entries) {

		WSubMenu subMenu = new WSubMenu(groupName);
		subMenu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);
		menu.add(subMenu);

		for (Showcase entry : entries) {
			WMenuItem item = new WMenuItem(entry.getWidgetClass().getSimpleName());
			item.setActionObject(entry);
			subMenu.add(item);

			item.setAction(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					Showcase<?> item = (Showcase) event.getActionObject();
					ctrl.doDisplayShowcase(item);
				}
			});

		}
	}

	public void addAjaxTargets(final AjaxTarget... targets) {

		for (MenuItem item : menu.getMenuItems(true)) {
			if (item instanceof WMenuItem) {
				WAjaxControl ajax = new WAjaxControl((WMenuItem) item, targets) {
					@Override
					public boolean isVisible() {
						WSubMenu submenu = getItemSubMenu((WMenuItem) getTrigger());
						return submenu.isOpen();
					}
				};
				ajaxContainer.add(ajax);
			}
		}

	}

}
