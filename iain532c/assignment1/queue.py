from queue import PriorityQueue


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


def output_state(state):

    for student in state:
        print(student, end=" ")
    print()


def print_table(arrangement):
    for i in range(len(arrangement)):
        for j in range(len(arrangement[i])):
            print(arrangement[i][j], end=" ")
    print()


def hamming_distance(new_state, original):

    distance = 0

    for i in range(len(new_state)):
        if new_state[i] != original[i]:
            distance = distance + 1

    return distance


def is_move_valid(player1, player2, rows, columns):

    # False if there is any error
    if player1['e'] == 1 or player2['e'] == 1:
        return False

    # Same row but columns not consecutive
    if player1['x'] == player2['x'] and abs(player1['y'] - player2['y']) == 1:
        return True

    # print(player1, player2)

    # Same columns but rows not consecutive or columns not first or last
    if player1['y'] == player2['y'] and (player1['y'] == 0 or player1['y'] == columns-1) and (abs(player1['x'] - player2['x']) == 1):
        return True

    return False


def simulate():
    test_cases = int(input())

    for i in range(test_cases):
        # Input
        rows, columns, queries = map(int, input().split())
        students = input().split()
        pq = PriorityQueue()
        states = []
        # Making a dictionary
        arrangement = [[students[row*columns + col] for col in range(columns)] for row in range(rows)]

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

                new_state = get_row_major(arrangement)
                distance = hamming_distance(students, new_state)

                flag = False

                for temp in states:
                    if temp == new_state:
                        flag = True
                        break

                if not flag:
                    pq.put((distance, new_state))

        while not pq.empty():
            val = pq.get()
            # val = heapq.heappop(states)
            # output_state(val[1])
            print(val)


simulate()
