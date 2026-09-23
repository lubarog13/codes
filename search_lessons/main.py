from models import Node
from uninformed import breadth_first_search, bidirectional_search

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

    result = breadth_first_search(initial_node, goal_node)
    print("Поиск в ширину:")
    if result is None:
        print("Решение не найдено")
    else:
        print("Глубина:", result.depth)
        print("Путь:", result.get_path())
        print("Узлов создано:", Node.number_of_nodes)

    Node.number_of_nodes = 0

    result = bidirectional_search(initial_node, goal_node)
    print("Двунаправленный поиск:")
    if result is None:
        print("Решение не найдено")
    else:
        print("Глубина:", result.depth)
        print("Путь:", result.get_path())
        print("Узлов создано:", Node.number_of_nodes)