from collections import deque, defaultdict

class Graph:
    def __init__(self, directed=False):
        self.adj_list = defaultdict(list)
        self.directed = directed
    
    def add_edge(self, u, v):
        self.adj_list[u].append(v)
        if not self.directed:
            self.adj_list[v].append(u)
    
    def bfs(self, start):
        # Dictionary to store distance from start node
        distance = {node: float('inf') for node in self.adj_list}
        distance[start] = 0
        
        queue = deque([start])
        
        while queue:
            current_node = queue.popleft()
            
            for neighbor in self.adj_list[current_node]:
                if distance[neighbor] == float('inf'):  # Unvisited node
                    distance[neighbor] = distance[current_node] + 1
                    queue.append(neighbor)
        
        return distance
    
    def print_graph(self):
        for key, value in self.adj_list.items():
            print(f"{key}: {value}")

# Example usage:
if __name__ == "__main__":
    g = Graph(directed=False)
    g.add_edge(1, 2)
    g.add_edge(1, 3)
    g.add_edge(2, 4)
    g.add_edge(3, 5)
    g.add_edge(4, 5)

    # Print the adjacency list (for debugging)
    g.print_graph()

    # Perform BFS starting from node 1
    distances = g.bfs(1)
    print("\nDistances from node 1:")
    for node, dist in distances.items():
        print(f"Node {node}: Distance {dist}")
