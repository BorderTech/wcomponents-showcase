package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MenuItem;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.showcase.item.ShowcaseDataUtil;
import com.github.bordertech.wcomponents.showcase.item.ShowcaseItem;

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

		add(menu);
		add(ajaxContainer);

		addExamples("UI", ShowcaseDataUtil.INPUT_EXAMPLES);

	}

	/**
	 * Adds a grouped set of examples to the menu.
	 *
	 * @param groupName the name of the group for the examples, or null to add to the menu directly.
	 * @param entries the examples to add to the group.
	 */
	private void addExamples(final String groupName, final ShowcaseItem[] entries) {
		WSubMenu subMenu = new WSubMenu(groupName);
		subMenu.setSelectionMode(WMenu.SelectionMode.SINGLE);
		menu.add(subMenu);

		for (ShowcaseItem entry : entries) {
			WMenuItem item = new WMenuItem(entry.getName());
			item.setActionObject(entry);
			subMenu.add(item);

			item.setAction(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					ShowcaseItem item = (ShowcaseItem) event.getActionObject();
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
