from heapq import heappop, heappush, heapify
from queue import Queue


class Node:
    def __init__(self, arrangement, swaps):
        self.arrangement = arrangement
        self.swaps = swaps

    def __lt__(self, other):
        return self.swaps < other.swaps


def where(arrangement, student):
    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            if student == arrangement[i][j]:
                return dict({'e': 0, 'x': i, 'y': j, 'student': student})
    return dict({'e': 1})


def is_final_node(current_node, final_node):
    return current_node.arrangement == final_node.arrangement


def print_answer(swaps):
    for swap in swaps:
        print("swap", swap[0], swap[1])


def are_constraints_resolved(arrangement, constraints, rows, cols):
    for i in range(rows):
        for j in range(cols - 1):
            if arrangement[i][j] < arrangement[i][j + 1]:
                query = (arrangement[i][j], arrangement[i][j + 1])
            else:
                query = (arrangement[i][j + 1], arrangement[i][j])

            if query in constraints:
                return False

    return True


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


def generate_children(parent_node, rows, cols):
    children = []
    swaps = parent_node.swaps
    arrangement = one_to_two(parent_node.arrangement, rows, cols)

    for i in range(rows):
        for j in range(cols - 1):
            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

            new_node = Node(two_to_one(arrangement, rows, cols), swaps.copy())

            if arrangement[i][j] < arrangement[i][j + 1]:
                new_node.swaps.append((arrangement[i][j], arrangement[i][j + 1]))
            else:
                new_node.swaps.append((arrangement[i][j + 1], arrangement[i][j]))

            children.append(new_node)

            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

    for i in range(rows - 1):
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

        new_node = Node(two_to_one(arrangement, rows, cols), swaps.copy())
        if arrangement[i][0] < arrangement[i + 1][0]:
            new_node.swaps.append((arrangement[i][0], arrangement[i + 1][0]))
        else:
            new_node.swaps.append((arrangement[i + 1][0], arrangement[i][0]))
        children.append(new_node)
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

    for i in range(rows - 1):
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]

        new_node = Node(two_to_one(arrangement, rows, cols), swaps.copy())
        if arrangement[i][cols - 1] < arrangement[i + 1][cols - 1]:
            new_node.swaps.append((arrangement[i][cols - 1], arrangement[i + 1][cols - 1]))
        else:
            new_node.swaps.append((arrangement[i + 1][cols - 1], arrangement[i][cols - 1]))
        children.append(new_node)
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]

    del arrangement

    return children


def iddfs(root_node, constraints, rows, columns, depth):

    if depth == 0:
        if are_constraints_resolved(one_to_two(root_node.arrangement, rows, columns), constraints, rows, columns):
            print_answer(root_node.swaps)
            return True
        return False


    children = generate_children(root_node, rows, columns)

    children.sort()

    for child in children:
        if iddfs(child, constraints, rows, columns, depth - 1):
            return True

    del children

    return False


def iddfs_helper(original, constraints, rows, columns, max_depth):
    for i in range(max_depth):
        if iddfs(original, constraints, rows, columns, i):
            return


def main():
    test_cases = int(input())
    for _ in range(test_cases):
        rows, columns = map(int, input().split())
        original_arrangement = input().split()
        constraints_no = int(input())
        constraints = set()
        max_depth = 10
        for _ in range(constraints_no):
            first, second = input().split()
            if first < second:
                constraints.add((first, second))
            else:
                constraints.add((second, first))
        original = Node(original_arrangement, [])
        iddfs_helper(original, constraints, rows, columns, max_depth)


main()
