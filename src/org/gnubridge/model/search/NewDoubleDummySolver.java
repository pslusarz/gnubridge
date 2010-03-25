package org.gnubridge.model.search;

import java.util.Collection;
import java.util.Stack;

public class NewDoubleDummySolver {

	private final Stack<AbstractNode> stack;

	public NewDoubleDummySolver(RootNode root) {
		stack = new Stack<AbstractNode>();
		stack.push(root);
	}

	public void evaluate() {
		while (!stack.isEmpty()) {
			AbstractNode node = stack.pop();
			node.expand();
			pushChildrenOnStack(node.getChildren());
			node.updateTree();
		}
	}

	private void pushChildrenOnStack(Collection<ChildNode> nodes) {
		for (ChildNode node : nodes) {
			stack.push(node);
		}

	}
}
