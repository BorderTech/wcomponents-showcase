package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ListLayout;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.common.TypeWDropdown;
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
	public SampleContainer<ListLayout> getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer<ListLayout> getPropertyContainerInstance(final SampleContainer<ListLayout> itemPanel) {
		SamplePanel sample = (SamplePanel) itemPanel;
		return new PropertiesPanel(itemPanel, sample.getPanel());
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<ListLayout> {

		private final WPanel panel;

		public SamplePanel() {

			// SAMPLE-START
			panel = new WPanel();
			panel.setLayout(new ListLayout(ListLayout.Type.STACKED, ListLayout.Alignment.LEFT, ListLayout.Separator.NONE, false));
			for (int i = 1; i < 4; i++) {
				WPanel child = new WPanel(Type.BOX);
				child.add(new WText("Child " + i));
				panel.add(child);
			}
			// SAMPLE-FINISH

			add(panel);
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

		private final TypeWDropdown<ListLayout.Alignment> drpAlignment = new TypeWDropdown<>(ListLayout.Alignment.values());
		private final TypeWDropdown<ListLayout.Type> drpType = new TypeWDropdown<>(ListLayout.Type.values());
		private final TypeWDropdown<ListLayout.Separator> drpSeparator = new TypeWDropdown<>(ListLayout.Separator.values());
		private final WCheckBox chbOrdered = new WCheckBox();

		public PropertiesPanel(final SampleContainer<ListLayout> sampleContainer, final WPanel panel) {
			super(sampleContainer, panel);

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
			ListLayout layout = new ListLayout(drpType.getSelected(), drpAlignment.getSelected(), drpSeparator.getSelected(), chbOrdered.isSelected(), hgap, vgap);
			getPanel().setLayout(layout);
		}

	}

}
