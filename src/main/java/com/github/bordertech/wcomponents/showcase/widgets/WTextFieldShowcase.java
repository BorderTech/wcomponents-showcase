package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextArea;
import com.github.bordertech.wcomponents.WTextField;
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

		public SamplePanel() {

			// SAMPLE-START
			WFieldLayout layout = new WFieldLayout();
			layout.setLabelWidth(30);
			add(layout);

			widget = new WTextField();
			layout.addField("Textfield", widget);
			// SAMPLE-FINISH
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

		public PropertiesPanel(final WTextField widget) {
			super(widget, widget);

			WFieldLayout layout = getFieldLayout();
			WContainer ajaxContainer = getAjaxContainer();

			// Columns
			final WNumberField numCols = new WNumberField();
			numCols.setMinValue(1);
			layout.addField("Columns", numCols);
			ajaxContainer.add(new WAjaxControl(numCols, new AjaxTarget[]{getMessages(), numCols}));
			numCols.setActionOnChange(new ValidatingAction(getMessages().getValidationErrors(), numCols) {
//				@Override
//				public void executeOnError(ActionEvent event, List<Diagnostic> diags) {
//					for (Diagnostic diag : diags) {
//						getMessages().error(diag.getDescription());
//					}
//				}

				@Override
				public void executeOnValid(final ActionEvent event) {
					int cols = numCols.getValue() == null ? 0 : numCols.getValue().intValue();
					getWidget().setColumns(cols);
				}
			});

//		private int columns;
//		private int maxLength;
//		private int minLength;
//		private Pattern pattern;
//		private WSuggestions suggestions;
		}

	}

}
