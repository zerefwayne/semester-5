from math import inf
from heapq import heappush, heappop


class Node:
    def __init__(self, location_1, location_2, history_1, history_2, actions, f=inf, t=0):
        self.location_1 = location_1
        self.location_2 = location_2
        self.history_1 = history_1
        self.history_2 = history_2
        self.actions = actions
        self.f = f
        self.t = t

    def __lt__(self, other):
        if self.f != other.f:
            return self.f < other.f
        else:
            return self.actions < other.actions

    def print_history(self):
        print(self.f)
        for loc in range(len(self.history_1)):
            print(self.history_1[loc][0], self.history_1[loc][1], self.history_2[loc][0], self.history_2[loc][1])


def in_bounds(next_node, rows, cols):
    return 0 <= next_node[0] < rows and 0 <= next_node[1] < cols


def is_valid(next_node, visited):

    query = str(next_node.location_1[0]) + "," + str(next_node.location_1[1]) + "," + str(next_node.location_2[0]) + "," + str(next_node.location_2[1]) + "," + str(next_node.t)

    if query not in visited:
        return True
    else:
        if next_node.f != visited[query].f:
            if next_node.f < visited[query].f:
                visited[query] = next_node
                return True

    return False


def calculate_heuristic(current, foods):

    min_distance = inf

    for destination in foods:
        distance = abs(current[0] - destination[0]) + abs(current[1] - destination[1])
        if distance < min_distance:
            min_distance = distance

    return min_distance


def a_star_food(player1, player2, grid, diff_height, rows, cols):

    nodes = []

    history_1 = [player1]
    history_2 = [player2]

    heappush(nodes, Node(player1, player2, history_1[:], history_2[:], [], 0, 0))

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

        # print(current.location_1, current.location_2, current.f, current.actions, current.t)

        x1 = current.location_1[0]
        y1 = current.location_1[1]
        x2 = current.location_2[0]
        y2 = current.location_2[1]

        t = current.t

        if current.location_1[0] == current.location_2[0] and current.location_1[1] == current.location_2[1]:
            current.print_history()
            return

        actions = current.actions[:]

        history_1 = current.history_1[:]
        history_2 = current.history_2[:]

        current_height_1 = abs(grid[x1][y1]) + (t % diff_height[x1][y1])
        current_height_2 = abs(grid[x2][y2]) + (t % diff_height[x2][y2])

        tummy = str(x1)+","+str(y1)+","+str(x2)+","+str(y2)+","+str(t)

        visited[tummy] = current

        for movement_1 in movements:

            next_node_1 = (x1 + movement_1[0], y1 + movement_1[1])

            if not in_bounds(next_node_1, rows, cols):
                continue

            next_node_height_1 = abs(grid[next_node_1[0]][next_node_1[1]]) + (
                        (t + 1) % diff_height[next_node_1[0]][next_node_1[1]])

            for movement_2 in movements:

                next_node_2 = (x2 + movement_2[0], y2 + movement_2[1])

                new_actions = actions[:]
                new_history_1 = history_1[:]
                new_history_2 = history_2[:]

                if not in_bounds(next_node_2, rows, cols):
                    continue

                next_node_height_2 = abs(grid[next_node_2[0]][next_node_2[1]]) + ((t+1) % diff_height[next_node_2[0]][next_node_2[1]])

                cost_to_move_1 = 1
                cost_to_move_2 = 1

                if current_height_1 > next_node_height_1:
                    cost_to_move_1 = current_height_1 - next_node_height_1 + 1
                elif current_height_1 < next_node_height_1:
                    cost_to_move_1 = 2 * (next_node_height_1 - current_height_1) + 1

                if movement_1 == (0, 0, 0):
                    cost_to_move_1 = 1

                if current_height_2 > next_node_height_2:
                    cost_to_move_2 = current_height_2 - next_node_height_2 + 1
                elif current_height_2 < next_node_height_2:
                    cost_to_move_2 = 2 * (next_node_height_2 - current_height_2) + 1

                if movement_2 == (0, 0, 0):
                    cost_to_move_2 = 1

                cost_to_move = current.f + cost_to_move_1 + cost_to_move_2

                new_actions.append((movement_1[2], movement_2[2]))
                new_history_1.append(next_node_1)
                new_history_2.append(next_node_2)

                new_node = Node(next_node_1, next_node_2, new_history_1[:], new_history_2[:], new_actions[:], cost_to_move)
                new_node.t = t + 1

                if is_valid(new_node, visited):
                    heappush(nodes, new_node)

    print("NIL")


def main():
    test_cases = int(input())
    for _ in range(test_cases):

        rows, cols = map(int, input().split())
        grid = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        diff_height = [[int(cell) for cell in input().strip().split()] for _ in range(rows)]
        p1x, p1y, p2x, p2y = map(int, input().split())
        player1 = (p1x, p1y)
        player2 = (p2x, p2y)

        a_star_food(player1, player2, grid, diff_height, rows, cols)


main()
