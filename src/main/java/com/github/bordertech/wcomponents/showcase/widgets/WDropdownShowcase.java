package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WMultiSelect;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class WDropdownShowcase extends AbstractShowcase<WDropdown> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WMultiSelect.class));

	public WDropdownShowcase() {
		super(WDropdown.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WDropdown> itemPanel) {
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WDropdown> {

		private final WDropdown widget;

		private final WMessages messages;

		private final WFieldLayout layout;

		private final WButton button;

		public SamplePanel() {

			// SAMPLE-START
			messages = new WMessages();
			add(messages);
			// SAMPLE-FINISH

			// SAMPLE-START
			layout = new WFieldLayout();
			layout.setLabelWidth(30);
			add(layout);
			// SAMPLE-FINISH

			// SAMPLE-START
			widget = new WDropdown();
			widget.setOptions(new String[]{null, "Apple", "Banana", "Orange"});
			widget.setActionOnChange(new Action() {
				@Override
				public void execute(ActionEvent event) {
					String selected = (String) widget.getSelected();
					String msg = selected == null ? "Null option selected." : "Selected option " + selected + ".";
					messages.info(msg);
				}
			});
			layout.addField("Dropdown", widget);
			// SAMPLE-FINISH

			// SAMPLE-START
			add(new WAjaxControl(widget, messages));
			// SAMPLE-FINISH

			// SAMPLE-START
			button = new WButton("Validate");
			button.setAction(new ValidatingAction(messages.getValidationErrors(), layout) {
				@Override
				public void executeOnValid(final ActionEvent event) {
					messages.success("Valid.");
				}
			});
			button.setAjaxTarget(SamplePanel.this);
			layout.addField((WLabel) null, button);
			// SAMPLE-FINISH

			// SAMPLE-START
			widget.setDefaultSubmitButton(button);
			// SAMPLE-FINISH

			messages.setMargin(new Margin(0, 0, 12, 0));

		}

		@Override
		public WDropdown getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractInputPropertyContainer<WDropdown> {

		private final WNumberField numOptionWidth = new WNumberField();

		public PropertiesPanel(final WDropdown widget) {
			super(widget, widget);

			numOptionWidth.setMinValue(1);
			numOptionWidth.setMaxValue(100);

			addPropertyWidget("Option width", numOptionWidth);
		}

		@Override
		protected void configWidgetProperties(final WDropdown widget) {
			super.configWidgetProperties(widget);
			widget.setOptionWidth(getPropertyIntValue(numOptionWidth));
		}

	}

}
