from math import inf
from heapq import heappush, heappop


class Node:
    def __init__(self, location, history, actions, f=inf, g=inf, h=inf, t=0):
        self.location = location
        self.history = history
        self.actions = actions
        self.f = f
        self.g = g
        self.h = h
        self.t = t

    def __lt__(self, other):
        if self.f != other.f:
            return self.f < other.f
        else:
            return self.actions < other.actions


    def print_history(self):
        print(self.g)
        for loc in self.history:
            print(loc[0], loc[1])


def in_bounds(next_node, grid, rows, cols):
    return 0 <= next_node[0] < rows and 0 <= next_node[1] < cols and grid[next_node[0]][next_node[1]] != 0


def is_valid(next_node, visited):

    query = str(next_node.location[0]) + "," + str(next_node.location[1]) + "," + str(next_node.t)

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


def calculate_heuristic(current, foods):

    min_distance = inf

    for destination in foods:
        distance = abs(current[0] - destination[0]) + abs(current[1] - destination[1])
        if distance < min_distance:
            min_distance = distance

    return min_distance


def a_star_food(source, grid, diff_height, rows, cols, foods):

    nodes = []

    init_history = [source]

    heappush(nodes, Node(source, init_history[:], [], 0, 0, 0, 0))

    visited = dict({})

    movements = [
        (0, 0, 0),
        (-1, 0, 1),
        (0, 1, 2),
        (1, 0, 3),
        (0, -1, 4)
    ]

    while len(nodes) > 0:
        current = heappop(nodes)

        x = current.location[0]
        y = current.location[1]

        t = current.t

        if grid[x][y] < 0:
            current.print_history()
            return

        actions = current.actions[:]
        history = current.history[:]

        current_height = abs(grid[x][y]) + (t % diff_height[x][y])

        visited[str(x)+","+str(y)+","+str(t)] = current

        for movement in movements:

            next_node = (x + movement[0], y + movement[1])

            new_actions = actions[:]
            new_history = history[:]

            if not in_bounds(next_node, grid, rows, cols):
                continue

            next_node_height = abs(grid[next_node[0]][next_node[1]]) + ((t+1) % diff_height[next_node[0]][next_node[1]])

            cost_to_move = 1

            if current_height > next_node_height:
                cost_to_move = current_height - next_node_height + 1
            elif current_height < next_node_height:
                cost_to_move = 2 * (next_node_height - current_height) + 1

            new_actions.append(movement[2])
            new_history.append(next_node)

            new_node = Node(next_node, new_history[:], new_actions[:])

            new_node.t = current.t + 1

            if movement == (0, 0, 0):
                new_node.g = current.g + 1
            else:
                new_node.g = current.g + cost_to_move
            new_node.h = calculate_heuristic(next_node, foods)
            new_node.f = new_node.g + new_node.h

            if is_valid(new_node, visited):
                heappush(nodes, new_node)

    print("NIL")


def main():
    test_cases = int(input())
    for _ in range(test_cases):

        rows, cols = map(int, input().split())
        grid = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        diff_height = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        source_x, source_y = map(int, input().split())
        source = (source_x, source_y)

        foods = []

        for row in range(rows):
            for col in range(cols):
                if grid[row][col] < 0:
                    foods.append((row, col))

        a_star_food(source, grid, diff_height, rows, cols, foods)


main()
