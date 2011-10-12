package de.tud.cs.st.vespucci.generateprologfacts.creator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class encapsulates the invariant prolog facts i.e. header comments inside the prolog file.
 * 
 * @author Patrick Jahnke
 * @author Thomas Schulz
 * @author Alexander Weitzmann
 * 
 */
public class InvariantPrologFacts {

	/**
	 * Defines a string, that can be used to visually separate text in a file.
	 */
	private static final String VERTICAL_SECTION_SEPARATOR = "%------\n";

	/**
	 * @param fullPath
	 *            The absolute path including filename.
	 * @return Returns the prolog file header.
	 */
	public static String createFileHeader(final String fullPath) {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		// insert common information
		strBuilder.append("% Prolog based representation of the Vespucci architecture diagram: ");
		strBuilder.append(fullPath + "\n");
		strBuilder.append("% Created by Vespucci, Technische Universität Darmstadt, Department of Computer Science\n");
		strBuilder.append("% www.opal-project.de\n\n");
		strBuilder.append(":- multifile ensemble/5.\n");
		strBuilder.append(":- multifile abstract_ensemble/5.\n");
		strBuilder.append(":- multifile outgoing/7.\n");
		strBuilder.append(":- multifile incoming/7.\n");
		strBuilder.append(":- multifile not_allowed/7.\n");
		strBuilder.append(":- multifile expected/7.\n");
		strBuilder.append(":- multifile global_incoming/7.\n");
		strBuilder.append(":- multifile global_outgoing/7.\n");
		strBuilder.append(":- multifile violation/7.\n");
		strBuilder.append(":- discontiguous ensemble/5.\n");
		strBuilder.append(":- discontiguous abstract_ensemble/5.\n");
		strBuilder.append(":- discontiguous outgoing/7.\n");
		strBuilder.append(":- discontiguous incoming/7.\n");
		strBuilder.append(":- discontiguous not_allowed/7.\n");
		strBuilder.append(":- discontiguous expected/7.\n\n");
		strBuilder.append(":- discontiguous global_incoming/7.\n");
		strBuilder.append(":- discontiguous global_outgoing/7.\n");
		strBuilder.append(":- discontiguous violation/7.\n\n");
		// insert Date
		final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		final Date date = new Date();
		strBuilder.append("% Date <" + dateFormat.format(date) + ">.\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		strBuilder.append("\n");

		return strBuilder.toString();
	}

	/**
	 * The string contains an explanation of the keywords used to describe the ensembles.
	 * 
	 * @return Returns a string for the begin of the ensemble facts.
	 */
	public static String createEnsembleHeader() {

		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);

		strBuilder.append("%ensemble(File, Name, Ensemble Parameter, Query, SubEnsembles) :- Definition of an ensemble\n");
		strBuilder.append("%\tFile - The simple file name in which the ensemble is defined (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tName - Name of the ensemble\n");
		strBuilder.append("%\tEnsemble Parameter - Parameter of the ensemble\n");
		strBuilder.append("%\tQuery - Query that determines which source elements belong to the ensemble\n");
		strBuilder.append("%\tSubEnsembles - List of all sub ensembles of this ensemble\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		return strBuilder.toString();
	}

	/**
	 * The string contains an explanation of the keywords used to describe the dependencies.
	 * 
	 * @return Returns a string for the begin of the dependency facts.
	 */
	public static String createDependencyHeader() {
		final StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);

		strBuilder.append("%DEPENDENCY(File, ID, SourceE, SourceE Parameter, TargetE, TargetE Parameter, Type) :-")
		          .append("Definition of a dependency between two ensembles.\n");
		strBuilder.append("%\tDEPENDENCY - The type of the dependency. ")
		          .append("Possible values: outgoing, incoming, expected, not_allowed, ")
		          .append("global_incoming, global_outgoing, violation\n");
		strBuilder.append("%\tFile - The simple file name in which the dependency is defined (e.g., 'Flashcards.sad')\n");
		strBuilder.append("%\tID - An ID identifying the dependency\n");
		strBuilder.append("%\tSourceE - The source ensemble\n");
		strBuilder.append("%\tSourceE Parameter - Parameter of the source ensemble\n");
		strBuilder.append("%\tTargetE - The target ensemble\n");
		strBuilder.append("%\tTargetE Parameter - Parameter of the target ensemble\n");
		strBuilder.append("%\tRelation classifier - "
				+ "Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)\n");
		strBuilder.append(VERTICAL_SECTION_SEPARATOR);
		return strBuilder.toString();
	}
}
