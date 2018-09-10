package com.github.bordertech.wcomponents.showcase.util;

import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.SubordinateTrigger;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.subordinate.Condition;
import com.github.bordertech.wcomponents.subordinate.Equal;
import com.github.bordertech.wcomponents.subordinate.Hide;
import com.github.bordertech.wcomponents.subordinate.Rule;
import com.github.bordertech.wcomponents.subordinate.Show;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jonathan
 */
public class PropertyUtil {

	private PropertyUtil() {
	}

	public static String getPropertyStringValue(final WTextField textField) {
		if (!isPropertyValid(textField)) {
			return null;
		}
		return textField.getValue();
	}

	public static Date getPropertyDateValue(final WDateField dateField) {
		if (!dateField.isParseable() || !isPropertyValid(dateField)) {
			return null;
		}
		return dateField.getValue();
	}

	public static int getPropertyIntValue(final WNumberField numField) {
		if (!isPropertyValid(numField)) {
			return 0;
		}
		BigDecimal value = numField.getValue();
		return value == null ? 0 : value.intValue();
	}

	public static boolean isPropertyValid(final Input propertyWidget) {
		List<Diagnostic> diags = new ArrayList<>();
		propertyWidget.validate(diags);
		return diags.isEmpty();
	}

	public static Rule buildEqualsHideShowRule(final SubordinateTrigger trigger, final Object value, final SubordinateTarget... targets) {
		Condition cond = new Equal(trigger, value);
		return buildHideShowRule(cond, targets);
	}

	public static Rule buildHideShowRule(final Condition condition, final SubordinateTarget... targets) {
		Rule rule = new Rule(condition);
		for (SubordinateTarget target : targets) {
			rule.addActionOnTrue(new Hide(target));
			rule.addActionOnFalse(new Show(target));
		}
		return rule;
	}

}
