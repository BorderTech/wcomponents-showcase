package com.github.bordertech.wcomponents.showcase;

import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.util.StreamUtil;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * SourceUtil helps render the java source and java doc from a component.
 * </p>
 *
 * It performs a series of operations on the inbound source to:
 *
 * <ul>
 * <li>Extract the java doc</li>
 * <li>remove any asterisks</li>
 * <li>remove the link items</li>
 * </ul>
 *
 * this class may need further enhancements as it is fairly simplistic at the moment.
 *
 * @author Steve Harney
 * @since 1.0.0
 */
public final class SourceUtil {

	/**
	 * Logger for this class.
	 */
	private static final Log LOG = LogFactory.getLog(SourceUtil.class);

	private SourceUtil() {
	}

	/**
	 * Tries to obtain the source file for the given class.
	 *
	 * @param className the name of the class to find the source for.
	 * @return the source file for the given class, or null on error.
	 */
	public static String getSource(final String className) {
		String sourceName = '/' + className.replace('.', '/') + ".java";

		InputStream stream = null;

		try {
			stream = WTextField.class.getResourceAsStream(sourceName);

			if (stream != null) {
				byte[] sourceBytes = StreamUtil.getBytes(stream);

				// we need to do some basic formatting of the source now.
				return new String(sourceBytes, "UTF-8");
			}
		} catch (IOException e) {
			LOG.warn("Unable to read source code for class " + className, e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					LOG.error("Error closing stream", e);
				}
			}
		}

		return null;
	}

	/**
	 * @param source the source to extract java doc from
	 */
	public static String getJavaDoc(final String source) {

		if (source == null) {
			return ("<p>Unable to extract JavaDoc, no source available.</p>");
		} else {
			StringBuilder javaDoc = extractJavaDoc(source);
			stripAsterisk(javaDoc);
			stripLinks(javaDoc);
			return ("<div>" + javaDoc.toString() + "</div>");
		}
	}

	public static String formatSource(final String source) {

		if (source == null) {
			return "";
		}

		String formattedSource = source.replace(' ', '\u00a0'); // nbsp
		formattedSource = WebUtilities.encode(formattedSource); // escape content
		formattedSource = formattedSource.replaceAll("\\r?\\n", "<br/>");
		return formattedSource;
	}

	/**
	 * extracts the javadoc. It assumes that the java doc for the class is the first javadoc in the file.
	 *
	 * @param source string representing the java class.
	 * @return a String builder containing the javadoc.
	 */
	private static StringBuilder extractJavaDoc(final String source) {
		int docStart = source.indexOf("/**");
		int docEnd = source.indexOf("*/", docStart);
		int classStart = source.indexOf("public class");
		int author = source.indexOf("@author");
		int since = source.indexOf("@since");

		if (classStart == -1) {
			classStart = docEnd;
		}
		if (docEnd == -1 || classStart < docStart) {
			return new StringBuilder("No JavaDoc provided");
		}
		if (author != -1 && author < docEnd) {
			docEnd = author;
		}
		if (since != -1 && since < docEnd) {
			docEnd = since;
		}

		return new StringBuilder(source.substring(docStart + 3, docEnd).trim());

	}

	/**
	 * This method removes the additional astrisks from the java doc.
	 *
	 * @param javaDoc the string builder containing the javadoc.
	 */
	private static void stripAsterisk(final StringBuilder javaDoc) {
		int index = javaDoc.indexOf("*");
		while (index != -1) {
			javaDoc.replace(index, index + 1, "");
			index = javaDoc.indexOf("*");
		}
	}

	/**
	 * this method is used to process the <code>@link</code> tags out of the javadoc.
	 *
	 * @param javaDoc the string builder containing the javadoc.
	 */
	private static void stripLinks(final StringBuilder javaDoc) {
		int startLink = javaDoc.indexOf("{@link ");
		while (startLink != -1) {
			int endLink = javaDoc.indexOf("}", startLink) + 1;
			String link = javaDoc.substring(startLink, endLink);
			String newLink = parseLink(link);
			javaDoc.replace(startLink, endLink, newLink);
			startLink = javaDoc.indexOf("{@link ");
		}
	}

	/**
	 * a helper method to process the links as they are found.
	 *
	 * @param link the string representing the original link.
	 * @return a new string to replace the old link.
	 */
	private static String parseLink(final String link) {
		String[] tokens = link.substring(7, link.length() - 1).split("\\s");
		if (tokens.length == 1) {
			return tokens[0];
		}
		StringBuilder result = new StringBuilder();

		boolean parametersSeen = false;
		boolean inParameters = false;

		for (int index = 0; index < tokens.length; index++) {
			result.append(" ").append(tokens[index]);
			if (tokens[index].indexOf('(') != -1 && !parametersSeen) {
				inParameters = true;
			}
			if (index == 1 && !inParameters) {
				result = new StringBuilder(tokens[index]);
			}
			if (tokens[index].indexOf(')') != -1 && !parametersSeen) {
				parametersSeen = true;
				if (index != tokens.length - 1) {
					result = new StringBuilder();
				}

			}

		}

		return result.toString().trim();
	}
}
