Coursera projects

Там Очистка всех disposable происходит в PresenterFragment в методе onDetach(), а так как теперь Presenter хранить состояние, то его очистка в методе фрагмента onDestroy() до его выполнения приведет к тому, что observer не будет выполнять код в subscribe(). Пример - Ставим delay(5, Timeunit.SECONDS) на getProjects() observer для большей задержки, во время загрузки - делаем поворот экрана. После этих действий код в subscribe перестанет вызываться.
Решение - перенести вызов disposeAll() метода в onDestroy() метод класса MvpPresenter