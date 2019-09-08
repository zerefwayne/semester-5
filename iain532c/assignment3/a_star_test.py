from pprint import pprint
from heapq import heappush, heappop, heapify
from math import inf


class Cell:
    def __init__(self):
        self.f = inf
        self.g = inf
        self.h = inf
        self.parent = (-1, -1)

    def __lt__(self, other):
        return self.f < other.f


def is_move_valid(rows, columns, x, y):
    return 0 <= x < rows and 0 <= y < columns


def print_path(rows, columns, cell_details, destination):

    board = [["-" for _ in range(columns)] for _ in range(rows)]

    current = (destination[0], destination[1])

    while current != (-1, -1):
        board[current[0]][current[1]] = "O"
        current = (cell_details[current[0]][current[1]].parent[0], cell_details[current[0]][current[1]].parent[1])

    pprint(board)


def is_destination(x, y, destination):
    return x == destination[0] and y == destination[1]


def calculate_h_value(i_new, j_new, destination):
    return ((i_new - destination[0]) ** 2 + (j_new - destination[1]) ** 2)**0.5


def is_unblocked(board, x, y):
    return board[x][y] == '1'


def a_star(rows, columns, board, visited, cell_details, source, destination):

    i, j = source[0], source[1]
    cell_details[i][j].f, cell_details[i][j].g, cell_details[i][j].h = 0.0, 0.0, 0.0
    movements = [(-1, 0, 1), (1, 0, 1), (0, 1, 1), (0, -1, 1), (-1, 1, 1.414), (-1, -1, 1.414), (1, 1, 1.414), (1, -1, 1.414)]
    open_list = []
    heappush(open_list, (0.0, (i, j)))

    while len(open_list) > 0:
        current = heappop(open_list)
        i, j = current[1][0], current[1][1]
        visited[i][j] = True

        for movement in movements:
            i_new = i + movement[0]
            j_new = j + movement[1]

            if is_move_valid(rows, columns, i_new, j_new):

                if is_destination(i_new, j_new, destination):
                    cell_details[i_new][j_new].parent = (i, j)
                    print_path(rows, columns, cell_details, destination)
                    return
                else:
                    if not visited[i_new][j_new] and is_unblocked(board, i_new, j_new):
                        g_new = cell_details[i][j].g + movement[2]
                        h_new = calculate_h_value(i_new, j_new, destination)
                        f_new = g_new + h_new

                        if cell_details[i_new][j_new].f == inf or cell_details[i_new][j_new].f > f_new:
                            heappush(open_list, (f_new, (i_new, j_new)))
                            cell_details[i_new][j_new].g, cell_details[i_new][j_new].f, cell_details[i_new][j_new].h, cell_details[i_new][j_new].parent = g_new, f_new, h_new, (i, j)


def main():

    rows, columns = map(int, input().split())
    board = []
    visited = [[False for _ in range(columns)] for _ in range(rows)]

    for i in range(rows):
        row = input().split()
        board.append(row)

    source_x, source_y = map(int, input().split())
    destination_x, destination_y = map(int, input().split())

    source = (source_x, source_y)
    destination = (destination_x, destination_y)

    cell_details = [[Cell() for c in range(columns)] for r in range(rows)]

    a_star(rows, columns, board, visited, cell_details, source, destination)


main()
