package com.github.bordertech.wcomponents.showcase.item;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.ItemPanel;

/**
 *
 * @author jonathan
 */
public class WTextFieldItem extends AbstractShowcaseItem {

	public WTextFieldItem() {
		super("WTextField", "WTextField");
	}

	@Override
	public ItemPanel getItemInstance() {
		return new WidgetPanel();
	}

	public WPanel getPropertiesInstance(final ItemPanel itemPanel) {
		return new PropertiesPanel((WTextField) itemPanel.getItem());
	}

	public static class WidgetPanel extends WPanel implements ItemPanel {

		private final WTextField widget = new WTextField();

		public WidgetPanel() {
			WFieldLayout layout = new WFieldLayout();
			add(layout);
			layout.addField("Textfield", widget);
		}

		@Override
		public WComponent getItem() {
			return widget;
		}
	}

	public static class PropertiesPanel extends WPanel {

		private final WFieldLayout layout = new WFieldLayout();

		private final WContainer ajaxContainer = new WContainer();

		public PropertiesPanel(final WTextField widget) {

			add(layout);
			add(ajaxContainer);

			final WCheckBox chb = new WCheckBox();
			layout.addField("Readonly", chb);
			ajaxContainer.add(new WAjaxControl(chb, widget));
			chb.setActionOnChange(new Action() {
				@Override
				public void execute(ActionEvent event) {
					widget.setReadOnly(chb.isSelected());
				}
			});

			final WCheckBox chb2 = new WCheckBox();
			layout.addField("Disabled", chb2);
			ajaxContainer.add(new WAjaxControl(chb2, widget));
			chb2.setActionOnChange(new Action() {
				@Override
				public void execute(ActionEvent event) {
					widget.setDisabled(chb2.isSelected());
				}
			});
		}

	}

}
