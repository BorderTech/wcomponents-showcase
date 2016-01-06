package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ListLayout;
import com.github.bordertech.wcomponents.showcase.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.SampleContainer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class ListLayoutShowcase extends AbstractShowcase<ListLayout> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public ListLayoutShowcase() {
		super(ListLayout.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<ListLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends WPanel implements SampleContainer<ListLayout> {

		private final WPanel panel;

		public SamplePanel() {

			WPanel box = new WPanel(Type.FEATURE);
			add(box);

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new ListLayout(ListLayout.Type.STACKED, ListLayout.Alignment.LEFT, ListLayout.Separator.NONE, false, 6, 6));
			for (int i = 1; i < 4; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			box.add(panel);
		}

		@Override
		public ListLayout getWidget() {
			return (ListLayout) panel.getLayout();
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return panel;
		}

		public WPanel getPanel() {
			return panel;
		}
	}

	public static class PropertiesPanel extends AbstractLayoutPropertyContainer<ListLayout> {

		private final WDropdown drpAlignment = new WDropdown(ListLayout.Alignment.values());
		private final WDropdown drpType = new WDropdown(ListLayout.Type.values());
		private final WDropdown drpSeparator = new WDropdown(ListLayout.Separator.values());
		private final WCheckBox chbOrdered = new WCheckBox();

		public PropertiesPanel(final WPanel panel) {
			super(panel);

			drpAlignment.setSelected(ListLayout.Alignment.LEFT);
			drpType.setSelected(ListLayout.Type.STACKED);
			drpSeparator.setSelected(ListLayout.Separator.NONE);

			addPropertyWidget("Alignment", drpAlignment);
			addPropertyWidget("Type", drpType);
			addPropertyWidget("Separator", drpSeparator);
			addPropertyWidget("Ordered", chbOrdered);
		}

		@Override
		protected void configWidgetProperties(final ListLayout widget) {
			super.configWidgetProperties(widget);
			int hgap = getHGap();
			int vgap = getVGap();
			ListLayout.Alignment alignment = (ListLayout.Alignment) drpAlignment.getSelected();
			ListLayout.Type type = (ListLayout.Type) drpType.getSelected();
			ListLayout.Separator separator = (ListLayout.Separator) drpSeparator.getSelected();
			boolean ordered = chbOrdered.isSelected();

			ListLayout layout = new ListLayout(type, alignment, separator, ordered, hgap, vgap);
			getPanel().setLayout(layout);
		}

	}

}
