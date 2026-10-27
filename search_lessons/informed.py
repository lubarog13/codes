import heapq
from typing import Callable

from models import Node

OPPOSITE = {
    "Вверх": "Вниз",
    "Вниз": "Вверх",
    "Влево": "Вправо",
    "Вправо": "Влево",
}


def format_board(state: list[list[int]]) -> str:
    return "\n".join(
        " ".join(str(cell) if cell != 0 else " " for cell in row)
        for row in state
    )


def print_solution_steps(node: Node) -> None:
    chain: list[Node] = []
    current = node
    while current is not None:
        chain.append(current)
        current = current.parent
    chain.reverse()

    print("Шаг 0 (старт):")
    print(format_board(chain[0].current_state))
    for i, n in enumerate(chain[1:], start=1):
        print(f"\nШаг {i}: {n.action}")
        print(format_board(n.current_state))
        print("Нажмите Enter для продолжения...")
        input()


def _goal_positions(goal_state: list[list[int]]) -> dict[int, tuple[int, int]]:
    positions: dict[int, tuple[int, int]] = {}
    for i in range(3):
        for j in range(3):
            tile = goal_state[i][j]
            if tile != 0:
                positions[tile] = (i, j)
    return positions


# h1 — число фишек не на своём месте (пустая клетка не учитывается).
def heuristic_misplaced(goal_state: list[list[int]]) -> Callable[[Node], float]:
    goal_pos = _goal_positions(goal_state)

    def h1(node: Node) -> float:
        count = 0
        for i in range(3):
            for j in range(3):
                tile = node.current_state[i][j]
                if tile != 0 and goal_pos[tile] != (i, j):
                    count += 1
        return float(count)

    return h1


# h2 — сумма манхэттенских расстояний от каждой фишки до цели.
def heuristic_manhattan(goal_state: list[list[int]]) -> Callable[[Node], float]:
    goal_pos = _goal_positions(goal_state)

    def h2(node: Node) -> float:
        total = 0
        for i in range(3):
            for j in range(3):
                tile = node.current_state[i][j]
                if tile != 0:
                    gi, gj = goal_pos[tile]
                    total += abs(i - gi) + abs(j - gj)
        return float(total)

    return h2


def greedy_search(
    initial_node: Node,
    goal_node: Node,
    criteria: Callable[[Node], float],
    by_step: bool = False,
) -> tuple[Node | None, int]:
    counter = 0
    heap: list[tuple[float, int, Node]] = []
    heapq.heappush(heap, (criteria(initial_node), counter, initial_node))
    counter += 1
    visited: set[str] = set()
    iterations = 0

    while heap:
        iterations += 1
        _, _, current_node = heapq.heappop(heap) # pop - извлекает и удаляет минимальный элемент из очереди
        state_key = current_node.get_state_string()
        if state_key in visited:
            continue
        visited.add(state_key)

        if current_node.compare_states(goal_node):
            if by_step:
                print_solution_steps(current_node)
            return current_node, iterations

        for child in current_node.create_children():
            child_key = child.get_state_string()
            # добавляет элемент в очередь с приоритетом
            if child_key not in visited:
                heapq.heappush(heap, (criteria(child), counter, child))
                counter += 1

    return None, iterations


def a_star_search(
    initial_node: Node,
    goal_node: Node,
    criteria: Callable[[Node], float],
    by_step: bool = False,
) -> tuple[Node | None, int]:
    counter = 0
    heap: list[tuple[float, int, Node]] = []
    g_score: dict[str, int] = {initial_node.get_state_string(): 0} # g_score - минимальное известное расстояние от начального узла до текущего
    heapq.heappush(
        heap,
        (criteria(initial_node), counter, initial_node),
    )
    counter += 1
    closed: set[str] = set()
    iterations = 0

    while heap:
        iterations += 1
        _, _, current_node = heapq.heappop(heap) # pop - извлекает и удаляет минимальный элемент из очереди
        state_key = current_node.get_state_string()
        if state_key in closed:
            continue
        if current_node.depth > g_score.get(state_key, float("inf")): # current_node.depth - текущая глубина узла
            continue
        closed.add(state_key)

        if current_node.compare_states(goal_node):
            if by_step:
                print_solution_steps(current_node)
            return current_node, iterations

        for child in current_node.create_children():
            child_key = child.get_state_string()
            if child_key in closed: # closed - множество узлов, которые уже были посещены
                continue
            tentative_g = child.depth
            # если текущее расстояние от начального узла до текущего узла больше, чем минимальное известное расстояние от начального узла до текущего узла, то пропускаем узел
            if tentative_g >= g_score.get(child_key, float("inf")):
                continue
            g_score[child_key] = tentative_g
            f = tentative_g + criteria(child) # f - оценка стоимости пути от начального узла до текущего узла
            heapq.heappush(heap, (f, counter, child)) # push - добавляет элемент в очередь с приоритетом
            counter += 1

    return None, iterations
