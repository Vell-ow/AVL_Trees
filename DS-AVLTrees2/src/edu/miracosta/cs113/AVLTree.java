package edu.miracosta.cs113;

public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E>
{
	private boolean increase;
	
	/** add starter method.
	 * pre: the item to insert implements the Comparable interface.
	 * @param item The item being inserted.
	 * @return true if the object is inserted; false
	 * if the object already exists in the tree
	 * @throws ClassCastException if item is not Comparable
	 */
	public boolean add(E item)
	{
		increase = false;
		root = add((AVLNode<E>) root, item);
		return addReturn;		
	}
	
	/** Recursive add method. Inserts the given object into the tree.
	 * post: addReturn is set true if the item is inserted,
	 * false if the item is already in the tree.
	 * @param localRoot The local root of the subtree
	 * @param item The object to be inserted
	 * @return The new local root of the subtree with the item inserted
	 */
	private AVLNode<E> add(AVLNode<E> localRoot, E item)
	{
		// if the AVL tree's root is null
		if(localRoot == null)
		{
			// set the indicator that we have added something to the tree to true
			addReturn = true;
			// set the indicator that the height increased to true
			increase = true;
			// add the new node
			return new AVLNode<E>(item);
		}
		// new if statement
		// if the item is the same as the data already in the tree(at the root)
		if(item.compareTo(localRoot.data) == 0)
		{
			// Item is already in the tree.
			// The height of the tree has not increased
			increase = false;
			// We are not adding
			addReturn = false;
			// Return the data that's already in the tree
			return localRoot;
		}
		// otherwise if the item is smaller than the local root
		else if(item.compareTo(localRoot.data)< 0)
		{
			// item < data
			// place it in the left subtree
			localRoot.left = add((AVLNode<E>) localRoot.left, item);
			// If the height has increased
			if(increase)
			{
				// Take the balance down by 1
				decrementBalance(localRoot);
				// If the local root balance is really left heavy
				if(localRoot.balance < AVLNode.LEFT_HEAVY)
				{
					// We do not increase the height of the tree
					increase = false;
					// Return the leftward rebalancing of the local root
					return rebalanceLeft(localRoot);
				}
			}
		}
		// Otherwise
		else
		{
			// Add a new node to the rightward half of the local root
			localRoot.right = add((AVLNode<E>) localRoot.right, item);
			// If we increase the height
			if(increase)
			{
				// Take the balance up by 1
				incrementBalance(localRoot);
				// If the local root balance is really right heavy
				if(localRoot.balance > AVLNode.RIGHT_HEAVY)
				{
					// We do not increase the height
					increase = false;
					// Return the rightward rebalancing of the local root
					return rebalanceRight(localRoot);
				}
			}			
		}
		// Return the local root
		return localRoot;
	}
	
	/**
	 * 1. If the left subtree has a positive balance (Left-Right case)
	 * 2. If the left-left subtree has a negative balance (Left-Right-Left case)
	 * 3. Set the left subtree (new left subtree) balance to 0.
	 * 4. Set the left-left subtree (new root) balance to 0.
	 * 5. Set the local root (new right subtree) balance to +1.
	 * else(Left-Right-Right case)
	 * 6. Set the left subtree (new left subtree) balance to –1.
	 * 7. Set the left-left subtree (new root) balance to 0.
	 * 8. Set the local root (new right subtree) balance to 0.
	 * 9. Rotate the left subtree left.
	 * else(Left-Left case)
	 * 10. Set the left subtree balance to 0.
	 * 11. Set the local root balance to 0.
	 * 12. Rotate the local root right.
	 */
	
	/** Method to rebalance left.
	 * pre: localRoot is the root of an AVL subtree that is
	 * critically left-heavy.
	 * post: Balance is restored.
	 * @param localRoot Root of the AVL subtree
	 * that needs rebalancing
	 * @return a new localRoot
	 */
	private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot)
	{
		// Obtain reference to left child.
		AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
		// See whether left-right heavy.
		if(leftChild.balance > AVLNode.BALANCED)
		{
			// Obtain reference to left-right child.
			AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
		
			/** Adjust the balances to be their new values after 
			 * the rotations are performed.
			 */
			if(leftRightChild.balance < AVLNode.BALANCED)
			{
				leftChild.balance = AVLNode.BALANCED;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.RIGHT_HEAVY;
			}
			else if(leftRightChild.balance > AVLNode.BALANCED)
			{
				leftChild.balance = AVLNode.LEFT_HEAVY;
				leftRightChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			else
			{
				leftChild.balance = AVLNode.BALANCED;
				localRoot.balance = AVLNode.BALANCED;
			}
			System.out.println("Rotate Left");
			localRoot.left = rotateLeft(leftChild);
		}
		else
		{
			// Left-Left case
			/** In this case, the leftChild(the new root)
			 * and the root (new right child) will both be balanced
			 * after the rotation.
			 */
			leftChild.balance = AVLNode.BALANCED;
			localRoot.balance = AVLNode.BALANCED;
		}
		// Now rotate the local root right.
		System.out.println("Right Rotation");
		return (AVLNode<E>) rotateRight(localRoot);
	}
	
	private void incrementBalance(AVLNode<E> node)
	{
		// Increment the balance.
		node.balance++;
		if(node.balance == AVLNode.BALANCED)
		{
			// If now balanced, overall height has not increased.
			increase = false;
		}
	}
	
	private void decrementBalance(AVLNode<E> node)
	{
		// Decrement the balance.
		node.balance--;
		if(node.balance == AVLNode.BALANCED)
		{
			// If now balanced, overall height has not increased.
			increase = false;
		}
	}
	
	/**
	 * 1. If the right subtree has a positive balance (Right-Left case)
	 * 2. If the right-right subtree has a negative balance (Right-Left-Right case)
	 * 3. Set the right subtree (new right subtree) balance to 0.
	 * 4. Set the right-right subtree (new root) balance to 0.
	 * 5. Set the local root (new left subtree) balance to +1.
	 * else(Right-Left-Left case)
	 * 6. Set the right subtree (new right subtree) balance to –1.
	 * 7. Set the Right-Right subtree (new root) balance to 0.
	 * 8. Set the local root (new left subtree) balance to 0.
	 * 9. Rotate the right subtree right.
	 * else(Right-Right case)
	 * 10. Set the right subtree balance to 0.
	 * 11. Set the local root balance to 0.
	 * 12. Rotate the local root left.
	 */	
	
	/** Method to rebalance right.
	 * pre: localRoot is the root of an AVL subtree that is
	 * critically right-heavy.
	 * post: Balance is restored.
	 * @param localRoot Root of the AVL subtree
	 * that needs rebalancing
	 * @return a new localRoot
	 */
	private AVLNode<E> rebalanceRight(AVLNode<E> localRoot)
	{
		// Obtain reference to right child.
		AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
		// See whether right-left heavy.
		if(rightChild.balance < AVLNode.BALANCED)
		{
			// Obtain reference to right-left child.
			AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;
		
			/** Adjust the balances to be their new values after 
			 * the rotations are performed.
			 */
			// If the balance of the right-left child is right-heavy
			if(rightLeftChild.balance > AVLNode.BALANCED)
			{
				// Balance the right child node
				rightChild.balance = AVLNode.BALANCED;
				// Balance the right-left child node
				rightLeftChild.balance = AVLNode.BALANCED;
				// The local root's balance is now left-heavy
				localRoot.balance = AVLNode.LEFT_HEAVY;
			}
			// Otherwise, if the right-left child is left-heavy
			else if(rightLeftChild.balance < AVLNode.BALANCED)
			{
				// The right child node is now right-heavy
				rightChild.balance = AVLNode.RIGHT_HEAVY;
				// Balance the right-left child node
				rightLeftChild.balance = AVLNode.BALANCED;
				// Balance the local root
				localRoot.balance = AVLNode.BALANCED;
			}
			// Otherwise
			else
			{
				// Balance the right child node
				rightChild.balance = AVLNode.BALANCED;
				// Balance the local root
				localRoot.balance = AVLNode.BALANCED;
			}
			System.out.println("Rotate Right");
			// Rotate the right child of the local root's right subtree
			localRoot.right = rotateRight(rightChild);
		}
		// Otherwise
		else
		{
			// Right-Right case
			/** In this case, the rightChild(the new root)
			 * and the root (new left child) will both be balanced
			 * after the rotation.
			 */
			// Balance the right child node
			rightChild.balance = AVLNode.BALANCED;
			// Balance the local root
			localRoot.balance = AVLNode.BALANCED;
		}
		// Now rotate the local root left.
		System.out.println("Rotate left");
		return (AVLNode<E>) rotateLeft(localRoot);
	}
	
	/** Class to represent an AVL Node. It extends the
	 *	BinaryTree.Node by adding the balance field.
	 */
	private static class AVLNode<E> extends Node<E>
	{
		// Constant to indicate left-heavy
		public static final int LEFT_HEAVY = -1;
		// constant to indicate balanced 
		public static final int BALANCED = 0;
		// constant to indicate right-heavy
		public static final int RIGHT_HEAVY = 1;
		// balance is right subtree height - left subtree height
		private int balance;
		
		// Methods
		/** Construct a node with the given item as the data field.
		 * @param item The data field
		 */
		public AVLNode(E item)
		{
			super(item);
			balance = BALANCED;
		}
		
		/** Return a string representation of this object.
		 * The balance value is appended to the contents.
		 * @return String representation of this object
		 */
		@Override
		public String toString()
		{
			return balance + ": " + super.toString();
		}
	}
}
