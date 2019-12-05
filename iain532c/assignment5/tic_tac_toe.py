import time
import math
from copy import deepcopy


def is_maximizing_player(player):
    return player == 'X'


def draw_board(current_state):
    print()
    for i in range(3):
        for j in range(3):
            print('{}|'.format(current_state[i][j]), end=" ")
        print()
    print()


def is_valid(board, px, py):
    if px < 0 or px > 2 or py < 0 or py > 2:
        return False
    elif board[px][py] != '.':
        return False
    else:
        return True


def is_end(current_state):
    for i in range(0, 3):
        if (current_state[0][i] != '.' and
                current_state[0][i] == current_state[1][i] and
                current_state[1][i] == current_state[2][i]):
            return current_state[0][i]

    for i in range(0, 3):
        if current_state[i] == ['X', 'X', 'X']:
            return 'X'
        elif current_state[i] == ['O', 'O', 'O']:
            return 'O'

    if (current_state[0][0] != '.' and
            current_state[0][0] == current_state[1][1] and
            current_state[0][0] == current_state[2][2]):
        return current_state[0][0]

    if (current_state[0][2] != '.' and
            current_state[0][2] == current_state[1][1] and
            current_state[0][2] == current_state[2][0]):
        return current_state[0][2]

    for i in range(0, 3):
        for j in range(0, 3):
            if current_state[i][j] == '.':
                return None

    return '.'


def generate_children(state, turn):
    children = []

    for i in range(3):
        for j in range(3):
            if state[i][j] == '.':
                new_state = deepcopy(state)
                new_state[i][j] = turn
                children.append((new_state, i, j))

    return children


def minimax(board, player_turn):

    result = is_end(board)

    if result == 'X':
        return 1, -1, -1
    elif result == 'O':
        return -1, -1, -1
    elif result == '.':
        return 0, -1, -1

    if is_maximizing_player(player_turn):
        max_val = -math.inf
        next_x = None
        next_y = None

        children = generate_children(deepcopy(board), player_turn)

        for child in children:
            (val, x, y) = minimax(child[0], 'O')

            if val > max_val:
                max_val = val
                next_x = child[1]
                next_y = child[2]

        return max_val, next_x, next_y

    else:
        min_val = math.inf
        next_x = None
        next_y = None

        children = generate_children(deepcopy(board), player_turn)

        for child in children:
            (val, x, y) = minimax(child[0], 'X')
            if val < min_val:
                min_val = val
                next_x = child[1]
                next_y = child[2]

        return min_val, next_x, next_y


class Game:

    def __init__(self):
        self.board = None
        self.turn = None

    def initialize_game(self):
        self.board = [['.' for _ in range(3)] for _ in range(3)]
        self.turn = 'O'

    def play(self):
        self.initialize_game()

        while True:

            result = is_end(self.board)

            if result is not None:

                draw_board(self.board)

                if result == 'X':
                    print('The winner is X!')
                elif result == 'O':
                    print('The winner is O!')
                elif result == '.':
                    print("It's a tie!")

                break

            else:

                if self.turn == 'X':

                    while True:

                        start = time.time()
                        (_, suggested_x, suggested_y) = minimax(self.board, self.turn)
                        end = time.time()
                        print('Evaluation time: {}s'.format(round(end - start, 7)))


                        draw_board(self.board)

                        print("Suggested move:", (suggested_x, suggested_y))

                        px = int(input("Enter the x coordinate: "))
                        py = int(input("Enter the y coordinate: "))

                        if is_valid(self.board, px, py):
                            self.board[px][py] = self.turn
                            self.turn = 'O'
                            break
                        else:
                            print("Please enter a valid move.")

                else:
                    (_, suggested_x, suggested_y) = minimax(self.board, self.turn)
                    self.board[suggested_x][suggested_y] = self.turn
                    self.turn = 'X'


def main():
    g = Game()
    g.play()


main()



