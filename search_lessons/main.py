from models import Node
from uninformed import breadth_first_search, bidirectional_search
import sys
if __name__ == "__main__":
    initial_state = [
        [4, 8, 1],
        [0, 3, 6],
        [2, 7, 5]
   
    ]
    goal_state = [
        [1, 2, 3],
        [8, 0, 4],
        [7, 6, 5]
    ]

    Node.number_of_nodes = 0
    initial_node = Node(initial_state, None, None, 0, 0)
    goal_node = Node(goal_state, None, None, 0, 0)

    by_step = len(sys.argv) > 1 and sys.argv[1] == "--by-step"
    print("Поиск в ширину:")

    result, iterations = breadth_first_search(initial_node, goal_node, by_step)
    if result is None:
        print("Решение не найдено")
    else:
        print("Глубина:", result.depth)
        print("Путь:", result.get_path())
        print("Итераций:", iterations)
        print("Узлов создано:", Node.number_of_nodes)

    Node.number_of_nodes = 0
    initial_node = Node(initial_state, None, None, 0, 0)
    goal_node = Node(goal_state, None, None, 0, 0)

    print("Двунаправленный поиск:")

    result, iterations = bidirectional_search(initial_node, goal_node, by_step)
    if result is None:
        print("Решение не найдено")
    else:
        print("Глубина:", result.depth)
        print("Путь:", result.get_path())
        print("Итераций:", iterations)
        print("Узлов создано:", Node.number_of_nodes)