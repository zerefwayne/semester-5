from queue import Queue


class Node:
    def __init__(self, arrangement, cost):
        self.arrangement = arrangement
        self.cost = cost

    def __lt__(self, other):
        if self.cost != other.cost:
            return self.cost < other.cost
        else:
            return self.arrangement < other.arrangment


def one_to_two(students, rows, columns):
    arrangement = [[students[row * columns + col] for col in range(columns)] for row in range(rows)]
    return arrangement


def two_to_one(arrangement):
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
    cost = parent_node.cost
    arrangement = one_to_two(parent_node.arrangement, rows, cols)

    for i in range(rows):
        for j in range(cols - 1):
            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]
            new_node = Node(two_to_one(arrangement), cost+1)
            children.append(new_node)
            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

    for i in range(rows - 1):
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]
        new_node = Node(two_to_one(arrangement), cost + 1)
        children.append(new_node)
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

    for i in range(rows - 1):
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]
        new_node = Node(two_to_one(arrangement), cost + 1)
        children.append(new_node)
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]

    return children


def bidirectional_search(rows, columns, start_node, finish_node):

    forward_map = {}
    backward_map = {}

    forward_queue = Queue()
    backward_queue = Queue()

    forward_queue.put(start_node)
    backward_queue.put(finish_node)

    while True:

        forward_child = forward_queue.get()
        backward_child = backward_queue.get()

        forward_children = generate_children(forward_child, rows, columns)
        backward_children = generate_children(backward_child, rows, columns)

        for child in forward_children:
            forward_queue.put(child)
            # print(child.arrangement, child.cost)
            forward_map[one_to_string(child.arrangement)] = child.cost
            if one_to_string(child.arrangement) in backward_map:
                return child.cost + backward_map[one_to_string(child.arrangement)]

        for child in backward_children:
            backward_queue.put(child)
            # print(child.arrangement, child.cost)
            backward_map[one_to_string(child.arrangement)] = child.cost
            if one_to_string(child.arrangement) in forward_map:
                return child.cost + forward_map[one_to_string(child.arrangement)]


def main():
    test_cases = int(input())
    for test_case in range(test_cases):
        rows, columns = map(int, input().split())
        start = input().split()
        finish = input().split()

        start_node = Node(start, 0)
        finish_node = Node(finish, 0)

        print(bidirectional_search(rows, columns, start_node, finish_node))


main()
