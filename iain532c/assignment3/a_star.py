class Node:
    def __init__(self, arrangement, depth, swaps):
        self.arrangement = arrangement
        self.depth = depth
        self.swaps = swaps

    def __lt__(self, other):
        if self.depth != other.depth:
            return self.depth < other.depth
        else:
            return self.swaps < other.swaps


def where(arrangement, student):
    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            if student == arrangement[i][j]:
                return dict({'e': 0, 'x': i, 'y': j, 'student': student})
    return dict({'e': 1})


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
    swaps = parent_node.swaps
    depth = parent_node.depth
    arrangement = one_to_two(parent_node.arrangement, rows, cols)

    for i in range(rows):
        for j in range(cols - 1):
            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

            new_node = Node(two_to_one(arrangement), depth+1, swaps.copy())

            if arrangement[i][j] < arrangement[i][j + 1]:
                new_node.swaps.append((arrangement[i][j], arrangement[i][j + 1]))
            else:
                new_node.swaps.append((arrangement[i][j + 1], arrangement[i][j]))

            children.append(new_node)

            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

    for i in range(rows - 1):
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

        new_node = Node(two_to_one(arrangement), depth+1, swaps.copy())
        if arrangement[i][0] < arrangement[i + 1][0]:
            new_node.swaps.append((arrangement[i][0], arrangement[i + 1][0]))
        else:
            new_node.swaps.append((arrangement[i + 1][0], arrangement[i][0]))
        children.append(new_node)
        arrangement[i][0], arrangement[i + 1][0] = arrangement[i + 1][0], arrangement[i][0]

    for i in range(rows - 1):
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]

        new_node = Node(two_to_one(arrangement), depth+1, swaps.copy())
        if arrangement[i][cols - 1] < arrangement[i + 1][cols - 1]:
            new_node.swaps.append((arrangement[i][cols - 1], arrangement[i + 1][cols - 1]))
        else:
            new_node.swaps.append((arrangement[i + 1][cols - 1], arrangement[i][cols - 1]))
        children.append(new_node)
        arrangement[i][cols - 1], arrangement[i + 1][cols - 1] = arrangement[i + 1][cols - 1], arrangement[i][cols - 1]

    del arrangement

    return children


def calculate_h_value(rows, columns, current_arrangement, final_arrangement):

    h_value = 0

    current = one_to_two(current_arrangement, rows, columns)
    final = one_to_two(final_arrangement, rows, columns)

    for child in current_arrangement:
        initial_position = where(current, child)
        final_position = where(final, child)

        if initial_position['e'] != 1 and final_position['e'] != 1:
            print(initial_position, final_position)

    return h_value




def a_star(rows, columns, original, final):
    calculate_h_value(rows, columns, original, final)


def main():
    test_cases = int(input())
    for _ in range(test_cases):
        rows, columns = map(int, input().split())
        original = input().split()
        final = input().split()

        a_star(rows, columns, original, final)

main()