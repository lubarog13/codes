from collections import deque

from models import Node

OPPOSITE = {
    "Вверх": "Вниз",
    "Вниз": "Вверх",
    "Влево": "Вправо",
    "Вправо": "Влево",
}


# Поиск в ширину
def breadth_first_search(initial_node: Node, goal_node: Node) -> Node | None:
    queue = deque([initial_node])
    visited = {initial_node.get_state_string()}

    while queue:
        current_node = queue.popleft()
        if current_node.compare_states(goal_node):
            return current_node

        for child in current_node.create_children():
            state = child.get_state_string()
            if state not in visited:
                visited.add(state)
                queue.append(child)

    return None

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
        if state in other_visited: # Встреча с другим узлом
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
def bidirectional_search(initial_node: Node, goal_node: Node) -> Node | None:
    if initial_node.compare_states(goal_node):
        return initial_node

    forward_queue = deque([initial_node])
    backward_queue = deque([goal_node])
    forward_visited: dict[str, Node] = {initial_node.get_state_string(): initial_node}
    backward_visited: dict[str, Node] = {goal_node.get_state_string(): goal_node}

    while forward_queue and backward_queue:
        # Расширяем меньший фронтир — меньше узлов на каждом шаге
        if len(forward_queue) <= len(backward_queue):
            meeting = expand_side(forward_queue, forward_visited, backward_visited)
            if meeting is not None:
                forward_meet, backward_meet = meeting
                return connect_paths(forward_meet, backward_meet)
        else:
            meeting = expand_side(backward_queue, backward_visited, forward_visited)
            if meeting is not None:
                backward_meet, forward_meet = meeting
                return connect_paths(forward_meet, backward_meet)

    return None
