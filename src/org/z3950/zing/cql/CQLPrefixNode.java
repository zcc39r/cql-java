// $Id: CQLPrefixNode.java,v 1.2 2002-11-14 22:07:32 mike Exp $

package org.z3950.zing.cql;
import java.lang.String;
import java.util.Properties;


/**
 * Represents a prefix node in a CQL parse-tree.
 *
 * @version	$Id: CQLPrefixNode.java,v 1.2 2002-11-14 22:07:32 mike Exp $
 */
public class CQLPrefixNode extends CQLNode {
    /**
     * The prefix definition that governs the subtree.
     */
    public CQLPrefix prefix;

    /**
     * The root of a parse-tree representing the part of the query
     * that is governed by this prefix definition.
     */ 
    public CQLNode subtree;

    /**
     * Creates a new CQLPrefixNode inducing a mapping from the
     * specified qualifier-set name to the specified identifier across
     * the specified subtree.
     */
    public CQLPrefixNode(String name, String identifier, CQLNode subtree) {
	this.prefix = new CQLPrefix(name, identifier);
	this.subtree = subtree;
    }

    public String toXCQL(int level) {
	String maybeName = "";
	if (prefix.name != null)
	    maybeName = indent(level+1) + "<name>" + prefix.name + "</name>\n";

	return (indent(level) + "<prefix>\n" + maybeName +
		indent(level+1) +
		    "<identifier>" + prefix.identifier + "</identifier>\n" +
		subtree.toXCQL(level+1) +
		indent(level) + "</prefix>\n");
    }

    public String toCQL() {
	// ### We don't always need parens around the operand
	return ">" + prefix.name + "=\"" + prefix.identifier + "\" " +
	    "(" + subtree.toCQL() + ")";
    }

    public String toPQF(Properties config) throws PQFTranslationException {
	// Prefixes and their identifiers don't actually play any role
	// in PQF translation, since the meanings of the qualifiers,
	// including their prefixes if any, are instead wired into
	// `config'.
	return subtree.toPQF(config);
    }
}