package com.github.bordertech.wcomponents.showcase.util;

import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WTextField;
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

}
