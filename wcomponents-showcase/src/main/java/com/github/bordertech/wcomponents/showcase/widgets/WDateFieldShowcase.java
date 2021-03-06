package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPartialDateField;
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
public class WDateFieldShowcase extends AbstractShowcase<WDateField> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public WDateFieldShowcase() {
		super(WDateField.class);
	}

	@Override
	public SampleContainer<WDateField> getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer<WDateField> getPropertyContainerInstance(final SampleContainer<WDateField> itemPanel) {
		return new PropertiesPanel(itemPanel);
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractInputSample<WDateField> {

		private final WDateField widget;

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
			widget = new WDateField();
			layout.addField("Datefield", widget);
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
		public WDateField getWidget() {
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

	public static class PropertiesPanel extends AbstractInputPropertyContainer<WDateField> {

		private final WDateField minDate = new WDateField();
		private final WDateField maxDate = new WDateField();

		public PropertiesPanel(final SampleContainer<WDateField> sampleContainer) {
			super(sampleContainer);

			addPropertyWidget("Min date", minDate);
			addPropertyWidget("Max date", maxDate);
		}

		@Override
		protected void configWidgetProperties(final WDateField widget) {
			super.configWidgetProperties(widget);
			widget.setMinDate(PropertyUtil.getPropertyDateValue(minDate));
			widget.setMaxDate(PropertyUtil.getPropertyDateValue(maxDate));
		}

	}

}
