package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WTextArea;
import com.github.bordertech.wcomponents.WTextField;
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
public class WTextAreaShowcase extends AbstractShowcase<WTextArea> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WTextField.class));

	public WTextAreaShowcase() {
		super(WTextArea.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WTextArea> itemPanel) {
		return new PropertiesPanel(itemPanel);
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractInputSample<WTextArea> {

		private final WTextArea widget;

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
			widget = new WTextArea();
			layout.addField("Textarea", widget);
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
		public WTextArea getWidget() {
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

	public static class PropertiesPanel extends WTextFieldShowcase.PropertiesPanel<WTextArea> {

		private final WNumberField numRows = new WNumberField();
		private final WCheckBox chbRichText = new WCheckBox();

		public PropertiesPanel(final SampleContainer<WTextArea> sampleContainer) {
			super(sampleContainer);

			numRows.setMinValue(1);

			addPropertyWidget("Rows", numRows);
			addPropertyWidget("Rich text", chbRichText);
		}

		@Override
		protected void configWidgetProperties(final WTextArea widget) {
			super.configWidgetProperties(widget);

			widget.setRows(PropertyUtil.getPropertyIntValue(numRows));
			widget.setRichTextArea(chbRichText.isSelected());
		}

	}

}
