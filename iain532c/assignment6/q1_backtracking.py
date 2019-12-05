class Faculty:
    def __init__(self, name):
        self.allotted_classes = [[False for _ in range(7)] for _ in range(5)]
        self.name = name

    def allot_slot(self, day, time):
        self.allotted_classes[day][time] = True

    def empty_slot(self, day, time):
        self.allotted_classes[day][time] = False

    def is_available(self, day, time):
        if not self.allotted_classes[day][time]:
            return True
        else:
            return False


class Course:
    def __init__(self, name, faculty, hours):
        self.name = name
        self.faculty = faculty
        self.hours = hours

    def can_be_allotted(self):
        return self.hours > 0

    def allot_course(self, day, time):
        self.faculty.allot_slot(day, time)
        self.hours = self.hours - 1

    def empty_course(self, day, time):
        self.hours = self.hours + 1
        self.faculty.empty_slot(day, time)

    def __lt__(self, other):
        return self.name < other.name


def calculate_next_day(day, time):
    if time == 6:
        return day + 1, 0
    else:
        return day, time + 1


class Batch:

    def __init__(self, name, courses=[]):
        self.name = name
        self.courses = courses
        self.courses.sort()
        self.number_of_courses = len(self.courses)
        self.schedule = [[None for _ in range(7)] for _ in range(5)]
        self.is_allotment_possible = False

    def __lt__(self, other):
        return self.name < other.name

    def get_courses(self, day, time):
        available_course_list = list()

        for course in self.courses:
            if course.can_be_allotted() and course.faculty.is_available(day, time):
                available_course_list.append(course)

        return available_course_list


    def allot_slot(self, day, time, course):
        self.schedule[day][time] = course
        course.allot_course(day, time)

    def empty_slot(self, day, time, course):
        self.schedule[day][time] = None
        course.empty_course(day, time)

    def is_table_filled(self):
        for day in range(5):
            for time in range(7):
                if self.schedule[day][time] is None:
                    return False
        return True

    def are_all_courses_allotted(self):

        allotted_flag = True

        for course in self.courses:
            if course.can_be_allotted():
                allotted_flag = False
                break

        return allotted_flag

    def create_schedule_recursive(self, day, time):

        if self.are_all_courses_allotted():
            return True

        if (day, time) == (5, 0):
            return False

        available_courses = self.get_courses(day, time)

        for available_course in available_courses:

            next_day, next_time = calculate_next_day(day, time)

            self.allot_slot(day, time, available_course)

            if self.create_schedule_recursive(next_day, next_time):
                return True

            self.empty_slot(day, time, available_course)

        return False

    def create_schedule(self):
        if self.create_schedule_recursive(0, 0):
            self.is_allotment_possible = True

    def print_schedule(self):
        print(self.name)
        if not self.is_allotment_possible:
            print('NIL')
        else:
            for day in range(5):
                for time in range(7):
                    if self.schedule[day][time] is None:
                        print('NIL', end=' ')
                    else:
                        print(self.schedule[day][time].name, end=' ')
                print()


def main():

    test_cases = int(input())

    for _ in range(test_cases):

        faculty_list = dict({})

        number_of_batches = int(input())
        batch_list = list()

        for _ in range(number_of_batches):

            batch_name = input()
            courses = list()
            number_of_courses = int(input())

            for _ in range(number_of_courses):

                course_name, faculty_name, hours = input().split()

                if faculty_name in faculty_list:
                    faculty = faculty_list[faculty_name]
                    courses.append(Course(course_name, faculty, int(hours)))
                else:
                    faculty_list[faculty_name] = Faculty(faculty_name)
                    faculty = faculty_list[faculty_name]
                    courses.append(Course(course_name, faculty, int(hours)))

            batch_list.append(Batch(batch_name, courses))

        for batch in batch_list:
            batch.create_schedule()

        for batch in batch_list:
            batch.print_schedule()


main()
















