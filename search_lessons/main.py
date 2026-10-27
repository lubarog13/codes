import sys

from models import Node
from uninformed import breadth_first_search, bidirectional_search
from informed import (
    a_star_search,
    greedy_search,
    heuristic_manhattan,
    heuristic_misplaced,
)


def run_search(
    title: str,
    search_fn,
    initial_node: Node,
    goal_node: Node,
    by_step: bool,
    criteria=None,
) -> None:
    print(title)
    if criteria is None:
        result, iterations = search_fn(initial_node, goal_node, by_step)
    else:
        result, iterations = search_fn(initial_node, goal_node, criteria, by_step)
    if result is None:
        print("Решение не найдено")
    else:
        print("Глубина:", result.depth)
        print("Путь:", result.get_path())
        print("Итераций:", iterations)
        print("Узлов создано:", Node.number_of_nodes)
    print()


if __name__ == "__main__":
    initial_state = [
        [4, 8, 1],
        [0, 3, 6],
        [2, 7, 5],
    ]
    goal_state = [
        [1, 2, 3],
        [8, 0, 4],
        [7, 6, 5],
    ]

    by_step = len(sys.argv) > 1 and sys.argv[1] == "--by-step"

    Node.number_of_nodes = 0
    initial_node = Node(initial_state, None, None, 0, 0)
    goal_node = Node(goal_state, None, None, 0, 0)
    run_search("Поиск в ширину:", breadth_first_search, initial_node, goal_node, by_step)

    Node.number_of_nodes = 0
    initial_node = Node(initial_state, None, None, 0, 0)
    goal_node = Node(goal_state, None, None, 0, 0)
    run_search(
        "Двунаправленный поиск:",
        bidirectional_search,
        initial_node,
        goal_node,
        by_step,
    )

    for name, heuristic_factory in (
        ("h1 (фишки не на месте)", heuristic_misplaced),
        ("h2 (манхэттен)", heuristic_manhattan),
    ):
        h = heuristic_factory(goal_state)

        Node.number_of_nodes = 0
        initial_node = Node(initial_state, None, None, 0, 0)
        goal_node = Node(goal_state, None, None, 0, 0)

        run_search(
            f"Жадный поиск, {name}:",
            greedy_search,
            initial_node,
            goal_node,
            by_step,
            criteria=h,
        )

        Node.number_of_nodes = 0
        initial_node = Node(initial_state, None, None, 0, 0)
        goal_node = Node(goal_state, None, None, 0, 0)

        run_search(
            f"A*, {name}:",
            a_star_search,
            initial_node,
            goal_node,
            by_step,
            criteria=h,
        )
