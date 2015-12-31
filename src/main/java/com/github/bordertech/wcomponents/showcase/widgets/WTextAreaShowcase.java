package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.showcase.SampleContainer;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextArea;
import com.github.bordertech.wcomponents.WTextField;
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
	}

	public static class PropertiesPanel extends WPanel implements PropertyContainer {

		private final WFieldLayout layout = new WFieldLayout();

		private final WContainer ajaxContainer = new WContainer();

		public PropertiesPanel(final WTextArea widget) {

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

			final WCheckBox chb3 = new WCheckBox();
			layout.addField("Rich text", chb3);
			ajaxContainer.add(new WAjaxControl(chb3, widget));
			chb3.setActionOnChange(new Action() {
				@Override
				public void execute(ActionEvent event) {
					widget.setRichTextArea(chb3.isSelected());
				}
			});

		}

	}

}
