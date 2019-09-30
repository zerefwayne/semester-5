from math import inf
from heapq import heappush, heappop
from copy import deepcopy


class Node:
    def __init__(self, location, grid, history, actions, f=inf, g=inf, h=inf):
        self.location = location
        self.history = history
        self.actions = actions
        self.f = f
        self.grid = grid
        self.g = g
        self.h = h

    def __lt__(self, other):
        if self.f != other.f:
            return self.f < other.f
        else:
            return self.actions < other.actions


    def print_history(self):
        print(self.g)
        for loc in self.history:
            print(loc[0], loc[1])


def is_valid(next_node, visited):

    query = str(next_node.location[0]) + "," + str(next_node.location[1])

    if query not in visited:
        return True
    else:
        if next_node.g != visited[query].g:
            if next_node.g < visited[query].g:
                visited[query] = next_node
                return True
        elif next_node.actions != visited[query].actions:
            if len(next_node.actions) != len(visited[query].actions):
                return len(next_node.actions) < len(visited[query].actions)
            else:
                return next_node.actions < visited[query].actions

    return False


def in_bounds(next_node, rows, cols):
    return 0 <= next_node[0] < rows and 0 <= next_node[1] < cols


def can_push(next_node, movement, grid, rows, cols):
    x = next_node[0] + movement[0]
    y = next_node[1] + movement[1]

    if in_bounds((x, y), rows, cols):
        if grid[x][y] == 1:
            grid[next_node[0]][next_node[1]] = 1
            grid[x][y] = 0
            return True
        else:
            return False
    else:
        return False


def a_star_food(source, grid, rows, cols, foods, cost_to_push):

    nodes = []

    init_history = [source]

    heappush(nodes, Node(source, deepcopy(grid), init_history[:], [], 0, 0, 0))

    visited = dict({})

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
        actions = current.actions[:]

        visited[str(x) + "," + str(y)] = current

        current_grid = deepcopy(current.grid)

        if current_grid[x][y] == 2:
            current.print_history()
            return

        for movement in movements:

            next_node = (x + movement[0], y + movement[1])

            new_actions = actions[:]
            new_history = history[:]

            if not in_bounds(next_node, rows, cols):
                continue

            if current_grid[next_node[0]][next_node[1]] == 0:

                pushed_grid = deepcopy(current_grid)

                if can_push(next_node, movement, pushed_grid, rows, cols):
                    new_actions.append(4 + movement[2])
                    new_history.append(next_node)

                    new_node = Node(next_node, deepcopy(pushed_grid), new_history[:], new_actions[:])

                    new_node.g = current.g + cost_to_push + 1
                    new_node.h = 0
                    new_node.f = new_node.g + new_node.h

                    if is_valid(new_node, visited):
                        heappush(nodes, new_node)


            else:
                new_actions.append(movement[2])
                new_history.append(next_node)

                new_node = Node(next_node, deepcopy(current_grid), new_history[:], new_actions[:])

                new_node.g = current.g + 1
                new_node.h = 0
                new_node.f = new_node.g + new_node.h

                if is_valid(new_node, visited):
                    heappush(nodes, new_node)

    print("NIL")


def main():
    test_cases = int(input())
    for _ in range(test_cases):

        rows, cols = map(int, input().split())
        grid = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        source_x, source_y = map(int, input().split())
        cost_to_push = int(input())
        source = (source_x, source_y)

        foods = []

        for row in range(rows):
            for col in range(cols):
                if grid[row][col] == 2:
                    foods.append((row, col))

        a_star_food(source, grid, rows, cols, foods, cost_to_push)


main()