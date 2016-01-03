package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
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
public class WPartialDateFieldShowcase extends AbstractShowcase<WPartialDateField> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WDateField.class));

	public WPartialDateFieldShowcase() {
		super(WPartialDateField.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WPartialDateField> itemPanel) {
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WPartialDateField> {

		private final WPartialDateField widget;

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
			widget = new WPartialDateField();
			layout.addField("Partial datefield", widget);
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
		public WPartialDateField getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractInputPropertyContainer<WPartialDateField> {

		public PropertiesPanel(final WPartialDateField widget) {
			super(widget, widget);
		}

	}

}
