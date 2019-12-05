from pprint import pprint
from math import inf
from heapq import heappush, heappop


class Faculty:
    def __init__(self, name):
        self.allotted_classes = [[None for _ in range(7)] for _ in range(5)]
        self.name = name

    def allot_slot(self, day, time, course):
        self.allotted_classes[day][time] = course

    def empty_slot(self, day, time):
        self.allotted_classes[day][time] = None

    def is_available(self, day, time):
        if self.allotted_classes[day][time] is None:
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

    def allot_course(self, day, time, course):
        self.faculty.allot_slot(day, time, course)
        self.hours = self.hours - 1

    def empty_course(self, day, time):
        self.hours = self.hours + 1
        self.faculty.empty_slot(day, time)

    def __lt__(self, other):
        return self.name < other.name


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
        course.allot_course(day, time, course)

    def empty_slot(self, day, time, course):
        self.schedule[day][time] = None
        course.empty_course(day, time)

    def are_all_courses_allotted(self):

        allotted_flag = True

        for course in self.courses:
            if course.can_be_allotted():
                allotted_flag = False
                break

        return allotted_flag

    def print_schedule(self):

        print(self.name)
        for day in range(5):
            for week in range(7):
                if self.schedule[day][week] is None:
                    print('NIL', end=' ')
                else:
                    print(self.schedule[day][week].name, end=' ')
            print()


class Slot:
    def __init__(self, batch, batch_index, day, time, rv=inf, degree=-inf, is_allotted=False):
        self.batch = batch
        self.day = day
        self.time = time
        self.remaining_value = rv
        self.batch_index = batch_index
        self.degree = degree
        self.is_allotted = is_allotted

    def allot_slot(self, course):
        self.is_allotted = True
        self.batch.allot_slot(self.day, self.time, course)

    def empty_slot(self, course):
        self.is_allotted = False
        self.batch.empty_slot(self.day, self.time, course)

    def clone(self):
        return Slot(self.batch, self.batch_index, self.day, self.time, self.remaining_value, self.degree, self.is_allotted)

    def __lt__(self, other):
        if self.remaining_value != other.remaining_value:
            return self.remaining_value < other.remaining_value
        else:
            if self.degree != other.degree:
                return self.degree > other.degree
            else:
                if self.batch.name != other.batch.name:
                    return self.batch.name < other.batch.name
                else:
                    if self.day != other.day:
                        return self.day < other.day
                    else:
                        if self.time != self.time:
                            return self.time < other.time


def intersection(list1, list2):
    return list(set(list1) & set(list2))


class Timetable:
    def __init__(self, batches=None):
        if batches is None:
            batches = []
        self.batches = batches
        self.timetable_possible = False
        batches.sort()
        self.number_of_batches = len(self.batches)
        self.slots = [[[Slot(self.batches[batch_index], batch_index,  day, time) for time in range(7)] for day in range(5)] for batch_index in range(self.number_of_batches)]

    def allotted_all_batches(self):
        for batch in self.batches:
            res = batch.are_all_courses_allotted()
            if not res:
                return False
        return True


    def get_selected_slots(self):

        for batch_index in range(self.number_of_batches):

            for day in range(5):
                for time in range(7):

                    slot = self.slots[batch_index][day][time]

                    if not slot.is_allotted:
                        slot.remaining_value = len(slot.batch.get_courses(slot.day, slot.time))
                        slot.degree = 0

        # for batch in range(self.number_of_batches):
        #
        #     for a_day in range(5):
        #         for a_time in range(7):
        #
        #             a_slot = self.slots[batch][a_day][a_time]
        #
        #             if a_slot.is_allotted:
        #                 continue
        #
        #             for b_day in range(5):
        #                 for b_time in range(7):
        #
        #                     if (a_day, a_time) == (b_day, b_time):
        #                         continue
        #
        #                     b_slot = self.slots[batch][b_day][b_time]
        #
        #                     if b_slot.is_allotted:
        #                         continue
        #
        #                     a_courses = a_slot.batch.get_courses(a_day, a_time)
        #                     b_courses = b_slot.batch.get_courses(b_day, b_time)
        #
        #                     ab_intersection = intersection(a_courses, b_courses)
        #
        #                     if len(ab_intersection) > 0:
        #                         a_slot.degree += 1
        #
        # for day in range(5):
        #     for time in range(7):
        #
        #         for a_batch_index in range(self.number_of_batches):
        #
        #             a_slot = self.slots[a_batch_index][day][time]
        #
        #             if a_slot.is_allotted:
        #                 continue
        #
        #             for b_batch_index in range(self.number_of_batches):
        #
        #                 if a_batch_index == b_batch_index:
        #                     continue
        #
        #                 b_slot = self.slots[b_batch_index][day][time]
        #
        #                 if b_slot.is_allotted:
        #                     continue
        #
        #                 a_courses = a_slot.batch.get_courses(day, time)
        #                 b_courses = b_slot.batch.get_courses(day, time)
        #
        #                 a_faculties = set()
        #                 b_faculties = set()
        #
        #                 for course in a_courses:
        #                     a_faculties.add(course.faculty.name)
        #
        #                 for course in b_courses:
        #                     b_faculties.add(course.faculty.name)
        #
        #                 ab_intersection = a_faculties.intersection(b_faculties)
        #
        #                 if len(ab_intersection) > 0:
        #                     a_slot.degree += 1


        slot_list = [self.slots[batch_index][day][time].clone() for time in range(7) for day in range(5) for batch_index in range(len(self.batches))]

        new_list = list()

        for slots in slot_list:
            if not slots.is_allotted:
                new_list.append(slots)

        new_list.sort()

        return new_list

    def sort_courses(self, slot):

        available_courses = slot.batch.get_courses(slot.day, slot.time)


        return available_courses

    def create_timetable_recursive(self):

        if self.allotted_all_batches():
            return True

        available_slots = self.get_selected_slots()

        breakFlag = False

        for slot in available_slots:

            available_courses = self.sort_courses(slot)

            for course in available_courses:

                breakFlag = True

                self.slots[slot.batch_index][slot.day][slot.time].allot_slot(course)

                if self.create_timetable_recursive():
                    return True

                self.slots[slot.batch_index][slot.day][slot.time].empty_slot(course)

        return not breakFlag

    def create_timetable(self):
        if self.create_timetable_recursive():
            self.timetable_possible = True

    def print_timetable(self):
        for batch in self.batches:
            batch.print_schedule()


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

        timetable = Timetable(batch_list)
        timetable.create_timetable()
        timetable.print_timetable()

main()

















