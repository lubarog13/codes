# Рассматривается задача «Головоломка 8-ка».
# Задана доска с 8 пронумерованными фишками и с одним пустым участком.
# Фишка, смежная с пустым участком, может быть передвинута на этот участок. Требуется достичь указанного целевого состояния.


class Node:
    current_state: list[list[int]]
    parent: 'Node'
    action: str
    depth: int
    node_id: int
    number_of_nodes: int = 0

    def __init__(self, current_state: list[list[int]], parent: 'Node', action: str, depth: int, node_id: int):
        self.current_state = current_state
        self.parent = parent
        self.action = action
        self.depth = depth
        self.node_id = node_id

        Node.number_of_nodes += 1

    def compare_states(self, other: 'Node') -> bool:
        return self.current_state == other.current_state

    def get_path(self) -> list[str]:
        path = []
        current = self
        while current:
            if current.action is not None:
                path.append(current.action)
            current = current.parent
        return path[::-1]

    def get_state_string(self) -> str:
        return " ".join(
            str(cell) if cell != 0 else " "
            for row in self.current_state
            for cell in row
        )

    def create_children(self) -> list['Node']:
        children = []
        pos_i = pos_j = 0
        for i in range(3):
            for j in range(3):
                if self.current_state[i][j] == 0:
                    pos_i, pos_j = i, j

        MOVES = {
            (-1, 0): "Вверх",
            (1, 0): "Вниз",
            (0, -1): "Влево",
            (0, 1): "Вправо"
        }

        for move, action in MOVES.items():
            new_pos_i = pos_i + move[0]
            new_pos_j = pos_j + move[1]

            if 0 <= new_pos_i <= 2 and 0 <= new_pos_j <= 2:
                new_state = [row[:] for row in self.current_state]
                new_state[pos_i][pos_j], new_state[new_pos_i][new_pos_j] = (
                    new_state[new_pos_i][new_pos_j],
                    new_state[pos_i][pos_j],
                )
                new_node = Node(new_state, self, action, self.depth + 1, Node.number_of_nodes + 1)
                if (not self.parent) or (not self.parent.compare_states(new_node)):
                    children.append(new_node)
        return children
