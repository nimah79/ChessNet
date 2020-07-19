package util;

public interface RadixTreeVisitor<V, R> {

	/**
	 * Visits a node in a radix tree.
	 * 
	 * @param key
	 *            the key of the node being visited
	 * @param value
	 *            the value of the node being visited
	 */
	public abstract void visit(String key, V value);

	/**
	 * An overall result from the traversal of the radix tree.
	 * 
	 * @return the result
	 */
	public abstract R getResult();

}