package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextArea;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author jonathan
 */
public class WTextFieldShowcase extends AbstractShowcase<WTextField> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WTextArea.class));

	public WTextFieldShowcase() {
		super(WTextField.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WTextField> itemPanel) {
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WTextField> {

		private final WTextField widget;

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
			widget = new WTextField();
			layout.addField("Textfield", widget);
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
		public WTextField getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractInputPropertyContainer<WTextField> {

		private final WNumberField numCols = new WNumberField();
		private final WNumberField numMinLength = new WNumberField();
		private final WNumberField numMaxLength = new WNumberField();
		private final WTextField txtPattern = new WTextField() {
			@Override
			public void validate(List<Diagnostic> diags) {
				super.validate(diags);
				if (diags.isEmpty() && !isEmpty()) {
					Pattern pattern = Pattern.compile(getValue());
					if (pattern == null) {
						diags.add(createErrorDiagnostic("Invalid pattern."));
					}
				}
			}
		};

		public PropertiesPanel(final WTextField widget) {
			super(widget, widget);

			numCols.setMinValue(1);
			numMaxLength.setMinValue(1);
			numMinLength.setMinValue(1);

			addPropertyWidget("Columns", numCols);
			addPropertyWidget("Min length", numMinLength);
			addPropertyWidget("Max length", numMaxLength);
			addPropertyWidget("Pattern", txtPattern);

//		private Pattern pattern;
//		private WSuggestions suggestions;
		}

		@Override
		protected void configWidgetProperties(final WTextField widget) {
			super.configWidgetProperties(widget);
			widget.setColumns(getPropertyIntValue(numCols));
			widget.setMaxLength(getPropertyIntValue(numMaxLength));
			widget.setMinLength(getPropertyIntValue(numMinLength));
			widget.setPattern(getPropertyStringValue(txtPattern));
		}

	}

}
