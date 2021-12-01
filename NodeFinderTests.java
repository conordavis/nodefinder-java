import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeFinderTests {
    Node a;
    Node b;
    Node c;
    Node d;
    Node e;
    Node f;

    @BeforeEach
    void init() {
        // Tree structure:
        //     a
        //    / \
        //   b   d
        //  /   / \
        // c   e   f
        a = new Node(1);
        b = new Node(2);
        c = new Node(3);
        d = new Node(4);
        e = new Node(5);
        f = new Node(1);  // shares same value as node A

        a.left = b;
        a.right = d;
        b.left = c;
        b.right = null;
        c.left = null;
        c.right = null;
        d.left = e;
        d.right = f;
        e.left = null;
        e.right = null;
        f.left = null;
        f.right = null;
    }

    @Test
    @DisplayName("If multiple nodes match the target value, NodeFinder is allowed to return any of them.")
    void ambiguousMatch() {
        Node result = NodeFinder.find(a, 1);
        assertTrue(result == a || result == f);
    }

    @Test
    @DisplayName("Searching an empty tree should always return null.")
    void emptyTree() {
        assertSame(null, NodeFinder.find(null, 1));
    }

    @Test
    void leftChild() {
        assertSame(b, NodeFinder.find(a, 2));
        assertSame(c, NodeFinder.find(b, 3));
    }

    @Test
    void leftGrandchild() {
        assertSame(c, NodeFinder.find(a, 3));
    }

    @Test
    void notFound() {
        assertNull(NodeFinder.find(a, 6));
        // A node should not be able to search inside its parent or siblings.
        assertNull(NodeFinder.find(b, 1));
        assertNull(NodeFinder.find(d, 2));
    }

    @Test
    void rightChild() {
        assertSame(d, NodeFinder.find(a, 4));
        // Node D knows nothing of node A, so searching inside D should always return F instead of A.
        assertSame(f, NodeFinder.find(d, 1));
    }

    @Test
    void rightGrandchild() {
        assertSame(e, NodeFinder.find(a, 5));
    }

    @Test
    void self() {
        assertSame(b, NodeFinder.find(b, 2));
        assertSame(c, NodeFinder.find(c, 3));
        assertSame(d, NodeFinder.find(d, 4));
        assertSame(e, NodeFinder.find(e, 5));
        // Node F knows nothing of node A, so searching inside F should always return F instead of A.
        assertSame(f, NodeFinder.find(f, 1));
    }
}
