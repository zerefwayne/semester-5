from heapq import heappop, heappush, heapify
from queue import Queue


class Node:
    def __init__(self, depth, arrangement, swaps):
        self.depth = depth
        self.arrangement = arrangement
        self.swaps = swaps

    def __lt__(self, other):
        if self.depth != other.depth:
            return self.depth < other.depth
        else:
            return self.swaps < other.swaps


def is_final_node(current_node, final_node):
    return current_node.arrangement == final_node.arrangement


def print_answer(swaps):
    for swap in swaps:
        print("swap", swap[0], swap[1])


def one_to_two(students, rows, columns):
    arrangement = [[students[row * columns + col] for col in range(columns)] for row in range(rows)]
    return arrangement


def two_to_one(arrangement, rows, columns):
    row_major = []

    for i in range(len(arrangement)):
        for j in range(len(arrangement[0])):
            row_major.append(arrangement[i][j])

    return row_major


def one_to_string(students):
    line = ""
    for student in students:
        line += student
        line += " "
    return line


def search(original_arrangement, final_arrangement, rows, cols):
    final_node = Node(0, final_arrangement[:], [])
    visited = {}
    nodes = []
    flag = 0
    heappush(nodes, Node(0, original_arrangement[:], []))

    while len(nodes) > 0:
        current_node = heappop(nodes)
        depth = current_node.depth
        swaps = current_node.swaps.copy()
        arrangement = one_to_two(current_node.arrangement, rows, cols)

        for i in range(rows):
            for j in range(cols - 1):
                arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

                if not one_to_string(two_to_one(arrangement, rows, cols)) in visited:
                    visited[one_to_string(two_to_one(arrangement, rows, cols))] = 1
                    new_node = Node(depth+1, two_to_one(arrangement, rows, cols), swaps.copy())

                    if arrangement[i][j] < arrangement[i][j+1]:
                        new_node.swaps.append((arrangement[i][j], arrangement[i][j+1]))
                    else:
                        new_node.swaps.append((arrangement[i][j+1], arrangement[i][j]))

                    if two_to_one(arrangement, rows, cols) == final_arrangement:
                        print_answer(new_node.swaps.copy())
                        return

                    print(new_node.depth, *new_node.arrangement, new_node.swaps)
                    heappush(nodes, new_node)

                arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

        for i in range(rows - 1):
            arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]
            if not one_to_string(two_to_one(arrangement, rows, cols)) in visited:
                visited[one_to_string(two_to_one(arrangement, rows, cols))] = 1
                new_node = Node(depth+1, two_to_one(arrangement, rows, cols), swaps.copy())

                if arrangement[i][0] < arrangement[i+1][0]:
                    new_node.swaps.append((arrangement[i][0], arrangement[i+1][0]))
                else:
                    new_node.swaps.append((arrangement[i+1][0], arrangement[i][0]))

                if two_to_one(arrangement, rows, cols) == final_arrangement:
                    print_answer(new_node.swaps.copy())
                    return

                print(new_node.depth, *new_node.arrangement, new_node.swaps)
                heappush(nodes, new_node)
            arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

        for i in range(rows - 1):
            arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][
                cols - 1]
            if not one_to_string(two_to_one(arrangement, rows, cols)) in visited:
                visited[one_to_string(two_to_one(arrangement, rows, cols))] = 1
                new_node = Node(depth+1, two_to_one(arrangement, rows, cols), swaps.copy())

                if arrangement[i][cols-1] < arrangement[i + 1][cols-1]:
                    new_node.swaps.append((arrangement[i][cols-1], arrangement[i+1][cols-1]))
                else:
                    new_node.swaps.append((arrangement[i+1][cols-1], arrangement[i][cols-1]))

                if two_to_one(arrangement, rows, cols) == final_arrangement:
                    print_answer(new_node.swaps.copy())
                    return

                print(new_node.depth, *new_node.arrangement, new_node.swaps)
                heappush(nodes, new_node)
            arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][
                cols - 1]


def main():
    test_cases = int(input())
    for _ in range(test_cases):
        rows, columns = map(int, input().split())
        original_arrangement = input().split()
        final_arrangement = input().split()
        search(original_arrangement[:], final_arrangement[:], rows, columns)


main()
