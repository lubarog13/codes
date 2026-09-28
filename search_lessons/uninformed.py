from collections import deque

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


# Поиск в ширину
def breadth_first_search(
    initial_node: Node, goal_node: Node, by_step: bool = False
) -> tuple[Node | None, int]:
    queue = deque([initial_node])
    visited = {initial_node.get_state_string()}
    iterations = 0

    while queue:
        iterations += 1
        current_node = queue.popleft()
        if current_node.compare_states(goal_node):
            if by_step:
                print_solution_steps(current_node)
            return current_node, iterations

        for child in current_node.create_children():
            state = child.get_state_string()
            if state not in visited:
                visited.add(state)
                queue.append(child)

    return None, iterations


# Расширяет один узел
def expand_side(
    queue: deque,
    visited: dict[str, Node],
    other_visited: dict[str, Node],
) -> tuple[Node, Node] | None:
    current = queue.popleft()

    for child in current.create_children():
        state = child.get_state_string()
        if state in visited:
            continue
        visited[state] = child
        queue.append(child)
        if state in other_visited:
            return child, other_visited[state]

    return None


# Склеивает путь (инверсия ходов из обратного поиска)
def connect_paths(forward_node: Node, backward_node: Node) -> Node:
    actions_to_goal = []
    current = backward_node
    while current.parent is not None:
        actions_to_goal.append(OPPOSITE[current.action])
        current = current.parent

    node = forward_node
    for action in actions_to_goal:
        for child in node.create_children():
            if child.action == action:
                node = child
                break
    return node


# Двунаправленный поиск
def bidirectional_search(
    initial_node: Node, goal_node: Node, by_step: bool = False
) -> tuple[Node | None, int]:
    if initial_node.compare_states(goal_node):
        if by_step:
            print_solution_steps(initial_node)
        return initial_node, 0

    forward_queue = deque([initial_node])
    backward_queue = deque([goal_node])
    forward_visited: dict[str, Node] = {initial_node.get_state_string(): initial_node}
    backward_visited: dict[str, Node] = {goal_node.get_state_string(): goal_node}
    iterations = 0

    while forward_queue and backward_queue:
        iterations += 1
        # Расширяем меньший фронтир — меньше узлов на каждом шаге
        if len(forward_queue) <= len(backward_queue):
            meeting = expand_side(forward_queue, forward_visited, backward_visited)
            if meeting is not None:
                forward_meet, backward_meet = meeting
                result = connect_paths(forward_meet, backward_meet)
                if by_step:
                    print_solution_steps(result)
                return result, iterations
        else:
            meeting = expand_side(backward_queue, backward_visited, forward_visited)
            if meeting is not None:
                backward_meet, forward_meet = meeting
                result = connect_paths(forward_meet, backward_meet)
                if by_step:
                    print_solution_steps(result)
                return result, iterations

    return None, iterations
