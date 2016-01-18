package com.github.bordertech.wcomponents.showcase.widgets;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.SimpleBeanBoundTableModel;
import com.github.bordertech.wcomponents.WBeanContainer;
import com.github.bordertech.wcomponents.WCheckBox;
import com.github.bordertech.wcomponents.WCheckBoxSelect;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WDefinitionList;
import com.github.bordertech.wcomponents.WField;
import com.github.bordertech.wcomponents.WHorizontalRule;
import com.github.bordertech.wcomponents.WMultiSelectPair;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPartialDateField;
import com.github.bordertech.wcomponents.WRadioButtonSelect;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.showcase.common.MarginCheckboxSelect;
import com.github.bordertech.wcomponents.showcase.common.PropertyContainer;
import com.github.bordertech.wcomponents.showcase.common.SampleContainer;
import com.github.bordertech.wcomponents.showcase.common.TypeWDropdown;
import com.github.bordertech.wcomponents.showcase.util.PropertyUtil;
import com.github.bordertech.wcomponents.showcase.util.ShowcaseDataUtil;
import com.github.bordertech.wcomponents.subordinate.Condition;
import com.github.bordertech.wcomponents.subordinate.Equal;
import com.github.bordertech.wcomponents.subordinate.NotEqual;
import com.github.bordertech.wcomponents.subordinate.Or;
import com.github.bordertech.wcomponents.subordinate.WSubordinateControl;
import com.github.bordertech.wcomponents.util.TableUtil;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class WTableShowcase extends AbstractShowcase<WTable> {

	private static final List<Class> RELATED = Collections.unmodifiableList(Arrays.asList((Class) WPartialDateField.class));

	public WTableShowcase() {
		super(WTable.class);
	}

	@Override
	public SampleContainer getSampleContainerInstance() {
		return new SamplePanel();
	}

	@Override
	public PropertyContainer getPropertyContainerInstance(final SampleContainer<WTable> itemPanel) {
		return new PropertiesPanel(itemPanel);
	}

	@Override
	public List<Class> getRelatedWidgets() {
		return RELATED;
	}

	public static class SamplePanel extends AbstractLayoutSample<WTable> {

		private final WTable widget;

		public SamplePanel() {

			setSearchAncestors(false);

			// SAMPLE-START
			widget = new WTable() {
				protected void preparePaintComponent(final Request request) {
					super.preparePaintComponent(request);
					if (!isInitialised()) {
						// Set the data used by the tables
						setBean(ShowcaseDataUtil.createExampleData());
						setInitialised(true);
					}
				}

			};

			widget.setType(WTable.Type.TABLE);
			widget.setRowsPerPage(3);
			widget.setBeanProperty(".");

			// Column - First name
			WTextField textField = new WTextField();
			textField.setAccessibleText("First name");
			widget.addColumn(new WTableColumn("First name", textField));

			// Column - Last name
			textField = new WTextField();
			textField.setAccessibleText("Last name");
			widget.addColumn(new WTableColumn("Last name", textField));

			// Column - Date field
			WDateField dateField = new WDateField();
			dateField.setAccessibleText("Date of birth");
			widget.addColumn(new WTableColumn("Date of birth", dateField));

			// Define the expandable level. The row will expand if the bean has "extra" details
			SimpleBeanBoundTableModel.LevelDetails level1 = new SimpleBeanBoundTableModel.LevelDetails("documents", TravelDocPanel.class);

			// Setup model with column properties and the "expandable" level
			SimpleBeanBoundTableModel model = new SimpleBeanBoundTableModel(
					new String[]{"firstName", "lastName",
						"dateOfBirth"}, level1);

			model.setSelectable(true);
			model.setEditable(true);
			model.setComparator(0, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
			model.setComparator(1, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);

			widget.setTableModel(model);

			// SAMPLE-FINISH
			add(widget);

		}

		@Override
		public WTable getWidget() {
			return widget;
		}

		@Override
		public AjaxTarget getAjaxTarget() {
			return widget;
		}

	}

	public static class PropertiesPanel extends AbstractPropertyContainer<WTable> {

		public final static SimpleBeanBoundTableModel EXPAND_MODEL;
		private final static SimpleBeanBoundTableModel HIERARCHIC_MODEL;

		static {
			// Define the expandable level. The row will expand if the bean has "extra" details
			SimpleBeanBoundTableModel.LevelDetails level1 = new SimpleBeanBoundTableModel.LevelDetails("documents", TravelDocPanel.class);

			// Setup model with column properties and the "expandable" level
			EXPAND_MODEL = new SimpleBeanBoundTableModel(
					new String[]{"firstName", "lastName",
						"dateOfBirth"}, level1);

			EXPAND_MODEL.setSelectable(true);
			EXPAND_MODEL.setEditable(true);
			EXPAND_MODEL.setComparator(0, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
			EXPAND_MODEL.setComparator(1, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);

			// Setup model with column properties and the "level" to iterate on (ie more details)
			HIERARCHIC_MODEL = new SimpleBeanBoundTableModel(
					new String[]{"firstName", "lastName",
						"dateOfBirth"}, "more");

			HIERARCHIC_MODEL.setIterateFirstLevel(true);
			HIERARCHIC_MODEL.setSelectable(true);
			HIERARCHIC_MODEL.setEditable(true);
			HIERARCHIC_MODEL.setComparator(0, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
			HIERARCHIC_MODEL.setComparator(1, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);

		}
		/**
		 * Default rows per page on the example tables.
		 */
		private static final int DEFAULT_ROWS_PER_PAGE = 3;
		/**
		 * Default rows per page options.
		 */
		private static final List<Integer> DEFAULT_ROWS_OPTIONS = Arrays.asList(0, 3, 5);

		private final WCheckBoxSelect cbsMargin = new MarginCheckboxSelect();

		/**
		 * Select Type.
		 */
		private final WCheckBox chbHeirarchic = new WCheckBox();

		/**
		 * Select Options.
		 */
		private final WRadioButtonSelect rbsRowsOption = new WRadioButtonSelect(ROWS_OPTION.values());

		/**
		 * Select Options.
		 */
		private final TypeWDropdown<WTable.SelectMode> drpSelect = new TypeWDropdown(WTable.SelectMode.values());
		/**
		 * Select All Options.
		 */
		private final TypeWDropdown<WTable.SelectAllType> drpSelectAll = new TypeWDropdown(WTable.SelectAllType.values());
		/**
		 * Expand Options.
		 */
		private final TypeWDropdown<WTable.ExpandMode> drpExpandMode = new TypeWDropdown(WTable.ExpandMode.values());

		/**
		 * Paging Options.
		 */
		private final TypeWDropdown<WTable.PaginationMode> drpPaging = new TypeWDropdown(WTable.PaginationMode.values());
		/**
		 * number of rows per page selection.
		 */
		private final WNumberField numRowsPerPage = new WNumberField();
		/**
		 * Location of pagination controls.
		 */
		private final TypeWDropdown<WTable.PaginationLocation> drpPaginationControlsLocation = new TypeWDropdown(WTable.PaginationLocation.values());
		/**
		 * Striping Options.
		 */
		private final TypeWDropdown<WTable.StripingType> drpStriping = new TypeWDropdown(WTable.StripingType.values());
		/**
		 * Separator Options.
		 */
		private final TypeWDropdown<WTable.SeparatorType> drpSeparator = new TypeWDropdown(WTable.SeparatorType.values());
		/**
		 * Sorting Options.
		 */
		private final TypeWDropdown<WTable.SortMode> drpSorting = new TypeWDropdown(WTable.SortMode.values());
		/**
		 * Column header toggle.
		 */
		private final WCheckBox chbShowColHeaders = new WCheckBox();
		/**
		 * Expand all toggle.
		 */
		private final WCheckBox chbExpandAll = new WCheckBox();
		/**
		 * Editable.
		 */
		private final WCheckBox chbEditable = new WCheckBox();
		/**
		 * Column order.
		 */
		private final WMultiSelectPair multiColumnOrder = new WMultiSelectPair(Arrays.asList(COLUMN.values()));

		/**
		 * Caption text.
		 */
		private final WTextField tfCaption = new WTextField();

		public PropertiesPanel(final SampleContainer<WTable> sampleContainer) {
			super(sampleContainer);

			// Create Options
			numRowsPerPage.setNumber(DEFAULT_ROWS_PER_PAGE);
			numRowsPerPage.setMinValue(1);

			multiColumnOrder.setSelected(multiColumnOrder.getOptions());
			multiColumnOrder.setMinSelect(1);
			multiColumnOrder.setShuffle(true);
			multiColumnOrder.setMandatory(true);

			rbsRowsOption.setButtonLayout(WRadioButtonSelect.Layout.FLAT);
			rbsRowsOption.setFrameless(true);

			// Defaults
			rbsRowsOption.setSelected(ROWS_OPTION.VALUE);
			drpSelect.setSelected(WTable.SelectMode.NONE);
			drpSelectAll.setSelected(WTable.SelectAllType.NONE);
			drpExpandMode.setSelected(WTable.ExpandMode.NONE);
			drpPaging.setSelected(WTable.PaginationMode.NONE);
			drpStriping.setSelected(WTable.StripingType.NONE);
			drpSeparator.setSelected(WTable.SeparatorType.NONE);
			drpSorting.setSelected(WTable.SortMode.NONE);
			chbShowColHeaders.setSelected(true);
			drpPaginationControlsLocation.setSelected(WTable.PaginationLocation.AUTO);

			addPropertyWidget("Margin", cbsMargin);
			// Select options
			addPropertyWidget("Select Mode", drpSelect);
			WField fieldSelectAllTypes = addPropertyWidget("Select All Type", drpSelectAll);
			// Expand Options
			addPropertyWidget("Expand Mode", drpExpandMode);
			WField fieldExpandAll = addPropertyWidget("Expand all", chbExpandAll);
			WField fieldHeirarchic = addPropertyWidget("Heirarchic", chbHeirarchic);
			// Paging options
			addPropertyWidget("Paging Mode", drpPaging);
			WField fieldPaginationLocation = addPropertyWidget("Location of pagination controls", drpPaginationControlsLocation);
			WField fieldRowsOptions = addPropertyWidget("Rows", rbsRowsOption);
			WField fieldRows = addPropertyWidget("Fixed rows per page", numRowsPerPage);

			// Other
			addPropertyWidget("Striping Type", drpStriping);
			addPropertyWidget("Separator Type", drpSeparator);
			addPropertyWidget("Sort Mode", drpSorting);
			addPropertyWidget("Show col headers", chbShowColHeaders);
			addPropertyWidget("Editable", chbEditable);
			addPropertyWidget("Caption", tfCaption);

			// Column Order
			addPropertyWidget("Column order", multiColumnOrder);

			WSubordinateControl ctrl = new WSubordinateControl();
			add(ctrl);

			// SelectMode rule
			Condition cond = new NotEqual(drpSelect, WTable.SelectMode.MULTIPLE);
			ctrl.addRule(PropertyUtil.buildHideShowRule(cond, fieldSelectAllTypes));
			// ExpandMode rule
			ctrl.addRule(PropertyUtil.buildEqualsHideShowRule(drpExpandMode, WTable.ExpandMode.NONE, fieldExpandAll, fieldHeirarchic));
			// PaginationMode rules
			ctrl.addRule(PropertyUtil.buildEqualsHideShowRule(drpPaging, WTable.PaginationMode.NONE, fieldPaginationLocation, fieldRowsOptions));
			cond = new Or(new Equal(drpPaging, WTable.PaginationMode.NONE), new Equal(rbsRowsOption, ROWS_OPTION.OPTIONS));
			ctrl.addRule(PropertyUtil.buildHideShowRule(cond, fieldRows));

		}

		@Override
		protected void configWidgetProperties(final WTable widget) {

			MarginCheckboxSelect.configMargin(widget, cbsMargin.getSelected());

			WTable.Type type = chbHeirarchic.isSelected() && drpExpandMode.getSelected() != WTable.ExpandMode.NONE ? WTable.Type.HIERARCHIC : WTable.Type.TABLE;

			// Check if type has changed
			if (widget.getType() != type) {
				widget.reset();
				widget.setType(type);
				widget.setTableModel(type == WTable.Type.HIERARCHIC ? HIERARCHIC_MODEL : EXPAND_MODEL);
			}

			// Select Mode
			widget.setSelectMode(drpSelect.getSelected());
			widget.setSelectAllMode(drpSelectAll.getSelected());

			// Expand Mode
			widget.setExpandMode(drpExpandMode.getSelected());
			widget.setExpandAll(chbExpandAll.isSelected());

			widget.setSortMode(drpSorting.getSelected());
			widget.setStripingType(drpStriping.getSelected());
			widget.setSeparatorType(drpSeparator.getSelected());
			widget.setShowColumnHeaders(chbShowColHeaders.isSelected());
			widget.setEditable(chbEditable.isSelected());
			widget.setCaption(tfCaption.getText());

			// Pagination
			widget.setPaginationMode(drpPaging.getSelected());
			if (drpPaging.getSelected() == WTable.PaginationMode.NONE) {
				widget.setRowsPerPage(DEFAULT_ROWS_PER_PAGE);
				widget.setRowsPerPageOptions(null);
				widget.setPaginationLocation(WTable.PaginationLocation.AUTO);
			} else {
				if (rbsRowsOption.getSelected() == ROWS_OPTION.OPTIONS) {
					widget.setRowsPerPageOptions(DEFAULT_ROWS_OPTIONS);
				} else {
					widget.setRowsPerPageOptions(null);
					int rows;
					if (numRowsPerPage.isEmpty() || numRowsPerPage.getNumber().intValue() < 1) {
						rows = DEFAULT_ROWS_PER_PAGE;
					} else {
						rows = numRowsPerPage.getNumber().intValue();
					}
					widget.setRowsPerPage(rows);
				}
				widget.setPaginationLocation(drpPaginationControlsLocation.getSelected());
			}

			if (PropertyUtil.isPropertyValid(multiColumnOrder)) {
				List<COLUMN> cols = (List<COLUMN>) multiColumnOrder.getSelected();
				int[] order = new int[cols.size()];
				int i = 0;
				for (COLUMN col : cols) {
					order[i++] = col.getCol();
				}
				widget.setColumnOrder(order);
			}
		}

	}

	/**
	 * An example component to display travel document details. Expects that the supplied bean is a {@link TravelDoc}.
	 */
	public static final class TravelDocPanel extends WBeanContainer {

		/**
		 * Creates a TravelDocPanel.
		 */
		public TravelDocPanel() {
			WHorizontalRule rule = new WHorizontalRule() {
				@Override
				public boolean isVisible() {
					List<Integer> index = TableUtil.getCurrentRowIndex(TravelDocPanel.this);
					// On expanded row, so check the index of the expanded level
					return index.get(1) > 0;
				}

			};
			add(rule);

			WText documentNumber = new WText();
			WText countryOfIssue = new WText();
			WText placeOfIssue = new WText();

			documentNumber.setBeanProperty("documentNumber");
			countryOfIssue.setBeanProperty("countryOfIssue");
			placeOfIssue.setBeanProperty("placeOfIssue");

			WDefinitionList list = new WDefinitionList(WDefinitionList.Type.COLUMN);
			add(list);

			list.addTerm("Document number", documentNumber);
			list.addTerm("Country of issue", countryOfIssue);
			list.addTerm("Place Of issue", placeOfIssue);
		}
	}

	/**
	 * Columns used on the table.
	 * <p>
	 * This enum is used as the options in the WShuffler to demonstrate how column orders can be changed.
	 * </p>
	 */
	private enum COLUMN {
		/**
		 * First name.
		 */
		FIRST_NAME(0, "First name"),
		/**
		 * Last name.
		 */
		LAST_NAME(1, "Last name"),
		/**
		 * Date of birth.
		 */
		DATE_OF_BIRTH(2, "Date of birth");

		/**
		 * Column index.
		 */
		private final int col;
		/**
		 * Column description.
		 */
		private final String desc;

		/**
		 * @param col the column index
		 * @param desc the column description
		 */
		COLUMN(final int col, final String desc) {
			this.col = col;
			this.desc = desc;
		}

		/**
		 * @return the col index
		 */
		public int getCol() {
			return col;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return desc;
		}

	}

	/**
	 * How to set the rows per page option.
	 */
	private enum ROWS_OPTION {
		/**
		 * Set value.
		 */
		VALUE("Fixed value"),
		/**
		 * Rows per page option.
		 */
		OPTIONS("Options");

		/**
		 * Column description.
		 */
		private final String desc;

		/**
		 * @param desc the column description
		 */
		ROWS_OPTION(final String desc) {
			this.desc = desc;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return desc;
		}

	}

}
