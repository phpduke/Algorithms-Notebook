import unittest
from graphx import Hash, Graph, BFS


class TestHash(unittest.TestCase):
    """Test cases for Hash class."""
    
    def test_hash_int(self):
        """Test hashing integers."""
        h = Hash()
        self.assertEqual(h.hash(1), 0)
        self.assertEqual(h.hash(2), 1)
        self.assertEqual(h.hash(1), 0)
    
    def test_hash_tuple2(self):
        """Test hashing 2-tuples."""
        h = Hash()
        self.assertEqual(h.hash((1, 2)), 0)
        self.assertEqual(h.hash((3, 4)), 1)
        self.assertEqual(h.hash((1, 2)), 0)
    
    def test_hash_tuple3(self):
        """Test hashing 3-tuples."""
        h = Hash()
        self.assertEqual(h.hash((1, 2, 3)), 0)
        self.assertEqual(h.hash((4, 5, 6)), 1)
        self.assertEqual(h.hash((1, 2, 3)), 0)
    
    def test_hash_consistency(self):
        """Test that equivalent inputs produce same hash."""
        h = Hash()
        self.assertEqual(h.hash(5), h.hash((5, 0, 0)))
        self.assertEqual(h.hash((5, 10)), h.hash((5, 10, 0)))


class TestGraph(unittest.TestCase):
    """Test cases for Graph class."""
    
    def test_directed_graph(self):
        """Test directed graph creation."""
        g = Graph(n=5, is_directed=True)
        self.assertTrue(g.is_directed)
        self.assertEqual(g.n, 5)
    
    def test_undirected_graph(self):
        """Test undirected graph creation."""
        g = Graph(n=5, is_directed=False)
        self.assertFalse(g.is_directed)
    
    def test_add_edge_directed(self):
        """Test adding edges to directed graph."""
        g = Graph(n=5, is_directed=True)
        g.add_edge(0, 1, weight=5)
        
        u_hash = g.h.hash(0)
        v_hash = g.h.hash(1)
        
        self.assertEqual(len(g.adj[u_hash]), 1)
        self.assertEqual(g.adj[u_hash][0], (v_hash, 5))
        self.assertEqual(len(g.adj[v_hash]), 0)
    
    def test_add_edge_undirected(self):
        """Test adding edges to undirected graph."""
        g = Graph(n=5, is_directed=False)
        g.add_edge(0, 1, weight=3)
        
        u_hash = g.h.hash(0)
        v_hash = g.h.hash(1)
        
        self.assertEqual(len(g.adj[u_hash]), 1)
        self.assertEqual(len(g.adj[v_hash]), 1)
        self.assertEqual(g.adj[u_hash][0], (v_hash, 3))
        self.assertEqual(g.adj[v_hash][0], (u_hash, 3))
    
    def test_add_edge_tuple(self):
        """Test adding edges with tuple nodes."""
        g = Graph(n=10, is_directed=True)
        g.add_edge((0, 0), (1, 1), weight=7)
        
        u_hash = g.h.hash((0, 0))
        v_hash = g.h.hash((1, 1))
        
        self.assertEqual(len(g.adj[u_hash]), 1)
        self.assertEqual(g.adj[u_hash][0], (v_hash, 7))


class TestBFS(unittest.TestCase):
    """Test cases for BFS class."""
    
    def test_simple_path(self):
        """Test BFS on simple path graph."""
        g = Graph(n=5, is_directed=True)
        g.add_edge(0, 1)
        g.add_edge(1, 2)
        g.add_edge(2, 3)
        
        bfs = BFS(g)
        bfs.run(0)
        
        self.assertEqual(bfs.min_dist(0), 0)
        self.assertEqual(bfs.min_dist(1), 1)
        self.assertEqual(bfs.min_dist(2), 2)
        self.assertEqual(bfs.min_dist(3), 3)
        self.assertEqual(bfs.min_dist(4), -1)
    
    def test_unreachable_node(self):
        """Test BFS with unreachable nodes."""
        g = Graph(n=5, is_directed=True)
        g.add_edge(0, 1)
        g.add_edge(2, 3)
        
        bfs = BFS(g)
        bfs.run(0)
        
        self.assertTrue(bfs.is_visited(0))
        self.assertTrue(bfs.is_visited(1))
        self.assertFalse(bfs.is_visited(2))
        self.assertFalse(bfs.is_visited(3))
    
    def test_undirected_graph_bfs(self):
        """Test BFS on undirected graph."""
        g = Graph(n=4, is_directed=False)
        g.add_edge(0, 1)
        g.add_edge(1, 2)
        g.add_edge(2, 3)
        
        bfs = BFS(g)
        bfs.run(0)
        
        self.assertEqual(bfs.min_dist(3), 3)
        
        bfs.clear()
        bfs.run(3)
        self.assertEqual(bfs.min_dist(0), 3)
    
    def test_cycle_graph(self):
        """Test BFS on graph with cycles."""
        g = Graph(n=4, is_directed=True)
        g.add_edge(0, 1)
        g.add_edge(1, 2)
        g.add_edge(2, 3)
        g.add_edge(3, 1)
        
        bfs = BFS(g)
        bfs.run(0)
        
        self.assertEqual(bfs.min_dist(1), 1)
        self.assertEqual(bfs.min_dist(2), 2)
        self.assertEqual(bfs.min_dist(3), 3)
    
    def test_tuple_nodes(self):
        """Test BFS with tuple nodes."""
        g = Graph(n=10, is_directed=True)
        g.add_edge((0, 0), (1, 1))
        g.add_edge((1, 1), (2, 2))
        
        bfs = BFS(g)
        bfs.run((0, 0))
        
        self.assertEqual(bfs.min_dist((0, 0)), 0)
        self.assertEqual(bfs.min_dist((1, 1)), 1)
        self.assertEqual(bfs.min_dist((2, 2)), 2)
    
    def test_clear(self):
        """Test BFS clear and rerun."""
        g = Graph(n=3, is_directed=True)
        g.add_edge(0, 1)
        g.add_edge(1, 2)
        
        bfs = BFS(g)
        bfs.run(0)
        self.assertTrue(bfs.is_visited(1))
        
        bfs.clear()
        self.assertFalse(bfs.is_visited(1))
        
        bfs.run(1)
        self.assertTrue(bfs.is_visited(2))
        self.assertEqual(bfs.min_dist(2), 1)


class TestIntegration(unittest.TestCase):
    """Integration tests combining multiple components."""
    
    def test_complete_workflow(self):
        """Test complete workflow with all features."""
        g = Graph(n=6, is_directed=False)
        
        g.add_edge(0, 1, weight=1)
        g.add_edge(0, 2, weight=2)
        g.add_edge(1, 3, weight=3)
        g.add_edge(2, 3, weight=4)
        g.add_edge(3, 4, weight=5)
        
        bfs = BFS(g)
        bfs.run(0)
        
        self.assertEqual(bfs.min_dist(4), 3)
        self.assertTrue(bfs.is_visited(3))
        self.assertFalse(bfs.is_visited(5))


if __name__ == '__main__':
    unittest.main()
