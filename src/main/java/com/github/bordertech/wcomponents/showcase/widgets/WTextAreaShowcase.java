package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WCheckBox;
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
		return new PropertiesPanel(itemPanel.getWidget());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<WTextArea> {

		private final WTextArea widget;

		public SamplePanel() {
			// SAMPLE-START
			WFieldLayout layout = new WFieldLayout();
			layout.setLabelWidth(30);
			add(layout);

			widget = new WTextArea();
			layout.addField("Textarea", widget);
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

	}

	public static class PropertiesPanel extends WTextFieldShowcase.PropertiesPanel {

		public PropertiesPanel(final WTextArea widget) {
			super(widget);

			WFieldLayout layout = getFieldLayout();
			WContainer ajaxContainer = getAjaxContainer();

			// Rows
			final WNumberField numRows = new WNumberField();
			layout.addField("Rows", numRows);
			ajaxContainer.add(new WAjaxControl(numRows, new AjaxTarget[]{getMessages(), widget}));
			numRows.setActionOnChange(new ValidatingAction(getMessages().getValidationErrors(), numRows) {
				@Override
				public void executeOnValid(final ActionEvent event) {
					int cols = numRows.getValue() == null ? 0 : numRows.getValue().intValue();
					widget.setColumns(cols);
				}
			});

			// Rich Text
			final WCheckBox chb = new WCheckBox();
			layout.addField("Rich text", chb);
			ajaxContainer.add(new WAjaxControl(chb, widget));
			chb.setActionOnChange(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					widget.setRichTextArea(chb.isSelected());
				}
			});

		}

	}

}
