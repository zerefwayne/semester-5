def get_row_major(arrangement):

    row_major = []

    for i in range(len(arrangement)):
        for j in range(len(arrangement[0])):
            row_major.append(arrangement[i][j])

    return row_major


def output(states):
    for state in states:
        for student in state:
            print(student, end=" ")
        print()


def print_table(arrangement, cycle):
    if cycle:
        print("cycle", end=" ")

    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            print(arrangement[i][j], end=" ")
    print()


def print_next_steps(arrangement):

    states = []

    rows = len(arrangement)
    cols = len(arrangement[0])

    for i in range(rows):
        for j in range(cols - 1):
            arrangement[i][j], arrangement[i][j+1] = arrangement[i][j+1], arrangement[i][j]
            states.append(get_row_major(arrangement))
            arrangement[i][j], arrangement[i][j + 1] = arrangement[i][j + 1], arrangement[i][j]

    for i in range(rows - 1):
        arrangement[i][0], arrangement[i+1][0] = arrangement[i+1][0], arrangement[i][0]
        states.append(get_row_major(arrangement))
        arrangement[i][0], arrangement[i+1][0] = arrangement[i+1][0], arrangement[i][0]

    for i in range(rows - 1):
        arrangement[i][cols-1], arrangement[i+1][cols-1] = arrangement[i+1][cols-1], arrangement[i][cols-1]
        states.append(get_row_major(arrangement))
        arrangement[i][cols-1], arrangement[i+1][cols-1] = arrangement[i+1][cols-1], arrangement[i][cols-1]

    states.sort()

    output(states)


def simulate():
    test_cases = int(input())

    for i in range(test_cases):
        # Input
        rows, columns = map(int, input().split())
        students = input().split()
        arrangement = [[students[row*columns + col] for col in range(columns)] for row in range(rows)]



simulate()
