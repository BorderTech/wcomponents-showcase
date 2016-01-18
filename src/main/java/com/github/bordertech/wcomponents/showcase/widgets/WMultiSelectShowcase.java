package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WMultiSelect;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class WMultiSelectShowcase extends AbstractShowcase<WMultiSelect> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WDropdown.class));

	public WMultiSelectShowcase() {
		super(WMultiSelect.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WMultiSelect> itemPanel) {
		return new PropertiesPanel(itemPanel);
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractInputSample<WMultiSelect> {

		private final WMultiSelect widget;

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
			widget = new WMultiSelect();
			widget.setOptions(new String[]{"Apple", "Banana", "Orange"});
			widget.setActionOnChange(new Action() {
				@Override
				public void execute(ActionEvent event) {
					List<String> selected = (List<String>) widget.getSelected();
					String msg = selected.isEmpty() ? "No options selected." : "Selected options " + selected + ".";
					messages.info(msg);
				}
			});
			layout.addField("Multiselect", widget);
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

		}

		@Override
		public WMultiSelect getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public WMessages getMessages() {
			return messages;
		}

	}

	public static class PropertiesPanel extends AbstractMultiSelectPropertyContainer<WMultiSelect> {

		private final WNumberField numRows = new WNumberField();

		public PropertiesPanel(final SampleContainer<WMultiSelect> sampleContainer) {
			super(sampleContainer);
			numRows.setMinValue(1);
			addPropertyWidget("Rows", numRows);
		}

		@Override
		protected void configWidgetProperties(final WMultiSelect widget) {
			super.configWidgetProperties(widget);
			widget.setRows(PropertyUtil.getPropertyIntValue(numRows));
		}

	}

}
