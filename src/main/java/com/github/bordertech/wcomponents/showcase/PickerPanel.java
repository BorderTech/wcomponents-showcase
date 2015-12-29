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
import com.github.bordertech.wcomponents.showcase.util.DataUtil;
import com.github.bordertech.wcomponents.showcase.widgets.Showcase;

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

		addExamples("UI", DataUtil.INPUT_EXAMPLES);

	}

	/**
	 * Adds a grouped set of examples to the menu.
	 *
	 * @param groupName the name of the group for the examples, or null to add to the menu directly.
	 * @param entries the examples to add to the group.
	 */
	private void addExamples(final String groupName, final Showcase[] entries) {

		WSubMenu subMenu = new WSubMenu(groupName);
		menu.add(subMenu);

		for (Showcase entry : entries) {
			WMenuItem item = new WMenuItem(entry.getWidgetName());
			item.setActionObject(entry);
			subMenu.add(item);

			item.setAction(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					Showcase item = (Showcase) event.getActionObject();
					ctrl.doShowItem(item);
				}
			});

		}
	}

	public void setAjaxTargets(final AjaxTarget... targets) {

		for (MenuItem item : menu.getMenuItems(true)) {
			if (item instanceof WMenuItem) {
				WAjaxControl ajax = new WAjaxControl((WMenuItem) item, targets);
				ajaxContainer.add(ajax);
			}
		}

	}

}
