def where(arrangement, student):
    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            if student == arrangement[i][j]:
                return dict({'e': 0, 'x': i, 'y': j, 'student': student})
    return dict({'e': 1})


def get_row_major(arrangement):
    row_major = []

    for i in range(len(arrangement)):
        for j in range(len(arrangement[0])):
            row_major.append(arrangement[i][j])

    return row_major


def print_table(arrangement, cycle):
    if cycle:
        print("cycle", end=" ")

    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            print(arrangement[i][j], end=" ")
    print()


def is_move_valid(player1, player2, rows, columns):
    # False if there is any error
    if player1['e'] == 1 or player2['e'] == 1:
        return False

    # Same row but columns not consecutive
    if player1['x'] == player2['x'] and abs(player1['y'] - player2['y']) == 1:
        return True

    # print(player1, player2)

    # Same columns but rows not consecutive or columns not first or last
    if player1['y'] == player2['y'] and (player1['y'] == 0 or player1['y'] == columns - 1) and (
            abs(player1['x'] - player2['x']) == 1):
        return True

    return False


def simulate():
    test_cases = int(input())

    for i in range(test_cases):
        # Input
        rows, columns, queries = map(int, input().split())
        students = input().split()
        states = []
        # Making a dictionary
        arrangement = [[students[row * columns + col] for col in range(columns)] for row in range(rows)]

        states.append(get_row_major(arrangement))

        for _ in range(queries):
            keyword, student1, student2 = input().split()
            # print(arrangement)

            student1 = where(arrangement, student1)
            # print("Student 1", student1)
            student2 = where(arrangement, student2)
            # print("Student 2", student2)
            if is_move_valid(student1, student2, rows, columns):
                arrangement[student1['x']][student1['y']] = student2['student']
                arrangement[student2['x']][student2['y']] = student1['student']

                state = get_row_major(arrangement)

                cycle = False

                for state_temp in states:
                    if state == state_temp:
                        cycle = True

                print_table(arrangement, cycle)

                if not cycle:
                    states.append(state)

            else:
                print("illegal")


simulate()
