package edu.miracosta.cs113;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * BinarySearchTreeTest : Tester class for a binary search tree implementation.
 *
 * @author King
 * @version 1.0
 */
public class BinarySearchTreeTest {

    /** Data to populate a BinarySearchTree. */
    private static final String[] STRING_DATA = {"The", "quick", "brown", "fox", "jumps",
            "over", "the", "lazy", "dog"};

    /** Data for testing this BinarySearchTree's find failure. */
    private static final String FIND_FAIL_DATA = "DOG";

    /** A BinaryTree to contain String data. */
    private BinarySearchTree<String> strTree;

    /** Helper method to populate the BinarySearchTree. Relies upon a correct implementation of the add method. */
    @Before
    public void buildTreeWithAdd() {
        strTree = new BinarySearchTree<String>();
        for (String s : STRING_DATA) {
            assertTrue("Method buildTree failed.", strTree.add(s));
        }
    }

    @Test
    public void testAddFailure() {
        assertFalse("Test add failed - the addition of a duplicate value has occurred.",
                strTree.add(STRING_DATA[0]));
    }

    @Test
    public void testFind() {
        assertEquals("Test find failed - expected element not found in tree.",
                STRING_DATA[5], strTree.find(STRING_DATA[5]));
    }

    @Test
    public void testFindFailure() {
        assertEquals("Test find failed - expected null.",
                null, strTree.find(FIND_FAIL_DATA));
    }

    @Test
    public void testContains() {
        assertTrue("Test contains failed - expected element should exist in this tree.",
                strTree.contains(STRING_DATA[4]));
    }

    @Test
    public void testContainsFailure() {
        assertFalse("Test contains failed - given element should have not been added to this tree.",
                strTree.contains(FIND_FAIL_DATA));
    }

    @Test
    public void testRemove() {
        // Remove element
        assertTrue("Test remove failed - an element that was added to this tree should be removed.",
                strTree.remove(STRING_DATA[5]));

        // Validate with contains
        assertFalse("Test remove failed - an element that was added to this tree should be removed.",
                strTree.contains(STRING_DATA[5]));
    }

    @Test
    public void testRemoveFailure() {
        assertFalse("Test remove failed - element intended for removal does not exist in this tree.",
                strTree.remove(FIND_FAIL_DATA));
    }

    @Test
    public void testDelete() {
        // Delete element
        assertEquals("Test delete failed - method should return data of removed element.",
                STRING_DATA[7], strTree.delete(STRING_DATA[7]));

        // Validate with contains
        assertFalse("Test delete failed - an element that was added to this list should be deleted.",
                strTree.contains(STRING_DATA[7]));
    }

    @Test
    public void testDeleteFailure() {
        assertEquals("Test delete failed - element intended for deletion does not exist in this tree.",
                null, strTree.delete(FIND_FAIL_DATA));
    }

    @Test
    public void testToString() {
        // TODO: =>
        // Validate simple toString constructed through an inorder traversal.

        System.out.println("Simple toString:\n\n" + strTree);
        System.out.println("Polymorphic toString:\n\n" + strTree.toString2());
    }


} // End of class BinarySearchTreeTest