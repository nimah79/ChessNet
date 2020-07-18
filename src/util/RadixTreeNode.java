package util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A node in a radix tree.
 * 
 * @param <V>
 */
class RadixTreeNode<V extends Serializable> implements
		Iterable<RadixTreeNode<V>>, Comparable<RadixTreeNode<V>>, Serializable {

	private static final long serialVersionUID = -2727984614882104944L;

	/** The prefix at this node */
	private String prefix;

	/** The value stored at this node */
	private V value;

	/**
	 * Whether or not this node stores a value. This value is mainly used by
	 * {@link RadixTreeVisitor} to figure out whether or not this node should be
	 * visited.
	 */
	private boolean hasValue;

	/**
	 * The children for this node. Note, because we use {@link TreeSet} here,
	 * traversal of {@link RadixTree} will be in lexicographical order.
	 */
	private Collection<RadixTreeNode<V>> children;

	/**
	 * Constructs a node from the given prefix.
	 * 
	 * @param prefix
	 *            the prefix
	 * @param value
	 *            the value
	 */
	RadixTreeNode(String prefix) {
		this(prefix, null);
		this.hasValue = false;
	}

	/**
	 * Constructs a node from the given prefix and value.
	 * 
	 * @param prefix
	 *            the prefix
	 * @param value
	 *            the value
	 */
	RadixTreeNode(String prefix, V value) {
		this.prefix = prefix;
		this.value = value;
		this.hasValue = true;
	}

	/**
	 * Gets the value attached to this node.
	 * 
	 * @return the value, or <code>null</code> if an internal node
	 */
	V getValue() {
		return value;
	}

	/**
	 * Sets the value attached to this node.
	 * 
	 * @param value
	 *            the value, or <code>null</code> if an internal node
	 */
	void setValue(V value) {
		this.value = value;
	}

	/**
	 * Gets the prefix associated with this node.
	 * 
	 * @return the prefix
	 */
	String getPrefix() {
		return prefix;
	}

	/**
	 * Sets the prefix associated with this node.
	 * 
	 * @param prefix
	 *            the prefix
	 */
	void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the children of this node.
	 * 
	 * @return the list of children
	 */
	Collection<RadixTreeNode<V>> getChildren() {
		// Delayed creation of children to reduce memory cost
		if (children == null)
			children = new TreeSet<RadixTreeNode<V>>();
		return children;
	}

	/**
	 * Whether or not this node has a value attached to it.
	 * 
	 * @return whether or not this node has a value
	 */
	boolean hasValue() {
		return hasValue;
	}

	/**
	 * Sets whether or not this node has a value attached to it.
	 * 
	 * @param hasValue
	 *            <code>true</code> if this node will have a value,
	 *            <code>false</code> otherwise. If <code>false</code>,
	 *            {@link #getValue()} will return <code>null</code> after this
	 *            call.
	 */
	void setHasValue(boolean hasValue) {
		this.hasValue = hasValue;
		if (!hasValue)
			this.value = null;
	}

	@Override
	public Iterator<RadixTreeNode<V>> iterator() {
		if (children == null) {
			return new Iterator<RadixTreeNode<V>>() {
				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public RadixTreeNode<V> next() {
					return null;
				}

				@Override
				public void remove() {
					// Unimplemented
				}
			};
		}

		return children.iterator();
	}

	@Override
	public int compareTo(RadixTreeNode<V> node) {
		return prefix.toString().compareTo(node.getPrefix().toString());
	}

}