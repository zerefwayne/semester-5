def get_row_major(arrangement):

    row_major = []

    for i in range(len(arrangement)):
        for j in range(len(arrangement[0])):
            row_major.append(arrangement[i][j])

    return row_major


def output_state(state):

    for student in state:
        print(student, end=" ")
    print()


def create_arrangement(students, rows, columns):
    new_arrangement = [[students[row * columns + col] for col in range(columns)] for row in range(rows)]
    return new_arrangement


def print_next_steps(prev_states, arrangement):

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

    flag = False

    for state in states:

        flag = False

        for prev in prev_states:
            if prev == state:
                flag = True
                break

        if not flag:
            arrangement = create_arrangement(state, rows, cols)
            prev_states.append(state)
            output_state(state)
            break

    return dict({"states": prev_states, "arrangement": arrangement})


def simulate():
    test_cases = int(input())

    for i in range(test_cases):
        # Input
        rows, columns, depth = map(int, input().split())
        visited = []

        students = input().split()
        arrangement = create_arrangement(students, rows, columns)

        visited.append(get_row_major(arrangement))

        for _ in range(depth):
            response = print_next_steps(visited, arrangement)
            arrangement = response['arrangement']
            visited = response['states']


simulate()
