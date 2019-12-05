from math import inf
from heapq import heappush, heappop


class Node:
    def __init__(self, location, history, actions, f=inf, g=inf, h=inf):
        self.location = location
        self.history = history
        self.actions = actions
        self.f = f
        self.g = g
        self.h = h

    def __lt__(self, other):
        if self.f != other.f:
            return self.f < other.f
        else:
            return self.actions < other.actions


    def print_history(self):
        for loc in self.history:
            print(loc[0], loc[1])


def is_valid_move(next_node, grid, visited, rows, cols):
    return 0 <= next_node[0] < rows and 0 <= next_node[1] < cols and next_node not in visited and grid[next_node[0]][next_node[1]] != 0


def calculate_heuristic(current, foods):

    min_distance = inf

    for destination in foods:
        distance = abs(current[0] - destination[0]) + abs(current[1] - destination[1])
        if distance < min_distance:
            min_distance = distance

    return min_distance


def a_star_food(source, grid, rows, cols, foods):

    nodes = []

    init_history = [source]

    heappush(nodes, Node(source, init_history[:], [], 0, 0, 0))

    visited = set()

    movements = [
        (-1, 0, 1),
        (0, 1, 2),
        (1, 0, 3),
        (0, -1, 4)
    ]

    while len(nodes) > 0:
        current = heappop(nodes)

        x = current.location[0]
        y = current.location[1]

        history = current.history[:]

        visited.add((x, y))

        if grid[x][y] == 2:
            current.print_history()
            return

        actions = current.actions[:]

        for movement in movements:
            next_node = (x + movement[0], y + movement[1])
            new_actions = actions[:]
            new_history = history[:]

            if is_valid_move(next_node, grid, visited, rows, cols):

                new_actions.append(movement[2])
                new_history.append(next_node)

                new_node = Node(next_node, new_history[:], new_actions[:])

                new_node.g = current.g + 1
                new_node.h = calculate_heuristic(next_node, foods)

                new_node.f = new_node.g + new_node.h

                heappush(nodes, new_node)

    print("NIL")


def main():
    test_cases = int(input())
    for _ in range(test_cases):

        rows, cols = map(int, input().split())
        grid = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        source_x, source_y = map(int, input().split())
        source = (source_x, source_y)

        foods = []

        for row in range(rows):
            for col in range(cols):
                if grid[row][col] == 2:
                    foods.append((row, col))

        a_star_food(source, grid, rows, cols, foods)


main()
